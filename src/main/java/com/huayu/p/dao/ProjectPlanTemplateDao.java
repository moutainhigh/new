package com.huayu.p.dao;

import com.ly.dao.base.BasePageDao;
import com.huayu.p.domain.ProjectPlanTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 计划模版
 *
 * @author ZXL 2017-05-19 10:22
 **/
@Repository
public class ProjectPlanTemplateDao extends BasePageDao<ProjectPlanTemplate,Serializable> {

    /**
     * 获取全部数据
     * @param projectPlanTemplate ProjectPlanTemplate
     * @return
     * list
     */
    public List<ProjectPlanTemplate> get(ProjectPlanTemplate projectPlanTemplate){
        String sql = "SELECT e.*,p.`name` stepName FROM project_plan_template e LEFT JOIN project_plan_template_step p ON e.stepId=p.id ORDER BY e.sort ASC";
        return super.get(sql,projectPlanTemplate);
    }

    /**
     * 获取节点个数和权重分数
     * @param projectPlanTemplate
     * @return
     */
    public ProjectPlanTemplate getNodeNumAndWeight(ProjectPlanTemplate projectPlanTemplate){
        String sql = "SELECT COUNT(0) nodeNum,SUM(weight) weight FROM project_plan_template";
        return super.getOne(sql,projectPlanTemplate);
    }

}
