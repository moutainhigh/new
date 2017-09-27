package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContractSplitDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 营销合同审批表实际发生拆分
 * @author ZXL 2017-08-23 17:09
 **/
@Repository
public class MarketCostContractSplitDetailDao extends BasePageDao<MarketCostContractSplitDetail,Serializable>{

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostContractSplitDetail
     * @return
     */
    public List<MarketCostContractSplitDetail> getAllByContractIdOfLedger(MarketCostContractSplitDetail marketCostContractSplitDetail){
        String sql = "SELECT l.*,a.secondName,a.threeName FROM market_cost_contract_split_detail l LEFT JOIN (SELECT a.code,b.`name` secondName,a.`name` threeName FROM (SELECT * FROM market_cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=2) b ON a.parentId=b.id) a ON l.threeCode=a.`code` WHERE contractId=:contractId";
        return super.get(sql,marketCostContractSplitDetail);
    }

    /*导入数据*/

    /**
     * 导入数据-自定义添加数据
     * @param marketCostContractSplitDetail
     * @return
     */
    public int definitionPostOfImport(MarketCostContractSplitDetail marketCostContractSplitDetail){
        String sql = "insert into market_cost_contract_split_detail (`type`, `sort`, `contractId`, `reimbursementId`, `secondCode`, `threeCode`, `projectId`, `pid`, `year`, `month`, `currentDate`, `money`) VALUES " +
                " (:type,:sort,:contractId,:reimbursementId,:secondCode,:threeCode,:projectId,:pid,:year,:month,:currentDate,:money)";
        return super.post(sql,marketCostContractSplitDetail);
    }

}
