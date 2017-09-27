package com.huayu.p.web;

import com.huayu.p.domain.ProjectArchives;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.service.ProjectArchivesService;
import com.huayu.p.service.ProjectAreaService;
import com.huayu.p.service.ProjectDownloadExcelService;
import com.huayu.p.service.ProjectPlanCompileService;
import com.huayu.p.util.FinalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 进度报表
 * @author ZXL 2017-06-05 14:44
 **/
@Controller
@RequestMapping("/project/report")
public class ProjectReportController {

    @Autowired
    private ProjectArchivesService projectArchivesService;
    @Autowired
    private ProjectAreaService projectAreaService;
    @Autowired
    private ProjectPlanCompileService projectPlanCompileService;
    @Autowired
    private ProjectDownloadExcelService projectDownloadExcelService;

    /**
     * uri:/project/report/getreport
     * 分页获取数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/getreport")
    public String getToReport(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        Page<ProjectArchives> page = projectArchivesService.getToReport(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.report.project_list";
    }

    /**
     * uri:/project/report/gettoreportxml
     * 订单列表数据下载
     * @param entity ShopOrder
     * @param response HttpServletResponse
     */
    @RequestMapping("/gettoreportxml")
    public String gettoreportxml(HttpServletResponse response, ProjectArchives entity) throws Exception {
        String fileName="项目计划完成总表";
        String columnNames[]={"所属区域","项目名称","节点个数","完成个数","节点完成率","节点权重","完成节点权重","完成得分率"};//列名
        String keys[]={"areaName","projectName","nodeNum","successNodeNum","successNodeNumRate","allWeight","successWeight","successWeightRate"};//map中的key
        Pageable pageable = new PageRequest(0, FinalUtil.EXPORTEXCEL_PAGESIZE);
        List<ProjectArchives> objList=projectArchivesService.getToReport(entity,pageable).getContent();
        //ExcelUtil.downloadExcel(response,objList, fileName, columnNames, keys);
        projectDownloadExcelService.createReportWorkBook(response,fileName,objList,keys,columnNames);
        return null;
    }

    /**
     * uri:/project/report/getsuccessreport
     * 分页获取数据
     * @param modelMap
     * @param entity
     * @return
     */
    @RequestMapping("/getsuccessreport")
    public String getsuccessreport(ModelMap modelMap, @ModelAttribute("entity") ProjectPlanCompile entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        Page<ProjectPlanCompile> page = projectPlanCompileService.getToReport(entity,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.report.project_success_list";
    }

    /**
     * uri:/project/report/getsuccessreportxml
     * 订单列表数据下载
     * @param entity ShopOrder
     * @param response HttpServletResponse
     */
    @RequestMapping("/getsuccessreportxml")
    public String getsuccessreportxml(HttpServletResponse response, ProjectPlanCompile entity) throws Exception {
        String fileName="项目计划完成情况明细表";
        String columnNames[]={"所属区域","项目名称","节点名称","计划完成时间","实际完成时间","节点权重","节点得分","节点得分率","未完成情况说明"};//列名
        String keys[]={"areaName","projectName","taskName","endDate","completeDate","allWeight","weight","successWeightRate","noCompleteRemark"};//map中的key
        Pageable pageable = new PageRequest(0, FinalUtil.EXPORTEXCEL_PAGESIZE);
        List<ProjectPlanCompile> objList=projectPlanCompileService.getToReport(entity,pageable).getContent();
        //ExcelUtil.downloadExcel(response,objList, fileName, columnNames, keys);
        projectDownloadExcelService.createSuccessReportWorkBook(response,fileName,objList,keys,columnNames);
        return null;
    }

    /**
     * uri:/project/report/getcharts
     * 分页获取数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/getcharts")
    public String getcharts(ModelMap modelMap, @ModelAttribute("entity") ProjectArchives entity){
        List<ProjectArchives> projectArchivesList = projectArchivesService.getAllToReport(entity);
        modelMap.addAttribute("projectArchivesList",projectArchivesList);
        modelMap.addAttribute("projectAreaList",projectAreaService.get());
        return ".p.report.project_echarts";
    }

    /**
     * uri:/project/report/getchartsdtl
     * 分页获取数据
     * @param modelMap ModelMap
     * @param entity ProjectArchives
     * @return
     * String
     */
    @RequestMapping("/getchartsdtl")
    public String getchartsdtl(ModelMap modelMap,@ModelAttribute("n") String n){
        ProjectArchives projectArchives = projectArchivesService.getOneByProjectName(n);
        modelMap.addAttribute("projectPlanCompileList",null==projectArchives?null:projectPlanCompileService.getAll(projectArchives.getProjectId()));
        return ".p.report.project_echarts_dtl";
    }

}
