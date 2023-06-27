package com.blog.upms.core.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Role
 * <p>
 * 角色表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:46
 */
@Data
@TableName("t_role")
@Schema(description = "角色")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "角色编号")
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称,不能为空")
    @Schema(description = "角色名称")
    private String name;


    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识,不能为空")
    @Schema(description = "角色标识")
    private String code;

    /**
     * 角色描述
     */
    @NotBlank(message = "角色描述,不能为空")
    @Schema(description = "角色描述")
    private String des;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @TableLogic
    private String delFlag;
}

