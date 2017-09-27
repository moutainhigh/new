package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-7 16:09:18.
*/
@Table(name = "hr_atte_holidays")
public class HrAtteHolidays extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -8308428055590147102L;
    //private String updateFiledKey = id,name,startTime,endTime,duration,status,createTime,createUser,updateTime,updateUser,deleteTime,deleteUser;
 //private String updateFiledValue = id=:id,name=:name,startTime=:startTime,endTime=:endTime,duration=:duration,status=:status,createTime=:createTime,createUser=:createUser,updateTime=:updateTime,updateUser=:updateUser,deleteTime=:deleteTime,deleteUser=:deleteUser;
    private Integer id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date endTime;
    private Integer duration;
    private Integer status;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    private Date deleteTime;
    private Integer deleteUser;

    public HrAtteHolidays() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }
}
