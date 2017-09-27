package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* 营销合同审批表-导入临时表
* Created by ZXL on 2017-8-23 14:36:55.
*/
@Table(name = "market_cost_contract_temp")
public class MarketCostContractTemp extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,contractId,type,status,contractCode,contractName,aunit,aunitId,bunit,projectId,pid,contractMoney,applyingMoney,appliedMoney,paidMoney,agentUserId,agentName,applyDate,startDate,endDate,remark,isAutoSplit,months,secondCode,threeCode,threeName;
 //private String updateFiledKeyValue = :id,:contractId,:type,:status,:contractCode,:contractName,:aunit,:aunitId,:bunit,:projectId,:pid,:contractMoney,:applyingMoney,:appliedMoney,:paidMoney,:agentUserId,:agentName,:applyDate,:startDate,:endDate,:remark,:isAutoSplit,:months,:secondCode,:threeCode,:threeName;
 //private String updateFiledValue = id=:id,contractId=:contractId,type=:type,status=:status,contractCode=:contractCode,contractName=:contractName,aunit=:aunit,aunitId=:aunitId,bunit=:bunit,projectId=:projectId,pid=:pid,contractMoney=:contractMoney,applyingMoney=:applyingMoney,appliedMoney=:appliedMoney,paidMoney=:paidMoney,agentUserId=:agentUserId,agentName=:agentName,applyDate=:applyDate,startDate=:startDate,endDate=:endDate,remark=:remark,isAutoSplit=:isAutoSplit,months=:months,secondCode=:secondCode,threeCode=:threeCode,threeName=:threeName;
    private Long id;//id
    private Long contractId;//合同ID
    private Short type;//合同类型 1营销合同 2框架合同
    private Short status;//状态 1申请中 2已完成 3已撤销 
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
    private String applyDate;//申请日期
    private Date startDate;//框架合同开始日期
    private Date endDate;//框架合同结束日期
    private String remark;//备注
    private Short isAutoSplit;//营销合同是否自动拆分 1否 2是
    private Short months;//自动拆分月数


    public MarketCostContractTemp() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public void setId(Long id){//id
        this.id = id;
    }
    public Long getId(){//id
        return this.id;
    }

    public void setContractId(Long contractId){//合同ID
        this.contractId = contractId;
    }
    public Long getContractId(){//合同ID
        return this.contractId;
    }

    public void setType(Short type){//合同类型 1营销合同 2框架合同
        this.type = type;
    }
    public Short getType(){//合同类型 1营销合同 2框架合同
        return this.type;
    }

    public void setStatus(Short status){//状态 1申请中 2已完成 3已撤销 
        this.status = status;
    }
    public Short getStatus(){//状态 1申请中 2已完成 3已撤销 
        return this.status;
    }

    public void setContractCode(String contractCode){//合同编号
        this.contractCode = contractCode;
    }
    public String getContractCode(){//合同编号
        return this.contractCode;
    }

    public void setContractName(String contractName){//合同名称
        this.contractName = contractName;
    }
    public String getContractName(){//合同名称
        return this.contractName;
    }

    public void setAunit(String aunit){//我方单位名称
        this.aunit = aunit;
    }
    public String getAunit(){//我方单位名称
        return this.aunit;
    }

    public void setAunitId(Integer aunitId){//我方单位ID
        this.aunitId = aunitId;
    }
    public Integer getAunitId(){//我方单位ID
        return this.aunitId;
    }

    public void setBunit(String bunit){//对方单位名称
        this.bunit = bunit;
    }
    public String getBunit(){//对方单位名称
        return this.bunit;
    }

    public void setProjectId(Long projectId){//项目ID
        this.projectId = projectId;
    }
    public Long getProjectId(){//项目ID
        return this.projectId;
    }

    public void setPid(Long pid){//立项ID
        this.pid = pid;
    }
    public Long getPid(){//立项ID
        return this.pid;
    }

    public void setContractMoney(BigDecimal contractMoney){//合同金额
        this.contractMoney = contractMoney;
    }
    public BigDecimal getContractMoney(){//合同金额
        return this.contractMoney;
    }

    public void setApplyingMoney(BigDecimal applyingMoney){//申请中
        this.applyingMoney = applyingMoney;
    }
    public BigDecimal getApplyingMoney(){//申请中
        return this.applyingMoney;
    }

    public void setAppliedMoney(BigDecimal appliedMoney){//已申请
        this.appliedMoney = appliedMoney;
    }
    public BigDecimal getAppliedMoney(){//已申请
        return this.appliedMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney){//已付金额
        this.paidMoney = paidMoney;
    }
    public BigDecimal getPaidMoney(){//已付金额
        return this.paidMoney;
    }

    public void setAgentUserId(Long agentUserId){//经办人
        this.agentUserId = agentUserId;
    }
    public Long getAgentUserId(){//经办人
        return this.agentUserId;
    }

    public void setAgentName(String agentName){//经办人姓名
        this.agentName = agentName;
    }
    public String getAgentName(){//经办人姓名
        return this.agentName;
    }

    public void setApplyDate(String applyDate){//申请日期
        this.applyDate = applyDate;
    }
    public String getApplyDate(){//申请日期
        return this.applyDate;
    }

    public void setStartDate(Date startDate){//框架合同开始日期
        this.startDate = startDate;
    }
    public Date getStartDate(){//框架合同开始日期
        return this.startDate;
    }

    public void setEndDate(Date endDate){//框架合同结束日期
        this.endDate = endDate;
    }
    public Date getEndDate(){//框架合同结束日期
        return this.endDate;
    }

    public void setRemark(String remark){//备注
        this.remark = remark;
    }
    public String getRemark(){//备注
        return this.remark;
    }

    public void setIsAutoSplit(Short isAutoSplit){//营销合同是否自动拆分 1否 2是
        this.isAutoSplit = isAutoSplit;
    }
    public Short getIsAutoSplit(){//营销合同是否自动拆分 1否 2是
        return this.isAutoSplit;
    }

    public void setMonths(Short months){//自动拆分月数
        this.months = months;
    }
    public Short getMonths(){//自动拆分月数
        return this.months;
    }


}
