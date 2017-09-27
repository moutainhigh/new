package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;

/**
 * 进度审核记录
* Created by DengPeng on 2017-6-1 13:14:36.
*/
@Table(name = "project_plan_check_log")
public class ProjectPlanCheckLog extends BaseDomain  implements Serializable{

    private Long checkId;
    private Short type;//1待审核 2审核
    private Short status;
    private String remark;
    private String completeRemark;
    private String noCompleteRemark;
    private Long checkUserId;
    private Date checkDate;
    private Long compileId;//项目计划编制ID
    private Long projectId;//项目档案ID
    private Integer templateId;//模板ID

    @TableField
    private String statusName;//状态中文名称
    @TableField
    private String typeName;//类型中文名称
    @TableField
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date completeDate;//完成时间

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getTypeName() {
        return null!=getType()?(getType()==2?"审核":"提交"):typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getCompleteRemark() {
        return completeRemark;
    }

    public void setCompleteRemark(String completeRemark) {
        this.completeRemark = completeRemark;
    }

    public String getNoCompleteRemark() {
        return noCompleteRemark;
    }

    public void setNoCompleteRemark(String noCompleteRemark) {
        this.noCompleteRemark = noCompleteRemark;
    }

    public String getStatusName() {
        return null!=getStatus()?(getStatus()==2?"通过":"未通过"):statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getCompileId() {
        return compileId;
    }

    public void setCompileId(Long compileId) {
        this.compileId = compileId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}
