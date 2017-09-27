package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-1-16 16:56:54.
*/
@Table(name = "hr_employee_outduty")
public class HrEmployeeOutduty extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -3220926846496794020L;
   //private String updateFiledKey = id,content,description,endTime,company,companyAfter,empId,empJobId,deptAfter,deptBefore,dutyBefore,jobBefore,onGuard,empGroupAfter,empGroupBefore,reason,salarystopdate,towhere,createtime,type,welfarestopdate;
 //private String updateFiledValue = id=:id,content=:content,description=:description,endTime=:endTime,company=:company,companyAfter=:companyAfter,empId=:empId,empJobId=:empJobId,deptAfter=:deptAfter,deptBefore=:deptBefore,dutyBefore=:dutyBefore,jobBefore=:jobBefore,onGuard=:onGuard,empGroupAfter=:empGroupAfter,empGroupBefore=:empGroupBefore,reason=:reason,salarystopdate=:salarystopdate,towhere=:towhere,createtime=:createtime,type=:type,welfarestopdate=:welfarestopdate;
    private Integer id;
    private String content;
    private String description;
    private Date leavedate;
    private Integer companyBefore;
    private Integer companyAfter;
    private Integer empId;
    private Integer empJobId;
    private Integer deptAfter;
    private Integer deptBefore;
    private Integer dutyBefore;
    private Integer jobBefore;
    private Integer empGroupAfter;
    private Integer empGroupBefore;
    private Integer reason;
    private Date salarystopdate;
    private String towhere;
    private Date createtime;
    private Integer type;
    private Date welfarestopdate;
   private Integer isDelete;
   private Integer deleteUser;
   private Date deletetime;


    public HrEmployeeOutduty() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private String empName;
   @TableField
    private String companyBeforeName;
   @TableField
    private String companyAfterName;
    @TableField
    private String deptBeforeName;
    @TableField
    private String deptAfterName;
    @TableField
    private String jobBeforeName;


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

   public String getEmpName() {
      return empName;
   }

   public void setEmpName(String empName) {
      this.empName = empName;
   }

   public String getCompanyBeforeName() {
      return companyBeforeName;
   }

   public void setCompanyBeforeName(String companyBeforeName) {
      this.companyBeforeName = companyBeforeName;
   }

   public String getCompanyAfterName() {
      return companyAfterName;
   }

   public void setCompanyAfterName(String companyAfterName) {
      this.companyAfterName = companyAfterName;
   }

   public String getDeptBeforeName() {
      return deptBeforeName;
   }

   public void setDeptBeforeName(String deptBeforeName) {
      this.deptBeforeName = deptBeforeName;
   }

   public String getDeptAfterName() {
      return deptAfterName;
   }

   public void setDeptAfterName(String deptAfterName) {
      this.deptAfterName = deptAfterName;
   }

   public String getJobBeforeName() {
      return jobBeforeName;
   }

   public void setJobBeforeName(String jobBeforeName) {
      this.jobBeforeName = jobBeforeName;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Date getLeavedate() {
      return leavedate;
   }

   public void setLeavedate(Date leavedate) {
      this.leavedate = leavedate;
   }

   public Integer getCompanyBefore() {
      return companyBefore;
   }

   public void setCompanyBefore(Integer companyBefore) {
      this.companyBefore = companyBefore;
   }

   public Integer getCompanyAfter() {
      return companyAfter;
   }

   public void setCompanyAfter(Integer companyAfter) {
      this.companyAfter = companyAfter;
   }

   public Integer getEmpId() {
      return empId;
   }

   public void setEmpId(Integer empId) {
      this.empId = empId;
   }

   public Integer getEmpJobId() {
      return empJobId;
   }

   public void setEmpJobId(Integer empJobId) {
      this.empJobId = empJobId;
   }

   public Integer getDeptAfter() {
      return deptAfter;
   }

   public void setDeptAfter(Integer deptAfter) {
      this.deptAfter = deptAfter;
   }

   public Integer getDeptBefore() {
      return deptBefore;
   }

   public void setDeptBefore(Integer deptBefore) {
      this.deptBefore = deptBefore;
   }

   public Integer getDutyBefore() {
      return dutyBefore;
   }

   public void setDutyBefore(Integer dutyBefore) {
      this.dutyBefore = dutyBefore;
   }

   public Integer getJobBefore() {
      return jobBefore;
   }

   public void setJobBefore(Integer jobBefore) {
      this.jobBefore = jobBefore;
   }

   public Integer getEmpGroupAfter() {
      return empGroupAfter;
   }

   public void setEmpGroupAfter(Integer empGroupAfter) {
      this.empGroupAfter = empGroupAfter;
   }

   public Integer getEmpGroupBefore() {
      return empGroupBefore;
   }

   public void setEmpGroupBefore(Integer empGroupBefore) {
      this.empGroupBefore = empGroupBefore;
   }

   public Integer getReason() {
      return reason;
   }

   public void setReason(Integer reason) {
      this.reason = reason;
   }

   public Date getSalarystopdate() {
      return salarystopdate;
   }

   public void setSalarystopdate(Date salarystopdate) {
      this.salarystopdate = salarystopdate;
   }

   public String getTowhere() {
      return towhere;
   }

   public void setTowhere(String towhere) {
      this.towhere = towhere;
   }

   public Date getCreatetime() {
      return createtime;
   }

   public void setCreatetime(Date createtime) {
      this.createtime = createtime;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public Date getWelfarestopdate() {
      return welfarestopdate;
   }

   public void setWelfarestopdate(Date welfarestopdate) {
      this.welfarestopdate = welfarestopdate;
   }
}
