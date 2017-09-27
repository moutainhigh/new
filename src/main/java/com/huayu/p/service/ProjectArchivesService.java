package com.huayu.p.service;


import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.p.dao.ProjectArchivesDao;
import com.huayu.p.dao.ProjectPlanCompileDao;
import com.huayu.p.domain.*;
import com.huayu.p.util.CommonUtil;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 项目档案
 *
 * @author ZXL 2017-05-19 10:24
 **/
@Service
public class ProjectArchivesService {

    @Autowired
    private ProjectArchivesDao projectArchivesDao;
    @Autowired
    private ProjectPlanCompileService projectPlanCompileService;
    @Autowired
    private ProjectPlanTemplateService projectPlanTemplateService;
    @Autowired
    private ProjectPlanCompileDao projectPlanCompileDao;
    @Autowired
    private ProjectPlanCompileLogService projectPlanCompileLogService;

    /**
     * 获取数据
     * @param projectId ID
     * @return
     * ProjectArchives
     */
    public ProjectArchives getOne(Long projectId){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        return projectArchivesDao.getOne(projectArchives);
    }

    /**
     * 获取数据
     * @param projectId ID
     * @return
     * ProjectArchives
     */
    public ProjectArchives getOneByProjectName(String projectName){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectName(projectName);
        return projectArchivesDao.getOneByProjectName(projectArchives);
    }

    /**
     * 分页获取数据
     * @param projectArchives ProjectArchives
     * @param pageable Pageable
     * @return
     * page
     */
    public Page<ProjectArchives> get(ProjectArchives projectArchives, Pageable pageable){
        return projectArchivesDao.get(projectArchives,pageable);
    }

    /**
     * 获取最大的编码
     * @param projectArchives ProjectArchives
     * @return
     * long
     */
    private Long getMaxCode(ProjectArchives projectArchives){
        ProjectArchives projectArchives2 = projectArchivesDao.getMaxCode(projectArchives);
        return StringUtils.isBlank(projectArchives2.getProjectCode())?Long.valueOf(projectArchives2.getParentCode()+"01"):(Long.valueOf(projectArchives2.getProjectCode())+1);
    }

    /**
     * 以父ID获取数据
     * @param parentId 父ID
     * @return
     * list
     */
    public List<ProjectArchives> getAllByParentId(Long parentId){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setParentId(parentId);
        return projectArchivesDao.getAllByParentId(projectArchives);
    }

    /**
     * 添加数据
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    @Transactional(rollbackFor = Exception.class)
    public int post(ProjectArchives projectArchives) throws Exception{
        User user = SpringSecurityUtil.getUser();
        int returnCount;
        synchronized (this){
            ProjectPlanTemplate projectPlanTemplate = projectPlanTemplateService.getNodeNumAndWeight();
            projectArchives.setProjectId(projectArchivesDao.getKey("project_archives",projectArchives));
            projectArchives.setStatus((short)2);
            projectArchives.setProjectCode((CommonUtil.isLong(projectArchives.getParentId())?this.getMaxCode(projectArchives):projectArchivesDao.getKey("project_archives_code",projectArchives))+"");
            projectArchives.setSort(Long.valueOf(CommonUtil.isLong(projectArchives.getParentId())?projectArchives.getProjectCode():(projectArchives.getProjectCode()+"00")));
            projectArchives.setIsParent(CommonUtil.isLong(projectArchives.getParentId())?(short)1:(short)2);
            projectArchives.setParentId(CommonUtil.isLong(projectArchives.getParentId())?projectArchives.getParentId():0);
            projectArchives.setIsCompile((short)1);
            projectArchives.setCreateDate(new Date());
            projectArchives.setCreateUserId(user.getUserId());
            projectArchives.setUpdateDate(DateTimeUtil.defaultVal());
            projectArchives.setUpdateUserId(0L);
            projectArchives.setDeleteDate(DateTimeUtil.defaultVal());
            projectArchives.setDeleteUserId(0L);
            projectArchives.setVersions(1);
            projectArchives.setSufVersions(1);
            projectArchives.setOnDate(DateTimeUtil.defaultVal());
            projectArchives.setIsOn((short)1);
            projectArchives.setOnUserId(0L);
            projectArchives.setPlanUpdateDate(DateTimeUtil.defaultVal());
            projectArchives.setPlanUpdateUserId(0L);
            projectArchives.setAssociatedNum(0);
            projectArchives.setNodeNum(projectPlanTemplate.getNodeNum());
            projectArchives.setSuccessNodeNum(0);
            projectArchives.setAllWeight(projectPlanTemplate.getWeight());
            projectArchives.setSuccessWeight(0);
            projectArchives.setIsComplete((short)1);
            returnCount = projectArchivesDao.post(projectArchives);
            if (returnCount>0){
                List<BaseProjectPlanCompile> projectPlanCompileList = projectPlanCompileService.batchPost(projectPlanTemplateService.get(),projectArchives.getProjectId(),user);
                projectPlanCompileLogService.batchPost(projectPlanCompileList);
                if (projectArchives.getParentId()>0){
                    this.addAssociatedNum(1,projectArchives.getParentId());
                }
            }
            return returnCount;
        }
    }

    /**
     * 增减关联次数
     * @param associatedNum
     * @param projectId
     * @return
     */
    @Transactional
    private int addAssociatedNum(Integer associatedNum,Long projectId){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        projectArchives.setAssociatedNum(associatedNum);
        return projectArchivesDao.addAssociatedNum(projectArchives);
    }

    /**
     * 修改数据
     * @param projectArchives ProjectArchives
     * @return
     * int
     * @throws Exception
     * E
     */
    @Transactional(rollbackFor = Exception.class)
    public int put(ProjectArchives projectArchives) throws Exception{
        ProjectArchives projectArchives1 = this.getOne(projectArchives.getProjectId());
        if (projectArchives.equals(projectArchives1)){
            throw new CustomException("修改项目档案失败，无修改项");
        }
        if (!projectArchives.getParentId().equals(projectArchives1.getParentId())){
            if (projectArchives1.getParentId()>0){
                this.addAssociatedNum(-1,projectArchives1.getParentId());
            }
            this.addAssociatedNum(1,projectArchives.getParentId());
        }
        if (projectArchives1.getIsParent()==2){
            projectArchives.setParentId(0L);
        }
        User user = SpringSecurityUtil.getUser();
        projectArchives.setUpdateDate(new Date());
        projectArchives.setUpdateUserId(user.getUserId());
        return projectArchivesDao.put(projectArchives);
    }

    /**
     * 删除数据
     * @param projectId ID
     * @return
     * int
     * @throws Exception
     * E
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long projectId) throws Exception{
        ProjectArchives projectArchives1 = this.getOne(projectId);
        if (projectArchives1.getIsOn()==2){
            throw new CustomException("该项目档案编制计划已生效，不能删除");
        }
        if (this.getAllByParentId(projectId).size()>0){
            throw new CustomException("该项目档案已被引用，不能删除");
        }
        if (projectArchives1.getParentId()>0){
            this.addAssociatedNum(-1,projectArchives1.getParentId());
        }
        User user = SpringSecurityUtil.getUser();
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        projectArchives.setDeleteDate(new Date());
        projectArchives.setDeleteUserId(user.getUserId());
        projectArchives.setStatus((short)1);
        return projectArchivesDao.delete(projectArchives);
    }

    /**
     * 修改是否编制项目 1否 2是
     * @param projectId 计划ID
     * @return
     * int
     */
    @Transactional
    public int putIsCompile(Long projectId){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        projectArchives.setIsCompile((short)2);
        return projectArchivesDao.putIsCompile(projectArchives);
    }

    /**
     * 修改是否编制项目 1否 2是
     * @param projectId 计划ID
     * @return
     * int
     */
    @Transactional
    public int putIsComplete(Long projectId){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        return projectArchivesDao.putIsComplete(projectArchives);
    }

    /**
     * 生效
     * @param projectId
     * @param isOn
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int putIsOn(Long projectId,Short isOn) throws Exception{
        User user = SpringSecurityUtil.getUser();
        ProjectArchives projectArchives = this.getOne(projectId);
        if (projectArchives.getIsOn().equals(isOn)){
            throw new CustomException("生效失败");
        }
        projectArchives.setIsOn(isOn);
        projectArchives.setOnDate(new Date());
        projectArchives.setOnUserId(user.getUserId());
        List<ProjectPlanCompileLog> projectPlanCompileLogList = projectPlanCompileLogService.getAll(projectId, projectArchives.getSufVersions());
        projectPlanCompileDao.batchPut(projectPlanCompileLogList);
        for (ProjectPlanCompileLog p:projectPlanCompileLogList){
            p.setVersions(projectArchives.getSufVersions()+1);
        }
        projectPlanCompileLogService.batchPost2(projectPlanCompileLogList);
        projectArchives.setVersions(projectArchives.getSufVersions());
        projectArchives.setSufVersions(projectArchives.getSufVersions()+1);
        return projectArchivesDao.putIsOn(projectArchives);
    }

    /**
     * 修改成未生效
     * @param projectId
     * @param isOn
     * @return
     * @throws Exception
     */
    @Transactional
    public int putNoOn(Long projectId,Short isOn) throws Exception{
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        projectArchives.setIsOn(isOn);
        return projectArchivesDao.putNoOn(projectArchives);
    }

    /**
     * 修改完成节点和完成权重
     * @param projectId
     * @param successNodeNum
     * @param successWeight
     * @return
     */
    @Transactional
    public int putNodeNumAndWeight(Long projectId,Integer successNodeNum,Integer successWeight){
        ProjectArchives projectArchives = new ProjectArchives();
        projectArchives.setProjectId(projectId);
        projectArchives.setSuccessNodeNum(successNodeNum);
        projectArchives.setSuccessWeight(successWeight);
        return projectArchivesDao.putNodeNumAndWeight(projectArchives);
    }

    /**
     * 获取项目进度报表
     * @param projectArchives
     * @param pageable
     * @return
     */
    public Page<ProjectArchives> getToReport(ProjectArchives projectArchives, Pageable pageable){
        return projectArchivesDao.getToReport(projectArchives,pageable);
    }

    /**
     * 获取项目进度报表
     * @param projectArchives
     * @param pageable
     * @return
     */
    public List<ProjectArchives> getAllToReport(ProjectArchives projectArchives){
        return projectArchivesDao.getAllToReport(projectArchives);
    }

}
