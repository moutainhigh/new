package com.huayu.user.domain;

import java.sql.Timestamp;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：用户_虚拟物品
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:28
 */
@Table(name="user_virtual_goods")
public class UserVirtualGoods extends BaseDomain implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = -4056281162156945755L;
	private Long id;
	private Long userId;
	private Integer vid;
	private Short type;
	private String express;
	private Double num;
	private Timestamp creatDate;
	private Long allocateId;

	// Constructors

	/** default constructor */
	public UserVirtualGoods() {
	}

	/** full constructor */
	public UserVirtualGoods(Long id, Long userId, Integer vid, Short type,
			String express, Double num, Timestamp creatDate, Long allocateId) {
		this.id = id;
		this.userId = userId;
		this.vid = vid;
		this.type = type;
		this.express = express;
		this.num = num;
		this.creatDate = creatDate;
		this.allocateId = allocateId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getVid() {
		return this.vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
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

	public Double getNum() {
		return this.num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public Timestamp getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Timestamp creatDate) {
		this.creatDate = creatDate;
	}

	public Long getAllocateId() {
		return this.allocateId;
	}

	public void setAllocateId(Long allocateId) {
		this.allocateId = allocateId;
	}

}