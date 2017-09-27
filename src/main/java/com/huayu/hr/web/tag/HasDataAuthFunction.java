package com.huayu.hr.web.tag;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import java.io.IOException;


public class HasDataAuthFunction{

	public static Boolean  has(Boolean test ,String companyCode) throws JspException, IOException {
		if (test && StringUtils.isNotBlank(companyCode)){
			User user = SpringSecurityUtil.getUser();
			String currCompanyCode = user.getCurrCompanyCode();
			if (StringUtils.isNotBlank(currCompanyCode)){
				if (Authority.isPosterity(currCompanyCode,companyCode)){
					return true;
				}
			}
		}
		return false;
	}

}
