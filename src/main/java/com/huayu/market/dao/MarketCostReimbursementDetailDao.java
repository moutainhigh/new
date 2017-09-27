package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostReimbursementDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 报销明细
 * @author ZXL 2017-08-30 17:40
 **/
@Repository
public class MarketCostReimbursementDetailDao extends BasePageDao<MarketCostReimbursementDetail,Serializable> {

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostContractSplitDetail
     * @return
     */
    public List<MarketCostReimbursementDetail> getAllByContractIdOfLedger(MarketCostReimbursementDetail marketCostReimbursementDetail){
        String sql = "SELECT l.*,a.secondName,a.threeName FROM market_cost_reimbursement_detail l LEFT JOIN (SELECT a.code,b.`name` secondName,a.`name` threeName FROM (SELECT * FROM market_cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=2) b ON a.parentId=b.id) a ON l.threeCode=a.`code` WHERE payId=:payId";
        return super.get(sql,marketCostReimbursementDetail);
    }

}
