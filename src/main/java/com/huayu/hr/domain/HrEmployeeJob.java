package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DengPeng on 2016/11/21.
 */
@Table(name = "hr_employee_job")
public class HrEmployeeJob extends BaseDomain implements Serializable{

    @TableField
    private static final long serialVersionUID = -6876438020229034669L;
    private Integer id;
    private Integer empId;
    private String empNo;
    private Integer empGroup;
    private Integer company;
    private Integer department;
    private Integer workType;
    private Integer job;
    private Integer jobLevel;
    private Integer jobSequence;
    private Integer recruitmentSource;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private Integer dutyName;
    private Integer dutyLevel;
    private String workAddress;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date turnFormalDate;
    private Integer onGuard;
    private Integer isFormal;
    private Integer formalType;
    private Integer createUser;
    private Date createtime;
    private Integer updateUser;
    private Date updatetime;
    private Integer isDelete;
    private Integer deleteUser;
    private Date deletetime;

    @TableField
    private String empName;

    @TableField
    private String companyCode;


    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private String jobName;
    @TableField
    private Integer oldJob;


    public HrEmployeeJob(Integer empId) {
        this.empId = empId;
    }

    public HrEmployeeJob() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getOldJob() {
        return oldJob;
    }

    public void setOldJob(Integer oldJob) {
        this.oldJob = oldJob;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getEmpGroup() {
        return empGroup;
    }

    public void setEmpGroup(Integer empGroup) {
        this.empGroup = empGroup;
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

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
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

    public Integer getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(Integer jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public Date getTurnFormalDate() {
        return turnFormalDate;
    }

    public void setTurnFormalDate(Date turnFormalDate) {
        this.turnFormalDate = turnFormalDate;
    }

    public Integer getOnGuard() {
        return onGuard;
    }

    public void setOnGuard(Integer onGuard) {
        this.onGuard = onGuard;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getJobSequence() {
        return jobSequence;
    }

    public void setJobSequence(Integer jobSequence) {
        this.jobSequence = jobSequence;
    }

    public Integer getRecruitmentSource() {
        return recruitmentSource;
    }

    public void setRecruitmentSource(Integer recruitmentSource) {
        this.recruitmentSource = recruitmentSource;
    }

    public Integer getDutyName() {
        return dutyName;
    }

    public void setDutyName(Integer dutyName) {
        this.dutyName = dutyName;
    }

    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public Integer getIsFormal() {
        return isFormal;
    }

    public void setIsFormal(Integer isFormal) {
        this.isFormal = isFormal;
    }

    public Integer getFormalType() {
        return formalType;
    }

    public void setFormalType(Integer formalType) {
        this.formalType = formalType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
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
}
