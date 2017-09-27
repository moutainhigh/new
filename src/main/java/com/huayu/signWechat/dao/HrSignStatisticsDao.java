package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignStatistics;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Repository
public class HrSignStatisticsDao extends BasePageDao<HrSignStatistics, Serializable> {

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSource) {
        String sql = "INSERT INTO hr_sign_statistics ( id,startDate,endDate,userid,pactCompany,plateId,companyId" +
                ",departmentId,userNum,birthdate,joinCompDate,InDueFormDate,useStatus,name,sex,position,absenceDayNum" +
                ",factPayDayNum,workDutyDayNum,restDayNum,elseDeduct,affairsLeave,sickLeave,restChanged,idCard" +
                ",technicalLevel,userCategory,workStatus,performanceSystem) values (:id,:startDate,:endDate,:userid" +
                ",:pactCompany,:plateId,:companyId,:departmentId,:userNum,:birthdate,:joinCompDate,:InDueFormDate" +
                ",:useStatus,:name,:sex,:position,:absenceDayNum,:factPayDayNum,:workDutyDayNum,:restDayNum,:elseDeduct" +
                ",:affairsLeave,:sickLeave,:restChanged,:idCard,:technicalLevel,:userCategory,:workStatus,:performanceSystem)";
        return super.batchUpdate(sql, sqlParameterSource);
    }

    public List<HrSignStatistics> getSignStatisticsData(HrSignStatistics entity) {
        String sql = "SELECT * FROM hr_sign_statistics WHERE startDate=:startDate AND endDate =:endDate";
        return super.get(sql, entity);
    }
}
