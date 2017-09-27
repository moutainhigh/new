package com.huayu.material.web;


import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.material.domain.WorkMaterialCategory;
import com.huayu.material.domain.validate.CategoryGroupDelete;
import com.huayu.material.domain.validate.CategoryGroupInsert;
import com.huayu.material.domain.validate.CategoryGroupUpdate;
import com.huayu.material.service.WorkMaterialCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/workMaterialCategory")
public class WorkMaterialCategoryController {
	
	@Resource
	private WorkMaterialCategoryService workMaterialCategoryService;

	/**
	 * 主页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String getMaterialCategoryList(Model model){
		List<WorkMaterialCategory> list = workMaterialCategoryService.getMaterialCategoryList();
		model.addAttribute("list",list);
		return ".admin.work_material.materialCatList";
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<WorkMaterialCategory> treeList() {
		return workMaterialCategoryService.getCatTree(-1);
	}

	@RequestMapping("/create")
	@ResponseBody
	public BaseResult insertContentCat(@Validated(CategoryGroupInsert.class) WorkMaterialCategory materialCategory, BindingResult result) {
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			workMaterialCategoryService.insertContentCat(materialCategory);
			baseResult.setSuccess();
		} catch (BusinessException e) {
			baseResult.setRmsg(e.getMessage());
		}
		return baseResult;
	}
	
	@RequestMapping("/update")
	public  String updateContentCat(@Validated(CategoryGroupUpdate.class) WorkMaterialCategory materialCategory, BindingResult result){
		if (result.hasErrors()){
			throw new BusinessException(result.getFieldError().getDefaultMessage());
		}
		workMaterialCategoryService.update(materialCategory);
		return "redirect:/admin/workMaterialCategory/index";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public BaseResult  deleteContent(@Validated(CategoryGroupDelete.class) WorkMaterialCategory category, BindingResult result){
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			workMaterialCategoryService.deleteContentCat(category);
			baseResult.setSuccess();
		} catch (BusinessException e) {
			baseResult.setRmsg(e.getMessage());
		}
		return baseResult;
	}

}
