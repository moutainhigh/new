package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.lang.String;

/**
* 营销合同审批表-导入临时表
* Created by ZXL on 2017-8-23 14:36:55.
*/
@Table(name = "market_cost_contract_temp_detail")
public class MarketCostContractTempDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,contractId,type,contractCode,contractName,contractMoney,applyDate,remark;
 //private String updateFiledKeyValue = :id,:contractId,:type,:contractCode,:contractName,:contractMoney,:applyDate,:remark;
 //private String updateFiledValue = id=:id,contractId=:contractId,type=:type,contractCode=:contractCode,contractName=:contractName,contractMoney=:contractMoney,applyDate=:applyDate,remark=:remark;
    private Long id;//id
    private Long contractId;//合同ID
    private Short type;//合同类型 1营销合同 2框架合同
    private String contractCode;//合同编号
    private String contractName;//合同名称
    private BigDecimal contractMoney;//合同金额
    private String applyDate;//申请日期
    private String remark;//备注
    private String secondCode;//二级费项编码
    private String threeCode;//三级费项编码
    private String threeName;//费用类型
    public MarketCostContractTempDetail() {
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

    public void setContractMoney(BigDecimal contractMoney){//合同金额
        this.contractMoney = contractMoney;
    }
    public BigDecimal getContractMoney(){//合同金额
        return this.contractMoney;
    }

    public void setApplyDate(String applyDate){//申请日期
        this.applyDate = applyDate;
    }
    public String getApplyDate(){//申请日期
        return this.applyDate;
    }

    public void setRemark(String remark){//备注
        this.remark = remark;
    }
    public String getRemark(){//备注
        return this.remark;
    }
    public void setSecondCode(String secondCode){//二级费项编码
        this.secondCode = secondCode;
    }
    public String getSecondCode(){//二级费项编码
        return this.secondCode;
    }

    public void setThreeCode(String threeCode){//三级费项编码
        this.threeCode = threeCode;
    }
    public String getThreeCode(){//三级费项编码
        return this.threeCode;
    }

    public void setThreeName(String threeName){//费用类型
        this.threeName = threeName;
    }
    public String getThreeName(){//费用类型
        return this.threeName;
    }

    @Override
    public String toString() {
        return "MarketCostContractTempDetail{" +
                "id=" + id +
                ", contractId=" + contractId +
                ", type=" + type +
                ", contractCode='" + contractCode + '\'' +
                ", contractName='" + contractName + '\'' +
                ", contractMoney=" + contractMoney +
                ", applyDate='" + applyDate + '\'' +
                ", remark='" + remark + '\'' +
                ", secondCode='" + secondCode + '\'' +
                ", threeCode='" + threeCode + '\'' +
                ", threeName='" + threeName + '\'' +
                '}';
    }
}
