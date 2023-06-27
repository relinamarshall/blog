package com.blog.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Data;

/**
 * XxlJobRegistry
 *
 * @author Wenzhou
 * @since 2023/5/10 20:38
 */
@Data
public class XxlJobRegistry {
    private int id;

    private String registryGroup;

    private String registryKey;

    private String registryValue;

    private Date updateTime;
}
