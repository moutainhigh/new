package com.huayu.p.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.p.domain.BaseProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompileLog;
import com.huayu.p.util.CommonUtil;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目计划编制
 *
 * @author ZXL 2017-05-19 10:21
 **/
@Repository
public class ProjectPlanCompileDao extends BasePageDao<ProjectPlanCompile,Serializable> {

    /**
     * 获取唯一数据
     * @param projectPlanCompileLog
     * @return
     */
    public ProjectPlanCompile getOne(ProjectPlanCompile projectPlanCompile){
        String sql = "SELECT * FROM project_plan_compile WHERE compileId=:compileId AND projectId=:projectId";
        return super.getOne(sql,projectPlanCompile);
    }

    /**
     * 获取全部数据
     * @param projectPlanCompile ProjectPlanCompile
     * @return
     * list
     */
    public List<ProjectPlanCompile> getAll(ProjectPlanCompile projectPlanCompile){
        String sql = "SELECT c.*,t.stepName,t.`name` taskName,t.iskeyNode FROM (SELECT * FROM project_plan_compile WHERE projectId=:projectId) c LEFT JOIN (SELECT e.*,p.`name` stepName FROM project_plan_template e LEFT JOIN project_plan_template_step p ON e.stepId=p.id) t ON c.templateId=t.templateId ORDER BY t.sort ASC";
        return super.get(sql,projectPlanCompile);
    }

    /**
     * 分页获取待审核数据
     * @param pageable
     * @param projectPlanCompile
     * @return
     */
    public Page<ProjectPlanCompile> getToCheck(Pageable pageable, ProjectPlanCompile projectPlanCompile){
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT e.*,t.stepName,t.`name` taskName,t.iskeyNode,s.projectName FROM project_plan_compile e LEFT JOIN project_archives s ON e.projectId=s.projectId LEFT JOIN (SELECT e.*,p.`name` stepName FROM project_plan_template e LEFT JOIN project_plan_template_step p ON e.stepId=p.id) t ON e.templateId=t.templateId WHERE e.isComplete=3 ");
        if (CommonUtil.isInt(projectPlanCompile.getI1())){
            sql.append(" AND s.areaId=:i1 ");
        }
        if (StringUtils.isNotBlank(projectPlanCompile.getS1())){
            sql.append(" AND INSTR(s.projectName,:s1)>0 ");
        }
        sql.append(" AND s.areaId REGEXP("+user.getDataAuthoritiesRegexp()+")");
        sql.append(" ORDER BY e.completeDate ASC");
        return super.get(sql.toString(),projectPlanCompile,pageable );
    }

    /**
     * 批量新增数据
     * @param projectPlanCompileList List
     * @return
     * int
     */
    public int[] batchPost(List<BaseProjectPlanCompile> projectPlanCompileList){
        String sql = "INSERT INTO project_plan_compile (compileId,projectId,templateId,startDate,endDate,weight,preWarnDay,sufWarnDay,departmentId,department,warnUserId,warnName,remark,isComplete,completeDate,completeRemark,noCompleteRemark,updateDate,updateUserId,createDate,createUserId,onDate,onUserId,warnPhone,reCompleteDate) VALUES (:compileId,:projectId,:templateId,:startDate,:endDate,:weight,:preWarnDay,:sufWarnDay,:departmentId,:department,:warnUserId,:warnName,:remark,:isComplete,:completeDate,:completeRemark,:noCompleteRemark,:updateDate,:updateUserId,:createDate,:createUserId,:onDate,:onUserId,:warnPhone,:reCompleteDate)";
        return super.batchUpdate(sql,SqlParameterSourceUtils.createBatch(projectPlanCompileList.toArray()));
    }

    /**
     * 批量修改数据
     * @param projectPlanCompileLogList list
     * @return
     * int
     */
    public int[] batchPut(List<ProjectPlanCompileLog> projectPlanCompileList){
        String sql = "UPDATE project_plan_compile SET startDate=:startDate,endDate=:endDate,weight=:weight,preWarnDay=:preWarnDay,sufWarnDay=:sufWarnDay,departmentId=:departmentId,department=:department,warnUserId=:warnUserId,warnName=:warnName,remark=:remark,updateDate=:updateDate,updateUserId=:updateUserId,versions=:versions,onDate=:onDate,onUserId=:onUserId,warnPhone=:warnPhone WHERE compileId=:compileId AND projectId=:projectId AND templateId=:templateId";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(projectPlanCompileList.toArray()));
    }

    /**
     * 修改完成情况
     * @param projectPlanCompile
     * @return
     */
    public int completePut(ProjectPlanCompile projectPlanCompile){
        String sql = "UPDATE project_plan_compile SET isComplete=:isComplete,completeDate=:completeDate,completeRemark=:completeRemark,noCompleteRemark=:noCompleteRemark,updateDate=:updateDate,updateUserId=:updateUserId,reCompleteDate=:reCompleteDate WHERE compileId=:compileId AND projectId=:projectId";
        return super.put(sql,projectPlanCompile);
    }

    /**
     * 审核完成情况
     * @param projectPlanCompile
     * @return
     */
    public int checkPut(ProjectPlanCompile projectPlanCompile){
        String sql = "UPDATE project_plan_compile SET isComplete=:isComplete,completeDate=:completeDate WHERE compileId=:compileId AND projectId=:projectId";
        return super.put(sql,projectPlanCompile);
    }

    /**
     * 获取开始预警数据
     * @param projectPlanCompile
     * @return
     */
    public List<ProjectPlanCompile> getOfStartDateToSms(ProjectPlanCompile projectPlanCompile){
        StringBuilder sql = new StringBuilder("SELECT e.*,t.stepName,t.`name` taskName,t.iskeyNode,s.projectName,(TO_DAYS(e.startDate) - TO_DAYS(CURDATE())) warnDay FROM project_plan_compile e ");
        sql.append(" LEFT JOIN project_archives s ON e.projectId=s.projectId ");
        sql.append(" LEFT JOIN (SELECT e.*,p.`name` stepName FROM project_plan_template e LEFT JOIN project_plan_template_step p ON e.stepId=p.id) t ON e.templateId=t.templateId ");
        sql.append(" WHERE e.isComplete=1 AND e.warnPhone!='' AND e.startDate IS NOT NULL AND DATE_FORMAT(e.startDate,'%Y-%m-%d') > DATE_FORMAT(CURDATE(),'%Y-%m-%d') AND (TO_DAYS(e.startDate) - TO_DAYS(CURDATE())) <= e.preWarnDay");
        return super.get(sql.toString(),projectPlanCompile);
    }

    /**
     * 获取结束预警数据
     * @param projectPlanCompile
     * @return
     */
    public List<ProjectPlanCompile> getOfEndDateToSms(ProjectPlanCompile projectPlanCompile){
        StringBuilder sql = new StringBuilder("SELECT e.*,t.stepName,t.`name` taskName,t.iskeyNode,s.projectName,(TO_DAYS(e.endDate) - TO_DAYS(CURDATE())) warnDay FROM project_plan_compile e ");
        sql.append(" LEFT JOIN project_archives s ON e.projectId=s.projectId ");
        sql.append(" LEFT JOIN (SELECT e.*,p.`name` stepName FROM project_plan_template e LEFT JOIN project_plan_template_step p ON e.stepId=p.id) t ON e.templateId=t.templateId ");
        sql.append(" WHERE e.isComplete=1 AND e.warnPhone!='' AND e.endDate IS NOT NULL AND DATE_FORMAT(e.endDate,'%Y-%m-%d') > DATE_FORMAT(CURDATE(),'%Y-%m-%d') AND (TO_DAYS(e.endDate) - TO_DAYS(CURDATE())) <= e.sufWarnDay");
        return super.get(sql.toString(),projectPlanCompile);
    }

    /**
     * 报表
     * @param projectPlanCompile
     * @param pageable
     * @return
     */
    public Page<ProjectPlanCompile> getToReport(ProjectPlanCompile projectPlanCompile,Pageable pageable){
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT e.*,t.`name` taskName,s.projectName,a.`name` areaName,s.allWeight FROM project_plan_compile e LEFT JOIN project_archives s ON e.projectId=s.projectId LEFT JOIN project_area a ON s.areaId=a.id LEFT JOIN project_plan_template t ON e.templateId=t.templateId WHERE e.isComplete=2 AND s.areaId REGEXP("+user.getDataAuthoritiesRegexp()+") ");
        if (StringUtils.isNotBlank(projectPlanCompile.getS1())) {
            sql.append(" AND INSTR(s.projectName,:s1)>0 ");
        }
        if (CommonUtil.isInt(projectPlanCompile.getI1())) {
            sql.append(" AND s.areaId=:i1 ");
        }
        if (StringUtils.isNotBlank(projectPlanCompile.getD3())&&StringUtils.isNotBlank(projectPlanCompile.getD4())) {//订单收货日期
            sql.append(" AND e.completeDate BETWEEN :d3 AND :d4 ");
        }
        sql.append(" ORDER BY s.areaId ASC,s.projectId ASC,e.completeDate DESC");
        return super.get(sql.toString(),projectPlanCompile,pageable);
    }

}
