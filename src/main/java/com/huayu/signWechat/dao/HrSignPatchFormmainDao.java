package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignPatchFormmain;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */
@Repository
public class HrSignPatchFormmainDao extends BasePageDao<HrSignPatchFormmain, Serializable> {

    public int updateByFormmainIdAndStatus(HrSignPatchFormmain hrSignPatchFormmain) {
        String sql = "update hr_sign_patch_formmain set status = 2 where formmainId = :formmainId and status = :status";
        return super.put(sql, hrSignPatchFormmain);
    }
}
