package com.blog.xxl.job.admin.core.alarm;


import com.blog.xxl.job.admin.core.model.XxlJobInfo;
import com.blog.xxl.job.admin.core.model.XxlJobLog;

/**
 * JobAlarm
 *
 * @author Wenzhou
 * @since 2023/5/10 21:27
 */
public interface JobAlarm {
    /**
     * job alarm
     *
     * @param info   XxlJobInfo
     * @param jobLog XxlJobInfo
     * @return boolean
     */
    boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);
}
