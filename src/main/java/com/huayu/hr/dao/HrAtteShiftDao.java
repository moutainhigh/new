package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteShift;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/5.
 */
@Repository
public class HrAtteShiftDao extends BasePageDao<HrAtteShift,Integer>{

    public int addHrAtteShift(HrAtteShift shift){
        Long key = super.getKey("hr_atte_shift",shift);
        shift.setId(key.intValue());
        return super.post(shift);
    }

    public Page<HrAtteShift> getShiftListData(HrAtteShift shift, Pageable pageable){
        String sql = "select * from hr_atte_shift";
        return super.get(sql,shift,pageable);
    }

    public List<HrAtteShift> getShiftListData(HrAtteShift shift){
        String sql = "select * from hr_atte_shift";
        return super.get(sql,shift);
    }

    public HrAtteShift getOneById(HrAtteShift shift){
        String sql = "select * from hr_atte_shift where id=:id";
        return super.getOne(sql,shift);
    }

    public int updateHrAtteShift(HrAtteShift shift) {
        StringBuilder sql = new StringBuilder("update hr_atte_shift  set schName=:schName,startTime=:startTime,endTime=:endTime,lateMinutes=:lateMinutes,");
        sql.append("earlyMinutes=:earlyMinutes,mustCheckIn=:mustCheckIn,mustCheckOut=:mustCheckOut,checkInTime1=:checkInTime1,checkInTime2=:checkInTime2,");
        sql.append("checkOutTime1=:checkOutTime1,checkOutTime2=:checkOutTime2,workDay=:workDay,workTime=:workTime,updateTime=:updateTime,updateUser=:updateUser where id=:id");
        return super.put(sql.toString(),shift);
    }
}
