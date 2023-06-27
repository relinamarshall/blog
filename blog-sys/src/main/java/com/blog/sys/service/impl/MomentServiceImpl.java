package com.blog.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.security.util.SecurityUtils;
import com.blog.sys.entity.Moment;
import com.blog.sys.mapper.MomentMapper;
import com.blog.sys.service.MomentService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * MomentServiceImpl
 *
 * @author Wenzhou
 * @since 2023/6/23 17:29
 */
@Service
@RequiredArgsConstructor
public class MomentServiceImpl extends ServiceImpl<MomentMapper, Moment> implements MomentService {
    /**
     * momentMapper
     */
    private final MomentMapper momentMapper;

    @Override
    public List<Moment> getMomentListByUserId() {
        String userId = SecurityUtils.getUser().getId();
        return momentMapper.selectList(Wrappers.<Moment>lambdaQuery()
                .eq(StringUtils.isNotBlank(userId), Moment::getUserId, userId)
                .orderByDesc(Moment::getCrtTm));
    }
}
