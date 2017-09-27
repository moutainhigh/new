package com.huayu.a.web.tools;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.LocalContext;
import com.huayu.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StopWatchHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(StopWatchHandlerInterceptor.class);

	private ThreadLocal<Long> stopwatchLocal = new ThreadLocal<>();

	private final boolean isOpen = true;


	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (isOpen) {
			stopwatchLocal.set(System.currentTimeMillis() );
		}
		bindData();
		return super.preHandle(request,response,handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		if (isOpen) {
			long endTime = System.currentTimeMillis();
			long beginTime = stopwatchLocal.get();
			long consumeTime = endTime - beginTime;
			log.debug("访问:{},消耗时间:{}毫秒",request.getRequestURL().toString()  ,consumeTime);
		}
		clearData();
	}

	private void bindData(){
		User user = SpringSecurityUtil.getUser();
		if (null!=user){
			LocalContext localContext = new LocalContext(user.getCurrDataId(),user.getRegSource());
			localContext.setCurrDataValue(user.getCurrDataValue());
			ConstantsHolder.setContext(localContext);
		}
	}

	private void clearData(){
		ConstantsHolder.clearContext();
	}

}
