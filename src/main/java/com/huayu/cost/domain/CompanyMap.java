package com.huayu.cost.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
 * 组织架构 映射
* Created by DengPeng on 2017-7-7 9:33:42.
*/
@Table(name = "company_map")
public class CompanyMap extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,type,parentId,name,hrCompany;
 //private String updateFiledValue = id=:id,type=:type,parentId=:parentId,name=:name,hrCompany=:hrCompany;
    private String id;
    private Integer type;
    private String parentId;
    private String name;
    private Integer hrCompany;

    public CompanyMap() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHrCompany() {
        return hrCompany;
    }

    public void setHrCompany(Integer hrCompany) {
        this.hrCompany = hrCompany;
    }
}
