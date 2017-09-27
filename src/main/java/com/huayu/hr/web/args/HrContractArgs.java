package com.huayu.hr.web.args;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by DengPeng on 2017/3/13.
 */
public class HrContractArgs {

    private Integer operType;

    private Integer empId;

    private String empNo;

    private String empName;

    private Integer department;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime2;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private Date endTime1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime2;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Date getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(Date startTime1) {
        this.startTime1 = startTime1;
    }

    public Date getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(Date startTime2) {
        this.startTime2 = startTime2;
    }

    public Date getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(Date endTime1) {
        this.endTime1 = endTime1;
    }

    public Date getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(Date endTime2) {
        this.endTime2 = endTime2;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
}
