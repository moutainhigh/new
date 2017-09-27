package com.huayu.market.service;

import com.huayu.market.dao.MarketCostContractSplitDetailDao;
import com.huayu.market.domain.MarketCostContractSplitDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 营销合同审批表实际发生拆分
 * @author ZXL 2017-08-23 17:14
 **/
@Service
public class MarketCostContractSplitDetailService {

    @Resource
    private MarketCostContractSplitDetailDao marketCostContractSplitDetailDao;

    /**
     * 台账
     * 以合同ID为条件获取数据
     * @param contractId 合同ID
     * @return
     */
    public List<MarketCostContractSplitDetail> getAllByContractIdOfLedger(Long contractId){
        MarketCostContractSplitDetail marketCostContractSplitDetail = new MarketCostContractSplitDetail();
        marketCostContractSplitDetail.setContractId(contractId);
        return marketCostContractSplitDetailDao.getAllByContractIdOfLedger(marketCostContractSplitDetail);
    }

    /**
     * 导入数据-自定义添加数据
     * @param type
     * @param sort
     * @param contractId
     * @param reimbursementId
     * @param secondCode
     * @param threeCode
     * @param projectId
     * @param pid
     * @param year
     * @param month
     * @param currentDate
     * @param money
     * @return
     */
    @Transactional
    public int definitionPostOfImport(Short type, Integer sort, Long contractId, Long reimbursementId, String secondCode, String threeCode, Long projectId, Long pid, Integer year, String month, Date currentDate, BigDecimal money){
        MarketCostContractSplitDetail marketCostReimbursementDetail = new MarketCostContractSplitDetail();
        marketCostReimbursementDetail.setType(type);
        marketCostReimbursementDetail.setSort(sort);
        marketCostReimbursementDetail.setContractId(contractId);
        marketCostReimbursementDetail.setReimbursementId(reimbursementId);
        marketCostReimbursementDetail.setSecondCode(secondCode);
        marketCostReimbursementDetail.setThreeCode(threeCode);
        marketCostReimbursementDetail.setProjectId(projectId);
        marketCostReimbursementDetail.setPid(pid);
        marketCostReimbursementDetail.setYear(year);
        marketCostReimbursementDetail.setMonth(month);
        marketCostReimbursementDetail.setCurrentDate(currentDate);
        marketCostReimbursementDetail.setMoney(money);
        return marketCostContractSplitDetailDao.definitionPostOfImport(marketCostReimbursementDetail);
    }

}
