package com.example.desafio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@SuppressWarnings("unused")
@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService orderExecutorService() {
        int corePoolSize = 20;
        int maxPoolSize = 100;
        long keepAliveTime = 30L;
        int queueCapacity = 500;

        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
