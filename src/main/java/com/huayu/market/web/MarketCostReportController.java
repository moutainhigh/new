package com.huayu.market.web;

import com.huayu.cost.domain.CostApplyLoan;
import com.huayu.cost.service.CostApplyLoanService;
import com.huayu.market.domain.MarketCostContract;
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
 * 营销费用报表功能
 * @author ZXL 2017-08-23 10:22
 **/
@Controller
@RequestMapping("/market/report")
public class MarketCostReportController {

    @Resource
    private MarketCostContractService marketCostContractService;
    @Resource
    private CostApplyLoanService costApplyLoanService;

    /**
     * uri:/market/report/happendetail
     * 报表功能
     * 营销费用发生明细
     * @param modelMap
     * @param marketCostContract
     * @return
     */
    @RequestMapping(value = "/happendetail")
    public String happendetail(ModelMap modelMap,@ModelAttribute("entity") MarketCostContract marketCostContract){
        Pageable pageable = new PageRequest(marketCostContract.getPageNo(),marketCostContract.getPageSize());
        Page<MarketCostContract> page = marketCostContractService.getHappenDetailOfReport(pageable,marketCostContract);
        modelMap.addAttribute("page",page);
        return ".market.report.report_happendetail_list";
    }

    /**
     * uri:/market/report/outofbalance
     * 报表功能
     * 营销费用发生明细
     * @param modelMap
     * @param marketCostContract
     * @return
     */
    @RequestMapping(value = "/outofbalance")
    public String outofbalance(ModelMap modelMap,@ModelAttribute("entity") CostApplyLoan costApplyLoan){
        Pageable pageable = new PageRequest(costApplyLoan.getPageNo(),costApplyLoan.getPageSize());
        Page<CostApplyLoan> page = costApplyLoanService.getOutOfAccountOfReport(pageable,costApplyLoan);
        modelMap.addAttribute("page",page);
        return ".market.report.report_outofbalance_list";
    }

}
