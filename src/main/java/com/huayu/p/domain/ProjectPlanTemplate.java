package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;

/**
 *计划模版
* Created by ZXL on 2017-5-19 9:58:15.
*/
@Table(name = "project_plan_template")
public class ProjectPlanTemplate extends BaseDomain  implements Serializable{

    private Integer templateId;
    private Integer stepId;//阶段ID
    private Integer sort;//显示排序
    private String name;//任务名称
    private Short iskeyNode;//是否关键节点 1否2是
    private String departmentId;//主办部门ID 无对应默认为0
    private String department;//主办部门
    private String sign;//工作完成标志
    private Integer weight;//权重分值
    private Integer preWarnDay;//开始前预警天数
    private Integer sufWarnDay;//结束前预警天数
    private String remark;//备注

    @TableField
    private String stepName;//阶段名称
    @TableField
    private Integer nodeNum;//节点个数

    public Integer getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getIskeyNode() {
        return iskeyNode;
    }

    public void setIskeyNode(Short iskeyNode) {
        this.iskeyNode = iskeyNode;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPreWarnDay() {
        return preWarnDay;
    }

    public void setPreWarnDay(Integer preWarnDay) {
        this.preWarnDay = preWarnDay;
    }

    public Integer getSufWarnDay() {
        return sufWarnDay;
    }

    public void setSufWarnDay(Integer sufWarnDay) {
        this.sufWarnDay = sufWarnDay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
}
