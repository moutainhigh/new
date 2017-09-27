package com.huayu.inventory.domain;

import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.huayu.inventory.domain.validate.GroupUpdate;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_repository")
public class PrRepository extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -6606605283615910134L;
    //private String updateFiledKey = id,projectId,name,address,remark;
 //private String updateFiledValue = id=:id,projectId=:projectId,name=:name,address=:address,remark=:remark;
    @NotNull(message = "Id不能为空", groups = {GroupUpdate.class,GroupDelete.class})
    private Integer id;
    @NotNull(message = "项目名称不能为空", groups = {GroupUpdate.class})
    private Integer projectId;
    @NotEmpty(message = "仓库名称不能为空", groups = {GroupInsert.class,GroupUpdate.class})
    private String name;
    private String rcode;
    @NotEmpty(message = "仓库地址不能为空", groups = {GroupInsert.class,GroupUpdate.class})
    private String address;
    private String remark;
    private Date createtime;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;
    private Integer status;


    public PrRepository() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private String projectName;


    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
