package com.huayu.market.service;

import com.huayu.market.dao.MarketCostCompanyBudgetDao;
import com.huayu.market.domain.MarketCostCompanyBudget;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 所属公司年度预算
 *
 * @author ZXL 2017-06-15 14:21
 **/
@Service
public class MarketCostCompanyBudgetService {

    @Autowired
    private MarketCostCompanyBudgetDao marketCostCompanyBudgetDao;

    /**
     * 获取是否已有数据
     * @param companyId
     * @param year
     * @return
     */
    public MarketCostCompanyBudget getOneByIdAndYear(Integer companyId,Integer year){
        MarketCostCompanyBudget marketCostCompanyBudget = new MarketCostCompanyBudget();
        marketCostCompanyBudget.setCompanyId(companyId);
        marketCostCompanyBudget.setYear(year);
        return marketCostCompanyBudgetDao.getOneByIdAndYear(marketCostCompanyBudget);
    }

    public MarketCostCompanyBudget createDomain(Integer companyId, Integer year, User user){
        MarketCostCompanyBudget marketCostCompanyBudget = new MarketCostCompanyBudget();
        marketCostCompanyBudget.setBudgetId(marketCostCompanyBudgetDao.getKey("market_cost_company_budget",marketCostCompanyBudget));
        marketCostCompanyBudget.setCompanyId(companyId);
        marketCostCompanyBudget.setYear(year);
        marketCostCompanyBudget.setCurrentMoney(new BigDecimal("0"));
        marketCostCompanyBudget.setDefaultMoney(new BigDecimal("0"));
        marketCostCompanyBudget.setAdjustMoney(new BigDecimal("0"));
        marketCostCompanyBudget.setFreezeMoney(new BigDecimal("0"));
        marketCostCompanyBudget.setUsedMoney(new BigDecimal("0"));
        marketCostCompanyBudget.setRestoreMoney(new BigDecimal("0"));
        marketCostCompanyBudget.setCreateUserId(user.getUserId());
        marketCostCompanyBudget.setCreateDate(new Date());
        return  marketCostCompanyBudget;
    }

    /**
     * 新增数据
     * @param companyId
     * @param year
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int post(MarketCostCompanyBudget marketCostCompanyBudget) throws Exception{
        return marketCostCompanyBudgetDao.post(marketCostCompanyBudget);
    }

    @Transactional
    public int addDefaultMoney(BigDecimal defaultMoney,Long budgetId){
        MarketCostCompanyBudget marketCostCompanyBudget = new MarketCostCompanyBudget();
        marketCostCompanyBudget.setBudgetId(budgetId);
        marketCostCompanyBudget.setDefaultMoney(defaultMoney);
        return marketCostCompanyBudgetDao.addDefaultMoney(marketCostCompanyBudget);
    }

    /**
     * 更新立项未用完数据
     * @param pbId
     * @param restoreMoney
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int putToTimingOfApproval(Long budgetId,BigDecimal restoreMoney) throws Exception{
        MarketCostCompanyBudget marketCostCompanyBudget = new MarketCostCompanyBudget();
        marketCostCompanyBudget.setBudgetId(budgetId);
        marketCostCompanyBudget.setRestoreMoney(restoreMoney);
        return marketCostCompanyBudgetDao.putToTimingOfApproval(marketCostCompanyBudget);
    }



}
