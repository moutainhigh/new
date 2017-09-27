package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.huayu.inventory.domain.validate.GroupUpdate;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-6 10:05:09.
*/
@Table(name = "pr_item_balance")
public class PrItemBalance extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,materialId,specificationId,sumPrice,sumTax,balanceDate,createtime,createtUser;
 //private String updateFiledValue = id=:id,materialId=:materialId,specificationId=:specificationId,sumPrice=:sumPrice,sumTax=:sumTax,balanceDate=:balanceDate,createtime=:createtime,createtUser=:createtUser;
 @NotNull(message = "Id不能为空", groups = {GroupUpdate.class})
    private Integer id;
    @NotNull(message = "材料Id不能为空", groups = {GroupInsert.class})
    private Integer materialId;
    @NotNull(message = "规格Id不能为空", groups = {GroupInsert.class})
    private Integer specificationId;
    @NotNull(message = "仓库Id不能为空", groups = {GroupInsert.class})
    private Integer repositoryId;
    @NotNull(message = "总金额不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal sumPrice;
    @NotNull(message = "税额不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal sumTax;
    @NotNull(message = "日期不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date balanceDate;
    private Date createtime;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;

    public PrItemBalance() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private String categoryName;
    @TableField
    private String materialName;
    @TableField
    private String specification;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getSumTax() {
        return sumTax;
    }

    public void setSumTax(BigDecimal sumTax) {
        this.sumTax = sumTax;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

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
}
