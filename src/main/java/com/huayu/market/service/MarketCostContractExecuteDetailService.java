package com.huayu.market.service;

import com.huayu.market.dao.MarketCostContractExecuteDetailDao;
import com.huayu.market.domain.MarketCostContractExecuteDetail;
import com.huayu.p.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 框架合同执行单/量价确认单明细
 * @author ZXL 2017-08-24 15:00
 **/
@Service
public class MarketCostContractExecuteDetailService {

    @Resource
    private MarketCostContractExecuteDetailDao marketCostContractExecuteDetailDao;

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param marketCostContractExecuteDetail
     * @return
     */
    public List<MarketCostContractExecuteDetail> getAllByContractIdOfLedger(Long contractId){
        MarketCostContractExecuteDetail marketCostContractExecuteDetail = new MarketCostContractExecuteDetail();
        marketCostContractExecuteDetail.setExecuteId(contractId);
        return marketCostContractExecuteDetailDao.getAllByContractIdOfLedger(marketCostContractExecuteDetail);
    }

    @Transactional
    public int definitionPostOfImport(Integer sort, Long projectId, Long executeId, String secondCode, String threeCode, String reason, Date happenDate, Integer year, String month, BigDecimal price,Long pid,String threeName){
        MarketCostContractExecuteDetail marketCostContractExecuteDetail = new MarketCostContractExecuteDetail();
        marketCostContractExecuteDetail.setDetailId(CommonUtil.getUUID());
        marketCostContractExecuteDetail.setType((short)1);
        marketCostContractExecuteDetail.setSort(sort);
        marketCostContractExecuteDetail.setProjectId(projectId);
        marketCostContractExecuteDetail.setExecuteId(executeId);
        marketCostContractExecuteDetail.setSecondCode(secondCode);
        marketCostContractExecuteDetail.setThreeCode(threeCode);
        marketCostContractExecuteDetail.setReason(reason);
        marketCostContractExecuteDetail.setHappenDate(happenDate);
        marketCostContractExecuteDetail.setYear(year);
        marketCostContractExecuteDetail.setMonth(month);
        marketCostContractExecuteDetail.setPrice(price);
        marketCostContractExecuteDetail.setNumber(1);
        marketCostContractExecuteDetail.setFinalMoney(price);
        marketCostContractExecuteDetail.setPid(pid);
        marketCostContractExecuteDetail.setThreeName(threeName);
        return marketCostContractExecuteDetailDao.definitionPostOfImport(marketCostContractExecuteDetail);
    }

}
