package com.blog.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BlogTag
 *
 * @author Wenzhou
 * @since 2023/6/25 20:40
 */
@Data
@TableName("b_blog_tag")
@Schema(description = "博客标签")
@EqualsAndHashCode(callSuper = true)
public class BlogTag extends Model<BlogTag> {
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    private String blogId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private String tagId;
}
