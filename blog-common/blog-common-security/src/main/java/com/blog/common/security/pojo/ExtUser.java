package com.blog.common.security.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * ExtUser
 *
 * @author Wenzhou
 * @since 2023/5/8 16:27
 */
@EqualsAndHashCode(callSuper = true)
public class ExtUser extends User implements OAuth2AuthenticatedPrincipal {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 用户ID
     */
    @Getter
    @JsonSerialize(using = ToStringSerializer.class)
    private final String id;

    public ExtUser(String id, String username, String password, boolean enabled,
                   boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    /**
     * Get the OAuth 2.0 token attributes
     *
     * @return the OAuth 2.0 token attributes
     */
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

}

