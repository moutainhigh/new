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
 * 项目签约信息
* Created by DengPeng on 2017-6-29 11:20:46.
*/
@Table(name = "market_cost_project_sign")
public class MarketCostProjectSign extends BaseDomain  implements Serializable{

 //private String updateFiledKey = signId,companyId,projectId,year,type,signMoney,signMoney01,signMoney02,signMoney03,signMoney04,signMoney05,signMoney06,signMoney07,signMoney08,signMoney09,signMoney10,signMoney11,signMoney12,createUserId,createDate;
 //private String updateFiledValue = signId=:signId,companyId=:companyId,projectId=:projectId,year=:year,type=:type,signMoney=:signMoney,signMoney01=:signMoney01,signMoney02=:signMoney02,signMoney03=:signMoney03,signMoney04=:signMoney04,signMoney05=:signMoney05,signMoney06=:signMoney06,signMoney07=:signMoney07,signMoney08=:signMoney08,signMoney09=:signMoney09,signMoney10=:signMoney10,signMoney11=:signMoney11,signMoney12=:signMoney12,createUserId=:createUserId,createDate=:createDate;
    private Long signId;
    private Integer companyId;
    private Long projectId;
    private Integer year;
    private Short type;
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

    @TableField
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
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
}
