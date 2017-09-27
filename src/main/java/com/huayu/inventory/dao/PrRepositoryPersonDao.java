package com.huayu.inventory.dao;

import com.huayu.inventory.domain.PrRepositoryPerson;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/13.
 */
@Repository
public class PrRepositoryPersonDao extends BasePageDao<PrRepositoryPerson,Integer>{

    public int postData(PrRepositoryPerson entity) {
        Long key = super.getKey("pr_repository_person",entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public List<PrRepositoryPerson> getAllPerson(PrRepositoryPerson person) {
        String sql = "select * from pr_repository_person where projectId=:projectId and type=:type";
        return super.get(sql,person);
    }

}
