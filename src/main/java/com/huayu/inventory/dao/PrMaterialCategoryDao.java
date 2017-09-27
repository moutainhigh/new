package com.huayu.inventory.dao;

import com.huayu.inventory.domain.PrMaterialCategory;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class PrMaterialCategoryDao extends BasePageDao<PrMaterialCategory, Serializable> {


	public int postData(PrMaterialCategory category){
		Long preId = super.getKey("pr_material_category",category);
		category.setId(preId.intValue());
		return super.post(category);
	}

	public List<PrMaterialCategory> getAllCategoryList(PrMaterialCategory category){
		String sql = "SELECT * from pr_material_category where `status` = 0 ORDER BY `code`";
		return super.get(sql,category);
	}

	public PrMaterialCategory getOne(PrMaterialCategory category) {
		String sql = "select * from pr_material_category where id=:id";
		return super.getOne(sql,category);
	}

	public int updateNameById(PrMaterialCategory materialCategory) {
		String sql = "update pr_material_category set name=:name where id=:id";
		return super.put(sql.toString(), materialCategory);
	}
	public int updateIsParentById(PrMaterialCategory materialCategory) {
		String sql = "update pr_material_category set isParent=:isParent where id=:id";
		return super.put(sql.toString(), materialCategory);
	}
	public int deleteById(PrMaterialCategory materialCategory) {
		String sql = "UPDATE pr_material_category SET `status`=1,deletetime=:deletetime,deleteUser=:deleteUser WHERE id = :id";
		return super.put(sql,materialCategory);
	}

	public List<PrMaterialCategory> getMaterialCategoryByParentId(PrMaterialCategory materialCategory2) {
		String sql = "select * from pr_material_category where parentId=:parentId AND status = 0 ORDER BY `code` ASC";
		return super.get(sql, materialCategory2);
	}


	public List<PrMaterialCategory> getCatByName(PrMaterialCategory category) {
	    	String sql = "SELECT * from pr_material_category WHERE name = :name AND status=0";
	    	return super.get(sql,category);
    }

	public List<PrMaterialCategory> getMaterialCategoryList(PrMaterialCategory category) {
		String sql = "SELECT * from pr_material_category WHERE `status` = 0 ORDER BY `code`";
		return super.get(sql,category);
	}
}
