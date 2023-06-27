package com.blog.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.upms.core.entity.Role;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * RoleMapper
 * <p>
 * 角色表
 *
 * @author Wenzhou
 * @since 2023/5/12 10:09
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId Long
     * @return List<Role>
     */
    List<Role> listRolesByUserId(String userId);
}
