package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.util.ArrayList;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * 营销合同审批表
* Created by DengPeng on 2017-7-27 15:46:28.
*/
@Table(name = "market_cost_contract")
public class MarketCostContract extends BaseDomain  implements Serializable{

 //private String updateFiledKey = contractId,type,status,contractCode,contractName,aunit,aunitId,bunit,projectId,pid,contractMoney,applyingMoney,appliedMoney,paidMoney,agentUserId,agentName,applyDate,startDate,endDate,remark,isAutoSplit,months,successDate,createDate;
 //private String updateFiledValue = contractId=:contractId,type=:type,status=:status,contractCode=:contractCode,contractName=:contractName,aunit=:aunit,aunitId=:aunitId,bunit=:bunit,projectId=:projectId,pid=:pid,contractMoney=:contractMoney,applyingMoney=:applyingMoney,appliedMoney=:appliedMoney,paidMoney=:paidMoney,agentUserId=:agentUserId,agentName=:agentName,applyDate=:applyDate,startDate=:startDate,endDate=:endDate,remark=:remark,isAutoSplit=:isAutoSplit,months=:months,successDate=:successDate,createDate=:createDate;
    private Long contractId;//合同ID
    private Short type;//合同类型 1营销合同 2框架合同
    private Short status;//'状态 1申请中 2已完成 3已撤销 '
    private String contractCode;//合同编号
    private String contractName;//合同名称
    private String aunit;//我方单位名称
    private Integer aunitId;//我方单位ID
    private String bunit;//对方单位名称
    private Long projectId;//项目ID
    private Long pid;//立项ID
    private BigDecimal contractMoney;//合同金额
    private BigDecimal applyingMoney;//申请中
    private BigDecimal appliedMoney;//已申请
    private BigDecimal paidMoney;//已付金额
    private Long agentUserId;//经办人
    private String agentName;//经办人姓名
    private Date applyDate;//申请日期
    private Date startDate;//框架合同开始日期
    private Date endDate;//框架合同结束日期
    private String remark;//备注
    private Short isAutoSplit;//营销合同是否自动拆分 1否 2是
    private Short months;//自动拆分月数
    private Date successDate;//操作时间
    private Date createDate;//创建时间

    @TableField
    private BigDecimal payMoney;//付款金额
    @TableField
    private Long payId;//
    @TableField
    private String businessTypeName;//业务类别名称
    @TableField
    private Date currentDate;//发生日期
    @TableField
    private BigDecimal money;//发生金额
    @TableField
    private String companyName;//公司名称
    @TableField
    private String projectName;//项目名称
    @TableField
    private String secondName;//二级费项名称
    @TableField
    private String threeName;//三级费项名称
    @TableField
    private String d3;//时间
    @TableField
    private String d4;//时间
    @TableField
    private String threeCode;//三级编码
    @TableField
    private String belongMonth;//属于月份
    @TableField
    private List<MarketCostContractSplitDetail> marketCostContractSplitDetailList = new ArrayList<>();//营销合同审批表实际发生拆分
    @TableField
    private List<MarketCostContractExecuteDetail> marketCostContractExecuteDetailList = new ArrayList<>();//框架合同执行单/量价确认单明细

    public MarketCostContract() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 20;
    }

    public List<MarketCostContractSplitDetail> getMarketCostContractSplitDetailList() {
        return marketCostContractSplitDetailList;
    }

    public void setMarketCostContractSplitDetailList(List<MarketCostContractSplitDetail> marketCostContractSplitDetailList) {
        this.marketCostContractSplitDetailList = marketCostContractSplitDetailList;
    }

    public List<MarketCostContractExecuteDetail> getMarketCostContractExecuteDetailList() {
        return marketCostContractExecuteDetailList;
    }

    public void setMarketCostContractExecuteDetailList(List<MarketCostContractExecuteDetail> marketCostContractExecuteDetailList) {
        this.marketCostContractExecuteDetailList = marketCostContractExecuteDetailList;
    }

    public String getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(String belongMonth) {
        this.belongMonth = belongMonth;
    }

    public String getThreeCode() {
        return threeCode;
    }

    public void setThreeCode(String threeCode) {
        this.threeCode = threeCode;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThreeName() {
        return threeName;
    }

    public void setThreeName(String threeName) {
        this.threeName = threeName;
    }

    public String getBusinessTypeName() {
        if (null!=getType()){
            switch (getType()){
                case 1:
                    return "单次合同";
                case 2:
                    return "框架合同";
                case 3:
                    return "费用报销";
                case 4:
                    return "差旅费报销";
                default:
                    return businessTypeName;
            }
        }
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public String getAunit() {
        return aunit;
    }

    public void setAunit(String aunit) {
        this.aunit = aunit;
    }

    public Integer getAunitId() {
        return aunitId;
    }

    public void setAunitId(Integer aunitId) {
        this.aunitId = aunitId;
    }

    public String getBunit() {
        return bunit;
    }

    public void setBunit(String bunit) {
        this.bunit = bunit;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public Long getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(Long agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getIsAutoSplit() {
        return isAutoSplit;
    }

    public void setIsAutoSplit(Short isAutoSplit) {
        this.isAutoSplit = isAutoSplit;
    }

    public Short getMonths() {
        return months;
    }

    public void setMonths(Short months) {
        this.months = months;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if(s1!=null && s.contains("s1")) {
            sb.append("&s1="+s1);
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
