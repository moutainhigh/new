package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteTravel;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/4/12.
 */
@Repository
public class HrAtteTravelDao extends BasePageDao<HrAtteTravel,Integer>{

    public int addHrAtteTravel(HrAtteTravel travel){
        Long key = super.getKey("hr_atte_travel", travel);
        travel.setId(key.intValue());
        return super.post(travel);
    }

    public int updateHrAtteTravel(HrAtteTravel travel){
        StringBuilder sql = new StringBuilder("update hr_atte_travel set  startDate=:startDate,endDate=:endDate,addr=:addr,travelType=:travelType,");
        sql.append("duration=:duration,billDate=:billDate,reason=:reason,updateTime=:updateTime,updateUser=:updateUser where id=:id and empId=:empId");
        return super.put(sql.toString(),travel);
    }

    public Page<HrAtteTravel> getTravelListData(HrAtteTravel travel, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select * from hr_atte_travel");
        StringBuilder condition = new StringBuilder(" where");
        boolean hasCondition = false;
        if (null!=travel.getCompany()){
            hasCondition = true;
            condition.append(" and company=:company");
        }
        if (null!=travel.getDepartment()){
            hasCondition = true;
            condition.append(" and department=:department");
        }
        if (null!=travel.getEmpId()){
            hasCondition = true;
            condition.append(" and empId=:empId");
        }
        if (null!=travel.getTravelType()){
            hasCondition = true;
            condition.append(" and travelType=:travelType");
        }
        if (null!=travel.getStartDate()&&null!=travel.getEndDate()){
            hasCondition = true;
            condition.append("and startDate>=:startDate and endDate<=:endDate");
        }
        if (hasCondition){
            int index = condition.indexOf("and");
            condition.delete(index,index+3);
            sql.append(condition);
        }
        return super.get(sql.toString(),travel,pageable);
    }

    public HrAtteTravel getTravelOne(HrAtteTravel travel) {
        String sql = "select * from hr_atte_travel where id=:id";
        return super.getOne(sql,travel);
    }
}
