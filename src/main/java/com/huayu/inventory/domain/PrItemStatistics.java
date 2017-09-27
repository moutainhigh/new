package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupQuery;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-29 13:25:52.
*/
@Table(name = "pr_item_statistics")
public class PrItemStatistics extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,repositoryId,startDate,endDate,repositoryName,categoryName,materialName,specificationName,unit,startPrice,startNum,startSumTax,startSumPrice,startSumExcTaxPrice,addPrice,addNum,addSumTax,addSumPrice,addSumExcTaxPrice,reducePirce,reduceNum,reduceSumTax,reduceSumPrice,reduceSumExcTaxPrice,endPrice,endNum,endSumTax,endSumPrice,endSumExcTaxPrice,status,cratetime;
 //private String updateFiledValue = id=:id,repositoryId=:repositoryId,startDate=:startDate,endDate=:endDate,repositoryName=:repositoryName,categoryName=:categoryName,materialName=:materialName,specificationName=:specificationName,unit=:unit,startPrice=:startPrice,startNum=:startNum,startSumTax=:startSumTax,startSumPrice=:startSumPrice,startSumExcTaxPrice=:startSumExcTaxPrice,addPrice=:addPrice,addNum=:addNum,addSumTax=:addSumTax,addSumPrice=:addSumPrice,addSumExcTaxPrice=:addSumExcTaxPrice,reducePirce=:reducePirce,reduceNum=:reduceNum,reduceSumTax=:reduceSumTax,reduceSumPrice=:reduceSumPrice,reduceSumExcTaxPrice=:reduceSumExcTaxPrice,endPrice=:endPrice,endNum=:endNum,endSumTax=:endSumTax,endSumPrice=:endSumPrice,endSumExcTaxPrice=:endSumExcTaxPrice,status=:status,cratetime=:cratetime;
    private Integer id;
    @NotNull(message = "仓库Id不能为空", groups = {GroupQuery.class})
    private Integer repositoryId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    @NotNull(message = "开始时间不能为空", groups = {GroupQuery.class})
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    @NotNull(message = "结束时间不能为空", groups = {GroupQuery.class})
    private Date endDate;
    private Integer categoryId;
    private Integer materialId;
    private Integer specificationId;
    private String categoryName;
    private String materialName;
    private String specificationName;
    private String unit;
    private BigDecimal startPrice = new BigDecimal(0);
    private BigDecimal startNum = new BigDecimal(0);
    private BigDecimal startSumTax = new BigDecimal(0);
    private BigDecimal startSumPrice = new BigDecimal(0);
    private BigDecimal startSumExcTaxPrice = new BigDecimal(0);
    private BigDecimal addPrice = new BigDecimal(0);
    private BigDecimal addNum = new BigDecimal(0);
    private BigDecimal addSumTax = new BigDecimal(0);
    private BigDecimal addSumPrice = new BigDecimal(0);
    private BigDecimal addSumExcTaxPrice = new BigDecimal(0);
    private BigDecimal reducePrice = new BigDecimal(0);
    private BigDecimal reduceNum = new BigDecimal(0);
    private BigDecimal reduceSumTax = new BigDecimal(0);
    private BigDecimal reduceSumPrice = new BigDecimal(0);
    private BigDecimal reduceSumExcTaxPrice = new BigDecimal(0);
    private BigDecimal endPrice = new BigDecimal(0);
    private BigDecimal endNum = new BigDecimal(0);
    private BigDecimal endSumTax = new BigDecimal(0);
    private BigDecimal endSumPrice = new BigDecimal(0);
    private BigDecimal endSumExcTaxPrice = new BigDecimal(0);
    private Integer status;
    private Date createtime;

    @TableField
    private BigDecimal sumPrice = new BigDecimal(0);
    @TableField
    private BigDecimal num = new BigDecimal(0);
    @TableField
    private BigDecimal tax = new BigDecimal(0);
    @TableField
    private Date statisticsDate;
    @TableField
    private String repositoryName;

    @TableField
    private BigDecimal balanceSumPrice = new BigDecimal(0);
    @TableField
    private BigDecimal balanceSumTax = new BigDecimal(0);



    public PrItemStatistics() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getRepositoryId() {
      return repositoryId;
   }

   public void setRepositoryId(Integer repositoryId) {
      this.repositoryId = repositoryId;
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

   public String getSpecificationName() {
      return specificationName;
   }

   public void setSpecificationName(String specificationName) {
      this.specificationName = specificationName;
   }

   public String getUnit() {
      return unit;
   }

   public void setUnit(String unit) {
      this.unit = unit;
   }

   public BigDecimal getStartPrice() {
      return startPrice;
   }

   public void setStartPrice(BigDecimal startPrice) {
      this.startPrice = startPrice;
   }

   public BigDecimal getStartNum() {
      return startNum;
   }

   public void setStartNum(BigDecimal startNum) {
      this.startNum = startNum;
   }

   public BigDecimal getStartSumTax() {
      return startSumTax;
   }

   public void setStartSumTax(BigDecimal startSumTax) {
      this.startSumTax = startSumTax;
   }

   public BigDecimal getStartSumPrice() {
      return startSumPrice;
   }

   public void setStartSumPrice(BigDecimal startSumPrice) {
      this.startSumPrice = startSumPrice;
   }

   public BigDecimal getStartSumExcTaxPrice() {
      return startSumExcTaxPrice;
   }

   public void setStartSumExcTaxPrice(BigDecimal startSumExcTaxPrice) {
      this.startSumExcTaxPrice = startSumExcTaxPrice;
   }

   public BigDecimal getAddPrice() {
      return addPrice;
   }

   public void setAddPrice(BigDecimal addPrice) {
      this.addPrice = addPrice;
   }

   public BigDecimal getAddNum() {
      return addNum;
   }

   public void setAddNum(BigDecimal addNum) {
      this.addNum = addNum;
   }

   public BigDecimal getAddSumTax() {
      return addSumTax;
   }

   public void setAddSumTax(BigDecimal addSumTax) {
      this.addSumTax = addSumTax;
   }

   public BigDecimal getAddSumPrice() {
      return addSumPrice;
   }

   public void setAddSumPrice(BigDecimal addSumPrice) {
      this.addSumPrice = addSumPrice;
   }

   public BigDecimal getAddSumExcTaxPrice() {
      return addSumExcTaxPrice;
   }

   public void setAddSumExcTaxPrice(BigDecimal addSumExcTaxPrice) {
      this.addSumExcTaxPrice = addSumExcTaxPrice;
   }

    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    public BigDecimal getReduceNum() {
      return reduceNum;
   }

   public void setReduceNum(BigDecimal reduceNum) {
      this.reduceNum = reduceNum;
   }

   public BigDecimal getReduceSumTax() {
      return reduceSumTax;
   }

   public void setReduceSumTax(BigDecimal reduceSumTax) {
      this.reduceSumTax = reduceSumTax;
   }

   public BigDecimal getReduceSumPrice() {
      return reduceSumPrice;
   }

   public void setReduceSumPrice(BigDecimal reduceSumPrice) {
      this.reduceSumPrice = reduceSumPrice;
   }

   public BigDecimal getReduceSumExcTaxPrice() {
      return reduceSumExcTaxPrice;
   }

   public void setReduceSumExcTaxPrice(BigDecimal reduceSumExcTaxPrice) {
      this.reduceSumExcTaxPrice = reduceSumExcTaxPrice;
   }

   public BigDecimal getEndPrice() {
      return endPrice;
   }

   public void setEndPrice(BigDecimal endPrice) {
      this.endPrice = endPrice;
   }

   public BigDecimal getEndNum() {
      return endNum;
   }

   public void setEndNum(BigDecimal endNum) {
      this.endNum = endNum;
   }

   public BigDecimal getEndSumTax() {
      return endSumTax;
   }

   public void setEndSumTax(BigDecimal endSumTax) {
      this.endSumTax = endSumTax;
   }

   public BigDecimal getEndSumPrice() {
      return endSumPrice;
   }

   public void setEndSumPrice(BigDecimal endSumPrice) {
      this.endSumPrice = endSumPrice;
   }

   public BigDecimal getEndSumExcTaxPrice() {
      return endSumExcTaxPrice;
   }

   public void setEndSumExcTaxPrice(BigDecimal endSumExcTaxPrice) {
      this.endSumExcTaxPrice = endSumExcTaxPrice;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public BigDecimal getBalanceSumPrice() {
        return balanceSumPrice;
    }

    public void setBalanceSumPrice(BigDecimal balanceSumPrice) {
        this.balanceSumPrice = balanceSumPrice;
    }

    public BigDecimal getBalanceSumTax() {
        return balanceSumTax;
    }

    public void setBalanceSumTax(BigDecimal balanceSumTax) {
        this.balanceSumTax = balanceSumTax;
    }
}
