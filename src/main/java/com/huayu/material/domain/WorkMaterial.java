package com.huayu.material.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-25 16:00:54.
*/
@Table(name = "work_material")
public class WorkMaterial extends BaseDomain  implements Serializable{

	@TableField
	private static final long serialVersionUID = -2136433953768373130L;
	//private String updateFiledKey = id,name,specification,categoryId,unit,status,careatetime,createUser,deletetime,deleteUser;
 //private String updateFiledValue = id=:id,name=:name,specification=:specification,categoryId=:categoryId,unit=:unit,status=:status,careatetime=:careatetime,createUser=:createUser,deletetime=:deletetime,deleteUser=:deleteUser;
    private Integer id;
    private String name;
    private String code;
    private String specification;
    private Integer categoryId;
    private String unit;
    private Integer status;
    private String remark;
    private Date careatetime;
    private Integer createUser;
	private Date updatetime;
	private Integer updateUser;
    private Date deletetime;
    private Integer deleteUser;

    public WorkMaterial() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

	@TableField
	private String categoryName;
	@TableField
	private BigDecimal price;
	@TableField
	private BigDecimal historyPrice;
	@TableField
	private String newTime;
	@TableField
	private String historyPriceTime;
	@TableField
	private String mcode;
	@TableField
	private String specificationArrayStr;
	@TableField
	private Integer city;

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCareatetime() {
		return careatetime;
	}

	public void setCareatetime(Date careatetime) {
		this.careatetime = careatetime;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getHistoryPrice() {
		return historyPrice;
	}

	public void setHistoryPrice(BigDecimal historyPrice) {
		this.historyPrice = historyPrice;
	}

	public String getNewTime() {
		return newTime;
	}

	public void setNewTime(String newTime) {
		this.newTime = newTime;
	}

	public String getHistoryPriceTime() {
		return historyPriceTime;
	}

	public void setHistoryPriceTime(String historyPriceTime) {
		this.historyPriceTime = historyPriceTime;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getSpecificationArrayStr() {
		return specificationArrayStr;
	}

	public void setSpecificationArrayStr(String specificationArrayStr) {
		this.specificationArrayStr = specificationArrayStr;
	}
}
