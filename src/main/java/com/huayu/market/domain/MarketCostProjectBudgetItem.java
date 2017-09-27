package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.ArrayList;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * 项目预算费用预算详细表
* Created by DengPeng on 2017-6-15 14:14:18.
*/
@Table(name = "market_cost_project_budget_item")
public class MarketCostProjectBudgetItem extends BaseDomain  implements Serializable,Cloneable{

 //private String updateFiledKey = pbiId,code,companyId,projectId,year,firstCode,secondCode,pbId,allPlanMoney,planMoney01,planMoney02,planMoney03,planMoney04,planMoney05,planMoney06,planMoney07,planMoney08,planMoney09,planMoney10,planMoney11,planMoney12,allHappenMoney,happenMoney01,happenMoney02,happenMoney03,happenMoney04,happenMoney05,happenMoney06,happenMoney07,happenMoney08,happenMoney09,happenMoney10,happenMoney11,happenMoney12,createUserId,createDate;
 //private String updateFiledValue = pbiId=:pbiId,code=:code,companyId=:companyId,projectId=:projectId,year=:year,firstCode=:firstCode,secondCode=:secondCode,pbId=:pbId,allPlanMoney=:allPlanMoney,planMoney01=:planMoney01,planMoney02=:planMoney02,planMoney03=:planMoney03,planMoney04=:planMoney04,planMoney05=:planMoney05,planMoney06=:planMoney06,planMoney07=:planMoney07,planMoney08=:planMoney08,planMoney09=:planMoney09,planMoney10=:planMoney10,planMoney11=:planMoney11,planMoney12=:planMoney12,allHappenMoney=:allHappenMoney,happenMoney01=:happenMoney01,happenMoney02=:happenMoney02,happenMoney03=:happenMoney03,happenMoney04=:happenMoney04,happenMoney05=:happenMoney05,happenMoney06=:happenMoney06,happenMoney07=:happenMoney07,happenMoney08=:happenMoney08,happenMoney09=:happenMoney09,happenMoney10=:happenMoney10,happenMoney11=:happenMoney11,happenMoney12=:happenMoney12,createUserId=:createUserId,createDate=:createDate;
    private Long pbiId;
    private String code;
    private Integer companyId;
    private Long projectId;
    private Integer year;
    private String firstCode;
    private String secondCode;
    private Long pbId;
    private BigDecimal allPlanMoney;
    private BigDecimal planMoney01;
    private BigDecimal planMoney02;
    private BigDecimal planMoney03;
    private BigDecimal planMoney04;
    private BigDecimal planMoney05;
    private BigDecimal planMoney06;
    private BigDecimal planMoney07;
    private BigDecimal planMoney08;
    private BigDecimal planMoney09;
    private BigDecimal planMoney10;
    private BigDecimal planMoney11;
    private BigDecimal planMoney12;
    private BigDecimal allHappenMoney;
    private BigDecimal happenMoney01;
    private BigDecimal happenMoney02;
    private BigDecimal happenMoney03;
    private BigDecimal happenMoney04;
    private BigDecimal happenMoney05;
    private BigDecimal happenMoney06;
    private BigDecimal happenMoney07;
    private BigDecimal happenMoney08;
    private BigDecimal happenMoney09;
    private BigDecimal happenMoney10;
    private BigDecimal happenMoney11;
    private BigDecimal happenMoney12;
    private Long createUserId;
    private Date createDate;

    @TableField
    private Integer threeId;
    @TableField
    private String threeName;
    @TableField
    private String threeCode;
    @TableField
    private Integer secondId;
    @TableField
    private String secondName;
    @TableField
    private Integer firstId;
    @TableField
    private String firstName;
    @TableField
    private List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList = new ArrayList<>();
    @TableField
    private List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemListCopy = new ArrayList<>();

    public MarketCostProjectBudgetItem() {
        this.dt = "desc";
        this.dtn = "pbiId";
        this.pageSize = 10;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public List<MarketCostProjectBudgetItem> getMarketCostProjectBudgetItemListCopy() {
        return marketCostProjectBudgetItemListCopy;
    }

    public void setMarketCostProjectBudgetItemListCopy(List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemListCopy) {
        this.marketCostProjectBudgetItemListCopy = marketCostProjectBudgetItemListCopy;
    }

    public Integer getThreeId() {
        return threeId;
    }

    public void setThreeId(Integer threeId) {
        this.threeId = threeId;
    }

    public String getThreeName() {
        return threeName;
    }

    public void setThreeName(String threeName) {
        this.threeName = threeName;
    }

    public String getThreeCode() {
        return threeCode;
    }

    public void setThreeCode(String threeCode) {
        this.threeCode = threeCode;
    }

    public Integer getSecondId() {
        return secondId;
    }

    public void setSecondId(Integer secondId) {
        this.secondId = secondId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getFirstId() {
        return firstId;
    }

    public void setFirstId(Integer firstId) {
        this.firstId = firstId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<MarketCostProjectBudgetItem> getMarketCostProjectBudgetItemList() {
        return marketCostProjectBudgetItemList;
    }

    public void setMarketCostProjectBudgetItemList(List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList) {
        this.marketCostProjectBudgetItemList = marketCostProjectBudgetItemList;
    }

    public Long getPbiId() {
        return pbiId;
    }

    public void setPbiId(Long pbiId) {
        this.pbiId = pbiId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public Long getPbId() {
        return pbId;
    }

    public void setPbId(Long pbId) {
        this.pbId = pbId;
    }

    public BigDecimal getAllPlanMoney() {
        return allPlanMoney;
    }

    public void setAllPlanMoney(BigDecimal allPlanMoney) {
        this.allPlanMoney = allPlanMoney;
    }

    public BigDecimal getPlanMoney01() {
        return planMoney01;
    }

    public void setPlanMoney01(BigDecimal planMoney01) {
        this.planMoney01 = planMoney01;
    }

    public BigDecimal getPlanMoney02() {
        return planMoney02;
    }

    public void setPlanMoney02(BigDecimal planMoney02) {
        this.planMoney02 = planMoney02;
    }

    public BigDecimal getPlanMoney03() {
        return planMoney03;
    }

    public void setPlanMoney03(BigDecimal planMoney03) {
        this.planMoney03 = planMoney03;
    }

    public BigDecimal getPlanMoney04() {
        return planMoney04;
    }

    public void setPlanMoney04(BigDecimal planMoney04) {
        this.planMoney04 = planMoney04;
    }

    public BigDecimal getPlanMoney05() {
        return planMoney05;
    }

    public void setPlanMoney05(BigDecimal planMoney05) {
        this.planMoney05 = planMoney05;
    }

    public BigDecimal getPlanMoney06() {
        return planMoney06;
    }

    public void setPlanMoney06(BigDecimal planMoney06) {
        this.planMoney06 = planMoney06;
    }

    public BigDecimal getPlanMoney07() {
        return planMoney07;
    }

    public void setPlanMoney07(BigDecimal planMoney07) {
        this.planMoney07 = planMoney07;
    }

    public BigDecimal getPlanMoney08() {
        return planMoney08;
    }

    public void setPlanMoney08(BigDecimal planMoney08) {
        this.planMoney08 = planMoney08;
    }

    public BigDecimal getPlanMoney09() {
        return planMoney09;
    }

    public void setPlanMoney09(BigDecimal planMoney09) {
        this.planMoney09 = planMoney09;
    }

    public BigDecimal getPlanMoney10() {
        return planMoney10;
    }

    public void setPlanMoney10(BigDecimal planMoney10) {
        this.planMoney10 = planMoney10;
    }

    public BigDecimal getPlanMoney11() {
        return planMoney11;
    }

    public void setPlanMoney11(BigDecimal planMoney11) {
        this.planMoney11 = planMoney11;
    }

    public BigDecimal getPlanMoney12() {
        return planMoney12;
    }

    public void setPlanMoney12(BigDecimal planMoney12) {
        this.planMoney12 = planMoney12;
    }

    public BigDecimal getAllHappenMoney() {
        return allHappenMoney;
    }

    public void setAllHappenMoney(BigDecimal allHappenMoney) {
        this.allHappenMoney = allHappenMoney;
    }

    public BigDecimal getHappenMoney01() {
        return happenMoney01;
    }

    public void setHappenMoney01(BigDecimal happenMoney01) {
        this.happenMoney01 = happenMoney01;
    }

    public BigDecimal getHappenMoney02() {
        return happenMoney02;
    }

    public void setHappenMoney02(BigDecimal happenMoney02) {
        this.happenMoney02 = happenMoney02;
    }

    public BigDecimal getHappenMoney03() {
        return happenMoney03;
    }

    public void setHappenMoney03(BigDecimal happenMoney03) {
        this.happenMoney03 = happenMoney03;
    }

    public BigDecimal getHappenMoney04() {
        return happenMoney04;
    }

    public void setHappenMoney04(BigDecimal happenMoney04) {
        this.happenMoney04 = happenMoney04;
    }

    public BigDecimal getHappenMoney05() {
        return happenMoney05;
    }

    public void setHappenMoney05(BigDecimal happenMoney05) {
        this.happenMoney05 = happenMoney05;
    }

    public BigDecimal getHappenMoney06() {
        return happenMoney06;
    }

    public void setHappenMoney06(BigDecimal happenMoney06) {
        this.happenMoney06 = happenMoney06;
    }

    public BigDecimal getHappenMoney07() {
        return happenMoney07;
    }

    public void setHappenMoney07(BigDecimal happenMoney07) {
        this.happenMoney07 = happenMoney07;
    }

    public BigDecimal getHappenMoney08() {
        return happenMoney08;
    }

    public void setHappenMoney08(BigDecimal happenMoney08) {
        this.happenMoney08 = happenMoney08;
    }

    public BigDecimal getHappenMoney09() {
        return happenMoney09;
    }

    public void setHappenMoney09(BigDecimal happenMoney09) {
        this.happenMoney09 = happenMoney09;
    }

    public BigDecimal getHappenMoney10() {
        return happenMoney10;
    }

    public void setHappenMoney10(BigDecimal happenMoney10) {
        this.happenMoney10 = happenMoney10;
    }

    public BigDecimal getHappenMoney11() {
        return happenMoney11;
    }

    public void setHappenMoney11(BigDecimal happenMoney11) {
        this.happenMoney11 = happenMoney11;
    }

    public BigDecimal getHappenMoney12() {
        return happenMoney12;
    }

    public void setHappenMoney12(BigDecimal happenMoney12) {
        this.happenMoney12 = happenMoney12;
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
}
