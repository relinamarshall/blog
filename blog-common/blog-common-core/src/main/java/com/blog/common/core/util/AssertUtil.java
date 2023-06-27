package com.blog.common.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

import lombok.experimental.UtilityClass;

/**
 * AssertUtil
 *
 * @author Wenzhou
 * @since 2023/6/27 21:33
 */
@UtilityClass
public final class AssertUtil {
    public static void notBlank(String string, String message) {
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }
}
