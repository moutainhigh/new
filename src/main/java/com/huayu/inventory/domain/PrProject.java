package com.huayu.inventory.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_project")
public class PrProject extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,name;
 //private String updateFiledValue = id=:id,name=:name;
    private Integer id;
    private String name;
    private String pcode;
    private Date createtime;
    private Integer createUser;


    public PrProject() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
