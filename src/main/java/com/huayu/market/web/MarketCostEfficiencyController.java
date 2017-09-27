package com.huayu.market.web;

import com.alibaba.fastjson.JSON;
import com.huayu.market.domain.*;
import com.huayu.market.service.*;
import com.huayu.p.util.CommonUtil;
import com.huayu.p.web.tools.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 项目费效分析
 * @author ZXL 2017-06-28 9:34
 **/
@Controller
@RequestMapping("/market/efficiency")
public class MarketCostEfficiencyController {

    @Autowired
    private MarketCostCompanyService marketCostCompanyService;
    @Autowired
    private MarketYearService marketYearService;
    @Autowired
    private MarketCostProjectBudgetService marketCostProjectBudgetService;
    @Autowired
    private MarketCostProjectSignService marketCostProjectSignService;
    @Autowired
    private MarketCostProjectPlansignService marketCostProjectPlansignService;
    @Resource
    private MarketCostProjectBudgetItemService marketCostProjectBudgetItemService;
    @Resource
    private MarketCostProjectService marketCostProjectService;
    @Resource
    private MarketCostContractService marketCostContractService;

    /**
     * uri:/market/efficiency/sign/get
     * 项目签约信息
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/sign/get")
    public String signGet(ModelMap modelMap, @ModelAttribute("entity")MarketCostProjectSign entity){
        entity.setI1(CommonUtil.isInt(entity.getI1())?entity.getI1():1001);
        entity.setI2(CommonUtil.isInt(entity.getI2())?entity.getI2():2017);
        List<MarketCostProjectSign> marketCostProjectSignList = marketCostProjectSignService.getToSign(entity);
        modelMap.addAttribute("marketCostProjectSignList",marketCostProjectSignList);
        modelMap.addAttribute("companyList",marketCostCompanyService.getAll());
        modelMap.addAttribute("yearList",marketYearService.getAll());
        modelMap.addAttribute("length",marketCostProjectSignList.size());
        return ".market.efficiency.sign_list";
    }

    /**
     * uri:/market/efficiency/sign/put
     * 签约信息添加新数据
     * @param empListStr 数据
     * @return
     * String
     */
    @RequestMapping(value="/sign/put",method = RequestMethod.POST)
    public @ResponseBody
    String put(Integer year,String empListStr,String admListStr) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            List<MarketCostProjectSign> marketCostProjectSignList = JSON.parseArray(empListStr,MarketCostProjectSign.class);
            MarketCostProjectSign marketCostProjectSign = JSON.parseArray(admListStr,MarketCostProjectSign.class).get(0);
            marketCostProjectSignService.batchPut(year,marketCostProjectSignList,marketCostProjectSign);
            result.put("msg","保存成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","保存失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/market/efficiency/plansign/get
     * 项目预计签约信息
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/plansign/get")
    public String plansignGet(ModelMap modelMap, @ModelAttribute("entity")MarketCostProjectPlansign entity){
        entity.setI1(CommonUtil.isInt(entity.getI1())?entity.getI1():1001);
        entity.setI2(CommonUtil.isInt(entity.getI2())?entity.getI2():2017);
        List<MarketCostProjectPlansign> marketCostProjectSignList = marketCostProjectPlansignService.getToSign(entity);
        modelMap.addAttribute("marketCostProjectSignList",marketCostProjectSignList);
        modelMap.addAttribute("companyList",marketCostCompanyService.getAll());
        modelMap.addAttribute("yearList",marketYearService.getAll());
        modelMap.addAttribute("length",marketCostProjectSignList.size());
        return ".market.efficiency.plansign_list";
    }

    /**
     * uri:/market/efficiency/plansign/put
     * 预计签约信息添加新数据
     * @param empListStr 数据
     * @return
     * String
     */
    @RequestMapping(value="/plansign/put",method = RequestMethod.POST)
    public @ResponseBody
    String plansignput(Integer year,String empListStr,String admListStr) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            List<MarketCostProjectPlansign> marketCostProjectSignList = JSON.parseArray(empListStr,MarketCostProjectPlansign.class);
            MarketCostProjectPlansign marketCostProjectSign = JSON.parseArray(admListStr,MarketCostProjectPlansign.class).get(0);
            marketCostProjectPlansignService.batchPut(year,marketCostProjectSignList,marketCostProjectSign);
            result.put("msg","保存成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","保存失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/market/efficiency/happen/get
     * 实际发生
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/happen/get")
    public String happenGet(ModelMap modelMap, @ModelAttribute("entity")MarketCostProjectBudget entity){
        entity.setI1(CommonUtil.isInt(entity.getI1())?entity.getI1():1001);
        entity.setI2(CommonUtil.isInt(entity.getI2())?entity.getI2():2017);
        List<MarketCostProjectBudget> marketCostProjectBudgets = marketCostProjectBudgetService.getToHappen(entity);
        modelMap.addAttribute("marketCostProjectBudgets",marketCostProjectBudgets);
        modelMap.addAttribute("companyList",marketCostCompanyService.getAll());
        modelMap.addAttribute("yearList",marketYearService.getAll());
        return ".market.efficiency.happen_list";
    }

    /**
     * uri:/market/efficiency/happen/projecthappen/{p}/{y}
     * 实际发生
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/happen/projecthappen/{p}/{y}")
    public String projecthappen(ModelMap modelMap, @PathVariable("p") Long p,@PathVariable("y")  Integer y) throws Exception{
        List<MarketCostProjectBudgetItem> marketCostProjectBudgetItemList = marketCostProjectBudgetItemService.getAllForHierarchyOfHappen(p, y);
        modelMap.addAttribute("marketCostProjectBudgetItemList",marketCostProjectBudgetItemList);
        modelMap.addAttribute("project",marketCostProjectService.getOneByProject(p));
        modelMap.addAttribute("year",y);
        return "/market/efficiency/happen_list_project";
    }

    /**
     * uri:/market/efficiency/happen/happendetail/{projectid}/{threecode}/{belongmonth}/{threename}
     * 实际发生明细
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/happen/happendetail/{projectid}/{threecode}/{belongmonth}/{threename}")
    public String happendetail(ModelMap modelMap,@PathVariable("projectid") Long projectid, @PathVariable("threecode") String threecode,@PathVariable("belongmonth") String belongmonth,@PathVariable("threename") String threename) throws Exception{
        List<MarketCostContract> marketCostContractList = marketCostContractService.getHappenDetailOfItemAndMonth(projectid,belongmonth,threecode);
        modelMap.addAttribute("marketCostContractList",marketCostContractList);
        modelMap.addAttribute("belongmonth",belongmonth);
        modelMap.addAttribute("threename",threename);
        return "/market/efficiency/happen_list_project_detail";
    }

    /**
     * uri:/market/efficiency/efficiency/get
     * 费效分析
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/efficiency/get")
    public String efficiencyGet(ModelMap modelMap,  @ModelAttribute("entity")MarketCostProjectBudget entity){
        entity.setI1(CommonUtil.isInt(entity.getI1())?entity.getI1():1001);
        entity.setI2(CommonUtil.isInt(entity.getI2())?entity.getI2():2017);
        List<MarketCostProjectBudget> marketCostProjectBudgets = marketCostProjectBudgetService.getToEfficiency(entity);
        modelMap.addAttribute("marketCostProjectBudgets",marketCostProjectBudgets);
        modelMap.addAttribute("companyList",marketCostCompanyService.getAll());
        modelMap.addAttribute("yearList",marketYearService.getAll());
        return ".market.efficiency.efficiency_list";
    }


}
