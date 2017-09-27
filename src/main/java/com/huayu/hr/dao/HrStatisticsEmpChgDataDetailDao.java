package com.huayu.hr.dao;

import com.huayu.hr.domain.HrStatisticsEmpChgDataDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/1/17.
 */
@Repository
public class HrStatisticsEmpChgDataDetailDao extends BasePageDao<HrStatisticsEmpChgDataDetail,Integer> {

    public int[] batchUpdate(SqlParameterSource[] a2) {
        String sql = "INSERT INTO hr_statistics_emp_chg_data_detail (id,statisticsId, empId,businessId, dutyLevel, statisticsType, company, department, year, month, status, createtime) " +
                "VALUES (:id,:statisticsId, :empId,:businessId, :dutyLevel, :statisticsType, :company, :department, :year, :month, :status, :createtime)";
        return super.batchUpdate(sql, a2);
    }

    public int postData(HrStatisticsEmpChgDataDetail detail) {
        String sql = "INSERT INTO hr_statistics_emp_chg_data_detail (statisticsId, empId,businessId, dutyLevel, statisticsType, company, department, year, month, status, createtime) " +
                "VALUES (:statisticsId, :empId,:businessId, :dutyLevel, :statisticsType, :company, :department, :year, :month, :status, :createtime)";
        return super.post(sql,detail);
    }

    public void removeData(HrStatisticsEmpChgDataDetail detail) {
        StringBuilder sql  = new StringBuilder("update  hr_statistics_emp_chg_data_detail set  status = 1 where  year=:year and month=:month and company=:company");
        super.put(sql.toString(),detail);
    }
}
