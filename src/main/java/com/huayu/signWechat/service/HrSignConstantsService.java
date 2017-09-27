package com.huayu.signWechat.service;

import com.huayu.signWechat.dao.HrSignConstantsDao;
import com.huayu.signWechat.domain.HrSignConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/17.
 */
@Service
public class HrSignConstantsService {

    @Autowired
    private HrSignConstantsDao hrSignConstantsDao;

    public HrSignConstants getConstantsByKey(String key) {
        return hrSignConstantsDao.getConstantsByKey(key);
    }
}
