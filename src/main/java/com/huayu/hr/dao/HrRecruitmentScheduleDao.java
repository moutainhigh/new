package com.huayu.hr.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.hr.domain.HrRecruitmentSchedule;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
@Repository
public class HrRecruitmentScheduleDao extends BasePageDao<HrRecruitmentSchedule,Integer> {

    public int postData(HrRecruitmentSchedule hrRecruitmentSchedule) {
        Long key = super.getKey("hr_recruitment_schedule", new HrRecruitmentSchedule());
        hrRecruitmentSchedule.setId(key.intValue());
        return super.post(hrRecruitmentSchedule);
    }

    public Page<HrRecruitmentSchedule> listData(HrRecruitmentSchedule schedule, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT e.* from hr_recruitment_schedule e ");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = e.companyId");
        stringBuilder.append(" WHERE e.`status` = 0");
        if (StringUtils.isNotBlank(schedule.getPlat())) {
            String s = this.getSqlMultiString(schedule.getPlat());
            stringBuilder.append(" and e.plat in ("+s+")");
        }

        if (StringUtils.isNotBlank(schedule.getCompany())) {
            String s = this.getSqlMultiString(schedule.getCompany());
            stringBuilder.append(" and e.company in ("+s+")");
        }
        if (StringUtils.isNotBlank(schedule.getDepartment())) {
            String s = this.getSqlMultiString(schedule.getDepartment());
            stringBuilder.append(" and e.department in ("+s+")");
        }
        if (StringUtils.isNotBlank(schedule.getProcessStatusStr())){
            String s = this.getSqlMultiString(String.valueOf(schedule.getProcessStatusStr()));
            stringBuilder.append(" and e.processStatus in ("+s+")");
        }
        stringBuilder.append(" and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        return super.get(stringBuilder.toString(), schedule, pageable);
    }

    private String getSqlMultiString(String condition) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = condition.split(",|，");
        for (String s : split) {
            stringBuilder.append("'"+s+"',");
        }
        return stringBuilder.toString().substring(0,stringBuilder.lastIndexOf(","));
    }

    public HrRecruitmentSchedule getOneHrRecruitmentSchedule(HrRecruitmentSchedule hrRecruitmentSchedule) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "SELECT s.*  from hr_recruitment_schedule s");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = s.companyId");
        stringBuilder.append(" and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        stringBuilder.append(" WHERE s.id =:id");
        return super.getOne(stringBuilder.toString(), hrRecruitmentSchedule);
    }

    public int updateHrRecruitmentSchedule(HrRecruitmentSchedule hrRecruitmentSchedule) {
        String sql = "UPDATE hr_recruitment_schedule SET position =:position ,plat =:plat ,companyId =:companyId,company =:company ,department =:department ," +
                "numberOfHiring =:numberOfHiring ,`level` =:level ,hireDate =:hireDate ,numberOfResumes =:numberOfResumes ,receiveResumeDate =:receiveResumeDate ," +
                "resumePassedNumber =:resumePassedNumber ,resumePassedDate =:resumePassedDate ,personNumByCall =:personNumByCall ,firstAuditionDate =:firstAuditionDate ," +
                "firstPassedPerson =:firstPassedPerson ,secondAuditonNumber =:secondAuditonNumber ,secondAuditionDate =:secondAuditionDate ,secondPassedPerson =:secondPassedPerson ," +
                "finalAuditionNumber =:finalAuditionNumber ,finalAuditionDate =:finalAuditionDate ,personNameToBeHired =:personNameToBeHired ,telephone =:telephone ," +
                "planComePositionDate =:planComePositionDate ,comePositionDate =:comePositionDate ,responsiblePerson =:responsiblePerson ,remark =:remark ,processStatus=:processStatus," +
                "description =:description WHERE id =:id ";
        return super.put(sql, hrRecruitmentSchedule);
    }

    public int deleteOneHrRecruitmentSchedule(HrRecruitmentSchedule hrRecruitmentSchedule) {
        String sql = "update hr_recruitment_schedule set status = 1, deleteDate = :deleteDate, deleteUser=:deleteUser where id =:id";
        return super.put(sql, hrRecruitmentSchedule);
    }

    public List<HrRecruitmentSchedule> getAllHrRecruitmentSchedule(HrRecruitmentSchedule hrRecruitmentSchedule, String field) {
        String sql = "SELECT "+field+" FROM hr_recruitment_schedule e  WHERE e.`status` = 0 GROUP BY "+field;
        return super.get(sql, hrRecruitmentSchedule);
    }

    public List<HrRecruitmentSchedule> scheduleStatisticsData(HrRecruitmentSchedule hrRecruitmentSchedule) {
        StringBuilder stringBuilder = new StringBuilder("SELECT  rs.plat,SUM(IFNULL(rs.numberOfHiring,0)) numberOfHiring,SUM(IFNULL(rs.numberOfResumes,0)) numberOfResumes,");
        stringBuilder.append("SUM(IFNULL(rs.resumePassedNumber,0)) resumePassedNumber,SUM(IFNULL(rs.personNumByCall,0)) personNumByCall,");
        stringBuilder.append("SUM(IFNULL(rs.secondAuditonNumber,0)) secondAuditonNumber,SUM(IFNULL(rs.finalAuditionNumber,0)) finalAuditionNumber ");
        stringBuilder.append(" FROM hr_recruitment_schedule rs  WHERE  rs.`status`= 0 AND rs.hireDate between :startDate and DATE_ADD(:endDate ,INTERVAL 1 DAY)");
        stringBuilder.append("  GROUP BY rs.plat ");
        return super.get(stringBuilder.toString(),hrRecruitmentSchedule);
    }
}
