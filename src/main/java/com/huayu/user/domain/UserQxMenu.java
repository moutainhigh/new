package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.List;

/**
 * desc：用户权限菜单表
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:28
 */
@Table(name = "user_qx_menu")
public class UserQxMenu extends BaseDomain implements Serializable {

	@TableField
	private static final long serialVersionUID = -1550458871098520636L;
	private Integer menuId;
	private Integer parentId;
	private Short isParent;
	private String name;
	private String regSystem;
	private String mark;
	private String url;
	private String icon;
	private String sort;
	private Short status;

	@TableField
	private List<UserQxMenu> list;

	@TableField
	private Integer roleId;

	@TableField
	private boolean checked;

	@TableField
	private boolean isContain;


	public UserQxMenu() {
		this.dt = "desc";
		this.dtn = "id";
		this.pageSize = 10;
	}


	public boolean isContain() {
		return isContain;
	}

	public void setContain(boolean contain) {
		isContain = contain;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Short getIsParent() {
		return this.isParent;
	}

	public void setIsParent(Short isParent) {
		this.isParent = isParent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegSystem() {
		return regSystem;
	}

	public void setRegSystem(String regSystem) {
		this.regSystem = regSystem;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public List<UserQxMenu> getList() {
		return list;
	}

	public void setList(List<UserQxMenu> list) {
		this.list = list;
	}

}