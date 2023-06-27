package com.blog.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.sys.entity.Moment;

import java.util.List;

/**
 * MomentService
 *
 * @author Wenzhou
 * @since 2023/6/23 17:28
 */
public interface MomentService extends IService<Moment> {
    /**
     * 获取用户动态列表
     *
     * @return List<Moment>
     */
    List<Moment> getMomentListByUserId();
}
