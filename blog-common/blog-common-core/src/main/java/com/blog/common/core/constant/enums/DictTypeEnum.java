package com.blog.common.core.constant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DictTypeEnum
 * <p>
 * 字典类型
 *
 * @author Wenzhou
 * @since 2023/6/19 16:10
 */
@Getter
@RequiredArgsConstructor
public enum DictTypeEnum {
    /**
     * 字典类型-系统内置（不可修改）
     */
    SYS("1", "系统内置"),

    /**
     * 字典类型-业务类型
     */
    BIZ("0", "业务类");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String desc;
}
