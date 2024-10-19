package kz.oj.fjhw9.service;

import kz.oj.fjhw9.data.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CategoryService {

    private final ConcurrentHashMap.KeySetView<Category, Boolean> categories = ConcurrentHashMap.newKeySet();

    public void update(List<Category> categories) {

        this.categories.clear();
        this.categories.addAll(categories);
    }
}
