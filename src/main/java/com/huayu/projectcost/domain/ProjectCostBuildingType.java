package com.huayu.projectcost.domain;

import com.huayu.projectcost.domain.validate.TypeGroupDelete;
import com.huayu.projectcost.domain.validate.TypeGroupInsert;
import com.huayu.projectcost.domain.validate.TypeGroupUpdate;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-9-14 16:22:51.
*/
@Table(name = "project_cost_buildingType")
public class ProjectCostBuildingType extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,name,status,createTime,createUser;
 //private String updateFiledValue = id=:id,name=:name,status=:status,createTime=:createTime,createUser=:createUser;
    @NotNull(message = "业态id不能为空", groups = {TypeGroupDelete.class, TypeGroupUpdate.class})
    private Integer id;
    @NotEmpty(message = "业态名称不能为空", groups = {TypeGroupInsert.class, TypeGroupUpdate.class})
    private String name;
    private Integer status;
    private Date createTime;
    private Integer createUser;

    public ProjectCostBuildingType() {
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
}
