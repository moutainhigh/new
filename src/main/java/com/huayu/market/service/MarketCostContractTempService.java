package com.huayu.market.service;

import com.huayu.common.tool.DateTimeUtil;
import com.huayu.market.dao.MarketCostContractTempDao;
import com.huayu.market.domain.MarketCostContractExecute;
import com.huayu.market.domain.MarketCostContractTemp;
import com.huayu.market.domain.MarketCostContractTempDetail;
import com.huayu.p.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 营销合同审批表-导入临时表
 * @author ZXL 2017-08-23 16:02
 **/
@Service
public class MarketCostContractTempService {

    @Resource
    private MarketCostContractTempDao marketCostContractTempDao;
    @Resource
    private MarketCostContractTempDetailService marketCostContractTempDetailService;
    @Resource
    private MarketCostContractService marketCostContractService;
    @Resource
    private MarketCostContractMoneyService marketCostContractMoneyService;
    @Resource
    private MarketCostContractSplitDetailService marketCostContractSplitDetailService;
    @Resource
    private MarketCostContractExecuteService marketCostContractExecuteService;
    @Resource
    private MarketCostContractExecuteDetailService marketCostContractExecuteDetailService;

    public List<MarketCostContractTemp> get(){
        return marketCostContractTempDao.get(new MarketCostContractTemp());
    }

    @Transactional
    public int putContractId(Long id,Long contractId){
        MarketCostContractTemp marketCostContractTemp = new MarketCostContractTemp();
        marketCostContractTemp.setId(id);
        marketCostContractTemp.setContractId(contractId);
        return marketCostContractTempDao.putContractId(marketCostContractTemp);
    }

    /**
     * 合同临时表生成合同ID
     */
    public void bathPutContractId(){
        List<MarketCostContractTemp> marketCostContractTemps = this.get();
        for (MarketCostContractTemp marketCostContractTemp : marketCostContractTemps) {
            try {
                Long contractId = CommonUtil.getUUID();
                this.putContractId(marketCostContractTemp.getId(),contractId);
                marketCostContractTempDetailService.put(contractId,marketCostContractTemp.getContractCode());
            }catch (Exception e){

            }
        }
    }

    /**
     * 营销合同导入
     */
    @Transactional
    public void marketinitialize(){
        List<MarketCostContractTemp> marketCostContractTemps = this.get();
        for (MarketCostContractTemp marketCostContractTemp : marketCostContractTemps) {
            marketCostContractService.definitionPostOfImport(marketCostContractTemp);//添加合同
            List<MarketCostContractTempDetail> byContractIdForSecondCode = marketCostContractTempDetailService.getByContractIdForSecondCode(marketCostContractTemp.getContractId());//费项合并明细
            List<MarketCostContractTempDetail> byContractId = marketCostContractTempDetailService.getByContractId(marketCostContractTemp.getContractId());
            for (MarketCostContractTempDetail marketCostContractTempDetail : byContractIdForSecondCode) {//费项合并明细
                marketCostContractMoneyService.definitionPostOfImport(marketCostContractTempDetail.getContractMoney(),marketCostContractTemp.getContractId(),marketCostContractTempDetail.getSecondCode(),marketCostContractTemp.getProjectId(),marketCostContractTemp.getPid());
            }
            int count = 1;
            for (MarketCostContractTempDetail marketCostContractTempDetail : byContractId) {
                Integer year = Integer.valueOf(marketCostContractTempDetail.getApplyDate().substring(0,4));
                String month = marketCostContractTempDetail.getApplyDate().substring(5,7);
                Date currentDate = DateTimeUtil.convertStringToDate(marketCostContractTempDetail.getApplyDate(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS);
                marketCostContractSplitDetailService.definitionPostOfImport((short)1,count,marketCostContractTempDetail.getContractId(),0L,marketCostContractTempDetail.getSecondCode(),marketCostContractTempDetail.getThreeCode(),marketCostContractTemp.getProjectId(),marketCostContractTemp.getPid(),year,month,currentDate,marketCostContractTempDetail.getContractMoney());
                count++;
            }
        }
    }

    /**
     * 框架合同导入
     */
    @Transactional
    public void frameinitialize(){
        List<MarketCostContractTemp> marketCostContractTemps = this.get();
        for (MarketCostContractTemp marketCostContractTemp : marketCostContractTemps) {
            marketCostContractService.definitionPostOfImport(marketCostContractTemp);//添加合同
            MarketCostContractTempDetail oneCostContractTempDetail = marketCostContractTempDetailService.getOneByContractId(marketCostContractTemp.getContractId());//费项合并明细
            if (null!=oneCostContractTempDetail&&null!=oneCostContractTempDetail.getContractId()){
                //框架合同执行单
                MarketCostContractExecute marketCostContractExecute = marketCostContractExecuteService.definitionPostOfImport(marketCostContractTemp.getContractName(),marketCostContractTemp.getContractId(),marketCostContractTemp.getContractName(),marketCostContractTemp.getContractCode(),marketCostContractTemp.getProjectId(),marketCostContractTemp.getPid(),marketCostContractTemp.getApplyDate(),marketCostContractTemp.getAgentName(),oneCostContractTempDetail.getContractMoney(),marketCostContractTemp.getRemark());
                List<MarketCostContractTempDetail> byContractIdForSecondCode = marketCostContractTempDetailService.getByContractIdForSecondCode(marketCostContractTemp.getContractId());//费项合并明细
                List<MarketCostContractTempDetail> byContractId = marketCostContractTempDetailService.getByContractId(marketCostContractTemp.getContractId());
                for (MarketCostContractTempDetail marketCostContractTempDetail : byContractIdForSecondCode) {//费项合并明细
                    marketCostContractMoneyService.definitionPostOfImport(marketCostContractTempDetail.getContractMoney(),marketCostContractExecute.getExecuteId(),marketCostContractTempDetail.getSecondCode(),marketCostContractTemp.getProjectId(),marketCostContractTemp.getPid());
                }
                int count = 1;
                for (MarketCostContractTempDetail marketCostContractTempDetail : byContractId) {
                    Integer year = Integer.valueOf(marketCostContractTempDetail.getApplyDate().substring(0,4));
                    String month = marketCostContractTempDetail.getApplyDate().substring(5,7);
                    Date currentDate = DateTimeUtil.convertStringToDate(marketCostContractTempDetail.getApplyDate(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS);
                    marketCostContractExecuteDetailService.definitionPostOfImport(count,marketCostContractTemp.getProjectId(),marketCostContractExecute.getExecuteId(),marketCostContractTempDetail.getSecondCode(),marketCostContractTempDetail.getThreeCode(),marketCostContractExecute.getRemark(),currentDate,year,month,marketCostContractTempDetail.getContractMoney(),marketCostContractTemp.getPid(),marketCostContractTempDetail.getThreeName());
                    count++;
                }
            }
        }
    }

}
