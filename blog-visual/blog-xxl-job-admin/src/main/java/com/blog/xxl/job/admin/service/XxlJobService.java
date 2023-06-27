package com.blog.xxl.job.admin.service;

import com.blog.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;

import java.util.Date;
import java.util.Map;

/**
 * XxlJobService
 *
 * @author Wenzhou
 * @since 2023/5/10 22:08
 */
public interface XxlJobService {
    /**
     * page list
     *
     * @param start           int
     * @param length          int
     * @param jobGroup        int
     * @param triggerStatus   int
     * @param jobDesc         String
     * @param executorHandler String
     * @param author          String
     * @return Map
     */
    Map<String, Object> pageList(int start,
                                 int length,
                                 int jobGroup,
                                 int triggerStatus,
                                 String jobDesc,
                                 String executorHandler,
                                 String author);

    /**
     * add job
     *
     * @param jobInfo XxlJobInfo
     * @return ReturnT
     */
    ReturnT<String> add(XxlJobInfo jobInfo);

    /**
     * update job
     *
     * @param jobInfo XxlJobInfo
     * @return ReturnT
     */
    ReturnT<String> update(XxlJobInfo jobInfo);

    /**
     * remove job *
     *
     * @param id int
     * @return ReturnT
     */
    ReturnT<String> remove(int id);

    /**
     * start job
     *
     * @param id int
     * @return ReturnT
     */
    ReturnT<String> start(int id);

    /**
     * stop job
     *
     * @param id int
     * @return ReturnT
     */
    ReturnT<String> stop(int id);

    /**
     * dashboard info
     *
     * @return Map
     */
    Map<String, Object> dashboardInfo();

    /**
     * chart info
     *
     * @param startDate Date
     * @param endDate   Date
     * @return ReturnT
     */
    ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate);

}
