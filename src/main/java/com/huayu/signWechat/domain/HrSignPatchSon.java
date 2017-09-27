package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-31 22:23:31.
*/
@Table(name = "hr_sign_patch_son")
public class HrSignPatchSon extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainId,patchDate,patchTime,patchType,notes;
 //private String updateFiledValue = id=:id,formmainId=:formmainId,patchDate=:patchDate,patchTime=:patchTime,patchType=:patchType,notes=:notes;
    private Integer id;
    private String formmainId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date patchDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date patchTime;
    private Integer patchType;
    private String notes;
    private Integer status;

    @TableField
    private String name;
    @TableField
    private String company;
    @TableField
    private String department;
    @TableField
    private String position;
    @TableField
    private String telephone;
    @TableField
    private String username;
    @TableField
    private String mobile;
    @TableField
    private String userNum;
    @TableField
    private Integer index;
    @TableField
    private String patchTypeName;
    @TableField
    private String patchTimeStr;
    @TableField
    private String userid;

    public HrSignPatchSon() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPatchTimeStr() {
        return patchTimeStr;
    }

    public void setPatchTimeStr(String patchTimeStr) {
        this.patchTimeStr = patchTimeStr;
    }

    public String getPatchTypeName() {
        return patchTypeName;
    }

    public void setPatchTypeName(String patchTypeName) {
        this.patchTypeName = patchTypeName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormmainId() {
        return formmainId;
    }

    public void setFormmainId(String formmainId) {
        this.formmainId = formmainId;
    }

    public Date getPatchDate() {
        return patchDate;
    }

    public void setPatchDate(Date patchDate) {
        this.patchDate = patchDate;
    }

    public Date getPatchTime() {
        return patchTime;
    }

    public void setPatchTime(Date patchTime) {
        this.patchTime = patchTime;
    }

    public Integer getPatchType() {
        return patchType;
    }

    public void setPatchType(Integer patchType) {
        this.patchType = patchType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
