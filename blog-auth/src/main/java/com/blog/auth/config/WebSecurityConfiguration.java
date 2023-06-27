package com.blog.auth.config;


import com.blog.auth.support.core.DiyDaoAuthenticationProvider;
import com.blog.auth.support.core.FormIdentityLoginConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurityConfiguration
 * <p>
 * 服务安全相关配置
 *
 * @author Wenzhou
 * @since 2023/5/11 13:51
 */
@EnableWebSecurity
public class WebSecurityConfiguration {
    /**
     * spring security 默认的安全策略
     *
     * @param http security注入点
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/token/*")
                        .permitAll()// 开放自定义的部分端点
                        .anyRequest()
                        .authenticated())
                .headers()
                .frameOptions()
                .sameOrigin()// 避免iframe同源无法登录
                .and()
                .apply(new FormIdentityLoginConfigurer()); // 表单登录个性化
        // 处理 UsernamePasswordAuthenticationToken
        http.authenticationProvider(new DiyDaoAuthenticationProvider());
        return http.build();
    }

    /**
     * 暴露静态资源
     * <p>
     * https://github.com/spring-projects/spring-security/issues/10938
     *
     * @param http
     * @return
     */
    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http.requestMatchers((matchers) -> matchers.antMatchers("/actuator/**", "/css/**", "/error"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
                .requestCache()
                .disable()
                .securityContext()
                .disable()
                .sessionManagement()
                .disable();
        return http.build();
    }

}
