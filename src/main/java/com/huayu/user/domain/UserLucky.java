package com.huayu.user.domain;


import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;


/**
 * 一元夺宝
 * @author pwj
 *
 */
@Table(name="user_lucky")
public class UserLucky extends BaseDomain implements java.io.Serializable {

	
	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = -6105289177525172253L;
	
	private Long id;//id
	private Long cid;//商品Id
	private Long number;//生成的抽奖号码
	private Long userId;//用户ID
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
