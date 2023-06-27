package com.blog.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.upms.core.entity.OauthClientDetails;

import org.apache.ibatis.annotations.Mapper;

/**
 * OauthClientDetailsMapper
 * <p>
 * 系统认证表
 *
 * @author Wenzhou
 * @since 2023/5/12 10:08
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

}