package com.huayu.user.dao;

import com.huayu.common.Constants;
import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * desc：用户Dao
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:14
 */
@Repository
public class UserDao extends BasePageDao<User, Serializable> {

	private static final Logger log = LoggerFactory.getLogger(UserDao.class);

	/***
	 * 用户登录
	 * 
	 * @param entity User
	 * @return User
	 */
	public User getOneForLogin(User entity) {
		String sql = "select * from user where username=:username or mobile=:username or email=:username ";
		try {
            entity = super.getOne(sql, entity);
			return entity;
		} catch (RuntimeException re) {
			log.error("用户登录.", re);
			throw re;
		}
	}

	@Override
	public User getOne(User entity) {
		StringBuilder sql  = new StringBuilder("select * from user where userId=:userId");
		if (!Constants.ADMIN.equals(entity.getRegSource())){
			sql.append(" and regSource=:regSource");
		}
		return super.getOne(sql.toString(),entity);
	}

	@Override
	public int put(User entity) {
		String sql  = "update from user set name=:name where userId=:userId and regSource=:regSource";
		return super.put(sql,entity);
	}

	@Override
	public int delete(User entity) {
		String sql  = "delete from user where userId=:userId and regSource=:regSource";
		return super.delete(sql,entity);
	}

	public Long countUserByName(User entity) {
		String sql  = "select count(0) from user where username=:username and userStatus=:userStatus";
		return super.getLong(sql,entity);
	}

	public int delUser(User user) {
		user.setUsername(user.getUsername()+"_"+user.getUserId()+"_delete");
		String sql  = "update user set username=:username,userStatus=:userStatus where userId=:userId  and regSource=:regSource";
		return super.put(sql,user);
	}

    public int updateUserInfo(User user) {
		String sql  = "update user set name=:name,password=:password where userId=:userId  and regSource=:regSource";
		return super.put(sql,user);
    }



}
