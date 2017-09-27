package com.huayu.cms.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import com.ly.service.base.ExcelAnnotation;

/**
 * desc：内容_内容
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:11
 */
@Table(name = "cms_content")
public class CmsContent extends BaseDomain implements Serializable {

	// Fields
	@TableField
	private static final long serialVersionUID = -4638837152649728561L;
	@ExcelAnnotation(exportName="日期")
	private Long cid;
	@ExcelAnnotation(exportName="标题")
	private String title;
	private String shortText;
	private String imgUrl;
	private String allText;
	private Long sort;
	private String f1;
	private String f2;
	private String f3;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ExcelAnnotation(exportName="日期")
	private Date createDate;
	private Date updateDate;
	private Long mid;

	// Constructors

	/** default constructor */
	public CmsContent() {
		this.dt = "desc";
		this.dtn = "cid";
		this.pageSize = 10;
	}

	/** full constructor */
	public CmsContent(Long cid, String title, String shortText, String imgUrl,
			String allText, Long sort, String f1, String f2, String f3,
			Date createDate, Date updateDate,Long mid) {
		this.cid = cid;
		this.title = title;
		this.shortText = shortText;
		this.imgUrl = imgUrl;
		this.allText = allText;
		this.sort = sort;
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.mid = mid;
	}

	// Property accessors

	public Long getCid() {
		return this.cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortText() {
		return this.shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAllText() {
		
		return this.allText;
	}

	public void setAllText(String allText) {
		this.allText = allText;
	}

	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getF1() {
		return this.f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return this.f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return this.f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}
	
	
	public String getTextHtml() {
		if(this.allText!=null){
			return StringEscapeUtils.unescapeHtml4(this.allText);
		}else{
			return 	this.allText ;
		}
	}

	@Override
	public String toString(String s) {
//		System.out.println(s+"---------------------");
		StringBuilder sb = new StringBuilder("?");
		if(mid!=null && s.contains("mid")) 
			sb.append("mid="+mid);		
		if(pageNo!=null && s.contains("pageNo")) 
			sb.append("&pageNo="+pageNo);
		if(pageSize!=null && s.contains("pageSize")) 
			sb.append("&pageSize="+pageSize);
		if(dt!=null && s.contains("dt")) 
			sb.append("&dt="+dt);
		if(dtn!=null && s.contains("dtn")) 
			sb.append("&dtn="+dtn);		
		if(title!=null && s.contains("title"))
			sb.append("&title="+title);
		return sb.toString();
	}

	
}