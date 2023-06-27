package com.blog.common.security.interceptor;


import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.core.util.DiyWebUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DiyOAuthRequestInterceptor
 * <p>
 * oauth2 feign token传递 重新 OAuth2FeignRequestInterceptor ，官方实现部分常见不适用
 *
 * @author Wenzhou
 * @since 2023/5/8 16:32
 */
@Slf4j
@RequiredArgsConstructor
public class DiyOAuthRequestInterceptor implements RequestInterceptor {

    private final BearerTokenResolver tokenResolver;

    /**
     * Create a template with the header of provided name and extracted extract </br>
     * <p>
     * 1. 如果使用 非web 请求，header 区别 </br>
     * <p>
     * 2. 根据authentication 还原请求token
     *
     * @param template RequestTemplate
     */
    @Override
    public void apply(RequestTemplate template) {
        Collection<String> fromHeader = template.headers().get(SecurityConstants.FROM);
        // 带from 请求直接跳过
        if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityConstants.FROM_IN)) {
            return;
        }

        // 非web 请求直接跳过
        if (!DiyWebUtil.getRequest().isPresent()) {
            return;
        }
        HttpServletRequest request = DiyWebUtil.getRequest().get();
        // 避免请求参数的 query token 无法传递
        String token = tokenResolver.resolve(request);
        if (CharSequenceUtil.isBlank(token)) {
            return;
        }
        template.header(HttpHeaders.AUTHORIZATION,
                String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), token));
    }
}

