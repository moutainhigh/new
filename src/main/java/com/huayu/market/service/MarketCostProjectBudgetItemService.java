package com.huayu.market.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.market.dao.MarketCostProjectBudgetItemDao;
import com.huayu.market.domain.*;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目预算费用预算详细表
 *
 * @author ZXL 2017-06-15 14:20
 **/
@Service
public class MarketCostProjectBudgetItemService {

    @Autowired
    private MarketCostProjectBudgetItemDao marketCostProjectBudgetItemDao;
    @Autowired
    private MarketCostCompanyBudgetService marketCostCompanyBudgetService;
    @Autowired
    private MarketCostItemService marketCostItemService;
    @Autowired
    private MarketCostProjectBudgetService marketCostProjectBudgetService;
    @Autowired
    private MarketCostProjectService marketCostProjectService;

    /**
     * 项目实际发生-全部
     * @param projectId
     * @param year
     * @return
     */
    public List<MarketCostProjectBudgetItem> getAllByProjectIdAndYearOfHappen(Long projectId,Integer year){
        MarketCostProjectBudgetItem marketCostProjectBudgetItem = new MarketCostProjectBudgetItem();
        marketCostProjectBudgetItem.setProjectId(projectId);
        marketCostProjectBudgetItem.setYear(year);
        return marketCostProjectBudgetItemDao.getAllByProjectIdAndYearOfHappen(marketCostProjectBudgetItem);
    }

    /**
     * 项目实际发生-层级
     * @param projectId
     * @param year
     * @return
     */
    public List<MarketCostProjectBudgetItem> getAllForHierarchyOfHappen(Long projectId,Integer year) throws Exception{
        List<MarketCostProjectBudgetItem> firstList = new ArrayList<>();
        List<MarketCostProjectBudgetItem> secondList = new ArrayList<>();
        List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList = this.getAllByProjectIdAndYearOfHappen(projectId,year);
        Integer firstId = 0;
        MarketCostProjectBudgetItem marketCostProjectBudgetItem = new MarketCostProjectBudgetItem();
        for (MarketCostProjectBudgetItem item:marketCostProjectBudgetItemList){
            if (firstId!=item.getSecondId()){
                firstId = item.getSecondId();
                marketCostProjectBudgetItem = (MarketCostProjectBudgetItem)item.clone();
                secondList.add(marketCostProjectBudgetItem);
            } else {
                marketCostProjectBudgetItem.setAllHappenMoney(marketCostProjectBudgetItem.getAllHappenMoney().add(item.getAllHappenMoney()));
                marketCostProjectBudgetItem.setHappenMoney01(marketCostProjectBudgetItem.getHappenMoney01().add(item.getHappenMoney01()));
                marketCostProjectBudgetItem.setHappenMoney02(marketCostProjectBudgetItem.getHappenMoney02().add(item.getHappenMoney02()));
                marketCostProjectBudgetItem.setHappenMoney03(marketCostProjectBudgetItem.getHappenMoney03().add(item.getHappenMoney03()));
                marketCostProjectBudgetItem.setHappenMoney04(marketCostProjectBudgetItem.getHappenMoney04().add(item.getHappenMoney04()));
                marketCostProjectBudgetItem.setHappenMoney05(marketCostProjectBudgetItem.getHappenMoney05().add(item.getHappenMoney05()));
                marketCostProjectBudgetItem.setHappenMoney06(marketCostProjectBudgetItem.getHappenMoney06().add(item.getHappenMoney06()));
                marketCostProjectBudgetItem.setHappenMoney07(marketCostProjectBudgetItem.getHappenMoney07().add(item.getHappenMoney07()));
                marketCostProjectBudgetItem.setHappenMoney08(marketCostProjectBudgetItem.getHappenMoney08().add(item.getHappenMoney08()));
                marketCostProjectBudgetItem.setHappenMoney09(marketCostProjectBudgetItem.getHappenMoney09().add(item.getHappenMoney09()));
                marketCostProjectBudgetItem.setHappenMoney10(marketCostProjectBudgetItem.getHappenMoney10().add(item.getHappenMoney10()));
                marketCostProjectBudgetItem.setHappenMoney11(marketCostProjectBudgetItem.getHappenMoney11().add(item.getHappenMoney11()));
                marketCostProjectBudgetItem.setHappenMoney12(marketCostProjectBudgetItem.getHappenMoney12().add(item.getHappenMoney12()));
            }
            marketCostProjectBudgetItem.getMarketCostProjectBudgetItemList().add(item);
        }
        for (MarketCostProjectBudgetItem item:secondList){
            if (firstId!=item.getFirstId()){
                firstId = item.getFirstId();
                marketCostProjectBudgetItem = (MarketCostProjectBudgetItem)item.clone();
                firstList.add(marketCostProjectBudgetItem);
            } else {
                marketCostProjectBudgetItem.setAllHappenMoney(marketCostProjectBudgetItem.getAllHappenMoney().add(item.getAllHappenMoney()));
                marketCostProjectBudgetItem.setHappenMoney01(marketCostProjectBudgetItem.getHappenMoney01().add(item.getHappenMoney01()));
                marketCostProjectBudgetItem.setHappenMoney02(marketCostProjectBudgetItem.getHappenMoney02().add(item.getHappenMoney02()));
                marketCostProjectBudgetItem.setHappenMoney03(marketCostProjectBudgetItem.getHappenMoney03().add(item.getHappenMoney03()));
                marketCostProjectBudgetItem.setHappenMoney04(marketCostProjectBudgetItem.getHappenMoney04().add(item.getHappenMoney04()));
                marketCostProjectBudgetItem.setHappenMoney05(marketCostProjectBudgetItem.getHappenMoney05().add(item.getHappenMoney05()));
                marketCostProjectBudgetItem.setHappenMoney06(marketCostProjectBudgetItem.getHappenMoney06().add(item.getHappenMoney06()));
                marketCostProjectBudgetItem.setHappenMoney07(marketCostProjectBudgetItem.getHappenMoney07().add(item.getHappenMoney07()));
                marketCostProjectBudgetItem.setHappenMoney08(marketCostProjectBudgetItem.getHappenMoney08().add(item.getHappenMoney08()));
                marketCostProjectBudgetItem.setHappenMoney09(marketCostProjectBudgetItem.getHappenMoney09().add(item.getHappenMoney09()));
                marketCostProjectBudgetItem.setHappenMoney10(marketCostProjectBudgetItem.getHappenMoney10().add(item.getHappenMoney10()));
                marketCostProjectBudgetItem.setHappenMoney11(marketCostProjectBudgetItem.getHappenMoney11().add(item.getHappenMoney11()));
                marketCostProjectBudgetItem.setHappenMoney12(marketCostProjectBudgetItem.getHappenMoney12().add(item.getHappenMoney12()));
            }
            marketCostProjectBudgetItem.getMarketCostProjectBudgetItemListCopy().add(item);
        }
        return firstList;
    }

    /**
     * 批量修改数据
     * 表：
     * marketCostProjectBudget market_cost_project_budget 项目明细
     * marketCostCompanyBudget market_cost_company_budget 所属公司年度预算
     * marketCostProjectBudgetItem market_cost_project_budget_item 项目预算费用预算详细表
     * @param projectId
     * @param companyId
     * @param year
     * @param marketCostProjectBudgetItemList
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchPut(Long projectId,Integer companyId,Integer year,List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList) throws Exception{
        boolean oldCompany = true;
        boolean oldProject = true;
        MarketCostProjectBudget marketCostProjectBudget = marketCostProjectBudgetService.getOneByIdAndYearAndCompanyId(projectId, companyId, year);
        if (null!=marketCostProjectBudget){
            throw new CustomException("该项目已编制年度营销费用");
        } else {
            oldProject = false;
        }
        User user = SpringSecurityUtil.getUser();
        MarketCostCompanyBudget marketCostCompanyBudget = marketCostCompanyBudgetService.getOneByIdAndYear(companyId, year);
        if (null==marketCostCompanyBudget){
            marketCostCompanyBudget = marketCostCompanyBudgetService.createDomain(companyId,year,user);
            oldCompany = false;
        }
        MarketCostProject marketCostProject = marketCostProjectService.getOne(projectId);
        marketCostProjectBudget = marketCostProjectBudgetService.createDomain(projectId, companyId, year, user, marketCostProject, marketCostCompanyBudget.getBudgetId());
        BigDecimal defaultMoney = new BigDecimal("0");
        for (MarketCostProjectBudgetItem item:marketCostProjectBudgetItemList){
                item = this.createDomain(item,companyId,projectId,year,marketCostProjectBudget.getPbId(),user);
                defaultMoney=defaultMoney.add(item.getAllPlanMoney());
        }
        this.batchAdd(marketCostProjectBudgetItemList);
        if (oldCompany){
            if (marketCostProject.getIsOwn()==1){
                marketCostCompanyBudgetService.addDefaultMoney(defaultMoney,marketCostCompanyBudget.getBudgetId());
            }
        } else {
            if (marketCostProject.getIsOwn()==1){
                marketCostCompanyBudget.setDefaultMoney(defaultMoney);
                marketCostCompanyBudget.setCurrentMoney(defaultMoney);
            }
            marketCostCompanyBudgetService.post(marketCostCompanyBudget);
        }
        if (oldProject){
            marketCostProjectBudgetService.addDefaultMoney(defaultMoney,marketCostProjectBudget.getPbId());
        } else {
            marketCostProjectBudget.setDefaultMoney(defaultMoney);
            marketCostProjectBudget.setCurrentMoney(defaultMoney);
            marketCostProjectBudgetService.post(marketCostProjectBudget);
        }
    }

    private MarketCostProjectBudgetItem createDomain(MarketCostProjectBudgetItem marketCostProjectBudgetItem,Integer companyId,Long projectId,Integer year,Long pbId,User user){
        marketCostProjectBudgetItem.setPbiId(marketCostProjectBudgetItemDao.getKey("market_cost_project_budget_item",marketCostProjectBudgetItem));
        marketCostProjectBudgetItem.setCompanyId(companyId);
        marketCostProjectBudgetItem.setProjectId(projectId);
        marketCostProjectBudgetItem.setYear(year);
        marketCostProjectBudgetItem.setPbId(pbId);
        marketCostProjectBudgetItem.setAllHappenMoney(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney01(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney02(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney03(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney04(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney05(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney06(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney07(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney08(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney09(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney10(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney11(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney12(new BigDecimal("0"));
        marketCostProjectBudgetItem.setCreateUserId(user.getUserId());
        marketCostProjectBudgetItem.setCreateDate(new Date());
        return marketCostProjectBudgetItem;
    }


    @Transactional
    public void add(Integer companyId,Long projectId,Integer year,Long pbId,Long budgetId) throws Exception{
        List<MarketCostItem> marketCostItemList = marketCostItemService.getAll();
        BigDecimal defaultMoney = new BigDecimal("0");
        List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList = new ArrayList<>();
        for (MarketCostItem marketCostItem:marketCostItemList){
            MarketCostProjectBudgetItem marketCostProjectBudgetItem = this.createEntity(marketCostItem.getCode(), marketCostItem.getSecondCode(), marketCostItem.getFirstCode(), companyId, projectId, year, pbId);
            defaultMoney=defaultMoney.add(marketCostProjectBudgetItem.getAllPlanMoney());
            marketCostProjectBudgetItemList.add(marketCostProjectBudgetItem);
        }
        this.batchAdd(marketCostProjectBudgetItemList);
        marketCostProjectBudgetService.addDefaultMoney(defaultMoney,pbId);
        marketCostCompanyBudgetService.addDefaultMoney(defaultMoney,budgetId);
    }

    private MarketCostProjectBudgetItem createEntity(String code,String secondCode,String firstCode,Integer companyId,Long projectId,Integer year,Long pbId){
        MarketCostProjectBudgetItem marketCostProjectBudgetItem = new MarketCostProjectBudgetItem();
        marketCostProjectBudgetItem.setPbiId(marketCostProjectBudgetItemDao.getKey("market_cost_project_budget_item",marketCostProjectBudgetItem));
        marketCostProjectBudgetItem.setCode(code);
        marketCostProjectBudgetItem.setCompanyId(companyId);
        marketCostProjectBudgetItem.setProjectId(projectId);
        marketCostProjectBudgetItem.setYear(year);
        marketCostProjectBudgetItem.setFirstCode(firstCode);
        marketCostProjectBudgetItem.setSecondCode(secondCode);
        marketCostProjectBudgetItem.setPbId(pbId);
        marketCostProjectBudgetItem.setAllPlanMoney(new BigDecimal("12000"));
        marketCostProjectBudgetItem.setPlanMoney01(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney02(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney03(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney04(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney05(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney06(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney07(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney08(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney09(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney10(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney11(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setPlanMoney12(new BigDecimal("1000"));
        marketCostProjectBudgetItem.setAllHappenMoney(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney01(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney02(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney03(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney04(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney05(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney06(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney07(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney08(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney09(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney10(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney11(new BigDecimal("0"));
        marketCostProjectBudgetItem.setHappenMoney12(new BigDecimal("0"));
        marketCostProjectBudgetItem.setCreateUserId(0L);
        marketCostProjectBudgetItem.setCreateDate(new Date());
        return marketCostProjectBudgetItem;
    }

    @Transactional(rollbackFor = Exception.class)
    private int[] batchAdd(List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList) throws Exception{
        return marketCostProjectBudgetItemDao.batchAdd(marketCostProjectBudgetItemList);
    }

}
