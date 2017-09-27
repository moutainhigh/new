package com.huayu.hr.dao;

import com.huayu.hr.domain.HrEmployeeOutduty;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/2/22.
 */
@Repository
public class HrEmployeeOutdutyDao extends BasePageDao<HrEmployeeOutduty,Integer>{

    @Override
    public int post(HrEmployeeOutduty entity) {
        Long longKey = super.getKey("hr_employee_outduty",entity);
        entity.setId(longKey.intValue());
        entity.setIsDelete(0);
        return super.post(entity);
    }

    public List<HrEmployeeOutduty> getPsnOutdutyList(HrEmployeeOutduty entity) {
        StringBuilder stringBuilder = new StringBuilder("select heo.*,c.name companyBeforeName,d.name deptBeforeName,j.name jobBeforeName,c2. NAME companyAfterName,d2. NAME deptAfterName");
        stringBuilder.append(" from hr_employee_outduty heo ");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = heo.companyBefore");
        stringBuilder.append(" INNER JOIN hr_department d ON d.id = heo.deptBefore");
        stringBuilder.append(" LEFT JOIN hr_company c2 ON c2.id = heo.companyAfter");
        stringBuilder.append(" LEFT JOIN hr_department d2 ON d2.id = heo.deptAfter");
        stringBuilder.append(" LEFT JOIN hr_job j ON j.id = heo.jobBefore");
        stringBuilder.append(" where heo.empId=:empId and heo.companyBefore=:companyBefore and heo.isDelete = 0 ");
        return super.get(stringBuilder.toString(),entity);
    }
}
