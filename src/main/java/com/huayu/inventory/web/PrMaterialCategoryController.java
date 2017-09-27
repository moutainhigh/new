package com.huayu.inventory.web;

import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.inventory.domain.PrMaterialCategory;
import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.huayu.inventory.domain.validate.GroupUpdate;
import com.huayu.inventory.service.PrMaterialCategoryService;
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
@RequestMapping("/admin/inventory/materialCategory")
public class PrMaterialCategoryController {
	
	@Resource
	private PrMaterialCategoryService prMaterialCategoryService;


	/**
	 * 主页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String getMaterialCategoryList(Model model){
		List<PrMaterialCategory> list = prMaterialCategoryService.getMaterialCategoryList();
		model.addAttribute("list",list);
		return ".inventory.materialCategory.materialCatList";
	}


	/**
	 * 材料页面 选择类目 弹窗数据
	 * @return
	 */
	@RequestMapping("/tree")
	@ResponseBody
	public List<PrMaterialCategory> treeList() {
		return prMaterialCategoryService.getCatTree(0);
	}


	/**
	 * 添加分类
	 * @param materialCategory
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	@ResponseBody
	public BaseResult insertContentCat(@Validated(GroupInsert.class) PrMaterialCategory materialCategory, BindingResult result) {
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			prMaterialCategoryService.insertContentCat(materialCategory);
			baseResult.setRdata(materialCategory);
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
	@ResponseBody
	public BaseResult updateContentCat(@Validated(GroupUpdate.class) PrMaterialCategory materialCategory, BindingResult result){
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			prMaterialCategoryService.update(materialCategory);
			baseResult.setSuccess();
		} catch (BusinessException e) {
			baseResult.setRmsg(e.getMessage());
		}
		return baseResult;
	}


	/**
	 * 删除分类
	 * @param category
	 * @param result
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public BaseResult deleteContent(@Validated(GroupDelete.class) PrMaterialCategory category, BindingResult result){
		BaseResult baseResult = BaseResult.initBaseResult();
		try {
			if (result.hasErrors()){
				baseResult.setRmsg( result.getFieldError().getDefaultMessage());
				return baseResult;
			}
			prMaterialCategoryService.deleteContentCat(category);
			baseResult.setSuccess();
		} catch (BusinessException e) {
			baseResult.setRmsg(e.getMessage());
		}
		return baseResult;
	}

}
