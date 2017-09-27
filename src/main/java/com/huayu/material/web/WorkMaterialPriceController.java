package com.huayu.material.web;

import com.huayu.common.BaseResult;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.material.domain.MaterialPriceTableResult;
import com.huayu.material.domain.WorkMaterial;
import com.huayu.material.domain.WorkMaterialPrice;
import com.huayu.material.service.WorkMaterialPriceService;
import com.huayu.material.service.WorkMaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
@Controller
@RequestMapping("/admin/workMaterialPrice")
public class WorkMaterialPriceController {

    @Resource
    private WorkMaterialPriceService workMaterialPriceService;

    @Resource
    private WorkMaterialService workMaterialService;


    @RequestMapping("/index")
    public String index(){

        return ".admin.work_material.materialPriceList";
    }

    /**
     * 各城市价格
     * @return
     */
    @RequestMapping(value = "/cityPrice/index")
    public String materialListData(){

        return ".admin.work_material.materialCityPriceList";
    }


    @RequestMapping(value = "/cityPrice/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> materialPriceListData(@RequestParam Map<String,Object> param, WorkMaterialPrice priceResult, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.ASC,"m.mcode");
        Page<WorkMaterialPrice> page = workMaterialPriceService.materialPriceListData(priceResult,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }



    @RequestMapping("/materialChartTable/{id}")
    public String materialChartTable(Model model,@PathVariable Integer id){
        model.addAttribute("id",id);
        return ".admin.work_material.materialChartTable";
    }


    @RequestMapping(value = "/materialHistoryPrice/{id}")
    public String materialHistoryPrice(@PathVariable Integer id, Model model){
        WorkMaterial material = workMaterialService.getMaterialById(id);
        model.addAttribute("material",material);
        model.addAttribute("id",id);
        return ".admin.work_material.materialHistoryPrice";
    }


    @RequestMapping(value = "/historyListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> materialHistoryPriceListData(@RequestParam Map<String,Object> param, WorkMaterialPrice priceResult, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength());
        Page<WorkMaterialPrice> page = workMaterialPriceService.materialHistoryPriceListData(priceResult,pageable, ConstantsHolder.getContext().getCurrDataId());
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    @RequestMapping("/historyPriceChart")
    @ResponseBody
    public Map<String,Object> getMaterialHistoryTable(@RequestParam("materialId") Integer id, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime){
        List<MaterialPriceTableResult> list = workMaterialService.getMaterialHistoryTable(id,startTime,endTime, ConstantsHolder.getContext().getCurrDataId());
        Double max = Double.valueOf(0);
        if (list != null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getPrice() != null){
                    String price = list.get(i).getPrice();
                    if (max < Double.parseDouble(price)) max = Double.parseDouble(price);
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("max",max);
        map.put("min",0);
        map.put("year", DateTimeUtil.getYear(new Date()));
       return map;
   }

    @RequestMapping("/edit/{id}")
    public String materialEdit(Model model, @PathVariable("id") Integer id){
        WorkMaterial materialResult = workMaterialService.getMaterialPriceById(id, ConstantsHolder.getContext().getCurrDataId());
        model.addAttribute("materialResult",materialResult);
        return ".admin.work_material.materialPriceEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public BaseResult updateMaterial(WorkMaterial materialResult){
        BaseResult baseResult = BaseResult.initBaseResult();
        workMaterialPriceService.updateMaterial(materialResult, ConstantsHolder.getContext().getCurrDataId());
        baseResult.setSuccess();
        return baseResult;
    }
}