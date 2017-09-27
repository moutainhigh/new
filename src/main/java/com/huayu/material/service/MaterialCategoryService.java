package com.huayu.material.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.material.dao.MaterialCatoryDao;
import com.huayu.material.domain.MaterialCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MaterialCategoryService {

	@Resource
	private MaterialCatoryDao materialCatoryDao;



	/**
	 * 新增节点
	 * @return
	 */
	@Transactional
	public void insertContentCat(MaterialCategory category) {
		List<MaterialCategory> countByName = materialCatoryDao.getCatByName(category);
		if (countByName != null && countByName.size() > 0){
			throw new BusinessException("分类名称重复");
		}
		category.setIsParent(0);
		category.setStatus(1);
		List<MaterialCategory> list = materialCatoryDao.getMaterialCategoryByParentId(category);
		if(list == null || list.size() == 0){
			category.setCode(category.getCode()+"01");
		}else{
			String oldCode = list.get(list.size() - 1).getCode();
			String _start = oldCode.substring(0,oldCode.length() - 2);
			String _end = oldCode.substring(oldCode.length() - 2);
			if (Integer.parseInt(_end)<9){
				category.setCode(_start+"0"+String.valueOf(Integer.parseInt(_end)+1));
			}else if (Integer.parseInt(_end) == 99){
				throw new BusinessException("超过最大分类数量限制");
			}else{
				category.setCode(_start+String.valueOf(Integer.parseInt(_end)+1));
			}
		}
		category.setCreatetime(new Date());
		category.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());

		if (materialCatoryDao.postData(category)!=1){
			throw new BusinessException("添加分类失败");
		}
		if (category.getParentId()!=-1) {
			MaterialCategory parent = new MaterialCategory();
			parent.setId(category.getParentId().intValue());
			parent.setIsParent(1);
			if (materialCatoryDao.updateIsParentById(parent) != 1) {
				throw new BusinessException("修改父级分类失败");
			}
		}
	}

	@Transactional
	public void update(MaterialCategory materialCategory) {
		if (materialCatoryDao.updateNameById(materialCategory)!=1){
			throw new BusinessException("修改分类失败");
		}
	}

	/**
	 * 删除节点以
	 * @return
	 */
	@Transactional
	public void deleteContentCat(MaterialCategory category) {
		if (materialCatoryDao.getMaterialCountByCatId(category.getId()) != 0){
			throw new BusinessException("不能删除关联了材料的分类");
		}
		if (materialCatoryDao.deleteById(category.getId())!=1){
			throw new BusinessException("删除分类失败");
		}
		List<MaterialCategory> children = materialCatoryDao.getMaterialCategoryByParentId(category);
		if (CollectionUtils.isEmpty(children)){
			MaterialCategory parent = new MaterialCategory();
			parent.setId(category.getParentId());
			parent.setIsParent(0);
			if (materialCatoryDao.updateIsParentById(parent)!=1){
				throw new BusinessException("更新父级分类失败");
			}
		}
	}


	public List<MaterialCategory> getCatTree(Integer parentId){
		MaterialCategory materialCategory = new MaterialCategory();
		materialCategory.setParentId(parentId.intValue());
		String sql = "SELECT * from material_category where `status` = 1 ORDER BY `code`";
		List<MaterialCategory> categories = materialCatoryDao.get(sql, materialCategory);
		Map<Integer,MaterialCategory> categoryMap = new LinkedHashMap<>();
		categories.forEach(e->{
			categoryMap.put(e.getId(),e);
		});
		return buildTree(parentId,categoryMap);
	}

	private List<MaterialCategory> buildTree(Integer parentId, Map<Integer,MaterialCategory> categoryMap) {
		List<MaterialCategory> list = new ArrayList<>();
		for(MaterialCategory m : categoryMap.values()) {
			if (!m.isContain()){
				if (m.getParentId().equals(parentId)){
					m.setContain(true);
					list.add(m);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)){
			for (MaterialCategory c : list){
				if (c.getIsParent()==1){
					List<MaterialCategory> child = buildTree(c.getId(),categoryMap);
					c.setList(child);
				}
			}
		}
		return list;
	}


	public List<MaterialCategory> getMaterialCategoryList() {
		String sql = "SELECT * from material_category WHERE `status` = 1 ORDER BY `code`";
		List<MaterialCategory> list = materialCatoryDao.get(sql, new MaterialCategory());
		return list;
	}


}
