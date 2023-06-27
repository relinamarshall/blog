package com.blog.common.xss.exception;

/**
 * XssException
 * <p>
 * xss 异常，校验模式抛出
 *
 * @author Wenzhou
 * @since 2023/5/12 9:32
 */
public interface XssException {
    /**
     * 输入的数据
     *
     * @return 数据
     */
    String getInput();

    /**
     * 获取异常的消息
     *
     * @return 消息
     */
    String getMessage();
}
