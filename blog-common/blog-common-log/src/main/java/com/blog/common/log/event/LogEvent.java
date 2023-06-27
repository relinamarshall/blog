package com.blog.common.log.event;

import com.blog.upms.core.entity.Log;

import org.springframework.context.ApplicationEvent;

/**
 * LogEvent
 * <p>
 * 系统日志事件
 *
 * @author Wenzhou
 * @since 2023/6/19 17:34
 */
public class LogEvent extends ApplicationEvent {
    /**
     * SysLogEvent
     *
     * @param source SysLog
     */
    public LogEvent(Log source) {
        super(source);
    }
}