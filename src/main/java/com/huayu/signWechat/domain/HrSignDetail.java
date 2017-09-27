package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-10 16:14:13.
*/
@Table(name = "hr_sign_detail")
public class HrSignDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,userId,username,telephone,groupname,checkin_type,exception_type,checkin_time,location_title,location_detail,wifiname,notes,wifimac,mediaids;
 //private String updateFiledValue = id=:id,userId=:userId,username=:username,telephone=:telephone,groupname=:groupname,checkin_type=:checkin_type,exception_type=:exception_type,checkin_time=:checkin_time,location_title=:location_title,location_detail=:location_detail,wifiname=:wifiname,notes=:notes,wifimac=:wifimac,mediaids=:mediaids;
    private Integer id;
    private String userid;
    private String username;
    private String telephone;
    private String groupname;
    private String checkin_type;
    private String exception_type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date checkin_time;
    private String location_title;
    private String location_detail;
    private String wifiname;
    private String notes;
    private String wifimac;
    private String mediaids;
    private Date createTime;
    private Date updateTime;
    private Integer updateUser;
    private Integer status;

    @TableField
    private String company;
    @TableField
    private String department;
    @TableField
    private String mobile;
    @TableField
    private String userNum;//用户编号
    @TableField
    private String week;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date startDate;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date endDate;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date checkDate;
    @TableField
    private String checkin_exception;
    @TableField
    private String checkout_type;
    @TableField
    private String checkout_exception;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date checkout_time;
    @TableField
    private Integer index;
    @TableField
    private String checkinTimeStr;
    @TableField
    private Integer num;
    @TableField
    private String position;


    public HrSignDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCheckinTimeStr() {
        return checkinTimeStr;
    }

    public void setCheckinTimeStr(String checkinTimeStr) {
        this.checkinTimeStr = checkinTimeStr;
    }

    public String getCheckin_exception() {
        return checkin_exception;
    }

    public void setCheckin_exception(String checkin_exception) {
        this.checkin_exception = checkin_exception;
    }

    public String getCheckout_type() {
        return checkout_type;
    }

    public void setCheckout_type(String checkout_type) {
        this.checkout_type = checkout_type;
    }

    public String getCheckout_exception() {
        return checkout_exception;
    }

    public void setCheckout_exception(String checkout_exception) {
        this.checkout_exception = checkout_exception;
    }

    public Date getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(Date checkout_time) {
        this.checkout_time = checkout_time;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
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

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getCheckin_type() {
        return checkin_type;
    }

    public void setCheckin_type(String checkin_type) {
        this.checkin_type = checkin_type;
    }

    public String getException_type() {
        return exception_type;
    }

    public void setException_type(String exception_type) {
        this.exception_type = exception_type;
    }

    public Date getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(Date checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getLocation_title() {
        return location_title;
    }

    public void setLocation_title(String location_title) {
        this.location_title = location_title;
    }

    public String getLocation_detail() {
        return location_detail;
    }

    public void setLocation_detail(String location_detail) {
        this.location_detail = location_detail;
    }

    public String getWifiname() {
        return wifiname;
    }

    public void setWifiname(String wifiname) {
        this.wifiname = wifiname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWifimac() {
        return wifimac;
    }

    public void setWifimac(String wifimac) {
        this.wifimac = wifimac;
    }

    public String getMediaids() {
        return mediaids;
    }

    public void setMediaids(String mediaids) {
        this.mediaids = mediaids;
    }

    @Override
    public String toString() {
        return "HrSignDetail{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", groupname='" + groupname + '\'' +
                ", checkin_type='" + checkin_type + '\'' +
                ", exception_type='" + exception_type + '\'' +
                ", checkin_time=" + checkin_time +
                ", location_title='" + location_title + '\'' +
                ", location_detail='" + location_detail + '\'' +
                ", wifiname='" + wifiname + '\'' +
                ", notes='" + notes + '\'' +
                ", wifimac='" + wifimac + '\'' +
                ", mediaids='" + mediaids + '\'' +
                '}';
    }
}
