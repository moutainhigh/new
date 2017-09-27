package com.huayu.a.service.tools;

import com.huayu.user.dao.UserQxResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 描述：Spring Security 资源过滤
 * 
 * @author 刘咏
 * @version 1.1
 * 
 * @日期：2014-10-27
 */
public class URLFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(URLFilterInvocationSecurityMetadataSource.class);

	private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();

	// 权限集合
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

	@Autowired
	private UserQxResourceDao userQxResourceDao;


	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		if (request.getRequestURI().startsWith("/admin/index/default")){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (null!=authentication.getPrincipal() && "anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())){
				return SecurityConfig.createListFromCommaDelimitedString("access_login");
			}
		}
		Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				attrs = entry.getValue();
				break;
			}
		}
		log.debug("URL资源：" + request.getRequestURI() + " -> " + attrs);
		return attrs;
	}


	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	private Map<String, String> loadResource() {
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> list = this.userQxResourceDao.getURLResourceMapping();
		Iterator<Map<String, String>> it = list.iterator();
		while (it.hasNext()) {
			Map<String, String> rs = it.next();
			String resourcePath = rs.get("resourcePath");
			String authorityMark = rs.get("authorityMark");
			if (StringUtils.hasText(resourcePath)&&StringUtils.hasText(authorityMark)){
				if (map.containsKey(resourcePath)) {
					String mark = map.get(resourcePath);
					map.put(resourcePath, mark +","+  authorityMark);
				} else {
					map.put(resourcePath, authorityMark);
				}
			}
		}
		return map;
	}

	protected Map<RequestMatcher, Collection<ConfigAttribute>> bindRequestMap() {
		Map<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<>();
		Map<String, String> resMap = this.loadResource();
		for (Map.Entry<String, String> entry : resMap.entrySet()) {
			String key = entry.getKey();
			Collection<ConfigAttribute> atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue());
			map.put(new AntPathRequestMatcher(key), atts);
		}

		return map;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.requestMap = this.bindRequestMap();
		log.debug("资源权限列表" + this.requestMap);
	}

	public void refreshResourceMap() {
		this.requestMap = this.bindRequestMap();
	}

}
