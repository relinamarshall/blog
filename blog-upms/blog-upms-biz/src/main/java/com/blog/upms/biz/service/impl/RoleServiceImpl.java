package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.upms.biz.mapper.RoleMapper;
import com.blog.upms.biz.mapper.RoleMenuMapper;
import com.blog.upms.biz.service.RoleService;
import com.blog.upms.core.entity.Role;
import com.blog.upms.core.entity.RoleMenu;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * RoleServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:48
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuMapper sysRoleMenuMapper;

    /**
     * 通过角色ID，删除角色,并清空角色菜单缓存
     *
     * @param id Long
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public Boolean removeRoleById(String id) {
        sysRoleMenuMapper.delete(Wrappers.<RoleMenu>update().lambda().eq(RoleMenu::getRoleId, id));
        return this.removeById(id);
    }
}
