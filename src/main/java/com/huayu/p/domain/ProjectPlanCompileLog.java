package com.huayu.p.domain;

import com.ly.dao.base.Table;

import java.io.Serializable;

/**
 * 项目计划编制修改日志
* Created by ZXL on 2017-5-19 9:57:55.
*/
@Table(name = "project_plan_compile_log")
public class ProjectPlanCompileLog extends BaseProjectPlanCompile implements Serializable{

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
