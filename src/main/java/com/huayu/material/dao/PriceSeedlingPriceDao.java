package com.huayu.material.dao;

import com.huayu.material.domain.PriceSeedlingPrice;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
@Repository
public class PriceSeedlingPriceDao extends BasePageDao<PriceSeedlingPrice, Serializable> {

    public PriceSeedlingPrice getMaterialPriceByYM(PriceSeedlingPrice params) {
        String sql  = " SELECT *  from price_seedling_price where matId = :matId and city= :city AND reportYearMonth=:reportYearMonth";
        return super.getOne(sql,params);
    }

    public List<PriceSeedlingPrice> getMaterialPrice(PriceSeedlingPrice priceResult) {
        String sql = "SELECT * from price_seedling_price WHERE matId = :matId";
        return super.get(sql,priceResult);
    }


    public int updateMaterial(PriceSeedlingPrice priceResult) {
        String sql = "UPDATE price_seedling_price SET price = :price,updatetime=:updatetime,updateUser=:updateUser WHERE matId = :matId AND  city = :city AND id=:id";
        return super.put(sql,priceResult);
    }

    public int insertHistoryPrice(PriceSeedlingPrice priceResult){
        Long key = super.getKey("price_seedling_history_price",priceResult);
        String sql = "INSERT into price_seedling_history_price (id,matId,city,price,createtime,createUser) VALUES (:id,:matId,:city,:price,:createtime,:createUser)";
        priceResult.setId(key.intValue());
        return super.post(sql,priceResult);
    }


    public int initPrice(PriceSeedlingPrice priceResult){
        Long key = super.getKey("price_seedling_price",priceResult);
        priceResult.setId(key.intValue());
       return super.post(priceResult);
    }
}
