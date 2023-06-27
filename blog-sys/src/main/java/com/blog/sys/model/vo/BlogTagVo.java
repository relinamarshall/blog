package com.blog.sys.model.vo;

import com.blog.sys.entity.Blog;
import com.blog.sys.entity.Tag;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BlogTagVo
 *
 * @author Wenzhou
 * @since 2023/6/25 9:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogTagVo extends Blog {
    /**
     * 标签列表
     */
    private List<Tag> tagList;
    /**
     * 标签Id列表
     */
    private List<String> tagIdsList;
}
