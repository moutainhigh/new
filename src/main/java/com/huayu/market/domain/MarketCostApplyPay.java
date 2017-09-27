package com.huayu.market.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.cost.domain.CostReimbursementPayDetail;
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
* Created by DengPeng on 2017-7-19 9:39:42.
*/
@Table(name = "market_cost_apply_pay")
public class MarketCostApplyPay extends BaseDomain  implements Serializable{

 //private String updateFiledKey = payId,type,title,serialsNumber,status,projectId,contractId,contractCode,pid,departmentId,agentUserId,applyUserId,applyName,applyMoney,balanceMoney,finalMoney,finalMoneyCn,payWay,payee,bank,bankNo,remark,createDate,successDate,payUserId,payDate,applyDate,accountUnitId,billMoney,paperTitle,contractMoney,paidMoney,paymentMoney,appliedMoney,affirmId;
 //private String updateFiledValue = payId=:payId,type=:type,title=:title,serialsNumber=:serialsNumber,status=:status,projectId=:projectId,contractId=:contractId,contractCode=:contractCode,pid=:pid,departmentId=:departmentId,agentUserId=:agentUserId,applyUserId=:applyUserId,applyName=:applyName,applyMoney=:applyMoney,balanceMoney=:balanceMoney,finalMoney=:finalMoney,finalMoneyCn=:finalMoneyCn,payWay=:payWay,payee=:payee,bank=:bank,bankNo=:bankNo,remark=:remark,createDate=:createDate,successDate=:successDate,payUserId=:payUserId,payDate=:payDate,applyDate=:applyDate,accountUnitId=:accountUnitId,billMoney=:billMoney,paperTitle=:paperTitle,contractMoney=:contractMoney,paidMoney=:paidMoney,paymentMoney=:paymentMoney,appliedMoney=:appliedMoney,affirmId=:affirmId;
    private Long payId;
    private Integer type;
    private String title;
    private String serialsNumber;
    private Integer status;
    private String projectId;
    private Long contractId;
    private String contractCode;
    private Long pid;
    private Long departmentId;
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
    private Date payDate;
    private Date applyDate;
    private String accountUnitId;
    private BigDecimal billMoney;
    private String paperTitle;
    private BigDecimal contractMoney;
    private BigDecimal paidMoney;
    private BigDecimal paymentMoney;
    private BigDecimal appliedMoney;
    private Long affirmId;

    @TableField
    private String typeName;//1营销合同 2框架合同 3营销费用 4营销差旅费
    @TableField
    private BigDecimal actualPay;//实际支付金额
    @TableField
    private String statusName;//状态 1申请中 2已完成 3已撤销 4已付款 5部分付款
    @TableField
    private String departmentName;//部门
    @TableField
    private String companyName;//公司
    @TableField
    private String projectName;//所属项目
    @TableField
    private List<CostReimbursementPayDetail> costReimbursementPayDetailList = new ArrayList<>();//付款明细列表
    @TableField
    private List<MarketCostContractSplitDetail> marketCostContractSplitDetailList = new ArrayList<>();//付款明细列表
    @TableField
    private List<MarketCostReimbursementDetail> marketCostReimbursementDetailList = new ArrayList<>();//付款明细
    @TableField
    private String unitName;//费用入账单位
    @TableField
    private String projectCompanyName;//所属项目区域/集团
    @TableField
    private String d3;//时间
    @TableField
    private String d4;//时间

    public MarketCostApplyPay() {
        this.dt = "desc";
        this.dtn = "applyDate";
        this.pageSize = 10;
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

    public List<MarketCostReimbursementDetail> getMarketCostReimbursementDetailList() {
        return marketCostReimbursementDetailList;
    }

    public void setMarketCostReimbursementDetailList(List<MarketCostReimbursementDetail> marketCostReimbursementDetailList) {
        this.marketCostReimbursementDetailList = marketCostReimbursementDetailList;
    }

    public List<MarketCostContractSplitDetail> getMarketCostContractSplitDetailList() {
        return marketCostContractSplitDetailList;
    }

    public void setMarketCostContractSplitDetailList(List<MarketCostContractSplitDetail> marketCostContractSplitDetailList) {
        this.marketCostContractSplitDetailList = marketCostContractSplitDetailList;
    }

    public String getProjectCompanyName() {
        return projectCompanyName;
    }

    public void setProjectCompanyName(String projectCompanyName) {
        this.projectCompanyName = projectCompanyName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<CostReimbursementPayDetail> getCostReimbursementPayDetailList() {
        return costReimbursementPayDetailList;
    }

    public void setCostReimbursementPayDetailList(List<CostReimbursementPayDetail> costReimbursementPayDetailList) {
        this.costReimbursementPayDetailList = costReimbursementPayDetailList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatusName() {
        if (null!=getStatus()){
            switch (getStatus()){
                case 2:
                    return "未支付";
                case 4:
                    return "已支付";
                case 5:
                    return "部分支付";
                default:
                    return statusName;
            }
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getActualPay() {
        return actualPay;
    }

    public void setActualPay(BigDecimal actualPay) {
        this.actualPay = actualPay;
    }

    public String getTypeName() {
        if (null!=getType()){
            switch (getType()){
                case 1:
                    return "营销合同付款";
                case 2:
                    return "框架合同付款";
                case 3:
                    return "营销费用报销";
                case 4:
                    return "营销差旅费报销";
                default:
                    return typeName;
            }
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
    @JSONField(format="yyyy-MM-dd")
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getAccountUnitId() {
        return accountUnitId;
    }

    public void setAccountUnitId(String accountUnitId) {
        this.accountUnitId = accountUnitId;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public BigDecimal getAppliedMoney() {
        return appliedMoney;
    }

    public void setAppliedMoney(BigDecimal appliedMoney) {
        this.appliedMoney = appliedMoney;
    }

    public Long getAffirmId() {
        return affirmId;
    }

    public void setAffirmId(Long affirmId) {
        this.affirmId = affirmId;
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
        if(s1!=null && s.contains("s1")) {
            sb.append("&s1="+s1);
        }
        if(i1!=null && s.contains("i1")) {
            sb.append("&i1="+i1);
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
