package com.huayu.market.web;

import com.alibaba.fastjson.JSON;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.market.domain.MarketCostProjectBudget;
import com.huayu.market.domain.MarketCostProjectBudgetItem;
import com.huayu.market.service.*;
import com.huayu.p.util.CommonUtil;
import com.huayu.p.web.tools.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 营销费用预算
 * @author ZXL 2017-06-28 9:34
 **/
@Controller
@RequestMapping("/market/budget")
public class MarketCostBudgetController {

    @Autowired
    private MarketCostCompanyService marketCostCompanyService;
    @Autowired
    private MarketYearService marketYearService;
    @Autowired
    private MarketCostProjectBudgetService marketCostProjectBudgetService;
    @Autowired
    private MarketCostProjectService marketCostProjectService;
    @Autowired
    private MarketCostItemService marketCostItemService;
    @Autowired
    private MarketCostProjectBudgetItemService marketCostProjectBudgetItemService;

    /**
     * uri:/market/budget/get
     * 营销费用年度预算列表
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/get")
    public String get(ModelMap modelMap, @ModelAttribute("entity")MarketCostProjectBudget entity){
        entity.setI1(CommonUtil.isInt(entity.getI1())?entity.getI1():1001);
        entity.setI2(CommonUtil.isInt(entity.getI2())?entity.getI2():2017);
        List<MarketCostProjectBudget> marketCostProjectBudgetList = marketCostProjectBudgetService.getToBudget(entity);
        modelMap.addAttribute("marketCostProjectBudgetList",marketCostProjectBudgetList);
        modelMap.addAttribute("companyList",marketCostCompanyService.getAll());
        modelMap.addAttribute("yearList",marketYearService.getAll());
        return ".market.budget.list";
    }

    /**
     * uri:/market/budget/addpage/{companyId}/{year}
     * 营销费用年度预算列表
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/addpage/{companyId}/{year}")
    public String addpage(ModelMap modelMap, @PathVariable("companyId") Integer companyId, @PathVariable("year") Integer year){
        modelMap.addAttribute("company",marketCostCompanyService.getOne(companyId));
        modelMap.addAttribute("year",year);
        modelMap.addAttribute("userName",SpringSecurityUtil.getUser().getName());
        modelMap.addAttribute("marketCostItemList",marketCostItemService.getAll());
        return ".market.budget.add";
    }

    /**
     * uri:/market/budget/getproject/{companyId}/{year}
     * 分页获取父类数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/getproject/{companyId}/{year}")
    public String getproject(ModelMap modelMap, @PathVariable("companyId") Integer companyId, @PathVariable("year") Integer year){
        modelMap.addAttribute("projectList",marketCostProjectService.getAllByCompanyIdAndYear(companyId,year));
        return "/market/budget/select_project_list";
    }

    /**
     * uri:/market/budget/put
     * 添加新数据
     * @param empListStr 数据
     * @return
     * String
     */
    @RequestMapping(value = "/put",method = RequestMethod.POST)
    public @ResponseBody
    String put(Long projectId,Integer companyId,Integer year,String empListStr) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList = JSON.parseArray(empListStr, MarketCostProjectBudgetItem.class);
            marketCostProjectBudgetItemService.batchPut(projectId,companyId,year,marketCostProjectBudgetItemList);
            result.put("msg","编制预算成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","编制预算失败");
        }
        return JSON.toJSONString(result);
    }

}
