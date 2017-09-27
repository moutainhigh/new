package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContractMoney;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 报销明细
 * @author ZXL 2017-08-23 17:05
 **/
@Repository
public class MarketCostContractMoneyDao extends BasePageDao<MarketCostContractMoney,Serializable>{

    /*导入数据*/

    /**
     * 导入数据-自定义添加数据
     * @param marketCostContractMoney
     * @return
     */
    public int definitionPostOfImport(MarketCostContractMoney marketCostContractMoney){
        String sql = "insert into market_cost_contract_money (`did`, `applyMoney`, `payId`, `secondCode`, `projectId`, `pid`) VALUES " +
                " (:did,:applyMoney,:payId,:secondCode,:projectId,:pid)";
        return super.post(sql,marketCostContractMoney);
    }

}
