package com.blog.upms.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.core.mybatis.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Article
 *
 * @author Wenzhou
 * @since 2023/6/19 18:15
 */
@Data
@TableName("t_article")
@Schema(description = "文章")
@EqualsAndHashCode(callSuper = true)
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "字典主键")
    private String id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String type;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String stat;

    /**
     * 置顶标记
     */
    @Schema(description = "置顶标记")
    private String topFlag;

    /**
     * 删除标记
     */
    @TableLogic
    private String delFlag;
}
