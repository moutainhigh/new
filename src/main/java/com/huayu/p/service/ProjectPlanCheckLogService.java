package com.huayu.p.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.p.dao.ProjectPlanCheckLogDao;
import com.huayu.p.domain.ProjectPlanCheckLog;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanTemplate;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 进度审核记录
 * @author ZXL 2017-06-01 13:22
 **/
@Service
public class ProjectPlanCheckLogService {

    @Autowired
    private ProjectPlanCheckLogDao projectPlanCheckLogDao;
    @Autowired
    private ProjectPlanCompileService projectPlanCompileService;
    @Autowired
    private ProjectPlanCompileLogService projectPlanCompileLogService;
    @Autowired
    private ProjectPlanTemplateService projectPlanTemplateService;
    @Autowired
    private ProjectArchivesService projectArchivesService;

    /**
     * 条件获取全部数据
     * @param projectId
     * @param compileId
     * @param templateId
     * @return
     */
    public List<ProjectPlanCheckLog> getAll(Long projectId, Long compileId){
        ProjectPlanCheckLog projectPlanCheckLog = new ProjectPlanCheckLog();
        projectPlanCheckLog.setProjectId(projectId);
        projectPlanCheckLog.setCompileId(compileId);
        return projectPlanCheckLogDao.getAll(projectPlanCheckLog);
    }

    /**
     * 新增审核数据
     * @param projectPlanCheckLog
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int post(ProjectPlanCheckLog projectPlanCheckLog) throws Exception{
        ProjectPlanCompile projectPlanCompile = projectPlanCompileService.getOne(projectPlanCheckLog.getProjectId(),projectPlanCheckLog.getCompileId());
        if (null==projectPlanCompile){
            throw new CustomException("操作失败，数据不存在");
        }
        if (projectPlanCompile.getIsComplete()!=3){
            throw new CustomException("操作失败，当前数据不能审核");
        }
        if (projectPlanCheckLog.getStatus()==2&&null==projectPlanCheckLog.getCompleteDate()){
            throw new CustomException("操作失败，审核通过时完成时间不能为空");
        }
        User user = SpringSecurityUtil.getUser();
        projectPlanCheckLog.setCheckUserId(user.getUserId());
        projectPlanCheckLog.setCheckDate(new Date());
        projectPlanCheckLog.setType((short)2);
        projectPlanCheckLog.setCompleteRemark("");
        projectPlanCheckLog.setNoCompleteRemark("");
        int retCount = projectPlanCheckLogDao.post(projectPlanCheckLog);
        if (retCount>0){
            Integer isComplete = projectPlanCheckLog.getStatus()==1?4:2;
            Date completeDate = projectPlanCheckLog.getStatus()==1?projectPlanCompile.getCompleteDate():projectPlanCheckLog.getCompleteDate();
            projectPlanCompileService.checkPut(projectPlanCheckLog.getProjectId(),projectPlanCompile.getCompileId(),isComplete.shortValue(),completeDate);
            projectPlanCompileLogService.checkPut(projectPlanCheckLog.getProjectId(),projectPlanCompile.getCompileId(),isComplete.shortValue(),completeDate,projectPlanCompile.getVersions()+1);
            if (projectPlanCheckLog.getStatus()==2){
                ProjectPlanTemplate projectPlanTemplate = projectPlanTemplateService.getOne(projectPlanCompile.getTemplateId());
                projectArchivesService.putNodeNumAndWeight(projectPlanCompile.getProjectId(),1,projectPlanTemplate.getWeight());
            }
        }
        return retCount;
    }

    /**
     * 新增待审核数据
     * @param projectPlanCheckLog
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int post2(Long projectId,Long compileId,Integer templateId,String completeRemark,String noCompleteRemark) throws Exception{
        User user = SpringSecurityUtil.getUser();
        ProjectPlanCheckLog projectPlanCheckLog = new ProjectPlanCheckLog();
        projectPlanCheckLog.setType((short)1);
        projectPlanCheckLog.setStatus((short)1);
        projectPlanCheckLog.setCompleteRemark(completeRemark);
        projectPlanCheckLog.setNoCompleteRemark(noCompleteRemark);
        projectPlanCheckLog.setRemark("");
        projectPlanCheckLog.setCheckUserId(user.getUserId());
        projectPlanCheckLog.setCheckDate(new Date());
        projectPlanCheckLog.setProjectId(projectId);
        projectPlanCheckLog.setCompileId(compileId);
        projectPlanCheckLog.setTemplateId(templateId);
        return projectPlanCheckLogDao.post(projectPlanCheckLog);
    }

}
