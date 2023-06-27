package com.blog.auth.support.handler;


import com.blog.common.core.util.DiyWebUtil;
import com.blog.common.core.util.SpringContextHolder;
import com.blog.common.log.event.LogEvent;
import com.blog.common.log.util.SysLogUtils;
import com.blog.upms.core.entity.Log;

import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * LogoutSuccessEventHandler
 * <p>
 * 事件机制处理退出相关
 *
 * @author Wenzhou
 * @since 2023/5/11 15:08
 */
@Slf4j
@Component
public class LogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            handle(authentication);
        }
    }

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     *
     * @param authentication 登录对象
     */
    public void handle(Authentication authentication) {
        log.info("用户：{} 退出成功", authentication.getPrincipal());
        Log logVo = SysLogUtils.getSysLog();
        logVo.setTitle("退出成功");
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Long endTime = System.currentTimeMillis();
        logVo.setTime(endTime - startTime);

        // 设置对应的token
        DiyWebUtil.getRequest().ifPresent(request -> logVo.setParams(request.getHeader(HttpHeaders.AUTHORIZATION)));

        // 这边设置ServiceId
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            logVo.setServiceId(authentication.getCredentials().toString());
        }
        logVo.setCrtBy(authentication.getName());
        logVo.setUpdBy(authentication.getName());
        SpringContextHolder.publishEvent(new LogEvent(logVo));
    }
}