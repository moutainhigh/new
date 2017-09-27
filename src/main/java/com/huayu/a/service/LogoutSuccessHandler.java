package com.huayu.a.service;

import com.huayu.common.tool.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DengPeng on 2017/4/19.
 */
@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    @Value("#{configProperties['seeyonSsoLogoutUrl']}")
    private String ssoLogoutUrl;

    @Autowired
    private SsoUserMap ssoUserMap;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails){
            UserDetails user = (UserDetails) principal;
            String ticket = ssoUserMap.getTicket(user.getUsername());
            if (null!=ticket){
                ssoUserMap.clear(ticket,user.getUsername());
                HttpClientUtil.getContent(ssoLogoutUrl + ticket);
                logger.info("通知OA单点登陆下线：{}",user.getUsername());
            }
        }
        response.sendRedirect("/admin/login/input");
    }
}
