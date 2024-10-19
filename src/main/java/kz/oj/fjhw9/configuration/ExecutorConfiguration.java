package kz.oj.fjhw9.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorConfiguration {

    @Value("${application.fixed-thread-pool-size}")
    private Integer fixedThredPoolSize;

    @Value("${application.scheduled-thread-pool-size}")
    private Integer scheduledThreadPoolSize;



    @Bean("myFixedThreadPool")
    public ExecutorService fixedThreadPool() {

        return Executors.newFixedThreadPool(10);
    }

    @Bean("myScheduledThreadPool")
    public ScheduledExecutorService scheduledThreadPool() {

        return Executors.newScheduledThreadPool(10);
    }
}
