package com.huayu.p.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.p.dao.ProjectPlanCompileLogDao;
import com.huayu.p.domain.BaseProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompileLog;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 项目计划编制修改日志
 *
 * @author ZXL 2017-05-19 10:27
 **/
@Service
public class ProjectPlanCompileLogService {

    @Autowired
    private ProjectPlanCompileLogDao projectPlanCompileLogDao;
    @Autowired
    private ProjectArchivesService projectArchivesService;
    @Autowired
    private PHrEmployeeService pHrEmployeeService;

    public ProjectPlanCompileLog getOneByKey(Long id){
        ProjectPlanCompileLog projectPlanCompileLog = new ProjectPlanCompileLog();
        projectPlanCompileLog.setId(id);
        projectPlanCompileLog.setIdName("id");
        return projectPlanCompileLogDao.getOne(projectPlanCompileLog);
    }

    /**
     * 获取全部数据
     * @param projectId 项目档案ID
     * @return
     * list
     */
    public List<ProjectPlanCompileLog> getAll(Long projectId,Integer versions){
        ProjectPlanCompileLog projectPlanCompileLog = new ProjectPlanCompileLog();
        projectPlanCompileLog.setProjectId(projectId);
        projectPlanCompileLog.setVersions(versions);
        return projectPlanCompileLogDao.getAll(projectPlanCompileLog);
    }

    /**
     * 批量添加数据
     * @param baseProjectPlanCompiles List
     * @return
     * int
     * @throws Exception
     * e
     */
    @Transactional
    public int[] batchPost(List<BaseProjectPlanCompile> baseProjectPlanCompiles) throws Exception{
        return projectPlanCompileLogDao.batchPost(baseProjectPlanCompiles);
    }

    /**
     * 批量添加数据
     * @param baseProjectPlanCompiles List
     * @return
     * int
     * @throws Exception
     * e
     */
    @Transactional
    public int[] batchPost2(List<ProjectPlanCompileLog> projectPlanCompileLogList) throws Exception{
        return projectPlanCompileLogDao.batchPost2(projectPlanCompileLogList);
    }

    /**
     * 批量修改数据
     * @param baseProjectPlanCompiles List
     * @param projectId 项目ID
     * @return
     * int
     * @throws Exception
     * e
     */
    @Transactional(rollbackFor = Exception.class)
    public int[] batchPut(Long projectId,List<ProjectPlanCompileLog> projectPlanCompileLogList) throws Exception{
        if (projectArchivesService.getOne(projectId).getIsCompile()==2){
            throw new CustomException("保存失败");
        }
        User user = SpringSecurityUtil.getUser();
        Date updateDate = new Date();
        for (ProjectPlanCompileLog p:projectPlanCompileLogList){
            p.setUpdateDate(updateDate);
            p.setUpdateUserId(user.getUserId());
            if (StringUtils.isNotBlank(p.getWarnUserId())){
                p.setWarnPhone(pHrEmployeeService.getMobilesInId(p.getWarnUserId()));
            }
        }
        projectArchivesService.putIsCompile(projectId);
        return projectPlanCompileLogDao.batchPut(projectPlanCompileLogList);
    }

    /**
     * 修改数据
     * @param projectPlanCompileLog ProjectPlanCompileLog
     * @return
     * int
     * @throws Exception
     * e
     */
    @Transactional(rollbackFor = Exception.class)
    public int put(ProjectPlanCompileLog projectPlanCompileLog) throws Exception{
        ProjectPlanCompileLog projectPlanCompileLog2 = this.getOneByKey(projectPlanCompileLog.getId());
        if (projectPlanCompileLog.equals(projectPlanCompileLog2)){
            throw new CustomException("修改失败，未有修改项");
        }
        if (projectPlanCompileLog2.getIsComplete()!=1){
            throw new CustomException("修改失败，已结束的项不能修改");
        }
        User user = SpringSecurityUtil.getUser();
        projectPlanCompileLog.setUpdateDate(new Date());
        projectPlanCompileLog.setUpdateUserId(user.getUserId());
        if (StringUtils.isNotBlank(projectPlanCompileLog.getWarnUserId())){
            projectPlanCompileLog.setWarnPhone(pHrEmployeeService.getMobilesInId(projectPlanCompileLog.getWarnUserId()));
        }
        projectArchivesService.putNoOn(projectPlanCompileLog.getProjectId(),(short)1);
        return projectPlanCompileLogDao.put(projectPlanCompileLog);
    }

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int completePut(ProjectPlanCompile projectPlanCompile, User user) throws Exception{
        ProjectPlanCompileLog projectPlanCompileLog = new ProjectPlanCompileLog();
        projectPlanCompileLog.setProjectId(projectPlanCompile.getProjectId());
        projectPlanCompileLog.setCompileId(projectPlanCompile.getCompileId());
        projectPlanCompileLog.setVersions(projectPlanCompile.getVersions());
        projectPlanCompileLog.setNoCompleteRemark(projectPlanCompile.getNoCompleteRemark());
        projectPlanCompileLog.setCompleteRemark(projectPlanCompile.getCompleteRemark());
        projectPlanCompileLog.setIsComplete(projectPlanCompile.getIsComplete());
        projectPlanCompileLog.setCompleteDate(projectPlanCompile.getCompleteDate());
        projectPlanCompileLog.setReCompleteDate(projectPlanCompile.getReCompleteDate());
        projectPlanCompileLog.setUpdateUserId(user.getUserId());
        projectPlanCompileLog.setUpdateDate(new Date());
        return projectPlanCompileLogDao.completePut(projectPlanCompileLog);
    }

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int checkPut(Long projectId,Long compileId,Short isComplete,Date completeDate,Integer versions) throws Exception{
        ProjectPlanCompileLog projectPlanCompileLog = new ProjectPlanCompileLog();
        projectPlanCompileLog.setProjectId(projectId);
        projectPlanCompileLog.setCompileId(compileId);
        projectPlanCompileLog.setIsComplete(isComplete);
        projectPlanCompileLog.setCompleteDate(completeDate);
        projectPlanCompileLog.setVersions(versions);
        return projectPlanCompileLogDao.checkPut(projectPlanCompileLog);
    }

}
