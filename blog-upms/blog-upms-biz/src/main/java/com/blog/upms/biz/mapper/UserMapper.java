package com.blog.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.upms.core.dto.UserDto;
import com.blog.upms.core.entity.User;
import com.blog.upms.core.vo.UserVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper
 * <p>
 * 用户表 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVo getUserVoByUsername(String username);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param page    分页
     * @param userDTO 查询参数
     * @return list
     */
    IPage<UserVo> getUserVosPage(Page<UserVo> page, @Param("query") UserDto userDTO);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return userVo
     */
    UserVo getUserVoById(String id);
}

