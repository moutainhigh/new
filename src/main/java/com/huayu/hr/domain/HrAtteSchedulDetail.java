package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-4-6 15:59:55.
*/
@Table(name = "hr_atte_schedul_detail")
public class HrAtteSchedulDetail extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 4325985374500766725L;
    //private String updateFiledKey = id,schedulId,shiftId,index,status,createTime,createUser,updateTime,updateUser,deleteTime,deleteUser;
 //private String updateFiledValue = id=:id,schedulId=:schedulId,shiftId=:shiftId,index=:index,status=:status,createTime=:createTime,createUser=:createUser,updateTime=:updateTime,updateUser=:updateUser,deleteTime=:deleteTime,deleteUser=:deleteUser;
    private Integer id;
    private Integer schedulId;
    private Integer shiftId;
    private Integer step;
    private Integer status;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    private Date deleteTime;
    private Integer deleteUser;

    @TableField
    private String empName;
    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private String shiftName;
    @TableField
    private Integer empId;
    @TableField
    private Date startDate;
    @TableField
    private Date endDate;
    @TableField
    private Integer forced;
    @TableField
    private Integer cycle;



    public HrAtteSchedulDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }



    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
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

    public Integer getForced() {
        return forced;
    }

    public void setForced(Integer forced) {
        this.forced = forced;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchedulId() {
        return schedulId;
    }

    public void setSchedulId(Integer schedulId) {
        this.schedulId = schedulId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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
