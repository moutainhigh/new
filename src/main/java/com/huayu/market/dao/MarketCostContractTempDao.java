package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContractTemp;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 营销合同审批表-导入临时表
 * @author ZXL 2017-08-23 15:52
 **/
@Repository
public class MarketCostContractTempDao extends BasePageDao<MarketCostContractTemp,Serializable>{

    public List<MarketCostContractTemp> get(MarketCostContractTemp marketCostContractTemp){
        String sql = "SELECT * FROM market_cost_contract_temp";
        return super.get(sql,marketCostContractTemp);
    }

    public int putContractId(MarketCostContractTemp marketCostContractTemp){
        String sql = "UPDATE market_cost_contract_temp SET contractId=:contractId WHERE id=:id";
        return super.put(sql,marketCostContractTemp);
    }

}
