package com.blog.xxl.job.admin.dao;


import com.blog.xxl.job.admin.core.model.XxlJobGroup;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * XxlJobGroupDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:33
 */
@Mapper
public interface XxlJobGroupDao {
    /**
     * findAll
     *
     * @return List<XxlJobGroup>
     */
    List<XxlJobGroup> findAll();

    /**
     * findByAddressType
     *
     * @param addressType int
     * @return List<XxlJobGroup>
     */
    List<XxlJobGroup> findByAddressType(@Param("addressType") int addressType);

    /**
     * save
     *
     * @param xxlJobGroup {@link XxlJobGroup}
     * @return int
     */
    int save(XxlJobGroup xxlJobGroup);

    /**
     * update
     *
     * @param xxlJobGroup {@link XxlJobGroup}
     * @return int
     */
    int update(XxlJobGroup xxlJobGroup);

    /**
     * remove
     *
     * @param id int
     * @return int
     */
    int remove(@Param("id") int id);

    /**
     * load
     *
     * @param id int
     * @return XxlJobGroup {@link XxlJobGroup}
     */
    XxlJobGroup load(@Param("id") int id);

    /**
     * pageList
     *
     * @param offset   int
     * @param pagesize int
     * @param appname  String
     * @param title    String
     * @return List<XxlJobGroup>
     */
    List<XxlJobGroup> pageList(@Param("offset") int offset,
                               @Param("pagesize") int pagesize,
                               @Param("appname") String appname,
                               @Param("title") String title);

    /**
     * pageListCount
     *
     * @param offset   int
     * @param pagesize int
     * @param appname  String
     * @param title    String
     * @return int
     */
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("appname") String appname,
                      @Param("title") String title);
}
