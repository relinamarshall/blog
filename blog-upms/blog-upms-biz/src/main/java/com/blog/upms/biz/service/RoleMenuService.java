package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.entity.RoleMenu;

/**
 * RoleMenuService
 * <p>
 * 角色菜单表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:39
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 更新角色菜单
     *
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return Boolean
     */
    Boolean saveRoleMenus(String roleId, String menuIds);

}
