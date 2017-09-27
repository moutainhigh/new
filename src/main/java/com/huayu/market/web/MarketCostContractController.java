package com.huayu.market.web;

import com.huayu.market.service.MarketCostApplyPayService;
import com.huayu.market.service.MarketCostApplyRepairService;
import com.huayu.market.service.MarketCostContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 营销合同审批表
 * @author ZXL 2017-08-29 17:45
 **/
@Controller
@RequestMapping("/market/contract")
public class MarketCostContractController {

    @Resource
    private MarketCostContractService marketCostContractService;
    @Resource
    private MarketCostApplyPayService marketCostApplyPayService;
    @Resource
    private MarketCostApplyRepairService marketCostApplyRepairService;

    /**
     * uri:/market/contract/marketledger/{contractId}
     * 台账
     * 营销合同
     * @param modelMap
     * @param contractId
     * @return
     */
    @RequestMapping("/marketledger/{contractId}")
    public String getMarketingContractOfLedger(ModelMap modelMap, @PathVariable("contractId") Long contractId) throws Exception{
        modelMap.addAttribute("marketCostContract",marketCostContractService.getMarketingContractOfLedger(contractId));
        return "/market/ledger/marketledger";
    }

    /**
     * uri:/market/contract/frameworkledger/{contractId}
     * 台账
     * 框架合同
     * @param modelMap
     * @param contractId
     * @return
     */
    @RequestMapping("/frameworkledger/{contractId}")
    public String getFrameworkContractOfLedger(ModelMap modelMap, @PathVariable("contractId") Long contractId) throws Exception{
        modelMap.addAttribute("marketCostContract",marketCostContractService.getFrameworkContractOfLedger(contractId));
        return "/market/ledger/frameworkledger";
    }

    /**
     * uri:/market/contract/payledger/{contractId}
     * 台账
     * 付款 费用/差旅费
     * @param modelMap
     * @param contractId
     * @return
     */
    @RequestMapping("/payledger/{contractId}")
    public String getPayOfLedger(ModelMap modelMap, @PathVariable("contractId") Long contractId) throws Exception{
        modelMap.addAttribute("marketCostContract",marketCostApplyPayService.getPayOfLedger(contractId));
        return "/market/ledger/payledger";
    }

    /**
     * uri:/market/contract/payrepairledger/{contractId}
     * 台账
     * 发生登记
     * @param modelMap
     * @param contractId
     * @return
     */
    @RequestMapping("/payrepairledger/{contractId}")
    public String getRepairOfLedger(ModelMap modelMap, @PathVariable("contractId") Long contractId) throws Exception{
        modelMap.addAttribute("marketCostContract",marketCostApplyRepairService.getPayOfLedger(contractId));
        return "/market/ledger/repairledger";
    }

}
