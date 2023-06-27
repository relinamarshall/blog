package com.blog.common.log.aspect;

import com.blog.common.log.event.LogEvent;
import com.blog.upms.core.entity.Log;
import com.blog.upms.core.feign.RemoteLogService;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * LogListener
 * <p>
 * 异步监听日志事件
 *
 * @author Wenzhou
 * @since 2023/6/19 17:34
 */
@Slf4j
@RequiredArgsConstructor
public class LogListener {
    /**
     * remoteLogService
     */
    private final RemoteLogService remoteLogService;

    /**
     * saveSysLog
     *
     * @param event SysLogEvent
     */
    @Async
    @Order
    @EventListener(LogEvent.class)
    public void saveSysLog(LogEvent event) {
        Log sysLog = (Log) event.getSource();
        remoteLogService.saveLog(sysLog);
    }
}
