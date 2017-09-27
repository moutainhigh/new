package com.huayu.inventory.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.inventory.domain.PrProject;
import com.huayu.inventory.domain.PrRepository;
import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.huayu.inventory.domain.validate.GroupUpdate;
import com.huayu.inventory.service.PrBaseInfoService;
import com.huayu.inventory.web.args.PrRepositoryArgs;
import com.huayu.user.domain.User;
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

import java.util.List;
import java.util.Map;

/**
 * 项目仓库管理
 * Created by DengPeng on 2017/6/12.
 */
@Controller
@RequestMapping("/admin/inventory")
public class PrBaseInfoController {

    @Autowired
    private PrBaseInfoService prBaseInfoService;


    /**
     * 设置城市
     * @param currDataId
     * @return
     */
    @RequestMapping(value = "/setCurrentProject",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult setCurrentProject(Integer currDataId, String currDataValue){
        BaseResult baseResult = BaseResult.initBaseResult();
        User user = SpringSecurityUtil.getUser();
        List<PrProject> allProjects = prBaseInfoService.getAllProjects(user.getDataAuthoritiesRegexp());
        for (int i = 0; i < allProjects.size(); i++) {
            PrProject prProject = allProjects.get(i);
            if (prProject.getId() == currDataId){
                user.setDataAuthorityRegexp("'"+prProject.getPcode()+"'");
                user.setCurrDataId(currDataId);
                user.setCurrDataValue(currDataValue);
                return baseResult.setSuccess();
            }
        }
        return baseResult;
    }

    @RequestMapping(value = "/storage/getAllProjects",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getAllProjects() {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            User user = SpringSecurityUtil.getUser();
            baseResult.setRdata(prBaseInfoService.getAllProjects(user.getDataAuthoritiesRegexp()));
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }


    @RequestMapping("/storage/index")
    public String projectIndex(Model model){
        return ".inventory.baseInfo.storageList";
    }

    @RequestMapping("/storage/edit")
    public String projectEdit(Model model){
        model.addAttribute("projects",prBaseInfoService.getAllPrProject());
        model.addAttribute("currProjectId", ConstantsHolder.getContext().getCurrDataId());
        return ".inventory.baseInfo.storageEdit";
    }

    /**
     * 保存仓库
     * @param repository
     * @param result
     * @return
     */
    @RequestMapping(value = "/storage/insert",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insert(@Validated(GroupInsert.class) PrRepositoryArgs repository, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prBaseInfoService.insertStorage(repository);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 仓库列表
     * @param param
     * @param repositoryArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/storage/repositoryListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> repositoryListData(@RequestParam Map<String,Object> param, PrRepositoryArgs repositoryArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"r.createtime");
        Page<PrRepository> page = prBaseInfoService.repositoryListData(repositoryArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 编辑仓库
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/storage/edit/{id}")
    public String projectEdit(Model model,@PathVariable Integer id){
        model.addAttribute("projects",prBaseInfoService.getAllPrProject());
        model.addAttribute("entity",prBaseInfoService.getRepositoryOne(id));
        return ".inventory.baseInfo.storageEdit";
    }

    /**
     * 修改仓库
     * @param repository
     * @param result
     * @return
     */
    @RequestMapping(value = "/storage/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@Validated(GroupUpdate.class) PrRepositoryArgs repository, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prBaseInfoService.updateStorage(repository);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 删除仓库
     * @param repository
     * @param result
     * @return
     */
    @RequestMapping(value = "/storage/delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteStorage(@Validated(GroupDelete.class) PrRepositoryArgs repository, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prBaseInfoService.deleteStorage(repository);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

}
