package com.blog.upms.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RoleMenu
 * <p>
 * 角色菜单表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:46
 */
@Data
@TableName("t_role_menu")
@Schema(description = "角色菜单")
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends Model<RoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色id")
    private String roleId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单id")
    private String menuId;

}
