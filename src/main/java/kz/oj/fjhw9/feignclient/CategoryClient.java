package kz.oj.fjhw9.feignclient;

import kz.oj.fjhw9.data.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "category", url = "${application.categories-url}")
public interface CategoryClient {

    @GetMapping
    List<Category> findAll();
}
