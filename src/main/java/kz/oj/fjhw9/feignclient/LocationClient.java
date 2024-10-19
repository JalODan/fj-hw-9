package kz.oj.fjhw9.feignclient;

import kz.oj.fjhw9.data.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "locations", url = "${application.locations-url}")
public interface LocationClient {

    @GetMapping
    List<Location> findAll();
}