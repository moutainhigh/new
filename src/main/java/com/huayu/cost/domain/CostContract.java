package com.huayu.cost.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;
import java.lang.String;

/**
* Created by DengPeng on 2017-4-25 17:47:46.
*/
@Table(name = "cost_contract")
public class CostContract extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -1477327304505714346L;
    //private String updateFiledKey = contractId,contractCode,contractName,bunit,departmentId,agentUserId,contractMoney,applyingMoney,appliedMoney,paidMoney,createDate;
 //private String updateFiledValue = contractId=:contractId,contractCode=:contractCode,contractName=:contractName,bunit=:bunit,departmentId=:departmentId,agentUserId=:agentUserId,contractMoney=:contractMoney,applyingMoney=:applyingMoney,appliedMoney=:appliedMoney,paidMoney=:paidMoney,createDate=:createDate;
    private Long contractId;
    private String contractCode;
    private String contractName;
    private String bunit;
    private Long departmentId;
    private Long agentUserId;
    private BigDecimal contractMoney;
    private BigDecimal applyingMoney;
    private BigDecimal appliedMoney;
    private BigDecimal paidMoney;
    private Date createDate;
    private Date payTime;
    private Long payUser;

    public CostContract() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getBunit() {
        return bunit;
    }

    public void setBunit(String bunit) {
        this.bunit = bunit;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(Long agentUserId) {
        this.agentUserId = agentUserId;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public BigDecimal getApplyingMoney() {
        return applyingMoney;
    }

    public void setApplyingMoney(BigDecimal applyingMoney) {
        this.applyingMoney = applyingMoney;
    }

    public BigDecimal getAppliedMoney() {
        return appliedMoney;
    }

    public void setAppliedMoney(BigDecimal appliedMoney) {
        this.appliedMoney = appliedMoney;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getPayUser() {
        return payUser;
    }

    public void setPayUser(Long payUser) {
        this.payUser = payUser;
    }
}
