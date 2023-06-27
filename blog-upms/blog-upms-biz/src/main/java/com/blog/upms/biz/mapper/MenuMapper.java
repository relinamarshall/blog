package com.blog.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.upms.core.entity.Menu;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * MenuMapper
 * <p>
 * 菜单权限表 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:08
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return Set<Menu>
     */
    Set<Menu> listMenusByRoleId(String roleId);
}