package kz.oj.fjhw9.task;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DictionariesUpdateTask implements Callable<Void> {

    private final CategoriesUpdateTask categoriesUpdateTask;
    private final LocationsUpdateTask locationsUpdateTask;

    private ExecutorService fixedThreadPool;

    @Autowired
    public void setFixedThreadPool(@Qualifier("myFixedThreadPool") ExecutorService fixedThreadPool) {

        this.fixedThreadPool = fixedThreadPool;
    }

    @SneakyThrows
    @Override
    @Timed
    public Void call() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        fixedThreadPool.invokeAll(List.of(categoriesUpdateTask, locationsUpdateTask));
        stopWatch.stop();

        log.info("Took {} ms", stopWatch.getTotalTimeMillis());
        return null;
    }

}
