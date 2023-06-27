package com.alibaba.nacos.config;

import com.alibaba.nacos.console.filter.XssFilter;
import com.alibaba.nacos.core.code.ControllerMethodsCache;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.ZoneId;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

/**
 * ConsoleConfig
 *
 * @author Wenzhou
 * @since 2023/5/10 13:47
 */
@Component
@EnableScheduling
@RequiredArgsConstructor
@PropertySource("/application.yml")
public class ConsoleConfig {
    /**
     * methodsCache
     */
    private final ControllerMethodsCache methodsCache;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        methodsCache.initClassMethod("com.alibaba.nacos.core.controller");
        methodsCache.initClassMethod("com.alibaba.nacos.naming.controllers");
        methodsCache.initClassMethod("com.alibaba.nacos.config.server.controller");
        methodsCache.initClassMethod("com.alibaba.nacos.controller");
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        // 5h
        config.setMaxAge(18000L);
        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public XssFilter xssFilter() {
        return new XssFilter();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(ZoneId.systemDefault().toString());
    }
}
