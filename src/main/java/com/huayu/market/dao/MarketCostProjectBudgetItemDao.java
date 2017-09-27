package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostProjectBudgetItem;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目预算费用预算详细表
 * @author ZXL 2017-06-15 14:19
 **/
@Repository
public class MarketCostProjectBudgetItemDao extends BasePageDao<MarketCostProjectBudgetItem,Serializable> {

    /**
     * 项目实际发生
     * @param marketCostProjectBudgetItem
     * @return
     */
    public List<MarketCostProjectBudgetItem> getAllByProjectIdAndYearOfHappen(MarketCostProjectBudgetItem marketCostProjectBudgetItem){
        String sql = "SELECT item.*,m.* FROM market_cost_project_budget_item m LEFT JOIN (SELECT a.id threeId,a.`name` threeName,a.`code` threeCode,b.id secondId,b.`name` secondName,c.id firstId,c.`name` firstName FROM (SELECT * FROM market_cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=2) b ON a.parentId=b.id LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=1) c ON b.parentId=c.id) item ON m.`code`=item.threeCode WHERE m.projectId=:projectId AND m.`year`=:year ORDER BY item.firstId ASC,item.secondId ASC,item.threeId ASC";
        return super.get(sql,marketCostProjectBudgetItem);
    }

    public int[] batchAdd(List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList){
        String sql = "INSERT INTO market_cost_project_budget_item (pbiId,code,companyId,projectId,year,firstCode,secondCode,pbId,allPlanMoney,planMoney01,planMoney02,planMoney03,planMoney04,planMoney05,planMoney06,planMoney07,planMoney08,planMoney09,planMoney10,planMoney11,planMoney12,allHappenMoney,happenMoney01,happenMoney02,happenMoney03,happenMoney04,happenMoney05,happenMoney06,happenMoney07,happenMoney08,happenMoney09,happenMoney10,happenMoney11,happenMoney12,createUserId,createDate) " +
                "VALUES (:pbiId,:code,:companyId,:projectId,:year,:firstCode,:secondCode,:pbId,:allPlanMoney,:planMoney01,:planMoney02,:planMoney03,:planMoney04,:planMoney05,:planMoney06,:planMoney07,:planMoney08,:planMoney09,:planMoney10,:planMoney11,:planMoney12,:allHappenMoney,:happenMoney01,:happenMoney02,:happenMoney03,:happenMoney04,:happenMoney05,:happenMoney06,:happenMoney07,:happenMoney08,:happenMoney09,:happenMoney10,:happenMoney11,:happenMoney12,:createUserId,:createDate)";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(marketCostProjectBudgetItemList.toArray()));
    }

}
