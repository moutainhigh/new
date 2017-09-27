package com.huayu.hr.dao;

import com.huayu.hr.domain.HrTrainEmp;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/1/18.
 */
@Repository
public class HrTrainEmpDao extends BasePageDao<HrTrainEmp,Integer> {


    @Override
    public int post(HrTrainEmp emp) {
        Long longKey = super.getKey("hr_train_emp",emp);
        emp.setId(longKey.intValue());
        emp.setCreatetime(new Date());
        return super.post(emp);
    }

    public List<HrTrainEmp> getTrainEmpList(HrTrainEmp emp) {
        StringBuilder sql = new StringBuilder("select * from hr_train_emp where trainId=:trainId and status=:status");
        return super.get(sql.toString(),emp);
    }

    public int delOneTrainEmp(HrTrainEmp emp) {
        StringBuilder sql = new StringBuilder("update  hr_train_emp set status = :status where trainId=:trainId and id = :id ");
        return super.put(sql.toString(),emp);
    }

    public int putTrainEmp(HrTrainEmp trainEmp) {
        StringBuilder sql = new StringBuilder("update  hr_train_emp set studyTime=:studyTime,score=:score,cost=:cost,certificateNo=:certificateNo,certificateName=:certificateName,note=:note,updatetime=:updatetime");
        sql.append(" where id=:id and trainId=:trainId");
        return super.put(sql.toString(),trainEmp);
    }

    public Page<HrTrainEmp> getStatisticsEmpData(HrTrainEmp trainEmp, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select te.id,te.companyStr,te.departmentStr,te.empName,j.name jobStr,t.type,t.name trainName,t.teacherName,t.startTime,t.endTime,t.way,te.studyTime,te.cost,te.note");
        sql.append(" from hr_train_emp te");
        sql.append(" inner join  hr_company c on c.id = te.company");
        sql.append(" left join  hr_job j on te.job = j.id");
        sql.append(" inner join  hr_train t on te.trainId = t.id");
        sql.append(" where t.status=:status");
        if (null!=trainEmp.getCompany()){
            sql.append(" and te.company=:company");
        }
        if (null!=trainEmp.getDepartment()){
            sql.append(" and te.department=:department");
        }
        if (StringUtils.isNotBlank(trainEmp.getEmpName())){
            sql.append(" and te.empName=:empName");
        }
        if (null!=trainEmp.getStartTime()&&null!=trainEmp.getEndTime()){
            sql.append(" and t.startTime>:startTime and t.endTime<=:endTime");

        }
        User user = SpringSecurityUtil.getUser();
        sql.append(" and c.code REGEXP ");
        if (StringUtils.isBlank(user.getDataAuthorityRegexp())){
            sql.append("'NULL'");
        }else {
            sql.append(user.getDataAuthoritiesRegexp());
        }
        if (null!=trainEmp.getStartTime() && null!=trainEmp.getEndTime()){
            sql.append(" and t.startTime>=:startTime and t.endTime<=:endTime ");
        }
        return super.get(sql.toString(),trainEmp,pageable);
    }

    public List<HrTrainEmp> getAllEmpTrainData(HrTrainEmp trainEmp) {
        StringBuilder sql = new StringBuilder("select te.id,te.companyStr,te.studyTime,te.score,te.cost,te.note");
        sql.append(",t.code trainCode,t.name trainName,t.teacherName,t.startTime,t.endTime,t.timeCount,t.way,t.type,t.target,t.content");
        sql.append("");
        sql.append(" from hr_train_emp te");
        sql.append(" inner join  hr_company c on c.id = te.company");
        sql.append(" left join  hr_job j on te.job = j.id");
        sql.append(" inner join  hr_train t on te.trainId = t.id");
        sql.append(" where t.status=:status  and te.empId=:empId");
        User user = SpringSecurityUtil.getUser();
        sql.append(" and c.code REGEXP ");
        if (StringUtils.isBlank(user.getDataAuthoritiesRegexp())){
            sql.append("'NULL'");
        }else {
            sql.append(user.getDataAuthoritiesRegexp());
        }
        return super.get(sql.toString(),trainEmp);
    }

}
