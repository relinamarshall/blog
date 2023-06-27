package com.blog.common.log.util;

import com.blog.common.core.constant.SecurityConstants;
import com.blog.upms.core.entity.Log;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import lombok.experimental.UtilityClass;

/**
 * SysLogUtils
 *
 * @author Wenzhou
 * @since 2023/6/19 17:28
 */
@UtilityClass
public class SysLogUtils {
    /**
     * getSysLog
     *
     * @return Log
     */
    public Log getSysLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Log log = new Log();
        log.setType(LogTypeEnum.NORMAL.getType());
        log.setRemoteAddr(ServletUtil.getClientIP(request));
        log.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        log.setMethod(request.getMethod());
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setParams(HttpUtil.toParams(request.getParameterMap()));
        log.setCrtBy(getUsername());
        log.setUpdBy(getUsername());
        log.setServiceId(getClientId());
        return log;
    }

    /**
     * 获取客户端
     *
     * @return clientId
     */
    private String getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal auth2Authentication = (OAuth2AuthenticatedPrincipal) principal;
            return MapUtil.getStr(auth2Authentication.getAttributes(), SecurityConstants.CLIENT_ID);
        }
        return null;
    }

    /**
     * 获取用户名称
     *
     * @return username
     */
    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

    /**
     * 获取 spel 定义的参数值
     *
     * @param context 参数容器
     * @param key     key
     * @param clazz   需要返回的类型
     * @param <T>     返回泛型
     * @return 参数值
     */
    public <T> T getValue(EvaluationContext context, String key, Class<T> clazz) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression expression = spelExpressionParser.parseExpression(key);
        return expression.getValue(context, clazz);
    }

    /**
     * 获取参数容器
     *
     * @param arguments       方法的参数列表
     * @param signatureMethod 被执行的方法体
     * @return 装载参数的容器
     */
    public EvaluationContext getContext(Object[] arguments, Method signatureMethod) {
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(signatureMethod);
        EvaluationContext context = new StandardEvaluationContext();
        if (parameterNames == null) {
            return context;
        }
        for (int i = 0; i < arguments.length; i++) {
            context.setVariable(parameterNames[i], arguments[i]);
        }
        return context;
    }
}