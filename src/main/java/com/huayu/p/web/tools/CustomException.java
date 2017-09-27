package com.huayu.p.web.tools;

/**
 * 自定义
 * author zxl
 * 2016-11-7 13:32
 */
public class CustomException extends Exception {

	public CustomException() {}                //用来创建无参数对象
	public CustomException(String message) {        //用来创建指定参数对象
		super(message);                             //调用超类构造器
	}

}
