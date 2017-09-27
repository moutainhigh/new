package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-13 11:35:08.
*/
@Table(name = "hr_atte_att_detail")
public class HrAtteAttDetail extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 3578683323573166075L;
   //private String updateFiledKey = id,userid,attDate,clockInTime,clockOutTime,startTime,endTime,workDay,realWorkDay,noIn,noOut,late,early,absent,lateCount,earlyCount,absentCount,workTime,symbol,attTime,absentR,isRead,exception;
 //private String updateFiledValue = id=:id,userid=:userid,attDate=:attDate,clockInTime=:clockInTime,clockOutTime=:clockOutTime,startTime=:startTime,endTime=:endTime,workDay=:workDay,realWorkDay=:realWorkDay,noIn=:noIn,noOut=:noOut,late=:late,early=:early,absent=:absent,lateCount=:lateCount,earlyCount=:earlyCount,absentCount=:absentCount,workTime=:workTime,symbol=:symbol,attTime=:attTime,absentR=:absentR,isRead=:isRead,exception=:exception;
    private Integer id;
    private Integer badgenumber;
    private Integer empId;
    private Integer userid;
   @JSONField(format="yyyy-MM-dd")
    private Date attDate;
   @JSONField(format="HH:mm:ss")
    private Date clockInTime;
   @JSONField(format="HH:mm:ss")
    private Date clockOutTime;
   @JSONField(format="HH:mm:ss")
    private Date startTime;
   @JSONField(format="HH:mm:ss")
    private Date endTime;
    private Double workDay;
    private Double realWorkDay;
    private Integer noIn;
    private Integer noOut;
    private Double late;
    private Double early;
    private Double absent;
    private Integer lateCount;
    private Integer earlyCount;
    private Integer absentCount;
    private Integer workTime;
    private String symbol;
    private Integer attTime;
    private Double absentR;
    private Integer isRead;
    private String exception;
    private Date createtime;

    public HrAtteAttDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private String companyName;

    @TableField
    private String departmentName;

    @TableField
    private String empName;


   public void addWorkDay(Double data){
      if (null==this.workDay){
         this.workDay = 0.0;
      }
      this.workDay += data==null?0:data;
   }

   public void addRealWorkDay(Double data){
      if (null==this.realWorkDay){
         this.realWorkDay = 0.0;
      }
      this.realWorkDay += data==null?0:data;
   }

   public void addLate(Double data){
      if (null==this.late){
         this.late = 0.0;
      }
      this.late += data==null?0:data;
   }

   public void addEarly(Double data){
      if (null==this.early){
         this.early = 0.0;
      }
      this.early += data==null?0:data;
   }

   public void addNoIn(Integer data){
      if (null==this.noIn){
         this.noIn = 0;
      }
      this.noIn += data==null?0:data;
   }

   public void addNoOut(Integer data){
      if (null==this.noOut){
         this.noOut = 0;
      }
      this.noOut += data==null?0:data;
   }


   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getDepartmentName() {
      return departmentName;
   }

   public void setDepartmentName(String departmentName) {
      this.departmentName = departmentName;
   }

   public String getEmpName() {
      return empName;
   }

   public void setEmpName(String empName) {
      this.empName = empName;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getBadgenumber() {
      return badgenumber;
   }

   public void setBadgenumber(Integer badgenumber) {
      this.badgenumber = badgenumber;
   }

   public Integer getEmpId() {
      return empId;
   }

   public void setEmpId(Integer empId) {
      this.empId = empId;
   }

   public Integer getUserid() {
      return userid;
   }

   public void setUserid(Integer userid) {
      this.userid = userid;
   }

   public Date getAttDate() {
      return attDate;
   }

   public void setAttDate(Date attDate) {
      this.attDate = attDate;
   }

   public Date getClockInTime() {
      return clockInTime;
   }

   public void setClockInTime(Date clockInTime) {
      this.clockInTime = clockInTime;
   }

   public Date getClockOutTime() {
      return clockOutTime;
   }

   public void setClockOutTime(Date clockOutTime) {
      this.clockOutTime = clockOutTime;
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

   public Double getWorkDay() {
      return workDay;
   }

   public void setWorkDay(Double workDay) {
      this.workDay = workDay;
   }

   public Double getRealWorkDay() {
      return realWorkDay;
   }

   public void setRealWorkDay(Double realWorkDay) {
      this.realWorkDay = realWorkDay;
   }

   public Integer getNoIn() {
      return noIn;
   }

   public void setNoIn(Integer noIn) {
      this.noIn = noIn;
   }

   public Integer getNoOut() {
      return noOut;
   }

   public void setNoOut(Integer noOut) {
      this.noOut = noOut;
   }

   public Double getLate() {
      return late;
   }

   public void setLate(Double late) {
      this.late = late;
   }

   public Double getEarly() {
      return early;
   }

   public void setEarly(Double early) {
      this.early = early;
   }

   public Double getAbsent() {
      return absent;
   }

   public void setAbsent(Double absent) {
      this.absent = absent;
   }

   public Integer getLateCount() {
      return lateCount;
   }

   public void setLateCount(Integer lateCount) {
      this.lateCount = lateCount;
   }

   public Integer getEarlyCount() {
      return earlyCount;
   }

   public void setEarlyCount(Integer earlyCount) {
      this.earlyCount = earlyCount;
   }

   public Integer getAbsentCount() {
      return absentCount;
   }

   public void setAbsentCount(Integer absentCount) {
      this.absentCount = absentCount;
   }

   public Integer getWorkTime() {
      return workTime;
   }

   public void setWorkTime(Integer workTime) {
      this.workTime = workTime;
   }

   public String getSymbol() {
      return symbol;
   }

   public void setSymbol(String symbol) {
      this.symbol = symbol;
   }

   public Integer getAttTime() {
      return attTime;
   }

   public void setAttTime(Integer attTime) {
      this.attTime = attTime;
   }

   public Double getAbsentR() {
      return absentR;
   }

   public void setAbsentR(Double absentR) {
      this.absentR = absentR;
   }

   public Integer getIsRead() {
      return isRead;
   }

   public void setIsRead(Integer isRead) {
      this.isRead = isRead;
   }

   public String getException() {
      return exception;
   }

   public void setException(String exception) {
      this.exception = exception;
   }

   public Date getCreatetime() {
      return createtime;
   }

   public void setCreatetime(Date createtime) {
      this.createtime = createtime;
   }
}
