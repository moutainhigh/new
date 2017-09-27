package com.huayu.inventory.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.ConstantsHolder;
import com.huayu.inventory.domain.PrItemOutStorageList;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/19.
 */
@Repository
public class PrItemOutStorageListDao extends BasePageDao<PrItemOutStorageList,Integer>{

    public int post(PrItemOutStorageList entity) {
        Long key = super.getKey("pr_item_out_storage_list",entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public int[] batchUpdate(SqlParameterSource[] a2) {
        String sql = "insert into pr_item_out_storage_list (id,deliveryId,outStorageId,materialId,specificationId,`use`,num,price,sumPrice,taxRate,tax,excTaxPrice,excTaxSumPrice,balanceNum,balancePrice,balanceSumPrice,balanceExcTaxPrice,balanceExcTaxSumPrice,remark) " +
                "values (:id,:deliveryId,:outStorageId,:materialId,:specificationId,:use,:num,:price,:sumPrice,:taxRate,:tax,:excTaxPrice,:excTaxSumPrice,:balanceNum,:balancePrice,:balanceSumPrice,:balanceExcTaxPrice,:balanceExcTaxSumPrice,:remark)";
        return super.batchUpdate(sql, a2);
    }

    public List<PrItemOutStorageList> getOutStorageListsByOutStorageId(PrItemOutStorageList prItemOutStorageList) {
        String sql = "SELECT m.id materialId,mc.`name` categoryName, m.`name` materialName, ms.specification, m.alias, di.dictValue unit,os.billReceiver,os.billReceiveDate, sl.*";
                sql+=" FROM pr_item_out_storage_list sl ";
                sql+=" INNER JOIN pr_item_out_storage os ON os.id = sl.outStorageId";
                sql+=" INNER JOIN pr_project p on p.id = os.projectId";
                sql+=" INNER JOIN pr_material m ON m.id = sl.materialId";
                sql+=" INNER JOIN pr_material_specification ms ON ms.id = sl.specificationId";
                sql+=" INNER JOIN pr_material_category mc ON mc.id = m.categoryId ";
                sql+=" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit'";
                sql+=" WHERE outStorageId = :outStorageId and os.projectId ="+ ConstantsHolder.getContext().getCurrDataId();
        return super.get(sql,prItemOutStorageList);
    }

    public PrItemOutStorageList getOutStorageSum(PrItemOutStorageList prItemOutStorage) {
        StringBuilder stringBuilder = new StringBuilder("SELECT  l.materialId,l.specificationId,SUM(l.sumPrice) sumPrice ,SUM(l.num) num,SUM(l.excTaxSumPrice) excTaxSumPrice");
        stringBuilder.append(" FROM pr_item_out_storage_list l ");
        stringBuilder.append(" WHERE l.materialId = :materialId AND l.specificationId = :specificationId and l.deliveryId=:deliveryId GROUP BY l.deliveryId");
        return super.getOne(stringBuilder.toString(),prItemOutStorage);
    }

    public PrItemOutStorageList getStorageDetailOne(PrItemOutStorageList prItemOutStorageList){
        StringBuilder stringBuilder = new StringBuilder("SELECT o.repositoryId,l.* FROM pr_item_out_storage_list l INNER JOIN pr_item_out_storage o ON o.id = l.outStorageId");
        stringBuilder.append(" WHERE l.id=:id AND l.outStorageId=:outStorageId");
        return super.getOne(stringBuilder.toString(),prItemOutStorageList);
    }

    public PrItemOutStorageList getStorageDetailOneByDelivery(PrItemOutStorageList prItemOutStorageList){
        StringBuilder stringBuilder = new StringBuilder("SELECT o.repositoryId,l.* FROM pr_item_out_storage_list l INNER JOIN pr_item_out_storage o ON o.id = l.outStorageId");
        stringBuilder.append(" WHERE  l.deliveryId =:deliveryId AND l.outStorageId=:outStorageId");
        return super.getOne(stringBuilder.toString(),prItemOutStorageList);
    }

    public PrItemOutStorageList getOutStorageByDelivery(PrItemOutStorageList prItemOutStorageList){
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM pr_item_out_storage_list WHERE deliveryId = :deliveryId LIMIT 0,1");
        return  super.getOne(stringBuilder.toString(),prItemOutStorageList);
    }

    public int updateStorageDetailOne(PrItemOutStorageList outStorageList) {
        StringBuilder stringBuilder = new StringBuilder("update pr_item_out_storage_list set `use`=:use,num=:num,price=:price,sumPrice=:sumPrice,tax=:tax,excTaxPrice=:excTaxPrice,excTaxSumPrice=:excTaxSumPrice");
        stringBuilder.append(",updatetime=:updatetime,updateUser=:updateUser  where id=:id AND outStorageId=:outStorageId");
        return super.put(stringBuilder.toString(),outStorageList);
    }


    public Page<PrItemOutStorageList> getOutStorageListDetailData(PrItemOutStorageList outStorage, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT m.`name` materialName,pms.specification,mc.`name` categoryName,o.outStorageNo,o.outStorageDate,o.itemReceiver,o.itemReceiverUnit,o.projectHouseNum,o.remark,r.name repositoryName,p.name projectName,di.dictValue unit,o.remark,osl.*");
        stringBuilder.append(" FROM pr_item_out_storage_list osl INNER JOIN pr_item_out_storage o ON o.id = osl.outStorageId ");
        stringBuilder.append(" INNER JOIN pr_project p on p.id = o.projectId ");
        stringBuilder.append(" INNER JOIN pr_repository r on r.id = o.repositoryId ");
        stringBuilder.append(" INNER JOIN pr_material m ON m.id = osl.materialId ");
        stringBuilder.append(" INNER JOIN pr_material_specification pms ON pms.id = osl.specificationId");
        stringBuilder.append(" INNER JOIN pr_material_category mc ON mc.id = m.categoryId");
        stringBuilder.append(" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit'");
        stringBuilder.append(" INNER JOIN pr_item_delivery d ON d.id = osl.deliveryId");
        stringBuilder.append(" WHERE ");
        User user = SpringSecurityUtil.getUser();
        stringBuilder.append(" p.pcode REGEXP ").append(user.getDataAuthorityRegexp());

        if (StringUtils.isNotBlank(outStorage.getItemProviderName())){
            stringBuilder.append(" AND  position(:itemProviderName in d.itemProviderName)");
        }

        if (null != outStorage.getRepositoryId()){
            stringBuilder.append(" AND d.repositoryId = :repositoryId");
        }

        if (StringUtils.isNotBlank(outStorage.getOutStorageNo())){
            stringBuilder.append(" AND  position( :outStorageNo in o.outStorageNo)");
        }

        if (StringUtils.isNotBlank(outStorage.getMaterialName())){
            stringBuilder.append(" AND  position( :materialName in m.name)");
        }

        if (StringUtils.isNotBlank(outStorage.getSpecification())){
            stringBuilder.append(" AND  position( :specification in pms.specification)");
        }

        if (StringUtils.isNotBlank(outStorage.getMcCode())){
            stringBuilder.append(" AND mc.code REGEXP '^"+outStorage.getMcCode()+"'");
        }

        if (StringUtils.isNotBlank(outStorage.getItemReceiver())){
            stringBuilder.append(" AND  position( :itemReceiver in o.itemReceiver)");
        }

        if (StringUtils.isNotBlank(outStorage.getItemReceiverUnit())){
            stringBuilder.append(" AND  position( :itemReceiverUnit in o.itemReceiverUnit)");
        }

        if (StringUtils.isNotBlank(outStorage.getProjectHouseNum())){
            stringBuilder.append(" AND  position( :projectHouseNum in o.projectHouseNum)");
        }

        if (null!=outStorage.getStartTime() && null!=outStorage.getEndTime()){
            stringBuilder.append(" AND o.outStorageDate >= :startTime AND o.outStorageDate <=:endTime");
        }
        if (null == pageable){
            return new PageImpl(super.get(stringBuilder.toString(),outStorage));
        }
        return super.get(stringBuilder.toString(), outStorage, pageable);
    }


}
