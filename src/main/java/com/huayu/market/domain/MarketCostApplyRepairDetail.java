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
* 营销费用发生登记明细
* Created by ZXL on 2017-9-4 11:11:55.
*/
@Table(name = "market_cost_apply_repair_detail")
public class MarketCostApplyRepairDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = did,payId,companyId,projectId,secondCode,threeCode,year,month,money,affiliationDate,isOwn,createDate;
 //private String updateFiledKeyValue = :did,:payId,:companyId,:projectId,:secondCode,:threeCode,:year,:month,:money,:affiliationDate,:isOwn,:createDate;
 //private String updateFiledValue = did=:did,payId=:payId,companyId=:companyId,projectId=:projectId,secondCode=:secondCode,threeCode=:threeCode,year=:year,month=:month,money=:money,affiliationDate=:affiliationDate,isOwn=:isOwn,createDate=:createDate;
    private Long did;//ID
    private Long payId;//付款ID
    private Integer companyId;//公司/区域ID
    private Long projectId;//项目ID
    private String secondCode;//二级费项编码
    private String threeCode;//三级费项编码
    private Integer year;//年
    private String month;//月
    private BigDecimal money;//金额
    private String affiliationDate;//费用归属年月
    private Short isOwn;//是否项目独立核算1 否 2 是
    private Date createDate;//创建日期

    @TableField
    private String companyName;//集团/公司
    @TableField
    private String projectName;//项目
    @TableField
    private String secondName;//二级费项名称
    @TableField
    private String threeName;//三级费项名称

    public MarketCostApplyRepairDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
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

    public void setDid(Long did){//ID
        this.did = did;
    }
    public Long getDid(){//ID
        return this.did;
    }

    public void setPayId(Long payId){//付款ID
        this.payId = payId;
    }
    public Long getPayId(){//付款ID
        return this.payId;
    }

    public void setCompanyId(Integer companyId){//公司/区域ID
        this.companyId = companyId;
    }
    public Integer getCompanyId(){//公司/区域ID
        return this.companyId;
    }

    public void setProjectId(Long projectId){//项目ID
        this.projectId = projectId;
    }
    public Long getProjectId(){//项目ID
        return this.projectId;
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

    public void setYear(Integer year){//年
        this.year = year;
    }
    public Integer getYear(){//年
        return this.year;
    }

    public void setMonth(String month){//月
        this.month = month;
    }
    public String getMonth(){//月
        return this.month;
    }

    public void setMoney(BigDecimal money){//金额
        this.money = money;
    }
    public BigDecimal getMoney(){//金额
        return this.money;
    }

    public void setAffiliationDate(String affiliationDate){//费用归属年月
        this.affiliationDate = affiliationDate;
    }
    public String getAffiliationDate(){//费用归属年月
        return this.affiliationDate;
    }

    public void setIsOwn(Short isOwn){//是否项目独立核算1 否 2 是
        this.isOwn = isOwn;
    }
    public Short getIsOwn(){//是否项目独立核算1 否 2 是
        return this.isOwn;
    }

    public void setCreateDate(Date createDate){//创建日期
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建日期
        return this.createDate;
    }


}
