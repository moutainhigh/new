package com.huayu.market.web;

import com.huayu.market.domain.MarketCostApplyPay;
import com.huayu.market.domain.MarketCostApplyRepair;
import com.huayu.market.domain.MarketCostContract;
import com.huayu.market.service.MarketCostApplyPayService;
import com.huayu.market.service.MarketCostApplyRepairService;
import com.huayu.market.service.MarketCostContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 管理费用台账管理
 * @author ZXL 2017-09-01 15:02
 **/
@Controller
@RequestMapping("/market/ledger")
public class MarketledgerController {

    @Resource
    private MarketCostContractService marketCostContractService;
    @Resource
    private MarketCostApplyPayService marketCostApplyPayService;
    @Resource
    private MarketCostApplyRepairService marketCostApplyRepairService;

    /**
     * uri:/market/ledger/contractlist
     * 台账
     * 合同台账
     * @param modelMap
     * @param marketCostContract
     * @return
     */
    @RequestMapping(value = "/contractlist")
    public String contractlist(ModelMap modelMap, @ModelAttribute("entity") MarketCostContract marketCostContract){
        Pageable pageable = new PageRequest(marketCostContract.getPageNo(),marketCostContract.getPageSize());
        Page<MarketCostContract> page = marketCostContractService.getOfLedger(pageable,marketCostContract);
        modelMap.addAttribute("page",page);
        return ".market.ledger.ledger_contract_list";
    }

    /**
     * uri:/market/ledger/applypaylist
     * 台账
     * 营销费用付款台账
     * @param modelMap
     * @param marketCostContract
     * @return
     */
    @RequestMapping(value = "/applypaylist")
    public String applypaylist(ModelMap modelMap, @ModelAttribute("entity") MarketCostApplyPay marketCostApplyPay){
        Pageable pageable = new PageRequest(marketCostApplyPay.getPageNo(),marketCostApplyPay.getPageSize());
        Page<MarketCostApplyPay> page = marketCostApplyPayService.getOfLedger(pageable,marketCostApplyPay);
        modelMap.addAttribute("page",page);
        return ".market.ledger.ledger_applypay_list";
    }

    /**
     * uri:/market/ledger/applyrepairlist
     * 台账
     * 营销费用发生登记台账
     * @param modelMap
     * @param marketCostContract
     * @return
     */
    @RequestMapping(value = "/applyrepairlist")
    public String applyrepairlist(ModelMap modelMap, @ModelAttribute("entity") MarketCostApplyRepair marketCostApplyRepair){
        Pageable pageable = new PageRequest(marketCostApplyRepair.getPageNo(),marketCostApplyRepair.getPageSize());
        Page<MarketCostApplyRepair> page = marketCostApplyRepairService.getOfLedger(pageable,marketCostApplyRepair);
        modelMap.addAttribute("page",page);
        return ".market.ledger.ledger_applyrepair_list";
    }

}
