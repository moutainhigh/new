package com.huayu.cost.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.Short;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 费用项目（科目）
* Created by DengPeng on 2017-7-7 9:35:55.
*/
@Table(name = "cost_item")
public class CostItem extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,code,name,parentId,level,sort,description,remark,createDate;
 //private String updateFiledValue = id=:id,code=:code,name=:name,parentId=:parentId,level=:level,sort=:sort,description=:description,remark=:remark,createDate=:createDate;
    private Integer id;
    private String code;
    private String name;
    private Integer parentId;
    private Short level;
    private Short sort;
    private String description;
    private String remark;
    private Date createDate;

    @TableField
    private String firstCode;//一级编码
    @TableField
    private String secondCode;//二级编码
    @TableField
    private Long companyId;//公司ID
    @TableField
    private Long departmentId;//部门ID
    @TableField
    private Integer year;//年度

    public CostItem() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
