package com.blog.xxl.job.admin.core.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import lombok.experimental.UtilityClass;

/**
 * JacksonUtil
 * <p>
 * 1、obj need private and set/get； 2、do not support inner class；
 *
 * @author Wenzhou
 * @since 2023/5/10 21:18
 */
@UtilityClass
public class JacksonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return OBJECT_MAPPER;
    }

    /**
     * bean、array、List、Map --> json
     *
     * @param obj Object
     * @return json string
     */
    public static String writeValueAsString(Object obj) {
        try {
            return getInstance().writeValueAsString(obj);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * string --> bean、Map、List(array)
     *
     * @param jsonStr String
     * @param clazz   Class
     * @return obj
     */
    public static <T> T readValue(String jsonStr, Class<T> clazz) {
        try {
            return getInstance().readValue(jsonStr, clazz);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * string --> List<Bean>...
     *
     * @param jsonStr          String
     * @param parametrized     Class
     * @param parameterClasses Class
     * @param <T>              ?
     * @return T
     */
    public static <T> T readValue(String jsonStr, Class<?> parametrized, Class<?>... parameterClasses) {
        try {
            JavaType javaType = getInstance().getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return getInstance().readValue(jsonStr, javaType);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

}
