package com.huayu.a.service.tools;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huayu.user.domain.User;

/**
 * desc：SpringSecurity工具
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:47
 */
public class SpringSecurityUtil {

	/***
	 * md5加密带盐值
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String md5(String password, String salt) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, salt).toString();
	}

	/***
	 * 获取session中的用户信息
	 * 
	 * @return
	 */
	public static User getUser() {
		try {
			User userDetails = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			return userDetails;
		} catch (Exception e) {
			return null;
		}
	}
}
