package com.huayu.hr.dao;

import com.huayu.hr.domain.HrRemind;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/3/22.
 */
@Repository
public class HrRemindDao extends BasePageDao<HrRemind,Integer>{
    @Override
    public int post(HrRemind entity) {
        Long longKey = super.getKey("hr_remind",entity);
        entity.setId(longKey);
        return super.post(entity);
    }

    public void disableLastData(HrRemind remind){
        String sql  = "delete r from hr_remind r  where r.year=:year and r.month=:month and type=:type";
        super.put(sql, remind);
    }

    public int deleteData(HrRemind remind){
        return super.put("delete r from hr_remind r  where r.id=:id",remind);
    }

    public HrRemind getRemindDataOne(HrRemind remind) {
        StringBuilder sql = new StringBuilder("SELECT r.* FROM hr_remind r ");
        sql.append(" WHERE r.type=:type AND r.empId=:empId");
        return super.getOne(sql.toString(),remind);
    }

    public List<HrRemind> getRemindData(HrRemind remind) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT r.*,e.empName,c.name companyName FROM hr_remind r ");
        sql.append(" INNER JOIN hr_employee_job hej ON hej.empId = r.empId and hej.onGuard=1");
        sql.append(" INNER JOIN hr_company c ON hej.company = c.id");
        sql.append(" INNER join hr_employee e ON e.id = r.empId");
        sql.append(" WHERE r.type=:type AND c.code REGEXP ");
        sql.append(user.getDataAuthoritiesRegexp());
        return super.get(sql.toString(),remind);
    }

    public List<HrRemind> getRemindData4BirthOrTurnFormal(HrRemind remind) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT hc.contractCompany,c.name companyName,e.empName,d.name departmentName,e.managerGroup,hej.empNo,r.date");
        sql.append(",(year(NOW())-year(e.birthdate)-1)+(DATE_FORMAT(e.birthdate,'%m%d')<=DATE_FORMAT(NOW(),'%m%d')) AS age,e.joinCompDate,hej.turnFormalDate");
        sql.append(",e.empName,e.sex,hj.name jobName,hej.isFormal,e.idCard,hej.dutyLevel");
        sql.append(" FROM hr_remind r ");
        sql.append(" INNER join hr_employee e ON e.id = r.empId");
        sql.append(" INNER JOIN hr_employee_job hej ON hej.id = e.lastEmpJobId and hej.onGuard=1");
        sql.append(" INNER JOIN hr_company c ON hej.company  = c.id");
        sql.append(" INNER JOIN hr_department d ON hej.department = d.id");
        sql.append(" LEFT JOIN hr_contract hc ON hc.empId= r.empId and hc.status=1");
        sql.append(" LEFT join hr_job hj ON hj.id = hej.job");
        sql.append(" WHERE r.type=:type AND c.code REGEXP ");
        sql.append(user.getDataAuthoritiesRegexp());
        return super.get(sql.toString(),remind);
    }

    public List<HrRemind> getRemindData4Contract(HrRemind remind) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT e.empName,e.sex,c.name companyName,d.name departmentName,hj.name jobName");
        sql.append(",e.joinCompDate,e.idCard,(year(NOW())-year(e.birthdate)-1)+(DATE_FORMAT(e.birthdate,'%m%d')<=DATE_FORMAT(NOW(),'%m%d')) AS age");
        sql.append(",hc.periodCount,hc.contractCompany,hc.contractStartTime,hc.contractEndTime");
        sql.append(" FROM hr_remind r ");
        sql.append(" INNER join hr_employee e ON e.id = r.empId");
        sql.append(" INNER JOIN hr_employee_job hej ON hej.id = e.lastEmpJobId and hej.onGuard=1");
        sql.append(" INNER JOIN hr_company c ON hej.company  = c.id");
        sql.append(" INNER JOIN hr_department d ON hej.department = d.id");
        sql.append(" INNER JOIN hr_contract hc ON hc.empId = r.empId and hc.status=1");
        sql.append(" LEFT join hr_job hj ON hj.id = hej.job");
        sql.append(" WHERE r.type=:type AND c.code REGEXP ");
        sql.append(user.getDataAuthoritiesRegexp());
        return super.get(sql.toString(),remind);
    }
}
