package com.huayu.cost.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-24 14:11:32.
*/
@Table(name = "cost_balance_detail")
public class CostBalanceDetail extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -7098702299193005560L;
    //private String updateFiledKey = balanceId,type,status,loanDate,loanMoney,hasMoney,waitMoney,theMoney,sort,payId,loanId,serialsNumber,createDate;
 //private String updateFiledValue = balanceId=:balanceId,type=:type,status=:status,loanDate=:loanDate,loanMoney=:loanMoney,hasMoney=:hasMoney,waitMoney=:waitMoney,theMoney=:theMoney,sort=:sort,payId=:payId,loanId=:loanId,serialsNumber=:serialsNumber,createDate=:createDate;
    private Long balanceId;
    private Integer type;
    private String payWay;
    private Integer status;
    private Date loanDate;
    private BigDecimal loanMoney;
    private BigDecimal hasMoney;
    private BigDecimal waitMoney;
    private BigDecimal theMoney;
    private Integer sort;
    private Long payId;
    private Long loanId;
    private String serialsNumber;
    private Date createDate;
    @JSONField(format="yyyy-MM-dd")
    private Date repayDate;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;


    public CostBalanceDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getBalanceIdStr() {
        return balanceId.toString();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public BigDecimal getHasMoney() {
        return hasMoney;
    }

    public void setHasMoney(BigDecimal hasMoney) {
        this.hasMoney = hasMoney;
    }

    public BigDecimal getWaitMoney() {
        return waitMoney;
    }

    public void setWaitMoney(BigDecimal waitMoney) {
        this.waitMoney = waitMoney;
    }

    public BigDecimal getTheMoney() {
        return theMoney;
    }

    public void setTheMoney(BigDecimal theMoney) {
        this.theMoney = theMoney;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getSerialsNumber() {
        return serialsNumber;
    }

    public void setSerialsNumber(String serialsNumber) {
        this.serialsNumber = serialsNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}
