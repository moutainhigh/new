package com.huayu.common;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DengPeng on 2017/6/5.
 */
public class PageArgsResolver implements WebArgumentResolver{

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if (PageArgs.class.isAssignableFrom(methodParameter.getParameterType())){
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            String start = request.getParameter("start");
            String length = request.getParameter("length");
            String pageIndex = request.getParameter("pageIndex");
            if (StringUtils.hasText(start)&&StringUtils.hasText(length)){
                return new PageArgs(Integer.parseInt(start),Integer.parseInt(length));
            }else if (StringUtils.hasText(pageIndex)){
                return new PageArgs(Integer.parseInt(pageIndex));
            }
        }
        return UNRESOLVED;
    }
}
