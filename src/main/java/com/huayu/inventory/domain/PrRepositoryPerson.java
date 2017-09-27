package com.huayu.inventory.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_repository_person")
public class PrRepositoryPerson extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -5108979089000067330L;
    //private String updateFiledKey = id,repositoryId,name,type;
 //private String updateFiledValue = id=:id,repositoryId=:repositoryId,name=:name,type=:type;
    private Integer id;
    private Integer projectId;
    private String name;
    private Integer type;

    public PrRepositoryPerson() {
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
