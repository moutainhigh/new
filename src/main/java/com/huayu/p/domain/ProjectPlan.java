package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目计划
* Created by ZXL on 2017-5-19 9:57:11.
*/
@Table(name = "project_plan")
public class ProjectPlan extends BaseDomain  implements Serializable{

    private Long planId;
    private Short status;//是否删除 1删除 2正常
    private Long projectId;//项目档案ID
    private String planName;//计划名称
    private Date documentsDate;//单据日期
    private Short versions;//版本
    private String remark;//备注
    private Short isOn;//是否生效 1否 2是
    private Date onDate;//生效日期
    private Date createDate;//创建时间
    private Long createUserId;//创建人员
    private Date updateDate;//修改时间
    private Long updateUserId;//修改人员ID
    private Date deleteDate;//删除时间
    private Long deleteUserId;//删除时间

    @TableField
    private String projectName;//项目名称

    public ProjectPlan() {
        this.dt = "desc";
        this.dtn = "planId";
        this.pageSize = 20;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Date getDocumentsDate() {
        return documentsDate;
    }

    public void setDocumentsDate(Date documentsDate) {
        this.documentsDate = documentsDate;
    }

    public Short getVersions() {
        return versions;
    }

    public void setVersions(Short versions) {
        this.versions = versions;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getIsOn() {
        return isOn;
    }

    public void setIsOn(Short isOn) {
        this.isOn = isOn;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Long getDeleteUserId() {
        return deleteUserId;
    }

    public void setDeleteUserId(Long deleteUserId) {
        this.deleteUserId = deleteUserId;
    }

    @Override
    public String toString(String s) {
        StringBuilder sb = new StringBuilder("?");
        if(dt!=null && s.contains("dt")) {
            sb.append("dt="+dt);
        }
        if(pageSize!=null && s.contains("pageSize")) {
            sb.append("&pageSize="+pageSize);
        }
        if(pageNo!=null && s.contains("pageNo")) {
            sb.append("&pageNo="+pageNo);
        }
        if(dtn!=null && s.contains("dtn")) {
            sb.append("&dtn="+dtn);
        }
        if(s1!=null && s.contains("s1")) {
            sb.append("&s1="+s1);
        }
        return sb.toString();
    }
}
