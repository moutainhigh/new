package com.huayu.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.huayu.cms.dao.CmsMenuDao;
import com.huayu.cms.domain.CmsMenu;

/**
 * desc：菜单service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:13
 */
@Service
public class CmsMenuService {

	@Autowired
	private CmsMenuDao cmsMenuDao;

	/***
	 * 根据菜单编号查找一个
	 * @param mid Long
	 * @return CmsMenu
     */
	public CmsMenu getOne(Long mid) {
		CmsMenu entity = new CmsMenu();
		entity.setMid(mid);
		entity.setIdName("mid");
		return cmsMenuDao.getOne(entity);
	}

	/***
	 * 分页查询所有数据
	 * @param pageable Pageable
	 * @return Page
     */
	public Page<CmsMenu> get(Pageable pageable) {
		return cmsMenuDao.get(new CmsMenu(), pageable);
	}

	/***
	 * 增加
	 * @param entity CmsMenu
	 * @return int
     */
	public int post(CmsMenu entity) {
		return cmsMenuDao.post(entity);
	}

	/***
	 * 修改
	 * @param entity CmsMenu
	 * @return int
     */
	public int put(CmsMenu entity) {
		entity.setIdName("mid");
		return cmsMenuDao.put(entity);
	}

	/***
	 * 删除
	 * @param mid Long
	 * @return int
     */
	public int delete(Long mid) {
		CmsMenu entity = new CmsMenu();
		entity.setMid(mid);
		entity.setIdName("mid");
		return cmsMenuDao.delete(entity);
	}

}
