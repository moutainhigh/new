package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostCompanyBudget;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 所属公司年度预算
 *
 * @author ZXL 2017-06-15 14:15
 **/
@Repository
public class MarketCostCompanyBudgetDao extends BasePageDao<MarketCostCompanyBudget,Serializable>{

    /**
     * 获取是否已有数据
     * @param marketCostProjectBudget
     * @return
     */
    public MarketCostCompanyBudget getOneByIdAndYear(MarketCostCompanyBudget marketCostProjectBudget){
        String sql = "SELECT * FROM market_cost_company_budget WHERE companyId=:companyId AND `year`=:year";
        return super.getOne(sql,marketCostProjectBudget);
    }

    public int addDefaultMoney(MarketCostCompanyBudget marketCostCompanyBudget){
        String sql = "UPDATE market_cost_company_budget SET currentMoney=currentMoney+:defaultMoney,defaultMoney=defaultMoney+:defaultMoney WHERE budgetId=:budgetId";
        return super.put(sql,marketCostCompanyBudget);
    }

    /**
     * 更新立项未用完数据
     * @param marketCostProjectBudget
     * @return
     */
    public int putToTimingOfApproval(MarketCostCompanyBudget marketCostCompanyBudget){
        String sql = "UPDATE market_cost_company_budget SET usedMoney=usedMoney-:restoreMoney,restoreMoney=restoreMoney+:restoreMoney WHERE budgetId=:budgetId";
        return super.put(sql,marketCostCompanyBudget);
    }

}
