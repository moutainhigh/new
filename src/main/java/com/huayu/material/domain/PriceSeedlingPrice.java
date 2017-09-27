package com.huayu.material.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-5-3 18:08:33.
*/
@Table(name = "price_seedling_price")
public class PriceSeedlingPrice extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,categoryId,materialName,specification,unit,price,note,insertTime,updateTime;
 //private String updateFiledValue = id=:id,categoryId=:categoryId,materialName=:materialName,specification=:specification,unit=:unit,price=:price,note=:note,insertTime=:insertTime,updateTime=:updateTime;
    private Integer id;
    private Integer matId;
    private BigDecimal price;
    private Integer city;               //0重庆 1成都 2江苏 3合肥  4苏州    （暂定）
    private String reportYearMonth;     //'上报年份月份 如: 201703',
    private Date createtime;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;

    public PriceSeedlingPrice() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private Integer categoryId;
    @TableField
    private String categoryName;
    @TableField
    private String mcode;
    @TableField
    private String name;
    @TableField
    private String specification;
    @TableField
    private String specification2;
    @TableField
    private String specification3;
    @TableField
    private String specification4;
    @TableField
    private String specification5;
    @TableField
    private String specification6;
    @TableField
    private String specification7;
    @TableField
    private String extend;
    @TableField
    private String extend2;
    @TableField
    private String unit;
    @TableField
    private Double pcq;
    @TableField
    private Double pcd;
    @TableField
    private Double pjs;
    @TableField
    private Double phf;
    @TableField
    private Double psz;

    @TableField
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    @TableField
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date historyPriceTime;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSpecification2() {
        return specification2;
    }

    public void setSpecification2(String specification2) {
        this.specification2 = specification2;
    }

    public String getSpecification3() {
        return specification3;
    }

    public void setSpecification3(String specification3) {
        this.specification3 = specification3;
    }

    public String getSpecification4() {
        return specification4;
    }

    public void setSpecification4(String specification4) {
        this.specification4 = specification4;
    }

    public String getSpecification5() {
        return specification5;
    }

    public void setSpecification5(String specification5) {
        this.specification5 = specification5;
    }

    public String getSpecification6() {
        return specification6;
    }

    public void setSpecification6(String specification6) {
        this.specification6 = specification6;
    }

    public String getSpecification7() {
        return specification7;
    }

    public void setSpecification7(String specification7) {
        this.specification7 = specification7;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatId() {
        return matId;
    }

    public void setMatId(Integer matId) {
        this.matId = matId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getReportYearMonth() {
        return reportYearMonth;
    }

    public void setReportYearMonth(String reportYearMonth) {
        this.reportYearMonth = reportYearMonth;
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

    public Double getPcq() {
        return pcq;
    }

    public void setPcq(Double pcq) {
        this.pcq = pcq;
    }

    public Double getPcd() {
        return pcd;
    }

    public void setPcd(Double pcd) {
        this.pcd = pcd;
    }

    public Double getPjs() {
        return pjs;
    }

    public void setPjs(Double pjs) {
        this.pjs = pjs;
    }

    public Double getPhf() {
        return phf;
    }

    public void setPhf(Double phf) {
        this.phf = phf;
    }

    public Double getPsz() {
        return psz;
    }

    public void setPsz(Double psz) {
        this.psz = psz;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Date getHistoryPriceTime() {
        return historyPriceTime;
    }

    public void setHistoryPriceTime(Date historyPriceTime) {
        this.historyPriceTime = historyPriceTime;
    }
}
