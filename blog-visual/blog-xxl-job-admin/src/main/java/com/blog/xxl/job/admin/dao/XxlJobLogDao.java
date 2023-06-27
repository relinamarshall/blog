package com.blog.xxl.job.admin.dao;


import com.blog.xxl.job.admin.core.model.XxlJobLog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * XxlJobLogDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:35
 */
@Mapper
public interface XxlJobLogDao {
    /**
     * pageList
     *
     * @param offset           int
     * @param pagesize         int
     * @param jobGroup         int
     * @param jobId            int
     * @param triggerTimeStart Date
     * @param triggerTimeEnd   Date
     * @param logStatus        int
     * @return List<XxlJobLog>
     */
    List<XxlJobLog> pageList(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("jobGroup") int jobGroup,
                             @Param("jobId") int jobId,
                             @Param("triggerTimeStart") Date triggerTimeStart,
                             @Param("triggerTimeEnd") Date triggerTimeEnd,
                             @Param("logStatus") int logStatus);

    /**
     * pageListCount
     *
     * @param offset           int
     * @param pagesize         int
     * @param jobGroup         int
     * @param jobId            int
     * @param triggerTimeStart Date
     * @param triggerTimeEnd   Date
     * @param logStatus        int
     * @return int
     */
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("jobGroup") int jobGroup,
                      @Param("jobId") int jobId,
                      @Param("triggerTimeStart") Date triggerTimeStart,
                      @Param("triggerTimeEnd") Date triggerTimeEnd,
                      @Param("logStatus") int logStatus);

    /**
     * load
     *
     * @param id long
     * @return XxlJobLog
     */
    XxlJobLog load(@Param("id") long id);

    /**
     * save
     *
     * @param xxlJobLog XxlJobLog
     * @return long
     */
    long save(XxlJobLog xxlJobLog);

    /**
     * updateTriggerInfo
     *
     * @param xxlJobLog XxlJobLog
     * @return int
     */
    int updateTriggerInfo(XxlJobLog xxlJobLog);

    /**
     * updateHandleInfo
     *
     * @param xxlJobLog XxlJobLog
     * @return int
     */
    int updateHandleInfo(XxlJobLog xxlJobLog);

    /**
     * delete
     *
     * @param jobId int
     * @return int
     */
    int delete(@Param("jobId") int jobId);

    /**
     * findLogReport
     *
     * @param from Date
     * @param to   Date
     * @return Map<String, Object>
     */
    Map<String, Object> findLogReport(@Param("from") Date from, @Param("to") Date to);

    /**
     * findClearLogIds
     *
     * @param jobGroup        int
     * @param jobId           int
     * @param clearBeforeTime Date
     * @param clearBeforeNum  int
     * @param pagesize        int
     * @return List<Long>
     */
    List<Long> findClearLogIds(@Param("jobGroup") int jobGroup,
                               @Param("jobId") int jobId,
                               @Param("clearBeforeTime") Date clearBeforeTime,
                               @Param("clearBeforeNum") int clearBeforeNum,
                               @Param("pagesize") int pagesize);

    /**
     * clearLog
     *
     * @param logIds List<Long>
     * @return int
     */
    int clearLog(@Param("logIds") List<Long> logIds);

    /**
     * findFailJobLogIds
     *
     * @param pagesize int
     * @return List<Long>
     */
    List<Long> findFailJobLogIds(@Param("pagesize") int pagesize);

    /**
     * updateAlarmStatus
     *
     * @param logId          long
     * @param oldAlarmStatus int
     * @param newAlarmStatus int
     * @return int
     */
    int updateAlarmStatus(@Param("logId") long logId,
                          @Param("oldAlarmStatus") int oldAlarmStatus,
                          @Param("newAlarmStatus") int newAlarmStatus);

    /**
     * findLostJobIds
     *
     * @param losedTime Date
     * @return List<Long>
     */
    List<Long> findLostJobIds(@Param("losedTime") Date losedTime);
}
