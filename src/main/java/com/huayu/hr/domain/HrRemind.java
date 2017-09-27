package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-3-22 16:38:48.
*/
@Table(name = "hr_remind")
public class HrRemind extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 3814074762617299697L;
    //private String updateFiledKey = id,empId,company,year,month,type,date,status,createtime;
 //private String updateFiledValue = id=:id,empId=:empId,company=:company,year=:year,month=:month,type=:type,date=:date,status=:status,createtime=:createtime;
    private Long id;
    private Integer empId;
    private Integer company;
    private Integer year;
    private Integer month;
    private Integer type;
    private Date date;
    private Integer status;
    private Date createtime;

    public HrRemind() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private String empName;
    @TableField
    private Integer contractCompany;
    @TableField
    private String managerGroup;
    @TableField
    private String empNo;
    @TableField
    private Integer age;
    @TableField
    private Date joinCompDate;
    @TableField
    private Date turnFormalDate;
    @TableField
    private Integer sex;
    @TableField
    private Integer isFormal;
    @TableField
    private String jobName;
    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private String idCard;
    @TableField
    private Integer dutyLevel;
    @TableField
    private String periodCount;
    @TableField
    private Date contractStartTime;
    @TableField
    private Date contractEndTime;


    public String getIsFormalStr() {
        return isFormal==null?"":isFormal==1?"转正":"试用";
    }


    public String getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(String periodCount) {
        this.periodCount = periodCount;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public Integer getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(Integer contractCompany) {
        this.contractCompany = contractCompany;
    }

    public String getManagerGroup() {
        return managerGroup;
    }

    public void setManagerGroup(String managerGroup) {
        this.managerGroup = managerGroup;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getJoinCompDate() {
        return joinCompDate;
    }

    public void setJoinCompDate(Date joinCompDate) {
        this.joinCompDate = joinCompDate;
    }

    public Date getTurnFormalDate() {
        return turnFormalDate;
    }

    public void setTurnFormalDate(Date turnFormalDate) {
        this.turnFormalDate = turnFormalDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsFormal() {
        return isFormal;
    }

    public void setIsFormal(Integer isFormal) {
        this.isFormal = isFormal;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
