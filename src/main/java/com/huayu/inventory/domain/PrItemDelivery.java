package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupDelete;
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
* Created by DengPeng on 2017-6-12 13:58:54.
*/
@Table(name = "pr_item_delivery")
public class PrItemDelivery extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 6456140522441610116L;
   //private String updateFiledKey = id,mid,repositoryId,receiptUser,checkUser,receiptDate,num,price,sumPrice,taxRate,tax,excTaxPrice,excTaxSumPrice,note,itemProvider,status,inStorageStatus,createtime,createUser,updatetime,updateUser;
 //private String updateFiledValue = id=:id,mid=:mid,repositoryId=:repositoryId,receiptUser=:receiptUser,checkUser=:checkUser,receiptDate=:receiptDate,num=:num,price=:price,sumPrice=:sumPrice,taxRate=:taxRate,tax=:tax,excTaxPrice=:excTaxPrice,excTaxSumPrice=:excTaxSumPrice,note=:note,itemProvider=:itemProvider,status=:status,inStorageStatus=:inStorageStatus,createtime=:createtime,createUser=:createUser,updatetime=:updatetime,updateUser=:updateUser;
   @NotNull(message = "收货Id不能为空", groups = {GroupDelete.class, GroupUpdate.class})
    private Integer id;
   @NotNull(message = "材料Id不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private Integer materialId;
   @NotNull(message = "规格Id不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private Integer specificationId;
   @NotNull(message = "仓库Id不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private Integer repositoryId;

   @NotNull(message = "收货人不能为空", groups = {GroupInsert.class, GroupUpdate.class})
   private String receiptUserName;

   @NotNull(message = "验货人不能为空", groups = {GroupInsert.class, GroupUpdate.class})
   private String checkUserName;

   @NotNull(message = "收货日期不能为空", groups = {GroupInsert.class, GroupUpdate.class})
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
    private Date receiptDate;

   @NotNull(message = "收货数量不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal num;
   private BigDecimal rejectedNum;
   @NotNull(message = "单价不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal price;
   @NotNull(message = "总金额不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal sumPrice;
   @NotNull(message = "税率不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal taxRate;
   @NotNull(message = "税额不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal tax;
    private BigDecimal excTaxPrice;
   @NotNull(message = "不含税总金额不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private BigDecimal excTaxSumPrice;
   @NotNull(message = "供货商不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private String itemProviderName;
    private String note;

    private Integer status;
    private Integer inStorageStatus;
    private String remark;
    private Date createtime;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;

   public PrItemDelivery(Integer id) {
      this.id = id;
   }

   public PrItemDelivery() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public PrItemDelivery(Integer materialId, Integer specificationId) {
      this.materialId = materialId;
      this.specificationId = specificationId;
   }
   @TableField
   private Integer receiptUser;
   @TableField
   private Integer checkUser;
   @TableField
   private Integer itemProvider;
   @TableField
   private Integer noteId;
   @TableField
   private String materialAlias;
   @TableField
   private String categoryName;
   @TableField
   private Integer categoryId;
   @TableField
   private String repositoryName;
   @TableField
   private String materialName;
   @TableField
   private String alias;
   @TableField
   private String specification;
   @TableField
   private String unit;
   @TableField
   private Integer mid;
   @TableField
   private Integer deliveryId;
   @TableField
   private String repositoryPerson;
   @TableField
   private Integer inStoreUser;
   @TableField
   private BigDecimal balanceNum;
   @TableField
   private BigDecimal inStorageSum;
   @TableField
   private BigDecimal inStoragePrice;
   @TableField
   private BigDecimal inStorageSumPrice;
   @TableField
   private BigDecimal inStorageExcTaxSumPrice;
   @TableField
   private BigDecimal inStorageTax;

   @TableField
   private BigDecimal oldNum;
   @TableField
   private Date startDate;
   @TableField
   private Date endDate;
   @TableField
   private String[] ids;
   @TableField
   private String mcode;
   @TableField
   private String mcCode;
   @TableField
   private BigDecimal outStorageNum;
   @TableField
   private BigDecimal outStorageSumPrice;
   @TableField
   private BigDecimal outStorageExcTaxSumPrice;
   @TableField
   private String billReceiver;
   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
   private Date billReceiveDate;
   @TableField
   private String outStorageNo;
   @TableField
   private String inStorageNum;


   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

   public Integer getNoteId() {
      return noteId;
   }

   public void setNoteId(Integer noteId) {
      this.noteId = noteId;
   }

   public String getOutStorageNo() {
      return outStorageNo;
   }

   public void setOutStorageNo(String outStorageNo) {
      this.outStorageNo = outStorageNo;
   }

   public String getInStorageNum() {
      return inStorageNum;
   }

   public void setInStorageNum(String inStorageNum) {
      this.inStorageNum = inStorageNum;
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

   public BigDecimal getOutStorageSumPrice() {
      return outStorageSumPrice;
   }

   public void setOutStorageSumPrice(BigDecimal outStorageSumPrice) {
      this.outStorageSumPrice = outStorageSumPrice;
   }

   public BigDecimal getOutStorageExcTaxSumPrice() {
      return outStorageExcTaxSumPrice;
   }

   public void setOutStorageExcTaxSumPrice(BigDecimal outStorageExcTaxSumPrice) {
      this.outStorageExcTaxSumPrice = outStorageExcTaxSumPrice;
   }

   public String getMcode() {
      return mcode;
   }

   public void setMcode(String mcode) {
      this.mcode = mcode;
   }

   public String getMcCode() {
      return mcCode;
   }

   public void setMcCode(String mcCode) {
      this.mcCode = mcCode;
   }

   public String[] getIds() {
      return ids;
   }

   public void setIds(String[] ids) {
      this.ids = ids;
   }

   public BigDecimal getOutStorageNum() {
      return outStorageNum;
   }

   public void setOutStorageNum(BigDecimal outStorageNum) {
      this.outStorageNum = outStorageNum;
   }


   public Integer getCategoryId() {
      return categoryId;
   }

   public void setCategoryId(Integer categoryId) {
      this.categoryId = categoryId;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public BigDecimal getInStorageTax() {
      return inStorageTax;
   }

   public void setInStorageTax(BigDecimal inStorageTax) {
      this.inStorageTax = inStorageTax;
   }

   public BigDecimal getOldNum() {
      return oldNum;
   }

   public void setOldNum(BigDecimal oldNum) {
      this.oldNum = oldNum;
   }

   public BigDecimal getRejectedNum() {
      return rejectedNum;
   }

   public void setRejectedNum(BigDecimal rejectedNum) {
      this.rejectedNum = rejectedNum;
   }

   public BigDecimal getBalanceNum() {
      return balanceNum;
   }

   public void setBalanceNum(BigDecimal balanceNum) {
      this.balanceNum = balanceNum;
   }

   public PrItemDelivery(int id) {
      this.id = id;
   }

    public static long getSerialVersionUID() {
      return serialVersionUID;
   }

   public String getRepositoryName() {
      return repositoryName;
   }

   public void setRepositoryName(String repositoryName) {
      this.repositoryName = repositoryName;
   }

   public String getAlias() {
      return alias;
   }

   public void setAlias(String alias) {
      this.alias = alias;
   }

   public Integer getMid() {
      return mid;
   }

   public void setMid(Integer mid) {
      this.mid = mid;
   }

   public Integer getDeliveryId() {
      return deliveryId;
   }

   public void setDeliveryId(Integer deliveryId) {
      this.deliveryId = deliveryId;
   }

   public String getRepositoryPerson() {
      return repositoryPerson;
   }

   public void setRepositoryPerson(String repositoryPerson) {
      this.repositoryPerson = repositoryPerson;
   }

   public Integer getInStoreUser() {
      return inStoreUser;
   }

   public void setInStoreUser(Integer inStoreUser) {
      this.inStoreUser = inStoreUser;
   }

   public Integer getRepositoryId() {
      return repositoryId;
   }

   public void setRepositoryId(Integer repositoryId) {
      this.repositoryId = repositoryId;
   }

   public String getCategoryName() {
      return categoryName;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
   }

   public String getUnit() {
      return unit;
   }

   public void setUnit(String unit) {
      this.unit = unit;
   }

   public String getMaterialName() {
      return materialName;
   }

   public void setMaterialName(String materialName) {
      this.materialName = materialName;
   }

   public String getMaterialAlias() {
      return materialAlias;
   }

   public void setMaterialAlias(String materialAlias) {
      this.materialAlias = materialAlias;
   }

   public String getSpecification() {
      return specification;
   }

   public void setSpecification(String specification) {
      this.specification = specification;
   }

   public String getReceiptUserName() {
      return receiptUserName;
   }

   public void setReceiptUserName(String receiptUserName) {
      this.receiptUserName = receiptUserName;
   }

   public String getCheckUserName() {
      return checkUserName;
   }

   public void setCheckUserName(String checkUserName) {
      this.checkUserName = checkUserName;
   }

   public String getItemProviderName() {
      return itemProviderName;
   }

   public void setItemProviderName(String itemProviderName) {
      this.itemProviderName = itemProviderName;
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

   public Integer getReceiptUser() {
      return receiptUser;
   }

   public void setReceiptUser(Integer receiptUser) {
      this.receiptUser = receiptUser;
   }

   public Integer getCheckUser() {
      return checkUser;
   }

   public void setCheckUser(Integer checkUser) {
      this.checkUser = checkUser;
   }

   public Date getReceiptDate() {
      return receiptDate;
   }

   public void setReceiptDate(Date receiptDate) {
      this.receiptDate = receiptDate;
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

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public Integer getItemProvider() {
      return itemProvider;
   }

   public void setItemProvider(Integer itemProvider) {
      this.itemProvider = itemProvider;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public Integer getInStorageStatus() {
      return inStorageStatus;
   }

   public void setInStorageStatus(Integer inStorageStatus) {
      this.inStorageStatus = inStorageStatus;
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
   public BigDecimal getInStorageSum() {
      return inStorageSum;
   }

   public void setInStorageSum(BigDecimal inStorageSum) {
      this.inStorageSum = inStorageSum;
   }

   public BigDecimal getInStoragePrice() {
      return inStoragePrice;
   }

   public void setInStoragePrice(BigDecimal inStoragePrice) {
      this.inStoragePrice = inStoragePrice;
   }

   public BigDecimal getInStorageSumPrice() {
      return inStorageSumPrice;
   }

   public void setInStorageSumPrice(BigDecimal inStorageSumPrice) {
      this.inStorageSumPrice = inStorageSumPrice;
   }

   public BigDecimal getInStorageExcTaxSumPrice() {
      return inStorageExcTaxSumPrice;
   }

   public void setInStorageExcTaxSumPrice(BigDecimal inStorageExcTaxSumPrice) {
      this.inStorageExcTaxSumPrice = inStorageExcTaxSumPrice;
   }
}
