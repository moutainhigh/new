package com.huayu.market.service;

import com.huayu.market.dao.MarketCostReimbursementDetailDao;
import com.huayu.market.domain.MarketCostReimbursementDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 报销明细
 * @author ZXL 2017-08-30 17:42
 **/
@Service
public class MarketCostReimbursementDetailService {

    @Resource
    private MarketCostReimbursementDetailDao marketCostReimbursementDetailDao;

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostContractSplitDetail
     * @return
     */
    public List<MarketCostReimbursementDetail> getAllByContractIdOfLedger(Long payId){
        MarketCostReimbursementDetail marketCostReimbursementDetail = new MarketCostReimbursementDetail();
        marketCostReimbursementDetail.setPayId(payId);
        return marketCostReimbursementDetailDao.getAllByContractIdOfLedger(marketCostReimbursementDetail);
    }

}
