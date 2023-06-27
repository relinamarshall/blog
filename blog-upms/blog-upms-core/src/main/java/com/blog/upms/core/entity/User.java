package com.blog.upms.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User
 *
 * @author Wenzhou
 * @since 2023/6/19 18:16
 */
@Data
@TableName("t_user")
@Schema(description = "日志")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "主键")
    private String id;

    /**
     * 用户名
     */
    @Schema(title = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 随机盐
     */
    @JsonIgnore
    @Schema(description = "随机盐")
    private String salt;

    /**
     * 头像
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 锁定标记
     */
    @Schema(description = "锁定标记")
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;
}
