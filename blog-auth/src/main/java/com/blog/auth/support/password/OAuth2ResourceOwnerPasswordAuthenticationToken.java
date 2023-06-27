package com.blog.auth.support.password;


import com.blog.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * OAuth2ResourceOwnerPasswordAuthenticationToken
 * <p>
 * 密码授权token信息
 *
 * @author Wenzhou
 * @since 2023/5/11 14:43
 */
public class OAuth2ResourceOwnerPasswordAuthenticationToken extends OAuth2ResourceOwnerBaseAuthenticationToken {

    public OAuth2ResourceOwnerPasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                          Authentication clientPrincipal,
                                                          Set<String> scopes,
                                                          Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }

}
