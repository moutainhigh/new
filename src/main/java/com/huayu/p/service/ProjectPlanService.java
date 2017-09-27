package com.huayu.p.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.p.dao.ProjectPlanDao;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 项目计划
 * @author ZXL 2017-05-19 10:25
 **/
@Service
public class ProjectPlanService {

    @Autowired
    private ProjectPlanDao projectPlanDao;
    @Autowired
    private ProjectPlanCompileService projectPlanCompileService;
    @Autowired
    private ProjectPlanCompileLogService projectPlanCompileLogService;
    @Autowired
    private ProjectPlanCheckLogService projectPlanCheckLogService;

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int completePut(ProjectPlanCompile projectPlanCompile) throws Exception{
        ProjectPlanCompile projectPlanCompile1 = projectPlanCompileService.getOne(projectPlanCompile.getProjectId(), projectPlanCompile.getCompileId());
        /*if (projectPlanCompile.equals2(projectPlanCompile1)){
            throw new CustomException("修改失败，无修改项");
        }*/
        if (projectPlanCompile1.getIsComplete()==2){
            throw new CustomException("修改失败，不能修改");
        }
        User user = SpringSecurityUtil.getUser();
        projectPlanCompile.setIsComplete((short)3);
        projectPlanCompile.setCompleteDate(projectPlanCompile1.getIsComplete()==3?projectPlanCompile1.getCompleteDate():new Date());
        projectPlanCompile.setReCompleteDate(projectPlanCompile1.getIsComplete()==1?projectPlanCompile.getCompleteDate():projectPlanCompile1.getReCompleteDate());
        projectPlanCompileService.completePut(projectPlanCompile,user);
        if (projectPlanCompile1.getIsComplete()!=3){
            projectPlanCheckLogService.post2(projectPlanCompile1.getProjectId(),projectPlanCompile1.getCompileId(),projectPlanCompile1.getTemplateId(),projectPlanCompile.getCompleteRemark(),projectPlanCompile.getNoCompleteRemark());
        }
        projectPlanCompile.setVersions(projectPlanCompile1.getVersions()+1);
        return projectPlanCompileLogService.completePut(projectPlanCompile,user);
    }

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int completePut2(ProjectPlanCompile projectPlanCompile) throws Exception{
        ProjectPlanCompile projectPlanCompile1 = projectPlanCompileService.getOne(projectPlanCompile.getProjectId(), projectPlanCompile.getCompileId());
        if (projectPlanCompile.equals2(projectPlanCompile1)){
            throw new CustomException("修改失败，无修改项");
        }
        if (projectPlanCompile.getIsComplete()==2&&null==projectPlanCompile.getCompleteDate()){
            throw new CustomException("修改失败");
        }
        User user = SpringSecurityUtil.getUser();
        if (projectPlanCompile.getIsComplete()==3){
            projectPlanCompile.setCompleteDate(projectPlanCompile1.getCompleteDate());
        }
        projectPlanCompileService.completePut(projectPlanCompile,user);
        projectPlanCompile.setVersions(projectPlanCompile1.getVersions()+1);
        return projectPlanCompileLogService.completePut(projectPlanCompile,user);
    }

}
