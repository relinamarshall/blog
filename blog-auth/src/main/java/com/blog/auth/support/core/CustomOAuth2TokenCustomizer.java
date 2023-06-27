package com.blog.auth.support.core;


import com.blog.common.core.constant.SecurityConstants;
import com.blog.common.security.pojo.ExtUser;

import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * CustomOAuth2TokenCustomizer
 * <p>
 * token 输出增强
 *
 * @author Wenzhou
 * @since 2023/5/11 14:17
 */
public class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

    /**
     * Customize the OAuth 2.0 Token attributes.
     *
     * @param context the context containing the OAuth 2.0 Token attributes
     */
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();
        claims.claim(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PROJECT_LICENSE);
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim(SecurityConstants.CLIENT_ID, clientId);
        // 客户端模式不返回具体用户信息
        if (SecurityConstants.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
            return;
        }

        ExtUser user = (ExtUser) context.getPrincipal().getPrincipal();
        claims.claim(SecurityConstants.DETAILS_USER, user);
    }
}