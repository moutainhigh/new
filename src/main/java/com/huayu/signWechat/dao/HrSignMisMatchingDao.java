package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignMismatching;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/7/19.
 */
@Repository
public class HrSignMisMatchingDao extends BasePageDao<HrSignMismatching, Integer> {

    public HrSignMismatching getMisUserByUserid(HrSignMismatching hrSignMismatching) {
        String sql = "select * from hr_sign_mismatching where userid=:userid";
        return super.getOne(sql, hrSignMismatching);
    }

    public int postData(HrSignMismatching hrSignMismatching) {
        Long key = super.getKey("hr_sign_mismatching", new HrSignMismatching());
        hrSignMismatching.setId(key.intValue());
        return super.post(hrSignMismatching);
    }
}
