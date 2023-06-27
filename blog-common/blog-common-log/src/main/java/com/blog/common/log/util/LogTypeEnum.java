package com.blog.common.log.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * LogTypeEnum
 * <p>
 * 日志类型
 *
 * @author Wenzhou
 * @since 2023/6/19 17:29
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {
    /**
     * 正常日志类型
     */
    NORMAL("0", "正常日志"),

    /**
     * 错误日志类型
     */
    ERROR("9", "错误日志");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;
}