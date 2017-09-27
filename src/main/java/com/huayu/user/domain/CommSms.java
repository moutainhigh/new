package com.huayu.user.domain;

import java.util.Date;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

@Table(name = "comm_sms")
public class CommSms  extends BaseDomain implements java.io.Serializable{

	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = 5484919404142749635L;

	private Long id;
	private String mobile;//手机号
	private String code;//验证码
	private Date sendDate;//发送时间
	private String result;//短信发送之后的返回值
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
