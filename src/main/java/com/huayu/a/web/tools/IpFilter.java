package com.huayu.a.web.tools;

import com.huayu.common.tool.IpUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DengPeng on 2017/1/20.
 */
public class IpFilter extends OncePerRequestFilter {

    private boolean enableFilterIp=false;

    public void setEnableFilterIp(boolean enableFilterIp) {
        this.enableFilterIp = enableFilterIp;
    }

    private String[] ips = {"113.204.228.230","61.128.134.135"};


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (enableFilterIp){
            boolean hasIp = false;
            String ipAddress = IpUtil.getIPAddress(request);
            for (String ip : ips) {
                if (ip.equals(ipAddress)) {
                    hasIp = true;
                    break;
                }
            }
            if (!hasIp) {
                request.getRequestDispatcher("/admin/login/p404").forward(request,response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
