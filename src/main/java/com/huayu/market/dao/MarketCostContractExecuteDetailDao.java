package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContractExecuteDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 框架合同执行单/量价确认单明细
 * @author ZXL 2017-08-24 14:57
 **/
@Repository
public class MarketCostContractExecuteDetailDao extends BasePageDao<MarketCostContractExecuteDetail,Serializable>{

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostContractExecuteDetail
     * @return
     */
    public List<MarketCostContractExecuteDetail> getAllByContractIdOfLedger(MarketCostContractExecuteDetail marketCostContractExecuteDetail){
        String sql = "SELECT a.title executeTitle,l.* FROM (SELECT CASE WHEN m.affirmId IS NULL THEN e.executeId ELSE m.affirmId END executeId,e.title FROM (SELECT * FROM market_cost_contract_execute WHERE `status`=2) e LEFT JOIN (SELECT * FROM market_cost_contract_affirm WHERE `status`=2) m ON e.executeId=m.executeId WHERE e.contractId=:executeId) a LEFT JOIN market_cost_contract_execute_detail l ON a.executeId=l.executeId";
        return super.get(sql,marketCostContractExecuteDetail);
    }

    public int definitionPostOfImport(MarketCostContractExecuteDetail marketCostContractExecuteDetail){
        String sql = "insert into market_cost_contract_execute_detail (`detailId`,`type`,`sort`,`projectId`,`executeId`,`secondCode`,`threeCode`,`reason`,`happenDate`,`year`,`month`,`price`,`number`,`finalMoney`,`pid`,`threeName`) VALUES " +
                "(:detailId,:type,:sort,:projectId,:executeId,:secondCode,:threeCode,:reason,:happenDate,:year,:month,:price,:number,:finalMoney,:pid,:threeName)";
        return super.post(sql,marketCostContractExecuteDetail);
    }

}
