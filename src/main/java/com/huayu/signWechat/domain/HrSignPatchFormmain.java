package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-8-1 11:35:47.
*/
@Table(name = "hr_sign_patch_formmain")
public class HrSignPatchFormmain extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainId,name,company,department,position,telephone,status,successDate;
 //private String updateFiledValue = id=:id,formmainId=:formmainId,name=:name,company=:company,department=:department,position=:position,telephone=:telephone,status=:status,successDate=:successDate;
    private Integer id;
    private String formmainId;
    private String name;
    private String company;
    private String department;
    private String position;
    private String telephone;
    private Integer status;
    private Date successDate;

    public HrSignPatchFormmain() {
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

    public String getFormmainId() {
        return formmainId;
    }

    public void setFormmainId(String formmainId) {
        this.formmainId = formmainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }
}
