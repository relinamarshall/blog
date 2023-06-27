package com.blog.common.core.exception;

/**
 * ValidateCodeException
 *
 * @author Wenzhou
 * @since 2023/6/19 16:15
 */
public class ValidateCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidateCodeException() {
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
