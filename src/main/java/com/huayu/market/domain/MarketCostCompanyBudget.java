package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;
import java.lang.Integer;

/**
 * 所属公司年度预算
* Created by DengPeng on 2017-6-15 14:11:01.
*/
@Table(name = "market_cost_company_budget")
public class MarketCostCompanyBudget extends BaseDomain  implements Serializable{

 //private String updateFiledKey = budgetId,companyId,year,currentMoney,defaultMoney,adjustMoney,createUserId,createDate;
 //private String updateFiledValue = budgetId=:budgetId,companyId=:companyId,year=:year,currentMoney=:currentMoney,defaultMoney=:defaultMoney,adjustMoney=:adjustMoney,createUserId=:createUserId,createDate=:createDate;
    private Long budgetId;
    private Integer companyId;
    private Integer year;
    private BigDecimal currentMoney;
    private BigDecimal defaultMoney;
    private BigDecimal adjustMoney;
    private BigDecimal freezeMoney;
    private BigDecimal usedMoney;
    private BigDecimal restoreMoney;//恢复金额
    private Long createUserId;
    private Date createDate;

    public BigDecimal getRestoreMoney() {
        return restoreMoney;
    }

    public void setRestoreMoney(BigDecimal restoreMoney) {
        this.restoreMoney = restoreMoney;
    }

    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public BigDecimal getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(BigDecimal currentMoney) {
        this.currentMoney = currentMoney;
    }

    public BigDecimal getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(BigDecimal defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public BigDecimal getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(BigDecimal adjustMoney) {
        this.adjustMoney = adjustMoney;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public MarketCostCompanyBudget() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }
}
