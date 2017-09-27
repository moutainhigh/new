package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* 岗位编制
* Created by DP on 2017-8-21 17:49:01.
*/
@Table(name = "hr_job_plait")
public class HrJobPlait extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,year,month,company,department,job,note,manageLine,dutyLevel,empId,createtime,createUser,updatetime,updateUser;
 //private String updateFiledKeyValue = :id,:year,:month,:company,:department,:job,:note,:manageLine,:dutyLevel,:empId,:createtime,:createUser,:updatetime,:updateUser;
 //private String updateFiledValue = id=:id,year=:year,month=:month,company=:company,department=:department,job=:job,note=:note,manageLine=:manageLine,dutyLevel=:dutyLevel,empId=:empId,createtime=:createtime,createUser=:createUser,updatetime=:updatetime,updateUser=:updateUser;
    private Integer id;//id
    private Integer year;//年份
    private Integer month;//月份
    private Integer companyId;//公司
    private Integer departmentId;//部门
    private Integer jobId;//岗位
    private String note;//note
    private String manageLine;//管线
    private String dutyLevel;//dutyLevel
    private Integer empId;//empId
    private Integer status;
    private Date createtime;//createtime
    private Integer createUser;//createUser
    private Date updatetime;//updatetime
    private Integer updateUser;//updateUser
    private Date deletetime;
    private Integer deleteUser;


    @TableField
    private Integer startMonth;
    @TableField
    private Integer endMonth;
    @TableField
    private Integer count;
    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private String jobName;
    @TableField
    private String empName;
    @TableField
    private String idCard;



    public HrJobPlait() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setId(Integer id){//id
        this.id = id;
    }
    public Integer getId(){//id
        return this.id;
    }

    public void setYear(Integer year){//年份
        this.year = year;
    }
    public Integer getYear(){//年份
        return this.year;
    }

    public void setMonth(Integer month){//月份
        this.month = month;
    }
    public Integer getMonth(){//月份
        return this.month;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setNote(String note){//note
        this.note = note;
    }
    public String getNote(){//note
        return this.note;
    }

    public void setManageLine(String manageLine){//管线
        this.manageLine = manageLine;
    }
    public String getManageLine(){//管线
        return this.manageLine;
    }

    public void setDutyLevel(String dutyLevel){//dutyLevel
        this.dutyLevel = dutyLevel;
    }
    public String getDutyLevel(){//dutyLevel
        return this.dutyLevel;
    }

    public void setEmpId(Integer empId){//empId
        this.empId = empId;
    }
    public Integer getEmpId(){//empId
        return this.empId;
    }

    public void setCreatetime(Date createtime){//createtime
        this.createtime = createtime;
    }
    public Date getCreatetime(){//createtime
        return this.createtime;
    }

    public void setCreateUser(Integer createUser){//createUser
        this.createUser = createUser;
    }
    public Integer getCreateUser(){//createUser
        return this.createUser;
    }

    public void setUpdatetime(Date updatetime){//updatetime
        this.updatetime = updatetime;
    }
    public Date getUpdatetime(){//updatetime
        return this.updatetime;
    }

    public void setUpdateUser(Integer updateUser){//updateUser
        this.updateUser = updateUser;
    }
    public Integer getUpdateUser(){//updateUser
        return this.updateUser;
    }


}
