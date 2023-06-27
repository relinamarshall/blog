package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.common.core.constant.enums.DictTypeEnum;
import com.blog.common.core.exception.ErrorCodes;
import com.blog.common.core.util.MsgUtil;
import com.blog.upms.biz.mapper.DictItemMapper;
import com.blog.upms.biz.mapper.DictMapper;
import com.blog.upms.biz.service.DictService;
import com.blog.upms.core.entity.Dict;
import com.blog.upms.core.entity.DictItem;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

/**
 * DictServiceImpl
 * <p>
 * 字典表
 *
 * @author Wenzhou
 * @since 2023/5/12 12:42
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final DictItemMapper dictItemMapper;

    /**
     * 根据ID 删除字典
     *
     * @param id 字典ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public void removeDict(String id) {
        Dict dict = this.getById(id);
        // 系统内置
        Assert.state(!DictTypeEnum.SYS.getType().equals(dict.getSysCfgFlag()),
                MsgUtil.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
        baseMapper.deleteById(id);
        dictItemMapper.delete(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, id));
    }

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, key = "#dict.code")
    public void updateDict(Dict dict) {
        Dict sysDict = this.getById(dict.getId());
        // 系统内置
        Assert.state(!DictTypeEnum.SYS.getType().equals(sysDict.getSysCfgFlag()),
                MsgUtil.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
        this.updateById(dict);
    }

    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public void clearDictCache() {

    }
}

