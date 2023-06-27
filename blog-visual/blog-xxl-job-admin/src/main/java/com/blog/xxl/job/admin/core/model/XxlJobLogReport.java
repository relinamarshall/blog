package com.blog.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Data;

/**
 * XxlJobLogReport
 *
 * @author Wenzhou
 * @since 2023/5/10 20:38
 */
@Data
public class XxlJobLogReport {
    private int id;

    private Date triggerDay;

    private int runningCount;

    private int sucCount;

    private int failCount;
}
