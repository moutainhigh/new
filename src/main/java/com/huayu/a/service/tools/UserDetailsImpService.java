package com.huayu.a.service.tools;

import com.huayu.user.dao.UserDao;
import com.huayu.user.dao.UserQxResourceDao;
import com.huayu.user.dao.UserQxRoleDao;
import com.huayu.user.domain.User;
import com.huayu.user.domain.UserQxResource;
import com.huayu.user.domain.UserQxRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * desc：用户service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:48
 */
@Service("userDetailsImpService")
@Deprecated
public class UserDetailsImpService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserQxRoleDao userQxRoleDao;

	@Autowired
	private UserQxResourceDao userQxResourceDao;

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
        User entity = new User();
        entity.setUsername(username);
        User user = userDao.getOneForLogin(entity);
		if (user == null)
			throw new UsernameNotFoundException(this.messageSource.getMessage( "UserDetailsService.userNotFount", new Object[] { username }, null));
		List<UserQxRole> userQxRoleList = userQxRoleDao.getForGrant(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<String> dataAuthorities = new HashSet<>();
		Set<UserQxRole> ex_roles = user.getEx_roles();
		UserQxResource resource;
		for (UserQxRole role : userQxRoleList) {
			resource = new UserQxResource();
			resource.setRoleId(role.getRoleId());
			resource.setRegSystem(user.getRegSource());
			List<UserQxResource> accessResources = userQxResourceDao.getAccessResources(resource);
			accessResources.forEach(re->{
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(re.getMark());
				grantedAuthorities.add(grantedAuthority);
			});
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getMark());
			grantedAuthorities.add(grantedAuthority);
			ex_roles.add(role);
			if ("hr".equals(user.getRegSource())){
				Arrays.stream(StringUtils.split(role.getDataAuthority(), ",")).forEach(e->{
					dataAuthorities.add(e);
				});
				user.setDataAuthorities(dataAuthorities);
			}else if ("projectPlan".equals(user.getRegSource())){
                Arrays.stream(StringUtils.split(role.getDataAuthority(), ",")).forEach(e->{
                    dataAuthorities.add(e);
                });
                user.setDataAuthorities(dataAuthorities);
            }
		}
		user.setAuthorities(grantedAuthorities);
		user.setEx_roles(ex_roles);
		if (dataAuthorities.size()>0){
			StringBuilder query = new StringBuilder("'");
			Iterator<String> iterator = dataAuthorities.iterator();
			while (iterator.hasNext()){
				query.append(iterator.next()).append("|");
			}
			query.delete(query.length()-1,query.length()).append("'");
			user.setDataAuthoritiesRegexp(query.toString());//设置查询的所有权限
		}
		return user;
	}

}
