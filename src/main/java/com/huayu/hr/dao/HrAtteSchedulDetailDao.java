package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteSchedulDetail;
import com.huayu.hr.web.args.HrAtteSchedulDetailArgs;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/6.
 */
@Repository
public class HrAtteSchedulDetailDao extends BasePageDao<HrAtteSchedulDetail,Integer>{

    public int addHrAtteSchedulDetail(HrAtteSchedulDetail entity){
        Long key = super.getKey("hr_atte_schedul_detail",entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public int deleteSchedulDetail(HrAtteSchedulDetail entity){
        String sql = "delete from hr_atte_schedul_detail where schedulId=:schedulId";
        return super.delete(sql,entity);
    }

    public List<HrAtteSchedulDetail> getSchedulDetailListData(HrAtteSchedulDetailArgs args) {
        StringBuilder sql =  new StringBuilder ("SELECT  e.empName,c.`name` companyName,d.`name` departmentName,sf.schName shiftName,s.empId,s.startDate,s.endDate,s.forced,s.cycle,sd.* ");
        sql.append(" FROM hr_atte_schedul_detail sd");
        sql.append(" INNER JOIN hr_atte_schedul s ON sd.schedulId = s.id INNER JOIN hr_atte_shift sf ON sf.id = sd.shiftId");
        sql.append(" INNER JOIN hr_employee e ON e.id = s.empId  INNER JOIN hr_company c ON c.id = e.company");
        sql.append(" INNER JOIN hr_department d ON d.id = e.department");
        sql.append(" WHERE e.company =:company order by sd.schedulId,sd.step");
        return super.get(sql.toString(),args);
    }

    public List<HrAtteSchedulDetail> getSchedulDetailList(HrAtteSchedulDetailArgs args) {
        StringBuilder sql =  new StringBuilder ("SELECT  sf.schName shiftName,sd.* ");
        sql.append(" FROM hr_atte_schedul_detail sd");
        sql.append(" INNER JOIN hr_atte_schedul s ON sd.schedulId = s.id INNER JOIN hr_atte_shift sf ON sf.id = sd.shiftId");
        sql.append(" INNER JOIN hr_employee e ON e.id = s.empId  INNER JOIN hr_company c ON c.id = e.company");
        sql.append(" INNER JOIN hr_department d ON d.id = e.department");
        sql.append(" WHERE e.empId =:empId order by sd.schedulId");
        return super.get(sql.toString(),args);
    }

}
