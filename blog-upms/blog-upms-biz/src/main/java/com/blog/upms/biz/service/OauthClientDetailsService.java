package com.blog.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.upms.core.entity.OauthClientDetails;

/**
 * OauthClientDetailsService
 * <p>
 * 客户端认证 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 通过ID删除客户端
     *
     * @param id String
     * @return Boolean
     */
    Boolean removeClientDetailsById(String id);

    /**
     * 修改客户端信息
     *
     * @param sysOauthClientDetails OauthClientDetails
     * @return Boolean
     */
    Boolean updateClientDetailsById(OauthClientDetails sysOauthClientDetails);

    /**
     * 清除客户端缓存
     */
    void clearClientCache();
}