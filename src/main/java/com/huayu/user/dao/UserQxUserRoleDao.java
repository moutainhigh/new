package com.huayu.user.dao;

import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.UserQxUserRole;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DengPeng on 2016/11/22.
 */
@Repository
public class UserQxUserRoleDao  extends BasePageDao<UserQxUserRole, Serializable> {


    public List<UserQxUserRole> getUserRole(UserQxUserRole userQxUserRole) {
        String sql = "select * from user_qx_user_role where username=:username";
        return super.get(sql,userQxUserRole);
    }

    public int deleteUserRole(UserQxUserRole userQxUserRole) {
        String sql = "delete from user_qx_user_role where username=:username";
        return super.delete(sql,userQxUserRole);
    }
}
