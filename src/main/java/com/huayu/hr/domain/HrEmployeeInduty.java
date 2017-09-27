package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-1-16 17:54:52.
*/
@Table(name = "hr_employee_induty")
public class HrEmployeeInduty extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -5406290358046788712L;
   //private String updateFiledKey = id,begindate,choldreason,deposereason,deposetype,enddate,jobtype,memo,company,department,dutyLevel,jobLevel,jobSequence,dutyName,job,empId,empGroup,empJobId,onGuard,posttype,preparenote,preparereason,preparetype,createtime;
 //private String updateFiledValue = id=:id,begindate=:begindate,choldreason=:choldreason,deposereason=:deposereason,deposetype=:deposetype,enddate=:enddate,jobtype=:jobtype,memo=:memo,company=:company,department=:department,dutyLevel=:dutyLevel,jobLevel=:jobLevel,jobSequence=:jobSequence,dutyName=:dutyName,job=:job,empId=:empId,empGroup=:empGroup,empJobId=:empJobId,onGuard=:onGuard,posttype=:posttype,preparenote=:preparenote,preparereason=:preparereason,preparetype=:preparetype,createtime=:createtime;
    private Integer id;
    private Date begindate;
    private Date enddate;
    private String choldreason;
    private String deposereason;
    private String deposetype;
    private Integer jobtype;
    private String memo;
    private Integer company;
    private Integer department;
    private Integer dutyLevel;
    private Integer jobLevel;
    private Integer jobSequence;
    private Integer dutyName;
    private Integer job;
    private Integer empId;
    private Integer empGroup;
    private Integer empJobId;
    private Integer onGuard;
    private Integer posttype;
    private String chgNote;
    private Integer chgCause;
    private Integer chgType;
    private Integer chgStatus;
    private Date createtime;
    private Date updatetime;
   private Integer isDelete;
   private Integer deleteUser;
   private Date deletetime;

    public HrEmployeeInduty() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public HrEmployeeInduty(Integer empId, Integer empJobId) {
      this.empId = empId;
      this.empJobId = empJobId;
   }

   @TableField
   private String companyName;
   @TableField
   private String departmentName;
   @TableField
   private String jobName;

   public Integer getIsDelete() {
      return isDelete;
   }

   public void setIsDelete(Integer isDelete) {
      this.isDelete = isDelete;
   }

   public Integer getDeleteUser() {
      return deleteUser;
   }

   public void setDeleteUser(Integer deleteUser) {
      this.deleteUser = deleteUser;
   }

   public Date getDeletetime() {
      return deletetime;
   }

   public void setDeletetime(Date deletetime) {
      this.deletetime = deletetime;
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

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getBegindate() {
      return begindate;
   }

   public void setBegindate(Date begindate) {
      this.begindate = begindate;
   }

   public String getCholdreason() {
      return choldreason;
   }

   public void setCholdreason(String choldreason) {
      this.choldreason = choldreason;
   }

   public String getDeposereason() {
      return deposereason;
   }

   public void setDeposereason(String deposereason) {
      this.deposereason = deposereason;
   }

   public String getDeposetype() {
      return deposetype;
   }

   public void setDeposetype(String deposetype) {
      this.deposetype = deposetype;
   }

   public Date getEnddate() {
      return enddate;
   }

   public void setEnddate(Date enddate) {
      this.enddate = enddate;
   }

   public Integer getJobtype() {
      return jobtype;
   }

   public void setJobtype(Integer jobtype) {
      this.jobtype = jobtype;
   }

   public String getMemo() {
      return memo;
   }

   public void setMemo(String memo) {
      this.memo = memo;
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

   public Integer getDutyLevel() {
      return dutyLevel;
   }

   public void setDutyLevel(Integer dutyLevel) {
      this.dutyLevel = dutyLevel;
   }

   public Integer getJobLevel() {
      return jobLevel;
   }

   public void setJobLevel(Integer jobLevel) {
      this.jobLevel = jobLevel;
   }

   public Integer getJobSequence() {
      return jobSequence;
   }

   public void setJobSequence(Integer jobSequence) {
      this.jobSequence = jobSequence;
   }

   public Integer getDutyName() {
      return dutyName;
   }

   public void setDutyName(Integer dutyName) {
      this.dutyName = dutyName;
   }

   public Integer getJob() {
      return job;
   }

   public void setJob(Integer job) {
      this.job = job;
   }

   public Integer getEmpId() {
      return empId;
   }

   public void setEmpId(Integer empId) {
      this.empId = empId;
   }

   public Integer getEmpGroup() {
      return empGroup;
   }

   public void setEmpGroup(Integer empGroup) {
      this.empGroup = empGroup;
   }

   public Integer getEmpJobId() {
      return empJobId;
   }

   public void setEmpJobId(Integer empJobId) {
      this.empJobId = empJobId;
   }

   public Integer getOnGuard() {
      return onGuard;
   }

   public void setOnGuard(Integer onGuard) {
      this.onGuard = onGuard;
   }

   public Integer getPosttype() {
      return posttype;
   }

   public void setPosttype(Integer posttype) {
      this.posttype = posttype;
   }

   public String getChgNote() {
      return chgNote;
   }

   public void setChgNote(String chgNote) {
      this.chgNote = chgNote;
   }

   public Integer getChgCause() {
      return chgCause;
   }

   public void setChgCause(Integer chgCause) {
      this.chgCause = chgCause;
   }

   public Integer getChgType() {
      return chgType;
   }

   public void setChgType(Integer chgType) {
      this.chgType = chgType;
   }

   public Integer getChgStatus() {
      return chgStatus;
   }

   public void setChgStatus(Integer chgStatus) {
      this.chgStatus = chgStatus;
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
}
