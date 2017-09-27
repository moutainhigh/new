package com.huayu.cms.dao;

import java.io.Serializable;

import com.huayu.cms.domain.CmsContent;
import org.springframework.stereotype.Repository;

import com.ly.dao.base.BasePageDao;

/**
 * desc：内容Dao
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:10
 */
@Repository
public class CmsContentDao extends BasePageDao<CmsContent, Serializable> {

	/***
	 * 修改
	 * @param entity CmsContent
	 * @return int
	 */
	public int put(CmsContent entity) {
		String sql = "UPDATE cms_content SET title=:title, imgUrl=:imgUrl, shortText=:shortText, allText=:allText,sort=:sort,f1=:f1,f2=:f2,f3=:f3,"
				+ " updateDate=:updateDate,mid=:mid WHERE cid=:cid";
		return super.put(sql, entity);
	}

}
