package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostProjectBudgetItemLog;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目预算费用预算更新记录表
 * @author ZXL 2017-07-04 13:42
 **/
@Repository
public class MarketCostProjectBudgetItemLogDao extends BasePageDao<MarketCostProjectBudgetItemLog,Serializable> {

    /**
     * 批量添加项目预算费用预算更新记录
     * @param marketCostProjectBudgetItemLogs
     * @return
     */
    public int[] batchPost(List<MarketCostProjectBudgetItemLog> marketCostProjectBudgetItemLogs){
        String sql = "INSERT INTO market_cost_project_budget_item_log (pbiId, code, companyId, projectId, year, firstCode, secondCode, pbId, allPlanMoney, planMoney01, planMoney02, planMoney03, planMoney04, planMoney05, planMoney06, planMoney07, planMoney08, planMoney09, planMoney10, planMoney11, planMoney12, adjustMoney, adjustMoney01, adjustMoney02, adjustMoney03, adjustMoney04, adjustMoney05, adjustMoney06, adjustMoney07, adjustMoney08, adjustMoney09, adjustMoney10, adjustMoney11, adjustMoney12, updateMoney, createUserId) " +
                "VALUES (pbiId, code, companyId, projectId, year, firstCode, secondCode, pbId, allPlanMoney, planMoney01, planMoney02, planMoney03, planMoney04, planMoney05, planMoney06, planMoney07, planMoney08, planMoney09, planMoney10, planMoney11, planMoney12, adjustMoney, adjustMoney01, adjustMoney02, adjustMoney03, adjustMoney04, adjustMoney05, adjustMoney06, adjustMoney07, adjustMoney08, adjustMoney09, adjustMoney10, adjustMoney11, adjustMoney12, updateMoney, createUserId)";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(marketCostProjectBudgetItemLogs.toArray()));
    }

}
