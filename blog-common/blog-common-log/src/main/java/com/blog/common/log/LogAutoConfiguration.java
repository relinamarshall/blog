package com.blog.common.log;

import com.blog.common.log.aspect.LogAspect;
import com.blog.common.log.aspect.LogListener;
import com.blog.upms.core.feign.RemoteLogService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.RequiredArgsConstructor;

/**
 * LogAutoConfiguration
 *
 * @author Wenzhou
 * @since 2023/6/19 17:57
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {
    @Bean
    public LogListener sysLogListener(RemoteLogService remoteLogService) {
        return new LogListener(remoteLogService);
    }

    @Bean
    public LogAspect sysLogAspect() {
        return new LogAspect();
    }
}
