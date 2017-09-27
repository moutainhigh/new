package com.huayu.user.dao;

import com.huayu.common.Constants;
import com.huayu.user.domain.UserQxResource;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc：用户_菜单资源Dao
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:18
 */
@Repository
public class UserQxResourceDao extends BasePageDao<UserQxResource, Serializable> {


	/***
	 * 根据用户名获取到用户的资源并封装成user_qx_resource集合
	 * @return List
     */
	public List<Map<String,String>> getURLResourceMapping(){
		List<Map<String,String>> list = new ArrayList<>();
		String sql = "select data,CONCAT('access_resource',rid) mark from user_qx_resource where status=1 order by data desc";
		List<UserQxResource> result = get(sql.toString(), new UserQxResource());
        for(UserQxResource o : result) {
            Map<String,String> map = new HashMap<>();
            map.put("resourcePath", o.getData());
            map.put("authorityMark", o.getMark());
            list.add(map);
        }
		return list;
	}

	public List<UserQxResource> getAllResource(UserQxResource resource){
		StringBuilder sql = new StringBuilder("select * from user_qx_resource where status !=-1");
		if (!Constants.ADMIN.equals(resource.getRegSystem())){
			sql.append(" and regSystem=:regSystem");
		}
		return super.get(sql.toString(),resource);
	}

	public int addResource(UserQxResource resource){
		Long key = super.getKey("user_qx_resource",resource);
		resource.setRid(key.intValue());
		return super.post(resource);
	}

	public int updateResource(UserQxResource resource) {
		String sql = "update user_qx_resource set name=:name,type=:type,data=:data,description=:description,status=:status,menuId=:menuId,regSystem=:regSystem where rid=:rid";
		return super.put(sql,resource);
	}

	public UserQxResource getResourceById(UserQxResource resource) {
		String sql = "select re.* from user_qx_resource re where re.rid=:rid and re.regSystem=:regSystem and re.status !=-1";
		return super.getOne(sql,resource);
	}

	public List<UserQxResource> getResourceByRoleMenu(UserQxResource roleResource) {
		String sql = "SELECT re.*,CASE WHEN rr.roleId IS NOT NULL THEN 1 ELSE 0 END AS checked" +
				" FROM user_qx_resource re" +
				" INNER JOIN (SELECT r.regSystem,r.roleId FROM user_qx_role r WHERE r.roleId = :roleId ) t ON t.regSystem = re.regSystem "+
				" LEFT JOIN user_qx_role_resource rr ON re.rid = rr.rid and rr.roleId=t.roleId" +
				" WHERE re.status=1";
		if (null !=roleResource.getMenuId()){
			sql += " AND re.menuId = :menuId";
		}
		sql+=" order by re.data desc";
		return super.get(sql,roleResource);
	}

	public List<UserQxResource> getAccessResources(UserQxResource resource) {
		String sql = "SELECT CONCAT('access_resource',re.rid) mark from user_qx_resource re LEFT JOIN user_qx_role_resource rr ON re.rid = rr.rid where rr.roleId=:roleId AND re.regSystem = :regSystem  and re.status=1";
		return super.get(sql,resource);
	}
}
