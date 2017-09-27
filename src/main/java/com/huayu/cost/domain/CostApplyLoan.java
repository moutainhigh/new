package com.huayu.cost.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* Created by DengPeng on 2017-4-21 11:30:47.
*/
@Table(name = "cost_apply_loan")
public class CostApplyLoan extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 7090400249931233371L;
   //private String updateFiledKey = loanId,serialsNumber,status,companyId,departmentId,applyUserId,applyName,use,loanMoney,loanMoneyCn,payWay,payee,bank,bankNo,remark,createDate,successDate,payUserId,payDate,repayMoney,balanceMoney,repayDate,applyDate;
 //private String updateFiledValue = loanId=:loanId,serialsNumber=:serialsNumber,status=:status,companyId=:companyId,departmentId=:departmentId,applyUserId=:applyUserId,applyName=:applyName,use=:use,loanMoney=:loanMoney,loanMoneyCn=:loanMoneyCn,payWay=:payWay,payee=:payee,bank=:bank,bankNo=:bankNo,remark=:remark,createDate=:createDate,successDate=:successDate,payUserId=:payUserId,payDate=:payDate,repayMoney=:repayMoney,balanceMoney=:balanceMoney,repayDate=:repayDate,applyDate=:applyDate;
    private Long loanId;
    private String serialsNumber;
    private Integer status;
    private Long companyId;
    private Long departmentId;
    private Long applyUserId;
    private String applyName;
    private String use;
    private BigDecimal loanMoney;
    private String loanMoneyCn;
    private String payWay;
    private String payee;
    private String bank;
    private String bankNo;
    private String remark;
    private Date createDate;
    private Date successDate;
    private Long payUserId;
    private Date payDate;
    private BigDecimal repayMoney;
    private BigDecimal balanceMoney;
    private Date repayDate;
   @JSONField(format="yyyy-MM-dd")
    private Date applyDate;

    public CostApplyLoan() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 20;
    }

    @TableField
    private Integer repayStatus;

   @TableField
   private BigDecimal needRepayMoney;

   @TableField
   private String companyName;

   @TableField
   private String departmentName;

   @TableField
   private String payWayStr;

   @TableField
   private List<CostLoanPayDetail> loanPayDetailList;

   @TableField
   private List<CostBalanceDetail> loanRePayDetailList;
   @TableField
   private BigDecimal waitbalanceMoney;//待冲账金额
   @TableField
   private String d3;
   @TableField
   private String d4;

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

    public BigDecimal getWaitbalanceMoney() {
        return waitbalanceMoney;
    }

    public void setWaitbalanceMoney(BigDecimal waitbalanceMoney) {
        this.waitbalanceMoney = waitbalanceMoney;
    }

    public String getLoanIdStr() {
      return loanId.toString();
   }

   public String getPayWayStr() {
      return payWayStr;
   }

   public void setPayWayStr(String payWayStr) {
      this.payWayStr = payWayStr;
   }


   public List<CostBalanceDetail> getLoanRePayDetailList() {
      return loanRePayDetailList;
   }

   public void setLoanRePayDetailList(List<CostBalanceDetail> loanRePayDetailList) {
      this.loanRePayDetailList = loanRePayDetailList;
   }

   public List<CostLoanPayDetail> getLoanPayDetailList() {
      return loanPayDetailList;
   }

   public void setLoanPayDetailList(List<CostLoanPayDetail> loanPayDetailList) {
      this.loanPayDetailList = loanPayDetailList;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getDepartmentName() {
      return departmentName;
   }

   public void setDepartmentName(String departmentName) {
      this.departmentName = departmentName;
   }

   public BigDecimal getNeedRepayMoney() {
      return needRepayMoney;
   }

   public void setNeedRepayMoney(BigDecimal needRepayMoney) {
      this.needRepayMoney = needRepayMoney;
   }

   public Integer getRepayStatus() {
      return repayStatus;
   }

   public void setRepayStatus(Integer repayStatus) {
      this.repayStatus = repayStatus;
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

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public Long getCompanyId() {
      return companyId;
   }

   public void setCompanyId(Long companyId) {
      this.companyId = companyId;
   }

   public Long getDepartmentId() {
      return departmentId;
   }

   public void setDepartmentId(Long departmentId) {
      this.departmentId = departmentId;
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

   public String getUse() {
      return use;
   }

   public void setUse(String use) {
      this.use = use;
   }

   public BigDecimal getLoanMoney() {
      return loanMoney;
   }

   public void setLoanMoney(BigDecimal loanMoney) {
      this.loanMoney = loanMoney;
   }

   public String getLoanMoneyCn() {
      return loanMoneyCn;
   }

   public void setLoanMoneyCn(String loanMoneyCn) {
      this.loanMoneyCn = loanMoneyCn;
   }

   public String getPayWay() {
      return payWay;
   }

   public void setPayWay(String payWay) {
      this.payWay = payWay;
   }

   public String getPayee() {
      return payee;
   }

   public void setPayee(String payee) {
      this.payee = payee;
   }

   public String getBank() {
      return bank;
   }

   public void setBank(String bank) {
      this.bank = bank;
   }

   public String getBankNo() {
      return bankNo;
   }

   public void setBankNo(String bankNo) {
      this.bankNo = bankNo;
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

   public Long getPayUserId() {
      return payUserId;
   }

   public void setPayUserId(Long payUserId) {
      this.payUserId = payUserId;
   }

   public Date getPayDate() {
      return payDate;
   }

   public void setPayDate(Date payDate) {
      this.payDate = payDate;
   }

   public BigDecimal getRepayMoney() {
      return repayMoney;
   }

   public void setRepayMoney(BigDecimal repayMoney) {
      this.repayMoney = repayMoney;
   }

   public BigDecimal getBalanceMoney() {
      return balanceMoney;
   }

   public void setBalanceMoney(BigDecimal balanceMoney) {
      this.balanceMoney = balanceMoney;
   }

   public Date getRepayDate() {
      return repayDate;
   }

   public void setRepayDate(Date repayDate) {
      this.repayDate = repayDate;
   }

   public Date getApplyDate() {
      return applyDate;
   }

   public void setApplyDate(Date applyDate) {
      this.applyDate = applyDate;
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
        if(d3!=null && s.contains("d3")) {
            sb.append("&d3="+d3);
        }
        if(d4!=null && s.contains("d4")) {
            sb.append("&d4="+d4);
        }
        return sb.toString();
    }
}
