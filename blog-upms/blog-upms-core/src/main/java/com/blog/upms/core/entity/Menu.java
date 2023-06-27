package com.blog.upms.core.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Menu
 * <p>
 * 菜单权限表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:36
 */
@Data
@TableName("t_menu")
@Schema(description = "菜单")
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "主键")
    private String id;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 菜单权限标识
     */
    @Schema(description = "菜单权限标识")
    private String permission;

    /**
     * 父菜单ID
     */
    @NotNull(message = "菜单父ID不能为空")
    @Schema(description = "菜单父id")
    private String parentId;

    /**
     * 图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 前端URL
     */
    @Schema(description = "前端路由标识路径")
    private String path;

    /**
     * 排序值
     */
    @Schema(description = "排序值")
    private Integer sortOrder;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "菜单类型")
    private String type;

    /**
     * 路由缓冲
     */
    @Schema(description = "路由缓冲")
    private String keepAlive;

    /**
     * 0--正常 1--删除
     */
    @TableLogic
    private String delFlag;
}
