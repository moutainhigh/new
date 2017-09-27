package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignWorktime;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/4.
 */
@Repository
public class HrSignWorktimeDao extends BasePageDao<HrSignWorktime, Serializable> {

    public HrSignWorktime getWorktimeByGroupname(HrSignWorktime h) {
        String sql = "select * from hr_sign_worktime where groupname = :groupname";
        return super.getOne(sql, h);
    }
}
