package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-8-25 17:08:42.
*/
@Table(name = "hr_sign_egress_main")
public class HrSignEgressMain extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainid,company,department,position,username,telephone,notes,createDate,successDate,status;
 //private String updateFiledValue = id=:id,formmainid=:formmainid,company=:company,department=:department,position=:position,username=:username,telephone=:telephone,notes=:notes,createDate=:createDate,successDate=:successDate,status=:status;
    private Integer id;
    private String formmainid;
    private String company;
    private String department;
    private String position;
    private String username;
    private String telephone;
    private String notes;
    private Date createDate;
    private Date successDate;
    private Integer status;

    public HrSignEgressMain() {
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

    public String getFormmainid() {
        return formmainid;
    }

    public void setFormmainid(String formmainid) {
        this.formmainid = formmainid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
