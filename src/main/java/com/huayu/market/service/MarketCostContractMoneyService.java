package com.huayu.market.service;

import com.huayu.market.dao.MarketCostContractMoneyDao;
import com.huayu.market.domain.MarketCostContractMoney;
import com.huayu.p.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * market_cost_contract_money
 * @author ZXL 2017-08-23 17:32
 **/
@Service
public class MarketCostContractMoneyService {

    @Resource
    private MarketCostContractMoneyDao marketCostContractMoneyDao;

    /*导入数据*/

    /**
     * 导入数据-自定义添加数据
     * @param marketCostContractMoney
     * @return
     */
    @Transactional
    public int definitionPostOfImport(BigDecimal applyMoney,Long payId,String secondCode,Long projectId,Long pid){
        MarketCostContractMoney marketCostContractMoney = new MarketCostContractMoney();
        marketCostContractMoney.setDid(CommonUtil.getUUID());
        marketCostContractMoney.setApplyMoney(applyMoney);
        marketCostContractMoney.setPayId(payId);
        marketCostContractMoney.setSecondCode(secondCode);
        marketCostContractMoney.setProjectId(projectId);
        marketCostContractMoney.setPid(pid);
        return marketCostContractMoneyDao.definitionPostOfImport(marketCostContractMoney);
    }


}
