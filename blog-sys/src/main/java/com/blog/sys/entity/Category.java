package com.blog.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Category
 * <p>
 * 分类
 *
 * @author Wenzhou
 * @since 2023/6/23 13:07
 */
@Data
@TableName("b_category")
@Schema(description = "分类")
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "主键")
    private String id;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private String userId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 删除标记 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;
}
