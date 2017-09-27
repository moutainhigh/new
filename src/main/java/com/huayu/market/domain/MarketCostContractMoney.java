package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;
import java.lang.String;

/**
* 报销明细
* Created by ZXL on 2017-8-23 14:36:56.
*/
@Table(name = "market_cost_contract_money")
public class MarketCostContractMoney extends BaseDomain  implements Serializable{

 //private String updateFiledKey = did,applyMoney,freezeMoney,usedMoney,payId,secondCode,projectId,pid,createDate;
 //private String updateFiledKeyValue = :did,:applyMoney,:freezeMoney,:usedMoney,:payId,:secondCode,:projectId,:pid,:createDate;
 //private String updateFiledValue = did=:did,applyMoney=:applyMoney,freezeMoney=:freezeMoney,usedMoney=:usedMoney,payId=:payId,secondCode=:secondCode,projectId=:projectId,pid=:pid,createDate=:createDate;
    private Long did;//报销明细ID
    private BigDecimal applyMoney;//本次立项申请金额
    private BigDecimal freezeMoney;//冻结金额
    private BigDecimal usedMoney;//已用金额
    private Long payId;//报销/付款申请ID
    private String secondCode;//二级费项编码
    private Long projectId;//项目ID
    private Long pid;//立项ID
    private Date createDate;//创建日期

    public MarketCostContractMoney() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public void setDid(Long did){//报销明细ID
        this.did = did;
    }
    public Long getDid(){//报销明细ID
        return this.did;
    }

    public void setApplyMoney(BigDecimal applyMoney){//本次立项申请金额
        this.applyMoney = applyMoney;
    }
    public BigDecimal getApplyMoney(){//本次立项申请金额
        return this.applyMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney){//冻结金额
        this.freezeMoney = freezeMoney;
    }
    public BigDecimal getFreezeMoney(){//冻结金额
        return this.freezeMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney){//已用金额
        this.usedMoney = usedMoney;
    }
    public BigDecimal getUsedMoney(){//已用金额
        return this.usedMoney;
    }

    public void setPayId(Long payId){//报销/付款申请ID
        this.payId = payId;
    }
    public Long getPayId(){//报销/付款申请ID
        return this.payId;
    }

    public void setSecondCode(String secondCode){//二级费项编码
        this.secondCode = secondCode;
    }
    public String getSecondCode(){//二级费项编码
        return this.secondCode;
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

    public void setCreateDate(Date createDate){//创建日期
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建日期
        return this.createDate;
    }


}
