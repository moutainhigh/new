package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;

/**
 *
* Created by DengPeng on 2017-7-3 15:29:50.
*/
@Table(name = "market_cost_approval_log")
public class MarketCostApprovalLog extends BaseDomain  implements Serializable{

 //private String updateFiledKey = logId,pid,money,createDate;
 //private String updateFiledValue = logId=:logId,pid=:pid,money=:money,createDate=:createDate;
    private Long logId;
    private Long pid;
    private BigDecimal money;
    private Date createDate;

    public MarketCostApprovalLog() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
