package com.blog.upms.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.constant.enums.DictTypeEnum;
import com.blog.common.core.exception.ErrorCodes;
import com.blog.common.core.util.MsgUtil;
import com.blog.upms.biz.mapper.DictItemMapper;
import com.blog.upms.biz.service.DictItemService;
import com.blog.upms.biz.service.DictService;
import com.blog.upms.core.entity.Dict;
import com.blog.upms.core.entity.DictItem;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

/**
 * DictItemServiceImpl
 * <p>
 * 字典项
 *
 * @author Wenzhou
 * @since 2023/5/12 11:38
 */
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    private final DictService dictService;

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public void removeDictItem(String id) {
        // 根据ID查询字典ID
        DictItem dictItem = this.getById(id);
        Dict dict = dictService.getById(dictItem.getDictId());
        // 系统内置
        Assert.state(!DictTypeEnum.SYS.getType().equals(dict.getSysCfgFlag()),
                MsgUtil.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
        this.removeById(id);
    }

    /**
     * 更新字典项
     *
     * @param item 字典项
     */
    @Override
    //todo
    @CacheEvict(value = CacheConstants.DICT_DETAILS, key = "#item.dictId")
    public void updateDictItem(DictItem item) {
        // 查询字典
        Dict dict = dictService.getById(item.getDictId());
        // 系统内置
        Assert.state(!DictTypeEnum.SYS.getType().equals(dict.getSysCfgFlag()),
                MsgUtil.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
        this.updateById(item);
    }
}
