package com.huayu.hr.dao;

import com.huayu.hr.domain.HrHomeRelation;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/14.
 */
@Repository
public class HrHomeRelationDao extends BasePageDao<HrHomeRelation,Integer> {


    public int addHomeInfo(HrHomeRelation homeRelation) {
        Long longKey = super.getKey("hr_home_relation", homeRelation);
        homeRelation.setId(longKey.intValue());
        return super.post(homeRelation);
    }

    public List<HrHomeRelation> getHomeInfoAll(HrHomeRelation relation) {
        String sql  = "select id,empId,name,relationship,bithday,duty,job,phone from hr_home_relation where empId=:empId and status = 0";
        return super.get(sql,relation);
    }

    public int delHomeInfo(HrHomeRelation relation) {
        String sql = "update hr_home_relation set status=1,deletetime=:deletetime where empId=:empId and id=:id";
        return super.put(sql,relation);
    }

    public int updateHomeInfo(HrHomeRelation homeRelation) {
        String sql = "update hr_home_relation set name=:name,relationship=:relationship,bithday=:bithday,duty=:duty,job=:job,phone=:phone,updatetime=:updatetime where empId=:empId and id=:id";
        return super.put(sql,homeRelation);
    }
}
