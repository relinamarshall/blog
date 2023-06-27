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
 * Moment
 * <p>
 * 动态
 *
 * @author Wenzhou
 * @since 2023/6/23 13:07
 */
@Data
@TableName("b_moment")
@Schema(description = "动态")
@EqualsAndHashCode(callSuper = true)
public class Moment extends BaseEntity {
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
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 点赞数量
     */
    @Schema(description = "点赞数量")
    private Integer likes;

    /**
     * 公开或私密
     */
    @Schema(title = "公开或私密")
    private String pubFlag;

    /**
     * 删除标记 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;
}
