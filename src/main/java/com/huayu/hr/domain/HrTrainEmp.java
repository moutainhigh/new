package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-1-18 15:17:51.
*/
@Table(name = "hr_train_emp")
public class HrTrainEmp extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -6107326241197334666L;
   //private String updateFiledKey = id,trainId,empId,empCode,empName,company,department,job,duty,studyTime,score,cost,certificateNo,certificateName,note,createtime;
 //private String updateFiledValue = id=:id,trainId=:trainId,empId=:empId,empCode=:empCode,empName=:empName,company=:company,department=:department,job=:job,duty=:duty,studyTime=:studyTime,score=:score,cost=:cost,certificateNo=:certificateNo,certificateName=:certificateName,note=:note,createtime=:createtime;
    private Integer id;
    private Integer trainId;
    private Integer empId;
    private String empName;
    private Integer company;
    private String companyStr;
    private Integer department;
    private String departmentStr;
    private Integer jobSequence;
    private Integer job;
    private BigDecimal studyTime;
    private String score;
    private BigDecimal cost;
    private String certificateNo;
    private String certificateName;
    private String note;
    private Integer status;
    private Date createtime;
    private Date updatetime;

    public HrTrainEmp() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   @TableField
   private Integer type;

   @TableField
    private String way;

   public String getTypeStr(){
      if (null==type){
         return "";
      }
      String str = null;
      switch (type){
         case 0:
            str="内训";
            break;
         case 1:
            str="外训";
            break;
      }
      return str;
   }

   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JSONField(format="yyyy-MM-dd")
   private Date startTime;
   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JSONField(format="yyyy-MM-dd")
   private Date endTime;

   @TableField
   private String teacherName;

   @TableField
   private String trainName;
   @TableField
   private String trainCode;

   @TableField
   private String jobStr;

   @TableField
   private Integer timeCount;

   @TableField
   private String target;

   @TableField
   private String content;


   public Integer getTimeCount() {
      return timeCount;
   }

   public void setTimeCount(Integer timeCount) {
      this.timeCount = timeCount;
   }

   public String getTarget() {
      return target;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getTrainCode() {
      return trainCode;
   }

   public void setTrainCode(String trainCode) {
      this.trainCode = trainCode;
   }

   public String getWay() {
      return way;
   }

   public void setWay(String way) {
      this.way = way;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public String getTrainName() {
      return trainName;
   }

   public void setTrainName(String trainName) {
      this.trainName = trainName;
   }

   public String getTeacherName() {
      return teacherName;
   }

   public void setTeacherName(String teacherName) {
      this.teacherName = teacherName;
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

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getTrainId() {
      return trainId;
   }

   public void setTrainId(Integer trainId) {
      this.trainId = trainId;
   }

   public Integer getEmpId() {
      return empId;
   }

   public void setEmpId(Integer empId) {
      this.empId = empId;
   }

   public String getEmpName() {
      return empName;
   }

   public void setEmpName(String empName) {
      this.empName = empName;
   }

   public Integer getCompany() {
      return company;
   }

   public void setCompany(Integer company) {
      this.company = company;
   }

   public Integer getDepartment() {
      return department;
   }

   public void setDepartment(Integer department) {
      this.department = department;
   }

   public static long getSerialVersionUID() {
      return serialVersionUID;
   }

   public Integer getJobSequence() {
      return jobSequence;
   }

   public void setJobSequence(Integer jobSequence) {
      this.jobSequence = jobSequence;
   }

   public Integer getJob() {
      return job;
   }

   public void setJob(Integer job) {
      this.job = job;
   }

   public BigDecimal getStudyTime() {
      return studyTime;
   }

   public void setStudyTime(BigDecimal studyTime) {
      this.studyTime = studyTime;
   }

   public String getScore() {
      return score;
   }

   public void setScore(String score) {
      this.score = score;
   }

   public BigDecimal getCost() {
      return cost;
   }

   public void setCost(BigDecimal cost) {
      this.cost = cost;
   }

   public String getCertificateNo() {
      return certificateNo;
   }

   public void setCertificateNo(String certificateNo) {
      this.certificateNo = certificateNo;
   }

   public String getCertificateName() {
      return certificateName;
   }

   public void setCertificateName(String certificateName) {
      this.certificateName = certificateName;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public Date getCreatetime() {
      return createtime;
   }

   public void setCreatetime(Date createtime) {
      this.createtime = createtime;
   }

   public Date getUpdatetime() {
      return updatetime;
   }

   public void setUpdatetime(Date updatetime) {
      this.updatetime = updatetime;
   }

   public String getCompanyStr() {
      return companyStr;
   }

   public void setCompanyStr(String companyStr) {
      this.companyStr = companyStr;
   }

   public String getDepartmentStr() {
      return departmentStr;
   }

   public void setDepartmentStr(String departmentStr) {
      this.departmentStr = departmentStr;
   }

   public String getJobStr() {
      return jobStr;
   }

   public void setJobStr(String jobStr) {
      this.jobStr = jobStr;
   }
}
