package com.huayu.cost.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-21 17:30:12.
*/
@Table(name = "cost_loan_pay_detail")
public class CostLoanPayDetail extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 2221218554077556903L;
    //private String updateFiledKey = id,money,loanId,serialsNumber,payWay,payDate,status,revokedtime,createtime;
 //private String updateFiledValue = id=:id,money=:money,loanId=:loanId,serialsNumber=:serialsNumber,payWay=:payWay,payDate=:payDate,status=:status,revokedtime=:revokedtime,createtime=:createtime;
    private Long id;
    private BigDecimal money;
    private Long loanId;
    private String serialsNumber;
    private String payWay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date payDate;
    private Integer status;
    private Date revokedtime;
    private Date createtime;
    private Date updatetime;
    private Date deletetime;
    private Integer createUser;
    private Integer revokedUser;
    private Integer updateUser;
    private Integer deleteUser;

    public CostLoanPayDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRevokedtime() {
        return revokedtime;
    }

    public void setRevokedtime(Date revokedtime) {
        this.revokedtime = revokedtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getRevokedUser() {
        return revokedUser;
    }

    public void setRevokedUser(Integer revokedUser) {
        this.revokedUser = revokedUser;
    }
}
