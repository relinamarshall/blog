package com.blog.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Data;

/**
 * XxlJobLogGlue
 *
 * @author Wenzhou
 * @since 2023/5/10 20:38
 */
@Data
public class XxlJobLogGlue {
    private int id;

    /**
     * 任务主键ID
     */
    private int jobId;

    /**
     * GLUE类型 {@link com.xxl.job.core.glue.GlueTypeEnum}
     */
    private String glueType;

    private String glueSource;

    private String glueRemark;

    private Date addTime;

    private Date updateTime;
}
