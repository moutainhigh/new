package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.Short;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 营销费用项目（科目）
* Created by DengPeng on 2017-6-15 14:13:07.
*/
@Table(name = "market_cost_item")
public class MarketCostItem extends BaseDomain  implements Serializable{

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
    private String firstCode;
    @TableField
    private String secondCode;
    @TableField
    private String firstName;
    @TableField
    private String secondName;

    public MarketCostItem() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
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
}
