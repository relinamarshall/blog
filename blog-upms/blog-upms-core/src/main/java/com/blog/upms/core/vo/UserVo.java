package com.blog.upms.core.vo;

import com.blog.upms.core.entity.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * UserVo
 *
 * @author Wenzhou
 * @since 2023/5/12 10:11
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 创建时间
     */
    private LocalDateTime crtTm;

    /**
     * 修改时间
     */
    private LocalDateTime updTm;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 锁定标记
     */
    private String lockFlag;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色列表
     */
    private List<Role> roleList;
}