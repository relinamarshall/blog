package com.blog.common.xss.exception;

import lombok.Getter;

/**
 * FromXssException
 * <p>
 * xss 表单异常
 *
 * @author Wenzhou
 * @since 2023/5/12 9:31
 */
@Getter
public class FromXssException extends IllegalStateException implements XssException {

    private final String input;

    public FromXssException(String input, String message) {
        super(message);
        this.input = input;
    }
}