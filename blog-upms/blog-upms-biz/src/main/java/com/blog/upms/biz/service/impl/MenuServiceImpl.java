package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.constant.CommonConstants;
import com.blog.common.core.constant.enums.MenuTypeEnum;
import com.blog.common.core.exception.ErrorCodes;
import com.blog.common.core.util.MsgUtil;
import com.blog.upms.biz.mapper.MenuMapper;
import com.blog.upms.biz.mapper.RoleMenuMapper;
import com.blog.upms.biz.service.MenuService;
import com.blog.upms.core.entity.Menu;
import com.blog.upms.core.entity.RoleMenu;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * MenuServiceImpl
 * <p>
 * 菜单权限表 服务实现类
 *
 * @author Wenzhou
 * @since 2023/5/12 12:47
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuMapper sysRoleMenuMapper;

    @Override
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")
    public Set<Menu> findMenuByRoleId(String roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    /**
     * 级联删除菜单
     *
     * @param id 菜单ID
     * @return true成功, false失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public Boolean removeMenuById(String id) {
        // 查询父节点为当前节点的节点
        List<Menu> menuList = this.list(Wrappers.<Menu>query().lambda().eq(Menu::getParentId, id));

        Assert.isTrue(CollUtil.isEmpty(menuList), MsgUtil.getMessage(ErrorCodes.SYS_MENU_DELETE_EXISTING));

        sysRoleMenuMapper.delete(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getMenuId, id));
        // 删除当前菜单及其子菜单
        return this.removeById(id);
    }

    @Override
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public Boolean updateMenuById(Menu sysMenu) {
        return this.updateById(sysMenu);
    }

    /**
     * 构建树查询 1. 不是懒加载情况，查询全部 2. 是懒加载，根据parentId 查询 2.1 父节点为空，则查询ID -1
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return List<Tree < Long>>
     */
    @Override
    public List<Tree<String>> treeMenu(boolean lazy, String parentId) {
        if (!lazy) {
            List<TreeNode<String>> collect = baseMapper
                    .selectList(Wrappers.<Menu>lambdaQuery().orderByAsc(Menu::getSortOrder))
                    .stream()
                    .map(getNodeFunction())
                    .collect(Collectors.toList());

            return TreeUtil.build(collect, CommonConstants.MENU_TREE_ROOT_ID);
        }

        String parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;

        List<TreeNode<String>> collect = baseMapper
                .selectList(Wrappers
                        .<Menu>lambdaQuery().eq(Menu::getParentId, parent).orderByAsc(Menu::getSortOrder))
                .stream()
                .map(getNodeFunction())
                .collect(Collectors.toList());

        return TreeUtil.build(collect, parent);
    }

    /**
     * 查询菜单
     *
     * @param all      全部菜单
     * @param parentId 父节点ID
     * @return List<Tree < Long>>
     */
    @Override
    public List<Tree<String>> filterMenu(Set<Menu> all, String parentId) {
        List<TreeNode<String>> collect = all.stream()
                .filter(menu -> MenuTypeEnum.LEFT_MENU.getType().equals(menu.getType()))
                .filter(menu -> StrUtil.isNotBlank(menu.getPath()))
                .map(getNodeFunction())
                .collect(Collectors.toList());
        String parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;
        return TreeUtil.build(collect, parent);
    }

    @Override
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public void clearMenuCache() {

    }

    @NotNull
    private Function<Menu, TreeNode<String>> getNodeFunction() {
        return menu -> {
            TreeNode<String> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSortOrder());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("icon", menu.getIcon());
            extra.put("path", menu.getPath());
            extra.put("type", menu.getType());
            extra.put("permission", menu.getPermission());
            extra.put("label", menu.getName());
            extra.put("sortOrder", menu.getSortOrder());
            extra.put("keepAlive", menu.getKeepAlive());
            node.setExtra(extra);
            return node;
        };
    }

}

