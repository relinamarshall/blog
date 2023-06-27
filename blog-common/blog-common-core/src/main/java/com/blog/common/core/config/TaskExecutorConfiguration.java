package com.blog.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TaskExecutorConfiguration
 *
 * @author Wenzhou
 * @since 2023/6/19 15:55
 */
@AutoConfiguration
public class TaskExecutorConfiguration implements AsyncConfigurer {
    /**
     * 获取当前机器的核数, 不一定准确 请根据实际场景 CPU密集 || IO 密集
     */
    public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();
    /**
     * corePoolSize
     */
    @Value("${thread.pool.corePoolSize:}")
    private Optional<Integer> corePoolSize;
    /**
     * maxPoolSize
     */
    @Value("${thread.pool.maxPoolSize:}")
    private Optional<Integer> maxPoolSize;
    /**
     * queueCapacity
     */
    @Value("${thread.pool.queueCapacity:}")
    private Optional<Integer> queueCapacity;
    /**
     * awaitTerminationSeconds
     */
    @Value("${thread.pool.awaitTerminationSeconds:}")
    private Optional<Integer> awaitTerminationSeconds;

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程大小 默认取 CPU 数量
        taskExecutor.setCorePoolSize(corePoolSize.orElse(CPU_NUM));
        // 最大线程大小 默认取 CPU * 2 数量
        taskExecutor.setMaxPoolSize(maxPoolSize.orElse(CPU_NUM * 2));
        // 队列最大容量
        taskExecutor.setQueueCapacity(queueCapacity.orElse(500));
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds.orElse(60));
        taskExecutor.setThreadNamePrefix("Blog-Thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
