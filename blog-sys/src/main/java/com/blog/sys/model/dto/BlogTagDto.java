package com.blog.sys.model.dto;

import com.blog.sys.entity.Blog;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BlogTagDto
 *
 * @author Wenzhou
 * @since 2023/6/25 20:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogTagDto extends Blog {
    /**
     * 标签列表
     */
    private List<String> tagList;
}
