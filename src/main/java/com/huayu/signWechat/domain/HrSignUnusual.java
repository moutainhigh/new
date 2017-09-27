package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-17 15:28:16.
*/
@Table(name = "hr_sign_unusual")
public class HrSignUnusual extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,userid,username,telephone,checkin_type,exception_type,checkin_time,update_checkin_time,update_time,update_user,status;
 //private String updateFiledValue = id=:id,userid=:userid,username=:username,telephone=:telephone,checkin_type=:checkin_type,exception_type=:exception_type,checkin_time=:checkin_time,update_checkin_time=:update_checkin_time,update_time=:update_time,update_user=:update_user,status=:status;
    private Integer id;
    private String userid;
    private String username;
    private String telephone;
    private String checkin_type;
    private String exception_type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date checkin_time;
    private String location_detail;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_checkin_time;
    private Date update_time;
    private Date createTime;
    private Integer update_user;
    private String status;
    private String idCard;
    private String groupname;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date startDate;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date endDate;
    @TableField
    private String userNum;
    @TableField
    private String company;
    @TableField
    private String department;
    @TableField
    private String position;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date checkDate;
    @TableField
    private String mobile;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @TableField
    private Integer index;
    @TableField
    private String exportCheckinTime;


    public HrSignUnusual() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getExportCheckinTime() {
        return exportCheckinTime;
    }

    public void setExportCheckinTime(String exportCheckinTime) {
        this.exportCheckinTime = exportCheckinTime;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
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

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public HrSignUnusual(String userid) {
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

    public String getLocation_detail() {
        return location_detail;
    }

    public void setLocation_detail(String location_detail) {
        this.location_detail = location_detail;
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

    public Date getUpdate_checkin_time() {
        return update_checkin_time;
    }

    public void setUpdate_checkin_time(Date update_checkin_time) {
        this.update_checkin_time = update_checkin_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Integer update_user) {
        this.update_user = update_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
