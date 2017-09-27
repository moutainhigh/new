package com.huayu.hr.dao;

import com.huayu.hr.domain.HrStatisticsEmpChgData;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/1/17.
 */
@Repository
public class HrStatisticsEmpChgDataDao extends BasePageDao<HrStatisticsEmpChgData,Integer> {

    public int[] batchInsertStatisticsData(SqlParameterSource[] a2) {
        String sql = "INSERT INTO hr_statistics_emp_chg_data (id,beginOnDutyM,beginOnDutyW,onDutyM, onDutyW, inDutyM, inDutyW, inDutyGatherM, outDutyM, outDutyW, outDutyGatherM, turnInCompM, turnInCompW, turnOutCompM, turnOutCompW, turnInDeptM, turnInDeptW, turnOutDeptM, turnOutDeptW, practice, channel, carM, note, company, department, year, month, status, createtime) " +
                "VALUES (:id,:beginOnDutyM,:beginOnDutyW,:onDutyM, :onDutyW, :inDutyM, :inDutyW, :inDutyGatherM, :outDutyM, :outDutyW, :outDutyGatherM, :turnInCompM, :turnInCompW, :turnOutCompM, :turnOutCompW, :turnInDeptM, :turnInDeptW, :turnOutDeptM, :turnOutDeptW, :practice, :channel, :carM, :note, :company, :department, :year, :month, :status, :createtime)";
        return super.batchUpdate(sql, a2);
    }

    public int postData(HrStatisticsEmpChgData data) {
        Long key = super.getKey("hr_statistics_emp_chg_data", data);
        data.setId(key.intValue());
        return super.post(data);
    }


    public HrStatisticsEmpChgData getOne(HrStatisticsEmpChgData data){
        String sql  = "SELECT * from hr_statistics_emp_chg_data d where d.company=:company and d.department = :department and d.year=:year and d.month=:month and d.status = 0 ";
        return super.getOne(sql,data);
    }

    /**
     * 获取统计结果
     * @param data
     * @return
     */
    public List<HrStatisticsEmpChgData> getStatisticsData(HrStatisticsEmpChgData data) {
        StringBuilder sql  = new StringBuilder("SELECT dept.isParent,dept.parentId departmentPid,d.*, dept.name deptName");
        sql.append(" FROM  hr_statistics_emp_chg_data d ");
        sql.append(" LEFT JOIN hr_department dept on dept.id = d.department");
        sql.append(" WHERE d.company=:company  and d.year=:year and d.month=:month and d.status = 0");
        return super.get(sql.toString(),data);
    }

    public List<HrStatisticsEmpChgData> getAllStatisticsChgData(HrStatisticsEmpChgData data) {
        StringBuilder sql  = new StringBuilder("SELECT d.company,c.name companyName,SUM(d.beginOnDutyM) beginOnDutyM,SUM(d.beginOnDutyW) beginOnDutyW,SUM(d.onDutyM) onDutyM,SUM(d.onDutyW) onDutyW,SUM(d.inDutyM) inDutyM,SUM(d.inDutyW) inDutyW");
        sql.append(",SUM(d.inDutyGatherM) inDutyGatherM,SUM(d.outDutyM) outDutyM,SUM(d.outDutyW) outDutyW,SUM(d.outDutyGatherM) outDutyGatherM,SUM(d.turnInCompM) turnInCompM");
        sql.append(",SUM(d.turnInCompW) turnInCompW,SUM(d.turnOutCompM) turnOutCompM,SUM(d.turnOutCompW) turnOutCompW,SUM(d.practice) practice,SUM(d.channel) channel,SUM(d.carM) carM");
        sql.append(" FROM  hr_statistics_emp_chg_data d ");
        sql.append(" LEFT JOIN hr_department dept on dept.id = d.department");
        sql.append(" LEFT JOIN hr_company c on c.id = d.company");
        sql.append(" WHERE  d.year=:year and d.month=:month and d.status = 0");
        sql.append(" and c.code REGEXP ").append(data.getAuthorityRegexp());
        sql.append(" GROUP BY d.company");
        return super.get(sql.toString(),data);
    }

    public List<HrStatisticsEmpChgData> getStatisticsChgDataSimple(HrStatisticsEmpChgData data) {
        String sql  = "SELECT d.* FROM hr_statistics_emp_chg_data d WHERE d.year=:year and d.month=:month and d.status = 0 order by id ASC";
        return super.get(sql,data);
    }

    public HrStatisticsEmpChgData getStatisticsDataOne(HrStatisticsEmpChgData data) {
        StringBuilder sql  = new StringBuilder("SELECT d.* ");
        sql.append(" FROM  hr_statistics_emp_chg_data d ");
        sql.append(" WHERE d.company=:company and d.department=:department   and d.year=:year and d.month=:month and d.status = 0");
        return super.getOne(sql.toString(),data);
    }


    public void removeData(HrStatisticsEmpChgData statisticsData) {
        StringBuilder sql  = new StringBuilder("update  hr_statistics_emp_chg_data set  status = 1 where  year=:year and month=:month");
        if (null!=statisticsData.getCompany()){
            sql.append(" and company=:company");
        }
        super.put(sql.toString(),statisticsData);
    }

    public int disableData(HrStatisticsEmpChgData statisticsData) {
        StringBuilder sql  = new StringBuilder("update  hr_statistics_emp_chg_data set  status = 1, note=:note where  id=:id");
        return super.put(sql.toString(),statisticsData);
    }

    public void removeAllEmpChgData(String yyyy, String mm) {
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setYear(yyyy);
        statisticsData.setMonth(mm);
        StringBuilder sql  = new StringBuilder("update  hr_statistics_emp_chg_data set  status = 1 where  year=:year and month=:month");
        super.put(sql.toString(),statisticsData);
    }

}
