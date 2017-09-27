package com.huayu.hr.dao;

import com.huayu.hr.domain.HrJobRequest;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2016/12/30.
 */
@Repository
public class HrJobRequestDao extends BasePageDao<HrJobRequest,Integer>{

    public int postJobRequest(HrJobRequest jobRequest) {
        Long longKey = super.getKey("hr_job_request",jobRequest);
        jobRequest.setId(longKey.intValue());
        return super.post(jobRequest);
    }

    public Page<HrJobRequest> getJobRequestListData(HrJobRequest hrJobRequest, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder("select r.id,r.name,r.job,r.mobile,r.plateId, r.area,r.`status`,r.createtime,c.title jobStr ");
        stringBuilder.append(" FROM hr_job_request r INNER JOIN hr_recruitment_content c ON c.id = r.job ");
        stringBuilder.append(" WHERE  r.plateId=:plateId and r.area = :area");
        if (StringUtils.isNotBlank(hrJobRequest.getName())){
            stringBuilder.append(" and position(:name in r.name)");
        }
        if (StringUtils.isNotBlank(hrJobRequest.getMobile())){
            stringBuilder.append(" and position(:mobile in r.mobile)");
        }
        if (null!=hrJobRequest.getStatus()){
            stringBuilder.append(" and r.`status`=:status");
        } else {
            stringBuilder.append(" and r.`status` < 2");
        }
        return super.get(stringBuilder.toString(),hrJobRequest,pageable);
    }

    public HrJobRequest getJobRequestOne(HrJobRequest jobRequest) {
        String sql = "select r.*,c.title jobStr from hr_job_request r inner join hr_recruitment_content c on c.id = r.job where r.id=:id";
        return super.getOne(sql,jobRequest);
    }

    public int updateJobRequestOneStatus(HrJobRequest hrJobRequest) {
        StringBuilder sql = new StringBuilder("update hr_job_request set operateuser=:operateuser");
        if (null!=hrJobRequest.getStatus()){
            sql.append(",status=:status");
        }
        if (StringUtils.isNotBlank(hrJobRequest.getNote())){
            sql.append(",note=:note");
        }
        sql.append(" where id=:id");
        return super.put(sql.toString(),hrJobRequest);
    }
}
