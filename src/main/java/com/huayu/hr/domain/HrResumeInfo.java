package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2016-12-19 13:37:08.
*/
@Table(name = "hr_resume_info")
public class HrResumeInfo extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -98655547517210829L;
    //private String updateFiledKey = id,empId,startTime,endTime,company,duty,phone,witness,witnessPhone,leaveReason,status,createtime,updatetime,deletetime;
 //private String updateFiledValue = id=:id,empId=:empId,startTime=:startTime,endTime=:endTime,company=:company,duty=:duty,phone=:phone,witness=:witness,witnessPhone=:witnessPhone,leaveReason=:leaveReason,status=:status,createtime=:createtime,updatetime=:updatetime,deletetime=:deletetime;
    private Integer id;
    private Integer empId;
    private Date startTime;
    private Date endTime;
    private String company;
    private String duty;
    private String phone;
    private String witness;
    private String witnessPhone;
    private String leaveReason;
    private Integer status;
    private Date createtime;
    private Date updatetime;
    private Date deletetime;

    public HrResumeInfo() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }


    public HrResumeInfo(Integer empId) {
        this.empId = empId;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public String getWitnessPhone() {
        return witnessPhone;
    }

    public void setWitnessPhone(String witnessPhone) {
        this.witnessPhone = witnessPhone;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }
}
