package com.huayu.p.dao;

import com.huayu.p.domain.BaseProjectPlanCompile;
import com.huayu.p.domain.ProjectPlanCompileLog;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目计划编制修改日志
 *
 * @author ZXL 2017-05-19 10:21
 **/
@Repository
public class ProjectPlanCompileLogDao extends BasePageDao<ProjectPlanCompileLog,Serializable> {

    /**
     * 获取全部数据
     * @param projectPlanCompileLog ProjectPlanCompile
     * @return
     * list
     */
    public List<ProjectPlanCompileLog> getAll(ProjectPlanCompileLog projectPlanCompileLog){
        String sql = "SELECT c.*,t.stepName,t.`name` taskName,t.iskeyNode FROM (SELECT * FROM project_plan_compile_log WHERE projectId=:projectId AND versions=:versions) c LEFT JOIN (SELECT e.*,p.`name` stepName FROM project_plan_template e LEFT JOIN project_plan_template_step p ON e.stepId=p.id) t ON c.templateId=t.templateId ORDER BY t.sort ASC";
        return super.get(sql,projectPlanCompileLog);
    }

    /**
     * 批量新增数据
     * @param baseProjectPlanCompileList List
     * @return
     * int
     */
    public int[] batchPost(List<BaseProjectPlanCompile> baseProjectPlanCompileList){
        //String sql = "INSERT INTO project_plan_compile_log (projectId,compileId,templateId,startDate,endDate,weight,preWarnDay,sufWarnDay,departmentId,department,warnUserId,warnName,remark,isComplete,completeDate,completeRemark,noCompleteRemark,updateDate,updateUserId,versions,createDate,createUserId,onDate,onUserId,warnPhone) VALUES (:projectId,:compileId,:templateId,:startDate,:endDate,:weight,:preWarnDay,:sufWarnDay,:departmentId,:department,:warnUserId,:warnName,:remark,:isComplete,:completeDate,:completeRemark,:noCompleteRemark,:updateDate,:updateUserId,:versions,:createDate,:createUserId,:onDate,:onUserId,:warnPhone)";
        //return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(baseProjectPlanCompileList.toArray()));
        return this.baseBatchPost(SqlParameterSourceUtils.createBatch(baseProjectPlanCompileList.toArray()));
    }

    /**
     * 批量新增数据
     * @param projectPlanCompileLogList List
     * @return
     * int
     */
    public int[] batchPost2(List<ProjectPlanCompileLog> projectPlanCompileLogList){
        //String sql = "INSERT INTO project_plan_compile_log (projectId,compileId,templateId,startDate,endDate,weight,preWarnDay,sufWarnDay,departmentId,department,warnUserId,warnName,remark,isComplete,completeDate,completeRemark,noCompleteRemark,updateDate,updateUserId,versions,createDate,createUserId,onDate,onUserId,warnPhone) VALUES (:projectId,:compileId,:templateId,:startDate,:endDate,:weight,:preWarnDay,:sufWarnDay,:departmentId,:department,:warnUserId,:warnName,:remark,:isComplete,:completeDate,:completeRemark,:noCompleteRemark,:updateDate,:updateUserId,:versions,:createDate,:createUserId,:onDate,:onUserId,:warnPhone)";
        //return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(projectPlanCompileLogList.toArray()));
        return this.baseBatchPost(SqlParameterSourceUtils.createBatch(projectPlanCompileLogList.toArray()));
    }

    /**
     * 批量新增数据
     * @param sqlParameterSources
     * @return
     */
    private int[] baseBatchPost(SqlParameterSource[] sqlParameterSources){
        String sql = "INSERT INTO project_plan_compile_log (projectId,compileId,templateId,startDate,endDate,weight,preWarnDay,sufWarnDay,departmentId,department,warnUserId,warnName,remark,isComplete,completeDate,completeRemark,noCompleteRemark,updateDate,updateUserId,versions,createDate,createUserId,onDate,onUserId,warnPhone,reCompleteDate) VALUES (:projectId,:compileId,:templateId,:startDate,:endDate,:weight,:preWarnDay,:sufWarnDay,:departmentId,:department,:warnUserId,:warnName,:remark,:isComplete,:completeDate,:completeRemark,:noCompleteRemark,:updateDate,:updateUserId,:versions,:createDate,:createUserId,:onDate,:onUserId,:warnPhone,:reCompleteDate)";
        return super.batchUpdate(sql,sqlParameterSources);
    }

    /**
     * 批量修改数据
     * @param projectPlanCompileLogList list
     * @return
     * int
     */
    public int[] batchPut(List<ProjectPlanCompileLog> projectPlanCompileLogList){
        //String sql = "UPDATE project_plan_compile_log SET startDate=:startDate,endDate=:endDate,weight=:weight,preWarnDay=:preWarnDay,sufWarnDay=:sufWarnDay,departmentId=:departmentId,department=:department,warnUserId=:warnUserId,warnName=:warnName,remark=:remark,updateDate=:updateDate,updateUserId=:updateUserId,warnPhone=:warnPhone WHERE id=:id AND compileId=:compileId AND projectId=:projectId AND templateId=:templateId";
        String sql = "UPDATE project_plan_compile_log SET startDate=:startDate,endDate=:endDate,preWarnDay=:preWarnDay,sufWarnDay=:sufWarnDay,warnUserId=:warnUserId,warnName=:warnName,remark=:remark,updateDate=:updateDate,updateUserId=:updateUserId,warnPhone=:warnPhone WHERE id=:id AND compileId=:compileId AND projectId=:projectId AND templateId=:templateId";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(projectPlanCompileLogList.toArray()));
    }

    /**
     * 批量修改数据
     * @param projectPlanCompileLogList list
     * @return
     * int
     */
    public int put(ProjectPlanCompileLog projectPlanCompileLog){
        //String sql = "UPDATE project_plan_compile_log SET startDate=:startDate,endDate=:endDate,weight=:weight,preWarnDay=:preWarnDay,sufWarnDay=:sufWarnDay,departmentId=:departmentId,department=:department,warnUserId=:warnUserId,warnName=:warnName,remark=:remark,updateDate=:updateDate,updateUserId=:updateUserId,warnPhone=:warnPhone WHERE id=:id AND compileId=:compileId AND projectId=:projectId AND templateId=:templateId";
        String sql = "UPDATE project_plan_compile_log SET startDate=:startDate,endDate=:endDate,preWarnDay=:preWarnDay,sufWarnDay=:sufWarnDay,warnUserId=:warnUserId,warnName=:warnName,remark=:remark,updateDate=:updateDate,updateUserId=:updateUserId,warnPhone=:warnPhone WHERE id=:id AND compileId=:compileId AND projectId=:projectId AND templateId=:templateId";
        return super.put(sql,projectPlanCompileLog);
    }

    /**
     * 修改完成情况
     * @param projectPlanCompileLog
     * @return
     */
    public int completePut(ProjectPlanCompileLog projectPlanCompileLog){
        String sql = "UPDATE project_plan_compile_log SET isComplete=:isComplete,completeDate=:completeDate,completeRemark=:completeRemark,noCompleteRemark=:noCompleteRemark,updateDate=:updateDate,updateUserId=:updateUserId,reCompleteDate=:reCompleteDate WHERE compileId=:compileId AND projectId=:projectId AND versions=:versions";
        return super.put(sql,projectPlanCompileLog);
    }

    /**
     * 审核完成情况
     * @param projectPlanCompileLog
     * @return
     */
    public int checkPut(ProjectPlanCompileLog projectPlanCompileLog){
        String sql = "UPDATE project_plan_compile_log SET isComplete=:isComplete,completeDate=:completeDate WHERE compileId=:compileId AND projectId=:projectId AND versions=:versions";
        return super.put(sql,projectPlanCompileLog);
    }
}
