package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContractTempDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 营销合同审批表-导入临时表
 * @author ZXL 2017-08-23 16:19
 **/
@Repository
public class MarketCostContractTempDetailDao extends BasePageDao<MarketCostContractTempDetail,Serializable>{

    public MarketCostContractTempDetail getOneByContractId(MarketCostContractTempDetail marketCostContractTempDetail){
        String sql = "SELECT contractId,SUM(contractMoney) contractMoney FROM market_cost_contract_temp_detail WHERE contractId=:contractId";
        return super.getOne(sql,marketCostContractTempDetail);
    }

    public List<MarketCostContractTempDetail> getByContractIdForSecondCode(MarketCostContractTempDetail marketCostContractTempDetail){
        String sql = "SELECT secondCode,SUM(contractMoney) contractMoney FROM market_cost_contract_temp_detail WHERE contractId=:contractId GROUP BY secondCode";
        return super.get(sql,marketCostContractTempDetail);
    }

    public List<MarketCostContractTempDetail> getByContractId(MarketCostContractTempDetail marketCostContractTempDetail){
        String sql = "SELECT * FROM market_cost_contract_temp_detail WHERE contractId=:contractId  ORDER BY applyDate ASC";
        return super.get(sql,marketCostContractTempDetail);
    }

    public int put(MarketCostContractTempDetail marketCostContractTempDetail){
        String sql = "UPDATE market_cost_contract_temp_detail SET contractId=:contractId WHERE contractCode=:contractCode";
        return super.put(sql,marketCostContractTempDetail);
    }

}
