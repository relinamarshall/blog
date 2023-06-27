package com.blog.upms.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * RoleVo
 *
 * @author Wenzhou
 * @since 2023/5/12 11:25
 */
@Data
@Schema(description = "前端角色展示对象")
public class RoleVo {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 菜单列表
     */
    private String menuIds;

}
