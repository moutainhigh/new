package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2016-12-14 10:51:10.
*/
@Table(name = "hr_home_relation")
public class HrHomeRelation extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -9189043941413221068L;
    //private String updateFiledKey = id,empId,name,relationship,bithday,duty,job,phone;
 //private String updateFiledValue = id=:id,empId=:empId,name=:name,relationship=:relationship,bithday=:bithday,duty=:duty,job=:job,phone=:phone;
    private Integer id;
    private Integer empId;
    private String name;
    private String relationship;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bithday;
    private String duty;
    private String job;
    private String phone;
    private Integer status;
    private Date createtime;
    private Date updatetime;
    private Date deletetime;

    public HrHomeRelation() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrHomeRelation(Integer empId) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Date getBithday() {
        return bithday;
    }

    public void setBithday(Date bithday) {
        this.bithday = bithday;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
