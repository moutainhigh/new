package com.huayu.hr.dao;

import com.huayu.hr.domain.HrEmployeeJob;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/7.
 */
@Repository
public class HrEmployeeJobDao extends BasePageDao<HrEmployeeJob,Integer>{

    public int post(HrEmployeeJob jobInfo) {
        Long long_Id = super.getKey("hr_employee_job", jobInfo);
        jobInfo.setId(long_Id.intValue());
        jobInfo.setIsDelete(0);
        return super.post(jobInfo);
    }

    public Long countByEmpOnGuard(HrEmployeeJob jobInfo){
        String sql = "select count(id) from hr_employee_job where onGuard=1 and job=:job and isDelete = 0";
        return super.getLong(sql,jobInfo);
    }

    public HrEmployeeJob getEmployeeJobInfo(HrEmployeeJob jobInfo) {
        StringBuilder stringBuilder = new StringBuilder( "select hej.*,c.name companyName,d.name departmentName,j.name jobName from hr_employee_job hej");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = hej.company");
        stringBuilder.append(" LEFT JOIN hr_department d ON d.id = hej.department");
        stringBuilder.append(" LEFT JOIN hr_job j ON j.id = hej.job");
        stringBuilder.append(" where hej.empId = :empId and hej.isDelete = 0 order by createtime desc limit 0,1");
        return super.getOne(stringBuilder.toString(),jobInfo);
    }

    public int updateEmployeeJobInfo(HrEmployeeJob jobInfo) {
        StringBuilder stringBuilder = new StringBuilder("update hr_employee_job set ");
        stringBuilder.append("empNo=:empNo,workType=:workType,jobLevel=:jobLevel,jobSequence=:jobSequence,");
        stringBuilder.append("startTime=:startTime,endTime=:endTime,recruitmentSource=:recruitmentSource,workAddress=:workAddress,turnFormalDate=:turnFormalDate,formalType=:formalType");
        stringBuilder.append(",isFormal=:isFormal,updatetime=:updatetime,updateUser=:updateUser");
        if (null!=jobInfo.getEmpGroup()){
            stringBuilder.append(",empGroup=:empGroup");
        }
        if (null!=jobInfo.getCompany()){
            stringBuilder.append(",company=:company");
        }
        if (null!=jobInfo.getDepartment()){
            stringBuilder.append(",department=:department");
        }
        if (null!=jobInfo.getJob()){
            stringBuilder.append(",job=:job");
        }
        if (null!=jobInfo.getDutyLevel()){
            stringBuilder.append(",dutyLevel=:dutyLevel");
        }
        stringBuilder.append(" where id=:id and empId=:empId and isDelete = 0");
        return super.put(stringBuilder.toString(),jobInfo);
    }

    public HrEmployeeJob getEmployeeJobAndCompInfo(HrEmployeeJob jobInfo) {
        String sql = "select hej.*, c.name companyName, j.name jobName,e.empName " +
                "FROM hr_employee_job hej " +
                "INNER JOIN hr_employee e ON e.id = hej.empId " +
                "INNER JOIN hr_company c ON c.id = hej.company " +
                "INNER JOIN hr_job j ON j.id = hej.job  " +
                "where hej.empId = :empId and hej.onGuard=1 and hej.isDelete = 0";
        return super.getOne(sql,jobInfo);
    }

    public int offGuard(HrEmployeeJob entity){
        StringBuilder stringBuilder = new StringBuilder("update hr_employee_job set onGuard=:onGuard,empGroup=:empGroup,endTime=:endTime,updatetime=:updatetime");
        stringBuilder.append(" where id=:id and empId=:empId and isDelete = 0");
        return super.put(stringBuilder.toString(),entity);
    }

    public int turnFormal(HrEmployeeJob entity){
        StringBuilder stringBuilder = new StringBuilder("update hr_employee_job set empGroup=:empGroup,isFormal=:isFormal,formalType=:formalType," +
                "turnFormalDate=:turnFormalDate,updatetime=:updatetime");
        stringBuilder.append(" where id=:id and empId=:empId and isDelete = 0");
        return super.put(stringBuilder.toString(),entity);
    }

    public Long checkEmpNoRepeat(HrEmployeeJob employeeJob) {
        String sql  = " select count(hej.id) from hr_employee_job hej where hej.empNo = :empNo and hej.isDelete = 0";
        return super.getLong(sql,employeeJob);
    }

    @Deprecated
    public List<HrEmployeeJob> getOutDutyEmpList(HrEmployeeJob employeeJob) {
        StringBuilder stringBuilder = new StringBuilder("SELECT e.id empId,e.empName FROM");
        stringBuilder.append(" (SELECT hej.empId FROM hr_employee_job hej WHERE hej.company =:company  AND hej.department =:department AND hej.isDelete = 0  GROUP BY hej.empId) a");
        stringBuilder.append(" INNER JOIN (SELECT COUNT(CASE WHEN hej.onGuard=1 THEN 1 ELSE NULL end) AS onGuard,id,hej.empId FROM hr_employee_job hej where hej.isDelete = 0 GROUP BY hej.empId HAVING onGuard=0) b ON a.empId=b.empId");
        stringBuilder.append(" INNER JOIN hr_employee e ON e.id = a.empId and e.isDelete = 0");
        return super.get(stringBuilder.toString(),employeeJob);
    }
}
