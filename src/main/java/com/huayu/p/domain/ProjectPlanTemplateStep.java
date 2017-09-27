package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;

/**
 * 计划模版阶段
* Created by ZXL on 2017-5-19 9:58:35.
*/
@Table(name = "project_plan_template_step")
public class ProjectPlanTemplateStep extends BaseDomain  implements Serializable{

    private Integer id;
    private String name;//名称
    private Integer sort;//排序
    private Short status;//状态 2正常1隐藏


}
