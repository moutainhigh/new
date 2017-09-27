package com.huayu.market.service;

import com.huayu.market.dao.MarketCostApplyRepairDetailDao;
import com.huayu.market.domain.MarketCostApplyRepairDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 营销费用发生登记明细
 * @author ZXL 2017-09-04 11:39
 **/
@Service
public class MarketCostApplyRepairDetailService {

    @Resource
    private MarketCostApplyRepairDetailDao marketCostApplyRepairDetailDao;

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostContractSplitDetail
     * @return
     */
    public List<MarketCostApplyRepairDetail> getAllByContractIdOfLedger(Long payId){
        MarketCostApplyRepairDetail marketCostApplyRepairDetail = new MarketCostApplyRepairDetail();
        marketCostApplyRepairDetail.setPayId(payId);
        return marketCostApplyRepairDetailDao.getAllByPayIdOfLedger(marketCostApplyRepairDetail);
    }

}
