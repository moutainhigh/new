package com.huayu.hr.web.args;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 人员调动 参数
 * Created by DengPeng on 2017/2/23.
 */
public class HrChgArgs {

    private Integer empId;
    private Integer company;
    private String companyName;
    private Integer department;
    private Integer job;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date chgTime;
    private Integer chgType;
    private Integer chgStatus;
    private Integer chgReason;
    private Integer dutyLevel;
    private Integer jobSequence;
    private String description;
    private Integer empGroup;
    private Integer lastEmpJobId;

    private String empIds;
    private String empObjArrStr;


    public String getEmpObjArrStr() {
        return empObjArrStr;
    }

    public void setEmpObjArrStr(String empObjArrStr) {
        this.empObjArrStr = empObjArrStr;
    }

    public String getEmpIds() {
        return empIds;
    }

    public void setEmpIds(String empIds) {
        this.empIds = empIds;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
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

    public Date getChgTime() {
        return chgTime;
    }

    public void setChgTime(Date chgTime) {
        this.chgTime = chgTime;
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

    public Integer getChgReason() {
        return chgReason;
    }

    public void setChgReason(Integer chgReason) {
        this.chgReason = chgReason;
    }

    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public Integer getJobSequence() {
        return jobSequence;
    }

    public void setJobSequence(Integer jobSequence) {
        this.jobSequence = jobSequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmpGroup() {
        return empGroup;
    }

    public void setEmpGroup(Integer empGroup) {
        this.empGroup = empGroup;
    }

    public Integer getLastEmpJobId() {
        return lastEmpJobId;
    }

    public void setLastEmpJobId(Integer lastEmpJobId) {
        this.lastEmpJobId = lastEmpJobId;
    }
}
