package com.blog.upms.core.vo;

import lombok.Data;

/**
 * TokenVo
 * <p>
 * 前端展示令牌管理
 *
 * @author Wenzhou
 * @since 2023/5/11 14:04
 */
@Data
public class TokenVo {

    private String id;

    private Long userId;

    private String clientId;

    private String username;

    private String accessToken;

    private String issuedAt;

    private String expiresAt;

}

