package com.huayu.material.dao;


import com.huayu.material.domain.WorkMaterialCategory;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class WorkMaterialCatoryDao extends BasePageDao<WorkMaterialCategory, Serializable> {
	
	@Resource
	private com.ly.dao.base.BaseNamedParameterJdbcTemplate jdbcTemplate;

	public int postData(WorkMaterialCategory category){
		Long preId = super.getKey("work_material_category",category);
		category.setId(preId.intValue());
		return super.post(category);
	}

	@Override
	public WorkMaterialCategory getOne(WorkMaterialCategory a1) {
		String sql = "select * from work_material_category where id=:id";
		return super.getOne(sql,a1);
	}

	public int updateNameById(WorkMaterialCategory materialCategory) {
		String sql = "update work_material_category set name=:name where id=:id";
		return super.put(sql.toString(), materialCategory);
	}
	public int updateIsParentById(WorkMaterialCategory materialCategory) {
		String sql = "update work_material_category set isParent=:isParent where id=:id";
		return super.put(sql.toString(), materialCategory);
	}
	public int deleteById(Integer id) {
		String sql = "UPDATE work_material_category SET `status`=0 WHERE id = ?";
		return jdbcTemplate.getJdbcOperations().update(sql, id.intValue());
	}

	public List<WorkMaterialCategory> getMaterialCategoryByParentId(WorkMaterialCategory materialCategory2) {
		String sql = "select * from work_material_category where parentId=:parentId AND STATUS = 1 ORDER BY `code` ASC";
		return super.get(sql, materialCategory2);
	}


	public int getMaterialCountByCatId(Integer id) {
		String sql = "SELECT count(1) FROM work_material where categoryId = ? and status=0";
		return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, Integer.class);
	}

    public List<WorkMaterialCategory> getCatByName(WorkMaterialCategory category) {
		String sql = "SELECT * from work_material_category WHERE name = :name and status=1";
		return super.get(sql,category);
    }
}
