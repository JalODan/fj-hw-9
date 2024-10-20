package kz.oj.fjhw9.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorConfiguration {

    @Value("${application.fixed-thread-pool-size}")
    private Integer fixedThreadPoolSize;

    @Value("${application.scheduled-thread-pool-size}")
    private Integer scheduledThreadPoolSize;

    @Bean("myFixedThreadPool")
    public ExecutorService fixedThreadPool() {

        return Executors.newFixedThreadPool(fixedThreadPoolSize);
    }

    @Bean("myScheduledThreadPool")
    public ScheduledExecutorService scheduledThreadPool() {

        return Executors.newScheduledThreadPool(scheduledThreadPoolSize);
    }

    @Bean
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }
}
