package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostApplyRepairDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 营销费用发生登记明细
 * @author ZXL 2017-09-04 11:34
 **/
@Repository
public class MarketCostApplyRepairDetailDao extends BasePageDao<MarketCostApplyRepairDetail,Serializable>{

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostApplyRepairDetail
     * @return
     */
    public List<MarketCostApplyRepairDetail> getAllByPayIdOfLedger(MarketCostApplyRepairDetail marketCostApplyRepairDetail){
        String sql = "SELECT l.*,y.`name` companyName,t.`name` projectName,a.secondName,a.threeName FROM market_cost_apply_repair_detail l LEFT JOIN (SELECT a.code,b.`name` secondName,a.`name` threeName FROM (SELECT * FROM market_cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=2) b ON a.parentId=b.id) a ON l.threeCode=a.`code` LEFT JOIN market_cost_company y ON l.companyId=y.companyId LEFT JOIN market_cost_project t ON l.projectId=t.projectId WHERE payId=:payId";
        return super.get(sql,marketCostApplyRepairDetail);
    }

}
