package com.huayu.user.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

@Table(name = "shop_order")
public class ShopOrder extends BaseDomain implements java.io.Serializable{
	
	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = -2759550570898275000L;
	private Long oid;//订单编号
	private String oCode;//订单代码
	private String outCode;//外部订单代码
	private Integer number;//数量
	private BigDecimal price;//价格
	private Integer payMethod;//支付方式
	private Integer status;//支付方式
	private Date createDate;//建立日期
	private Long buyId;//买家编号
	private Long sendId;//卖家编号
	private Long numIid;//商品编号
	private Long cid;//商品分类编号
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getoCode() {
		return oCode;
	}
	public void setoCode(String oCode) {
		this.oCode = oCode;
	}
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getBuyId() {
		return buyId;
	}
	public void setBuyId(Long buyId) {
		this.buyId = buyId;
	}
	public Long getSendId() {
		return sendId;
	}
	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	
	
	
	
	
	
	
}
