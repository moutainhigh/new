package com.huayu.market.service;

import com.huayu.market.dao.MarketCostApplyRepairDao;
import com.huayu.market.domain.MarketCostApplyRepair;
import com.huayu.p.web.tools.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 营销费用发生登记
 * @author ZXL 2017-09-04 11:30
 **/
@Service
public class MarketCostApplyRepairService {

    @Resource
    private MarketCostApplyRepairDao marketCostApplyRepairDao;
    @Resource
    private MarketCostApplyRepairDetailService marketCostApplyRepairDetailService;

    /**
     * 台账
     * 获取数据
     * @param marketCostApplyPay
     * @return
     */
    public MarketCostApplyRepair getOneOfLedger(Long payId){
        MarketCostApplyRepair marketCostApplyRepair = new MarketCostApplyRepair();
        marketCostApplyRepair.setPayId(payId);
        return marketCostApplyRepairDao.getOneOfLedger(marketCostApplyRepair);
    }

    /**
     * 台账
     * @param payId
     * @return
     * @throws Exception
     */
    public MarketCostApplyRepair getPayOfLedger(Long payId) throws Exception{
        MarketCostApplyRepair marketCostApplyRepair = this.getOneOfLedger(payId);
        if (null==marketCostApplyRepair){
            throw new CustomException("数据不存在");
        }
        marketCostApplyRepair.setMarketCostApplyRepairDetailList(marketCostApplyRepairDetailService.getAllByContractIdOfLedger(payId));
        return marketCostApplyRepair;
    }

    /**
     * 台账列表
     * @param pageable
     * @param marketCostApplyPay
     * @return
     */
    public Page<MarketCostApplyRepair> getOfLedger(Pageable pageable, MarketCostApplyRepair marketCostApplyRepair){
        return marketCostApplyRepairDao.getOfLedger(pageable, marketCostApplyRepair);
    }

}
