package kz.oj.fjhw9.feignclient.event;

import kz.oj.fjhw9.data.EventsPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventClient {

    private final EventClientInternal eventClientInternal;
    private final int pageSize = 100;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Async
    public CompletableFuture<EventsPage> getEvents(LocalDate dateFrom, LocalDate dateTo, int page) {

        log.info("Requesting events page #{}...", page);
        String fields = "title,price,site_url";
        return CompletableFuture.completedFuture(getEventsInternal(dateFrom, dateTo, fields, page));
    }

    @Async
    public CompletableFuture<Integer> countPages(LocalDate dateFrom, LocalDate dateTo) {

        log.info("Requesting the number of pages...");
        EventsPage page = getEventsInternal(dateFrom, dateTo, "id", 1);
        double d = (double) page.count() / pageSize;
        int retval = (int) Math.ceil(d);

        log.info("There are {} pages", retval);
        return CompletableFuture.completedFuture(retval);
    }

    private EventsPage getEventsInternal(LocalDate dateFrom, LocalDate dateTo, String fields, int page) {

        String dateFromString = formatter.format(dateFrom);
        String dateToString = formatter.format(dateTo);

        return eventClientInternal.getPage(dateFromString, dateToString, fields, pageSize, page);
    }
}
