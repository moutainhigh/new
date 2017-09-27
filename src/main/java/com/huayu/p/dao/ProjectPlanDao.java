package com.huayu.p.dao;

import com.ly.dao.base.BasePageDao;
import com.huayu.p.domain.ProjectPlan;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 项目计划
 * @author ZXL 2017-05-19 10:20
 **/
@Repository
public class ProjectPlanDao extends BasePageDao<ProjectPlan,Serializable> {

    /**
     * 获取唯一数据
     * @param projectPlan ProjectPlan
     * @return
     * ProjectPlan
     */
    public ProjectPlan getOneByProjectId(ProjectPlan projectPlan){
        String sql = "SELECT n.*,s.projectName FROM (SELECT * FROM project_plan WHERE projectId=:projectId  AND `status`=2 LIMIT 0,1) n LEFT JOIN project_archives s ON n.projectId=s.projectId";
        return super.getOne(sql,projectPlan);
    }

    /**
     * 获取唯一数据
     * @param projectPlan ProjectPlan
     * @return
     * ProjectPlan
     */
    public ProjectPlan getOneByPlanId(ProjectPlan projectPlan){
        String sql = "SELECT n.*,s.projectName FROM (SELECT * FROM project_plan WHERE planId=:planId) n LEFT JOIN project_archives s ON n.projectId=s.projectId";
        return super.getOne(sql,projectPlan);
    }

}
