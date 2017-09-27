package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 项目预算费用预算更新记录表
* Created by DengPeng on 2017-7-4 11:32:29.
*/
@Table(name = "market_cost_project_budget_item_log")
public class MarketCostProjectBudgetItemLog extends BaseDomain  implements Serializable{

 //private String updateFiledKey = logId,pbiId,code,companyId,projectId,year,firstCode,secondCode,pbId,allPlanMoney,planMoney01,planMoney02,planMoney03,planMoney04,planMoney05,planMoney06,planMoney07,planMoney08,planMoney09,planMoney10,planMoney11,planMoney12,adjustMoney,adjustMoney01,adjustMoney02,adjustMoney03,adjustMoney04,adjustMoney05,adjustMoney06,adjustMoney07,adjustMoney08,adjustMoney09,adjustMoney10,adjustMoney11,adjustMoney12,updateMoney,createUserId,createDate;
 //private String updateFiledValue = logId=:logId,pbiId=:pbiId,code=:code,companyId=:companyId,projectId=:projectId,year=:year,firstCode=:firstCode,secondCode=:secondCode,pbId=:pbId,allPlanMoney=:allPlanMoney,planMoney01=:planMoney01,planMoney02=:planMoney02,planMoney03=:planMoney03,planMoney04=:planMoney04,planMoney05=:planMoney05,planMoney06=:planMoney06,planMoney07=:planMoney07,planMoney08=:planMoney08,planMoney09=:planMoney09,planMoney10=:planMoney10,planMoney11=:planMoney11,planMoney12=:planMoney12,adjustMoney=:adjustMoney,adjustMoney01=:adjustMoney01,adjustMoney02=:adjustMoney02,adjustMoney03=:adjustMoney03,adjustMoney04=:adjustMoney04,adjustMoney05=:adjustMoney05,adjustMoney06=:adjustMoney06,adjustMoney07=:adjustMoney07,adjustMoney08=:adjustMoney08,adjustMoney09=:adjustMoney09,adjustMoney10=:adjustMoney10,adjustMoney11=:adjustMoney11,adjustMoney12=:adjustMoney12,updateMoney=:updateMoney,createUserId=:createUserId,createDate=:createDate;
    private Long logId;
    private Long pbiId;
    private String code;
    private Integer companyId;
    private Long projectId;
    private Integer year;
    private String firstCode;
    private String secondCode;
    private Long pbId;
    private BigDecimal allPlanMoney;
    private BigDecimal planMoney01;
    private BigDecimal planMoney02;
    private BigDecimal planMoney03;
    private BigDecimal planMoney04;
    private BigDecimal planMoney05;
    private BigDecimal planMoney06;
    private BigDecimal planMoney07;
    private BigDecimal planMoney08;
    private BigDecimal planMoney09;
    private BigDecimal planMoney10;
    private BigDecimal planMoney11;
    private BigDecimal planMoney12;
    private BigDecimal adjustMoney;
    private BigDecimal adjustMoney01;
    private BigDecimal adjustMoney02;
    private BigDecimal adjustMoney03;
    private BigDecimal adjustMoney04;
    private BigDecimal adjustMoney05;
    private BigDecimal adjustMoney06;
    private BigDecimal adjustMoney07;
    private BigDecimal adjustMoney08;
    private BigDecimal adjustMoney09;
    private BigDecimal adjustMoney10;
    private BigDecimal adjustMoney11;
    private BigDecimal adjustMoney12;
    private BigDecimal updateMoney;
    private Long createUserId;
    private Date createDate;

    public MarketCostProjectBudgetItemLog() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }
}
