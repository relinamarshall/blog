package com.blog.upms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.upms.biz.mapper.UserRoleMapper;
import com.blog.upms.biz.service.UserRoleService;
import com.blog.upms.core.entity.UserRole;

import org.springframework.stereotype.Service;

/**
 * UserRoleServiceImpl
 * <p>
 * 用户角色表 服务实现类
 *
 * @author Wenzhou
 * @since 2023/5/12 12:48
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
