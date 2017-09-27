package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteHolidays;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/7.
 */
@Repository
public class HrAtteHolidaysDao extends BasePageDao<HrAtteHolidays,Integer> {


    public Page<HrAtteHolidays> getHolidayListData(HrAtteHolidays entity, Pageable pageable) {
        String sql = "select * from hr_atte_holidays where status=:status";
        return super.get(sql,entity,pageable);
    }

    public int addHoliday(HrAtteHolidays holiday) {
        Long key = super.getKey("hr_atte_holidays",holiday);
        holiday.setId(key.intValue());
        return super.post(holiday);
    }

    public int updateHoliday(HrAtteHolidays holiday) {
        StringBuilder sql = new StringBuilder("update  hr_atte_holidays set name=:name,startTime=:startTime,endTime=:endTime,duration=:duration,updateTime=:updateTime,updateUser=:updateUser");
        sql.append(" where id=:id");
        return super.put(sql.toString(),holiday);
    }

    public HrAtteHolidays getOneById(HrAtteHolidays holiday) {
        String sql = "select * from hr_atte_holidays where id=:id";
        return super.getOne(sql,holiday);
    }

    public int deleteHoliday(HrAtteHolidays holiday) {
        String sql = "update  hr_atte_holidays set status=:status,deleteTime=:deleteTime,deleteUser=:deleteUser where id =:id";
        return super.put(sql, holiday);
    }

    public List<HrAtteHolidays> getAllHoliday(HrAtteHolidays hrAtteHolidays) {
        String sql = "select * from hr_atte_holidays where status=:status";
        return super.get(sql, hrAtteHolidays);
    }
}
