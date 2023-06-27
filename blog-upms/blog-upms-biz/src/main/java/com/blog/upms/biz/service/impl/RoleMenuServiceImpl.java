package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.upms.biz.mapper.RoleMenuMapper;
import com.blog.upms.biz.service.RoleMenuService;
import com.blog.upms.core.entity.RoleMenu;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * RoleMenuServiceImpl
 * <p>
 * 角色菜单表 服务实现类
 *
 * @author Wenzhou
 * @since 2023/5/12 12:48
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    private final CacheManager cacheManager;

    /**
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveRoleMenus(String roleId, String menuIds) {
        this.remove(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getRoleId, roleId));

        if (StrUtil.isBlank(menuIds)) {
            return Boolean.TRUE;
        }
        List<RoleMenu> roleMenuList = Arrays.stream(menuIds.split(StrUtil.COMMA)).map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());

        // 清空userinfo
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_DETAILS)).clear();
        // 清空全部的菜单缓存 fix #I4BM58
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.MENU_DETAILS)).clear();
        return this.saveBatch(roleMenuList);
    }

}
