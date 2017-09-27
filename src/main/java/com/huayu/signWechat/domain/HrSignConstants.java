package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-8-17 9:57:31.
*/
@Table(name = "hr_sign_constants")
public class HrSignConstants extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,name,key,value,status;
 //private String updateFiledValue = id=:id,name=:name,key=:key,value=:value,status=:status;
    private Integer id;
    private String name;
    private String key;
    private String value;
    private Integer status;

    public HrSignConstants() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
