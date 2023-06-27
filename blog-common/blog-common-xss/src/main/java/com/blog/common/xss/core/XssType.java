package com.blog.common.xss.core;


import com.blog.common.xss.exception.FromXssException;
import com.blog.common.xss.exception.JacksonXssException;

/**
 * XssType
 * <p>
 * xss 数据处理类型
 *
 * @author Wenzhou
 * @since 2023/5/12 9:30
 */
public enum XssType {
    /**
     * 表单
     */
    FORM() {
        @Override
        public RuntimeException getXssException(String input, String message) {
            return new FromXssException(input, message);
        }
    },

    /**
     * body json
     */
    JACKSON() {
        @Override
        public RuntimeException getXssException(String input, String message) {
            return new JacksonXssException(input, message);
        }
    };

    /**
     * 获取 xss 异常
     *
     * @param input   input
     * @param message message
     * @return XssException
     */
    public abstract RuntimeException getXssException(String input, String message);
}

