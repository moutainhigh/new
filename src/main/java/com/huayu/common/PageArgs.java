package com.huayu.common; /*

/**
 * @Filename com.home.cms.web.param
 * @Description:
 * @Version 1.0
 * @Author D
 * @Email
 */
public class PageArgs {
	private Integer start;
	private Integer pageIndex;
	private Integer length;

	public PageArgs() {
		this.start = 0;
		this.length = 10;
		initPageInfo();
	}

	public PageArgs(Integer pageIndex) {
		this();
		this.pageIndex = pageIndex;
	}

	public PageArgs(Integer start, Integer length) {
		this.start = start;
		this.length = length;
		initPageInfo();
	}


	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public  void initPageInfo(){
		if (null == this.start|| this.start < 0){
			this.start=0;
		}
		if ( null == this.getLength() || this.length <=0 ){
			this.length=10;
		}
		if (null == this.pageIndex){
			this.pageIndex = this.start/this.length;
		}
	}
}
