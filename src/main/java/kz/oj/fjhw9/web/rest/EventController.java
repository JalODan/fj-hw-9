package kz.oj.fjhw9.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.oj.fjhw9.data.Currency;
import kz.oj.fjhw9.service.EventService;
import kz.oj.fjhw9.web.rest.response.GetEventsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Events Controller")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(operationId = "getEvents", summary = "Получение событий на основе пользовательских пожеланий")
    public CompletableFuture<GetEventsResponse> getEvents(
            @Parameter(description = "Бюджет пользователя") @RequestParam("budget") Double budget,
            @Parameter(description = "Валюта пользователя") @RequestParam("currency") Currency currency,
            @Parameter(description = "Начало периода, за который пользователя интересуют события") @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
            @Parameter(description = "Начало периода, за который пользователя интересуют события") @RequestParam(value = "dateTo", required = false) LocalDate dateTo
    ) {

        return eventService.getEvents(budget, currency, dateFrom, dateTo);
    }
}
