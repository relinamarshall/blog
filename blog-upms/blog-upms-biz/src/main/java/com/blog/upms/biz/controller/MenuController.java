package com.blog.upms.biz.controller;

import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.common.security.util.SecurityUtils;
import com.blog.upms.biz.service.MenuService;
import com.blog.upms.core.entity.Menu;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * MenuController
 * <p>
 * 菜单管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Tag(name = "菜单管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MenuController {

    private final MenuService menuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @param parentId 父节点ID
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R<List<Tree<String>>> getUserMenu(String parentId) {
        // 获取符合条件的菜单
        Set<Menu> menuSet = SecurityUtils.getRoles()
                .stream()
                .map(menuService::findMenuByRoleId)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return R.ok(menuService.filterMenu(menuSet, parentId));
    }

    /**
     * 返回树形菜单集合
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R<List<Tree<String>>> getTree(boolean lazy, String parentId) {
        return R.ok(menuService.treeMenu(lazy, parentId));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @GetMapping("/tree/{roleId}")
    public R<List<String>> getRoleTree(@PathVariable String roleId) {
        return R.ok(menuService.findMenuByRoleId(roleId).stream().map(Menu::getId).collect(Collectors.toList()));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public R<Menu> getById(@PathVariable String id) {
        return R.ok(menuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return 含ID 菜单信息
     */
    @Log("新增菜单")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_add')")
    public R<Menu> save(@Valid @RequestBody Menu sysMenu) {
        menuService.save(sysMenu);
        return R.ok(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @Log("删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R<Boolean> removeById(@PathVariable String id) {
        return R.ok(menuService.removeMenuById(id));
    }

    /**
     * 更新菜单
     *
     * @param sysMenu Menu
     * @return Boolean
     */
    @Log("更新菜单")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_edit')")
    public R<Boolean> update(@Valid @RequestBody Menu sysMenu) {
        return R.ok(menuService.updateMenuById(sysMenu));
    }

    /**
     * 清除菜单缓存
     */
    @Log("清除菜单缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R clearMenuCache() {
        menuService.clearMenuCache();
        return R.ok();
    }
}