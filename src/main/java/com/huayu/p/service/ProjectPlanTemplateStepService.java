package com.huayu.p.service;

import com.huayu.p.dao.ProjectPlanTemplateStepDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 计划模版阶段
 *
 * @author ZXL 2017-05-19 10:29
 **/
@Service
public class ProjectPlanTemplateStepService {

    @Autowired
    private ProjectPlanTemplateStepDao projectPlanTemplateStepDao;

}
