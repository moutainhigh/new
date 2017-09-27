package com.huayu.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时任务
 * @author ZXL 2017-05-27 15:42
 **/
@Service
public class MarketTimingService {

    @Autowired
    private MarketCostApprovalService marketCostApprovalService;

    /**
     * 预警发送短信
     */
    @Scheduled(cron="0 0 1 * * ?")
    public void autoRecoveryData(){
        marketCostApprovalService.autoRecoveryData();
    }


}
