package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignEgressMain;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */
@Repository
public class HrSignEgressMainDao extends BasePageDao<HrSignEgressMain, Serializable> {
    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources) {
        String sql = "insert into hr_sign_egress_main (`id`,`formmainid`,`username`,`company`,`department`,`position`,`telephone`,`status`) values(:id,:formmainid,:username,:company,:department,:position,:telephone,:status)";
        return super.batchUpdate(sql, sqlParameterSources);
    }
}
