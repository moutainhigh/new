package com.huayu.hr.dao;

import com.huayu.hr.domain.DataConversion;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/1/9.
 */
@Repository
public class DataConversionDao extends BasePageDao<DataConversion,Integer> {

    @Override
    public DataConversion getOne(DataConversion a) {
        String sql = "select * from data_conversion where pk_old=:pk_old and pk_type=:pk_type";
        return super.getOne(sql,a);
    }
}
