package com.huayu.market.domain;

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
* 营销费用发生登记
* Created by ZXL on 2017-9-4 11:11:54.
*/
@Table(name = "market_cost_apply_repair")
public class MarketCostApplyRepair extends BaseDomain  implements Serializable{

 //private String updateFiledKey = payId,title,serialsNumber,status,agentUserId,applyName,applyDate,affiliationDate,remark,createDate,successDate,payUserId,payDate,billMoney,paperTitle,money;
 //private String updateFiledKeyValue = :payId,:title,:serialsNumber,:status,:agentUserId,:applyName,:applyDate,:affiliationDate,:remark,:createDate,:successDate,:payUserId,:payDate,:billMoney,:paperTitle,:money;
 //private String updateFiledValue = payId=:payId,title=:title,serialsNumber=:serialsNumber,status=:status,agentUserId=:agentUserId,applyName=:applyName,applyDate=:applyDate,affiliationDate=:affiliationDate,remark=:remark,createDate=:createDate,successDate=:successDate,payUserId=:payUserId,payDate=:payDate,billMoney=:billMoney,paperTitle=:paperTitle,money=:money;
    private Long payId;//付款ID
    private String title;//主题
    private String serialsNumber;//流水号
    private Integer status;//状态 1申请中 2已完成 3已撤销 4已付款 5部分付款
    private Long agentUserId;//经办人
    private String applyName;//经办人姓名
    private Date applyDate;//申请日期
    private String affiliationDate;//费用归属年月
    private String remark;//备注
    private Date createDate;//创建日期
    private Date successDate;//流程完成时间
    private Long payUserId;//支付人员ID
    private Date payDate;//支付时间
    private BigDecimal billMoney;//发票金额
    private String paperTitle;//摘要标题
    private BigDecimal money;//金额

    @TableField
    private String d3;//时间
    @TableField
    private String d4;//时间
    @TableField
    private List<MarketCostApplyRepairDetail> marketCostApplyRepairDetailList = new ArrayList<>();//营销费用发生登记明细

    public MarketCostApplyRepair() {
        this.dt = "desc";
        this.dtn = "payId";
        this.pageSize = 10;
    }

    public List<MarketCostApplyRepairDetail> getMarketCostApplyRepairDetailList() {
        return marketCostApplyRepairDetailList;
    }

    public void setMarketCostApplyRepairDetailList(List<MarketCostApplyRepairDetail> marketCostApplyRepairDetailList) {
        this.marketCostApplyRepairDetailList = marketCostApplyRepairDetailList;
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

    public void setPayId(Long payId){//付款ID
        this.payId = payId;
    }
    public Long getPayId(){//付款ID
        return this.payId;
    }

    public void setTitle(String title){//主题
        this.title = title;
    }
    public String getTitle(){//主题
        return this.title;
    }

    public void setSerialsNumber(String serialsNumber){//流水号
        this.serialsNumber = serialsNumber;
    }
    public String getSerialsNumber(){//流水号
        return this.serialsNumber;
    }

    public void setStatus(Integer status){//状态 1申请中 2已完成 3已撤销 4已付款 5部分付款
        this.status = status;
    }
    public Integer getStatus(){//状态 1申请中 2已完成 3已撤销 4已付款 5部分付款
        return this.status;
    }

    public void setAgentUserId(Long agentUserId){//经办人
        this.agentUserId = agentUserId;
    }
    public Long getAgentUserId(){//经办人
        return this.agentUserId;
    }

    public void setApplyName(String applyName){//经办人姓名
        this.applyName = applyName;
    }
    public String getApplyName(){//经办人姓名
        return this.applyName;
    }

    public void setApplyDate(Date applyDate){//申请日期
        this.applyDate = applyDate;
    }
    public Date getApplyDate(){//申请日期
        return this.applyDate;
    }

    public void setAffiliationDate(String affiliationDate){//费用归属年月
        this.affiliationDate = affiliationDate;
    }
    public String getAffiliationDate(){//费用归属年月
        return this.affiliationDate;
    }

    public void setRemark(String remark){//备注
        this.remark = remark;
    }
    public String getRemark(){//备注
        return this.remark;
    }

    public void setCreateDate(Date createDate){//创建日期
        this.createDate = createDate;
    }
    public Date getCreateDate(){//创建日期
        return this.createDate;
    }

    public void setSuccessDate(Date successDate){//流程完成时间
        this.successDate = successDate;
    }
    public Date getSuccessDate(){//流程完成时间
        return this.successDate;
    }

    public void setPayUserId(Long payUserId){//支付人员ID
        this.payUserId = payUserId;
    }
    public Long getPayUserId(){//支付人员ID
        return this.payUserId;
    }

    public void setPayDate(Date payDate){//支付时间
        this.payDate = payDate;
    }
    public Date getPayDate(){//支付时间
        return this.payDate;
    }

    public void setBillMoney(BigDecimal billMoney){//发票金额
        this.billMoney = billMoney;
    }
    public BigDecimal getBillMoney(){//发票金额
        return this.billMoney;
    }

    public void setPaperTitle(String paperTitle){//摘要标题
        this.paperTitle = paperTitle;
    }
    public String getPaperTitle(){//摘要标题
        return this.paperTitle;
    }

    public void setMoney(BigDecimal money){//金额
        this.money = money;
    }
    public BigDecimal getMoney(){//金额
        return this.money;
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
