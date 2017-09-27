package com.huayu.material.dao;

import com.huayu.material.domain.MaterialCategory;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class MaterialCatoryDao extends BasePageDao<MaterialCategory, Serializable> {
	
	@Resource
	private com.ly.dao.base.BaseNamedParameterJdbcTemplate jdbcTemplate;

	public int postData(MaterialCategory category){
		Long preId = super.getKey("material_category",category);
		category.setId(preId.intValue());
		return super.post(category);
	}

	@Override
	public MaterialCategory getOne(MaterialCategory a1) {
		String sql = "select * from material_category where id=:id";
		return super.getOne(sql,a1);
	}

	public int updateNameById(MaterialCategory materialCategory) {
		String sql = "update material_category set name=:name where id=:id";
		return super.put(sql.toString(), materialCategory);
	}
	public int updateIsParentById(MaterialCategory materialCategory) {
		String sql = "update material_category set isParent=:isParent where id=:id";
		return super.put(sql.toString(), materialCategory);
	}
	public int deleteById(Integer id) {
		String sql = "UPDATE material_category SET `status`=0 WHERE id = ?";
		return jdbcTemplate.getJdbcOperations().update(sql, id);
	}

	public List<MaterialCategory> getMaterialCategoryByParentId(MaterialCategory materialCategory2) {
		String sql = "select * from material_category where parentId=:parentId AND STATUS = 1 ORDER BY `code` ASC";
		return super.get(sql, materialCategory2);
	}


	public int getMaterialCountByCatId(Integer id) {
		String sql = "SELECT count(1) FROM material where categoryId = ? and status=0";
		return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, Integer.class);
	}

    public List<MaterialCategory> getCatByName(MaterialCategory category) {
	    	String sql = "SELECT * from material_category WHERE name = :name and parentId = :parentId and status=1";
	    	return super.get(sql,category);
    }

}
