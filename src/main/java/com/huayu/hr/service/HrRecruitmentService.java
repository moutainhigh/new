package com.huayu.hr.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.ExcelOperateUtil;
import com.huayu.hr.dao.HrRecruitmentPlanDao;
import com.huayu.hr.dao.HrRecruitmentScheduleDao;
import com.huayu.hr.dao.HrRecruitmentStoreDao;
import com.huayu.hr.domain.HrRecruitmentPlan;
import com.huayu.hr.domain.HrRecruitmentSchedule;
import com.huayu.hr.domain.HrRecruitmentStore;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
@Service
public class HrRecruitmentService {

    @Autowired
    private HrRecruitmentScheduleDao hrRecruitmentScheduleDao;

    @Autowired
    private HrRecruitmentPlanDao hrRecruitmentPlanDao;

    @Autowired
    private HrRecruitmentStoreDao hrRecruitmentStoreDao;

    @Transactional
    public void insert(HrRecruitmentSchedule hrRecruitmentSchedule) {
        hrRecruitmentSchedule.setStatus(0);
        hrRecruitmentSchedule.setCreateDate(new Date());
        hrRecruitmentSchedule.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (1 != hrRecruitmentScheduleDao.postData(hrRecruitmentSchedule)) {
            throw new BusinessException("保存出错!");
        }
    }

    public Page<HrRecruitmentSchedule> scheduleListData(HrRecruitmentSchedule hrRecruitmentSchedule, Pageable pageable) {
        return hrRecruitmentScheduleDao.listData(hrRecruitmentSchedule, pageable);
    }

    public HrRecruitmentSchedule getHrRecruitmentScheduleById(Integer id) {
        HrRecruitmentSchedule HrRecruitmentSchedule = new HrRecruitmentSchedule();
        HrRecruitmentSchedule.setId(id);
        return hrRecruitmentScheduleDao.getOneHrRecruitmentSchedule(HrRecruitmentSchedule);
    }

    @Transactional
    public void updateHrRecruitmentSchedule(HrRecruitmentSchedule hrRecruitmentSchedule) {
        if (1 != hrRecruitmentScheduleDao.updateHrRecruitmentSchedule(hrRecruitmentSchedule)) {
            throw new BusinessException("修改失败!");
        }
    }

    @Transactional
    public void deleteOneHrRecruitmentSchedule(HrRecruitmentSchedule hrRecruitmentSchedule) {
        hrRecruitmentSchedule.setDeleteDate(new Date());
        hrRecruitmentSchedule.setDeleteUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (1 != hrRecruitmentScheduleDao.deleteOneHrRecruitmentSchedule(hrRecruitmentSchedule)) {
            throw new BusinessException("删除失败!");
        }
    }

    public List<HrRecruitmentSchedule> getHrRecruitmentScheduleByids(String ids) {
        List<HrRecruitmentSchedule> list = new ArrayList<>();
        String[] split = ids.split(",");
        for (String id : split) {
            HrRecruitmentSchedule hrRecruitmentSchedule = new HrRecruitmentSchedule();
            hrRecruitmentSchedule.setId(Integer.parseInt(id));
            HrRecruitmentSchedule oneHrRecruitmentSchedule = hrRecruitmentScheduleDao.getOneHrRecruitmentSchedule(hrRecruitmentSchedule);
            list.add(oneHrRecruitmentSchedule);
        }
        return list;
    }

    public List<HrRecruitmentSchedule> getAllHrRecruitmentSchedule(String field) {
        HrRecruitmentSchedule hrRecruitmentSchedule = new HrRecruitmentSchedule();
        hrRecruitmentSchedule.setStatus(0);
        return hrRecruitmentScheduleDao.getAllHrRecruitmentSchedule(hrRecruitmentSchedule, field);
    }


    @Transactional
    public List<HrRecruitmentSchedule> scheduleStatisticsData(HrRecruitmentSchedule hrRecruitmentSchedule) {
        if (null==hrRecruitmentSchedule.getStartDate() || null == hrRecruitmentSchedule.getEndDate()){
            return Collections.emptyList();
        }
        return hrRecruitmentScheduleDao.scheduleStatisticsData(hrRecruitmentSchedule);
    }

    public Page<HrRecruitmentPlan> planListData(HrRecruitmentPlan plan, Pageable pageable) {
        return hrRecruitmentPlanDao.listData(plan, pageable);
    }


    /**
     * 添加招聘计划
     * @param plan
     */
    @Transactional
    public void insertPlan(HrRecruitmentPlan plan) {
        plan.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        plan.setCreatetime(new Date());
        plan.setStatus(0);
        if (hrRecruitmentPlanDao.postData(plan)!=1){
            throw new BusinessException("添加失败");
        }
    }

    public HrRecruitmentPlan getPlanOne(HrRecruitmentPlan plan) {

        return hrRecruitmentPlanDao.getPlanOne(plan);
    }

    @Transactional
    public void updatePlan(HrRecruitmentPlan plan) {
        plan.setUpdateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        plan.setUpdatetime(new Date());
        if(hrRecruitmentPlanDao.updatePlan(plan)!=1){
            throw new BusinessException("修改计划失败");
        }
    }

    @Transactional
    public void deletePlan(HrRecruitmentPlan plan) {
        plan.setDeleteUser(SpringSecurityUtil.getUser().getUserId().intValue());
        plan.setDeletetime(new Date());
        plan.setStatus(1);
        if(hrRecruitmentPlanDao.deletePlan(plan)!=1){
            throw new BusinessException("删除计划失败");
        }
    }

    /**
     * 导出统计数据
     * @param plan
     * @return
     */
    public List<HrRecruitmentPlan> downloadPlanData(HrRecruitmentPlan plan) {
        return hrRecruitmentPlanDao.downloadPlanData(plan);
    }

    /**
     * 计划统计
     * @param plan
     * @return
     */
    public List<HrRecruitmentPlan> planStatisticsData(HrRecruitmentPlan plan) {

        return hrRecruitmentPlanDao.planStatisticsData(plan);
    }


    /**
     * 添加人才库信息
     * @param store
     */
    @Transactional
    public void insertStore(HrRecruitmentStore store) {
        User user = SpringSecurityUtil.getUser();
        store.setCreateUser(user.getUserId().intValue());
        store.setCreatetime(new Date());
        store.setStatus(0);
        store.setCompany(user.getCurrCompanyId());
        if (hrRecruitmentStoreDao.postData(store)!=1){
            throw new BusinessException("添加失败");
        }
    }


    @Transactional
    public void insertStore(Workbook workbook) throws Exception {
        Sheet sheet = ExcelOperateUtil.getSheetByNum(workbook, 1);
        int rowNum = ExcelOperateUtil.getRowNum(sheet);
        HrRecruitmentStore store;
        Date date = new Date();
        User user = SpringSecurityUtil.getUser();
        for (int i = 2; i < rowNum; i++) {
            List<String> data = ExcelOperateUtil.getRowDataList(sheet.getRow(i),22);
            if (StringUtils.isBlank(data.get(1))){
                continue;
            }
            store = new HrRecruitmentStore();
            store.setCompany(user.getCurrCompanyId());
            store.setCreateUser(user.getUserId().intValue());
            store.setCreatetime(date);
            store.setStatus(0);
            store.setName(data.get(1));
            store.setSex(data.get(2));
            store.setAskJob(data.get(3));
            store.setIdCard(data.get(4));
            store.setBirth(data.get(5));
            store.setSchool(data.get(6));
            store.setEducation(data.get(7));
            store.setProfession(data.get(8));
            store.setLiveAddress(data.get(9));
            store.setJoinWorkDate(data.get(10));
            store.setCompanyName(data.get(11));
            store.setJob(data.get(12));
            store.setRecentWorkTime(data.get(13));
            store.setLeaveReason(data.get(14));
            store.setResumeSrc(data.get(15));
            store.setMobile(data.get(16));
            store.setEmail(data.get(17));
            store.setAuditionDate(data.get(18));
            store.setAuditionUser(data.get(19));
            store.setNote(data.get(20));
            store.setOtherExperience(data.get(21));
            if (hrRecruitmentStoreDao.postData(store)!=1){
                throw new BusinessException("保存数据失败");
            }
        }
    }

    public HrRecruitmentStore getStoreOne(HrRecruitmentStore store) {

        return hrRecruitmentStoreDao.getStoreOne(store);
    }

    @Transactional
    public void updateStore(HrRecruitmentStore store) {
        User user = SpringSecurityUtil.getUser();
        store.setCompany(user.getCurrCompanyId());
        store.setUpdateUser(user.getUserId().intValue());
        store.setUpdatetime(new Date());
        if(hrRecruitmentStoreDao.updateStore(store)!=1){
            throw new BusinessException("修改失败");
        }
    }

    @Transactional
    public void deleteStore(HrRecruitmentStore store) {
        store.setDeleteUser(SpringSecurityUtil.getUser().getUserId().intValue());
        store.setDeletetime(new Date());
        store.setStatus(1);
        if(hrRecruitmentStoreDao.deleteStore(store)!=1){
            throw new BusinessException("删除失败");
        }
    }

    public Page<HrRecruitmentStore> storeListData(HrRecruitmentStore store, Pageable pageable) {
        return hrRecruitmentStoreDao.listData(store, pageable);
    }

    public List<HrRecruitmentStore> storeListData(HrRecruitmentStore store) {
        return hrRecruitmentStoreDao.listData(store);
    }

}
