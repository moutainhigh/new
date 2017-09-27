package com.huayu.hr.web.args;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by DengPeng on 2017/3/28.
 */
public class HrStatisticsArgs {


    private Integer type;

    private Integer department;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer chgType;

    private Integer jobSequence;

    private Integer companyId;

    private boolean searchAll;

    private boolean searchNow;


    public Integer getChgType() {
        return chgType;
    }

    public void setChgType(Integer chgType) {
        this.chgType = chgType;
    }

    public boolean isSearchNow() {
        return searchNow;
    }

    public void setSearchNow(boolean searchNow) {
        this.searchNow = searchNow;
    }

    public boolean isSearchAll() {
        return searchAll;
    }

    public void setSearchAll(boolean searchAll) {
        this.searchAll = searchAll;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getJobSequence() {
        return jobSequence;
    }

    public void setJobSequence(Integer jobSequence) {
        this.jobSequence = jobSequence;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
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
}
