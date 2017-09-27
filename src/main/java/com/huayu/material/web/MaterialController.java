package com.huayu.material.web;

import com.huayu.common.BaseResult;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.material.domain.Material;
import com.huayu.material.service.MaterialService;
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
 * 材料管理
 * Created by Administrator on 2017/4/28.
 */
@Controller
@RequestMapping("/admin/material")
public class MaterialController {

    private static Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Resource
    private MaterialService materialService;



    /**
     * 材料页面
     * @return
     */
    @RequestMapping("/index")
    public String materialList(){

        return ".admin.material.materialList";
    }


    @RequestMapping("/insert")
    @ResponseBody
    public BaseResult insertMaterial(Material materialResult){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            materialService.insertMaterial(materialResult);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public BaseResult updateMaterial(Material materialResult){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            materialService.updateMaterial(materialResult);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
            logger.error("修改材料失败,{}",e);
        }
        return baseResult;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult deleteMaterial(String ids){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            materialService.deleteMaterial(ids);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public BaseResult deleteMaterial(Integer id){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            materialService.deleteOneMaterial(id);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listData(@RequestParam Map<String,Object> param, Material materialResult, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.mcode");
        Page<Material> page = materialService.materialListData(materialResult,pageable,ConstantsHolder.getContext().getCurrDataId());
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/materialEdit")
    public String materialEdit(Model model){
        return ".admin.material.materialEdit";
    }

    @RequestMapping("/materialEdit/{id}")
    public String materialEdit(Model model, @PathVariable("id") Integer id){
        Material materialResult = materialService.getMaterialResultById(id);
        model.addAttribute("materialResult",materialResult);
        return ".admin.material.materialEdit";
    }



}
