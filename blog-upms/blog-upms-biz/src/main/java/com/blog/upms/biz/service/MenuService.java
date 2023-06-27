package com.blog.upms.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.entity.Menu;

import java.util.List;
import java.util.Set;

import cn.hutool.core.lang.tree.Tree;

/**
 * MenuService
 * <p>
 * 菜单权限表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过角色编号查询URL 权限
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    Set<Menu> findMenuByRoleId(String roleId);

    /**
     * 级联删除菜单
     *
     * @param id 菜单ID
     * @return true成功, false失败
     */
    Boolean removeMenuById(String id);

    /**
     * 更新菜单信息
     *
     * @param sysMenu 菜单信息
     * @return 成功、失败
     */
    Boolean updateMenuById(Menu sysMenu);

    /**
     * 构建树
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return List<Tree < String>>
     */
    List<Tree<String>> treeMenu(boolean lazy, String parentId);

    /**
     * 查询菜单
     *
     * @param menuSet  Set<Menu>
     * @param parentId String
     * @return List<Tree < String>>
     */
    List<Tree<String>> filterMenu(Set<Menu> menuSet, String parentId);

    /**
     * 清除菜单缓存
     */
    void clearMenuCache();
}

