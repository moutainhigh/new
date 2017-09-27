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
import java.util.List;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_material_category")
public class PrMaterialCategory extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -5214465406485199384L;
    //private String updateFiledKey = id,parentId,isParent,name,budget,status,code,createtime,createUser,deletetime,deleteUser;
 //private String updateFiledValue = id=:id,parentId=:parentId,isParent=:isParent,name=:name,budget=:budget,status=:status,code=:code,createtime=:createtime,createUser=:createUser,deletetime=:deletetime,deleteUser=:deleteUser;
    @NotNull(message = "Id不能为空", groups = {GroupDelete.class, GroupUpdate.class})
    private Integer id;
    @NotNull(message = "父级Id不能为空", groups = {GroupInsert.class,GroupDelete.class})
    private Integer parentId;
    private Integer isParent;
    @NotEmpty(message = "分类名称不能为空", groups = {GroupInsert.class,GroupUpdate.class})
    private String name;
    private Integer status;
//    @NotEmpty(message = "材料编号不能为空", groups = {GroupInsert.class})
    private String code;
    private Date createtime;
    private Integer createUser;
    private Date deletetime;
    private Integer deleteUser;

    public PrMaterialCategory() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public PrMaterialCategory(Integer id) {
        this.id = id;
    }

    @TableField
    private boolean contain;

    @TableField
    private List<PrMaterialCategory> list;

    public boolean isContain() {
        return contain;
    }

    public void setContain(boolean contain) {
        this.contain = contain;
    }

    public List<PrMaterialCategory> getList() {
        return list;
    }

    public void setList(List<PrMaterialCategory> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }
}
