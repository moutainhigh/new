package com.huayu.user.dao;

import com.huayu.user.domain.SysUserMapping;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/4/18.
 */
@Repository
public class SysUserMappingDao extends BasePageDao<SysUserMapping, Integer> {

    public SysUserMapping findUserMap(SysUserMapping mapping){
        StringBuilder sql  = new StringBuilder("select * from sys_user_mapping ");
        if (StringUtils.isNotBlank(mapping.getUserName())){
            sql.append(" where userName = :userName ");
            return super.getOne(sql.toString(),mapping);
        }
        if (StringUtils.isNotBlank(mapping.getSeeyon())){
            sql.append(" where seeyon = :seeyon ");
            return super.getOne(sql.toString(),mapping);
        }
        if (StringUtils.isNotBlank(mapping.getMingyuan())){
            sql.append(" where mingyuan = :mingyuan ");
            return super.getOne(sql.toString(),mapping);
        }
        return null;
    }

}
