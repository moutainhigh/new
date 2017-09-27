package com.huayu.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.common.tool.Authority;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.*;


@Table(name = "user")
public class User extends BaseDomain implements UserDetails, java.io.Serializable {

	@TableField
	private static final long serialVersionUID = -6240130095017274090L;
	private Long userId;
	private String username;
	private String name;
	private String password;
	private String nick;
	private String avatar;
	private String idcard;
	private String email;
	private String mobile;
	private Short userType;
	private Short businessType;
	private Short userStatus;//用户状态1未激活 2正常 3删除 4冻结 5监控
	private String regSource;
	@JSONField(format="yyyy-MM-dd")
	private Date createDate;
	private Date lastLoginDate;
	private String lastLoginIp;
	private Integer loginTimes;
	private Short errorLoginTimes;
	private String openId;
	private Long yyId;
	private Integer cid;
	private String roomNo;


	@TableField
	private String token;//token标识
	@TableField
	private Date tokenDate;//token标识时间

	public User() {
		this.dt = "desc";
		this.dtn = "userId";
		this.pageSize = 10;
	}


	@Override
	public String toString(String s) {
		StringBuilder sb = new StringBuilder("?");
		if(pageNo!=null && s.contains("pageNo"))
			sb.append("&pageNo="+pageNo);
		if(pageSize!=null && s.contains("pageSize"))
			sb.append("&pageSize="+pageSize);
		if(dt!=null && s.contains("dt"))
			sb.append("&dt="+dt);
		if(dtn!=null && s.contains("dtn"))
			sb.append("&dtn="+dtn);
		return sb.toString();
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = null != username?username.trim():username;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = null != name?name.trim():name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Short getUserType() {
		return this.userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public Short getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(Short businessType) {
		this.businessType = businessType;
	}

	public Short getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Short userStatus) {
		this.userStatus = userStatus;
	}

	public String getRegSource() {
		return this.regSource;
	}

	public void setRegSource(String regSource) {
		this.regSource = regSource;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Short getErrorLoginTimes() {
		return this.errorLoginTimes;
	}

	public void setErrorLoginTimes(Short errorLoginTimes) {
		this.errorLoginTimes = errorLoginTimes;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getYyId() {
		return this.yyId;
	}

	public void setYyId(Long yyId) {
		this.yyId = yyId;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenDate() {
		return tokenDate;
	}

	public void setTokenDate(Date tokenDate) {
		this.tokenDate = tokenDate;
	}

	// 账户没有过期(激活)
	@Override
	public boolean isAccountNonExpired() {
		return (userStatus != 1);
	}

	// 账户没有被锁定
	@Override
	public boolean isAccountNonLocked() {
		return (userStatus != 4);
	}

	// 证书没有过期
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 账户是否有效
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * 权限集合
	 */
	@TableField
	private Collection<GrantedAuthority> ex_authorities;
	/**
	 * 角色集合
	 */
	@TableField
	private Set<UserQxRole> ex_roles = new HashSet<>(0);


	public Set<UserQxRole> getEx_roles() {
		return ex_roles;
	}

	public void setEx_roles(Set<UserQxRole> ex_roles) {
		this.ex_roles = ex_roles;
	}

	// 权限集合
	public Collection<GrantedAuthority> getAuthorities() {
		return this.ex_authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.ex_authorities = authorities;
	}

	public boolean hasAuth(String authCode){
		boolean flag = false;
		if (StringUtils.isEmpty(authCode)){
			return flag;
		}else{
			Iterator<? extends GrantedAuthority> iterator = this.getAuthorities().iterator();
			while (!flag && iterator.hasNext()){
				if (iterator.next().getAuthority().equals(authCode.trim())){
					flag = true;
				}
			}
		}
		return flag;
	}

	public boolean hasAnyAuth(String ... authCodes){
		boolean flag = false;
		Iterator<String> it = Arrays.asList(authCodes).iterator();
		while (!flag && it.hasNext()){
			String code = it.next();
			if (StringUtils.isEmpty(code)){
				return flag;
			}
			Iterator<? extends GrantedAuthority> iterator = this.getAuthorities().iterator();
			while (!flag && iterator.hasNext()){
				if (iterator.next().getAuthority().equals(code.trim())){
					flag = true;
				}
			}
		}
		return flag;
	}

	@TableField
	private Integer[] roles;

	public Integer[] getRoles() {
		return roles;
	}

	public void setRoles(Integer[] roles) {
		this.roles = roles;
	}

	public boolean hasRole(Integer roleId){
		if (null == roles){
			return false;
		}
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleId)){
				return true;
			}
		}
		return false;
	}

	/**
	 * hr查询条件区分
	 */

	@TableField
	private Integer plateId;//当前板块

	@TableField
	private String companyName;//当前公司名字

	@TableField
	private Integer currCompanyId;//当前公司ID

	@TableField
	private String currCompanyCode;//公司编码

	@TableField
	private Set<String> dataAuthorities;//数据权限集合

	@TableField
	private Authority dataAuthorityMap;

	@TableField
	private String dataAuthorityRegexp;//当前公司权限代码

	@TableField
	private String dataAuthoritiesRegexp;//所有权限代码


	@TableField
	private Integer currDataId; //公用字段 表示当前所选的 一个项目 或者一个城市
	@TableField
	private String currDataValue; //公用字段 表示当前所选的 一个项目 或者一个城市

	public Integer getPlateId() {
		return plateId;
	}

	public void setPlateId(Integer plateId) {
		this.plateId = plateId;
	}

	public Set<String> getDataAuthorities() {
		return dataAuthorities;
	}

	public void setDataAuthorities(Set<String> dataAuthorities) {
		this.dataAuthorities = dataAuthorities;
	}

	public Authority getDataAuthorityMap() {
		return dataAuthorityMap;
	}

	public void setDataAuthorityMap(Authority dataAuthorityMap) {
		this.dataAuthorityMap = dataAuthorityMap;
	}

	public String getDataAuthorityRegexp() {
		return dataAuthorityRegexp;
	}

	public void setDataAuthorityRegexp(String dataAuthorityRegexp) {
		this.dataAuthorityRegexp = dataAuthorityRegexp;
	}

	public String getDataAuthoritiesRegexp() {
		return dataAuthoritiesRegexp;
	}

	public void setDataAuthoritiesRegexp(String dataAuthoritiesRegexp) {
		this.dataAuthoritiesRegexp = dataAuthoritiesRegexp;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCurrCompanyId() {
		return currCompanyId;
	}

	public void setCurrCompanyId(Integer currCompanyId) {
		this.currCompanyId = currCompanyId;
	}

	public String getCurrCompanyCode() {
		return currCompanyCode;
	}

	public void setCurrCompanyCode(String currCompanyCode) {
		this.currCompanyCode = currCompanyCode;
	}

	public Integer getCurrDataId() {
		return currDataId;
	}

	public void setCurrDataId(Integer currDataId) {
		this.currDataId = currDataId;
	}

	public String getCurrDataValue() {
		return currDataValue;
	}

	public void setCurrDataValue(String currDataValue) {
		this.currDataValue = currDataValue;
	}
}