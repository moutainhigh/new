package com.huayu.inventory.dao;

import com.huayu.inventory.domain.PrItemStatistics;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/29.
 */
@Repository
public class PrItemStatisticsDao extends BasePageDao<PrItemStatistics,Integer>{


    /**
     * 0 期初 1 期末
     * 统计期初，期末数量
     * @param entity
     * @return
     */
    public List<PrItemStatistics> getBeginOrEndStatisticsData(Integer type, PrItemStatistics entity){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT r.*,c.id categoryId,m.`name` materialName,c.`name` categoryName,pms.specification specificationName,cd.dictValue unit");//,IFNULL(b.sumPrice,0) balanceSumPrice,IFNULL(b.sumTax,0) balanceSumTax
        stringBuilder.append(" FROM (SELECT t.materialId,t.specificationId,t.repositoryId,t.receiveNum-IFNULL(t2.oslNum,0) num,t.tmpSumPrice+t.inStorageMoney-IFNULL(t2.oslSumPrice,0) sumPrice,t.tmpTax+t.inStorageTax-IFNULL(t2.oslTax,0) tax");
        stringBuilder.append(" FROM (SELECT SUM(t.receiveNum) receiveNum,SUM(t.inStorageMoney) inStorageMoney,SUM(t.inStorageTax) inStorageTax,SUM(t.tmpTax) tmpTax,SUM(t.tmpSumPrice) tmpSumPrice,t.materialId,t.specificationId,t.repositoryId FROM (");
        stringBuilder.append(" SELECT d.num -IFNULL(re.num, 0) receiveNum,SUM(IFNULL(isl.inStorageMoney,0)) inStorageMoney,SUM(IFNULL(isl.rateMoney,0)) inStorageTax,ROUND((d.num -IFNULL(re.num, 0)-SUM(IFNULL(isl.num,0)))* d.price/(1+d.taxRate * 0.01) * d.taxRate * 0.01,2) tmpTax,");
        stringBuilder.append(" ROUND((d.num -IFNULL(re.num, 0)-SUM(IFNULL(isl.num,0))) * d.price,2) tmpSumPrice,d.id,d.materialId,d.specificationId,d.repositoryId  FROM  pr_item_delivery d ");
        stringBuilder.append(" LEFT JOIN pr_item_in_storage_list isl ON isl.deliveryId = d.id ");
        stringBuilder.append(" LEFT JOIN pr_item_rejected re on re.deliveryId = d.id  AND re.returnedDate "+(type==0?"<":"<=")+":statisticsDate");
        stringBuilder.append(" WHERE d.repositoryId = :repositoryId  AND d.`status` = 0  AND d.receiptDate "+(type==0?"<":"<=")+" :statisticsDate  GROUP BY d.id )t GROUP BY t.materialId,t.specificationId ) t ");
        stringBuilder.append(" LEFT JOIN (SELECT os.repositoryId,osl.materialId,osl.specificationId,SUM(IFNULL(osl.num,0)) oslNum,SUM(IFNULL(osl.sumPrice-osl.excTaxSumPrice,0)) oslTax,SUM(IFNULL(osl.sumPrice,0)) oslSumPrice FROM pr_item_out_storage_list osl ");
        stringBuilder.append(" INNER JOIN pr_item_out_storage os ON os.id = osl.outStorageId AND os.outStorageDate "+(type==0?"<":"<=")+" :statisticsDate WHERE repositoryId =:repositoryId GROUP BY osl.materialId,osl.specificationId ");
        stringBuilder.append(" ) t2 ON  t2.materialId = t.materialId AND t2.specificationId = t.specificationId WHERE t.receiveNum-IFNULL(t2.oslNum,0)>=0 ) r ");
        stringBuilder.append(" INNER JOIN pr_material m ON m.id = r.materialId ");
        stringBuilder.append(" INNER JOIN pr_material_category c ON c.id = m.categoryId");
        stringBuilder.append(" INNER JOIN pr_material_specification pms on pms.id = r.specificationId");
        stringBuilder.append(" INNER JOIN common_dict cd ON cd.dictKey = m.unit and cd.dictGroup='unit'");
        stringBuilder.append(" WHERE r.num!=0 OR r.sumPrice!=0 OR r.tax!=0 ORDER BY c.id");
        return super.get(stringBuilder.toString(),entity);
    }



    /**
     * 本期增加
     * @param entity
     * @return
     */
    public List<PrItemStatistics> getAddStatisticsData(PrItemStatistics entity){
        StringBuilder stringBuilder = new StringBuilder("SELECT r.addSumPrice,r.addNum,r.tax,r.materialId,r.specificationId,c.id categoryId,m.`name` materialName,c.`name` categoryName,pms.specification specificationName,cd.dictValue unit FROM (");
        stringBuilder.append("SELECT SUM(t.tmpSumPrice)+ SUM(t.inStorageMoney) addSumPrice,SUM(t.addNum) addNum,SUM(t.tax) tax,t.materialId,t.specificationId FROM (");
        stringBuilder.append("SELECT d.num-IFNULL(re.num, 0) addNum, SUM(IFNULL(isl.inStorageMoney,0)) inStorageMoney,");
        stringBuilder.append("SUM(IFNULL(isl.rateMoney,0)) + ROUND((d.num -IFNULL(re.num, 0)-SUM(IFNULL(isl.num,0))) * d.price /(1+d.taxRate * 0.01) * d.taxRate *0.01, 2) tax,");
        stringBuilder.append("ROUND((d.num -IFNULL(re.num, 0)-SUM(IFNULL(isl.num,0))) * d.price,2) tmpSumPrice,");
        stringBuilder.append("d.id,d.materialId,d.specificationId");
        stringBuilder.append(" FROM  pr_item_delivery d");
        stringBuilder.append(" LEFT JOIN pr_item_in_storage_list isl ON isl.deliveryId = d.id");
        stringBuilder.append(" LEFT JOIN pr_item_rejected re on re.deliveryId = d.id AND re.returnedDate >=:startDate AND re.returnedDate <= :endDate ");
        stringBuilder.append(" WHERE d.repositoryId = :repositoryId  AND d.`status` = 0  AND d.receiptDate >=:startDate AND d.receiptDate <= :endDate ");
        stringBuilder.append(" GROUP BY d.id ) t ");
        stringBuilder.append(" GROUP BY t.materialId,t.specificationId ) r ");
        stringBuilder.append(" INNER JOIN pr_material m ON m.id = r.materialId");
        stringBuilder.append(" INNER JOIN pr_material_category c ON c.id = m.categoryId");
        stringBuilder.append(" INNER JOIN pr_material_specification pms on pms.id = r.specificationId");
        stringBuilder.append(" INNER JOIN common_dict cd ON cd.dictKey = m.unit and cd.dictGroup='unit' WHERE r.addSumPrice !=0 OR r.addNum !=0 OR r.tax !=0 ORDER BY c.id");
        return super.get(stringBuilder.toString(),entity);
    }

    /**
     * 本期减少
     * @param entity
     * @return
     */
    public List<PrItemStatistics> getReduceStatisticsData(PrItemStatistics entity){
        StringBuilder stringBuilder = new StringBuilder("SELECT l.materialId,l.specificationId,c.`name` categoryName,m.`name` materialName,pms.specification specificationName," );
        stringBuilder.append("c.id categoryId,SUM(l.num) reduceNum,SUM(l.sumPrice) reduceSumPrice,");
        stringBuilder.append("SUM(l.excTaxSumPrice) reduceSumExcTaxPrice,cd.dictValue unit");//,SUM(IFNULL(b.sumPrice,0)) balanceSumPrice,SUM(IFNULL(b.sumTax,0)) balanceSumTax
        stringBuilder.append(" FROM  pr_item_out_storage os");
        stringBuilder.append(" INNER JOIN pr_item_out_storage_list l ON l.outStorageId = os.id");
        stringBuilder.append(" INNER JOIN pr_material m ON m.id = l.materialId");
        stringBuilder.append(" INNER JOIN pr_material_category c ON c.id = m.categoryId");
        stringBuilder.append(" INNER JOIN pr_material_specification pms on pms.id = l.specificationId");
        stringBuilder.append(" INNER JOIN common_dict cd ON cd.dictKey = m.unit and cd.dictGroup='unit' ");
        stringBuilder.append(" WHERE os.repositoryId = :repositoryId AND os.outStorageDate >=:startDate AND os.outStorageDate<= :endDate ");
        stringBuilder.append(" GROUP BY l.materialId,l.specificationId  HAVING reduceNum!=0 OR reduceSumPrice!=0 OR reduceSumExcTaxPrice!=0 ORDER BY c.id");
        return super.get(stringBuilder.toString(),entity);
    }

    /**
     * 调价
     * @param type
     * @param entity
     * @return
     */
    public List<PrItemStatistics> getBalanceStatisticsData(Integer type,PrItemStatistics entity){
        StringBuilder stringBuilder = new StringBuilder("SELECT m.categoryId,t.materialId,t.specificationId,t.repositoryId,SUM(t.sumPrice) balanceSumPrice,SUM(t.sumTax) balanceSumTax FROM pr_item_balance t ");
        stringBuilder.append(" INNER JOIN pr_material m ON m.id = t.materialId ");
        if(0 == type){
            stringBuilder.append("WHERE t.balanceDate < :statisticsDate");
        }else if(1 == type){
            stringBuilder.append("WHERE t.balanceDate >= :startDate AND t.balanceDate <= :endDate ");
        }else{
            stringBuilder.append("WHERE t.balanceDate <= :statisticsDate");
        }
        stringBuilder.append(" AND repositoryId=:repositoryId GROUP BY t.materialId,t.specificationId,t.repositoryId ");
        return super.get(stringBuilder.toString(),entity);
    }

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSource) {
        String sql  = "INSERT INTO pr_item_statistics " +
                "(id, repositoryId, startDate, endDate, categoryId, categoryName, materialId, materialName, specificationId, specificationName, unit, startPrice, startNum, startSumTax, startSumPrice, startSumExcTaxPrice, " +
                "addPrice, addNum, addSumTax, addSumPrice, addSumExcTaxPrice, reducePrice, reduceNum, reduceSumTax, reduceSumPrice, reduceSumExcTaxPrice, endPrice, endNum, " +
                "endSumTax, endSumPrice, endSumExcTaxPrice, status, createtime) " +
                "VALUES " +
                "(:id, :repositoryId, :startDate, :endDate, :categoryId, :categoryName, :materialId, :materialName, :specificationId, :specificationName, :unit, :startPrice, :startNum, :startSumTax, :startSumPrice, :startSumExcTaxPrice," +
                " :addPrice, :addNum, :addSumTax, :addSumPrice, :addSumExcTaxPrice, :reducePrice, :reduceNum, :reduceSumTax, :reduceSumPrice, :reduceSumExcTaxPrice, :endPrice, :endNum," +
                " :endSumTax, :endSumPrice, :endSumExcTaxPrice, :status, :createtime)";
        return super.batchUpdate(sql,sqlParameterSource);
    }

    public List<PrItemStatistics> getStatisticsData(PrItemStatistics entity){
        String sql = "SELECT s.*,SUM(b.sumTax) balanceSumTax,SUM(b.sumPrice) balanceSumPrice " +
                "FROM pr_item_statistics s " +
                "LEFT JOIN pr_item_balance b ON b.materialId = s.materialId AND b.specificationId = s.specificationId AND b.repositoryId =s.repositoryId " +
                "WHERE status=0 AND s.repositoryId = :repositoryId AND s.startDate=:startDate AND s.endDate=:endDate GROUP BY s.categoryId,s.materialId,s.specificationId";
        return super.get(sql,entity);
    }
}
