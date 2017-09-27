package com.huayu.inventory.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-13 15:46:25.
*/
@Table(name = "pr_material_specification")
public class PrMaterialSpecification extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 5340068759077422333L;
    //private String updateFiledKey = id,matId,specification;
 //private String updateFiledValue = id=:id,matId=:matId,specification=:specification;
    private Integer id;
    private Integer matId;
    private String specification;
    private BigDecimal budget;
    private Integer status;
    private Date deletetime;
    private Integer deleteUser;

    public PrMaterialSpecification() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public PrMaterialSpecification(Integer id, Integer matId) {
        this.id = id;
        this.matId = matId;
    }

    @TableField
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatId() {
        return matId;
    }

    public void setMatId(Integer matId) {
        this.matId = matId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
