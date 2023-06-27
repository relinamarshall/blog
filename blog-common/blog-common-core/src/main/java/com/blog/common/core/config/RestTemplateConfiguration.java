package com.blog.common.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfiguration
 *
 * @author Wenzhou
 * @since 2023/6/19 15:55
 */
@AutoConfiguration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
