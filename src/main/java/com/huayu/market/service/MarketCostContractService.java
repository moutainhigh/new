package com.huayu.market.service;

import com.huayu.common.tool.DateTimeUtil;
import com.huayu.market.dao.MarketCostContractDao;
import com.huayu.market.domain.MarketCostContract;
import com.huayu.market.domain.MarketCostContractExecuteDetail;
import com.huayu.market.domain.MarketCostContractSplitDetail;
import com.huayu.market.domain.MarketCostContractTemp;
import com.huayu.p.web.tools.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 营销合同审批表
 * @author ZXL 2017-07-27 15:50
 **/
@Service
public class MarketCostContractService {

    @Autowired
    private MarketCostContractDao marketCostContractDao;
    @Resource
    private MarketCostContractSplitDetailService marketCostContractSplitDetailService;
    @Resource
    private MarketCostContractExecuteDetailService marketCostContractExecuteDetailService;

    /**
     * 获取数据为台账
     * @param contractId
     * @return
     */
    public MarketCostContract getOneOfLedger(Long contractId){
        MarketCostContract marketCostContract = new MarketCostContract();
        marketCostContract.setContractId(contractId);
        return marketCostContractDao.getOneOfLedger(marketCostContract);
    }

    /**
     * 营销合同
     * 获取数据为台账
     * @param contractId
     * @return
     */
    public MarketCostContract getMarketingContractOfLedger(Long contractId) throws Exception{
        MarketCostContract marketCostContract = this.getOneOfLedger(contractId);
        if (null==marketCostContract){
            throw new CustomException("合同数据不存在");
        }
        List<MarketCostContractSplitDetail> marketCostContractSplitDetailList = marketCostContractSplitDetailService.getAllByContractIdOfLedger(contractId);
        marketCostContract.setMarketCostContractSplitDetailList(marketCostContractSplitDetailList);
        return marketCostContract;
    }

    /**
     * 框架合同
     * 获取数据为台账
     * @param contractId
     * @return
     */
    public MarketCostContract getFrameworkContractOfLedger(Long contractId) throws Exception{
        MarketCostContract marketCostContract = this.getOneOfLedger(contractId);
        if (null==marketCostContract){
            throw new CustomException("合同数据不存在");
        }
        List<MarketCostContractExecuteDetail> marketCostContractExecuteDetailList = marketCostContractExecuteDetailService.getAllByContractIdOfLedger(contractId);
        marketCostContract.setMarketCostContractExecuteDetailList(marketCostContractExecuteDetailList);
        return marketCostContract;
    }

    /**
     * 营销费用发生明细
     * @param belongMonth
     * @param threeCode
     * @return
     */
    public List<MarketCostContract> getHappenDetailOfItemAndMonth(Long projectId,String belongMonth,String threeCode){
        MarketCostContract marketCostContract = new MarketCostContract();
        marketCostContract.setBelongMonth(belongMonth);
        marketCostContract.setThreeCode(threeCode);
        marketCostContract.setProjectId(projectId);
        return marketCostContractDao.getHappenDetailOfItemAndMonth(marketCostContract);
    }

    /**
     * 台账列表
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public Page<MarketCostContract> getOfLedger(Pageable pageable,MarketCostContract marketCostContract){
        return marketCostContractDao.getOfLedger(pageable, marketCostContract);
    }

    /**
     * 获取已支付金额
     * @param marketCostContract
     * @return
     */
    public BigDecimal getOneToAllPayMoney(Long payId){
        MarketCostContract marketCostContract = new MarketCostContract();
        marketCostContract.setPayId(payId);
        marketCostContract = marketCostContractDao.getOneToAllPayMoney(marketCostContract);
        return null==marketCostContract?new BigDecimal("0"):marketCostContract.getPaidMoney();
    }

    /**
     * 更新已支付
     * @param marketCostContract
     * @return
     */
    @Transactional
    public int putToPaidMoney(Long contractId,BigDecimal paidMoney,BigDecimal payMoney){
        MarketCostContract marketCostContract = new MarketCostContract();
        marketCostContract.setContractId(contractId);
        marketCostContract.setPaidMoney(paidMoney);
        marketCostContract.setPayMoney(payMoney);
        return marketCostContractDao.putToPaidMoney(marketCostContract);
    }

    /*报表*/

    /**
     * 报表功能
     * 营销费用发生明细
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public Page<MarketCostContract> getHappenDetailOfReport(Pageable pageable, MarketCostContract marketCostContract){
        if (StringUtils.isBlank(marketCostContract.getD3())||StringUtils.isBlank(marketCostContract.getD4())){
            marketCostContract.setD3(DateTimeUtil.getMinOrMaxDate(DateTimeUtil.min, DateTimeUtil.YYYY_MM_DD)+" 00:00:00");
            marketCostContract.setD4(DateTimeUtil.getMinOrMaxDate(DateTimeUtil.max, DateTimeUtil.YYYY_MM_DD)+" 23:59:59");
        } else {
            marketCostContract.setD3(marketCostContract.getD3()+" 00:00:00");
            marketCostContract.setD4(marketCostContract.getD4()+" 23:59:59");
        }
        return marketCostContractDao.getHappenDetailOfReport(pageable,marketCostContract);
    }

    /*导入数据*/

    /**
     * 导入数据-自定义添加数据
     * @param marketCostContract
     * @return
     */
    @Transactional
    public int definitionPostOfImport(MarketCostContractTemp marketCostContractTemp){
        MarketCostContract marketCostContract = new MarketCostContract();
        marketCostContract.setContractId(marketCostContractTemp.getContractId());
        marketCostContract.setType(marketCostContractTemp.getType());
        marketCostContract.setStatus(marketCostContractTemp.getStatus());
        marketCostContract.setContractCode(marketCostContractTemp.getContractCode());
        marketCostContract.setContractName(marketCostContractTemp.getContractName());
        marketCostContract.setAunit(marketCostContractTemp.getAunit());
        marketCostContract.setAunitId(marketCostContractTemp.getAunitId());
        marketCostContract.setBunit(marketCostContractTemp.getBunit());
        marketCostContract.setProjectId(marketCostContractTemp.getProjectId());
        marketCostContract.setPid(marketCostContractTemp.getPid());
        marketCostContract.setContractMoney(marketCostContractTemp.getContractMoney());
        marketCostContract.setAgentUserId(marketCostContractTemp.getAgentUserId());
        marketCostContract.setAgentName(marketCostContractTemp.getAgentName());
        marketCostContract.setApplyDate(DateTimeUtil.convertStringToDate(marketCostContractTemp.getApplyDate(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        marketCostContract.setStartDate(marketCostContractTemp.getStartDate());
        marketCostContract.setEndDate(marketCostContractTemp.getEndDate());
        marketCostContract.setRemark(marketCostContractTemp.getRemark());
        marketCostContract.setIsAutoSplit(marketCostContractTemp.getIsAutoSplit());
        marketCostContract.setMonths(marketCostContractTemp.getMonths());
        return marketCostContractDao.definitionPostOfImport(marketCostContract);
    }

}
