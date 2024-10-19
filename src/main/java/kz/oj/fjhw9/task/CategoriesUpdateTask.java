package kz.oj.fjhw9.task;

import kz.oj.fjhw9.feignclient.CategoryClient;
import kz.oj.fjhw9.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class CategoriesUpdateTask implements Callable<Void> {

    private final CategoryService categoryService;
    private final CategoryClient categoryClient;

    @Override
    public Void call() {

        categoryService.update(categoryClient.findAll());
        return null;
    }

}
