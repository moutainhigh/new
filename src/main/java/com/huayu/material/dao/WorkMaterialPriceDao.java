package com.huayu.material.dao;

import com.huayu.material.domain.WorkMaterialPrice;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
@Repository
public class WorkMaterialPriceDao extends BasePageDao<WorkMaterialPrice, Serializable> {


    public WorkMaterialPrice getMaterialPriceByYM(WorkMaterialPrice params) {
        String sql  = " SELECT * from work_material_price where matId = :matId and city= :city AND reportYearMonth=:reportYearMonth";
        return super.getOne(sql,params);
    }

    public List<WorkMaterialPrice> getMaterialPrice(WorkMaterialPrice price) {
        String sql = "SELECT * from work_material_price WHERE matId = :matId";
        return super.get(sql,price);
    }


    public int updateMaterial(WorkMaterialPrice price) {
        String sql = "UPDATE work_material_price SET price = :price,updatetime=:updatetime,updateUser=:updateUser WHERE matId = :matId AND  city = :city AND id=:id";
        return super.put(sql,price);

    }

    public int insertHistoryPrice(WorkMaterialPrice price){
        Long key = super.getKey("work_material_history_price",price);
        String sql = "INSERT into work_material_history_price (id,matId,city,price,createtime,createUser) VALUES (:id,:matId,:city,:price,:createtime,:createUser)";
        price.setId(key.intValue());
        return super.post(sql,price);
    }


    public int initPrice(WorkMaterialPrice price){
        Long key = super.getKey("work_material_price",price);
        price.setId(key.intValue());
        return super.post(price);
    }
}
