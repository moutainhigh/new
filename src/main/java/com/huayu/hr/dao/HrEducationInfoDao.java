package com.huayu.hr.dao;

import com.huayu.hr.domain.HrEducationInfo;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/19.
 */
@Repository
public class HrEducationInfoDao extends BasePageDao<HrEducationInfo,Integer> {

    public int add(HrEducationInfo educationInfo) {
        Long longKey = super.getKey("hr_education_info", educationInfo);
        educationInfo.setId(longKey.intValue());
        return super.post(educationInfo);
    }

    public int updateTopEducation(HrEducationInfo one) {
        String sql = "update hr_education_info set education=:education,updatetime=:updatetime where empId=:empId and topEducation=1";
        return super.put(sql,one);
    }

    public int updateOne(HrEducationInfo one) {
        String sql = "update hr_education_info set admissionDate=:admissionDate,graduationDate=:graduationDate,topEducation=:topEducation,school=:school,profession=:profession,education=:education,degree=:degree,note=:note,degreeDate=:degreeDate,degreeOrg=:degreeOrg,updatetime=:updatetime where empId=:empId and id=:id";
        return super.put(sql,one);
    }

    public int delOne(HrEducationInfo one) {
        String sql = "update hr_education_info set status=1,deletetime=:deletetime where empId=:empId and id=:id";
        return super.put(sql,one);
    }

    public List<HrEducationInfo> getEducationInfoAll(HrEducationInfo one) {
        String sql  = "select id,empId,admissionDate,graduationDate,topEducation,school,profession,education,degree,note,degreeDate,degreeOrg from hr_education_info where empId=:empId and status = 0 order by admissionDate desc";
        return super.get(sql,one);
    }

    public HrEducationInfo getTopEducationInfo(HrEducationInfo one) {
        String sql  = "select id,empId,admissionDate,graduationDate,topEducation,school,profession,education,degree,note,degreeDate,degreeOrg from hr_education_info where empId=:empId and status = 0 and topEducation=1 order by admissionDate desc";
        return super.getOne(sql,one);
    }
}
