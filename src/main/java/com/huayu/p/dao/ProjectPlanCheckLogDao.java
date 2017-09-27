package com.huayu.p.dao;

import com.huayu.p.domain.ProjectPlanCheckLog;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 进度审核记录
 * @author ZXL 2017-06-01 13:18
 **/
@Repository
public class ProjectPlanCheckLogDao extends BasePageDao<ProjectPlanCheckLog,Serializable> {

    /**
     * 条件获取全部数据
     * @param projectPlanCheckLog
     * @return
     */
    public List<ProjectPlanCheckLog> getAll(ProjectPlanCheckLog projectPlanCheckLog){
        String sql = "SELECT * FROM project_plan_check_log WHERE compileId=:compileId AND projectId=:projectId GROUP BY checkId ASC";
        return super.get(sql,projectPlanCheckLog);
    }

    /**
     * 新增数据
     * @param projectPlanCheckLog
     * @return
     */
    public int post(ProjectPlanCheckLog projectPlanCheckLog){
        String sql = "INSERT INTO project_plan_check_log (type,`status`,remark,completeRemark,noCompleteRemark,checkUserId,checkDate,projectId,compileId,templateId) VALUES (:type,:status,:remark,:completeRemark,:noCompleteRemark,:checkUserId,:checkDate,:projectId,:compileId,:templateId)";
        return super.post(sql,projectPlanCheckLog);
    }

}
