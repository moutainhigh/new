package com.huayu.hr.dao;

import com.huayu.hr.domain.HrJob;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/7.
 */
@Repository
public class HrJobDao  extends BasePageDao<HrJob, Integer> {

    public HrJob getOne(HrJob job){
        String  sql = "SELECT * FROM  hr_job where id=:id and status=0";
        return super.getOne(sql,job);
    }

    public List<HrJob> getAllJobByDepartmentId(HrJob job) {
        String  sql = "select id,name,departmentId,status from hr_job where departmentId=:departmentId  and status=0";
        return super.get(sql,job);
    }


    public List<HrJob> getAllJobByDepartWithPlait(HrJob job) {
        String  sql = "select j.id,j.name,j.departmentId,j.status,p.count from hr_job j " +
                "INNER JOIN (SELECT SUM(CASE WHEN empId IS NULL THEN 1 ELSE 0 END) count,jobId FROM hr_job_plait  where departmentId=:departmentId AND year=:year AND month=:month AND `status`=0 GROUP BY jobId) p ON j.id = p.jobId  " +
                "where j.departmentId=:departmentId  and j.status=0";
        return super.get(sql,job);
    }

    public int addOne(HrJob job) {
        Long longKey = super.getKey("hr_job", job);
        job.setId(longKey.intValue());
        return super.post(job);
    }

    public int updateOne(HrJob job) {
        String sql = "UPDATE hr_job SET name=:name  WHERE departmentId=:departmentId AND id=:id";
        return super.put(sql,job);
    }

    public int delOne(HrJob job) {
        String sql = "UPDATE hr_job SET status=1,deletetime=:deletetime WHERE departmentId=:departmentId AND id=:id";
        return super.put(sql,job);
    }

}
