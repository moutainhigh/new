package com.huayu.user.domain;

import java.sql.Timestamp;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：用户区域表
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:27
 */
@Table(name="user_area")
public class UserArea extends BaseDomain implements java.io.Serializable {

	// Fields
	@TableField
	private static final long serialVersionUID = -7381319497122021613L;
	private Long userId;
	private String address;
	private String zip;
	private Long areaId;
	private String country;
	private String state;
	private String city;
	private String district;
	private String longitude;
	private String latitude;
	private Timestamp creatDate;

	// Constructors

	/** default constructor */
	public UserArea() {
	}

	/** full constructor */
	public UserArea(Long userId, String address, String zip, Long areaId,
			String country, String state, String city, String district,
			String longitude, String latitude, Timestamp creatDate) {
		this.userId = userId;
		this.address = address;
		this.zip = zip;
		this.areaId = areaId;
		this.country = country;
		this.state = state;
		this.city = city;
		this.district = district;
		this.longitude = longitude;
		this.latitude = latitude;
		this.creatDate = creatDate;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Timestamp getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Timestamp creatDate) {
		this.creatDate = creatDate;
	}

}