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
* Created by DengPeng on 2017-7-18 15:54:57.
*/
@Table(name = "hr_sign_leave")
public class HrSignLeave extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainId,company,department,position,username,IDcard,telephone,leaveType,startLeave,endLeave,leaveDayCount,notes;
 //private String updateFiledValue = id=:id,formmainId=:formmainId,company=:company,department=:department,position=:position,username=:username,IDcard=:IDcard,telephone=:telephone,leaveType=:leaveType,startLeave=:startLeave,endLeave=:endLeave,leaveDayCount=:leaveDayCount,notes=:notes;
    private String id;
    private String formmainId;
    private String company;
    private String department;
    private String position;
    private String username;
    private String idCard;
    private String telephone;
    private String leaveType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startLeave;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endLeave;
    private BigDecimal leaveDayCount;
    private String reason;
    private String notes;
    private Integer state;
    private Integer finishedFlag;
    private BigDecimal restChanged;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date startDate;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date endDate;
    @TableField
    private String userid;
    @TableField
    private String userNum;
    @TableField
    private Integer index;
    @TableField
    private String startLeaveStr;
    @TableField
    private String endLeaveStr;
    @TableField
    private Integer status;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;


    public HrSignLeave() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
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

    public BigDecimal getRestChanged() {
        return restChanged;
    }

    public void setRestChanged(BigDecimal restChanged) {
        this.restChanged = restChanged;
    }

    public String getStartLeaveStr() {
        return startLeaveStr;
    }

    public void setStartLeaveStr(String startLeaveStr) {
        this.startLeaveStr = startLeaveStr;
    }

    public String getEndLeaveStr() {
        return endLeaveStr;
    }

    public void setEndLeaveStr(String endLeaveStr) {
        this.endLeaveStr = endLeaveStr;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFinishedFlag() {
        return finishedFlag;
    }

    public void setFinishedFlag(Integer finishedFlag) {
        this.finishedFlag = finishedFlag;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormmainId() {
        return formmainId;
    }

    public void setFormmainId(String formmainId) {
        this.formmainId = formmainId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
