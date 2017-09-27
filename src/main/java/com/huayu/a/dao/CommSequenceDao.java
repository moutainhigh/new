package com.huayu.a.dao;

import com.huayu.a.domain.CommSequence;
import com.huayu.common.BusinessException;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by DengPeng on 2017/3/7.
 */
@Repository
public class CommSequenceDao extends BasePageDao<CommSequence,String>{

    @Transactional
    public synchronized Long[] getKey(CommSequence sequence){
        Assert.hasText(sequence.getName(),"表名不能为空");
        Assert.notNull(sequence.getOffset(),"偏移ID不能为空");
        Long[] ids = new Long[2];
        String sql = "select id from comm_sequence WHERE name = :name";
        ids[0] =super.getLong(sql,sequence);
        sql = "UPDATE comm_sequence SET id = LAST_INSERT_ID(id + :offset) WHERE name = :name";
        if (super.put(sql,sequence)!=1){
            throw new BusinessException("获取ID失败");
        }
        sql = "select LAST_INSERT_ID()";
        ids[1] = super.getLong(sql,sequence);
        return ids;
    }


    @Transactional
    public Long getCurrKey(CommSequence sequence){
        String sql = "select id from comm_sequence WHERE name = :name";
        return  super.getLong(sql,sequence);
    }

    @Transactional
    public void updateKey(CommSequence sequence){
        String sql = "UPDATE comm_sequence SET id =:id WHERE name = :name";
        if (super.put(sql,sequence)!=1){
            throw new BusinessException("修改ID失败");
        }
    }
}
