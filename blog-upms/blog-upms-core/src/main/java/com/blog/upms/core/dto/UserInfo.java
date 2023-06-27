package com.blog.upms.core.dto;


import com.blog.upms.core.entity.Role;
import com.blog.upms.core.entity.User;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * UserInfo
 *
 * @author Wenzhou
 * @since 2023/5/8 16:02
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 用户基本信息
     */
    private User user;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;

    /**
     * 角色集合
     */
    private List<Role> roleList;
}

