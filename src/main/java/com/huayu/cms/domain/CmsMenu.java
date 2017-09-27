package com.huayu.cms.domain;

import java.sql.Timestamp;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：内容_菜单
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:11
 */
@Table(name = "cms_menu")
public class CmsMenu extends BaseDomain implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = -9042973089457660736L;
	private Long mid;
	private String name;
	private String url;
	private Long pmid;
	private Short isParent;
	private Short status;
	private Integer sortOrder;
	private Short type;
	private String note;
	private Timestamp createDate;
	private String titleAlias;
	private Short isTitleShow;
	private String shortTextAlias;
	private Short isShortTextShow;
	private String imgUrlAlias;
	private Short isImgUrlShow;
	private String allTextAlias;
	private Short isAllTextShow;
	private String sortAlias;
	private Short isSortShow;
	private String f1alias;
	private Short isF1show;
	private String f2alias;
	private Short isF2show;
	private String f3alias;
	private Short isF3show;
	private Short insertBtn;
	private Short deleteBtn;
	private Short updateBtn;

	// Constructors

	/** default constructor */
	public CmsMenu() {
	}

	/** full constructor */
	public CmsMenu(Long mid, String name, String url, Long pmid,
			Short isParent, Short status, Integer sortOrder, Short type,
			String note, Timestamp createDate, String titleAlias,
			Short isTitleShow, String shortTextAlias, Short isShortTextShow,
			String imgUrlAlias, Short isImgUrlShow, String allTextAlias,
			Short isAllTextShow, String sortAlias, Short isSortShow,
			String f1alias, Short isF1show, String f2alias, Short isF2show,
			String f3alias, Short isF3show, Short insertBtn, Short deleteBtn,
			Short updateBtn) {
		this.mid = mid;
		this.name = name;
		this.url = url;
		this.pmid = pmid;
		this.isParent = isParent;
		this.status = status;
		this.sortOrder = sortOrder;
		this.type = type;
		this.note = note;
		this.createDate = createDate;
		this.titleAlias = titleAlias;
		this.isTitleShow = isTitleShow;
		this.shortTextAlias = shortTextAlias;
		this.isShortTextShow = isShortTextShow;
		this.imgUrlAlias = imgUrlAlias;
		this.isImgUrlShow = isImgUrlShow;
		this.allTextAlias = allTextAlias;
		this.isAllTextShow = isAllTextShow;
		this.sortAlias = sortAlias;
		this.isSortShow = isSortShow;
		this.f1alias = f1alias;
		this.isF1show = isF1show;
		this.f2alias = f2alias;
		this.isF2show = isF2show;
		this.f3alias = f3alias;
		this.isF3show = isF3show;
		this.insertBtn = insertBtn;
		this.deleteBtn = deleteBtn;
		this.updateBtn = updateBtn;
	}

	// Property accessors

	public Long getMid() {
		return this.mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPmid() {
		return this.pmid;
	}

	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}

	public Short getIsParent() {
		return this.isParent;
	}

	public void setIsParent(Short isParent) {
		this.isParent = isParent;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getTitleAlias() {
		return this.titleAlias;
	}

	public void setTitleAlias(String titleAlias) {
		this.titleAlias = titleAlias;
	}

	public Short getIsTitleShow() {
		return this.isTitleShow;
	}

	public void setIsTitleShow(Short isTitleShow) {
		this.isTitleShow = isTitleShow;
	}

	public String getShortTextAlias() {
		return this.shortTextAlias;
	}

	public void setShortTextAlias(String shortTextAlias) {
		this.shortTextAlias = shortTextAlias;
	}

	public Short getIsShortTextShow() {
		return this.isShortTextShow;
	}

	public void setIsShortTextShow(Short isShortTextShow) {
		this.isShortTextShow = isShortTextShow;
	}

	public String getImgUrlAlias() {
		return this.imgUrlAlias;
	}

	public void setImgUrlAlias(String imgUrlAlias) {
		this.imgUrlAlias = imgUrlAlias;
	}

	public Short getIsImgUrlShow() {
		return this.isImgUrlShow;
	}

	public void setIsImgUrlShow(Short isImgUrlShow) {
		this.isImgUrlShow = isImgUrlShow;
	}

	public String getAllTextAlias() {
		return this.allTextAlias;
	}

	public void setAllTextAlias(String allTextAlias) {
		this.allTextAlias = allTextAlias;
	}

	public Short getIsAllTextShow() {
		return this.isAllTextShow;
	}

	public void setIsAllTextShow(Short isAllTextShow) {
		this.isAllTextShow = isAllTextShow;
	}

	public String getSortAlias() {
		return this.sortAlias;
	}

	public void setSortAlias(String sortAlias) {
		this.sortAlias = sortAlias;
	}

	public Short getIsSortShow() {
		return this.isSortShow;
	}

	public void setIsSortShow(Short isSortShow) {
		this.isSortShow = isSortShow;
	}

	public String getF1alias() {
		return this.f1alias;
	}

	public void setF1alias(String f1alias) {
		this.f1alias = f1alias;
	}

	public Short getIsF1show() {
		return this.isF1show;
	}

	public void setIsF1show(Short isF1show) {
		this.isF1show = isF1show;
	}

	public String getF2alias() {
		return this.f2alias;
	}

	public void setF2alias(String f2alias) {
		this.f2alias = f2alias;
	}

	public Short getIsF2show() {
		return this.isF2show;
	}

	public void setIsF2show(Short isF2show) {
		this.isF2show = isF2show;
	}

	public String getF3alias() {
		return this.f3alias;
	}

	public void setF3alias(String f3alias) {
		this.f3alias = f3alias;
	}

	public Short getIsF3show() {
		return this.isF3show;
	}

	public void setIsF3show(Short isF3show) {
		this.isF3show = isF3show;
	}

	public Short getInsertBtn() {
		return this.insertBtn;
	}

	public void setInsertBtn(Short insertBtn) {
		this.insertBtn = insertBtn;
	}

	public Short getDeleteBtn() {
		return this.deleteBtn;
	}

	public void setDeleteBtn(Short deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public Short getUpdateBtn() {
		return this.updateBtn;
	}

	public void setUpdateBtn(Short updateBtn) {
		this.updateBtn = updateBtn;
	}

}