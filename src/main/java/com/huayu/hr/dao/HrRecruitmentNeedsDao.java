package com.huayu.hr.dao;

import com.huayu.hr.domain.HrRecruitmentNeeds;
import com.huayu.hr.web.args.HrRecruitmentNeedsArgs;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
@Repository
public class HrRecruitmentNeedsDao extends BasePageDao<HrRecruitmentNeeds, Integer> {

    public Page<HrRecruitmentNeeds> listNeedsData(HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM hr_recruitment_needs");
        sql.append(" where `status` = 2 ");
        if (null != hrRecruitmentNeedsArgs.getStartTime() && null != hrRecruitmentNeedsArgs.getEndTime()) {
            sql.append(" and askDate BETWEEN :startTime AND :endTime");
        }
        return super.get(sql.toString(), hrRecruitmentNeedsArgs, pageable);
    }


    public List<HrRecruitmentNeeds> statisticsDataByDutyLevel(HrRecruitmentNeedsArgs entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d1.company, SUM(d2.addNum) topManage, SUM(d3.addNum) subsidiaryTopManage, SUM(d4.addNum) topInspector,");
        sql.append(" SUM(d5.addNum) subsidiaryTopInspector, SUM(d6.addNum) manage, SUM(d7.addNum) subsidiaryManage,");
        sql.append(" SUM(d8.addNum) charge, SUM(d9.addNum) director, SUM(d0.addNum) staff FROM hr_recruitment_needs d1");
        sql.append(" LEFT JOIN hr_recruitment_needs d2 ON d1.id = d2.id AND d2.dutyLevel = '总经理级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d3 ON d1.id = d3.id AND d3.dutyLevel = '副总经理级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d4 ON d1.id = d4.id AND d4.dutyLevel = '总监级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d5 ON d1.id = d5.id AND d5.dutyLevel = '副总监级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d6 ON d1.id = d6.id AND d6.dutyLevel = '经理级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d7 ON d1.id = d7.id AND d7.dutyLevel = '副经理级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d8 ON d1.id = d8.id AND d8.dutyLevel = '主管级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d9 ON d1.id = d9.id AND d9.dutyLevel = '主办级'");
        sql.append(" LEFT JOIN hr_recruitment_needs d0 ON d1.id = d0.id AND d0.dutyLevel = '员级'");
        sql.append(" WHERE d1.`status` = 2");
        if (null != entity.getStartTime() && null != entity.getEndTime()) {
            sql.append(" AND d1.askDate BETWEEN :startTime AND :endTime");
        }
        sql.append(" GROUP BY d1.company");
        return super.get(sql.toString(), entity);
    }

    public List<HrRecruitmentNeeds> statisticsDataByManageGroup(HrRecruitmentNeedsArgs entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT manageGroup,SUM(addNum) manageGroupNum FROM hr_recruitment_needs where 1=1");
        if (null != entity.getStartTime() && null != entity.getEndTime()) {
            sql.append(" and askDate BETWEEN :startTime AND :endTime");
        }
        sql.append(" GROUP BY manageGroup");
        return super.get(sql.toString(), entity);
    }

    public List<HrRecruitmentNeeds> statisticsDataByDepartmentLevel(HrRecruitmentNeedsArgs entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT departmentLevel,SUM(addNum) manageGroupNum FROM hr_recruitment_needs where 1=1");
        if (null != entity.getStartTime() && null != entity.getEndTime()) {
            sql.append(" and askDate BETWEEN :startTime AND :endTime");
        }
        sql.append(" GROUP BY departmentLevel");
        return super.get(sql.toString(), entity);
    }

    public List<HrRecruitmentNeeds> statisticsDataByCompanyLevel(HrRecruitmentNeedsArgs entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT companyLevel,SUM(addNum) manageGroupNum FROM hr_recruitment_needs where 1=1");
        if (null != entity.getStartTime() && null != entity.getEndTime()) {
            sql.append(" and askDate BETWEEN :startTime AND :endTime");
        }
        sql.append(" GROUP BY companyLevel");
        return super.get(sql.toString(), entity);
    }
}
