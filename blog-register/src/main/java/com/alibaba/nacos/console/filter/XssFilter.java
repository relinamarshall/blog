package com.alibaba.nacos.console.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * XssFilter
 *
 * @author Wenzhou
 * @since 2023/5/10 13:45
 */
public class XssFilter extends OncePerRequestFilter {

    private static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";

    private static final String CONTENT_SECURITY_POLICY = "script-src 'self'";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader(CONTENT_SECURITY_POLICY_HEADER, CONTENT_SECURITY_POLICY);
        filterChain.doFilter(request, response);
    }

}
