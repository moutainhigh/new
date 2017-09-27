package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：用户权限资源表
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:28
 */
@Table(name="user_qx_resource")
public class UserQxResource extends BaseDomain implements java.io.Serializable {

	@TableField
	private static final long serialVersionUID = 3836928396111329536L;
	private Integer rid;
	private Integer menuId;
	private String type;
	private String name;
	private String data;
	private Integer status;
	private String regSystem;
	private String description;

	@TableField
	private String mark;

	@TableField
	private Integer roleId;

	@TableField
	private boolean checked;

	public UserQxResource() {
		this.dt = "desc";
		this.dtn = "userId";
		this.pageSize = 10;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRegSystem() {
		return regSystem;
	}

	public void setRegSystem(String regSystem) {
		this.regSystem = regSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(int flag) {
		if (flag ==1){
			this.checked = true;
		}
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}