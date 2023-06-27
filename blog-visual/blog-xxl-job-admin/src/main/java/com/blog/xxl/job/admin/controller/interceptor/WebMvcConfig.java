package com.blog.xxl.job.admin.controller.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebMvcConfig
 *
 * @author Wenzhou
 * @since 2023/5/11 8:56
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private PermissionInterceptor permissionInterceptor;

    @Resource
    private CookieInterceptor cookieInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**");
        registry.addInterceptor(cookieInterceptor).addPathPatterns("/**");
    }
}
