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
* 报销明细
* Created by ZXL on 2017-8-23 14:36:56.
*/
@Table(name = "market_cost_reimbursement_detail")
public class MarketCostReimbursementDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = reimbursementId,type,sort,reason,startDate,endDate,allDay,startSite,endSite,carFee,stayFee,mealFee,otherFee,money,payId,secondCode,threeCode,projectId,pid,applyDate,year,month,createDate;
 //private String updateFiledKeyValue = :reimbursementId,:type,:sort,:reason,:startDate,:endDate,:allDay,:startSite,:endSite,:carFee,:stayFee,:mealFee,:otherFee,:money,:payId,:secondCode,:threeCode,:projectId,:pid,:applyDate,:year,:month,:createDate;
 //private String updateFiledValue = reimbursementId=:reimbursementId,type=:type,sort=:sort,reason=:reason,startDate=:startDate,endDate=:endDate,allDay=:allDay,startSite=:startSite,endSite=:endSite,carFee=:carFee,stayFee=:stayFee,mealFee=:mealFee,otherFee=:otherFee,money=:money,payId=:payId,secondCode=:secondCode,threeCode=:threeCode,projectId=:projectId,pid=:pid,applyDate=:applyDate,year=:year,month=:month,createDate=:createDate;
    private Long reimbursementId;//报销明细ID
    private Short type;//报销明细类型 1营销合同 2框架合同 3营销费用 4营销差旅费 5营销费用发生
    private Short sort;//序号
    private String reason;//事由
    private Date startDate;//开始日期
    private Date endDate;//结束日期
    private String allDay;//天数
    private String startSite;//开始地点
    private String endSite;//结束地点
    private BigDecimal carFee;//交通费
    private BigDecimal stayFee;//住宿费
    private BigDecimal mealFee;//餐费
    private BigDecimal otherFee;//其它费
    private BigDecimal money;//金额
    private Long payId;//报销/付款申请ID
    private String secondCode;//二级费项编码
    private String threeCode;//部门费用预算详细表ID
    private Long projectId;//项目ID
    private Long pid;//立项ID
    private Date applyDate;//0
    private Integer year;//实际发生年度
    private String month;//实际发生月份
    private Date createDate;//创建日期

    @TableField
    private String secondName;//二级费项名称
    @TableField
    private String threeName;//三级费项名称

    public MarketCostReimbursementDetail() {
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

    public void setReimbursementId(Long reimbursementId){//报销明细ID
        this.reimbursementId = reimbursementId;
    }
    public Long getReimbursementId(){//报销明细ID
        return this.reimbursementId;
    }

    public void setType(Short type){//报销明细类型 1营销合同 2框架合同 3营销费用 4营销差旅费 5营销费用发生
        this.type = type;
    }
    public Short getType(){//报销明细类型 1营销合同 2框架合同 3营销费用 4营销差旅费 5营销费用发生
        return this.type;
    }

    public void setSort(Short sort){//序号
        this.sort = sort;
    }
    public Short getSort(){//序号
        return this.sort;
    }

    public void setReason(String reason){//事由
        this.reason = reason;
    }
    public String getReason(){//事由
        return this.reason;
    }

    public void setStartDate(Date startDate){//开始日期
        this.startDate = startDate;
    }
    public Date getStartDate(){//开始日期
        return this.startDate;
    }

    public void setEndDate(Date endDate){//结束日期
        this.endDate = endDate;
    }
    public Date getEndDate(){//结束日期
        return this.endDate;
    }

    public void setAllDay(String allDay){//天数
        this.allDay = allDay;
    }
    public String getAllDay(){//天数
        return this.allDay;
    }

    public void setStartSite(String startSite){//开始地点
        this.startSite = startSite;
    }
    public String getStartSite(){//开始地点
        return this.startSite;
    }

    public void setEndSite(String endSite){//结束地点
        this.endSite = endSite;
    }
    public String getEndSite(){//结束地点
        return this.endSite;
    }

    public void setCarFee(BigDecimal carFee){//交通费
        this.carFee = carFee;
    }
    public BigDecimal getCarFee(){//交通费
        return this.carFee;
    }

    public void setStayFee(BigDecimal stayFee){//住宿费
        this.stayFee = stayFee;
    }
    public BigDecimal getStayFee(){//住宿费
        return this.stayFee;
    }

    public void setMealFee(BigDecimal mealFee){//餐费
        this.mealFee = mealFee;
    }
    public BigDecimal getMealFee(){//餐费
        return this.mealFee;
    }

    public void setOtherFee(BigDecimal otherFee){//其它费
        this.otherFee = otherFee;
    }
    public BigDecimal getOtherFee(){//其它费
        return this.otherFee;
    }

    public void setMoney(BigDecimal money){//金额
        this.money = money;
    }
    public BigDecimal getMoney(){//金额
        return this.money;
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

    public void setThreeCode(String threeCode){//部门费用预算详细表ID
        this.threeCode = threeCode;
    }
    public String getThreeCode(){//部门费用预算详细表ID
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

    public void setApplyDate(Date applyDate){//0
        this.applyDate = applyDate;
    }
    public Date getApplyDate(){//0
        return this.applyDate;
    }

    public void setYear(Integer year){//实际发生年度
        this.year = year;
    }
    public Integer getYear(){//实际发生年度
        return this.year;
    }

    public void setMonth(String month){//实际发生月份
        this.month = month;
    }
    public String getMonth(){//实际发生月份
        return this.month;
    }

    public void setCreateDate(Date createDate){//创建日期
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建日期
        return this.createDate;
    }


}
