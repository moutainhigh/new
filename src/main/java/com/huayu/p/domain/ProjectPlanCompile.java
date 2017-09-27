package com.huayu.p.domain;

import com.huayu.common.tool.DateTimeUtil;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* 项目计划编制
* Created by ZXL on 2017-5-19 9:57:32.
*/
@Table(name = "project_plan_compile")
public class ProjectPlanCompile extends BaseProjectPlanCompile implements Serializable{

    @TableField
    private Short completeStatus;//1按时完成2未按时完成3超期未完成4未超期未完成

    public Short getCompleteStatus() {
        if (null!=getIsComplete()&&null!=getEndDate()){
            if (getIsComplete()==2){
                if (DateTimeUtil.isAfter(DateTimeUtil.YYYY_MM_DD,getCompleteDate(),getEndDate(),10))
                    return 2;
                else
                    return 1;
            } else {
                if (DateTimeUtil.isAfter(DateTimeUtil.YYYY_MM_DD,new Date(),getEndDate(),10))
                    return 3;
                else
                    return 4;
            }
        }
        return completeStatus;
    }

    public void setCompleteStatus(Short completeStatus) {
        this.completeStatus = completeStatus;
    }

}
