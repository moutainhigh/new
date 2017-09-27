package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;

/**
 * Created by DengPeng on 2016/11/22.
 */
@Table(name = "user_qx_user_role")
public class UserQxUserRole extends BaseDomain implements Serializable {

    private String username;
    private Integer roleId;

    public UserQxUserRole() {

    }

    public UserQxUserRole(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
