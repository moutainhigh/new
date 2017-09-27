package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContractExecute;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 框架合同执行单
 * @author ZXL 2017-08-23 15:03
 **/
@Repository
public class MarketCostContractExecuteDao extends BasePageDao<MarketCostContractExecute,Serializable>{

    public int definitionPostOfImport(MarketCostContractExecute marketCostContractExecute){
        String sql = "insert into market_cost_contract_execute (`executeId`,`status`,`useStatus`,`title`,`serialsNumber`,`contractId`,`contractName`,`contractCode`,`projectId`,`pid`,`applyDate`,`departmentId`,`agentUserId`, `agentName`,`applyMoney`,`remark`) VALUES " +
                "(:executeId,:status,:useStatus,:title,:serialsNumber,:contractId,:contractName,:contractCode,:projectId,:pid,:applyDate,:departmentId,:agentUserId,:agentName,:applyMoney,:remark)";
        return super.post(sql,marketCostContractExecute);
    }

}
