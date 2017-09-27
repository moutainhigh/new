package com.huayu.user.dao;

import com.huayu.user.domain.UserQxMenu;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * desc：用户_权限菜单Dao
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:17
 */
@Repository
public class UserQxMenuDao extends BasePageDao<UserQxMenu, Serializable> {

	/**
	 * 先根据用户名获取到user_qx_menu集合
	 * @param username String
	 * @return List
	 */
	public List<UserQxMenu> getByUsername(String username) {
		UserQxMenu entity = new UserQxMenu();
		entity.setName(username);
		String sql = "SELECT * FROM user_qx_menu WHERE menuId IN(SELECT DISTINCT menuId FROM user_qx_role_menu rm JOIN user_qx_user_role ur ON rm.roleId = ur.roleId JOIN user u ON u.username = ur.username AND u.username=:name) AND `status` =1 order by sort ";
		return super.get(sql, entity);
	}

	public List<UserQxMenu> getAllMenu(UserQxMenu userQxMenu) {
		StringBuilder sql = new StringBuilder("SELECT * FROM user_qx_menu where status!=-1 ");
		if (null!=userQxMenu.getParentId()){
			sql.append(" and parentId=:parentId");
		}
		sql.append(" order by menuId");
		return super.get(sql.toString(),userQxMenu);
	}

	public List<UserQxMenu> getAllMenuWithRoleId(UserQxMenu userQxMenu) {
		String sql  = "SELECT CASE WHEN rm.roleId IS NOT NULL THEN 1 ELSE 0 END checked,rm.roleId,m.* FROM user_qx_menu m" +
				" INNER JOIN (SELECT r.regSystem,r.roleId FROM user_qx_role r WHERE r.roleId = :roleId ) t ON t.regSystem = m.regSystem "+
				" LEFT JOIN user_qx_role_menu rm ON m.menuId = rm.menuId and rm.roleId = t.roleId" +
				" where m.status=1  order by sort";
		return super.get(sql.toString(),userQxMenu);
	}

	public int addRoleMenus(UserQxMenu rm) {
		String sql = "insert user_qx_role_menu (roleId,menuId) values (:roleId,:menuId)";
		return super.post(sql,rm);
	}

	public int removeRoleMenus(UserQxMenu rm) {
		String sql = "delete from user_qx_role_menu where roleId=:roleId and menuId=:menuId";
		return super.delete(sql,rm);
	}

    public int updateMenu2Parent(UserQxMenu menu) {
        String sql = "update user_qx_menu set isParent=:isParent where menuId=:menuId ";
        return super.put(sql,menu);
    }

	public int updateMenu(UserQxMenu menu) {
		String sql = "update user_qx_menu set name=:name,regSystem=:regSystem,url=:url,icon=:icon,status=:status,sort=:sort where menuId=:menuId and parentId=:parentId";
		return super.put(sql,menu);
	}

	public int addMenu(UserQxMenu menu) {
		String sql = "INSERT INTO user_qx_menu (menuId,parentId,isParent,name,regSystem,url,icon,sort,status) VALUES (:menuId,:parentId,0,:name,:regSystem,:url,:icon,:sort,:status)";
		return super.put(sql,menu);
	}

    public int deleteMenus(UserQxMenu menu) {
        String sql = "update user_qx_menu set status=-1 where menuId=:menuId";
        return super.put(sql,menu);
    }
}
