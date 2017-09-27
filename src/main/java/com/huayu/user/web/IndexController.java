package com.huayu.user.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.service.HrRemindService;
import com.huayu.hr.service.HrService;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/index")
public class IndexController {

	@Autowired
	private HrRemindService hrRemindService;

	@Autowired
	private HrService hrService;

	@RequestMapping("/default")
	public String index(Model model) {
		User user = SpringSecurityUtil.getUser();
		model.addAttribute("user",user);
		if ("hr".equals(user.getRegSource())&&null!=user.getCurrCompanyId()){
			List<HrEmployee> allNoEducationEmp = hrService.getAllNoEducationEmp();
			model.addAttribute("noEducationEmpList", allNoEducationEmp);
			model.addAttribute("birthRemindData", hrRemindService.getRemindData(1));
			model.addAttribute("turnFormalRemindData", hrRemindService.getRemindData(2));
			model.addAttribute("contractEndRemindData", hrRemindService.getRemindData(3));
		}
		return ".admin.index.default";
	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {

		return "/admin/index/accessDenied";
	}

	@RequestMapping("/broadcast/{text}")
	@ResponseBody
	public BaseResult broadcast(@PathVariable String text, HttpSession session) {
		if (StringUtils.hasText(text)){
			if ("clear".equals(text)){
				session.removeAttribute("broadcastMsg");
			}else{
				session.setAttribute("broadcastMsg", text);
			}
		}
		return BaseResult.initBaseResult().setSuccess();
	}

}
