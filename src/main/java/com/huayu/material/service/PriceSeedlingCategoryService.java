package com.huayu.material.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.material.dao.PriceSeedlingCategoryDao;
import com.huayu.material.domain.PriceSeedlingCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PriceSeedlingCategoryService {

	@Resource
	private PriceSeedlingCategoryDao catoryDao;



	/**
	 * 新增节点
	 * @return
	 */
	@Transactional
	public void insertContentCat(PriceSeedlingCategory category) {
		List<PriceSeedlingCategory> countByName = catoryDao.getCatByName(category);
		if (countByName != null && countByName.size() > 0){
			throw new BusinessException("分类名称重复");
		}
		category.setIsParent(0);
		category.setStatus(1);
		List<PriceSeedlingCategory> list = catoryDao.getMaterialCategoryByParentId(category);
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

		if (catoryDao.postData(category)!=1){
			throw new BusinessException("添加分类失败");
		}
		if (category.getParentId()!=-1) {
			PriceSeedlingCategory parent = new PriceSeedlingCategory();
			parent.setId(category.getParentId().intValue());
			parent.setIsParent(1);
			if (catoryDao.updateIsParentById(parent) != 1) {
				throw new BusinessException("修改父级分类失败");
			}
		}
	}

	@Transactional
	public void update(PriceSeedlingCategory materialCategory) {
		if (catoryDao.updateNameById(materialCategory)!=1){
			throw new BusinessException("修改分类失败");
		}
	}

	/**
	 * 删除节点以
	 * @return
	 */
	@Transactional
	public void deleteContentCat(PriceSeedlingCategory category) {
		if (catoryDao.getMaterialCountByCatId(category.getId()) != 0){
			throw new BusinessException("不能删除关联了材料的分类");
		}
		if (catoryDao.deleteById(category.getId())!=1){
			throw new BusinessException("删除分类失败");
		}
		List<PriceSeedlingCategory> children = catoryDao.getMaterialCategoryByParentId(category);
		if (CollectionUtils.isEmpty(children)&&category.getParentId()!=-1){
			PriceSeedlingCategory parent = new PriceSeedlingCategory();
			parent.setId(category.getParentId());
			parent.setIsParent(0);
			if (catoryDao.updateIsParentById(parent)!=1){
				throw new BusinessException("更新父级分类失败");
			}
		}
	}


	public List<PriceSeedlingCategory> getCatTree(Integer parentId){
		PriceSeedlingCategory materialCategory = new PriceSeedlingCategory();
		materialCategory.setParentId(parentId.intValue());
		String sql = "SELECT * from price_seedling_category where `status` = 1 ORDER BY `code`";
		List<PriceSeedlingCategory> categories = catoryDao.get(sql, materialCategory);
		Map<Integer,PriceSeedlingCategory> categoryMap = new LinkedHashMap<>();
		categories.forEach(e->{
			categoryMap.put(e.getId(),e);
		});
		return buildTree(parentId,categoryMap);
	}

	private List<PriceSeedlingCategory> buildTree(Integer parentId, Map<Integer,PriceSeedlingCategory> categoryMap) {
		List<PriceSeedlingCategory> list = new ArrayList<>();
		for(PriceSeedlingCategory m : categoryMap.values()) {
			if (!m.isContain()){
				if (m.getParentId().equals(parentId)){
					m.setContain(true);
					list.add(m);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)){
			for (PriceSeedlingCategory c : list){
				if (c.getIsParent()==1){
					List<PriceSeedlingCategory> child = buildTree(c.getId(),categoryMap);
					c.setList(child);
				}
			}
		}
		return list;
	}


	public List<PriceSeedlingCategory> getCategoryList() {
		String sql = "SELECT * from price_seedling_category WHERE `status` = 1 ORDER BY `code`";
		List<PriceSeedlingCategory> list = catoryDao.get(sql, new PriceSeedlingCategory());
		return list;
	}


}
