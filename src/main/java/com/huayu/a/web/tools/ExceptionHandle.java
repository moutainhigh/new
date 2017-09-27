package com.huayu.a.web.tools;

import com.huayu.common.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by DengPeng on 2017/5/24.
 */
@ControllerAdvice
public class ExceptionHandle {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public String handleSQLException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        if (request.getMethod().equals("POST")){
            BaseResult baseResult = BaseResult.initBaseResult();
            String message = ex.getMessage();
            if (!StringUtils.hasText(message)){
                message = "服务器异常！";
            }
            baseResult.setRmsg(message);
            logger.error("请求异常！ url:{}",request.getRequestURL());
            logger.error("异常详情：",ex);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type","text/html;charset=UTF-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException e) {
                logger.error("响应错误失败",e);
            }
            writer.write(baseResult.toString());
            writer.flush();
            writer.close();
            return null;
        }else{
            return "/admin/index/500";
        }
    }
}
