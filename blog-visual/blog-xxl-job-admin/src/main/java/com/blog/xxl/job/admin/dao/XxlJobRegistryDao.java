package com.blog.xxl.job.admin.dao;

import com.blog.xxl.job.admin.core.model.XxlJobRegistry;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * XxlJobRegistryDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:36
 */
@Mapper
public interface XxlJobRegistryDao {
    /**
     * findDead
     *
     * @param timeout int
     * @param nowTime Date
     * @return List<Integer>
     */
    List<Integer> findDead(@Param("timeout") int timeout, @Param("nowTime") Date nowTime);

    /**
     * removeDead
     *
     * @param ids List<Integer>
     * @return int
     */
    int removeDead(@Param("ids") List<Integer> ids);

    /**
     * findAll
     *
     * @param timeout int
     * @param nowTime Date
     * @return List<XxlJobRegistry>
     */
    List<XxlJobRegistry> findAll(@Param("timeout") int timeout, @Param("nowTime") Date nowTime);

    /**
     * registryUpdate
     *
     * @param registryGroup String
     * @param registryKey   String
     * @param registryValue String
     * @param updateTime    Date
     * @return int
     */
    int registryUpdate(@Param("registryGroup") String registryGroup,
                       @Param("registryKey") String registryKey,
                       @Param("registryValue") String registryValue,
                       @Param("updateTime") Date updateTime);

    /**
     * registrySave
     *
     * @param registryGroup String
     * @param registryKey   String
     * @param registryValue String
     * @param updateTime    Date
     * @return int
     */
    int registrySave(@Param("registryGroup") String registryGroup,
                     @Param("registryKey") String registryKey,
                     @Param("registryValue") String registryValue,
                     @Param("updateTime") Date updateTime);

    /**
     * registryDelete
     *
     * @param registryGroup String
     * @param registryKey   String
     * @param registryValue String
     * @return int
     */
    int registryDelete(@Param("registryGroup") String registryGroup,
                       @Param("registryKey") String registryKey,
                       @Param("registryValue") String registryValue);

}
