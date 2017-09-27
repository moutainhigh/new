package com.huayu.a.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.user.dao.UserDao;
import com.huayu.user.dao.UserQxResourceDao;
import com.huayu.user.dao.UserQxRoleDao;
import com.huayu.user.domain.User;
import com.huayu.user.domain.UserQxResource;
import com.huayu.user.domain.UserQxRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by dengpeng on 17-1-2.
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserQxRoleDao userQxRoleDao;

    @Autowired
    private UserQxResourceDao userQxResourceDao;

    @Autowired
    private MessageSource messageSource;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        User user = new User();
        user.setUsername(username);
        user = userDao.getOneForLogin(user);
        if (user == null)
            throw new UsernameNotFoundException(this.messageSource.getMessage( "UserDetailsService.userNotFount", new Object[] { username }, null));
        String p = SpringSecurityUtil.md5(password, String.valueOf(user.getUserId()));
        if (!p.equals(user.getPassword())) {
            throw new BadCredentialsException(messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", new Object[]{"Bad credentials"},null), null);
        }
        loadUserAuthorities(user);
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    /**
     * 加载用户角色 权限
     * @param user
     */
    public void loadUserAuthorities(User user){
        List<UserQxRole> userQxRoleList = userQxRoleDao.getForGrant(user.getUsername());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<String> dataAuthorities = new HashSet<>();
        Set<UserQxRole> ex_roles = user.getEx_roles();
        UserQxResource resource;
        Authority authority = new Authority();
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
                if (StringUtils.isNotBlank(role.getDataAuthority())){
                    Arrays.stream(StringUtils.split(role.getDataAuthority(), ",")).forEach(e->{
                        dataAuthorities.add(e);
                        authority.put(e.replace("^","").replace("$","").replace("!",""),e);
                    });
                }
            }else if ("projectPlan".equals(user.getRegSource()) || "inventory".equals(user.getRegSource()) || "material".equals(user.getRegSource())){
                if (StringUtils.isNotBlank(role.getDataAuthority())) {
                    Arrays.stream(StringUtils.split(role.getDataAuthority(), ",")).forEach(e -> {
                        dataAuthorities.add(e);
                    });
                }
            } else if ("projectcost".equals(user.getRegSource())) {
                if (StringUtils.isNotBlank(role.getDataAuthority())){
                    Arrays.stream(StringUtils.split(role.getDataAuthority(), ",")).forEach(e->{
                        dataAuthorities.add(e);
                        authority.put(e.replace("^","").replace("$","").replace("!",""),e);
                    });
                }
            }
        }
        user.setDataAuthorityMap(authority);
        user.setDataAuthorities(dataAuthorities);
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
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
