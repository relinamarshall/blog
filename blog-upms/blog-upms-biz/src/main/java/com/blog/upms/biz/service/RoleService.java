package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.entity.Role;


/**
 * RoleService
 * <p>
 * 角色表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:39
 */
public interface RoleService extends IService<Role> {

    /**
     * 通过角色ID，删除角色
     *
     * @param id Long
     * @return Boolean
     */
    Boolean removeRoleById(String id);
}
