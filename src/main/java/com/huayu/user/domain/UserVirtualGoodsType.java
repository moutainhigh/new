package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：用户_虚拟物品类型
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:29
 */
@Table(name="user_virtual_goods_type")
public class UserVirtualGoodsType extends BaseDomain implements java.io.Serializable {

	// Fields
	@TableField
	private static final long serialVersionUID = 5163935661524661880L;
	private Integer id;
	private String unitName;
	private String name;
	private Double num;
	private Short type;
	private String express;
	private String note;

	// Constructors

	/** default constructor */
	public UserVirtualGoodsType() {
	}

	/** full constructor */
	public UserVirtualGoodsType(Integer id, String unitName, String name,
			Double num, Short type, String express, String note) {
		this.id = id;
		this.unitName = unitName;
		this.name = name;
		this.num = num;
		this.type = type;
		this.express = express;
		this.note = note;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNum() {
		return this.num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getExpress() {
		return this.express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}