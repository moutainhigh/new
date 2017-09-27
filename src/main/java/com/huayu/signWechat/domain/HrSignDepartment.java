package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-7-13 11:11:13.
*/
@Table(name = "hr_sign_department")
public class HrSignDepartment extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,name,parentid,status,order;
 //private String updateFiledValue = id=:id,name=:name,parentid=:parentid,status=:status,order=:order;
    private Integer id;
    private String name;
    private Integer parentid;
    private Integer status;
    private String order;

    public HrSignDepartment() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrSignDepartment(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
