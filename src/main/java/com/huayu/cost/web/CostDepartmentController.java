package com.huayu.cost.web;

import com.huayu.cost.service.CompanyMapService;
import com.huayu.cost.service.CostDepartmentService;
import com.huayu.market.service.MarketYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 部门费用预算主表
 * @author ZXL 2017-07-07 11:25
 **/
@Controller
@RequestMapping("/admin/costdepartment")
public class CostDepartmentController {

    @Autowired
    private CostDepartmentService costDepartmentService;
    @Autowired
    private CompanyMapService companyMapService;
    @Autowired
    private MarketYearService marketYearService;

    /**
     * uri:/admin/costdepartment/addpage
     * 添加页面
     * @param modelMap
     * @param entity
     * @return
     */
    /*@RequestMapping("/addpage")
    public String signGet(ModelMap modelMap){
        modelMap.addAttribute("companyMapList",companyMapService.getAllOfParent());
        modelMap.addAttribute("yearList",marketYearService.getAll());
        return ".admin.cost.costdepartment_addpage";
    }*/

    /**
     * uri:/admin/costdepartment/post
     * 添加新数据
     * @param empListStr 数据
     * @return
     * String
     */
    /*@RequestMapping(value="/post",method = RequestMethod.POST)
    public @ResponseBody
    String post(CompanyMap companyMap) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            companyMapService.post(companyMap);
            result.put("msg","保存成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","保存失败");
        }
        return JSON.toJSONString(result);
    }*/

}
