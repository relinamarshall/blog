package com.blog.common.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.blog.common.feign.sentinel.ext.DiySentinelFeign;
import com.blog.common.feign.sentinel.handle.DiyUrlBlockHandler;
import com.blog.common.feign.sentinel.parser.DiyHeaderRequestOriginParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

import feign.Feign;
import okhttp3.OkHttpClient;

/**
 * FeignAutoConfiguration
 * <p>
 * sentinel 配置
 *
 * @author Wenzhou
 * @since 2023/5/6 17:06
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class FeignAutoConfiguration {
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    public Feign.Builder feignSentinelBuilder() {
        return DiySentinelFeign.builder();
    }

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler(ObjectMapper objectMapper) {
        return new DiyUrlBlockHandler(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser requestOriginParser() {
        return new DiyHeaderRequestOriginParser();
    }

    /**
     * OkHttp 客户端配置
     *
     * @return OkHttp 客户端配
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                // 是否开启缓存
                .retryOnConnectionFailure(false)
                // 连接超时时间
                .connectTimeout(30L, TimeUnit.SECONDS)
                // 读取超时时间
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                // 是否允许重定向
                .followRedirects(true)
                .build();
    }
}
