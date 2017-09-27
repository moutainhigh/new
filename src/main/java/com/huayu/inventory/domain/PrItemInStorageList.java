package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-15 10:01:34.
*/
@Table(name = "pr_item_in_storage_list")
public class PrItemInStorageList extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,inStorageId,deliveryId,num,balanceNum,price,taxRate,priceSrc,remark;
 //private String updateFiledValue = id=:id,inStorageId=:inStorageId,deliveryId=:deliveryId,num=:num,balanceNum=:balanceNum,price=:price,taxRate=:taxRate,priceSrc=:priceSrc,remark=:remark;
 private Integer id;
    private Integer inStorageId;
    private Integer deliveryId;
    private BigDecimal num;
    @TableField
    private BigDecimal balanceNum;
    private BigDecimal price;
    private BigDecimal inStorageMoney;
    private BigDecimal taxRate;
    private BigDecimal priceNoRate;
    private BigDecimal rateMoney;
    private BigDecimal moneyNoRate;
    private String priceSrc;
    private String remark;
    @TableField
    private String materialName;
    @TableField
    private String specification;
    @TableField
    private String unit;
    @TableField
    private BigDecimal deliveryNum;
    @TableField
    private BigDecimal deliveryPrice;
    @TableField
    private BigDecimal deliverySumPrice;
    @TableField
    private BigDecimal deliveryTax;
    @TableField
    private BigDecimal deliveryExcTaxSumPrice;
    @TableField
    private BigDecimal deliveryTaxRate;
    @TableField
    private String alias;
    @TableField
    private String materialCategory;
    @TableField
    private String repositoryName;
    @TableField
    private String inStorageNum;
    @TableField
    private String billNum;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    private Date inStoreDate;
    @TableField
    private String itemProviderName;
    @TableField
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @TableField
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    @TableField
    private String inStoreUser;
    @TableField
    private Integer repositoryId;
    @TableField
    private String billReceiver;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date billReceiveDate;



    public PrItemInStorageList() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

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

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getInStoreUser() {
        return inStoreUser;
    }

    public void setInStoreUser(String inStoreUser) {
        this.inStoreUser = inStoreUser;
    }

    public String getInStorageNum() {
        return inStorageNum;
    }

    public void setInStorageNum(String inStorageNum) {
        this.inStorageNum = inStorageNum;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public Date getInStoreDate() {
        return inStoreDate;
    }

    public void setInStoreDate(Date inStoreDate) {
        this.inStoreDate = inStoreDate;
    }

    public String getItemProviderName() {
        return itemProviderName;
    }

    public void setItemProviderName(String itemProviderName) {
        this.itemProviderName = itemProviderName;
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

    public BigDecimal getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(BigDecimal deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getDeliverySumPrice() {
        return deliverySumPrice;
    }

    public void setDeliverySumPrice(BigDecimal deliverySumPrice) {
        this.deliverySumPrice = deliverySumPrice;
    }

    public BigDecimal getDeliveryTax() {
        return deliveryTax;
    }

    public void setDeliveryTax(BigDecimal deliveryTax) {
        this.deliveryTax = deliveryTax;
    }

    public BigDecimal getDeliveryExcTaxSumPrice() {
        return deliveryExcTaxSumPrice;
    }

    public void setDeliveryExcTaxSumPrice(BigDecimal deliveryExcTaxSumPrice) {
        this.deliveryExcTaxSumPrice = deliveryExcTaxSumPrice;
    }

    public BigDecimal getDeliveryTaxRate() {
        return deliveryTaxRate;
    }

    public void setDeliveryTaxRate(BigDecimal deliveryTaxRate) {
        this.deliveryTaxRate = deliveryTaxRate;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInStorageId() {
        return inStorageId;
    }

    public void setInStorageId(Integer inStorageId) {
        this.inStorageId = inStorageId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(BigDecimal balanceNum) {
        this.balanceNum = balanceNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getPriceSrc() {
        return priceSrc;
    }

    public void setPriceSrc(String priceSrc) {
        this.priceSrc = priceSrc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getInStorageMoney() {
        return inStorageMoney;
    }

    public void setInStorageMoney(BigDecimal inStorageMoney) {
        this.inStorageMoney = inStorageMoney;
    }

    public BigDecimal getPriceNoRate() {
        return priceNoRate;
    }

    public void setPriceNoRate(BigDecimal priceNoRate) {
        this.priceNoRate = priceNoRate;
    }

    public BigDecimal getRateMoney() {
        return rateMoney;
    }

    public void setRateMoney(BigDecimal rateMoney) {
        this.rateMoney = rateMoney;
    }

    public BigDecimal getMoneyNoRate() {
        return moneyNoRate;
    }

    public void setMoneyNoRate(BigDecimal moneyNoRate) {
        this.moneyNoRate = moneyNoRate;
    }
}
