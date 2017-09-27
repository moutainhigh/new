package com.huayu.cms.service;

import com.huayu.cms.dao.CmsContentDao;
import com.huayu.cms.domain.CmsContent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * desc：内容service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:11
 */
@Service
public class CmsContentService {

	@Autowired
	private CmsContentDao cmsContentDao;
		
	/***
	 * 查找一个
	 * @param cid Long
	 * @return CmsContent
	 */
	public CmsContent getOne(Long cid) {
		CmsContent entity = new CmsContent();
		entity.setCid(cid);
		entity.setIdName("cid");
		return cmsContentDao.getOne(entity);
	}
	
	/***
	 * 分页查询所有数据
	 * @param pageable Pageable
	 * @return Page
	 */
	public Page<CmsContent> get(CmsContent entity, Pageable pageable) {
		StringBuilder sb = new StringBuilder("select * from cms_content where mid=:mid");
		if(entity.getTitle()!=null)
			sb.append(" and locate(:title,title)>0");
		return cmsContentDao.get(sb.toString(),entity, pageable);
	}

	/***
	 * 查询所有数据
	 * @param entity
	 * @return
	 */
	public List<CmsContent> get(CmsContent entity) {
		StringBuilder sb = new StringBuilder("select * from cms_content where mid=:mid");
		if(entity.getTitle()!=null)
			sb.append(" and locate(:title,title)>0");
		return cmsContentDao.get(entity);
	}
	
	/***
	 * 增加
	 * @param entity CmsContent
	 * @return int
	 */
    @Transactional
	public int post(CmsContent entity) {
		Long cid = cmsContentDao.getKey("cms_content",new CmsContent());
        entity.setCid(cid);
		if(entity.getAllText()!=null)
			entity.setAllText(StringEscapeUtils.escapeHtml4(entity.getAllText()));
		entity.setCreateDate(new Date());
		entity.setUpdateDate(entity.getCreateDate());
		return cmsContentDao.post(entity);
	}
	
	/***
	 * 修改
	 * @param entity CmsContent
	 * @return int
	 */
	public int put(CmsContent entity) {
		if(entity.getAllText()!=null)
			entity.setAllText(StringEscapeUtils.escapeHtml4(entity.getAllText()));
		entity.setUpdateDate(new Date());
		return cmsContentDao.put(entity);
	}
	
	/***
	 * 删除
	 * @param cid Long
	 * @return int
	 */
	public int delete(Long cid) {
		CmsContent entity = new CmsContent();
		entity.setCid(cid);
		entity.setIdName("cid");
		return cmsContentDao.delete(entity);
	}
	
	//extend
	//-----------------------------------------------------------------------------------------------------------
	/***
	 * 增加或修改
	 * @param entity CmsContent
	 * @return int
	 */
	public int save(CmsContent entity) {
		if(entity.getCid()==null) {
			return this.post(entity);
		} else {
			return this.put(entity);
		}
	}
	/**
	 * 根据Cid 查询下一个
	 * @param cid
	 */
	public CmsContent getNextOne(String t, Long cid, Long mid) {
		CmsContent entity = new CmsContent();
		entity.setCid(cid);
		entity.setMid(mid);
		String sql=" SELECT * from cms_content cc WHERE cc.cid "+t+" :cid AND cc.mid=:mid    ORDER BY cid  "+(t.equals(">")?"ASC":"DESC")+"  LIMIT 0,1 ";
		return cmsContentDao.getOne(sql,entity);
	}
	
	
	
}
