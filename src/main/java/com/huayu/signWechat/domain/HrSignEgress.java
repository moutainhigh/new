package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-17 18:08:32.
*/
@Table(name = "hr_sign_egress")
public class HrSignEgress extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainId,userid,username,telephone,groupname,company,department,position,startTime,endTime,location_detail,wifiname,notes,wifimac,mediaids;
 //private String updateFiledValue = id=:id,formmainId=:formmainId,userid=:userid,username=:username,telephone=:telephone,groupname=:groupname,company=:company,department=:department,position=:position,startTime=:startTime,endTime=:endTime,location_detail=:location_detail,wifiname=:wifiname,notes=:notes,wifimac=:wifimac,mediaids=:mediaids;
    private String id;
    private String formmainId;
    private String username;
    private String telephone;
    private String company;
    private String department;
    private String position;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String location_detail;
    private String notes;
    private Integer state;
    private Integer finishedFlag;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date egressDate;
    @TableField
    private String userNum;//用户编码
    @TableField
    private String idCard;
    @TableField
    private Date startDate;
    @TableField
    private Date endDate;
    @TableField
    private Integer index;
    @TableField
    private String startTimeStr;
    @TableField
    private String endTimeStr;
    @TableField
    private Integer status;
    @TableField
    private String locationDetail;
    @TableField
    private String userid;
    @TableField
    private String checkin_type;

    public HrSignEgress() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getCheckin_type() {
        return checkin_type;
    }

    public void setCheckin_type(String checkin_type) {
        this.checkin_type = checkin_type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getEgressDate() {
        return egressDate;
    }

    public void setEgressDate(Date egressDate) {
        this.egressDate = egressDate;
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

   public String getLocation_detail() {
      return location_detail;
   }

   public void setLocation_detail(String location_detail) {
      this.location_detail = location_detail;
   }

   public String getNotes() {
      return notes;
   }

   public void setNotes(String notes) {
      this.notes = notes;
   }

}
