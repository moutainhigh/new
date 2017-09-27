package com.huayu.cms.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.huayu.cms.service.CmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huayu.cms.domain.CmsContent;
import com.huayu.cms.service.CmsContentService;
import com.ly.service.base.ExcelUtil;

/**
 * desc：内容内容控制器controller
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:14
 */
@Controller
@RequestMapping("/admin/cms/content/")
public class CmsContentController  {

	@Autowired
	private CmsContentService cmsContentService;

	@Autowired
	private CmsMenuService cmsMenuService;

	protected String getOne(CmsContent cmsContent, ModelMap modelMap) {
		return null;
	}

	@RequestMapping("/get")
    public String get(CmsContent entity, ModelMap modelMap) {
		Pageable pageable = new PageRequest(entity.getPageNo(), entity.getPageSize(),entity.getDts());
		modelMap.addAttribute("entity",entity);
		modelMap.addAttribute("page",cmsContentService.get(entity,pageable));
		modelMap.addAttribute("menu", cmsMenuService.getOne(entity.getMid()));
		return ".admin.cms.list";
	}

	@RequestMapping("/edit")
    public String edit(CmsContent entity, ModelMap modelMap) {
		if (entity.getCid() != null) {
			modelMap.addAttribute("entity", cmsContentService.getOne(entity.getCid()));
		}
		modelMap.addAttribute("menu", cmsMenuService.getOne(entity.getMid()));
		return ".admin.cms.edit";
	}

	@RequestMapping("/post")
	public String post(CmsContent entity, ModelMap model) {
		cmsContentService.post(entity);
		return "redirect:/admin/cms/content/get?mid=" + entity.getMid();
	}

	@RequestMapping("/put")
    public String put(CmsContent entity, ModelMap modelMap) {
		cmsContentService.put(entity);
		return "redirect:/admin/cms/content/get?mid=" + entity.getMid();
	}

	@RequestMapping("/delete")
    public String delete(CmsContent entity, ModelMap modelMap) {
		cmsContentService.delete(entity.getCid());
		return "redirect:/admin/cms/content/get?mid=" + entity.getMid();
	}

	@RequestMapping("/xls")
	public void xls(CmsContent cmsContent,HttpServletResponse response) {
		
		ExcelUtil<CmsContent> util = new ExcelUtil<CmsContent>(CmsContent.class);
		try {
			util.exportExcel("内容", "内容", cmsContentService.get(cmsContent), "0", response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
