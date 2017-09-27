package com.huayu.p.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.p.dao.ProjectPlanCompileDao;
import com.huayu.p.domain.BaseProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanTemplate;
import com.huayu.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目计划编制
 *
 * @author ZXL 2017-05-19 10:26
 **/
@Service
public class ProjectPlanCompileService {

    private Logger log = LoggerFactory.getLogger(ProjectPlanCompileService.class);

    @Autowired
    private ProjectPlanCompileDao projectPlanCompileDao;
    @Autowired
    private CommSequenceDao commSequenceDao;
    @Autowired
    private SendsmsService sendsmsService;

    /**
     * 获取唯一数据
     * @param projectId
     * @param compileId
     * @return
     */
    public ProjectPlanCompile getOne(Long projectId,Long compileId){
        ProjectPlanCompile projectPlanCompile = new ProjectPlanCompile();
        projectPlanCompile.setProjectId(projectId);
        projectPlanCompile.setCompileId(compileId);
        return projectPlanCompileDao.getOne(projectPlanCompile);
    }

    /**
     * 获取全部数据
     * @param projectId 项目档案ID
     * @return
     * list
     */
    public List<ProjectPlanCompile> getAll(Long projectId){
        ProjectPlanCompile projectPlanCompile = new ProjectPlanCompile();
        projectPlanCompile.setProjectId(projectId);
        return projectPlanCompileDao.getAll(projectPlanCompile);
    }

    /**
     * 分页获取待审核数据
     * @param pageable
     * @param projectPlanCompile
     * @return
     */
    public Page<ProjectPlanCompile> getToCheck(Pageable pageable,ProjectPlanCompile projectPlanCompile){
        return projectPlanCompileDao.getToCheck(pageable,projectPlanCompile);
    }

    /**
     * 批量添加数据
     * @param projectPlanTemplateList List
     * @param projectId 项目档案ID
     * @param user 创建用户
     * @return
     * int
     * @throws Exception
     * e
     */
    @Transactional
    public List<BaseProjectPlanCompile> batchPost(List<ProjectPlanTemplate> projectPlanTemplateList, Long projectId, User user) throws Exception{
        Long[] ids = commSequenceDao.getKey(new CommSequence("project_plan_compile", 48));
        int c = 0;
        List<BaseProjectPlanCompile> projectPlanCompileList = new ArrayList<>();
        for (ProjectPlanTemplate p:projectPlanTemplateList) {
            ProjectPlanCompile projectPlanCompile = new ProjectPlanCompile();
            projectPlanCompile.setCompileId(ids[0]+c+1);
            c++;
            projectPlanCompile.setProjectId(projectId);
            projectPlanCompile.setTemplateId(p.getTemplateId());
            projectPlanCompile.setStartDate(null);
            projectPlanCompile.setEndDate(null);
            projectPlanCompile.setWeight(p.getWeight());
            projectPlanCompile.setPreWarnDay(p.getPreWarnDay());
            projectPlanCompile.setSufWarnDay(p.getSufWarnDay());
            projectPlanCompile.setDepartmentId(p.getDepartmentId());
            projectPlanCompile.setDepartment(p.getDepartment());
            projectPlanCompile.setWarnUserId("");
            projectPlanCompile.setWarnName("");
            projectPlanCompile.setRemark("");
            projectPlanCompile.setIsComplete((short)1);
            projectPlanCompile.setCompleteDate(null);
            projectPlanCompile.setCompleteRemark("");
            projectPlanCompile.setNoCompleteRemark("");
            projectPlanCompile.setUpdateDate(DateTimeUtil.defaultVal());
            projectPlanCompile.setUpdateUserId(0L);
            projectPlanCompile.setVersions(1);
            projectPlanCompile.setCreateDate(new Date());
            projectPlanCompile.setCreateUserId(user.getUserId());
            projectPlanCompile.setOnDate(DateTimeUtil.defaultVal());
            projectPlanCompile.setOnUserId(0L);
            projectPlanCompile.setWarnPhone("");
            projectPlanCompileList.add(projectPlanCompile);
        }
        projectPlanCompileDao.batchPost(projectPlanCompileList);
        return projectPlanCompileList;
    }

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @param user
     * @return
     */
    @Transactional
    public int completePut(ProjectPlanCompile projectPlanCompile,User user) throws Exception{
        projectPlanCompile.setUpdateUserId(user.getUserId());
        projectPlanCompile.setUpdateDate(new Date());
        return projectPlanCompileDao.completePut(projectPlanCompile);
    }

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int checkPut(Long projectId,Long compileId,Short isComplete,Date completeDate) throws Exception{
        ProjectPlanCompile projectPlanCompileLog = new ProjectPlanCompile();
        projectPlanCompileLog.setProjectId(projectId);
        projectPlanCompileLog.setCompileId(compileId);
        projectPlanCompileLog.setIsComplete(isComplete);
        projectPlanCompileLog.setCompleteDate(completeDate);
        return projectPlanCompileDao.checkPut(projectPlanCompileLog);
    }

    /**
     * 获取开始预警数据
     * @param projectPlanWarnDay 循环预警天数
     * @return
     */
    public void getOfStartDateToSms(Integer projectPlanWarnDay){
        try {
            List<ProjectPlanCompile> projectPlanCompileList = projectPlanCompileDao.getOfStartDateToSms(new ProjectPlanCompile());
            for (ProjectPlanCompile p:projectPlanCompileList) {
                try {
                    if (this.isSms(p.getWarnDay(),projectPlanWarnDay,p.getPreWarnDay())){
                        for (String mobile:p.getWarnPhone().split(",")){
                            sendsmsService.smsStartWarn(mobile,p.getProjectName(),p.getTaskName(),p.getWarnDay()+"");
                        }
                    }
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 获取结束预警数据
     * @param projectPlanWarnDay 循环预警天数
     * @return
     */
    public void getOfEndDateToSms(Integer projectPlanWarnDay){
        try {
            List<ProjectPlanCompile> projectPlanCompileList = projectPlanCompileDao.getOfEndDateToSms(new ProjectPlanCompile());
            for (ProjectPlanCompile p:projectPlanCompileList) {
                try {
                    if (this.isSms(p.getWarnDay(),projectPlanWarnDay,p.getSufWarnDay())){
                        for (String mobile:p.getWarnPhone().split(",")){
                            sendsmsService.smsEndWarn(mobile,p.getProjectName(),p.getTaskName(),p.getWarnDay()+"");
                        }
                    }
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 是否发送短信
     * @param warnDay
     * @param projectPlanWarnDay
     * @param sufWarnDay
     * @return
     */
    private boolean isSms(Integer warnDay,Integer projectPlanWarnDay,Integer sufWarnDay){
        boolean retB = false;
        if (warnDay.equals(sufWarnDay)){
            retB = true;
        } else {
            if((sufWarnDay-warnDay)%projectPlanWarnDay==0){
                retB = true;
            }
        }
        return retB;
    }

    /**
     * 报表
     * @param projectPlanCompile
     * @param pageable
     * @return
     */
    public Page<ProjectPlanCompile> getToReport(ProjectPlanCompile projectPlanCompile,Pageable pageable){
        return projectPlanCompileDao.getToReport(projectPlanCompile,pageable);
    }

}
