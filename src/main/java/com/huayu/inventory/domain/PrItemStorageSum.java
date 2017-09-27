package com.huayu.inventory.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by DengPeng on 2017-6-19 16:06:07.
 */
@Table(name = "pr_item_storage_sum")
public class PrItemStorageSum extends BaseDomain  implements Serializable{

    //private String updateFiledKey = id,materialId,specificationId,repositoryId,balanceNum,balanceSumPrice;
    //private String updateFiledValue = id=:id,materialId=:materialId,specificationId=:specificationId,repositoryId=:repositoryId,balanceNum=:balanceNum,balanceSumPrice=:balanceSumPrice;
    private Integer id;
    private Integer materialId;
    private Integer specificationId;
    private Integer repositoryId;
    private BigDecimal num;
    private BigDecimal sumPrice;
    private BigDecimal excTaxSumPrice;
    private BigDecimal supplySum;

    public PrItemStorageSum() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public PrItemStorageSum(Integer materialId, Integer specificationId, Integer repositoryId) {
        this.materialId = materialId;
        this.specificationId = specificationId;
        this.repositoryId = repositoryId;
    }

    public BigDecimal getExcTaxSumPrice() {
        return excTaxSumPrice;
    }

    public void setExcTaxSumPrice(BigDecimal excTaxSumPrice) {
        this.excTaxSumPrice = excTaxSumPrice;
    }

    @TableField
    private BigDecimal newSum;
    @TableField
    private BigDecimal newSumPrice;

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

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getNewSum() {
        return newSum;
    }

    public void setNewSum(BigDecimal newSum) {
        this.newSum = newSum;
    }

    public BigDecimal getNewSumPrice() {
        return newSumPrice;
    }

    public void setNewSumPrice(BigDecimal newSumPrice) {
        this.newSumPrice = newSumPrice;
    }

    public BigDecimal getSupplySum() {
        return supplySum;
    }

    public void setSupplySum(BigDecimal supplySum) {
        this.supplySum = supplySum;
    }
}