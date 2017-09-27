package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-8-25 17:09:00.
*/
@Table(name = "hr_sign_leave_son")
public class HrSignLeaveSon extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainid,leaveType,startLeave,endLeave,leaveDayCount,restChanged,notes,createDate,status;
 //private String updateFiledValue = id=:id,formmainid=:formmainid,leaveType=:leaveType,startLeave=:startLeave,endLeave=:endLeave,leaveDayCount=:leaveDayCount,restChanged=:restChanged,notes=:notes,createDate=:createDate,status=:status;
    private Integer id;
    private String formmainid;
    private String leaveType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startLeave;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endLeave;
    private BigDecimal leaveDayCount;
    private BigDecimal restChanged;
    private String notes;
    private Date createDate;
    private Integer status;

    public HrSignLeaveSon() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormmainid() {
        return formmainid;
    }

    public void setFormmainid(String formmainid) {
        this.formmainid = formmainid;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Date getStartLeave() {
        return startLeave;
    }

    public void setStartLeave(Date startLeave) {
        this.startLeave = startLeave;
    }

    public Date getEndLeave() {
        return endLeave;
    }

    public void setEndLeave(Date endLeave) {
        this.endLeave = endLeave;
    }

    public BigDecimal getLeaveDayCount() {
        return leaveDayCount;
    }

    public void setLeaveDayCount(BigDecimal leaveDayCount) {
        this.leaveDayCount = leaveDayCount;
    }

    public BigDecimal getRestChanged() {
        return restChanged;
    }

    public void setRestChanged(BigDecimal restChanged) {
        this.restChanged = restChanged;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
