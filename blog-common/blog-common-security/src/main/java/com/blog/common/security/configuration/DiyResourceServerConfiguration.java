package com.blog.common.security.configuration;


import com.blog.common.security.component.DiyBearerTokenExtractor;
import com.blog.common.security.component.ResourceAuthExceptionEntryPoint;
import com.blog.common.security.config.PermitAllUrlProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DiyResourceServerConfiguration
 * <p>
 * 资源服务器认证授权配置
 *
 * @author Wenzhou
 * @since 2023/5/8 16:19
 */
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class DiyResourceServerConfiguration {

    protected final ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

    private final PermitAllUrlProperties permitAllUrl;

    private final DiyBearerTokenExtractor pigBearerTokenExtractor;

    private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(ArrayUtil.toArray(permitAllUrl.getUrls(), String.class))
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
                                .authenticationEntryPoint(resourceAuthExceptionEntryPoint)
                                .bearerTokenResolver(pigBearerTokenExtractor))
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .disable();

        return http.build();
    }

}

