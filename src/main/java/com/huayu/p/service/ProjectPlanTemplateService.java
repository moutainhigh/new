package com.huayu.p.service;

import com.huayu.p.dao.ProjectPlanTemplateDao;
import com.huayu.p.domain.ProjectPlanTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计划模版
 *
 * @author ZXL 2017-05-19 10:27
 **/
@Service
public class ProjectPlanTemplateService {

    @Autowired
    private ProjectPlanTemplateDao projectPlanTemplateDao;

    public ProjectPlanTemplate getOne(Integer templateId){
        ProjectPlanTemplate projectPlanTemplate = new ProjectPlanTemplate();
        projectPlanTemplate.setTemplateId(templateId);
        projectPlanTemplate.setIdName("templateId");
        return projectPlanTemplateDao.getOne(projectPlanTemplate);
    }

    /**
     * 获取全部数据
     * @return
     * list
     */
    public List<ProjectPlanTemplate> get(){
        return projectPlanTemplateDao.get(new ProjectPlanTemplate());
    }

    /**
     * 获取节点个数和权重分数
     * @return
     */
    public ProjectPlanTemplate getNodeNumAndWeight(){
        return projectPlanTemplateDao.getNodeNumAndWeight(new ProjectPlanTemplate());
    }

}
