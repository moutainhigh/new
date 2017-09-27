package com.huayu.inventory.dao;

import com.huayu.common.ConstantsHolder;
import com.huayu.inventory.domain.PrItemBalance;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/7/6.
 */
@Repository
public class PrItemBalanceDao extends BasePageDao<PrItemBalance,Integer>{

    public int postData(PrItemBalance entity) {
        Long key = super.getKey("pr_item_balance",entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public Page<PrItemBalance> balanceListData(PrItemBalance entity, Pageable pageable) {
        StringBuilder sql =new StringBuilder("SELECT b.*,c.name categoryName,m.name materialName,pms.specification FROM pr_item_balance b  ");
        sql.append(" INNER JOIN pr_material m ON m.id = b.materialId");
        sql.append(" INNER JOIN pr_material_category c ON c.id = m.categoryId");
        sql.append(" INNER JOIN pr_material_specification pms on pms.id = b.specificationId");
        sql.append(" INNER JOIN pr_repository r ON b.repositoryId = r.id ");
        sql.append(" WHERE r.projectId = ").append(ConstantsHolder.getContext().getCurrDataId());
        if (null != entity.getRepositoryId()){
            sql.append(" AND b.repositoryId = :repositoryId");
        }
        if (null!=entity.getBalanceDate()){
            sql.append(" AND b.balanceDate = :balanceDate");
        }
        if (null!=entity.getStartTime() && null!=entity.getEndTime()){
            sql.append(" AND b.balanceDate>= :startTime AND b.balanceDate <=:endTime");
        }
        return super.get(sql.toString(),entity,pageable);
    }

    public PrItemBalance getBalanceOne(PrItemBalance balance) {
        StringBuilder sql =new StringBuilder("SELECT b.*,c.name categoryName,m.name materialName,pms.specification FROM pr_item_balance b  ");
        sql.append(" INNER JOIN pr_material m ON m.id = b.materialId");
        sql.append(" INNER JOIN pr_material_category c ON c.id = m.categoryId");
        sql.append(" INNER JOIN pr_material_specification pms on pms.id = b.specificationId");
        sql.append(" INNER JOIN pr_repository r ON r.id = b.repositoryId");
        sql.append(" WHERE b.id=:id");
        sql.append(" AND r.projectId = ").append(ConstantsHolder.getContext().getCurrDataId());
        return super.getOne(sql.toString(),balance);
    }

    public int putBalanceOne(PrItemBalance balance){
        String sql  = "update pr_item_balance set balanceDate=:balanceDate,sumTax=:sumTax,sumPrice=:sumPrice where id=:id and materialId=:materialId and specificationId=:specificationId";
        return super.put(sql,balance);
    }
}
