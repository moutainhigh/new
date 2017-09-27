package com.huayu.inventory.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.inventory.domain.PrItemInStorageList;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
@Repository
public class PrItemInStorageListDao extends BasePageDao<PrItemInStorageList, Serializable> {
    public List<PrItemInStorageList> getStorageListByInStorageId(Integer inStorageId) {
        String sql = "SELECT sl.id,sl.inStorageId, r.`name` repositoryName, m.`name` materialName, m.alias, ms.specification, cd.dictValue unit," +
                " d.num deliveryNum, d.id deliveryId, d.price deliveryPrice, d.sumPrice deliverySumPrice, d.tax deliveryTax," +
                " d.excTaxSumPrice deliveryExcTaxSumPrice, d.taxRate deliveryTaxRate, sl.num, sl.price, sl.inStorageMoney," +
                " sl.taxRate, sl.priceNoRate, sl.moneyNoRate, sl.priceSrc, sl.remark, sl.rateMoney, mc.`name` materialCategory,i.billReceiver,i.billReceiveDate" +
                " FROM pr_item_in_storage_list sl " +
                "INNER JOIN pr_item_in_storage i ON i.id = sl.inStorageId " +
                "INNER JOIN pr_item_delivery d ON sl.deliveryId = d.id " +
                "INNER JOIN pr_material m ON d.materialId = m.id " +
                "INNER JOIN pr_material_specification ms ON d.specificationId = ms.id " +
                "INNER JOIN pr_repository r ON r.id = d.repositoryId " +
                "INNER JOIN pr_material_category mc ON mc.id = m.categoryId " +
                "INNER JOIN common_dict cd ON cd.dictKey = m.unit AND cd.dictGroup='unit' " +
                "WHERE sl.inStorageId = "+inStorageId;
        return super.get(sql, new PrItemInStorageList());
    }

    public PrItemInStorageList getStorageListByDeliverId(Integer id) {
        PrItemInStorageList prItemInStorageList = new PrItemInStorageList();
        prItemInStorageList.setDeliveryId(id);
        String sql = "SELECT * FROM pr_item_in_storage_list WHERE deliveryId =:deliveryId LIMIT 0,1";
        return super.getOne(sql,prItemInStorageList);
    }

    public BigDecimal getInStorageSumNum(Integer deliveryId) {
        String sql = "SELECT sum(num) num FROM pr_item_in_storage_list WHERE deliveryId = "+deliveryId;
        PrItemInStorageList one = super.getOne(sql, new PrItemInStorageList());
        if (one != null && one.getNum() != null){
            return one.getNum();
        } else {
            return new BigDecimal(0);
        }
    }

    public Page<PrItemInStorageList> getInStorageListDetailData(PrItemInStorageList storage, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT i.inStorageNum,i.inStoreDate,i.inStoreUser,i.billNum,d.itemProviderName,r.`name` repositoryName, m.`name` materialName,ms.specification,cd.dictValue unit,mc.`name` materialCategory,sl.* ");
        stringBuilder.append(" FROM pr_item_in_storage_list sl");
        stringBuilder.append(" INNER JOIN pr_item_in_storage i ON i.id = sl.inStorageId");
        stringBuilder.append(" INNER JOIN pr_project p on p.id = i.projectId ");
        stringBuilder.append(" INNER JOIN pr_item_delivery d ON sl.deliveryId = d.id ");
        stringBuilder.append(" INNER JOIN pr_material m ON d.materialId = m.id");
        stringBuilder.append(" INNER JOIN pr_material_specification ms ON d.specificationId = ms.id");
        stringBuilder.append(" INNER JOIN pr_repository r ON r.id = d.repositoryId");
        stringBuilder.append(" INNER JOIN pr_material_category mc ON mc.id = m.categoryId ");
        stringBuilder.append(" INNER JOIN common_dict cd ON cd.dictKey = m.unit AND cd.dictGroup='unit'");
        stringBuilder.append(" WHERE ");
        User user = SpringSecurityUtil.getUser();
        stringBuilder.append(" p.pcode REGEXP ").append(user.getDataAuthorityRegexp());

        if (StringUtils.isNotBlank(storage.getItemProviderName())){
            stringBuilder.append(" AND  position(:itemProviderName in d.itemProviderName)");
        }

        if (null != storage.getRepositoryId()){
            stringBuilder.append(" AND d.repositoryId = :repositoryId");
        }

        if (StringUtils.isNotBlank(storage.getMaterialName())){
            stringBuilder.append(" AND  position( :materialName in m.name)");
        }

        if (StringUtils.isNotBlank(storage.getSpecification())){
            stringBuilder.append(" AND  position( :specification in ms.specification)");
        }

        if (StringUtils.isNotBlank(storage.getBillNum())){
            stringBuilder.append(" AND position( :billNum in i.billNum)" );
        }

        if (StringUtils.isNotBlank(storage.getInStorageNum())){
            stringBuilder.append(" AND  position( :inStorageNum in i.inStorageNum)");
        }

        if (null!=storage.getStartTime() && null!=storage.getEndTime()){
            stringBuilder.append(" AND o.inStoreDate >= :startTime AND o.inStoreDate <=:endTime");
        }
        if (null == pageable){
            List<PrItemInStorageList> prItemInStorageLists = super.get(stringBuilder.toString(), storage);
            return new PageImpl(prItemInStorageLists);
        }
        return super.get(stringBuilder.toString(), storage, pageable);
    }

    public int updateInStorageDetail(PrItemInStorageList detail) {
        String sql = "update pr_item_in_storage_list set num=:num,price=:price,inStorageMoney=:inStorageMoney,taxRate=:taxRate,priceNoRate=:priceNoRate,rateMoney=:rateMoney," +
                "moneyNoRate=:moneyNoRate,priceSrc=:priceSrc,remark=:remark where id=:id and inStorageId=:inStorageId ";
        return super.put(sql,detail);
    }
}
