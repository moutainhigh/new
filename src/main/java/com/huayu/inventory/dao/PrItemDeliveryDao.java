package com.huayu.inventory.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.ConstantsHolder;
import com.huayu.inventory.domain.PrItemDelivery;
import com.huayu.inventory.web.args.PrItemDeliveryArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/14.
 */
@Repository
public class PrItemDeliveryDao extends BasePageDao<PrItemDelivery,Integer> {

    public int postData(PrItemDelivery delivery) {
        Long key = super.getKey("pr_item_delivery",delivery);
        delivery.setId(key.intValue());
        return super.post(delivery);
    }

    public Page<PrItemDelivery> getReceiptListData(PrItemDeliveryArgs delivery, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT osl.outStorageNum,isl.inStorageSum, isl.inStorageSumPrice, isl.inStorageExcTaxSumPrice,isl.inStorageTax,");
        sql.append("r.`name` repositoryName,m.`name` materialName,m.alias materialAlias,pms.specification,di.dictValue unit, d.*");
        sql.append(" FROM pr_item_delivery d ");
        sql.append(" INNER JOIN pr_material m ON d.materialId = m.id ");
        if (StringUtils.isNotBlank(delivery.getMcCode())) {
            sql.append(" INNER JOIN pr_material_category mc ON m.categoryId = mc.id");
        }
        sql.append(" INNER JOIN pr_material_specification pms ON pms.id = d.specificationId and pms.status=0 ");
        sql.append(" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit' ");
        sql.append(" LEFT JOIN  (SELECT deliveryId,SUM(num) outStorageNum FROM pr_item_out_storage_list GROUP BY deliveryId) osl ON d.id = osl.deliveryId ");
        sql.append(" LEFT JOIN (SELECT deliveryId,SUM(num) inStorageSum,SUM(inStorageMoney) inStorageSumPrice, SUM(moneyNoRate) inStorageExcTaxSumPrice,SUM(rateMoney) inStorageTax FROM pr_item_in_storage_list GROUP BY deliveryId) isl ON d.id = isl.deliveryId ");
        sql.append(" LEFT JOIN pr_repository r ON r.id = d.repositoryId");
        sql.append(" WHERE  m.status=0");
        if (null != delivery.getStatus()){
            sql.append(" AND d.`status` = :status ");
        }else{
            sql.append(" AND d.`status` = 0 ");
        }
        if (null == delivery.getInStorageStatus()){
            sql.append(" AND d.inStorageStatus<=2 ");
        }else{
            if (delivery.getInStorageStatus().equals(3)){
                sql.append(" AND d.inStorageStatus <= 1 ");
            }else{
                sql.append(" AND d.inStorageStatus = :inStorageStatus ");
            }
        }

        if (StringUtils.isNotBlank(delivery.getItemProviderName())){
            sql.append(" AND  position(:itemProviderName in d.itemProviderName)");
        }

        if (StringUtils.isNotBlank(delivery.getMcode())){
            sql.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(delivery.getMaterialName())){
            sql.append(" AND  position( :materialName in m.name)");
        }

        if (StringUtils.isNotBlank(delivery.getSpecification())){
            sql.append(" AND  position( :specification in pms.specification)");
        }

        if (null != delivery.getRepositoryId()){
            sql.append(" AND d.repositoryId = :repositoryId");
        }

        if (null!=delivery.getStartDate() && null!=delivery.getEndDate()){
            sql.append(" AND d.receiptDate >= :startDate AND d.receiptDate <=:endDate");
        }
        if (StringUtils.isNotBlank(delivery.getMcCode())){
            sql.append(" AND mc.code REGEXP '^"+delivery.getMcCode()+"'");
        }
        sql.append(" AND m.projectId = ").append(ConstantsHolder.getContext().getCurrDataId());
        sql.append(" GROUP BY d.id");
        return super.get(sql.toString(),delivery,pageable);
    }

    public PrItemDelivery getReceiptDataDetail(PrItemDeliveryArgs delivery) {
        StringBuilder sql = new StringBuilder("SELECT osl.outStorageNum,isl.inStorageSum, isl.inStorageSumPrice, isl.inStorageExcTaxSumPrice,isl.inStorageTax,d.*");
        sql.append(" FROM pr_item_delivery d ");
        sql.append(" LEFT JOIN  (SELECT deliveryId,SUM(num) outStorageNum FROM pr_item_out_storage_list GROUP BY deliveryId) osl ON d.id = osl.deliveryId ");
        sql.append(" LEFT JOIN (SELECT deliveryId,SUM(num) inStorageSum,SUM(inStorageMoney) inStorageSumPrice, SUM(moneyNoRate) inStorageExcTaxSumPrice,SUM(rateMoney) inStorageTax FROM pr_item_in_storage_list  GROUP BY deliveryId) isl ON d.id = isl.deliveryId ");
        sql.append(" WHERE  m.status=0 and d.id = :id");
        return super.getOne(sql.toString(),delivery);
    }


    public PrItemDelivery getReceiptOne(PrItemDelivery delivery) {
        StringBuilder sql = new StringBuilder("SELECT mc.name categoryName,m.`name` materialName,m.alias materialAlias,pms.specification,d.*,cd.dictValue unit ");
        sql.append(" FROM pr_item_delivery d ");
        sql.append(" INNER JOIN pr_material m ON d.materialId = m.id ");
        sql.append(" INNER JOIN pr_material_category mc ON m.categoryId = mc.id");
        sql.append(" INNER JOIN pr_material_specification pms ON pms.id = d.specificationId and pms.status=0 ");
        sql.append(" INNER JOIN common_dict cd ON cd.dictKey = m.unit and cd.dictGroup='unit' ");
        sql.append(" WHERE d.id=:id");
        sql.append(" AND m.projectId = ").append(ConstantsHolder.getContext().getCurrDataId());
        return super.getOne(sql.toString(),delivery);
    }

    public int updateReceipt(PrItemDelivery args) {
        String sql = "update pr_item_delivery  set receiptUserName=:receiptUserName,checkUserName=:checkUserName,receiptDate=:receiptDate," +
                "num=:num,price=:price,sumPrice=:sumPrice,taxRate=:taxRate,tax=:tax,excTaxSumPrice=:excTaxSumPrice,note=:note,itemProviderName=:itemProviderName," +//excTaxPrice=:excTaxPrice,
                "updatetime=:updatetime,updateUser=:updateUser" +
                " where id=:id and materialId=:materialId and specificationId=:specificationId";
        return super.put(sql,args);
    }

    public PrItemDelivery getItemDeliveryById(PrItemDelivery itemDelivery) {
        String sql = "SELECT mc.`name` categoryName, m.id mid, d.repositoryId, d.rejectedNum, d.id deliveryId, m.`name`" +
                " materialName, ms.specification, ms.id specificationId, cd.dictValue unit, m.alias, d.num, d.price," +
                " d.sumPrice, d.tax, d.taxRate, d.excTaxPrice, d.excTaxSumPrice, d.itemProviderName, r.`name` repositoryName" +
                " FROM pr_item_delivery d INNER JOIN pr_material m ON m.id = d.materialId INNER JOIN pr_material_specification ms" +
                " ON d.specificationId = ms.id INNER JOIN pr_material_category mc ON m.categoryId = mc.id INNER JOIN pr_repository r" +
                " ON d.repositoryId = r.id INNER JOIN common_dict cd ON cd.dictKey = m.unit AND cd.dictGroup='unit' WHERE d.id = :id";
        return this.getOne(sql, itemDelivery);
    }

    /**
     * 修改入库状态
     * @param deliveryId
     * @param i
     */
    public void updateDeliveryInStorageStatus(Integer deliveryId, int i) {
        String sql = "UPDATE pr_item_delivery SET inStorageStatus = :inStorageStatus WHERE id =:id ";
        PrItemDelivery itemDelivery = new PrItemDelivery();
        itemDelivery.setInStorageStatus(i);
        itemDelivery.setId(deliveryId);
        super.put(sql,itemDelivery);
    }

    public PrItemDelivery getItemDeliveryById(Integer id) {
        String sql = "select * from pr_item_delivery where id="+id;
        return super.getOne(sql,new PrItemDelivery());
    }

    public int deleteReceipt(PrItemDeliveryArgs delivery) {
        String sql = "UPDATE pr_item_delivery SET status = 1 WHERE id =:id";
        return super.put(sql,delivery);
    }

    public void putItemDeliveryNumAndStatus(PrItemDelivery prItemDelivery) {
        String sql = "UPDATE pr_item_delivery SET rejectedNum = :rejectedNum, inStorageStatus = :inStorageStatus WHERE id = :id";
        super.put(sql,prItemDelivery);
    }

    public void putItemDelivery(PrItemDelivery prItemDelivery) {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE pr_item_delivery SET");
        if (prItemDelivery.getStatus() != null) {
            sb.append(" status = :status");
        }
        if (prItemDelivery.getRejectedNum() != null) {
            sb.append(" rejectedNum = :rejectedNum");
        }
        if (prItemDelivery.getInStorageStatus() != null) {
            sb.append(" inStorageStatus=:inStorageStatus");
        }
        sb.append(" where id = :id");
        super.put(sb.toString(), prItemDelivery);
    }

    public List<PrItemDelivery> getItemDelivery(PrItemDelivery delivery) {
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM pr_item_delivery");
        stringBuilder.append(" WHERE");
        if (null!=delivery.getMaterialId() && null != delivery.getSpecificationId()){
            stringBuilder.append(" materialId=:materialId and specificationId=:specificationId");
        }
        if (null!=delivery.getRepositoryId()){
            stringBuilder.append(" repositoryId=:repositoryId");
        }
        stringBuilder.append(" and status=0");
        return super.get(stringBuilder.toString(),delivery);
    }

    /**
     * 查未入库的
     * @param entity
     * @param projectId
     * @param inStorageStatus
     * @return
     */
    public List<PrItemDelivery> receiptStatistics(PrItemDeliveryArgs entity, Integer projectId, Integer inStorageStatus) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT d.itemProviderName, d.receiptDate, m.`name` materialName, ms.specification," +
                " cd.dictValue unit, d.num, d.price, d.taxRate, d.excTaxSumPrice, d.sumPrice, d.id FROM pr_item_delivery d" +
                " INNER JOIN pr_material_specification ms ON d.specificationId = ms.id INNER JOIN pr_material m" +
                " ON d.materialId = m.id INNER JOIN common_dict cd ON m.unit = cd.dictKey WHERE d.inStorageStatus = 0" +
                " AND d.`status` = 0 AND m.projectId = "+projectId);
        if (entity.getRepositoryId() != null) {
            stringBuilder.append(" AND d.repositoryId = :repositoryId");
        }
        if (null!=entity.getStartDate() && null!=entity.getEndDate()){
            stringBuilder.append(" AND d.receiptDate >= :startDate AND d.receiptDate <=:endDate");
        }

        return super.get(stringBuilder.toString(), entity);
    }

    /**
     * 查询部分入库的
     * @param entity
     * @param projectId
     * @return
     */
    public List<PrItemDelivery> receiptStatisticsHasInstorage(PrItemDeliveryArgs entity, Integer projectId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d.itemProviderName, d.receiptDate, m.`name` materialName, ms.specification, cd.dictValue unit," +
                " (d.num - SUM(sl.num)) num, d.price, d.taxRate, ( d.excTaxSumPrice - SUM(sl.moneyNoRate) ) excTaxSumPrice," +
                " ( d.sumPrice - SUM(sl.inStorageMoney) ) sumPrice, d.id FROM pr_item_delivery d INNER JOIN pr_material_specification ms" +
                " ON d.specificationId = ms.id INNER JOIN pr_item_in_storage_list sl ON sl.deliveryId = d.id INNER JOIN pr_material m" +
                " ON d.materialId = m.id INNER JOIN common_dict cd ON m.unit = cd.dictKey WHERE d.`status` = 0 AND d.inStorageStatus = 1 AND m.projectId = "+projectId);
        if (entity.getRepositoryId() != null) {
            sb.append(" AND d.repositoryId = :repositoryId");
        }
        if (null!=entity.getStartDate() && null!=entity.getEndDate()){
            sb.append(" AND d.receiptDate >= :startDate AND d.receiptDate <=:endDate");
        }
        return super.get(sb.toString(), entity);
    }

    public StringBuilder buildSql(PrItemDelivery delivery){
        StringBuilder stringBuilder = new StringBuilder("SELECT d.id,m.id materialId,pms.id specificationId,m.`mcode` ,m.`name` materialName,m.alias,pms.specification,di.dictValue unit,mc.`name` categoryName,");
        stringBuilder.append("m.remark,SUM(IFNULL(l.num,0)) outStorageNum,SUM(IFNULL(l.sumPrice,0)) outStorageSumPrice,SUM(IFNULL(l.excTaxSumPrice,0)) outStorageExcTaxSumPrice,d.taxRate,d.tax,");
        stringBuilder.append("d.sumPrice,d.num,d.excTaxSumPrice,d.price,d.excTaxPrice,d.receiptDate");
        stringBuilder.append(" FROM pr_item_delivery d ");
        stringBuilder.append(" INNER JOIN pr_material m ON m.id = d.materialId AND m.status=0");
        stringBuilder.append(" INNER JOIN pr_material_category mc ON m.categoryId = mc.id");
        stringBuilder.append(" INNER JOIN pr_material_specification pms ON pms.id = d.specificationId AND pms.status=0");
        stringBuilder.append(" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit' ");
        stringBuilder.append(" INNER JOIN pr_project pp ON m.projectId = pp.id");
        stringBuilder.append(" LEFT JOIN pr_item_out_storage_list l ON l.deliveryId= d.id");
        stringBuilder.append(" WHERE ");
        User user = SpringSecurityUtil.getUser();
        stringBuilder.append(" pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());
        if (StringUtils.isNotBlank(delivery.getMcode())){
            stringBuilder.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(delivery.getMaterialName())){
            stringBuilder.append(" AND  position( :materialName in m.name)");
        }

        if (StringUtils.isNotBlank(delivery.getSpecification())){
            stringBuilder.append(" AND  position( :specification in pms.specification)");
        }

        if (StringUtils.isNotBlank(delivery.getMcCode())){
            stringBuilder.append(" AND mc.code REGEXP '^"+delivery.getMcCode()+"'");
        }

        if (null!=delivery.getIds()){
            stringBuilder.append(" AND d.id in (").append(StringUtils.join(delivery.getIds(),",")).append(")");
        }
        stringBuilder.append(" AND d.repositoryId = :repositoryId AND d.`status` = 0 ");
        stringBuilder.append(" GROUP BY d.id");
//        stringBuilder.append(" HAVING d.num > SUM(IFNULL(l.num,0)) ");
        return stringBuilder;
    }

    /**
     * 获取收货加出库列表
     */
    public List<PrItemDelivery> getItemDeliveryList(PrItemDelivery delivery) {
        StringBuilder stringBuilder = buildSql(delivery);
        return super.get(stringBuilder.toString(),delivery);
    }

    public Page<PrItemDelivery> getItemDeliveryList(PrItemDelivery delivery, Pageable pageable) {
        StringBuilder stringBuilder = buildSql(delivery);
        return super.get(stringBuilder.toString(),delivery,pageable);
    }

    public List<PrItemDelivery> receiveOutBillListData(PrItemDelivery delivery) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT outStorageNo,NULL AS inStorageNum, billReceiveDate,billReceiver,NULL AS inStorageSumPrice, t.sumPrice outStorageSumPrice FROM pr_item_out_storage o ");
        stringBuilder.append(" INNER JOIN (SELECT SUM(ol.sumPrice) sumPrice,outStorageId FROM pr_item_out_storage_list ol GROUP BY  ol.outStorageId ) t ON t.outStorageId = o.id ");
        stringBuilder.append(" WHERE repositoryId=:repositoryId and billReceiveDate = :billReceiveDate ");
        return super.get(stringBuilder.toString(),delivery);
    }

    public List<PrItemDelivery> receiveInBillListData(PrItemDelivery delivery) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT NULL AS outStorageNo,inStorageNum,billReceiveDate,billReceiver,t.sumPrice inStorageSumPrice,NULL AS outStorageSumPrice FROM pr_item_in_storage i INNER JOIN pr_repository r ON i.projectId = r.projectId ");
        stringBuilder.append(" INNER JOIN (SELECT SUM(inStorageMoney) sumPrice,inStorageId FROM pr_item_in_storage_list GROUP BY inStorageId) t ON t.inStorageId = i.id ");
        stringBuilder.append(" WHERE  r.id = :repositoryId and billReceiveDate = :billReceiveDate ");

        return super.get(stringBuilder.toString(),delivery);
    }
}
