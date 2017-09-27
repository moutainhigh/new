package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignLeaveMain;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */
@Repository
public class HrSignLeaveMainDao extends BasePageDao<HrSignLeaveMain, Serializable> {
    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources) {
        String sql = "insert into hr_sign_leave_main (`id`,`formmainid`,`company`,`department`,`position`,`username`,`telephone`,`reason`,`status`)" +
                " values(:id,:formmainid,:company,:department,:position,:username,:telephone,:reason,:status)";
        return super.batchUpdate(sql, sqlParameterSources);
    }
}
