package com.huayu.hr.dao;

import com.huayu.hr.domain.HrJobPlait;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HrJobPlaitDao extends BasePageDao<HrJobPlait,Integer>{

    public int[] batchAdd(SqlParameterSource[] sqlParameterSources) {
        String sql = "insert into hr_job_plait (id,year,month,companyId,departmentId,jobId,note,manageLine,dutyLevel,status,createtime,createUser) " +
                "values (:id,:year,:month,:companyId,:departmentId,:jobId,:note,:manageLine,:dutyLevel,0,:createtime,:createUser)";
        return super.batchUpdate(sql,sqlParameterSources);
    }

    public List<HrJobPlait> getPlaitsSimple(HrJobPlait jobPlait) {
        StringBuilder sql = new StringBuilder("select * ");
        sql.append(" from hr_job_plait where year = :year and month = :month and jobId=:jobId and status=0");
        return super.get(sql.toString(),jobPlait);
    }

    public int occupyJobPlait(HrJobPlait jobPlait) {
        String sql = "update hr_job_plait p  INNER JOIN (SELECT id FROM hr_job_plait where year = :year and month =:month and jobId=:jobId and status=0 AND empId IS NULL LIMIT 0,1) t ON p.id = t.id set p.empId=:empId";
        return super.put(sql,jobPlait);
    }

    public int cancelOccupyJobPlait(HrJobPlait jobPlait) {
        String sql = "update hr_job_plait set empId=null where empId=:empId and jobId=:jobId and year = :year and month =:month and status=0";
        return super.put(sql,jobPlait);
    }

    public HrJobPlait getOneJobPlait(HrJobPlait jobPlait) {
        String sql = "select * from hr_job_plait where status=0 and id=:id";
        return super.getOne(sql,jobPlait);
    }

    public int batchUpdateJobPlait(HrJobPlait jobPlait) {
        String sql = "update hr_job_plait set dutyLevel=:dutyLevel,manageLine=:manageLine where year = :year and month >= :startMonth and month <= :endMonth and jobId=:jobId and status=0";
        return super.put(sql,jobPlait);
    }

    public Page<HrJobPlait> getPlaits(HrJobPlait jobPlait, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT c.`name` companyName,d.`name` departmentName,j.`name` jobName,e.empName,e.idCard,p.* ");
        sql.append(" FROM hr_job_plait p ");
        sql.append(" INNER JOIN hr_company c ON c.id =p.companyId");
        sql.append(" INNER JOIN hr_department d ON d.id = p.departmentId");
        sql.append(" INNER JOIN hr_job j ON j.id = p.jobId");
        sql.append(" LEFT JOIN hr_employee e ON e.id = p.empId");
        sql.append(" WHERE  p.jobId=:jobId  AND p.status=0");
        if (null!=jobPlait.getYear()){
            sql.append(" AND p.year = :year ");
        }
        if (null!= jobPlait.getMonth()){
            sql.append(" AND p.month = :month");
        }
        return super.get(sql.toString(),jobPlait,pageable);
    }

    public int deleteOne(HrJobPlait hrJobPlait) {
        String sql = "update hr_job_plait set status=1,deleteUser=:deleteUser,deletetime=:deletetime where id=:id and status=0";
        return super.put(sql,hrJobPlait);
    }

    public int batchDelete(HrJobPlait hrJobPlait) {
        String sql = "update hr_job_plait set status=1,deleteUser=:deleteUser,deletetime=:deletetime where jobId=:jobId and status=0";
        return super.put(sql,hrJobPlait);
    }

    public void updatePlaitStore() {
        String sql = "call updatePlaitCount()";
        super.jdbcTemplate.getJdbcOperations().execute(sql);
    }
}
