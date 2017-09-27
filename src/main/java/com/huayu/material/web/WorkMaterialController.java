package com.huayu.material.web;

import com.huayu.common.BaseResult;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.material.domain.WorkMaterial;
import com.huayu.material.service.WorkMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/28.
 */
@Controller
@RequestMapping("/admin/workMaterial")
public class WorkMaterialController {

    private static Logger logger = LoggerFactory.getLogger(WorkMaterialController.class);

    @Resource
    private WorkMaterialService workMaterialService;

    /**
     * 材料页面
     * @return
     */
    @RequestMapping("/index")
    public String materialList(){

        return ".admin.work_material.materialList";
    }


    @RequestMapping("/insert")
    @ResponseBody
    public BaseResult insertMaterial(WorkMaterial materialResult){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            workMaterialService.insertMaterial(materialResult);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateMaterial(WorkMaterial materialResult){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            workMaterialService.updateMaterial(materialResult);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
            logger.error("url:/work_material/material/update,{}",e);
        }
        return baseResult;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteMaterial(String ids){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            workMaterialService.deleteMaterial(ids);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/deleteOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteMaterial(Integer id){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            workMaterialService.deleteOneMaterial(id);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listData(@RequestParam Map<String,Object> param, WorkMaterial materialResult, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.mcode");
        Page<WorkMaterial> page = workMaterialService.materialListData(materialResult,pageable, ConstantsHolder.getContext().getCurrDataId());
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    @RequestMapping("/edit")
    public String materialEdit(){
        return ".admin.work_material.materialEdit";
    }

    @RequestMapping("/edit/{id}")
    public String materialEdit(Model model, @PathVariable("id") Integer id){
        WorkMaterial materialResult = workMaterialService.getMaterialResultById(id);
        model.addAttribute("materialResult",materialResult);
        return ".admin.work_material.materialEdit";
    }


}
