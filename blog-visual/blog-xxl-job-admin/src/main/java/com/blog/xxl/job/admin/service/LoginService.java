package com.blog.xxl.job.admin.service;

import com.blog.xxl.job.admin.core.model.XxlJobUser;
import com.blog.xxl.job.admin.core.util.CookieUtil;
import com.blog.xxl.job.admin.core.util.I18nUtil;
import com.blog.xxl.job.admin.core.util.JacksonUtil;
import com.blog.xxl.job.admin.dao.XxlJobUserDao;
import com.xxl.job.core.biz.model.ReturnT;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * deleteByJobId
 *
 * @author Wenzhou
 * @since 2023/5/10 21:17
 */
@Configuration
public class LoginService {
    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    @Resource
    private XxlJobUserDao xxlJobUserDao;

    /**
     * makeToken
     *
     * @param xxlJobUser XxlJobUser
     * @return String
     */
    private String makeToken(XxlJobUser xxlJobUser) {
        String tokenJson = JacksonUtil.writeValueAsString(xxlJobUser);
        return new BigInteger(Objects.requireNonNull(tokenJson).getBytes()).toString(16);
    }

    private XxlJobUser parseToken(String tokenHex) {
        XxlJobUser xxlJobUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray()); // username_password(md5)
            xxlJobUser = JacksonUtil.readValue(tokenJson, XxlJobUser.class);
        }
        return xxlJobUser;
    }

    /**
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param username   String
     * @param password   String
     * @param ifRemember boolean
     * @return ReturnT<String>
     */
    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username,
                                 String password, boolean ifRemember) {
        // param
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return new ReturnT<>(500, I18nUtil.getString("login_param_empty"));
        }

        // valid passowrd
        XxlJobUser xxlJobUser = xxlJobUserDao.loadByUserName(username);
        if (xxlJobUser == null) {
            return new ReturnT<>(500, I18nUtil.getString("login_param_unvalid"));
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(xxlJobUser.getPassword())) {
            return new ReturnT<>(500, I18nUtil.getString("login_param_unvalid"));
        }

        String loginToken = makeToken(xxlJobUser);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ReturnT<String>
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return XxlJobUser
     */
    public XxlJobUser ifLogin(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            XxlJobUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                XxlJobUser dbUser = xxlJobUserDao.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }
}
