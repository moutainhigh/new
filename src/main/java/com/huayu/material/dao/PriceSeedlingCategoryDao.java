package com.huayu.material.dao;

import com.huayu.material.domain.PriceSeedlingCategory;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class PriceSeedlingCategoryDao extends BasePageDao<PriceSeedlingCategory, Serializable> {
	
	@Resource
	private com.ly.dao.base.BaseNamedParameterJdbcTemplate jdbcTemplate;

	public int postData(PriceSeedlingCategory category){
		Long preId = super.getKey("price_seedling_category",category);
		category.setId(preId.intValue());
		return super.post(category);
	}

	@Override
	public PriceSeedlingCategory getOne(PriceSeedlingCategory category) {
		String sql = "select * from price_seedling_category where id=:id";
		return super.getOne(sql,category);
	}

	public int updateNameById(PriceSeedlingCategory category) {
		String sql = "update price_seedling_category set name=:name where id=:id";
		return super.put(sql.toString(), category);
	}
	public int updateIsParentById(PriceSeedlingCategory category) {
		String sql = "update price_seedling_category set isParent=:isParent where id=:id";
		return super.put(sql.toString(), category);
	}
	public int deleteById(Integer id) {
		String sql = "UPDATE price_seedling_category SET `status`=0 WHERE id = ?";
		return jdbcTemplate.getJdbcOperations().update(sql, id);
	}

	public List<PriceSeedlingCategory> getMaterialCategoryByParentId(PriceSeedlingCategory category) {
		String sql = "select * from price_seedling_category where parentId=:parentId AND STATUS = 1 ORDER BY `code` ASC";
		return super.get(sql, category);
	}


	public int getMaterialCountByCatId(Integer id) {
		String sql = "SELECT count(1) FROM price_seedling where categoryId = ? and status=0";
		return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, Integer.class);
	}

    public List<PriceSeedlingCategory> getCatByName(PriceSeedlingCategory category) {
	    	String sql = "SELECT * from price_seedling_category WHERE name = :name and status=1";
	    	return super.get(sql,category);
    }

}
