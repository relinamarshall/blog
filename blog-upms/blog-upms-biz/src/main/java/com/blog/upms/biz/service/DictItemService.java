package com.blog.upms.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.entity.DictItem;

/**
 * DictItemService
 * <p>
 * 字典项
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    void removeDictItem(String id);

    /**
     * 更新字典项
     *
     * @param item 字典项
     */
    void updateDictItem(DictItem item);
}
