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
 * Blog
 * <p>
 * 博客
 *
 * @author Wenzhou
 * @since 2023/6/19 18:15
 */
@Data
@TableName("b_blog")
@Schema(description = "博客")
@EqualsAndHashCode(callSuper = true)
public class Blog extends BaseEntity {
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
     * 分类编号
     */
    @Schema(description = "分类编号")
    private String categoryId;

    /**
     * 类型
     */
    @Schema(title = "类型")
    private String type;

    /**
     * 标题
     */
    @Schema(title = "标题")
    private String title;

    /**
     * 内容
     */
    @Schema(title = "内容")
    private String content;

    /**
     * 描述
     */
    @Schema(title = "描述")
    private String des;

    /**
     * 密码保护
     */
    @Schema(title = "密码保护")
    private String password;

    /**
     * 浏览次数
     */
    @Schema(title = "浏览次数")
    private Integer views;

    /**
     * 文章字数
     */
    @Schema(title = "文章字数")
    private Integer words;

    /**
     * 阅读时长(分钟)
     */
    @Schema(title = "阅读时长(分钟)")
    private Integer readTm;

    /**
     * 置顶标记
     */
    @Schema(title = "置顶标记")
    private String topFlag;

    /**
     * 公开类型
     */
    @Schema(title = "公开类型")
    private String pubType;

    /**
     * 删除标记 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;
}
