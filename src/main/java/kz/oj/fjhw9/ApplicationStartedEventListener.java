package kz.oj.fjhw9;

import kz.oj.fjhw9.task.DictionariesUpdateTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@EnableAsync
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private ScheduledExecutorService scheduledExecutorService;
    private final DictionariesUpdateTask dictionariesUpdateTask;

    @Value("${application.dictionary-init-task-schedule}")
    private Duration duration;

    @Autowired
    public void setScheduledExecutorService(@Qualifier("myScheduledThreadPool") ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {

        scheduledExecutorService.scheduleAtFixedRate(dictionariesUpdateTask::call, 0, duration.toSeconds(), TimeUnit.SECONDS);
    }

}
