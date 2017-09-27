package com.huayu.market.service;

import com.huayu.market.dao.MarketCostApprovalLogDao;
import com.huayu.market.domain.MarketCostApprovalLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 月度立项审批单恢复记录
 * @author ZXL 2017-07-03 15:35
 **/
@Service
public class MarketCostApprovalLogService {

    @Autowired
    private MarketCostApprovalLogDao marketCostApprovalLogDao;

    /**
     * 添加月度立项审批单恢复记录
     * @param pid
     * @param money
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int post(Long pid, BigDecimal money) throws Exception{
        MarketCostApprovalLog marketCostApprovalLog = new MarketCostApprovalLog();
        marketCostApprovalLog.setPid(pid);
        marketCostApprovalLog.setMoney(money);
        return marketCostApprovalLogDao.post(marketCostApprovalLog);
    }

}
