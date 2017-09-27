package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 月度立项审批单
* Created by DengPeng on 2017-7-3 11:58:11.
*/
@Table(name = "market_cost_approval")
public class MarketCostApproval extends BaseDomain  implements Serializable{

 //private String updateFiledKey = pid,companyId,projectId,title,serialsNumber,status,applyUserId,applyName,applyDate,costYear,year,month,belongMonth,budgetId,pbId,areaUsableMoney,projectUsableMoney,isOwn,applyMoney,finalMoney,freezeMoney,usedMoney,remark,createDate,successDate;
 //private String updateFiledValue = pid=:pid,companyId=:companyId,projectId=:projectId,title=:title,serialsNumber=:serialsNumber,status=:status,applyUserId=:applyUserId,applyName=:applyName,applyDate=:applyDate,costYear=:costYear,year=:year,month=:month,belongMonth=:belongMonth,budgetId=:budgetId,pbId=:pbId,areaUsableMoney=:areaUsableMoney,projectUsableMoney=:projectUsableMoney,isOwn=:isOwn,applyMoney=:applyMoney,finalMoney=:finalMoney,freezeMoney=:freezeMoney,usedMoney=:usedMoney,remark=:remark,createDate=:createDate,successDate=:successDate;
    private Long pid;
    private Integer companyId;
    private Long projectId;
    private String title;
    private String serialsNumber;
    private Short status;
    private Long applyUserId;
    private String applyName;
    private Date applyDate;
    private Integer costYear;
    private Integer year;
    private String month;
    private String belongMonth;
    private Long budgetId;
    private Long pbId;
    private BigDecimal areaUsableMoney;
    private BigDecimal projectUsableMoney;
    private Short isOwn;
    private BigDecimal applyMoney;
    private BigDecimal finalMoney;
    private BigDecimal freezeMoney;
    private BigDecimal usedMoney;
    private String remark;
    private Date createDate;
    private Date successDate;

    @TableField
    private String companyName;//公司名称
    @TableField
    private String projectName;//项目名称
    @TableField
    private String statusName;//状态 1申请中 2已完成 3已撤销
    @TableField
    private String d3;//时间
    @TableField
    private String d4;//时间
    @TableField
    private BigDecimal balanceMoney;//剩余金额

    public MarketCostApproval() {
        this.dt = "desc";
        this.dtn = "pid";
        this.pageSize = 10;
    }

    public BigDecimal getBalanceMoney() {
        if (null!=getApplyMoney()&&null!=getFinalMoney()&&null!=getFreezeMoney()&&null!=getUsedMoney()&&null!=getStatus()){
            if (getStatus()==1||getStatus()==3){
                return getApplyMoney();
            } else if (getStatus()==2){
                return getFinalMoney().subtract(getFreezeMoney()).subtract(getUsedMoney());
            }
        }
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getStatusName() {
        if (null!=getStatus()){
            switch (getStatus()){
                case 1:
                    return "申请中";
                case 2:
                    return "已归档";
                case 3:
                    return "已撤销";
                default:
                    return "";
            }
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSerialsNumber() {
        return serialsNumber;
    }

    public void setSerialsNumber(String serialsNumber) {
        this.serialsNumber = serialsNumber;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getCostYear() {
        return costYear;
    }

    public void setCostYear(Integer costYear) {
        this.costYear = costYear;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(String belongMonth) {
        this.belongMonth = belongMonth;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getPbId() {
        return pbId;
    }

    public void setPbId(Long pbId) {
        this.pbId = pbId;
    }

    public BigDecimal getAreaUsableMoney() {
        return areaUsableMoney;
    }

    public void setAreaUsableMoney(BigDecimal areaUsableMoney) {
        this.areaUsableMoney = areaUsableMoney;
    }

    public BigDecimal getProjectUsableMoney() {
        return projectUsableMoney;
    }

    public void setProjectUsableMoney(BigDecimal projectUsableMoney) {
        this.projectUsableMoney = projectUsableMoney;
    }

    public Short getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(Short isOwn) {
        this.isOwn = isOwn;
    }

    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(BigDecimal applyMoney) {
        this.applyMoney = applyMoney;
    }

    public BigDecimal getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(BigDecimal finalMoney) {
        this.finalMoney = finalMoney;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
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
        if(l2!=null && s.contains("l2")) {
            sb.append("&l2="+l2);
        }
        if(getD3()!=null && s.contains("d3")) {
            sb.append("&d3="+getD3());
        }
        if(getD4()!=null && s.contains("d4")) {
            sb.append("&d4="+getD4());
        }
        return sb.toString();
    }
}
