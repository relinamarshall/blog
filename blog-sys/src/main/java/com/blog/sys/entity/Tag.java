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
 * Tag
 * <p>
 * 标签
 *
 * @author Wenzhou
 * @since 2023/6/19 18:15
 */
@Data
@TableName("b_tag")
@Schema(description = "标签")
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {
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
    @Schema(title = "名称")
    private String name;

    /**
     * 颜色
     */
    @Schema(title = "颜色")
    private String color;

    /**
     * 删除标记 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;
}
