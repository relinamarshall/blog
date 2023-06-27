package com.blog.gateway.config;

import com.blog.gateway.filter.PasswordDecoderFilter;
import com.blog.gateway.filter.RequestGlobalFilter;
import com.blog.gateway.filter.SwaggerBasicGatewayFilter;
import com.blog.gateway.filter.ValidateCodeGatewayFilter;
import com.blog.gateway.handler.GlobalExceptionHandler;
import com.blog.gateway.handler.ImageCodeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * GatewayConfiguration
 * 网关配置
 *
 * @author Wenzhou
 * @since 2023/5/11 10:52
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfiguration {
    @Bean
    public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties configProperties) {
        return new PasswordDecoderFilter(configProperties);
    }

    @Bean
    public RequestGlobalFilter pigRequestGlobalFilter() {
        return new RequestGlobalFilter();
    }

    @Bean
    @ConditionalOnProperty(name = "swagger.basic.enabled")
    public SwaggerBasicGatewayFilter swaggerBasicGatewayFilter(
            SpringDocConfiguration.SwaggerDocProperties swaggerProperties) {
        return new SwaggerBasicGatewayFilter(swaggerProperties);
    }

    @Bean
    public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties,
                                                               ObjectMapper objectMapper, RedisTemplate redisTemplate) {
        return new ValidateCodeGatewayFilter(configProperties, objectMapper, redisTemplate);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }

    @Bean
    public ImageCodeHandler imageCodeHandler(RedisTemplate redisTemplate) {
        return new ImageCodeHandler(redisTemplate);
    }
}
