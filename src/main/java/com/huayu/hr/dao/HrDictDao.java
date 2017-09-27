package com.huayu.hr.dao;

import com.huayu.hr.domain.HrDict;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/6.
 */
@Repository
public class HrDictDao extends BasePageDao<HrDict,Integer>{

    public List<HrDict> getChildDictByDictValue(HrDict dict) {
        String sql = "select d1.id,d1.dictName,d1.dictValue,d1.parentId from hr_dict d1 inner join hr_dict d2 on d1.parentId = d2.id where d2.dictValue = :dictValue ";
        return  super.get(sql,dict);
    }
}
