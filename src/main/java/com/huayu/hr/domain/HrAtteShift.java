package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-5 10:19:27.
*/
@Table(name = "hr_atte_shift")
public class HrAtteShift extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 365015191074322330L;
   //private String updateFiledKey = id,schName,startTime,endTime,lateMinutes,earlyMinutes,mustCheckIn,mustCheckOut,checkInTime1,checkInTime2,checkOutTime1,checkOutTime2,workDay,workTime,status,createTime,createUser,updateTime,updateUser,deleteTime,deleteUser;
 //private String updateFiledValue = id=:id,schName=:schName,startTime=:startTime,endTime=:endTime,lateMinutes=:lateMinutes,earlyMinutes=:earlyMinutes,mustCheckIn=:mustCheckIn,mustCheckOut=:mustCheckOut,checkInTime1=:checkInTime1,checkInTime2=:checkInTime2,checkOutTime1=:checkOutTime1,checkOutTime2=:checkOutTime2,workDay=:workDay,workTime=:workTime,status=:status,createTime=:createTime,createUser=:createUser,updateTime=:updateTime,updateUser=:updateUser,deleteTime=:deleteTime,deleteUser=:deleteUser;
    private Integer id;
    private String schName;
//   @DateTimeFormat(pattern = "HH:mm:ss")
   @JSONField(format="HH:mm:ss")
    private Time startTime;
//   @DateTimeFormat(pattern = "HH:mm:ss")
   @JSONField(format="HH:mm:ss")
    private Time endTime;
    private Integer lateMinutes;
    private Integer earlyMinutes;
    private Integer mustCheckIn;
    private Integer mustCheckOut;
//   @DateTimeFormat(pattern = "HH:mm:ss")
    private Time checkInTime1;
//   @DateTimeFormat(pattern = "HH:mm:ss")
    private Time checkInTime2;
//   @DateTimeFormat(pattern = "HH:mm:ss")
    private Time checkOutTime1;
//   @DateTimeFormat(pattern = "HH:mm:ss")
    private Time checkOutTime2;
    private BigDecimal workDay;
    private Integer workTime;
    private Integer status;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    private Date deleteTime;
    private Integer deleteUser;

    public HrAtteShift() {
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

   public String getSchName() {
      return schName;
   }

   public void setSchName(String schName) {
      this.schName = schName;
   }

   public Time getStartTime() {
      return startTime;
   }

   public void setStartTime(Time startTime) {
      this.startTime = startTime;
   }

   public Time getEndTime() {
      return endTime;
   }

   public void setEndTime(Time endTime) {
      this.endTime = endTime;
   }

   public Integer getLateMinutes() {
      return lateMinutes;
   }

   public void setLateMinutes(Integer lateMinutes) {
      this.lateMinutes = lateMinutes;
   }

   public Integer getEarlyMinutes() {
      return earlyMinutes;
   }

   public void setEarlyMinutes(Integer earlyMinutes) {
      this.earlyMinutes = earlyMinutes;
   }

   public Integer getMustCheckIn() {
      return mustCheckIn;
   }

   public void setMustCheckIn(Integer mustCheckIn) {
      this.mustCheckIn = mustCheckIn;
   }

   public Integer getMustCheckOut() {
      return mustCheckOut;
   }

   public void setMustCheckOut(Integer mustCheckOut) {
      this.mustCheckOut = mustCheckOut;
   }

   public Time getCheckInTime1() {
      return checkInTime1;
   }

   public void setCheckInTime1(Time checkInTime1) {
      this.checkInTime1 = checkInTime1;
   }

   public Time getCheckInTime2() {
      return checkInTime2;
   }

   public void setCheckInTime2(Time checkInTime2) {
      this.checkInTime2 = checkInTime2;
   }

   public Time getCheckOutTime1() {
      return checkOutTime1;
   }

   public void setCheckOutTime1(Time checkOutTime1) {
      this.checkOutTime1 = checkOutTime1;
   }

   public Time getCheckOutTime2() {
      return checkOutTime2;
   }

   public void setCheckOutTime2(Time checkOutTime2) {
      this.checkOutTime2 = checkOutTime2;
   }

   public BigDecimal getWorkDay() {
      return workDay;
   }

   public void setWorkDay(BigDecimal workDay) {
      this.workDay = workDay;
   }

   public Integer getWorkTime() {
      return workTime;
   }

   public void setWorkTime(Integer workTime) {
      this.workTime = workTime;
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
