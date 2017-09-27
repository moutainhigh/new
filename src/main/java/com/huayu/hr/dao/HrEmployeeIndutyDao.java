package com.huayu.hr.dao;

import com.huayu.hr.domain.HrEmployeeInduty;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/1/20.
 */
@Repository
public class HrEmployeeIndutyDao extends BasePageDao<HrEmployeeInduty,Integer> {

    @Override
    public int post(HrEmployeeInduty entity) {
        Long longKey = super.getKey("hr_employee_induty",entity);
        entity.setId(longKey.intValue());
        entity.setCreatetime(new Date());
        entity.setIsDelete(0);
        return super.post(entity);
    }

    public HrEmployeeInduty getIndutyOne(HrEmployeeInduty entity){
        StringBuilder stringBuilder = new StringBuilder("select * from hr_employee_induty where empId=:empId and empJobId=:empJobId and onGuard=1 and isDelete =0 ");
        return super.getOne(stringBuilder.toString(),entity);
    }

    public int updateIndutyInfoOne(HrEmployeeInduty entity){
        StringBuilder stringBuilder = new StringBuilder("update hr_employee_induty");
        stringBuilder.append(" set enddate=:enddate,onGuard=:onGuard,chgCause=:chgCause,chgType=:chgType,updatetime=:updatetime");
        stringBuilder.append(" where id=:id and empId=:empId and isDelete =0 ");
        return super.put(stringBuilder.toString(),entity);
    }

    public int updateIndutyBaseInfo(HrEmployeeInduty entity){
        StringBuilder stringBuilder = new StringBuilder("update hr_employee_induty");
        stringBuilder.append(" set job=:job,dutyLevel=:dutyLevel,jobSequence=:jobSequence,empGroup=:empGroup,updatetime=:updatetime");
        stringBuilder.append(" where id=:id and empId=:empId and isDelete =0 ");
        return super.put(stringBuilder.toString(),entity);
    }

    public List<HrEmployeeInduty> getPsnIndutyList(HrEmployeeInduty entity) {
        StringBuilder stringBuilder = new StringBuilder("select hei.*,c.name companyName,d.name departmentName,j.name jobName from hr_employee_induty hei ");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = hei.company");
        stringBuilder.append(" LEFT JOIN hr_department d ON d.id = hei.department");
        stringBuilder.append(" LEFT JOIN hr_job j ON j.id = hei.job");
        stringBuilder.append(" where hei.empId=:empId ");
        User user = SpringSecurityUtil.getUser();
        stringBuilder.append(" and c.code REGEXP ");
        if (StringUtils.isBlank(user.getDataAuthorityRegexp())){
            stringBuilder.append("'NULL'");
        } else {
            stringBuilder.append(user.getDataAuthorityRegexp());
        }
        stringBuilder.append(" and hei.isDelete =0  ORDER BY hei.begindate DESC");
        return super.get(stringBuilder.toString(),entity);
    }


}
