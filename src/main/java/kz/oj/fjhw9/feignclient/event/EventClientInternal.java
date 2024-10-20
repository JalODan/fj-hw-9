package kz.oj.fjhw9.feignclient.event;

import kz.oj.fjhw9.data.EventsPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "event", url = "${application.events-url}")
interface EventClientInternal {

    @GetMapping
    EventsPage getPage(
            @RequestParam(name = "actual_since") String dateFrom,
            @RequestParam(name = "actual_until") String dateTo,
            @RequestParam(name = "fields") String fields,
            @RequestParam(name = "page_size") Integer pageSize,
            @RequestParam(name = "page") Integer page
    );
}
