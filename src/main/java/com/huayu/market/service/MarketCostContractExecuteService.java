package com.huayu.market.service;

import com.huayu.common.tool.DateTimeUtil;
import com.huayu.market.dao.MarketCostContractExecuteDao;
import com.huayu.market.domain.MarketCostContractExecute;
import com.huayu.p.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 框架合同执行单
 * @author ZXL 2017-08-24 13:46
 **/
@Service
public class MarketCostContractExecuteService {

    @Resource
    private MarketCostContractExecuteDao marketCostContractExecuteDao;

    @Transactional
    public MarketCostContractExecute definitionPostOfImport(String title, Long contractId, String contractName, String contractCode, Long projectId, Long pid, String applyDate, String agentName, BigDecimal applyMoney, String remark){
        MarketCostContractExecute marketCostContractExecute = new MarketCostContractExecute();
        marketCostContractExecute.setExecuteId(CommonUtil.getUUID());
        marketCostContractExecute.setStatus((short)2);
        marketCostContractExecute.setUseStatus((short)1);
        marketCostContractExecute.setTitle(title);
        marketCostContractExecute.setSerialsNumber("");
        marketCostContractExecute.setContractId(contractId);
        marketCostContractExecute.setContractName(contractName);
        marketCostContractExecute.setContractCode(contractCode);
        marketCostContractExecute.setProjectId(projectId);
        marketCostContractExecute.setPid(pid);
        marketCostContractExecute.setApplyDate(DateTimeUtil.convertStringToDate(applyDate, DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        marketCostContractExecute.setDepartmentId(0L);
        marketCostContractExecute.setAgentUserId(0L);
        marketCostContractExecute.setAgentName(agentName);
        marketCostContractExecute.setApplyMoney(applyMoney);
        marketCostContractExecute.setRemark(remark);
        return marketCostContractExecuteDao.definitionPostOfImport(marketCostContractExecute)>0?marketCostContractExecute:null;
    }

}
