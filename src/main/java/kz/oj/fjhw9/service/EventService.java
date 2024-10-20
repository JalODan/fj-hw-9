package kz.oj.fjhw9.service;

import jakarta.annotation.Nullable;
import kz.oj.fjhw9.data.Currency;
import kz.oj.fjhw9.data.Event;
import kz.oj.fjhw9.data.EventsPage;
import kz.oj.fjhw9.feignclient.event.EventClient;
import kz.oj.fjhw9.web.rest.response.GetEventsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final PriceParser priceParser;
    private final CurrencyService currencyService;
    private final EventClient eventClient;

    public CompletableFuture<GetEventsResponse> getEvents(
            Double budget,
            Currency currency,
            @Nullable LocalDate dateFrom,
            @Nullable LocalDate dateTo
    ) {

        LocalDate finalDateFrom = dateFromOrDefault(dateFrom);
        LocalDate finalDateTo = dateToOrDefault(dateTo);

        CompletableFuture<Double> budgetInRublesFuture = currencyService.convertToRubles(budget, currency);
        CompletableFuture<Integer> pageCountFuture = eventClient.countPages(finalDateFrom, finalDateTo);

        return budgetInRublesFuture
                .thenCombine(pageCountFuture, (budgetInRubles, pageCount) -> getEventsInternal(finalDateFrom, finalDateTo, budgetInRubles, pageCount))
                .thenCompose(future -> future);
    }

    private CompletableFuture<GetEventsResponse> getEventsInternal(LocalDate dateFrom, LocalDate dateTo, double budgetInRubles, int pageCount) {

        List<CompletableFuture<EventsPage>> futures = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            CompletableFuture<EventsPage> eventsFuture = eventClient.getEvents(dateFrom, dateTo, i);
            futures.add(eventsFuture);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v -> {

            List<Event> events = futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(eventsPage -> eventsPage.results().stream())
                    .filter(event -> {
                        Optional<Integer> priceOptional = priceParser.parse(event.price());
                        return priceOptional.isPresent() && priceOptional.get() <= budgetInRubles;
                    })
                    .toList();

            return new GetEventsResponse(events);
        });
    }

    private LocalDate dateFromOrDefault(LocalDate dateFrom) {

        if (dateFrom != null) {
            return dateFrom;
        }

        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private LocalDate dateToOrDefault(LocalDate dateTo) {

        if (dateTo != null) {
            return dateTo;
        }

        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

}
