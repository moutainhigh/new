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
* Created by DengPeng on 2017-7-18 11:23:48.
*/
@Table(name = "hr_recruitment_plan")
public class HrRecruitmentPlan extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 4719714665469162177L;
   //private String updateFiledKey = id,company,companyName,departmentName,jobName,dutylevel,dutyLevelName,vacancyCount,askDate,overDate,fileCount,requirement,progress,fileName,vacancyCount2,planJoinDate,overJoinDate,payment,reportJobName;
 //private String updateFiledValue = id=:id,company=:company,companyName=:companyName,departmentName=:departmentName,jobName=:jobName,dutylevel=:dutylevel,dutyLevelName=:dutyLevelName,vacancyCount=:vacancyCount,askDate=:askDate,overDate=:overDate,fileCount=:fileCount,requirement=:requirement,progress=:progress,fileName=:fileName,vacancyCount2=:vacancyCount2,planJoinDate=:planJoinDate,overJoinDate=:overJoinDate,payment=:payment,reportJobName=:reportJobName;
    private Integer id;
   private String plat;
    private Integer companyId;
    private String companyName;
    private String departmentName;
    private String jobName;
    private Integer dutyLevel;
    private String dutyLevelName;
    private Integer vacancyCount;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date askDate;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date overDate;
    private Integer fileCount;
    private String requirement;
    private String progress;
    private String fileName;
    private Integer vacancyCount2;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date planJoinDate;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date overJoinDate;
    private BigDecimal payment;
    private String reportJobName;
   private Integer status;
    private Date createtime;
    private Integer createUser;
    private Date deletetime;
   private Integer deleteUser;
   private Date updatetime;
   private Integer updateUser;

    public HrRecruitmentPlan() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date startDate;
   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date endDate;


   public String getPlat() {
      return plat;
   }

   public void setPlat(String plat) {
      this.plat = plat;
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

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getCompanyId() {
      return companyId;
   }

   public void setCompanyId(Integer companyId) {
      this.companyId = companyId;
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

   public String getJobName() {
      return jobName;
   }

   public void setJobName(String jobName) {
      this.jobName = jobName;
   }

   public Integer getDutyLevel() {
      return dutyLevel;
   }

   public void setDutyLevel(Integer dutyLevel) {
      this.dutyLevel = dutyLevel;
   }

   public String getDutyLevelName() {
      return dutyLevelName;
   }

   public void setDutyLevelName(String dutyLevelName) {
      this.dutyLevelName = dutyLevelName;
   }

   public Integer getVacancyCount() {
      return vacancyCount;
   }

   public void setVacancyCount(Integer vacancyCount) {
      this.vacancyCount = vacancyCount;
   }

   public Date getAskDate() {
      return askDate;
   }

   public void setAskDate(Date askDate) {
      this.askDate = askDate;
   }

   public Date getOverDate() {
      return overDate;
   }

   public void setOverDate(Date overDate) {
      this.overDate = overDate;
   }

   public Integer getFileCount() {
      return fileCount;
   }

   public void setFileCount(Integer fileCount) {
      this.fileCount = fileCount;
   }

   public String getRequirement() {
      return requirement;
   }

   public void setRequirement(String requirement) {
      this.requirement = requirement;
   }

   public String getProgress() {
      return progress;
   }

   public void setProgress(String progress) {
      this.progress = progress;
   }

   public String getFileName() {
      return fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   public Integer getVacancyCount2() {
      return vacancyCount2;
   }

   public void setVacancyCount2(Integer vacancyCount2) {
      this.vacancyCount2 = vacancyCount2;
   }

   public Date getPlanJoinDate() {
      return planJoinDate;
   }

   public void setPlanJoinDate(Date planJoinDate) {
      this.planJoinDate = planJoinDate;
   }

   public Date getOverJoinDate() {
      return overJoinDate;
   }

   public void setOverJoinDate(Date overJoinDate) {
      this.overJoinDate = overJoinDate;
   }

   public BigDecimal getPayment() {
      return payment;
   }

   public void setPayment(BigDecimal payment) {
      this.payment = payment;
   }

   public String getReportJobName() {
      return reportJobName;
   }

   public void setReportJobName(String reportJobName) {
      this.reportJobName = reportJobName;
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

   public Integer getCreateUser() {
      return createUser;
   }

   public void setCreateUser(Integer createUser) {
      this.createUser = createUser;
   }

   public Date getDeletetime() {
      return deletetime;
   }

   public void setDeletetime(Date deletetime) {
      this.deletetime = deletetime;
   }

   public Integer getDeleteUser() {
      return deleteUser;
   }

   public void setDeleteUser(Integer deleteUser) {
      this.deleteUser = deleteUser;
   }

   public Date getUpdatetime() {
      return updatetime;
   }

   public void setUpdatetime(Date updatetime) {
      this.updatetime = updatetime;
   }

   public Integer getUpdateUser() {
      return updateUser;
   }

   public void setUpdateUser(Integer updateUser) {
      this.updateUser = updateUser;
   }
}
