package com.huayu.hr.service;

import com.huayu.hr.dao.HrRecruitmentNeedsDao;
import com.huayu.hr.domain.HrRecruitmentNeeds;
import com.huayu.hr.web.args.HrRecruitmentNeedsArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
@Service
public class HrRecruitmentNeedsService {

    @Autowired
    private HrRecruitmentNeedsDao hrRecruitmentNeedsDao;

    public Page<HrRecruitmentNeeds> listNeedsData(HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs, Pageable pageable) {
        return hrRecruitmentNeedsDao.listNeedsData(hrRecruitmentNeedsArgs, pageable);
    }


    public List<HrRecruitmentNeeds> statisticsDataByDutyLevel(HrRecruitmentNeedsArgs entity) {
        List<HrRecruitmentNeeds> hrRecruitmentNeeds = hrRecruitmentNeedsDao.statisticsDataByDutyLevel(entity);
        for (HrRecruitmentNeeds h : hrRecruitmentNeeds) {
            Integer sum = 0;
            if (null != h.getTopManage()) {
                sum += h.getTopManage();
            }
            if (null != h.getSubsidiaryTopManage()) {
                sum += h.getSubsidiaryTopManage();
            }
            if (null != h.getTopInspector()) {
                sum += h.getTopInspector();
            }
            if (null != h.getSubsidiaryTopInspector()) {
                sum += h.getSubsidiaryTopInspector();
            }
            if (null != h.getManage()) {
                sum += h.getManage();
            }
            if (null != h.getSubsidiaryManage()) {
                sum += h.getSubsidiaryManage();
            }
            if (null != h.getCharge()) {
                sum += h.getCharge();
            }
            if (null != h.getDirector()) {
                sum += h.getDirector();
            }
            if (null != h.getStaff()) {
                sum += h.getStaff();
            }
            h.setSummation(sum);
        }
        return hrRecruitmentNeeds;
    }

    public List<HrRecruitmentNeeds> statisticsDataByManageGroup(HrRecruitmentNeedsArgs entity) {
        return hrRecruitmentNeedsDao.statisticsDataByManageGroup(entity);
    }

    public List<HrRecruitmentNeeds> statisticsDataByDepartmentLevel(HrRecruitmentNeedsArgs entity) {
        return hrRecruitmentNeedsDao.statisticsDataByDepartmentLevel(entity);
    }

    public List<HrRecruitmentNeeds> statisticsDataByCompanyLevel(HrRecruitmentNeedsArgs entity) {
        return hrRecruitmentNeedsDao.statisticsDataByCompanyLevel(entity);
    }
}
