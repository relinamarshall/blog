package com.blog.upms.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.core.constant.CacheConstants;
import com.blog.upms.biz.mapper.OauthClientDetailsMapper;
import com.blog.upms.biz.service.OauthClientDetailsService;
import com.blog.upms.core.entity.OauthClientDetails;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * OauthClientDetailsServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:47
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
        implements OauthClientDetailsService {

    /**
     * 通过ID删除客户端
     *
     * @param id String
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#id")
    public Boolean removeClientDetailsById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据客户端信息
     *
     * @param clientDetails OauthClientDetails
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
    public Boolean updateClientDetailsById(OauthClientDetails clientDetails) {
        return this.updateById(clientDetails);
    }

    /**
     * 清除客户端缓存
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
    public void clearClientCache() {

    }
}
