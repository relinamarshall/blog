package com.blog.xxl.job.admin.dao;


import com.blog.xxl.job.admin.core.model.XxlJobUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * XxlJobUserDao
 *
 * @author Wenzhou
 * @since 2023/5/10 20:36
 */
@Mapper
public interface XxlJobUserDao {
    /**
     * pageList
     *
     * @param offset   int
     * @param pagesize int
     * @param username String
     * @param role     int
     * @return List<XxlJobUser>
     */
    List<XxlJobUser> pageList(@Param("offset") int offset,
                              @Param("pagesize") int pagesize,
                              @Param("username") String username,
                              @Param("role") int role);

    /**
     * pageListCount
     *
     * @param offset   int
     * @param pagesize int
     * @param username String
     * @param role     int
     * @return int
     */
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("username") String username,
                      @Param("role") int role);

    /**
     * loadByUserName
     *
     * @param username String
     * @return XxlJobUser
     */
    XxlJobUser loadByUserName(@Param("username") String username);

    /**
     * save
     *
     * @param xxlJobUser XxlJobUser
     * @return int
     */
    int save(XxlJobUser xxlJobUser);

    /**
     * update
     *
     * @param xxlJobUser XxlJobUser
     * @return int
     */
    int update(XxlJobUser xxlJobUser);

    /**
     * delete
     *
     * @param id int
     * @return int
     */
    int delete(@Param("id") int id);

}
