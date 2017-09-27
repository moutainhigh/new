package com.huayu.inventory.dao;

import com.huayu.inventory.domain.PrItemStorageSum;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/19.
 */
@Repository
public class PrItemStorageSumDao extends BasePageDao<PrItemStorageSum, Serializable> {
    public int post(PrItemStorageSum entity) {
        Long key = super.getKey("pr_item_storage_sum",entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public int putItemStorageSum(PrItemStorageSum entity) {
        String sql = "UPDATE pr_item_storage_sum SET materialId = :materialId, specificationId = :specificationId,repositoryId = :repositoryId" +
                ",num=:num,sumPrice= :sumPrice,excTaxSumPrice=:excTaxSumPrice ";
        if (null != entity.getSupplySum()){
            sql+=",supplySum=:supplySum";
        }
        sql+=" WHERE id= :id";
        return super.put(sql,entity);
    }

    public int updateSumAndPrice(PrItemStorageSum entity) {
        String sql = "UPDATE pr_item_storage_sum SET num=num+(:newSum),sumPrice= sumPrice+(:newSumPrice),excTaxSumPrice=excTaxSumPrice+(:excTaxSumPrice) " +
                "WHERE materialId = :materialId and specificationId = :specificationId and repositoryId = :repositoryId";
        return super.put(sql,entity);
    }

    public PrItemStorageSum getItemStorageSum(PrItemStorageSum entity) {
        String sql = "SELECT * FROM pr_item_storage_sum WHERE materialId =:materialId AND specificationId =:specificationId AND repositoryId =:repositoryId ";
        return super.getOne(sql,entity);
    }

    public void updateSumNum(PrItemStorageSum prItemStorageSum) {
        String sql = "UPDATE pr_item_storage_sum SET num = :num, sumPrice=:sumPrice, excTaxSumPrice=:excTaxSumPrice,supplySum=:supplySum WHERE" +
                " materialId = :materialId and specificationId = :specificationId and repositoryId = :repositoryId";
        super.put(sql,prItemStorageSum);
    }


}
