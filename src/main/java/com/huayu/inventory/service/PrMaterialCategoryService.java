package com.huayu.inventory.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.inventory.dao.PrMaterialCategoryDao;
import com.huayu.inventory.dao.PrMaterialDao;
import com.huayu.inventory.domain.PrMaterialCategory;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class PrMaterialCategoryService {

	@Autowired
	private PrMaterialCategoryDao prMaterialCategoryDao;

	@Autowired
	private PrMaterialDao prMaterialDao;


	/**
	 * 新增节点
	 * @return
	 */
	@Transactional
	public void insertContentCat(PrMaterialCategory category) {
		List<PrMaterialCategory> countByName = prMaterialCategoryDao.getCatByName(category);
		if (countByName != null && countByName.size() > 0){
			throw new BusinessException("分类名称重复");
		}
		category.setIsParent(0);
		category.setStatus(0);
		List<PrMaterialCategory> list = prMaterialCategoryDao.getMaterialCategoryByParentId(category);
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

		if (prMaterialCategoryDao.postData(category)!=1){
			throw new BusinessException("添加分类失败");
		}
		if (category.getParentId()!=0){
			PrMaterialCategory parent = new PrMaterialCategory();
			parent.setId(category.getParentId().intValue());
			parent.setIsParent(1);
			if (prMaterialCategoryDao.updateIsParentById(parent)!=1){
				throw new BusinessException("修改父级分类失败");
			}
		}
	}

	@Transactional
	public void update(PrMaterialCategory materialCategory) {
		if (prMaterialCategoryDao.updateNameById(materialCategory)!=1){
			throw new BusinessException("修改分类失败");
		}
	}

	/**
	 * 删除节点以
	 * @return
	 */
	@Transactional
	public void deleteContentCat(PrMaterialCategory category) {
		if (prMaterialDao.getMaterialCountByCatId(category.getId()) != 0){
			throw new BusinessException("不能删除关联了材料的分类");
		}
		User user = SpringSecurityUtil.getUser();
		category.setDeletetime(new Date());
		category.setDeleteUser(user.getUserId().intValue());
		if (prMaterialCategoryDao.deleteById(category)!=1){
			throw new BusinessException("删除分类失败");
		}
		List<PrMaterialCategory> children = prMaterialCategoryDao.getMaterialCategoryByParentId(category);
		if (CollectionUtils.isEmpty(children)){
			PrMaterialCategory parent = new PrMaterialCategory();
			parent.setId(category.getParentId());
			parent.setIsParent(0);
			if (prMaterialCategoryDao.updateIsParentById(parent)!=1){
				throw new BusinessException("更新父级分类失败");
			}
		}
	}


	public List<PrMaterialCategory> getCatTree(Integer parentId){
		PrMaterialCategory materialCategory = new PrMaterialCategory();
		List<PrMaterialCategory> categories = prMaterialCategoryDao.getAllCategoryList(materialCategory);
		Map<Integer,PrMaterialCategory> categoryMap = new LinkedHashMap<>();
		categories.forEach(e->{
			categoryMap.put(e.getId(),e);
		});
		return buildTree(parentId,categoryMap);
	}

	private List<PrMaterialCategory> buildTree(Integer parentId, Map<Integer,PrMaterialCategory> categoryMap) {
		List<PrMaterialCategory> list = new ArrayList<>();
		for(PrMaterialCategory m : categoryMap.values()) {
			if (!m.isContain()){
				if (m.getParentId().equals(parentId)){
					m.setContain(true);
					list.add(m);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)){
			for (PrMaterialCategory c : list){
				if (c.getIsParent()==1){
					List<PrMaterialCategory> child = buildTree(c.getId(),categoryMap);
					c.setList(child);
				}
			}
		}
		return list;
	}


	public List<PrMaterialCategory> getMaterialCategoryList() {

		return prMaterialCategoryDao.getMaterialCategoryList(new PrMaterialCategory());
	}


}
