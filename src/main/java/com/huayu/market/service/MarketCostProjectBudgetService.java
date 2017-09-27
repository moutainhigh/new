package com.huayu.market.service;

import com.huayu.market.dao.MarketCostProjectBudgetDao;
import com.huayu.market.domain.MarketCostProject;
import com.huayu.market.domain.MarketCostProjectBudget;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目明细
 *
 * @author ZXL 2017-06-15 14:20
 **/
@Service
public class MarketCostProjectBudgetService {

    @Autowired
    private MarketCostProjectBudgetDao marketCostProjectBudgetDao;

    /**
     * 条件获取预算数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public Page<MarketCostProjectBudget> getToBudget(Pageable pageable, MarketCostProjectBudget marketCostProjectBudget){
        return marketCostProjectBudgetDao.getToBudget(pageable,marketCostProjectBudget);
    }

    /**
     * 条件获取预算数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public List<MarketCostProjectBudget> getToBudget(MarketCostProjectBudget marketCostProjectBudget){
        return marketCostProjectBudgetDao.getToBudget(marketCostProjectBudget);
    }

    /**
     * 条件获取实际发生数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public List<MarketCostProjectBudget> getToHappen(MarketCostProjectBudget marketCostProjectBudget){
        return marketCostProjectBudgetDao.getToHappen(marketCostProjectBudget);
    }

    /**
     * 条件获取费效分析数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public List<MarketCostProjectBudget> getToEfficiency(MarketCostProjectBudget marketCostProjectBudget){
        return marketCostProjectBudgetDao.getToEfficiency(marketCostProjectBudget);
    }

    /**
     * 获取是否已有数据
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    public MarketCostProjectBudget getOneByIdAndYearAndCompanyId(Long projectId,Integer companyId,Integer year){
        MarketCostProjectBudget marketCostProjectBudget = new MarketCostProjectBudget();
        marketCostProjectBudget.setProjectId(projectId);
        marketCostProjectBudget.setCompanyId(companyId);
        marketCostProjectBudget.setYear(year);
        return marketCostProjectBudgetDao.getOneByIdAndYearAndCompanyId(marketCostProjectBudget);
    }

    /**
     * 新增数据
     * @param companyId
     * @param year
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int post(MarketCostProjectBudget marketCostProjectBudget) throws Exception{
        return marketCostProjectBudgetDao.post(marketCostProjectBudget);
    }

    /**
     * 新增数据
     * @param companyId
     * @param year
     * @param user
     * @return
     */
    public MarketCostProjectBudget createDomain(Long projectId, Integer companyId, Integer year, User user, MarketCostProject marketCostProject, Long budgetId){
        MarketCostProjectBudget marketCostProjectBudget = new MarketCostProjectBudget();
        marketCostProjectBudget.setPbId(marketCostProjectBudgetDao.getKey("market_cost_project_budget",marketCostProjectBudget));
        marketCostProjectBudget.setStatus((short)2);
        marketCostProjectBudget.setCompanyId(companyId);
        marketCostProjectBudget.setProjectId(projectId);
        marketCostProjectBudget.setYear(year);
        marketCostProjectBudget.setType(marketCostProject.getType());
        marketCostProjectBudget.setBudgetId(budgetId);
        marketCostProjectBudget.setCurrentMoney(new BigDecimal("0"));
        marketCostProjectBudget.setDefaultMoney(new BigDecimal("0"));
        marketCostProjectBudget.setAdjustMoney(new BigDecimal("0"));
        marketCostProjectBudget.setFreezeMoney(new BigDecimal("0"));
        marketCostProjectBudget.setUsedMoney(new BigDecimal("0"));
        marketCostProjectBudget.setRestoreMoney(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney01(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney02(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney03(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney04(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney05(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney06(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney07(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney08(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney09(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney10(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney11(new BigDecimal("0"));
        marketCostProjectBudget.setHappenMoney12(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney01(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney02(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney03(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney04(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney05(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney06(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney07(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney08(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney09(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney10(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney11(new BigDecimal("0"));
        marketCostProjectBudget.setSignMoney12(new BigDecimal("0"));
        marketCostProjectBudget.setCreateUserId(user.getUserId());
        marketCostProjectBudget.setCreateDate(new Date());
        return marketCostProjectBudget;
    }

    /**
     * 编制预算修改项目明细预算
     * @param defaultMoney
     * @param pbId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addDefaultMoney(BigDecimal defaultMoney, Long pbId)throws Exception{
        MarketCostProjectBudget marketCostProjectBudget = new MarketCostProjectBudget();
        marketCostProjectBudget.setPbId(pbId);
        marketCostProjectBudget.setDefaultMoney(defaultMoney);
        return marketCostProjectBudgetDao.addDefaultMoney(marketCostProjectBudget);
    }

    /**
     * 更新立项未用完数据
     * @param pbId
     * @param restoreMoney
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int putToTimingOfApproval(Long pbId,BigDecimal restoreMoney) throws Exception{
        MarketCostProjectBudget marketCostProjectBudget = new MarketCostProjectBudget();
        marketCostProjectBudget.setPbId(pbId);
        marketCostProjectBudget.setRestoreMoney(restoreMoney);
        return marketCostProjectBudgetDao.putToTimingOfApproval(marketCostProjectBudget);
    }

}
