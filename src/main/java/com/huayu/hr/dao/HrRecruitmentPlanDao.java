package com.huayu.hr.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.hr.domain.HrRecruitmentPlan;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/7/18.
 */
@Repository
public class HrRecruitmentPlanDao extends BasePageDao<HrRecruitmentPlan,Integer>{

    public int postData(HrRecruitmentPlan entity){
        Long key = super.getKey("hr_recruitment_plan", entity);
        entity.setId(key.intValue());
        return super.post(entity);
    }

    public Page<HrRecruitmentPlan> listData(HrRecruitmentPlan plan, Pageable pageable) {
        StringBuilder stringBuilder  = new StringBuilder("select p.* from hr_recruitment_plan p inner join hr_company c on c.id = p.companyId  ");
        stringBuilder.append(" where   p.status=0 and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        if (null!=plan.getStartDate() && null!=plan.getEndDate()){
            stringBuilder.append(" and p.askDate between :startDate and DATE_ADD(:endDate ,INTERVAL 1 DAY)");
        }
        return super.get(stringBuilder.toString(),plan,pageable);
    }

    public HrRecruitmentPlan getPlanOne(HrRecruitmentPlan plan) {
        StringBuilder stringBuilder  = new StringBuilder("select p.* from hr_recruitment_plan p inner join hr_company c on c.id = p.companyId  ");
        stringBuilder.append(" where  p.status=0  and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        stringBuilder.append(" and p.id = :id ");
        return super.getOne(stringBuilder.toString(),plan);
    }

    public int updatePlan(HrRecruitmentPlan plan) {
        StringBuilder stringBuilder = new StringBuilder(" update hr_recruitment_plan set ");
        stringBuilder.append("plat=:plat,companyId=:companyId,companyName=:companyName,departmentName=:departmentName,jobName=:jobName,dutyLevel=:dutyLevel,");
        stringBuilder.append("dutyLevelName=:dutyLevelName,vacancyCount=:vacancyCount,askDate=:askDate,overDate=:overDate,fileCount=:fileCount,requirement=:requirement,");
        stringBuilder.append("progress=:progress,fileName=:fileName,vacancyCount2=:vacancyCount2,planJoinDate=:planJoinDate,overJoinDate=:overJoinDate,payment=:payment,reportJobName=:reportJobName");
        stringBuilder.append(" where id=:id");
        return super.put(stringBuilder.toString(),plan);
    }

    public int deletePlan(HrRecruitmentPlan plan) {
        StringBuilder stringBuilder = new StringBuilder(" update hr_recruitment_plan set");
        stringBuilder.append(" deletetime=:deletetime,deleteUser=:deleteUser,status=:status ");
        stringBuilder.append(" where id=:id");
        return super.put(stringBuilder.toString(),plan);
    }

    public List<HrRecruitmentPlan> downloadPlanData(HrRecruitmentPlan plan) {
        StringBuilder stringBuilder  = new StringBuilder("select p.* from hr_recruitment_plan p inner join hr_company c on c.id = p.companyId  ");
        stringBuilder.append(" where  p.status=0  and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        if (null!=plan.getStartDate() && null!=plan.getEndDate()){
            stringBuilder.append(" and p.askDate between :startDate and DATE_ADD(:endDate ,INTERVAL 1 DAY)");
        }
        return super.get(stringBuilder.toString(),plan);
    }

    public List<HrRecruitmentPlan> planStatisticsData(HrRecruitmentPlan plan) {
        StringBuilder stringBuilder  = new StringBuilder("SELECT p.plat,SUM(p.vacancyCount) vacancyCount,SUM(p.fileCount) fileCount,SUM(p.vacancyCount2) vacancyCount2 FROM hr_recruitment_plan p INNER JOIN hr_company c ON c.id = p.companyId    ");
        stringBuilder.append(" WHERE  p.status=0  AND c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        if (null!=plan.getStartDate() && null!=plan.getEndDate()){
            stringBuilder.append(" AND p.askDate between :startDate AND DATE_ADD(:endDate ,INTERVAL 1 DAY)");
        }
        stringBuilder.append(" GROUP BY p.plat");
        return super.get(stringBuilder.toString(),plan);
    }
}
