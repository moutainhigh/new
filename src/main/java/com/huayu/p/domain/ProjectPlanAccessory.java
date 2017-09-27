package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.Long;
import java.lang.Short;
import java.util.Date;
import java.lang.String;

/**
 * 进度上传附件
* Created by DengPeng on 2017-5-26 11:43:32.
*/
@Table(name = "project_plan_accessory")
public class ProjectPlanAccessory extends BaseDomain  implements Serializable{

    private Long id;//ID
    private Short status;//状态 2正常1删除
    private Long projectId;//项目档案ID
    private Long compileId;//进度ID
    private String title;//附件标题
    private String url;//地址
    private Date createDate;//创建时间
    private Long createUserId;//创建人ID
    private Date deleteDate;//删除时间
    private Long deteleUserId;//删除人员ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompileId() {
        return compileId;
    }

    public void setCompileId(Long compileId) {
        this.compileId = compileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Long getDeteleUserId() {
        return deteleUserId;
    }

    public void setDeteleUserId(Long deteleUserId) {
        this.deteleUserId = deteleUserId;
    }
}
