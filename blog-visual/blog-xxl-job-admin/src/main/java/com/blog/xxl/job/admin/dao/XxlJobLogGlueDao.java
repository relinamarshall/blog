package com.blog.xxl.job.admin.dao;


import com.blog.xxl.job.admin.core.model.XxlJobLogGlue;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * XxlJobLogGlueDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:35
 */
@Mapper
public interface XxlJobLogGlueDao {

    /**
     * save
     *
     * @param xxlJobLogGlue XxlJobLogGlue
     * @return int
     */
    int save(XxlJobLogGlue xxlJobLogGlue);

    /**
     * findByJobId
     *
     * @param jobId int
     * @return List<XxlJobLogGlue>
     */
    List<XxlJobLogGlue> findByJobId(@Param("jobId") int jobId);

    /**
     * removeOld
     *
     * @param jobId int
     * @param limit int
     * @return int
     */
    int removeOld(@Param("jobId") int jobId, @Param("limit") int limit);

    /**
     * deleteByJobId
     *
     * @param jobId int
     * @return int
     */
    int deleteByJobId(@Param("jobId") int jobId);
}
