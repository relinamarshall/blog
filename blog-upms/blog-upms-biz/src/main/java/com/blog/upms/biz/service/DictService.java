package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.entity.Dict;

/**
 * DictService
 * <p>
 * 字典表
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据ID 删除字典
     *
     * @param id Long
     */
    void removeDict(String id);

    /**
     * 更新字典
     *
     * @param sysDict 字典
     */
    void updateDict(Dict dict);

    /**
     * 清除缓存
     */
    void clearDictCache();
}

