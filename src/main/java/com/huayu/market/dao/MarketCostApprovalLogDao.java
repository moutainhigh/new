package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostApprovalLog;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 月度立项审批单恢复记录
 * @author ZXL 2017-07-03 15:34
 **/
@Repository
public class MarketCostApprovalLogDao extends BasePageDao<MarketCostApprovalLog,Serializable>{

    /**
     * 添加月度立项审批单恢复记录
     * @param marketCostApprovalLog
     * @return
     */
    public int post(MarketCostApprovalLog marketCostApprovalLog){
        String sql = "INSERT INTO market_cost_approval_log (pid,money) VALUES (:pid,:money)";
        return super.post(sql,marketCostApprovalLog);
    }

}
