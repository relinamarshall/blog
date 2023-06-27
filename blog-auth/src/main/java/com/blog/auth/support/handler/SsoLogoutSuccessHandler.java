package com.blog.auth.support.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * SsoLogoutSuccessHandler
 * <p>
 * sso 退出功能 ，根据客户端传入跳转
 *
 * @author Wenzhou
 * @since 2023/5/11 14:01
 */
public class SsoLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final String REDIRECT_URL = "redirect_url";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        if (response == null) {
            return;
        }

        // 获取请求参数中是否包含 回调地址
        String redirectUrl = request.getParameter(REDIRECT_URL);
        if (CharSequenceUtil.isNotBlank(redirectUrl)) {
            response.sendRedirect(redirectUrl);
        } else if (CharSequenceUtil.isNotBlank(request.getHeader(HttpHeaders.REFERER))) {
            // 默认跳转referer 地址
            String referer = request.getHeader(HttpHeaders.REFERER);
            response.sendRedirect(referer);
        }
    }
}
