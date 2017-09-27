package com.huayu.market.service;

import com.huayu.market.dao.MarketCostApprovalDao;
import com.huayu.market.domain.MarketCostApproval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 月度立项审批单
 * @author ZXL 2017-07-03 13:07
 **/
@Service
public class MarketCostApprovalService {

    private Logger log = LoggerFactory.getLogger(MarketCostApprovalService.class);

    @Autowired
    private MarketCostApprovalDao marketCostApprovalDao;
    @Autowired
    private MarketCostApprovalLogService marketCostApprovalLogService;
    @Autowired
    private MarketCostCompanyBudgetService marketCostCompanyBudgetService;
    @Autowired
    private MarketCostProjectBudgetService marketCostProjectBudgetService;

    /**
     * 获取
     * @param pageable
     * @param marketCostApproval
     * @return
     */
    public Page<MarketCostApproval> get(Pageable pageable, MarketCostApproval marketCostApproval){
        return marketCostApprovalDao.get(pageable,marketCostApproval);
    }

    /**
     * 自动取消订单
     */
    public void autoRecoveryData(){
        List<MarketCostApproval> marketCostApprovalList = marketCostApprovalDao.getToTimingOfApproval();
        for (MarketCostApproval m:marketCostApprovalList) {
            try {
                this.recoveryData(m);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 取消订单
     * @param shopOrder ShopOrder
     * @return
     * int
     */
    @Transactional(rollbackFor = Exception.class)
    public void recoveryData(MarketCostApproval marketCostApproval) throws Exception{
        BigDecimal  balanceMoney = marketCostApproval.getFinalMoney().subtract(marketCostApproval.getUsedMoney());
        int retNum = marketCostApprovalLogService.post(marketCostApproval.getPid(),balanceMoney);
        if (retNum>0){
            marketCostApprovalDao.putToTimingOfApproval(marketCostApproval);
            if (marketCostApproval.getIsOwn()==1){
                marketCostCompanyBudgetService.putToTimingOfApproval(marketCostApproval.getBudgetId(),balanceMoney);
            }
            marketCostProjectBudgetService.putToTimingOfApproval(marketCostApproval.getPbId(),balanceMoney);
        }
    }

}
