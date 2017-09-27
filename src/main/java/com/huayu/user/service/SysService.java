package com.huayu.user.service;

import com.huayu.common.BusinessException;
import com.huayu.user.dao.*;
import com.huayu.user.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class SysService {

	@Autowired
	private UserQxMenuDao userQxMenuDao;

	@Autowired
	private UserQxRoleDao userQxRoleDao;

	@Autowired
	private SysUserMappingDao userMappingDao;

	@Autowired
	private UserQxResourceDao userQxResourceDao;

	@Autowired
	private UserQxRoleResourceDao userQxRoleResourceDao;



	/****
	 * 组合用户权限菜单
	 * @param username String
	 * @return List
     */
	public List<UserQxMenu> getForMenu(String username) {
		List<UserQxMenu> sessionList = userQxMenuDao.getByUsername(username);
		Map<Integer,UserQxMenu> menuMap = new HashMap<>();
		sessionList.forEach(e->{
			menuMap.put(e.getMenuId(),e);
		});

		List<UserQxMenu> allList = new ArrayList<>();
		for(UserQxMenu node1 : sessionList){
			boolean mark = false;
			for(UserQxMenu node2 : sessionList){
				if(node1.getParentId()!=0 && node1.getParentId().equals(node2.getMenuId())){
					mark = true;
					if(node2.getList() == null){
						node2.setList(new ArrayList<>());
					}
					node2.getList().add(node1);
					break;
				}
			}
			if(!mark){
				allList.add(node1); 
			}
		}

		return allList;
	}

	@Cacheable(value = "menu-cache",key = "methodName+'_'+#username")
	public List<UserQxMenu> buildMenuTree(String username){
		List<UserQxMenu> sessionList = userQxMenuDao.getByUsername(username);
		Map<Integer,UserQxMenu> menuMap = new LinkedHashMap<>();
		sessionList.forEach(e->{
			menuMap.put(e.getMenuId(),e);
		});
		return getAllMenus(0,menuMap);
	}

	private List<UserQxMenu> getAllMenus(Integer parentId, Map<Integer,UserQxMenu> menuMap) {
		List<UserQxMenu> list = new ArrayList<>();
		for(UserQxMenu m : menuMap.values()) {
			if (!m.isContain()){
				if (m.getParentId().equals(parentId)){
					m.setContain(true);
					list.add(m);
				}
			}
		}
		if (!CollectionUtils.isEmpty(list)){
			for (UserQxMenu c : list){
				if (c.getIsParent()==1){
					List<UserQxMenu> child = getAllMenus(c.getMenuId(),menuMap);
					c.setList(child);
				}
			}
		}
		return list;
	}


	@Cacheable(value = "menu-cache",key = "methodName+'_'+#parentId")
	public List<UserQxMenu> getAllMenus(Integer parentId) {
		UserQxMenu m = new UserQxMenu();
		List<UserQxMenu> menuList = userQxMenuDao.getAllMenu(m);

		Map<Integer,UserQxMenu> menuMap = new LinkedHashMap<>();
		menuList.forEach(e->{
			menuMap.put(e.getMenuId(),e);
		});

		return getAllMenus(parentId,menuMap);
	}

	public UserQxRole getRoleById(UserQxRole userQxRole){
		return userQxRoleDao.getRoleOne(userQxRole);
	}

	public List<UserQxRole> getAllRole(String regSource){
		UserQxRole userQxRole = new UserQxRole();
		userQxRole.setRegSystem(regSource);
		return userQxRoleDao.getAllRoles(userQxRole);
	}

	/**
	 * 返回 key 所属系统
	 * value 对应的角色
	 * @return
	 * @param regSource
	 */
	public Map<String,List<UserQxRole>> getAllRoleWithGroup(String regSource){
		UserQxRole userQxRole = new UserQxRole();
		userQxRole.setStatus((short)1);
		userQxRole.setRegSystem(regSource);
		List<UserQxRole> roles = userQxRoleDao.getAllRoles(userQxRole);
		Map<String,List<UserQxRole>> map=new HashMap<>();
		for (int i=0,size = roles.size();i<size;i++){
			UserQxRole role = roles.get(i);
			List<UserQxRole> roleList = map.get(role.getRegSystem());
			if (null!=roleList){
				roleList.add(role);
			}else{
				roleList = new ArrayList<>();
				roleList.add(role);
				map.put(role.getRegSystem(),roleList);
			}
		}
		return map;
	}

	/**
	 * 组装菜单树形 并设置选中
	 * @param roleId
	 * @return
	 */
	public List<UserQxMenu> getAllMenuWithRoleId(Integer roleId) {
		UserQxMenu m = new UserQxMenu();
		m.setRoleId(roleId);
		List<UserQxMenu> list = userQxMenuDao.getAllMenuWithRoleId(m);
		Map<Integer,UserQxMenu> menuMap = new LinkedHashMap<>();
		list.forEach(e->{
			menuMap.put(e.getMenuId(),e);
		});
		return this.getAllMenus(0, menuMap);
	}

	/**
	 * 根据关联系统的用户名或者ticket查找对应的值
	 * @param seeyon
	 * @param mingyuan
	 * @return
	 */
	public SysUserMapping findUserMap(String userName, String seeyon, String mingyuan){
		SysUserMapping mapping = new SysUserMapping();
		mapping.setUserName(userName);
		mapping.setSeeyon(seeyon);
		mapping.setMingyuan(mingyuan);
		return userMappingDao.findUserMap(mapping);
	}



	/**
	 * 保存角色 菜单关联
	 * @param userQxMenus
	 */
	@Transactional
	@CacheEvict(value = "menu-cache",allEntries=true)
	public void saveRoleMenus(List<UserQxMenu> userQxMenus) {
		for (UserQxMenu rm :userQxMenus){
			if (rm.isChecked()){
				if (userQxMenuDao.addRoleMenus(rm)!=1){
					throw new BusinessException("添加权限失败");
				}
			}else{
				if (userQxMenuDao.removeRoleMenus(rm)!=1){
					throw new BusinessException("删除权限失败");
				}
			}
		}
	}


	/**
	 *
	 * @param menu
	 */
	@Transactional
	@CacheEvict(value = "menu-cache",allEntries=true)
	public void addMenus(UserQxMenu menu) {
		menu.setStatus((short)1);
		if (userQxMenuDao.addMenu(menu)!=1){
			throw new BusinessException("添加菜单失败");
		}
		if (0 != menu.getParentId()){
            UserQxMenu m = new UserQxMenu();
            m.setMenuId(menu.getParentId());
            m.setIsParent((short)1);
            if (userQxMenuDao.updateMenu2Parent(m)!=1){
                throw new BusinessException("修改父级菜单失败");
            }
        }
	}

	@Transactional
	@CacheEvict(value = "menu-cache",allEntries=true)
	public void updateMenus(UserQxMenu menu) {
		if (userQxMenuDao.updateMenu(menu)!=1){
			throw new BusinessException("修改菜单失败");
		}
	}

    @Transactional
	@CacheEvict(value = "menu-cache",allEntries=true)
    public void deleteMenus(UserQxMenu menu) {
        if (userQxMenuDao.deleteMenus(menu)!=1){
            throw new BusinessException("删除菜单失败");
        }
        if (null != menu.getParentId()){
            UserQxMenu m = new UserQxMenu();
            m.setMenuId(menu.getParentId());
            m.setIsParent((short)0);
            if (userQxMenuDao.updateMenu2Parent(m)!=1){
                throw new BusinessException("修改父级菜单失败");
            }
        }
    }

    @Transactional
	public void addRole(UserQxRole userRole) {
		if (userQxRoleDao.addRole(userRole)!=1){
			throw new BusinessException("添加角色失败");
		}
	}

	@Transactional
	public void updateRole(UserQxRole role) {
		if (userQxRoleDao.updateRole(role)!=1){
			throw new BusinessException("修改角色失败");
		}
	}


	public List<UserQxResource> getAllResources(String regSource) {
		UserQxResource resource = new UserQxResource();
		resource.setRegSystem(regSource);
		return userQxResourceDao.getAllResource(resource);
	}

	@Transactional
	@CacheEvict(value = "menu-cache",allEntries=true)
	public void addResource(UserQxResource resource) {
		if (userQxResourceDao.addResource(resource)!=1){
			throw new BusinessException("添加资源失败");
		}
	}

	@Transactional
	@CacheEvict(value = "menu-cache",allEntries=true)
	public void updateResource(UserQxResource resource) {
		if (userQxResourceDao.updateResource(resource)!=1){
			throw new BusinessException("修改资源失败");
		}
	}

	public UserQxResource getResourceById(UserQxResource resource) {

		return userQxResourceDao.getResourceById(resource);
	}


	public List<UserQxResource> getResourceByRoleMenu(Integer roleId, Integer menuId) {
		UserQxResource resource = new UserQxResource();
		resource.setRoleId(roleId);
		resource.setMenuId(menuId);
		return userQxResourceDao.getResourceByRoleMenu(resource);
	}

	@Transactional
	public void saveRoleResource(List<UserQxRoleResource> list) {
		ArrayList<SqlParameterSource> sqlParameterSources = new ArrayList<>();
		ArrayList<SqlParameterSource> sqlParameterSources2 = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			UserQxRoleResource roleResource = list.get(i);
			if (roleResource.isChecked()){
				sqlParameterSources.add(new BeanPropertySqlParameterSource(roleResource));
			}else{
				sqlParameterSources2.add(new BeanPropertySqlParameterSource(roleResource));
			}
		}
		if (sqlParameterSources.size()>0){
			int[] flags = userQxRoleResourceDao.addRoleResourceBatch(sqlParameterSources);
			for (int i=0;i<flags.length;i++) {
				if (flags[i] == 0) {
					throw new BusinessException("关联资源失败");
				}
			}
		}
		if (sqlParameterSources2.size()>0){
			int[] flags =userQxRoleResourceDao.delRoleResourceBatch(sqlParameterSources2);
			for (int i=0;i<flags.length;i++) {
				if (flags[i] == 0) {
					throw new BusinessException("取消关联资源失败");
				}
			}
		}
	}

}
