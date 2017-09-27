package com.huayu.material.web;

import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.material.domain.PriceSeedlingCategory;
import com.huayu.material.domain.validate.CategoryGroupDelete;
import com.huayu.material.domain.validate.CategoryGroupInsert;
import com.huayu.material.domain.validate.CategoryGroupUpdate;
import com.huayu.material.service.PriceSeedlingCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 材料分类
 */
@Controller
@RequestMapping("/admin/priceSeedlingCategory")
public class PriceSeedlingCategoryController {
	
	@Resource
	private PriceSeedlingCategoryService priceSeedlingCategoryService;


	/**
	 * 主页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String getMaterialCategoryList(Model model){
		List<PriceSeedlingCategory> list = priceSeedlingCategoryService.getCategoryList();
		model.addAttribute("list",list);
		return ".admin.priceSeedling.materialCatList";
	}


	/**
	 * 材料页面 选择类目 弹窗数据
	 * @return
	 */
	@RequestMapping("/tree")
	@ResponseBody
	public List<PriceSeedlingCategory> treeList() {
		return priceSeedlingCategoryService.getCatTree(-1);
	}


	/**
	 * 添加分类
	 * @param materialCategory
	 * @param result
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public BaseResult insertContentCat(@Validated(CategoryGroupInsert.class) PriceSeedlingCategory materialCategory, BindingResult result) {
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			priceSeedlingCategoryService.insertContentCat(materialCategory);
			baseResult.setSuccess();
		} catch (BusinessException e) {
			baseResult.setRmsg(e.getMessage());
		}
		return baseResult;
	}

	/**
	 * 修改分类名称
	 * @param materialCategory
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public  String updateContentCat(@Validated(CategoryGroupUpdate.class) PriceSeedlingCategory materialCategory, BindingResult result){
		if (result.hasErrors()){
			throw new BusinessException(result.getFieldError().getDefaultMessage());
		}
		priceSeedlingCategoryService.update(materialCategory);
		return "redirect:/admin/priceSeedlingCategory/index";
	}


	/**
	 * 删除分类
	 * @param category
	 * @param result
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public BaseResult  deleteContent(@Validated(CategoryGroupDelete.class) PriceSeedlingCategory category, BindingResult result){
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			priceSeedlingCategoryService.deleteContentCat(category);
			baseResult.setSuccess();
		} catch (BusinessException e) {
			baseResult.setRmsg(e.getMessage());
		}
		return baseResult;
	}

}
