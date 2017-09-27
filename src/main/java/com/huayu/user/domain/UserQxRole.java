package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：用户权限表
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:28
 */
@Table(name = "user_qx_role")
public class UserQxRole extends BaseDomain implements java.io.Serializable {

	@TableField
	private static final long serialVersionUID = -7628139534577976806L;
	private Integer roleId;
	private String name;
	private String mark;
	private String dataAuthority;
	private Short status;
	private String regSystem;
	private String note;

	public UserQxRole() {
		this.dt = "desc";
		this.dtn = "id";
		this.pageSize = 10;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDataAuthority() {
		return dataAuthority;
	}

	public void setDataAuthority(String dataAuthority) {
		this.dataAuthority = dataAuthority;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getRegSystem() {
		return regSystem;
	}

	public void setRegSystem(String regSystem) {
		this.regSystem = regSystem;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}