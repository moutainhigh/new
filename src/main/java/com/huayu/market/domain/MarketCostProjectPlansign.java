package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.Integer;

/**
* 项目预计签约信息
* Created by ZXL on 2017-8-4 16:18:44.
*/
@Table(name = "market_cost_project_plansign")
public class MarketCostProjectPlansign extends BaseDomain  implements Serializable{

 //private String updateFiledKey = signId,companyId,projectId,year,type,signMoney,signMoney01,signMoney02,signMoney03,signMoney04,signMoney05,signMoney06,signMoney07,signMoney08,signMoney09,signMoney10,signMoney11,signMoney12,createUserId,createDate;
 //private String updateFiledKeyValue = :signId,:companyId,:projectId,:year,:type,:signMoney,:signMoney01,:signMoney02,:signMoney03,:signMoney04,:signMoney05,:signMoney06,:signMoney07,:signMoney08,:signMoney09,:signMoney10,:signMoney11,:signMoney12,:createUserId,:createDate;
 //private String updateFiledValue = signId=:signId,companyId=:companyId,projectId=:projectId,year=:year,type=:type,signMoney=:signMoney,signMoney01=:signMoney01,signMoney02=:signMoney02,signMoney03=:signMoney03,signMoney04=:signMoney04,signMoney05=:signMoney05,signMoney06=:signMoney06,signMoney07=:signMoney07,signMoney08=:signMoney08,signMoney09=:signMoney09,signMoney10=:signMoney10,signMoney11=:signMoney11,signMoney12=:signMoney12,createUserId=:createUserId,createDate=:createDate;
    private Long signId;//签约ID
    private Integer companyId;//公司ID
    private Long projectId;//项目ID
    private Integer year;//年度
    private Short type;//类型1 平台 2项目
    private BigDecimal signMoney;//签约金额合计
    private BigDecimal signMoney01;//1月签约金额
    private BigDecimal signMoney02;//2月签约金额
    private BigDecimal signMoney03;//3月签约金额
    private BigDecimal signMoney04;//4月签约金额
    private BigDecimal signMoney05;//5月签约金额
    private BigDecimal signMoney06;//6月签约金额
    private BigDecimal signMoney07;//7月签约金额
    private BigDecimal signMoney08;//8月签约金额
    private BigDecimal signMoney09;//9月签约金额
    private BigDecimal signMoney10;//10月签约金额
    private BigDecimal signMoney11;//11月签约金额
    private BigDecimal signMoney12;//12月签约金额
    private Long createUserId;//创建人ID
    private Date createDate;//创建时间

    @TableField
    private String projectName;

    public MarketCostProjectPlansign() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setSignId(Long signId){//签约ID
        this.signId = signId;
    }
    public Long getSignId(){//签约ID
        return this.signId;
    }

    public void setCompanyId(Integer companyId){//公司ID
        this.companyId = companyId;
    }
    public Integer getCompanyId(){//公司ID
        return this.companyId;
    }

    public void setProjectId(Long projectId){//项目ID
        this.projectId = projectId;
    }
    public Long getProjectId(){//项目ID
        return this.projectId;
    }

    public void setYear(Integer year){//年度
        this.year = year;
    }
    public Integer getYear(){//年度
        return this.year;
    }

    public void setType(Short type){//类型1 平台 2项目
        this.type = type;
    }
    public Short getType(){//类型1 平台 2项目
        return this.type;
    }

    public void setSignMoney(BigDecimal signMoney){//签约金额合计
        this.signMoney = signMoney;
    }
    public BigDecimal getSignMoney(){//签约金额合计
        return this.signMoney;
    }

    public void setSignMoney01(BigDecimal signMoney01){//1月签约金额
        this.signMoney01 = signMoney01;
    }
    public BigDecimal getSignMoney01(){//1月签约金额
        return this.signMoney01;
    }

    public void setSignMoney02(BigDecimal signMoney02){//2月签约金额
        this.signMoney02 = signMoney02;
    }
    public BigDecimal getSignMoney02(){//2月签约金额
        return this.signMoney02;
    }

    public void setSignMoney03(BigDecimal signMoney03){//3月签约金额
        this.signMoney03 = signMoney03;
    }
    public BigDecimal getSignMoney03(){//3月签约金额
        return this.signMoney03;
    }

    public void setSignMoney04(BigDecimal signMoney04){//4月签约金额
        this.signMoney04 = signMoney04;
    }
    public BigDecimal getSignMoney04(){//4月签约金额
        return this.signMoney04;
    }

    public void setSignMoney05(BigDecimal signMoney05){//5月签约金额
        this.signMoney05 = signMoney05;
    }
    public BigDecimal getSignMoney05(){//5月签约金额
        return this.signMoney05;
    }

    public void setSignMoney06(BigDecimal signMoney06){//6月签约金额
        this.signMoney06 = signMoney06;
    }
    public BigDecimal getSignMoney06(){//6月签约金额
        return this.signMoney06;
    }

    public void setSignMoney07(BigDecimal signMoney07){//7月签约金额
        this.signMoney07 = signMoney07;
    }
    public BigDecimal getSignMoney07(){//7月签约金额
        return this.signMoney07;
    }

    public void setSignMoney08(BigDecimal signMoney08){//8月签约金额
        this.signMoney08 = signMoney08;
    }
    public BigDecimal getSignMoney08(){//8月签约金额
        return this.signMoney08;
    }

    public void setSignMoney09(BigDecimal signMoney09){//9月签约金额
        this.signMoney09 = signMoney09;
    }
    public BigDecimal getSignMoney09(){//9月签约金额
        return this.signMoney09;
    }

    public void setSignMoney10(BigDecimal signMoney10){//10月签约金额
        this.signMoney10 = signMoney10;
    }
    public BigDecimal getSignMoney10(){//10月签约金额
        return this.signMoney10;
    }

    public void setSignMoney11(BigDecimal signMoney11){//11月签约金额
        this.signMoney11 = signMoney11;
    }
    public BigDecimal getSignMoney11(){//11月签约金额
        return this.signMoney11;
    }

    public void setSignMoney12(BigDecimal signMoney12){//12月签约金额
        this.signMoney12 = signMoney12;
    }
    public BigDecimal getSignMoney12(){//12月签约金额
        return this.signMoney12;
    }

    public void setCreateUserId(Long createUserId){//创建人ID
        this.createUserId = createUserId;
    }
    public Long getCreateUserId(){//创建人ID
        return this.createUserId;
    }

    public void setCreateDate(Date createDate){//创建时间
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建时间
        return this.createDate;
    }


}
