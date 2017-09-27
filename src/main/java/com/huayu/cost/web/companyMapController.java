package com.huayu.cost.web;

import com.huayu.cost.service.CompanyMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 组织架构 映射
 * @author ZXL 2017-07-07 9:52
 **/
@Controller
@RequestMapping("/admin/companymap")
public class companyMapController {

    @Autowired
    private CompanyMapService companyMapService;

    /**
     * uri:/admin/companymap/addpage
     * 添加页面
     * @param modelMap
     * @param entity
     * @return
     */
    /*@RequestMapping("/addpage")
    public String signGet(ModelMap modelMap){
        modelMap.addAttribute("companyMapList",companyMapService.getAllOfParent());
        return ".admin.cost.company_map_addpage";
    }*/

    /**
     * uri:/admin/companymap/post
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

    /**
     * uri:/admin/companymap/getson
     * 获取子类
     * @param empListStr 数据
     * @return
     * String
     */
    /*@RequestMapping(value="/getson",method = RequestMethod.POST)
    public @ResponseBody
    String post(String id) throws Exception{
        return JSON.toJSONString(companyMapService.getAllByParentId(id));
    }*/

}
