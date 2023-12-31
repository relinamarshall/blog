package com.blog.xxl.job.admin.controller.interceptor;


import com.blog.xxl.job.admin.core.util.FtlUtil;
import com.blog.xxl.job.admin.core.util.I18nUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CookieInterceptor
 *
 * @author Wenzhou
 * @since 2023/5/11 8:55
 */
@Component
public class CookieInterceptor implements AsyncHandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // cookie
        if (modelAndView != null && request.getCookies() != null && request.getCookies().length > 0) {
            HashMap<String, Cookie> cookieMap = new HashMap<>();
            for (Cookie ck : request.getCookies()) {
                cookieMap.put(ck.getName(), ck);
            }
            modelAndView.addObject("cookieMap", cookieMap);
        }

        // static method
        if (modelAndView != null) {
            modelAndView.addObject("I18nUtil", FtlUtil.generateStaticModel(I18nUtil.class.getName()));
        }
    }
}
