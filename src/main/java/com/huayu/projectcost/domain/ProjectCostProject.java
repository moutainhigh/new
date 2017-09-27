package com.huayu.projectcost.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-9-15 15:33:28.
*/
@Table(name = "project_cost_project")
public class ProjectCostProject extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,parentId,pcCompanyId,name,briefName,pcode,projectStatus,buildingType,systemLevel,isLastLevel,isCooperate,createTime,createUser;
 //private String updateFiledValue = id=:id,parentId=:parentId,pcCompanyId=:pcCompanyId,name=:name,briefName=:briefName,pcode=:pcode,projectStatus=:projectStatus,buildingType=:buildingType,systemLevel=:systemLevel,isLastLevel=:isLastLevel,isCooperate=:isCooperate,createTime=:createTime,createUser=:createUser;
    private Integer id;
    private Integer parentId;
    private Integer pcCompanyId;
    private String name;
    private String briefName;
    private String pcode;
    private Integer projectStatus;
    private String buildingType;
    private Integer systemLevel;
    private Integer isParent;
    private Integer isCooperate;
    private Integer status;
    private Date createTime;
    private Integer createUser;

    @TableField
    private String pcConmpanyName;

    public ProjectCostProject() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getPcConmpanyName() {
        return pcConmpanyName;
    }

    public void setPcConmpanyName(String pcConmpanyName) {
        this.pcConmpanyName = pcConmpanyName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPcCompanyId() {
        return pcCompanyId;
    }

    public void setPcCompanyId(Integer pcCompanyId) {
        this.pcCompanyId = pcCompanyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getSystemLevel() {
        return systemLevel;
    }

    public void setSystemLevel(Integer systemLevel) {
        this.systemLevel = systemLevel;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Integer getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(Integer isCooperate) {
        this.isCooperate = isCooperate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
