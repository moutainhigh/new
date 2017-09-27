package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.Integer;

/**
 * 项目明细
* Created by DengPeng on 2017-6-15 14:13:49.
*/
@Table(name = "market_cost_project_budget")
public class MarketCostProjectBudget extends BaseDomain  implements Serializable{

 //private String updateFiledKey = pbId,companyId,projectId,year,type,budgetId,currentMoney,defaultMoney,adjustMoney,freezeMoney,usedMoney,happenMoney,happenMoney01,happenMoney02,happenMoney03,happenMoney04,happenMoney05,happenMoney06,happenMoney07,happenMoney08,happenMoney09,happenMoney10,happenMoney11,happenMoney12,signMoney,signMoney01,signMoney02,signMoney03,signMoney04,signMoney05,signMoney06,signMoney07,signMoney08,signMoney09,signMoney10,signMoney11,signMoney12,createUserId,createDate;
 //private String updateFiledValue = pbId=:pbId,companyId=:companyId,projectId=:projectId,year=:year,type=:type,budgetId=:budgetId,currentMoney=:currentMoney,defaultMoney=:defaultMoney,adjustMoney=:adjustMoney,freezeMoney=:freezeMoney,usedMoney=:usedMoney,happenMoney=:happenMoney,happenMoney01=:happenMoney01,happenMoney02=:happenMoney02,happenMoney03=:happenMoney03,happenMoney04=:happenMoney04,happenMoney05=:happenMoney05,happenMoney06=:happenMoney06,happenMoney07=:happenMoney07,happenMoney08=:happenMoney08,happenMoney09=:happenMoney09,happenMoney10=:happenMoney10,happenMoney11=:happenMoney11,happenMoney12=:happenMoney12,signMoney=:signMoney,signMoney01=:signMoney01,signMoney02=:signMoney02,signMoney03=:signMoney03,signMoney04=:signMoney04,signMoney05=:signMoney05,signMoney06=:signMoney06,signMoney07=:signMoney07,signMoney08=:signMoney08,signMoney09=:signMoney09,signMoney10=:signMoney10,signMoney11=:signMoney11,signMoney12=:signMoney12,createUserId=:createUserId,createDate=:createDate;
    private Long pbId;
    private Integer companyId;
    private Long projectId;
    private Integer year;
    private Short type;
    private Long budgetId;
    private BigDecimal currentMoney;
    private BigDecimal defaultMoney;
    private BigDecimal adjustMoney;
    private BigDecimal freezeMoney;
    private BigDecimal usedMoney;
    private BigDecimal happenMoney;
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
    private BigDecimal signMoney;
    private BigDecimal signMoney01;
    private BigDecimal signMoney02;
    private BigDecimal signMoney03;
    private BigDecimal signMoney04;
    private BigDecimal signMoney05;
    private BigDecimal signMoney06;
    private BigDecimal signMoney07;
    private BigDecimal signMoney08;
    private BigDecimal signMoney09;
    private BigDecimal signMoney10;
    private BigDecimal signMoney11;
    private BigDecimal signMoney12;
    private Long createUserId;
    private Date createDate;
    private Short status;
    private BigDecimal restoreMoney;//恢复金额

    @TableField
    private String projectName;
    @TableField
    private Short isOwn;
    @TableField
    private String isOwnName;
    @TableField
    private BigDecimal residueMoney;//剩余预算
    @TableField
    private BigDecimal efficiencyRate;//费效

    public MarketCostProjectBudget() {
        this.dt = "desc";
        this.dtn = "pbId";
        this.pageSize = 10;
    }

    public BigDecimal getEfficiencyRate() {
        if (null!=getHappenMoney()&&null!=getSignMoney()){
            //return getSignMoney().compareTo(new BigDecimal("0"))==0?new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP):getHappenMoney().divide(getSignMoney(),2, BigDecimal.ROUND_HALF_UP);
            return getSignMoney().compareTo(new BigDecimal("0"))==0?new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_HALF_UP):getHappenMoney().multiply(new BigDecimal("100")).divide(getSignMoney(),2, BigDecimal.ROUND_HALF_UP);
        }
        return efficiencyRate;
    }

    public void setEfficiencyRate(BigDecimal efficiencyRate) {
        this.efficiencyRate = efficiencyRate;
    }

    public BigDecimal getResidueMoney() {
        return null!=getCurrentMoney()&&null!=getFreezeMoney()&&null!=getUsedMoney()?(getCurrentMoney().subtract(getFreezeMoney()).subtract(getUsedMoney())):residueMoney;
    }

    public void setResidueMoney(BigDecimal residueMoney) {
        this.residueMoney = residueMoney;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Short getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(Short isOwn) {
        this.isOwn = isOwn;
    }

    public String getIsOwnName() {
        return isOwnName;
    }

    public void setIsOwnName(String isOwnName) {
        this.isOwnName = isOwnName;
    }

    public Long getPbId() {
        return pbId;
    }

    public void setPbId(Long pbId) {
        this.pbId = pbId;
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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
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

    public BigDecimal getHappenMoney() {
        return happenMoney;
    }

    public void setHappenMoney(BigDecimal happenMoney) {
        this.happenMoney = happenMoney;
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

    public BigDecimal getSignMoney() {
        return signMoney;
    }

    public void setSignMoney(BigDecimal signMoney) {
        this.signMoney = signMoney;
    }

    public BigDecimal getSignMoney01() {
        return signMoney01;
    }

    public void setSignMoney01(BigDecimal signMoney01) {
        this.signMoney01 = signMoney01;
    }

    public BigDecimal getSignMoney02() {
        return signMoney02;
    }

    public void setSignMoney02(BigDecimal signMoney02) {
        this.signMoney02 = signMoney02;
    }

    public BigDecimal getSignMoney03() {
        return signMoney03;
    }

    public void setSignMoney03(BigDecimal signMoney03) {
        this.signMoney03 = signMoney03;
    }

    public BigDecimal getSignMoney04() {
        return signMoney04;
    }

    public void setSignMoney04(BigDecimal signMoney04) {
        this.signMoney04 = signMoney04;
    }

    public BigDecimal getSignMoney05() {
        return signMoney05;
    }

    public void setSignMoney05(BigDecimal signMoney05) {
        this.signMoney05 = signMoney05;
    }

    public BigDecimal getSignMoney06() {
        return signMoney06;
    }

    public void setSignMoney06(BigDecimal signMoney06) {
        this.signMoney06 = signMoney06;
    }

    public BigDecimal getSignMoney07() {
        return signMoney07;
    }

    public void setSignMoney07(BigDecimal signMoney07) {
        this.signMoney07 = signMoney07;
    }

    public BigDecimal getSignMoney08() {
        return signMoney08;
    }

    public void setSignMoney08(BigDecimal signMoney08) {
        this.signMoney08 = signMoney08;
    }

    public BigDecimal getSignMoney09() {
        return signMoney09;
    }

    public void setSignMoney09(BigDecimal signMoney09) {
        this.signMoney09 = signMoney09;
    }

    public BigDecimal getSignMoney10() {
        return signMoney10;
    }

    public void setSignMoney10(BigDecimal signMoney10) {
        this.signMoney10 = signMoney10;
    }

    public BigDecimal getSignMoney11() {
        return signMoney11;
    }

    public void setSignMoney11(BigDecimal signMoney11) {
        this.signMoney11 = signMoney11;
    }

    public BigDecimal getSignMoney12() {
        return signMoney12;
    }

    public void setSignMoney12(BigDecimal signMoney12) {
        this.signMoney12 = signMoney12;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public BigDecimal getRestoreMoney() {
        return restoreMoney;
    }

    public void setRestoreMoney(BigDecimal restoreMoney) {
        this.restoreMoney = restoreMoney;
    }

    @Override
    public String toString(String s) {
        StringBuilder sb = new StringBuilder("?");
        if(dt!=null && s.contains("dt")) {
            sb.append("dt="+dt);
        }
        if(pageSize!=null && s.contains("pageSize")) {
            sb.append("&pageSize="+pageSize);
        }
        if(pageNo!=null && s.contains("pageNo")) {
            sb.append("&pageNo="+pageNo);
        }
        if(dtn!=null && s.contains("dtn")) {
            sb.append("&dtn="+dtn);
        }
        if(i1!=null && s.contains("i1")) {
            sb.append("&i1="+i1);
        }
        if(i2!=null && s.contains("i2")) {
            sb.append("&i2="+i2);
        }
        return sb.toString();
    }

}
