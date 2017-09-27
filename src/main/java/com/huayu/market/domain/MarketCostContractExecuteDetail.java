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
* 框架合同执行单/量价确认单明细
* Created by ZXL on 2017-8-23 14:36:56.
*/
@Table(name = "market_cost_contract_execute_detail")
public class MarketCostContractExecuteDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = detailId,type,sort,projectId,executeId,secondCode,threeCode,reason,happenDate,year,month,price,number,finalMoney,applyingMoney,appliedMoney,createDate,pid,threeName;
 //private String updateFiledKeyValue = :detailId,:type,:sort,:projectId,:executeId,:secondCode,:threeCode,:reason,:happenDate,:year,:month,:price,:number,:finalMoney,:applyingMoney,:appliedMoney,:createDate,:pid,:threeName;
 //private String updateFiledValue = detailId=:detailId,type=:type,sort=:sort,projectId=:projectId,executeId=:executeId,secondCode=:secondCode,threeCode=:threeCode,reason=:reason,happenDate=:happenDate,year=:year,month=:month,price=:price,number=:number,finalMoney=:finalMoney,applyingMoney=:applyingMoney,appliedMoney=:appliedMoney,createDate=:createDate,pid=:pid,threeName=:threeName;
    private Long detailId;//ID
    private Short type;//类型 1执行单 2确认单
    private Integer sort;//序号
    private Long projectId;//项目ID
    private Long executeId;//执行单ID/确认单ID
    private String secondCode;//二级费项编码
    private String threeCode;//三级费项编码
    private String reason;//事由
    private Date happenDate;//发生日期
    private Integer year;//实际发生年度
    private String month;//发生月度
    private BigDecimal price;//单价
    private Integer number;//数量
    private BigDecimal finalMoney;//金额
    private BigDecimal applyingMoney;//申请中
    private BigDecimal appliedMoney;//已申请
    private Date createDate;//创建时间
    private Long pid;//立项ID
    private String threeName;//三级费项名称

    @TableField
    private String executeTitle;//执行单名称

    public MarketCostContractExecuteDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getExecuteTitle() {
        return executeTitle;
    }

    public void setExecuteTitle(String executeTitle) {
        this.executeTitle = executeTitle;
    }

    public void setDetailId(Long detailId){//ID
        this.detailId = detailId;
    }
    public Long getDetailId(){//ID
        return this.detailId;
    }

    public void setType(Short type){//类型 1执行单 2确认单
        this.type = type;
    }
    public Short getType(){//类型 1执行单 2确认单
        return this.type;
    }

    public void setSort(Integer sort){//序号
        this.sort = sort;
    }
    public Integer getSort(){//序号
        return this.sort;
    }

    public void setProjectId(Long projectId){//项目ID
        this.projectId = projectId;
    }
    public Long getProjectId(){//项目ID
        return this.projectId;
    }

    public void setExecuteId(Long executeId){//执行单ID/确认单ID
        this.executeId = executeId;
    }
    public Long getExecuteId(){//执行单ID/确认单ID
        return this.executeId;
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

    public void setReason(String reason){//事由
        this.reason = reason;
    }
    public String getReason(){//事由
        return this.reason;
    }

    public void setHappenDate(Date happenDate){//发生日期
        this.happenDate = happenDate;
    }
    public Date getHappenDate(){//发生日期
        return this.happenDate;
    }

    public void setYear(Integer year){//实际发生年度
        this.year = year;
    }
    public Integer getYear(){//实际发生年度
        return this.year;
    }

    public void setMonth(String month){//发生月度
        this.month = month;
    }
    public String getMonth(){//发生月度
        return this.month;
    }

    public void setPrice(BigDecimal price){//单价
        this.price = price;
    }
    public BigDecimal getPrice(){//单价
        return this.price;
    }

    public void setNumber(Integer number){//数量
        this.number = number;
    }
    public Integer getNumber(){//数量
        return this.number;
    }

    public void setFinalMoney(BigDecimal finalMoney){//金额
        this.finalMoney = finalMoney;
    }
    public BigDecimal getFinalMoney(){//金额
        return this.finalMoney;
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

    public void setCreateDate(Date createDate){//创建时间
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建时间
        return this.createDate;
    }

    public void setPid(Long pid){//立项ID
        this.pid = pid;
    }
    public Long getPid(){//立项ID
        return this.pid;
    }

    public void setThreeName(String threeName){//三级费项名称
        this.threeName = threeName;
    }
    public String getThreeName(){//三级费项名称
        return this.threeName;
    }


}
