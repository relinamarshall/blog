package com.blog.common.xss.exception;

import lombok.Getter;

/**
 * JacksonXssException
 * <p>
 * xss jackson 异常
 *
 * @author Wenzhou
 * @since 2023/5/12 9:33
 */
@Getter
public class JacksonXssException extends IllegalStateException implements XssException {

    private final String input;

    public JacksonXssException(String input, String message) {
        super(message);
        this.input = input;
    }
}

