package com.blog.gateway.config;


import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import reactor.core.publisher.Mono;

/**
 * RateLimiterConfiguration
 * <p>
 * 路由限流配置
 *
 * @author Wenzhou
 * @since 2023/5/11 10:53
 */
@Configuration(proxyBeanMethods = false)
public class RateLimiterConfiguration {
    /**
     * Remote addr key resolver key resolver.
     * <p>
     * https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-requestratelimiter-gatewayfilter-factory
     */
    @Bean
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono
                .just(Objects.requireNonNull(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()))
                        .getAddress()
                        .getHostAddress());
    }
}
