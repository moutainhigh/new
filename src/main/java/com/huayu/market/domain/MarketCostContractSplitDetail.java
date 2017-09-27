package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* 营销合同审批表实际发生拆分
* Created by ZXL on 2017-8-23 14:36:56.
*/
@Table(name = "market_cost_contract_split_detail")
public class MarketCostContractSplitDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = splitdId,type,sort,contractId,reimbursementId,secondCode,threeCode,projectId,pid,year,splitId,month,currentDate,money,createDate;
 //private String updateFiledKeyValue = :splitdId,:type,:sort,:contractId,:reimbursementId,:secondCode,:threeCode,:projectId,:pid,:year,:splitId,:month,:currentDate,:money,:createDate;
 //private String updateFiledValue = splitdId=:splitdId,type=:type,sort=:sort,contractId=:contractId,reimbursementId=:reimbursementId,secondCode=:secondCode,threeCode=:threeCode,projectId=:projectId,pid=:pid,year=:year,splitId=:splitId,month=:month,currentDate=:currentDate,money=:money,createDate=:createDate;
    private Long splitdId;//拆分ID
    private Short type;//合同类型 1自己拆分 2手动拆分
    private Integer sort;//序号
    private Long contractId;//合同ID
    private Long reimbursementId;//报销明细ID
    private String secondCode;//二级费项编码
    private String threeCode;//三级费项编码
    private Long projectId;//项目ID
    private Long pid;//立项ID
    private Integer year;//年
    private Long splitId;//手动拆分ID
    private String month;//月
    private Date currentDate;//当前日期
    private BigDecimal money;//实际发生金额
    private Date createDate;//创建时间

    @TableField
    private String secondName;//二级费项名称
    @TableField
    private String threeName;//三级费项名称

    public MarketCostContractSplitDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
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

    public void setSplitdId(Long splitdId){//拆分ID
        this.splitdId = splitdId;
    }
    public Long getSplitdId(){//拆分ID
        return this.splitdId;
    }

    public void setType(Short type){//合同类型 1自己拆分 2手动拆分
        this.type = type;
    }
    public Short getType(){//合同类型 1自己拆分 2手动拆分
        return this.type;
    }

    public void setSort(Integer sort){//序号
        this.sort = sort;
    }
    public Integer getSort(){//序号
        return this.sort;
    }

    public void setContractId(Long contractId){//合同ID
        this.contractId = contractId;
    }
    public Long getContractId(){//合同ID
        return this.contractId;
    }

    public void setReimbursementId(Long reimbursementId){//报销明细ID
        this.reimbursementId = reimbursementId;
    }
    public Long getReimbursementId(){//报销明细ID
        return this.reimbursementId;
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

    public void setYear(Integer year){//年
        this.year = year;
    }
    public Integer getYear(){//年
        return this.year;
    }

    public void setSplitId(Long splitId){//手动拆分ID
        this.splitId = splitId;
    }
    public Long getSplitId(){//手动拆分ID
        return this.splitId;
    }

    public void setMonth(String month){//月
        this.month = month;
    }
    public String getMonth(){//月
        return this.month;
    }

    public void setCurrentDate(Date currentDate){//当前日期
        this.currentDate = currentDate;
    }
    public Date getCurrentDate(){//当前日期
        return this.currentDate;
    }

    public void setMoney(BigDecimal money){//实际发生金额
        this.money = money;
    }
    public BigDecimal getMoney(){//实际发生金额
        return this.money;
    }

    public void setCreateDate(Date createDate){//创建时间
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建时间
        return this.createDate;
    }


}
