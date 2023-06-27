package com.blog.common.security.component;


import com.blog.common.security.config.PermitAllUrlProperties;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * DiyBearerTokenExtractor
 *
 * @author Wenzhou
 * @since 2023/5/8 16:33
 */
public class DiyBearerTokenExtractor implements BearerTokenResolver {

    private static final Pattern AUTHORIZATION_PATTERN =
            Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
    public static final String BEARER = "bearer";

    private static final boolean ALLOW_FORM_ENCODED_BODY_PARAMETER = false;

    private static final boolean ALLOW_URI_QUERY_PARAMETER = true;

    private static final String BEARER_TOKEN_HEADER_NAME = HttpHeaders.AUTHORIZATION;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final PermitAllUrlProperties urlProperties;

    public DiyBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
        this.urlProperties = urlProperties;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        boolean match = urlProperties.getUrls().stream()
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));

        if (match) {
            return null;
        }

        final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        final String parameterToken = isParameterTokenSupportedForRequest(request)
                ? resolveFromRequestParameters(request) : null;
        if (authorizationHeaderToken != null) {
            if (parameterToken != null) {
                final BearerTokenError error = BearerTokenErrors
                        .invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            return authorizationHeaderToken;
        }
        if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
            return parameterToken;
        }
        return null;
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(BEARER_TOKEN_HEADER_NAME);
        if (!StringUtils.startsWithIgnoreCase(authorization, BEARER)) {
            return null;
        }
        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }

    private static String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues("access_token");
        if (values == null || values.length == 0) {
            return null;
        }
        if (values.length == 1) {
            return values[0];
        }
        BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
        throw new OAuth2AuthenticationException(error);
    }

    private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
        return (("POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || "GET".equals(request.getMethod()));
    }

    private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
        return ((ALLOW_FORM_ENCODED_BODY_PARAMETER && "POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || (ALLOW_URI_QUERY_PARAMETER && "GET".equals(request.getMethod())));
    }

}

