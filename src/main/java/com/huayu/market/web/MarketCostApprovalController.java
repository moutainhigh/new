package com.huayu.market.web;

import com.huayu.market.domain.MarketCostApproval;
import com.huayu.market.service.MarketCostApprovalService;
import com.huayu.market.service.MarketCostCompanyService;
import com.huayu.market.service.MarketCostProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 月度立项审批单
 * @author ZXL 2017-07-03 13:09
 **/
@Controller
@RequestMapping("/market/approval")
public class MarketCostApprovalController {

    @Autowired
    private MarketCostApprovalService marketCostApprovalService;
    @Autowired
    private MarketCostProjectService marketCostProjectService;
    @Autowired
    private MarketCostCompanyService marketCostCompanyService;

    /**
     * uri:/market/approval/get
     * 月度立项审批单信息
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/get")
    public String signGet(ModelMap modelMap, @ModelAttribute("entity")MarketCostApproval entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        Page<MarketCostApproval> page = marketCostApprovalService.get(pageable,entity);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("companyList",marketCostCompanyService.getAll());
        return ".market.approval.approval_list";
    }


}
