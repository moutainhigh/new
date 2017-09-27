package com.huayu.p.web;

import com.alibaba.fastjson.JSON;
import com.huayu.p.domain.ProjectArchives;
import com.huayu.p.service.ProjectArchivesService;
import com.huayu.p.service.ProjectAreaService;
import com.huayu.p.util.CommonUtil;
import com.huayu.p.web.tools.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 项目档案
 * @author ZXL 2017-05-19 10:28
 **/
@Controller
@RequestMapping("/project/archives")
public class ProjectArchivesController {

    @Autowired
    private ProjectArchivesService projectArchivesService;
    @Autowired
    private ProjectAreaService projectAreaService;

    /**
     * uri:/project/archives/get
     * 分页获取数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/get")
    public String get(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        entity.setIsParent((short)0);
        Page<ProjectArchives> page = projectArchivesService.get(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.archives.list";
    }

    /**
     * uri:/project/archives/getparent
     * 分页获取父类数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/getparent")
    public String getparent(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        entity.setIsParent((short)2);
        Page<ProjectArchives> page = projectArchivesService.get(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return "/p/archives/parent_list";
    }

    /**
     * uri:/project/archives/edit
     * 获取新增或修改页面
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap,Long id){
        if (null!=id) {
            ProjectArchives projectArchives = projectArchivesService.getOne(id);
            modelMap.addAttribute("entity",projectArchives);
        }
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.archives.edit";
    }

    /**
     * uri:/project/archives/post
     * 添加新数据
     * @param projectArchives ProjectArchives
     * @return
     * String
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public @ResponseBody String post(ProjectArchives projectArchives) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            if (projectArchivesService.post(projectArchives)>0){
                result.put("msg","添加项目档案成功");
            }else {
                result.put("code","1");
                result.put("msg","添加项目档案失败");
            }
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","添加项目档案失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/archives/put
     * 修改
     * @param projectArchives ProjectArchives
     * @return
     * string
     */
    @RequestMapping(value = "/put",method = RequestMethod.POST)
    public @ResponseBody String put(ProjectArchives projectArchives) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            if (projectArchivesService.put(projectArchives)>0){
                result.put("msg","修改项目档案成功");
            }else {
                result.put("code","1");
                result.put("msg","修改项目档案失败");
            }
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","修改项目档案失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/archives/del/{id}
     * 修改状态
     * @param id 档案ID
     * @return
     * String
     */
    @RequestMapping("/del/{id}")
    public @ResponseBody
    String delete(@PathVariable("id") Long id){
        Map<String,Object> result = CommonUtil.result();
        try {
            if (projectArchivesService.delete(id)>0){
                result.put("msg","删除项目档案成功");
            }else {
                result.put("code","1");
                result.put("msg","删除项目档案失败");
            }
        } catch (CustomException c) {
            result.put("code","1");
            result.put("msg",c.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","删除项目档案失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/archives/complete/{id}
     * 修改完成状态
     * @param id 档案ID
     * @return
     * String
     */
    @RequestMapping("/complete/{id}")
    public @ResponseBody
    String complete(@PathVariable("id") Long id){
        Map<String,Object> result = CommonUtil.result();
        try {
            if (projectArchivesService.putIsComplete(id)>0){
                result.put("msg","操作成功");
            }else {
                result.put("code","1");
                result.put("msg","操作失败");
            }
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","操作失败");
        }
        return JSON.toJSONString(result);
    }

}
