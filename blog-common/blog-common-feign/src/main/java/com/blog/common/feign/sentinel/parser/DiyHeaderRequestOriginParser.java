package com.blog.common.feign.sentinel.parser;


import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

import javax.servlet.http.HttpServletRequest;

/**
 * HeaderRequestOriginParser
 * <p>
 * sentinel 请求头解析判断
 *
 * @author Wenzhou
 * @since 2023/5/6 17:05
 */
public class DiyHeaderRequestOriginParser implements RequestOriginParser {
    /**
     * 请求头获取allow
     */
    private static final String ALLOW = "Allow";

    /**
     * Parse the origin from given HTTP request.
     *
     * @param request HTTP request
     * @return parsed origin
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getHeader(ALLOW);
    }
}
