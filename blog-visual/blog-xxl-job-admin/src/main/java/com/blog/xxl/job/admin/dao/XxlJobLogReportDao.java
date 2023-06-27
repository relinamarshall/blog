package com.blog.xxl.job.admin.dao;


import com.blog.xxl.job.admin.core.model.XxlJobLogReport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * XxlJobLogReportDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:35
 */
@Mapper
public interface XxlJobLogReportDao {
    /**
     * save
     *
     * @param xxlJobLogReport XxlJobLogReport
     * @return int
     */
    int save(XxlJobLogReport xxlJobLogReport);

    /**
     * update
     *
     * @param xxlJobLogReport XxlJobLogReport
     * @return int
     */
    int update(XxlJobLogReport xxlJobLogReport);

    /**
     * queryLogReport
     *
     * @param triggerDayFrom Date
     * @param triggerDayTo   Date
     * @return List<XxlJobLogReport>
     */
    List<XxlJobLogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                         @Param("triggerDayTo") Date triggerDayTo);

    /**
     * queryLogReportTotal
     *
     * @return XxlJobLogReport
     */
    XxlJobLogReport queryLogReportTotal();
}
