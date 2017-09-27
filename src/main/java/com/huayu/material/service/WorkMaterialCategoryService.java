package com.huayu.material.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.material.dao.WorkMaterialCatoryDao;
import com.huayu.material.domain.WorkMaterialCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WorkMaterialCategoryService {

	@Resource
	private WorkMaterialCatoryDao workMaterialCatoryDao;


	/**
	 * 新增节点
	 * @return
	 */
	@Transactional
	public void insertContentCat(WorkMaterialCategory category) {
		List<WorkMaterialCategory> countByName = workMaterialCatoryDao.getCatByName(category);
		if (countByName != null && countByName.size() > 0){
			throw new BusinessException("分类名称重复");
		}
		category.setIsParent(0);
		category.setStatus(1);
		List<WorkMaterialCategory> list = workMaterialCatoryDao.getMaterialCategoryByParentId(category);
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

		if (workMaterialCatoryDao.postData(category)!=1){
			throw new BusinessException("添加分类失败");
		}
		if (category.getParentId()!=-1) {
			WorkMaterialCategory parent = new WorkMaterialCategory();
			parent.setId(category.getParentId().intValue());
			parent.setIsParent(1);
			if (workMaterialCatoryDao.updateIsParentById(parent) != 1) {
				throw new BusinessException("修改父级分类失败");
			}
		}
	}

	@Transactional
	public void update(WorkMaterialCategory materialCategory) {
		if(workMaterialCatoryDao.updateNameById(materialCategory)!=1){
			throw new BusinessException("修改分项失败");
		}
	}

	/**
	 * 删除节点以
	 * @return
	 */
	@Transactional
	public void deleteContentCat(WorkMaterialCategory category) {
		if (workMaterialCatoryDao.getMaterialCountByCatId(category.getId()) != 0){
			throw new BusinessException("该分项工程已经关联了分项分类，不能删除");
		}
		if (workMaterialCatoryDao.deleteById(category.getId())!=1){
			throw new BusinessException("删除分项工程失败");
		}
		List<WorkMaterialCategory> children = workMaterialCatoryDao.getMaterialCategoryByParentId(category);
		if (CollectionUtils.isEmpty(children)){
			WorkMaterialCategory parent = new WorkMaterialCategory();
			parent.setId(category.getParentId());
			parent.setIsParent(0);
			if (workMaterialCatoryDao.updateIsParentById(parent)!=1){
				throw new BusinessException("更新父级分项工程失败");
			}
		}
	}


	public List<WorkMaterialCategory> getCatTree(Integer parentId) {
		WorkMaterialCategory materialCategory = new WorkMaterialCategory();
		materialCategory.setParentId(parentId.intValue());
		String sql = "SELECT * from work_material_category where `status` = 1 ORDER BY `code`";
		List<WorkMaterialCategory> categories = workMaterialCatoryDao.get(sql, materialCategory);
		Map<Integer,WorkMaterialCategory> categoryMap = new LinkedHashMap<>();
		categories.forEach(e->{
			categoryMap.put(e.getId(),e);
		});
		return buildTree(parentId,categoryMap);
	}

	private List<WorkMaterialCategory> buildTree(Integer parentId, Map<Integer,WorkMaterialCategory> categoryMap) {
		List<WorkMaterialCategory> list = new ArrayList<>();
		for(WorkMaterialCategory m : categoryMap.values()) {
			if (!m.isContain()){
				if (m.getParentId().equals(parentId)){
					m.setContain(true);
					list.add(m);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)){
			for (WorkMaterialCategory c : list){
				if (c.getIsParent()==1){
					List<WorkMaterialCategory> child = buildTree(c.getId(),categoryMap);
					c.setList(child);
				}
			}
		}
		return list;
	}

	public List<WorkMaterialCategory> getMaterialCategoryList() {
		String sql = "SELECT * from work_material_category WHERE `status` = 1 ORDER BY `code`";
		return workMaterialCatoryDao.get(sql, new WorkMaterialCategory());
	}

}
