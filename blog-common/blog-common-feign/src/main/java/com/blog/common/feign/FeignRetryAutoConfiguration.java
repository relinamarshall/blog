package com.blog.common.feign;


import com.blog.common.feign.retry.FeignRetryAspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

/**
 * FeignRetryAutoConfiguration
 * 重试配置
 *
 * @author Wenzhou
 * @since 2023/5/6 17:08
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RetryTemplate.class)
public class FeignRetryAutoConfiguration {
    @Bean
    public FeignRetryAspect feignRetryAspect() {
        return new FeignRetryAspect();
    }
}
