package com.huayu.p.web.tools;


import com.huayu.a.domain.CommLog;
import com.huayu.a.service.CommLogService;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.p.util.CommonUtil;
import com.huayu.user.service.UserService;
import com.ly.web.base.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class appInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	@Autowired
    private CommLogService commLogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler)throws Exception{
        /*if(request.getServletPath().startsWith("/app/user")&&!request.getServletPath().startsWith("/app/user/tokenFail")){
        	String token = request.getHeader("token");
        	if(StringUtils.isBlank(token)||userService.isOverTyToken(token)){
        		request.getRequestDispatcher("/app/user/tokenFail").forward(request, response);
        		return false;
        	}else{
        		return true;
        	}
        }else{
            return true;
        }*/
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
        if (null!= SpringSecurityUtil.getUser()&&request.getServletPath().startsWith("/project")) {
            CommLog entity = new CommLog();
            entity.setLogIp(WebUtil.getIp(request));
            entity.setLogUrl(request.getRequestURL().toString());
            entity.setUserId(SpringSecurityUtil.getUser().getUserId());
            entity.setUsername(SpringSecurityUtil.getUser().getUsername());
            entity.setLogData(CommonUtil.packageParameters(request));
            commLogService.post(entity);
        }
	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		
	}
}
