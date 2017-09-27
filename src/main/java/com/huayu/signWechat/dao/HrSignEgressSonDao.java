package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignEgressSon;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */
@Repository
public class HrSignEgressSonDao extends BasePageDao<HrSignEgressSon, Serializable> {
    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources1) {
        String sql = "insert into hr_sign_egress_son (id, formmainid, startTime,endTime,locationDetail,notes,status) values (:id, :formmainid, :startTime,:endTime,:locationDetail,:notes,:status)";
        return super.batchUpdate(sql, sqlParameterSources1);
    }
}
