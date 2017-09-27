package com.huayu.material.dao;

import com.huayu.material.domain.MaterialPrice;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
@Repository
public class MaterialPriceDao extends BasePageDao<MaterialPrice, Serializable> {

    public MaterialPrice getMaterialPriceByYM(MaterialPrice params) {
        String sql  = " SELECT * from material_price where matId = :matId and city= :city AND reportYearMonth=:reportYearMonth";
        return super.getOne(sql,params);
    }

    public List<MaterialPrice> getMaterialPrice(MaterialPrice priceResult) {
        String sql = "SELECT * from material_price WHERE matId = :matId";
        return super.get(sql,priceResult);
    }


    public int updateMaterial(MaterialPrice priceResult) {
        String sql = "UPDATE material_price SET price = :price,updatetime=:updatetime,updateUser=:updateUser WHERE matId = :matId AND  city = :city AND id=:id";
        return super.put(sql,priceResult);

    }

    public int insertHistoryPrice(MaterialPrice priceResult){
        Long key = super.getKey("material_history_price",priceResult);
        String sql = "INSERT into material_history_price (id,matId,city,price,createtime,createUser) VALUES (:id,:matId,:city,:price,:createtime,:createUser)";
        priceResult.setId(key.intValue());
        return super.post(sql,priceResult);
    }


    public int initPrice(MaterialPrice priceResult){
        Long key = super.getKey("material_price",priceResult);
        priceResult.setId(key.intValue());
       return super.post(priceResult);
    }
}
