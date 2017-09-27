package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-12 14:40:16.
*/
@Table(name = "hr_atte_overtime")
public class HrAtteOvertime extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 4237686787314347476L;
   //private String updateFiledKey = id,empId,empName,company,companyName,department,departmentName,startDate,endDate,type,duration,billDate,reason,status,createTime,createUser,updateTime,updateUser,deleteTime,deleteUser;
 //private String updateFiledValue = id=:id,empId=:empId,empName=:empName,company=:company,companyName=:companyName,department=:department,departmentName=:departmentName,startDate=:startDate,endDate=:endDate,type=:type,duration=:duration,billDate=:billDate,reason=:reason,status=:status,createTime=:createTime,createUser=:createUser,updateTime=:updateTime,updateUser=:updateUser,deleteTime=:deleteTime,deleteUser=:deleteUser;
    private Integer id;
    private Integer empId;
    private String empName;
    private Integer company;
    private String companyName;
    private Integer department;
    private String departmentName;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date startDate;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date endDate;
    private Integer type;
    private Integer duration;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date billDate;
    private String reason;
    private Integer status;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    private Date deleteTime;
    private Integer deleteUser;

    public HrAtteOvertime() {
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

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public Integer getDepartment() {
      return department;
   }

   public void setDepartment(Integer department) {
      this.department = department;
   }

   public String getDepartmentName() {
      return departmentName;
   }

   public void setDepartmentName(String departmentName) {
      this.departmentName = departmentName;
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

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public Integer getDuration() {
      return duration;
   }

   public void setDuration(Integer duration) {
      this.duration = duration;
   }

   public Date getBillDate() {
      return billDate;
   }

   public void setBillDate(Date billDate) {
      this.billDate = billDate;
   }

   public String getReason() {
      return reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
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
