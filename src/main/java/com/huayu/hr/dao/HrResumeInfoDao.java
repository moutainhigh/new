package com.huayu.hr.dao;

import com.huayu.hr.domain.HrResumeInfo;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/19.
 */
@Repository
public class HrResumeInfoDao  extends BasePageDao<HrResumeInfo,Integer> {

    public int addOne(HrResumeInfo one) {
        Long longKey = super.getKey("hr_resume_info", one);
        one.setId(longKey.intValue());
        return super.post(one);
    }

    public int updateOne(HrResumeInfo one) {
        String sql = "update hr_resume_info set startTime=:startTime,endTime=:endTime,company=:company,duty=:duty,phone=:phone,witness=:witness,witnessPhone=:witnessPhone,leaveReason=:leaveReason,updatetime=:updatetime where empId=:empId and id=:id";
        return super.put(sql,one);
    }

    public int delOne(HrResumeInfo one) {
        String sql = "update hr_resume_info set status=1,deletetime=:deletetime where empId=:empId and id=:id";
        return super.put(sql,one);
    }

    public List<HrResumeInfo> getResumeInfoByEmpId(HrResumeInfo one) {
        String sql  = "select id,empId,startTime,endTime,company,duty,phone,witness,witnessPhone,leaveReason,status,createtime,updatetime,deletetime from hr_resume_info where empId=:empId and status = 0 order by startTime desc";
        return super.get(sql,one);
    }
}
