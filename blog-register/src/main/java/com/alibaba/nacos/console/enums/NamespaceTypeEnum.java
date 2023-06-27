package com.alibaba.nacos.console.enums;

/**
 * NamespaceTypeEnum
 * <p>
 * the enum of namespace. 0 : Global configuration， 1 : Default private namespace ，2 :
 * Custom namespace.
 *
 * @author Wenzhou
 * @since 2023/5/10 13:44
 */
public enum NamespaceTypeEnum {
    /**
     * Global configuration.
     */
    GLOBAL(0, "Global configuration"),

    /**
     * Default private namespace.
     */
    PRIVATE(1, "Default private namespace"),

    /**
     * Custom namespace.
     */
    CUSTOM(2, "Custom namespace");

    /**
     * the namespace type.
     */
    private final int type;

    /**
     * the description.
     */
    private final String description;

    NamespaceTypeEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
