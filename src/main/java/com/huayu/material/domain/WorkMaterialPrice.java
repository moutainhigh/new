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
@Table(name = "work_material_price")
public class WorkMaterialPrice extends BaseDomain  implements Serializable{

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

    @TableField
    private String mcode;
    @TableField
    private String name;
    @TableField
    private String specification;
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

    public WorkMaterialPrice() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
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
