package com.huayu.user.dao;

import com.huayu.common.Constants;
import com.huayu.user.domain.UserQxRole;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * desc：用户_权限Dao
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:22
 */
@Repository
public class UserQxRoleDao extends BasePageDao<UserQxRole, Serializable> {

	/**
	 * 根据用户名获取到用户的权限并封装成GrantedAuthority集合
	 * @param username String
	 */
	public List<UserQxRole> getForGrant(String username){
		UserQxRole entity = new UserQxRole();
		entity.setName(username);
		String sql = "SELECT r.* FROM user_qx_role r JOIN user_qx_user_role ur ON r.roleId = ur.roleId AND ur.username=:name";
		return get(sql, entity);
	}

	public List<UserQxRole> getAllRoles(UserQxRole userQxRole) {
		StringBuilder sql = new StringBuilder("SELECT * FROM user_qx_role  where 1=1");
		if (!Constants.ADMIN.equals(userQxRole.getRegSystem())){
			sql.append(" and regSystem =:regSystem");
		}
		sql.append(" order by regSystem");
		return get(sql.toString(), userQxRole);
	}

	public UserQxRole getRoleOne(UserQxRole entity) {
		StringBuilder sql = new StringBuilder("SELECT * FROM user_qx_role  where  roleId=:roleId");
		if (!Constants.ADMIN.equals(entity.getRegSystem())){
			sql.append(" and regSystem =:regSystem");
		}
		if (null!=entity.getStatus()){
			sql.append(" and status = :status");
		}
		return super.getOne(sql.toString(), entity);
	}

	public int addRole(UserQxRole userRole) {
		Long key = super.getKey("user_qx_role",userRole);
		userRole.setRoleId(key.intValue());
		return super.post(userRole);
	}

	public int updateRole(UserQxRole role) {
		String sql = "update user_qx_role set name=:name,status=:status,mark=:mark,dataAuthority=:dataAuthority,note=:note where roleId=:roleId and regSystem=:regSystem";
		return super.put(sql,role);
	}
}
