package com.huayu.inventory.web;

import com.huayu.a.service.CommonDictService;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.inventory.domain.PrMaterial;
import com.huayu.inventory.domain.PrMaterialSpecification;
import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupUpdate;
import com.huayu.inventory.service.PrBaseInfoService;
import com.huayu.inventory.service.PrMaterialService;
import com.huayu.inventory.web.args.PrMaterialArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 材料管理
 * Created by Administrator on 2017/4/28.
 */
@Controller
@RequestMapping("/admin/inventory/material")
public class PrMaterialController {

    private static Logger logger = LoggerFactory.getLogger(PrMaterialController.class);

    @Resource
    private PrMaterialService prMaterialService;

    @Autowired
    private PrBaseInfoService prBaseInfoService;

    @Autowired
    private CommonDictService commonDictService;



    /**
     * 材料页面
     * @return
     */
    @RequestMapping("/index")
    public String materialList(){

        return ".inventory.material.materialList";
    }


    /**
     * 添加材料
     * @param materialArgs
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertMaterial(PrMaterialArgs materialArgs){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            prMaterialService.insertMaterial(materialArgs);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 修改材料
     * @param materialArgs
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateMaterial(PrMaterialArgs materialArgs){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            prMaterialService.updateMaterial(materialArgs);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
            logger.error("修改材料失败,{}",e);
        }
        return baseResult;
    }


    /**
     * 添加规格
     * @param materialArgs
     * @return
     */
    @RequestMapping(value = "/insertMaterialSpecification",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertMaterialSpecification(@Validated(GroupUpdate.class) PrMaterialArgs materialArgs, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prMaterialService.insertMaterialSpecification(materialArgs,true);
            baseResult.setSuccess();
        }catch (BusinessException e){
            baseResult.setRmsg(e.getMessage());
            logger.error("添加规格失败",e);
        }
        return baseResult;
    }


    /**
     * 单个删除
     * @param materialArgs
     * @return
     */
    @RequestMapping(value = "/deleteOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteMaterial(@Validated(GroupDelete.class) PrMaterialArgs materialArgs, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prMaterialService.deleteOneMaterial(materialArgs);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 材料列表
     * @param param
     * @param materialArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/materialSpecificationListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> materialSpecificationListData(@RequestParam Map<String,Object> param, PrMaterialArgs materialArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.categoryId,m.name,pms.specification,m.createtime");
        Page<PrMaterial> page = prMaterialService.materialSpecificationListData(materialArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    /**
     * 获取材料规格弹窗
     * @param model
     * @return
     */
    @RequestMapping(value = "/getMaterialSpecification",method = RequestMethod.GET)
    public String getMaterialSpecification(Model model){
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return "/inventory/material/materialSpecificationListPop";
    }

    /**
     * 获取材料弹窗
     * @param model
     * @return
     */
    @RequestMapping(value = "/getMaterial",method = RequestMethod.GET)
    public String getMaterial(Model model){
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return "/inventory/material/materialListPop";
    }

    /**
     * 只获取材料
     * @param param
     * @param materialArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/materialListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> materialListData(@RequestParam Map<String,Object> param, PrMaterialArgs materialArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.createtime");
        Page<PrMaterial> page = prMaterialService.materialListData(materialArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 新增材料
     * @param model
     * @return
     */
    @RequestMapping("/materialEdit")
    public String materialEdit(Model model){
        model.addAttribute("units",commonDictService.getDictList("unit"));
        model.addAttribute("projectName", ConstantsHolder.getContext().getCurrDataValue());
        return ".inventory.material.materialEdit";
    }

    /**
     * 材料编辑
     * @param model
     * @param id
     * @param specificationId
     * @return
     */
    @RequestMapping("/materialEdit/{id}/{specificationId}")
    public String materialEdit(Model model, @PathVariable("id") Integer id, @PathVariable("specificationId") Integer specificationId){
        PrMaterial entity = prMaterialService.getMaterialResultById(id,specificationId);
        model.addAttribute("entity",entity);
        model.addAttribute("units",commonDictService.getDictList("unit"));
        model.addAttribute("projectName", ConstantsHolder.getContext().getCurrDataValue());
        return ".inventory.material.materialEdit";
    }

    /**
     * 添加规格
     * @param model
     * @return
     */
    @RequestMapping("/materialSpecificationEdit")
    public String materialSpecificationEdit(Model model){
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("units",commonDictService.getDictList("unit"));
        model.addAttribute("addSpecification",true);
        model.addAttribute("projectName", ConstantsHolder.getContext().getCurrDataValue());
        return ".inventory.material.materialSpecificationAdd";
    }

    /**
     * 根据分类获取材料
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/getMaterials",method = RequestMethod.POST)
    @ResponseBody
    public List<PrMaterial> getMaterials(@RequestParam Integer categoryId){
        return prMaterialService.getMaterials(categoryId);
    }

    /**
     * 获取材料规格
     * @param matId
     * @return
     */
    @RequestMapping(value = "/getMaterialSpecification",method = RequestMethod.POST)
    @ResponseBody
    public List<PrMaterialSpecification> getMaterialSpecification(@RequestParam Integer matId){
        return prMaterialService.getMaterialSpecification(matId);
    }

}
