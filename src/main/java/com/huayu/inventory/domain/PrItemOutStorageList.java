package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupBatchUpdate;
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
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_item_out_storage_list")
public class PrItemOutStorageList extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 7127759723245631766L;
    @NotNull(message = "出库Id不能为空", groups = {GroupUpdate.class})
    private Integer id;
    @NotNull(message = "出库主Id不能为空", groups = {GroupUpdate.class, GroupBatchUpdate.class})
    private Integer outStorageId;
    private Integer deliveryId;
    private Integer materialId;
    private Integer specificationId;
    private BigDecimal num;
    private BigDecimal price;
    private BigDecimal sumPrice;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private BigDecimal excTaxPrice;
    private BigDecimal excTaxSumPrice;
    private BigDecimal balanceNum;
    private BigDecimal balancePrice;
    private BigDecimal balanceSumPrice;
    private BigDecimal balanceExcTaxPrice;
    private BigDecimal balanceExcTaxSumPrice;
    private String remark;
    private String use;
    private Date updatetime;
    private Integer updateUser;

    @TableField
    private String materialName;
    @TableField
    private String specification;
    @TableField
    private String alias;
    @TableField
    private String unit;
    @TableField
    private String categoryName;
    @TableField
    private Integer repositoryId;
    @TableField
    private String detailArray;
    @TableField
    private String projectName;
    @TableField
    private String repositoryName;
    @TableField
    private String outStorageNo;
    @TableField
    private String projectHouseNum;
    @TableField
    private String itemReceiver;
    @TableField
    private String itemReceiverUnit;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    private Date outStorageDate;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @TableField
    private String mcCode;
    @TableField
    private String itemProviderName;
    @TableField
    private String billReceiver;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date billReceiveDate;


    public String getBillReceiver() {
        return billReceiver;
    }

    public void setBillReceiver(String billReceiver) {
        this.billReceiver = billReceiver;
    }

    public Date getBillReceiveDate() {
        return billReceiveDate;
    }

    public void setBillReceiveDate(Date billReceiveDate) {
        this.billReceiveDate = billReceiveDate;
    }

    public String getItemProviderName() {
        return itemProviderName;
    }

    public void setItemProviderName(String itemProviderName) {
        this.itemProviderName = itemProviderName;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOutStorageNo() {
        return outStorageNo;
    }

    public void setOutStorageNo(String outStorageNo) {
        this.outStorageNo = outStorageNo;
    }

    public String getProjectHouseNum() {
        return projectHouseNum;
    }

    public void setProjectHouseNum(String projectHouseNum) {
        this.projectHouseNum = projectHouseNum;
    }

    public String getItemReceiver() {
        return itemReceiver;
    }

    public void setItemReceiver(String itemReceiver) {
        this.itemReceiver = itemReceiver;
    }

    public String getItemReceiverUnit() {
        return itemReceiverUnit;
    }

    public void setItemReceiverUnit(String itemReceiverUnit) {
        this.itemReceiverUnit = itemReceiverUnit;
    }

    public Date getOutStorageDate() {
        return outStorageDate;
    }

    public void setOutStorageDate(Date outStorageDate) {
        this.outStorageDate = outStorageDate;
    }

    public PrItemOutStorageList() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getDetailArray() {
        return detailArray;
    }

    public void setDetailArray(String detailArray) {
        this.detailArray = detailArray;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
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

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getBalanceExcTaxPrice() {
        return balanceExcTaxPrice;
    }

    public void setBalanceExcTaxPrice(BigDecimal balanceExcTaxPrice) {
        this.balanceExcTaxPrice = balanceExcTaxPrice;
    }

    public BigDecimal getBalanceExcTaxSumPrice() {
        return balanceExcTaxSumPrice;
    }

    public void setBalanceExcTaxSumPrice(BigDecimal balanceExcTaxSumPrice) {
        this.balanceExcTaxSumPrice = balanceExcTaxSumPrice;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOutStorageId() {
        return outStorageId;
    }

    public void setOutStorageId(Integer outStorageId) {
        this.outStorageId = outStorageId;
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

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getExcTaxPrice() {
        return excTaxPrice;
    }

    public void setExcTaxPrice(BigDecimal excTaxPrice) {
        this.excTaxPrice = excTaxPrice;
    }

    public BigDecimal getExcTaxSumPrice() {
        return excTaxSumPrice;
    }

    public void setExcTaxSumPrice(BigDecimal excTaxSumPrice) {
        this.excTaxSumPrice = excTaxSumPrice;
    }

    public BigDecimal getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(BigDecimal balanceNum) {
        this.balanceNum = balanceNum;
    }

    public BigDecimal getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(BigDecimal balancePrice) {
        this.balancePrice = balancePrice;
    }

    public BigDecimal getBalanceSumPrice() {
        return balanceSumPrice;
    }

    public void setBalanceSumPrice(BigDecimal balanceSumPrice) {
        this.balanceSumPrice = balanceSumPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
