package com.huayu.p.web;

import com.alibaba.fastjson.JSON;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.p.domain.ProjectArchives;
import com.huayu.p.domain.ProjectPlanCheckLog;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompileLog;
import com.huayu.p.service.*;
import com.huayu.p.util.CommonUtil;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 项目计划Controller
 * @author ZXL 2017-05-22 16:07
 **/
@Controller
@RequestMapping("/project/plan")
public class ProjectPlanController {

    @Autowired
    private ProjectArchivesService projectArchivesService;
    @Autowired
    private ProjectPlanCompileLogService projectPlanCompileLogService;
    @Autowired
    private ProjectPlanCompileService projectPlanCompileService;
    @Autowired
    private ProjectPlanService projectPlanService;
    @Autowired
    private ProjectPlanAccessoryService projectPlanAccessoryService;
    @Autowired
    private ProjectAreaService projectAreaService;
    @Autowired
    private ProjectPlanCheckLogService projectPlanCheckLogService;

    /**
     * uri:/project/plan/get
     * 分页获取编制计划数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/get")
    public String get(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        entity.setIsParent((short)0);
        entity.setIsPlan((short)2);
        Page<ProjectArchives> page = projectArchivesService.get(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.plan.compile_list";
    }

    /**
     * uri:/project/plan/planget
     * 分页获取进度维护数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/planget")
    public String planget(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        entity.setIsParent((short)0);
        entity.setIsCompile((short)2);
        entity.setSufVersions(2);
        Page<ProjectArchives> page = projectArchivesService.get(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.plan.compile_planlist";
    }

    /**
     * uri:/project/plan/plancheckget
     * 分页获取进度查看数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/plancheckget")
    public String plancheckget(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        entity.setIsParent((short)0);
        entity.setIsCompile((short)2);
        entity.setSufVersions(2);
        Page<ProjectArchives> page = projectArchivesService.get(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.plan.compile_planchecklist";
    }

    /**
     * uri:/project/plan/edit/{id}
     * 获取新增或修改页面
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/edit/{id}")
    public String edit(ModelMap modelMap,@PathVariable("id") Long id){
        User user = SpringSecurityUtil.getUser();
        ProjectArchives projectArchives = projectArchivesService.getOne(id);
        if (null!=projectArchives&&user.getDataAuthoritiesRegexp().indexOf(String.valueOf(projectArchives.getAreaId()))!=-1) {
            modelMap.addAttribute("projectArchives", projectArchives);
            modelMap.addAttribute("projectPlanCompileLogList", projectPlanCompileLogService.getAll(projectArchives.getProjectId(), projectArchives.getSufVersions()));
        }
        return projectArchives.getIsCompile()==1?".p.plan.compile_edit":".p.plan.compile_edit_on";
    }

    /**
     * uri:/project/plan/bput
     * 添加新数据
     * @param empListStr 数据
     * @return
     * String
     */
    @RequestMapping(value = "/bput",method = RequestMethod.POST)
    public @ResponseBody String bput(Long projectId,String empListStr) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            List<ProjectPlanCompileLog> projectPlanCompileLogList = JSON.parseArray(empListStr, ProjectPlanCompileLog.class);
            projectPlanCompileLogService.batchPut(projectId,projectPlanCompileLogList);
            result.put("msg","编制计划成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","编制计划失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/plan/put
     * 添加新数据
     * @param empListStr 数据
     * @return
     * String
     */
    @RequestMapping(value = "/put",method = RequestMethod.POST)
    public @ResponseBody String put(String empListStr) throws Exception{
        ProjectPlanCompileLog projectPlanCompileLog = JSON.parseObject(empListStr,ProjectPlanCompileLog.class);
        Map<String,Object> result = CommonUtil.result();
        try {
            projectPlanCompileLogService.put(projectPlanCompileLog);
            result.put("msg","修改成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","修改失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/plan/on
     * 生效
     * @param empListStr 数据
     * @return
     * String
     */
    @RequestMapping(value = "/on",method = RequestMethod.POST)
    public @ResponseBody String on(Long projectId) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            projectArchivesService.putIsOn(projectId,(short)2);
            result.put("msg","生效成功");
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","生效失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/plan/planedit/{id}
     * 获取进度维护数据
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/planedit/{id}")
    public String planedit(ModelMap modelMap,@PathVariable("id") Long id){
        User user = SpringSecurityUtil.getUser();
        ProjectArchives projectArchives = projectArchivesService.getOne(id);
        if (null!=projectArchives&&user.getDataAuthoritiesRegexp().indexOf(String.valueOf(projectArchives.getAreaId()))!=-1){
            modelMap.addAttribute("projectArchives",projectArchives);
            modelMap.addAttribute("projectPlanCompileList",projectPlanCompileService.getAll(projectArchives.getProjectId()));
        }
        return ".p.plan.compile_planEdit";
    }

    /**
     * uri:/project/plan/plancheckedit/{id}
     * 获取进度维护数据
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/plancheckedit/{id}")
    public String plancheckedit(ModelMap modelMap,@PathVariable("id") Long id){
        User user = SpringSecurityUtil.getUser();
        ProjectArchives projectArchives = projectArchivesService.getOne(id);
        if (null!=projectArchives&&user.getDataAuthoritiesRegexp().indexOf(String.valueOf(projectArchives.getAreaId()))!=-1) {
            modelMap.addAttribute("projectArchives", projectArchives);
            modelMap.addAttribute("projectPlanCompileList", projectPlanCompileService.getAll(projectArchives.getProjectId()));
        }
        return ".p.plan.compile_plancheckEdit";
    }

    /**
     * uri:/project/plan/completeedit/{pid}/{cid}
     * 获取进度维护完成情况编辑页面
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/completeedit/{pid}/{cid}")
    public String completeedit(ModelMap modelMap,@PathVariable("pid") Long pid,@PathVariable("cid") Long cid){
        modelMap.addAttribute("entity",projectPlanCompileService.getOne(pid,cid));
        modelMap.addAttribute("projectPlanAccessoryList",projectPlanAccessoryService.getAll(pid,cid));
        return "/p/plan/compile_complete_edit";
    }

    /**
     * uri:/project/plan/completedtl/{pid}/{cid}
     * 获取进度维护完成情况编辑页面
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/completedtl/{pid}/{cid}")
    public String completedtl(ModelMap modelMap,@PathVariable("pid") Long pid,@PathVariable("cid") Long cid){
        modelMap.addAttribute("entity",projectPlanCompileService.getOne(pid,cid));
        modelMap.addAttribute("checkLogList",projectPlanCheckLogService.getAll(pid,cid));
        modelMap.addAttribute("projectPlanAccessoryList",projectPlanAccessoryService.getAll(pid,cid));
        return "/p/plan/compile_complete_detail";
    }

    /**
     * uri:/project/plan/completeput
     * 修改进度完成情况
     * @param projectArchives ProjectArchives
     * @return
     * string
     */
    @RequestMapping(value = "/completeput",method = RequestMethod.POST)
    public @ResponseBody String completeput(ProjectPlanCompile projectPlanCompile) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            projectPlanService.completePut(projectPlanCompile);
            result.put("msg","修改成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","修改失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * uri:/project/plan/getchecks
     * 分页获取待审核数据
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/getchecks")
    public String getchecks(ModelMap modelMap, @ModelAttribute("entity") ProjectPlanCompile entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        Page<ProjectPlanCompile> page = projectPlanCompileService.getToCheck(pageable,entity);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.plan.compile_planCheck";
    }

    /**
     * uri:/project/plan/checkedit/{pid}/{cid}
     * 获取进度维护审核情况编辑页面
     * @param modelMap ModelMap
     * @return
     * String
     */
    @RequestMapping("/checkedit/{pid}/{cid}")
    public String checkedit(ModelMap modelMap,@PathVariable("pid") Long pid,@PathVariable("cid") Long cid){
        modelMap.addAttribute("entity",projectPlanCompileService.getOne(pid,cid));
        modelMap.addAttribute("checkLogList",projectPlanCheckLogService.getAll(pid,cid));
        modelMap.addAttribute("projectPlanAccessoryList",projectPlanAccessoryService.getAll(pid,cid));
        return "/p/plan/compile_complete_check";
    }

    /**
     * uri:/project/plan/checkeput
     * 进度审核
     * @param projectArchives ProjectArchives
     * @return
     * string
     */
    @RequestMapping(value = "/checkeput",method = RequestMethod.POST)
    public @ResponseBody String checkeput(ProjectPlanCheckLog projectPlanCheckLog) throws Exception{
        Map<String,Object> result = CommonUtil.result();
        try {
            projectPlanCheckLogService.post(projectPlanCheckLog);
            result.put("msg","操作成功");
        } catch (CustomException e) {
            result.put("code","1");
            result.put("msg",e.getMessage());
        } catch (Exception e) {
            result.put("code","1");
            result.put("msg","操作失败");
        }
        return JSON.toJSONString(result);
    }


}
