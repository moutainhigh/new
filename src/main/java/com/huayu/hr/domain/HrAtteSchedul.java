package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* Created by DengPeng on 2017-4-5 14:07:22.
*/
@Table(name = "hr_atte_schedul")
public class HrAtteSchedul extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -2393864730733364360L;
    //private String updateFiledKey = id,userId,shiftId,startDate,endDate,units,cyle,status,createTime,createUser,updateTime,updateUser,deleteTime,deleteUser;
 //private String updateFiledValue = id=:id,userId=:userId,shiftId=:shiftId,startDate=:startDate,endDate=:endDate,units=:units,cyle=:cyle,status=:status,createTime=:createTime,createUser=:createUser,updateTime=:updateTime,updateUser=:updateUser,deleteTime=:deleteTime,deleteUser=:deleteUser;
    private Integer id;
    private Integer empId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Integer forced;
    private Integer cycle;
    private Integer status;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    private Date deleteTime;
    private Integer deleteUser;

    public HrAtteSchedul() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrAtteSchedul(Integer empId) {
        this.empId = empId;
    }

    @TableField
    private String empName;
    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private List<HrAtteSchedulDetail> details;

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<HrAtteSchedulDetail> getDetails() {
        return details;
    }

    public void setDetails(List<HrAtteSchedulDetail> details) {
        this.details = details;
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

    public Integer getForced() {
        return forced;
    }

    public void setForced(Integer forced) {
        this.forced = forced;
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

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
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
