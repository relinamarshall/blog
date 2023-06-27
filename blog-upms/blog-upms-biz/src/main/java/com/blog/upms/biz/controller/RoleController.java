package com.blog.upms.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.core.util.R;
import com.blog.common.log.annotation.Log;
import com.blog.upms.biz.service.RoleMenuService;
import com.blog.upms.biz.service.RoleService;
import com.blog.upms.core.entity.Role;
import com.blog.upms.core.vo.RoleVo;

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

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * RoleController
 * <p>
 * 角色管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Tag(name = "角色管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class RoleController {

    private final RoleService roleService;

    private final RoleMenuService roleMenuService;

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public R<Role> getById(@PathVariable String id) {
        return R.ok(roleService.getById(id));
    }

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     * @return success、false
     */
    @Log("添加角色")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_role_add')")
    public R<Boolean> save(@Valid @RequestBody Role sysRole) {
        return R.ok(roleService.save(sysRole));
    }

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     * @return success/false
     */
    @Log("修改角色")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_role_edit')")
    public R<Boolean> update(@Valid @RequestBody Role sysRole) {
        return R.ok(roleService.updateById(sysRole));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Log("删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_role_del')")
    public R<Boolean> removeById(@PathVariable String id) {
        return R.ok(roleService.removeRoleById(id));
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    public R<List<Role>> listRoles() {
        return R.ok(roleService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<Role>> getRolePage(Page<Role> page) {
        return R.ok(roleService.page(page, Wrappers.<Role>lambdaQuery().orderByDesc(Role::getCrtTm)));
    }

    /**
     * 更新角色菜单
     *
     * @param roleVo 角色对象
     * @return success、false
     */
    @Log("更新角色菜单")
    @PutMapping("/menu")
    @PreAuthorize("@pms.hasPermission('sys_role_perm')")
    public R<Boolean> saveRoleMenus(@RequestBody RoleVo roleVo) {
        return R.ok(roleMenuService.saveRoleMenus(roleVo.getRoleId(), roleVo.getMenuIds()));
    }
}
