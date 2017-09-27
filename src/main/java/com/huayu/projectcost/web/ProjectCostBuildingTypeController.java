package com.huayu.projectcost.web;

import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.projectcost.domain.ProjectCostBuildingType;
import com.huayu.projectcost.domain.validate.TypeGroupDelete;
import com.huayu.projectcost.domain.validate.TypeGroupInsert;
import com.huayu.projectcost.domain.validate.TypeGroupUpdate;
import com.huayu.projectcost.service.ProjectCostBuildingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
@Controller
@RequestMapping("/admin/projectcost/setting")
public class ProjectCostBuildingTypeController {

    @Autowired
    private ProjectCostBuildingTypeService projectCostBuildingTypeService;

    @RequestMapping("/toSettingPage")
    public String toSettingPage(Model model) {
       model.addAttribute("entity", projectCostBuildingTypeService.getAllType());
        return ".projectcost.setting.buildingTypeList";
    }

    @RequestMapping("/addType")
    @ResponseBody
    public BaseResult addType(@Validated(TypeGroupInsert.class) ProjectCostBuildingType projectCostBuildingType, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            List<ProjectCostBuildingType> typeList = projectCostBuildingTypeService.getTypeByName(projectCostBuildingType);
            if (!CollectionUtils.isEmpty(typeList)) {
                baseResult.setRmsg("业态名重复,请重新操作!");
            } else {
                projectCostBuildingTypeService.insertProjectType(projectCostBuildingType);
                baseResult.setSuccess();
            }
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/updateType")
    @ResponseBody
    public BaseResult updateType(@Validated(TypeGroupUpdate.class) ProjectCostBuildingType projectCostBuildingType, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            projectCostBuildingTypeService.updateType(projectCostBuildingType);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/deleteType")
    @ResponseBody
    public BaseResult deleteType(@Validated(TypeGroupDelete.class) ProjectCostBuildingType projectCostBuildingType, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            projectCostBuildingTypeService.deleteType(projectCostBuildingType);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }



}
