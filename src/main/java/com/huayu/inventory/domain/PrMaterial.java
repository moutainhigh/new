package com.huayu.inventory.domain;

import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_material")
public class PrMaterial extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 7581936672857680404L;
   //private String updateFiledKey = id,projectId,repositoryId,mcode,name,alias,brand,specification,categoryId,unit,supplySum,lastPrice,currentRepositoryNum,status,remark,careatetime,createUser,deletetime,deleteUser;
 //private String updateFiledValue = id=:id,projectId=:projectId,repositoryId=:repositoryId,mcode=:mcode,name=:name,alias=:alias,brand=:brand,specification=:specification,categoryId=:categoryId,unit=:unit,supplySum=:supplySum,lastPrice=:lastPrice,currentRepositoryNum=:currentRepositoryNum,status=:status,remark=:remark,careatetime=:careatetime,createUser=:createUser,deletetime=:deletetime,deleteUser=:deleteUser;
   @NotNull(message = "材料Id不能为空", groups = {GroupInsert.class, GroupDelete.class})
    private Integer id;
    private Integer projectId;
    private String mcode;
    private String name;
    private String alias;
    private String brand;
    private Integer categoryId;
    private Integer unit;
    private Integer status;
    private String remark;
    private Date createtime;
    private Integer createUser;
    private Date deletetime;
    private Integer deleteUser;

    public PrMaterial() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public PrMaterial(Integer repositoryId,Integer id, Integer specificationId) {
       this.repositoryId = repositoryId;
       this.id = id;
       this.specificationId = specificationId;
   }

   @TableField
    private String categoryName;
   @TableField
    private String specification;
   @TableField
   @NotNull(message = "材料规格Id不能为空", groups = {GroupDelete.class})
    private Integer specificationId;

   @TableField
   private BigDecimal balanceNum;

   @TableField
   private BigDecimal balancePrice;

   @TableField
   private BigDecimal balanceSumPrice;

   @TableField
   private BigDecimal balanceExcTaxSumPrice;

   @TableField
   private Integer repositoryId;

   @TableField
   private BigDecimal budget;
   @TableField
   private BigDecimal taxRate;
   @TableField
   private BigDecimal tax;

   @TableField
   private String unitStr;

   public BigDecimal getBudget() {
      return budget;
   }

   public void setBudget(BigDecimal budget) {
      this.budget = budget;
   }

   public BigDecimal getBalanceExcTaxSumPrice() {
      return balanceExcTaxSumPrice;
   }

   public void setBalanceExcTaxSumPrice(BigDecimal balanceExcTaxSumPrice) {
      this.balanceExcTaxSumPrice = balanceExcTaxSumPrice;
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

   public Integer getSpecificationId() {
      return specificationId;
   }

   public void setSpecificationId(Integer specificationId) {
      this.specificationId = specificationId;
   }

   public String getCategoryName() {
      return categoryName;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
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

   public Integer getRepositoryId() {
      return repositoryId;
   }

   public void setRepositoryId(Integer repositoryId) {
      this.repositoryId = repositoryId;
   }

   public String getMcode() {
      return mcode;
   }

   public void setMcode(String mcode) {
      this.mcode = mcode;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAlias() {
      return alias;
   }

   public void setAlias(String alias) {
      this.alias = alias;
   }

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public Integer getCategoryId() {
      return categoryId;
   }

   public void setCategoryId(Integer categoryId) {
      this.categoryId = categoryId;
   }

   public Integer getUnit() {
      return unit;
   }

   public void setUnit(Integer unit) {
      this.unit = unit;
   }

   public String getUnitStr() {
      return unitStr;
   }

   public void setUnitStr(String unitStr) {
      this.unitStr = unitStr;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
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

   public String getSpecification() {
      return specification;
   }

   public void setSpecification(String specification) {
      this.specification = specification;
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
}
