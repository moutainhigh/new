package com.huayu.bill.dao;

import com.huayu.bill.domain.BillDic;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Repository
public class BillDictDao extends BasePageDao<BillDic,Serializable> {


    @Override
    public int put(BillDic dict) {
        String sql = "update bill_dict set parentId=:parentId,value=:value,updatetime=:updatetime,updateuser=:updateuser where id=:id";
        return super.put(sql,dict);
    }
}
