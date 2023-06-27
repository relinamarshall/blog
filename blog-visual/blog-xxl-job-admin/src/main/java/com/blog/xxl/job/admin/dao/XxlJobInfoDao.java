package com.blog.xxl.job.admin.dao;


import com.blog.xxl.job.admin.core.model.XxlJobInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * XxlJobInfoDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:35
 */
@Mapper
public interface XxlJobInfoDao {
    /**
     * pageList
     *
     * @param offset          int
     * @param pagesize        int
     * @param jobGroup        int
     * @param triggerStatus   int
     * @param jobDesc         String
     * @param executorHandler String
     * @param author          String
     * @return List<XxlJobInfo>
     */
    List<XxlJobInfo> pageList(@Param("offset") int offset,
                              @Param("pagesize") int pagesize,
                              @Param("jobGroup") int jobGroup,
                              @Param("triggerStatus") int triggerStatus,
                              @Param("jobDesc") String jobDesc,
                              @Param("executorHandler") String executorHandler,
                              @Param("author") String author);

    /**
     * pageListCount
     *
     * @param offset          int
     * @param pagesize        int
     * @param jobGroup        int
     * @param triggerStatus   int
     * @param jobDesc         String
     * @param executorHandler String
     * @param author          String
     * @return int
     */
    int pageListCount(@Param("offset") int offset, @Param("pagesize") int pagesize,
                      @Param("jobGroup") int jobGroup, @Param("triggerStatus") int triggerStatus,
                      @Param("jobDesc") String jobDesc, @Param("executorHandler") String executorHandler,
                      @Param("author") String author);

    /**
     * save
     *
     * @param info XxlJobInfo
     * @return int
     */
    int save(XxlJobInfo info);

    /**
     * loadById
     *
     * @param id int
     * @return XxlJobInfo
     */
    XxlJobInfo loadById(@Param("id") int id);

    /**
     * update
     *
     * @param xxlJobInfo XxlJobInfo
     * @return int
     */
    int update(XxlJobInfo xxlJobInfo);

    /**
     * delete
     *
     * @param id long
     * @return int
     */
    int delete(@Param("id") long id);

    /**
     * getJobsByGroup
     *
     * @param jobGroup int
     * @return List<XxlJobInfo>
     */
    List<XxlJobInfo> getJobsByGroup(@Param("jobGroup") int jobGroup);

    /**
     * findAllCount
     *
     * @return int
     */
    int findAllCount();

    /**
     * scheduleJobQuery
     *
     * @param maxNextTime long
     * @param pagesize    int
     * @return List<XxlJobInfo>
     */
    List<XxlJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime,
                                      @Param("pagesize") int pagesize);

    /**
     * scheduleUpdate
     *
     * @param xxlJobInfo XxlJobInfo
     * @return int
     */
    int scheduleUpdate(XxlJobInfo xxlJobInfo);
}
