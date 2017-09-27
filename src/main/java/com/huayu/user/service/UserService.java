package com.huayu.user.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.Constants;
import com.huayu.common.ConstantsHolder;
import com.huayu.user.dao.UserDao;
import com.huayu.user.dao.UserQxUserRoleDao;
import com.huayu.user.web.args.UserArgs;
import com.huayu.user.domain.User;
import com.huayu.user.domain.UserQxUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * desc：用户service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:29
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserQxUserRoleDao userQxUserRoleDao;


	/**
	 * 获取用户
	 * @param username
	 * @return
	 */
	public User getOneForLogin(String username){
		User user = new User();
		user.setUsername(username);
		return userDao.getOneForLogin(user);
	}

	/***
	 * 单个对象
	 * @param userId Long
	 * @return User
     */
	public User getOne(Long userId, String reg) {
		User entity = new User();
		entity.setUserId(userId);
		entity.setRegSource(reg);
		return userDao.getOne(entity);
	}

	/***
	 * 列表
	 * @param entity userArgs
	 * @param pageable Pageable
     * @return Page
     */
	public Page<User> get(UserArgs entity, Pageable pageable) {
		entity.setUserStatus((short)3);
		StringBuilder sb = new StringBuilder("select * from user where  userStatus < :userStatus");
		if (!Constants.ADMIN.equals(ConstantsHolder.getContext().getCurrSystem())){
			sb.append(" and regSource = :regSource");
		}
		if (StringUtils.hasText(entity.getSystem())){
			sb.append(" and regSource = :system");
		}
		if (StringUtils.hasText(entity.getUsername())){
			sb.append(" and position(:username in username)");
		}
		if (StringUtils.hasText(entity.getName())){
			sb.append(" and position(:name in name)");
		}
		return userDao.get(sb.toString(),entity, pageable);
	}

	public Long countUserByName(String username){
		User entity = new User();
		entity.setUsername(username);
		entity.setUserStatus((short)2);
		return userDao.countUserByName(entity);
	}

	/***
	 * 增加
	 * @param entity User
	 * @return int 0 失败 1 成功
     */
	@Transactional
	private int addUserInfo(User entity) {
		long userId = userDao.getKey("user", new User());
		entity.setUserId(userId);
		String md5 = SpringSecurityUtil.md5(entity.getPassword(), String.valueOf(userId));
		entity.setPassword(md5);
		entity.setNick(entity.getName());
		entity.setAvatar("");
		entity.setIdcard("");
		entity.setEmail("");
		entity.setMobile("");
		entity.setUserType((short)2);
		entity.setBusinessType((short)1);
		entity.setUserStatus((short)2);
		entity.setCreateDate(new Date());
		entity.setLastLoginDate(new Date());
		entity.setLastLoginIp("");
		entity.setLoginTimes(0);
		entity.setErrorLoginTimes((short)0);
		entity.setOpenId("");
		entity.setYyId(0L);
		return userDao.post(entity);
	}

	@Transactional
	private int updateUserInfo(User user) {

		return userDao.updateUserInfo(user);
	}

	/***
	 * 修改
	 * @param entity User
	 * @return int
     */
	@Transactional
	public int put(User entity) {
		return userDao.put(entity);
	}

	/***
	 * 删除
	 * @param userId v
	 * @return int
     */
	@Transactional
	public int delete(Long userId,String reg) {
		User entity = new User();
		entity.setRegSource(reg);
		entity.setUserId(userId);
		entity.setIdName("userId");
		return userDao.delete(entity);
	}


	/**
	 * 用户注册
	 * @param user
	 * @return  0 失败 1 成功 2 用户名重复
	 */
	@Transactional
	public int userRregistration(User user) {
		if (this.countUserByName(user.getUsername())>0){
			return 2;
		}
		if (this.addUserInfo(user)<=0){
			return  0;
		}
		Integer[] roles = user.getRoles();
		if (null!=roles){
			UserQxUserRole userRole = new UserQxUserRole();
			userRole.setUsername(user.getUsername());
			for (int i = 0;i<roles.length;i++){
				userRole.setRoleId(roles[i]);
				if (userQxUserRoleDao.post(userRole)<=0){
					throw new BusinessException("添加角色失败");
				}
			}
		}
		return 1;
	}

	@Transactional
	public void updatePassword(String oldPassword, String password){
		User user = SpringSecurityUtil.getUser();
		String old = SpringSecurityUtil.md5(oldPassword, String.valueOf(user.getUserId()));
		if (!user.getPassword().equals(old)){
			throw new BusinessException("原始密码错误");
		}
		String md5 = SpringSecurityUtil.md5(password, String.valueOf(user.getUserId()));
		user.setPassword(md5);
		user.setRegSource(user.getRegSource());
		if(this.updateUserInfo(user)<=0){
			throw new BusinessException("修改用户信息失败");
		}
	}

	@Transactional
	@CacheEvict( value = "menu-cache",allEntries=true)
	public int userUpdate(User user) {
		User one = this.getOne(user.getUserId(),user.getRegSource());
		if(null==one){
			return 0;
		}
		if (!one.getPassword().equals(user.getPassword())){
			String md5 = SpringSecurityUtil.md5(user.getPassword(), String.valueOf(user.getUserId()));
			user.setPassword(md5);
		}
		if(this.updateUserInfo(user)<=0){
			throw new BusinessException("修改用户信息失败");
		}
		if (userQxUserRoleDao.deleteUserRole(new UserQxUserRole(user.getUsername()))<=0){
			throw new BusinessException("删除用户角色信息失败");
		}
		UserQxUserRole userRole = new UserQxUserRole();
		Integer[] roles = user.getRoles();
		userRole.setUsername(user.getUsername());
		for (int i = 0;i<roles.length;i++){
			userRole.setRoleId(roles[i]);
			if (userQxUserRoleDao.post(userRole)<=0){
				throw new BusinessException("添加用户角色信息失败");
			}
		}
		return 1;
	}

	@Transactional
	public int delUser(Long id,String reg) {
		User user = this.getOne(id,reg);
		if (null==user){
			return 0;
		}
		if (userQxUserRoleDao.deleteUserRole(new UserQxUserRole(user.getUsername()))<=0){
			throw new BusinessException("删除用户角色信息失败");
		}
		user.setRegSource(reg);
		user.setUserStatus((short)3);
		if (userDao.delUser(user)<=0){
			return 0;
		}
		return  1;
	}

	public User getUserOne(Long id, String reg) {
		User user = this.getOne(id, reg);
		if (null==user){
			return null;
		}
		UserQxUserRole userQxUserRole = new UserQxUserRole();
		userQxUserRole.setUsername(user.getUsername());
		List<UserQxUserRole> roleList = userQxUserRoleDao.getUserRole(userQxUserRole);
		Integer[] roles = new Integer[roleList.size()];
		for (int i = 0;i<roleList.size();i++){
			roles[i]= roleList.get(i).getRoleId();
		}
		user.setRoles(roles);
		return user;
	}


}
