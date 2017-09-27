package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteOvertime;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/4/12.
 */
@Repository
public class HrAtteOvertimeDao extends BasePageDao<HrAtteOvertime,Integer> {

    public int addHrAtteOvertime(HrAtteOvertime entity){
        Long key = super.getKey("hr_atte_overtime", entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public int updateHrAtteOvertime(HrAtteOvertime entity){
        StringBuilder sql = new StringBuilder("update hr_atte_overtime set  startDate=:startDate,endDate=:endDate,type=:type,");
        sql.append("duration=:duration,billDate=:billDate,reason=:reason,updateTime=:updateTime,updateUser=:updateUser where id=:id and empId=:empId");
        return super.put(sql.toString(),entity);
    }

    public Page<HrAtteOvertime> getOvertimeListData(HrAtteOvertime entity, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select * from hr_atte_overtime");
        StringBuilder condition = new StringBuilder(" where");
        boolean hasCondition = false;
        if (null!= entity.getCompany()){
            hasCondition = true;
            condition.append(" and company=:company");
        }
        if (null!= entity.getDepartment()){
            hasCondition = true;
            condition.append(" and department=:department");
        }
        if (null!= entity.getEmpId()){
            hasCondition = true;
            condition.append(" and empId=:empId");
        }
        if (null!= entity.getType()){
            hasCondition = true;
            condition.append(" and type=:type");
        }
        if (null!= entity.getStartDate()&&null!= entity.getEndDate()){
            hasCondition = true;
            condition.append("and startDate>=:startDate and endDate<=:endDate");
        }
        if (hasCondition){
            int index = condition.indexOf("and");
            condition.delete(index,index+3);
            sql.append(condition);
        }
        return super.get(sql.toString(), entity,pageable);
    }

    public HrAtteOvertime getOvertimeOne(HrAtteOvertime entity) {
        String sql = "select * from hr_atte_overtime where id=:id";
        return super.getOne(sql,entity);
    }
}
