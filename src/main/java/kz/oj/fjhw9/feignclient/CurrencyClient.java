package kz.oj.fjhw9.feignclient;

import kz.oj.fjhw9.data.DailyRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "currency", url = "${application.currencies-url}")
public interface CurrencyClient {

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    DailyRatesResponse getDailyRates(@RequestParam(name = "date_req") LocalDate date);
}
