package com.huayu.market.service;

import com.huayu.market.dao.MarketCostContractTempDetailDao;
import com.huayu.market.domain.MarketCostContractTempDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 营销合同审批表-导入临时表
 * @author ZXL 2017-08-23 16:22
 **/
@Service
public class MarketCostContractTempDetailService {

    @Resource
    private MarketCostContractTempDetailDao marketCostContractTempDetailDao;

    public MarketCostContractTempDetail getOneByContractId(Long contractId){
        MarketCostContractTempDetail marketCostContractTempDetail = new MarketCostContractTempDetail();
        marketCostContractTempDetail.setContractId(contractId);
        return marketCostContractTempDetailDao.getOneByContractId(marketCostContractTempDetail);
    }

    public List<MarketCostContractTempDetail> getByContractIdForSecondCode(Long contractId){
        MarketCostContractTempDetail marketCostContractTempDetail = new MarketCostContractTempDetail();
        marketCostContractTempDetail.setContractId(contractId);
        return marketCostContractTempDetailDao.getByContractIdForSecondCode(marketCostContractTempDetail);
    }

    public List<MarketCostContractTempDetail> getByContractId(Long contractId){
        MarketCostContractTempDetail marketCostContractTempDetail = new MarketCostContractTempDetail();
        marketCostContractTempDetail.setContractId(contractId);
        return marketCostContractTempDetailDao.getByContractId(marketCostContractTempDetail);
    }

    @Transactional
    public int put(Long contractId,String contractCode){
        MarketCostContractTempDetail marketCostContractTempDetail = new MarketCostContractTempDetail();
        marketCostContractTempDetail.setContractId(contractId);
        marketCostContractTempDetail.setContractCode(contractCode);
        return marketCostContractTempDetailDao.put(marketCostContractTempDetail);
    }

}
