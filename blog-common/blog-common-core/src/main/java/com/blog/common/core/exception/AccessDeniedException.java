package com.blog.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * AccessDeniedException
 *
 * @author Wenzhou
 * @since 2023/6/19 16:14
 */
@NoArgsConstructor
public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
