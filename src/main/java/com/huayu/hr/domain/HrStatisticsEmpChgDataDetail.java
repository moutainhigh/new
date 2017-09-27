package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-1-16 19:46:02.
*/
@Table(name = "hr_statistics_emp_chg_data_detail")
public class HrStatisticsEmpChgDataDetail extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -4293954795352569143L;
    //private String updateFiledKey = id,empId,dutyLevel,statisticsType,companyId,department,formatMonth,createtime;
 //private String updateFiledValue = id=:id,empId=:empId,dutyLevel=:dutyLevel,statisticsType=:statisticsType,companyId=:companyId,department=:department,formatMonth=:formatMonth,createtime=:createtime;
    private Integer id;
    private Integer statisticsId;
    private Integer empId;
    private Integer businessId;
    private Integer dutyLevel;
    private Integer statisticsType;
    private Integer company;
    private Integer department;
    private String year;
    private String month;
    private Integer status;
    private Date createtime;

    public HrStatisticsEmpChgDataDetail() {
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

    public Integer getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(Integer statisticsId) {
        this.statisticsId = statisticsId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public Integer getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(Integer statisticsType) {
        this.statisticsType = statisticsType;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
