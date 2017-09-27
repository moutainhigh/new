package com.huayu.hr.dao;

import com.huayu.common.BusinessException;
import com.huayu.hr.domain.HrStatisticsData;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/1/13.
 */
@Repository
public class HrStatisticsDataDao  extends BasePageDao<HrStatisticsData,Integer> {


    private void buildSqlOne( Integer dutyLevel, Integer company, Integer department , Integer jobSequence, StringBuilder sql) {
        if (null!=company && null!=dutyLevel){
            sql.append(" and j.company = ").append(company).append(" and j.dutyLevel =").append(dutyLevel).append(" and j.isDelete=0");
        }else if (null!=company && null!=department && null != jobSequence){
            sql.append(" and j.company = ").append(company).append(" and j.department = "+department).append(" and j.jobSequence =").append(jobSequence).append(" and j.isDelete=0");
        }else{
            throw new BusinessException("参数错误");
        }
    }

    private void buildSqlTwo(Integer dutyLevel, Integer company, Integer department, Integer jobSequence, StringBuilder sql) {
        if (null!=company && null!=dutyLevel){
            sql.append(" and j.company = ").append(company).append(" and j.dutyLevel =").append(dutyLevel).append(" and j.isDelete=0").append(" ) t ");
        }else if (null!=company && null!=department && null != jobSequence){
            sql.append(" and j.company="+company).append(" and j.department="+department).append("  and j.jobSequence = ").append(jobSequence).append(" and j.isDelete=0").append(" ) t ");
        }else{
            throw new BusinessException("参数错误");
        }
    }


    /**
     * 统计在职
     */
    public Long countOnDuty(Integer dutyLevel,  Integer jobSequence, Integer companyId, Integer department) {
        StringBuilder sql = new StringBuilder("SELECT");
        sql.append(" COUNT(j.empId) FROM hr_employee_job j ");
        sql.append(" WHERE j.onGuard = 1");
        buildSqlOne(dutyLevel, companyId,department, jobSequence, sql);
        return super.getLong(sql.toString(),new HrStatisticsData());
    }

    public List<HrStatisticsData> countOnDutyPCC(Integer companyId, Integer department) {
        StringBuilder sql = new StringBuilder("SELECT");
        sql.append(" COUNT(j.empId) sum,j.jobSequence FROM hr_employee_job j ");
        sql.append(" WHERE j.onGuard = 1");
        sql.append(" AND j.company = ").append(companyId).append(" AND j.department = "+department).append(" AND j.jobSequence REGEXP").append("'7|8|9'");
        sql.append(" AND j.isDelete=0");
        sql.append(" GROUP BY j.jobSequence");
        return super.get(sql.toString(),new HrStatisticsData());
    }

    public Long countOnDuty( Integer plateId) {
        StringBuilder sql = new StringBuilder("SELECT");
        sql.append(" COUNT(j.empId) FROM hr_employee_job j ");
        sql.append(" WHERE j.onGuard = 1");
        if (null!=plateId){
            sql.append(" and c.plateId ="+plateId);
        }
        sql.append(" AND j.isDelete=0");
        return super.getLong(sql.toString(),new HrStatisticsData());
    }


    /**
     * 年龄统计
     * @return
     */
    public HrStatisticsData countAge(Integer dutyLevel, Integer jobSequence , Integer company, Integer department) {
        StringBuilder sql  = new StringBuilder("SELECT ");
        sql.append(" COUNT(CASE WHEN t.age<=30 THEN 1 ELSE NULL end) AS age1,");
        sql.append(" COUNT(CASE WHEN t.age>30 and t.age<=40 THEN 1 ELSE NULL end) AS age2,");
        sql.append(" COUNT(CASE WHEN t.age>40 and t.age<=50 THEN 1 ELSE NULL end) AS age3,");
        sql.append(" COUNT(CASE WHEN t.age>50 and t.age<=60 THEN 1 ELSE NULL end) AS age4,");
        sql.append(" COUNT(CASE WHEN t.age>60 THEN 1 ELSE NULL end) AS age5");
        sql.append(" FROM (SELECT (year(now())-year(e.birthdate)-1)+(DATE_FORMAT(e.birthdate,'%m%d')<=DATE_FORMAT(NOW(),'%m%d')) AS age");
        sql.append(" FROM hr_employee_job j");
        sql.append(" INNER JOIN hr_employee e ON e.id = j.empId");
        sql.append(" WHERE  j.onGuard = 1 ");
        buildSqlTwo(dutyLevel, company,department, jobSequence, sql);
        return super.getOne(sql.toString(),new HrStatisticsData());
    }

    /**
     * 司龄统计
     * @return
     */
    public HrStatisticsData countCompTime(Integer dutyLevel, Integer jobSequence, Integer company, Integer department) {
        StringBuilder sql  = new StringBuilder("SELECT ");
        sql.append(" COUNT(CASE WHEN t.num<1 THEN 1 ELSE NULL end) AS ctime1,");
        sql.append(" COUNT(CASE WHEN t.num>=1 and t.num<3 THEN 1 ELSE NULL end) AS ctime2,");
        sql.append(" COUNT(CASE WHEN t.num>=3 and t.num<5 THEN 1 ELSE NULL end) AS ctime3,");
        sql.append(" COUNT(CASE WHEN t.num>=5 and t.num<10 THEN 1 ELSE NULL end) AS ctime4,");
        sql.append(" COUNT(CASE WHEN t.num>=10 THEN 1 ELSE NULL end)AS ctime5");
        sql.append(" FROM (SELECT (year(now())-year(e.joinCompDate)-1)+(DATE_FORMAT(e.joinCompDate,'%m%d')<=DATE_FORMAT(NOW(),'%m%d')) AS num");
        sql.append(" FROM hr_employee_job j");
        sql.append(" INNER JOIN hr_employee e ON e.id = j.empId");
        sql.append(" WHERE  j.onGuard = 1 ");
        buildSqlTwo(dutyLevel, company,department, jobSequence, sql);
        return super.getOne(sql.toString(),new HrStatisticsData());
    }

    /**
     * 性别统计
     */
    public HrStatisticsData countSex(Integer dutyLevel, Integer jobSequence, Integer company, Integer department) {
        StringBuilder sql  = new StringBuilder("SELECT ");
        sql.append(" count(CASE e.sex WHEN 1 THEN 1 ELSE NULL END ) male, COUNT(CASE e.sex WHEN 0 THEN 1 ELSE NULL END) female");
        sql.append(" FROM hr_employee_job j");
        sql.append(" INNER JOIN hr_employee e ON e.id = j.empId");
        sql.append(" WHERE  j.onGuard = 1 ");
        buildSqlOne(dutyLevel, company, department, jobSequence, sql);
        return super.getOne(sql.toString(),new HrStatisticsData());
    }


    public int[] batchInsertStatisticsData(SqlParameterSource[] sqlParameterSources){
        String sql = "INSERT INTO hr_statistics_data (id,sum, edu1, edu2, edu3, edu4, age1, age2, age3, age4, age5, ctime1, ctime2, ctime3, ctime4, ctime5, male, female, statisticsType, year, month, company, dutyLevel, department, status, createtime) " +
                "VALUES (:id,:sum, :edu1, :edu2, :edu3, :edu4, :age1, :age2, :age3, :age4, :age5, :ctime1, :ctime2, :ctime3, :ctime4, :ctime5, :male, :female, :statisticsType, :year, :month, :company, :dutyLevel, :department, :status, :createtime)";
        return super.batchUpdate(sql,sqlParameterSources);
    }


    public List<HrStatisticsData> getStatisticsData(HrStatisticsData statisticsData) {
        StringBuilder sql  = new StringBuilder("SELECT");
        sql.append(" dept.isParent,dept.parentId departmentPid,d.*,dept.name deptName");
        sql.append(" FROM hr_statistics_data d");
        sql.append(" LEFT JOIN hr_department dept on dept.id = d.department");
        sql.append(" LEFT JOIN hr_company c ON c.id = d.company");
        sql.append(" WHERE d.statisticsType=:statisticsType and d.year=:year and d.month=:month and d.status=0 ");
        if (statisticsData.getCompany()!=null){
            sql.append(" and d.company=:company");
        }
        if (StringUtils.isNotBlank(statisticsData.getAuthorityRegexp())){
            sql.append(" and c.code REGEXP ").append(statisticsData.getAuthorityRegexp());
        }
        return super.get(sql.toString(),statisticsData);
    }

    public List<HrStatisticsData> getStatisticsDataSum(HrStatisticsData statisticsData) {
        StringBuilder sql  = new StringBuilder("SELECT");
        sql.append(" d.department,c.isParent,c.parentId companyPid,d.company,c.name companyStr,sum(d.sum) sum,sum(d.edu1) edu1,sum(d.edu2) edu2,sum(d.edu3) edu3,sum(d.edu4) edu4");
        sql.append(",sum(d.age1) age1,sum(d.age2) age2,sum(d.age3) age3,sum(d.age4) age4,sum(d.age5) age5");
        sql.append(",sum(d.ctime1) ctime1,sum(d.ctime2) ctime2,sum(d.ctime3) ctime3,sum(d.ctime4) ctime4,sum(d.ctime5) ctime5,sum(d.male) male,sum(d.female) female,d.dutyLevel");
        sql.append(" FROM hr_statistics_data d");
        sql.append(" LEFT JOIN hr_department dept on dept.id = d.department");
        sql.append(" LEFT JOIN hr_company c ON c.id = d.company");
        sql.append(" WHERE d.statisticsType=:statisticsType and d.year=:year and d.month=:month and d.status=0 ");
        sql.append(" and c.code REGEXP ").append(statisticsData.getAuthorityRegexp());
        if (statisticsData.getStatisticsType().equals(2)){
            sql.append(" GROUP BY d.dutyLevel");
        }else{
            sql.append(" GROUP BY d.company");
        }
        return super.get(sql.toString(),statisticsData);
    }

    public int removeData(HrStatisticsData statisticsData) {
        StringBuilder sql  = new StringBuilder("update  hr_statistics_data set  status = 1 where statisticsType=:statisticsType and year=:year and month=:month and company=:company");
        return super.put(sql.toString(),statisticsData);
    }

    public List<HrStatisticsData> getStatisticsDataSimple(HrStatisticsData statisticsData) {
        StringBuilder sql  = new StringBuilder("SELECT d.*");
        sql.append(" FROM hr_statistics_data d");
        sql.append(" WHERE d.year=:year and d.month=:month and d.status=0 order by id ASC ");
        return super.get(sql.toString(),statisticsData);
    }
}
