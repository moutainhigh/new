package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupInsert;
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
@Table(name = "pr_item_rejected")
public class PrItemRejected extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 9020293062817244692L;
   //private String updateFiledKey = id,mid,repositoryId,returnedUser,returnedDate,num,price,sumPrice,taxRate,tax,excTaxPrice,excTaxSumPrice,reason,createtime,crateUser;
 //private String updateFiledValue = id=:id,mid=:mid,repositoryId=:repositoryId,returnedUser=:returnedUser,returnedDate=:returnedDate,num=:num,price=:price,sumPrice=:sumPrice,taxRate=:taxRate,tax=:tax,excTaxPrice=:excTaxPrice,excTaxSumPrice=:excTaxSumPrice,reason=:reason,createtime=:createtime,crateUser=:crateUser;
    private Integer id;
    private Integer materialId;
    private Integer deliveryId;
    private Integer repositoryId;
   @NotNull(message = "退货人不能为空", groups = {GroupInsert.class})
    private String returnedUser;
   @NotNull(message = "退货日期不能为空", groups = {GroupInsert.class})
   @JSONField(format="yyyy-MM-dd")
   @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date returnedDate;
   @NotNull(message = "退货数量不能为空", groups = {GroupInsert.class})
    private BigDecimal num;
    private BigDecimal price;
    private BigDecimal sumPrice;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private BigDecimal excTaxPrice;
    private BigDecimal excTaxSumPrice;
    private String reason;
    private Date createtime;
    private Integer crateUser;

   @TableField
    private BigDecimal balanceNum;
   @TableField
    private BigDecimal deliveryNum;
   @TableField
   private String materialName;
   @TableField
   private String specification;
   @TableField
   private String unit;
   @TableField
   private String repositoryName;
   @TableField
   private String returnedUserName;

   public String getReturnedUserName() {
      return returnedUserName;
   }

   public void setReturnedUserName(String returnedUserName) {
      this.returnedUserName = returnedUserName;
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

   public String getRepositoryName() {
      return repositoryName;
   }

   public void setRepositoryName(String repositoryName) {
      this.repositoryName = repositoryName;
   }

   public PrItemRejected() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public Integer getDeliveryId() {
      return deliveryId;
   }

   public void setDeliveryId(Integer deliveryId) {
      this.deliveryId = deliveryId;
   }

   public BigDecimal getDeliveryNum() {
      return deliveryNum;
   }

   public void setDeliveryNum(BigDecimal deliveryNum) {
      this.deliveryNum = deliveryNum;
   }

   public BigDecimal getBalanceNum() {
      return balanceNum;
   }

   public void setBalanceNum(BigDecimal balanceNum) {
      this.balanceNum = balanceNum;
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

   public Integer getRepositoryId() {
      return repositoryId;
   }

   public void setRepositoryId(Integer repositoryId) {
      this.repositoryId = repositoryId;
   }

   public String getReturnedUser() {
      return returnedUser;
   }

   public void setReturnedUser(String returnedUser) {
      this.returnedUser = returnedUser;
   }

   public Date getReturnedDate() {
      return returnedDate;
   }

   public void setReturnedDate(Date returnedDate) {
      this.returnedDate = returnedDate;
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

   public String getReason() {
      return reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }

   public Date getCreatetime() {
      return createtime;
   }

   public void setCreatetime(Date createtime) {
      this.createtime = createtime;
   }

   public Integer getCrateUser() {
      return crateUser;
   }

   public void setCrateUser(Integer crateUser) {
      this.crateUser = crateUser;
   }


}
