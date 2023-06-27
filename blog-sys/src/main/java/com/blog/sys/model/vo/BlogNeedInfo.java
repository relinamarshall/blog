package com.blog.sys.model.vo;

import com.blog.sys.entity.Category;
import com.blog.sys.entity.Tag;

import java.util.List;

import lombok.Data;

/**
 * BlogNeedInfo
 *
 * @author Wenzhou
 * @since 2023/6/26 21:24
 */
@Data
public class BlogNeedInfo {
    /**
     * 分类
     */
    private List<Category> categoryList;

    /**
     * 标签
     */
    private List<Tag> tagList;
}
