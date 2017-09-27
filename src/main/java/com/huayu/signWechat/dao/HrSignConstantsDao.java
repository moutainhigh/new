package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignConstants;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/17.
 */
@Repository
public class HrSignConstantsDao extends BasePageDao<HrSignConstants, Serializable> {

    public HrSignConstants getConstantsByKey(String key) {
        String sql = "select * from hr_sign_constants where key = '"+key+"' and status = 0";
        return super.getOne(sql, new HrSignConstants());
    }
}
