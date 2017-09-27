package com.huayu.user.dao;

import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.UserQxRoleResource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DengPeng on 2017/5/3.
 */
@Repository
public class UserQxRoleResourceDao extends BasePageDao<UserQxRoleResource, Serializable> {

    public int addRoleResource(UserQxRoleResource roleResource){
        return super.post(roleResource);
    }

    public int delRoleResource(UserQxRoleResource roleResource) {
        String sql = "delete from user_qx_role_resource where rid =:rid and roleId = :roleId";
        return super.delete(sql,roleResource);
    }

    public int[] addRoleResourceBatch(List<SqlParameterSource> sqlParameterSources) {
        String sql = "insert into user_qx_role_resource (rid,roleId) values (:rid,:roleId)";
       return super.batchUpdate(sql, sqlParameterSources.toArray(new SqlParameterSource[sqlParameterSources.size()]));
    }

    public int[] delRoleResourceBatch(List<SqlParameterSource> sqlParameterSources2) {
        String sql = "delete from user_qx_role_resource where rid =:rid and roleId = :roleId";
        return super.batchUpdate(sql, sqlParameterSources2.toArray(new SqlParameterSource[sqlParameterSources2.size()]));
    }
}
