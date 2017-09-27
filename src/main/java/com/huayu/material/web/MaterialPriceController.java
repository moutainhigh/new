package com.huayu.material.web;

import com.huayu.common.BaseResult;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.material.domain.*;
import com.huayu.material.service.MaterialPriceService;
import com.huayu.material.service.MaterialService;
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
 * 材料价格
 * Created by Administrator on 2017/5/3.
 */
@Controller
@RequestMapping("/admin/materialPrice")
public class MaterialPriceController {

    @Resource
    private MaterialPriceService materialPriceService;
    @Resource
    private MaterialService materialService;


    @RequestMapping("/index")
    public String index(){

        return ".admin.material.materialPriceList";
    }

    @RequestMapping("/materialChartTable/{id}")
    public String materialChartTable(Model model,@PathVariable Integer id){
        model.addAttribute("id",id);
        return ".admin.material.materialChartTable";
    }


    @RequestMapping(value = "/cityPrice/index")
    public String materialListData(){

        return ".admin.material.materialCityPriceList";
    }

    @RequestMapping(value = "/cityPrice/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> cityPriceListData(@RequestParam Map<String,Object> param, MaterialPrice priceResult, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.ASC,"m.mcode");
        Page<MaterialPrice> page = materialPriceService.materialPriceListData(priceResult,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    @RequestMapping(value = "/historyPrice/{id}")
    public String materialHistoryPrice(@PathVariable Integer id, Model model){
        Material material = materialService.getMaterialById(id);
        model.addAttribute("material",material);
        model.addAttribute("id",id);
        return ".admin.material.materialHistoryPrice";
    }


    @RequestMapping(value = "/historyListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> materialHistoryPriceListData(@RequestParam Map<String,Object> param, MaterialPrice priceResult, PageArgs args){

        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength());
        Page<MaterialPrice> page = materialPriceService.materialHistoryPriceListData(priceResult,pageable, ConstantsHolder.getContext().getCurrDataId());
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    @RequestMapping("/historyPriceChart")
    @ResponseBody
    public Map<String,Object> getMaterialHistoryTable(@RequestParam("materialId") Integer id, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime){
        List<MaterialPriceTableResult> list = materialService.getMaterialHistoryTable(id,startTime,endTime, ConstantsHolder.getContext().getCurrDataId());
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

    @RequestMapping("/materialEdit/{id}")
    public String materialEdit(Model model, @PathVariable("id") Integer id){
        Material materialResult = materialService.getMaterialPriceById(id, ConstantsHolder.getContext().getCurrDataId());
        model.addAttribute("materialResult",materialResult);
        return ".admin.material.materialPriceEdit";
    }

    /**
     * 调价
     * @param materialResult
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult updateMaterial(Material materialResult){
        BaseResult baseResult = BaseResult.initBaseResult();
        materialPriceService.updateMaterial(materialResult, ConstantsHolder.getContext().getCurrDataId());
        baseResult.setSuccess();
        return baseResult;
    }
}