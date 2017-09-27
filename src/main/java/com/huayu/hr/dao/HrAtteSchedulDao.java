package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteSchedul;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/5.
 */
@Repository
public class HrAtteSchedulDao extends BasePageDao<HrAtteSchedul,Integer>{

    public int addSchedul(HrAtteSchedul entity){
        Long key = super.getKey("hr_atte_schedul",entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public int deleteSchedulAndDetail(HrAtteSchedul entity){
        String sql = "DELETE s,sd FROM hr_atte_schedul s INNER JOIN hr_atte_schedul_detail sd ON s.id=sd.schedulId WHERE s.empId=:empId AND s.id=:id";
        return super.delete(sql,entity);
    }

    public Long countSchedul(HrAtteSchedul entity){
        StringBuilder sql = new StringBuilder("SELECT COUNT(s.id) FROM hr_atte_schedul s WHERE  s.empId=:empId");
        sql.append(" and (").append(" (s.startDate>=:startDate  and s.endDate<=:endDate)");
        sql.append(" or (s.startDate <=:startDate  and s.endDate>=:endDate)");
        sql.append(" or (s.startDate <=:startDate  and s.endDate>=:startDate and s.endDate<=:endDate)");
        sql.append(" or (s.startDate >=:startDate and s.startDate <=:endDate  and s.endDate>=:endDate) ").append(")");
        return super.getLong(sql.toString(),entity);
    }

    public List<HrAtteSchedul> countSchedul(HrAtteSchedul entity, List<Integer> empIds){
        StringBuilder sql = new StringBuilder("SELECT s.empId,e.empName FROM hr_atte_schedul s INNER JOIN hr_employee e ON e.id = s.empId WHERE ");
        sql.append(" s.empId in (").append(StringUtils.collectionToDelimitedString(empIds,",")).append(")");
        sql.append(" and (").append(" (s.startDate>=:startDate  and s.endDate<=:endDate)");
        sql.append(" or (s.startDate <=:startDate  and s.endDate>=:endDate)");
        sql.append(" or (s.startDate <=:startDate  and s.endDate>=:startDate and s.endDate<=:endDate)");
        sql.append(" or (s.startDate >=:startDate and s.startDate <=:endDate  and s.endDate>=:endDate) ").append(")");
        return super.get(sql.toString(),entity);
    }

    public List<HrAtteSchedul> getSchedules(HrAtteSchedul entity){
        String sql = "SELECT COUNT(s.id) FROM hr_atte_schedul s WHERE  empId=:empId ";
        return super.get(sql,entity);
    }


}
