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
* Created by DengPeng on 2017-4-25 15:56:26.
*/
@Table(name = "cost_apply_pay")
public class CostApplyPay extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 9144578115694555793L;
   //private String updateFiledKey = payId,type,title,serialsNumber,status,companyId,departmentId,projectId,contractId,contractCode,agentUserId,applyUserId,applyName,applyMoney,balanceMoney,finalMoney,finalMoneyCn,payWay,payee,bank,bankNo,remark,createDate,successDate,payUserId,payDate,applyDate;
 //private String updateFiledValue = payId=:payId,type=:type,title=:title,serialsNumber=:serialsNumber,status=:status,companyId=:companyId,departmentId=:departmentId,projectId=:projectId,contractId=:contractId,contractCode=:contractCode,agentUserId=:agentUserId,applyUserId=:applyUserId,applyName=:applyName,applyMoney=:applyMoney,balanceMoney=:balanceMoney,finalMoney=:finalMoney,finalMoneyCn=:finalMoneyCn,payWay=:payWay,payee=:payee,bank=:bank,bankNo=:bankNo,remark=:remark,createDate=:createDate,successDate=:successDate,payUserId=:payUserId,payDate=:payDate,applyDate=:applyDate;
    private Long payId;
    private Integer type;
    private String title;
    private String serialsNumber;
    private Integer status;
    private Long companyId;
    private Long departmentId;
    private String projectId;
    private Long contractId;
    private String contractCode;
    private Long agentUserId;
    private Long applyUserId;
    private String applyName;
    private BigDecimal applyMoney;
    private BigDecimal balanceMoney;
    private BigDecimal finalMoney;
    private String finalMoneyCn;
    private String payWay;
    private String payee;
    private String bank;
    private String bankNo;
    private String remark;
    private Date createDate;
    private Date successDate;
    private Long payUserId;
   @JSONField(format="yyyy-MM-dd")
    private Date payDate;
   @JSONField(format="yyyy-MM-dd")
    private Date applyDate;

    public CostApplyPay() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public CostApplyPay(Long payId) {
      this.payId = payId;
   }

   @TableField
    private Integer category ;
   @TableField
    private String companyName ;
   @TableField
    private String departmentName ;
   @TableField
    private String projectName ;

//   @TableField
//    private BigDecimal needPayMoney ;

   @TableField
   private List<CostReimbursementPayDetail> reimbPayDetailList;

   @TableField
   private BigDecimal actualPay;

    public String getPayIdStr(){
       if (null ==payId){
          return "";
       }
       return this.payId.toString();
    }

    public String getContractIdStr(){
       if (null ==contractId){
          return "";
       }
       return this.contractId.toString();
    }

    public String getTypeStr(){
       if (null==type){
          return "";
       }
       switch (type){
          case 1:
             return "日常报销";
          case 2:
             return "差旅费报销";
          case 3:
             return "费用合同付款";
          case 4:
             return "非合同付款";
          default:
                return "";
       }
    }

   public BigDecimal getActualPay() {
      return actualPay;
   }

   public void setActualPay(BigDecimal actualPay) {
      this.actualPay = actualPay;
   }

   public List<CostReimbursementPayDetail> getReimbPayDetailList() {
      return reimbPayDetailList;
   }

   public void setReimbPayDetailList(List<CostReimbursementPayDetail> reimbPayDetailList) {
      this.reimbPayDetailList = reimbPayDetailList;
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

   public String getProjectName() {
      return projectName;
   }

   public void setProjectName(String projectName) {
      this.projectName = projectName;
   }

//   public BigDecimal getNeedPayMoney() {
//      return needPayMoney;
//   }
//
//   public void setNeedPayMoney(BigDecimal needPayMoney) {
//      this.needPayMoney = needPayMoney;
//   }

   public Integer getCategory() {
      return category;
   }

   public void setCategory(Integer category) {
      this.category = category;
   }

   public Long getPayId() {
      return payId;
   }

   public void setPayId(Long payId) {
      this.payId = payId;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
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

   public String getProjectId() {
      return projectId;
   }

   public void setProjectId(String projectId) {
      this.projectId = projectId;
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

   public Long getAgentUserId() {
      return agentUserId;
   }

   public void setAgentUserId(Long agentUserId) {
      this.agentUserId = agentUserId;
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

   public BigDecimal getApplyMoney() {
      return applyMoney;
   }

   public void setApplyMoney(BigDecimal applyMoney) {
      this.applyMoney = applyMoney;
   }

   public BigDecimal getBalanceMoney() {
      return balanceMoney;
   }

   public void setBalanceMoney(BigDecimal balanceMoney) {
      this.balanceMoney = balanceMoney;
   }

   public BigDecimal getFinalMoney() {
      return finalMoney;
   }

   public void setFinalMoney(BigDecimal finalMoney) {
      this.finalMoney = finalMoney;
   }

   public String getFinalMoneyCn() {
      return finalMoneyCn;
   }

   public void setFinalMoneyCn(String finalMoneyCn) {
      this.finalMoneyCn = finalMoneyCn;
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

   public Date getApplyDate() {
      return applyDate;
   }

   public void setApplyDate(Date applyDate) {
      this.applyDate = applyDate;
   }
}
