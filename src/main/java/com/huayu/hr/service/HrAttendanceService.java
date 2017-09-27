package com.huayu.hr.service;

import com.alibaba.fastjson.JSON;
import com.huayu.hr.dao.*;
import com.huayu.hr.domain.*;
import com.huayu.hr.web.args.HrAtteAttDetailArgs;
import com.huayu.hr.web.args.HrAtteSchedulArgs;
import com.huayu.hr.web.args.HrAtteSchedulDetailArgs;
import com.huayu.hr.web.args.HrAtteShiftArgs;
import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.user.domain.User;
import org.junit.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2017/4/5.
 */
@Service
public class HrAttendanceService {

    @Autowired
    private HrAtteShiftDao hrAtteShiftDao;

    @Autowired
    private HrAtteSchedulDao hrAtteSchedulDao;

    @Autowired
    private HrAtteSchedulDetailDao hrAtteSchedulDetailDao;

    @Autowired
    private CommSequenceDao commSequenceDao;

    @Autowired
    private HrAtteHolidaysDao hrAtteHolidaysDao;

    @Autowired
    private HrAtteTravelDao hrAtteTravelDao;

    @Autowired
    private HrAtteOvertimeDao hrAtteOvertimeDao;

    @Autowired
    private HrAtteAttDetailDao hrAtteAttDetailDao;

    @Autowired
    private HrEmployeeDao employeeDao;

    /**
     * 添加班次
     * @param shift
     * @return
     */
    @Transactional
    public BaseResult addShift(HrAtteShift shift){
        BaseResult result = BaseResult.initBaseResult();
        shift.setCreateTime(new Date());
        shift.setStatus(0);
        if (hrAtteShiftDao.addHrAtteShift(shift)!=1){
            throw new BusinessException("保存班次失败");
        }
        result.setSuccess();
        return result;
    }

    /**
     * 修改班次
     * @param shift
     * @return
     */
    @Transactional
    public BaseResult updateShift(HrAtteShift shift){
        BaseResult result = BaseResult.initBaseResult();
        shift.setUpdateTime(new Date());
        if (hrAtteShiftDao.updateHrAtteShift(shift)!=1){
            throw new BusinessException("修改班次失败");
        }
        result.setSuccess();
        return result;
    }

    public Page<HrAtteShift> shiftListData(HrAtteShiftArgs args, Pageable pageable) {
        HrAtteShift shift = new HrAtteShift();
        BeanUtils.copyProperties(args,shift);
        return hrAtteShiftDao.getShiftListData(shift, pageable);
    }

    public List<HrAtteShift> shiftListData() {
        HrAtteShift shift = new HrAtteShift();
        return hrAtteShiftDao.getShiftListData(shift);
    }

    public HrAtteShift getOneShift(HrAtteShift shift){
        return hrAtteShiftDao.getOneById(shift);
    }

    @Transactional
    public void addSchedul(HrAtteSchedulArgs args){
        User user = SpringSecurityUtil.getUser();
        Integer userId = user.getUserId().intValue();
        List<Integer> empIds = JSON.parseArray(args.getEmpIds(), Integer.class);
        checkSchedul(empIds,args.getStartDate(),args.getEndDate());
        List<Integer> cycleDetail = JSON.parseArray(args.getCycleDetail(), Integer.class);
        SqlParameterSource[] sqlParameterSources;
        HrAtteSchedulDetail detail;
        Date now = new Date();
        int size =empIds.size();
        String schedulSql = "INSERT INTO hr_atte_schedul (id, empId, startDate, endDate, forced, cycle, status, createTime, createUser) VALUES (:id, :empId, :startDate, :endDate, :forced, :cycle, :status, :createTime, :createUser)";
        sqlParameterSources = new SqlParameterSource[size];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_atte_schedul", size));
        for (int i = 0; i < size; i++){
            Integer empId = empIds.get(i);
            HrAtteSchedul schedul = new HrAtteSchedul();
            BeanUtils.copyProperties(args,schedul);
            schedul.setId(ids[0].intValue()+i+1);
            schedul.setEmpId(empId);
            schedul.setStatus(0);
            schedul.setCreateTime(now);
            schedul.setCreateUser(userId);
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(schedul);
        }
        int[] flags = hrAtteSchedulDetailDao.batchUpdate(schedulSql, sqlParameterSources);
        for (int k = 0; k < flags.length; k++){
            if (flags[k]==0){
                throw new BusinessException("保存班次失败");
            }
        }
        int _len = cycleDetail.size();
        sqlParameterSources = new SqlParameterSource[_len*size];
        Long[] detailIds  = commSequenceDao.getKey(new CommSequence("hr_atte_schedul_detail", _len*size));
        int count = 0;
        for (int i = 0; i<ids[1]-ids[0]; i++){
            for (int k = 0; k < _len; k++) {
                detail = new HrAtteSchedulDetail();
                detail.setId(detailIds[0].intValue()+count+1);
                detail.setSchedulId(ids[0].intValue()+i+1);
                detail.setShiftId(cycleDetail.get(k));
                detail.setStep(k);
                detail.setStatus(0);
                detail.setCreateTime(now);
                detail.setCreateUser(userId);
                sqlParameterSources[count] = new BeanPropertySqlParameterSource(detail);
                count++;
            }
        }
        String sql = "INSERT INTO hr_atte_schedul_detail (id,schedulId,shiftId,step,status,createTime,createUser,updateTime,updateUser,deleteTime,deleteUser) VALUES (:id,:schedulId,:shiftId,:step,:status,:createTime,:createUser,:updateTime,:updateUser,:deleteTime,:deleteUser)";
        flags = hrAtteSchedulDetailDao.batchUpdate(sql, sqlParameterSources);
        for (int k = 0; k < flags.length; k++){
            if (flags[k]==0){
                throw new BusinessException("保存班次详情失败");
            }
        }
    }

    @Transactional
    void checkSchedul(List<Integer> empIds, Date startDate, Date endDate){
        HrAtteSchedul tmp = new HrAtteSchedul();
        tmp.setStartDate(startDate);
        tmp.setEndDate(endDate);
        List<HrAtteSchedul> schedules = hrAtteSchedulDao.countSchedul(tmp, empIds);
        if (schedules.size()>0){
            StringBuilder stringBuilder = new StringBuilder();
            for (HrAtteSchedul s : schedules){
                stringBuilder.append(s.getEmpName()).append(",");
            }
            stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
            throw new BusinessException(stringBuilder.toString()+"已有排班数据");
        }
    }


    /**
     *  已有班次时间  ---------
     *  排班时间 *******
     *
     *  1、
     *        ----------
     *  *******************
     *
     *  2、
     *   ----------------
     *       *********
     * 3、
     *   -----------
     *         **************
     *  4、
     *           -------------
     *   **************
     *
     * @param startDate
     * @param endDate
     * @param empId
     */
    @Transactional
    void coverSchedul(Date startDate, Date endDate, Integer empId){
        List<HrAtteSchedul> schedules = hrAtteSchedulDao.getSchedules(new HrAtteSchedul(empId));
        if (!CollectionUtils.isEmpty(schedules)){
            for (HrAtteSchedul s : schedules){
                if (s.getStartDate().after(startDate)&&s.getEndDate().before(endDate)){ // 1
                    this.deleteSchedulAndDetail(s.getId(),empId);
                }else if(s.getStartDate().before(startDate)&&s.getEndDate().after(endDate)){ // 2

                }else if (s.getStartDate().before(startDate)&&s.getEndDate().after(startDate)&&s.getEndDate().before(endDate)){

                }else {

                }
            }
        }
    }

    @Transactional
    public void deleteSchedulAndDetail(Integer id, Integer empId){
        HrAtteSchedul schedul = new HrAtteSchedul();
        schedul.setEmpId(empId);
        schedul.setId(id);
        hrAtteSchedulDao.deleteSchedulAndDetail(schedul);
    }


    /**
     * 添加班次
     * @param holiday
     * @return
     */
    @Transactional
    public BaseResult addHoliday(HrAtteHolidays holiday){
        BaseResult result = BaseResult.initBaseResult();
        holiday.setCreateTime(new Date());
        holiday.setStatus(0);
        if (hrAtteHolidaysDao.addHoliday(holiday)!=1){
            throw new BusinessException("保存班次失败");
        }
        result.setSuccess();
        return result;
    }

    @Transactional
    public BaseResult updateHoliday(HrAtteHolidays holiday) {
        BaseResult result = BaseResult.initBaseResult();
        holiday.setUpdateTime(new Date());
        if (hrAtteHolidaysDao.updateHoliday(holiday)!=1){
            throw new BusinessException("修改班次失败");
        }
        result.setSuccess();
        return result;
    }


    public Page<HrAtteHolidays> holidayListData(HrAtteHolidays entity, Pageable pageable) {
        entity.setStatus(0);
        return hrAtteHolidaysDao.getHolidayListData(entity, pageable);
    }


    public HrAtteHolidays getHolidayOne(HrAtteHolidays holiday) {

        return hrAtteHolidaysDao.getOneById(holiday);
    }

    @Transactional
    public void deleteHoliday(HrAtteHolidays holiday) {
        holiday.setDeleteTime(new Date());
        holiday.setStatus(1);
        if (hrAtteHolidaysDao.deleteHoliday(holiday)!=1){
            throw new BusinessException("删除假日失败");
        }
    }

    /**
     * 组装排班列表 月视图
     * @param date
     * @return
     */
    public List<String> buildCalendarDate(Date date) {
        String[] weekDaysName = { "日", "一", "二", "三", "四", "五", "六" };
        int week = DateTimeUtil.getFirstDateWeekOfMonth(date);
        List<String> dateList = new ArrayList<>();
        for (int i = 0, max = DateTimeUtil.getLastDateNumOfMonth(date); i< max; i++){
            String s = weekDaysName[(week+i)%7];
            dateList.add((i+1)+","+s);
        }
        return dateList;
    }

    /**
     * 排班列表数据
     * @param args
     * @return
     */
    public List<HrAtteSchedul> schedulListData(HrAtteSchedulDetailArgs args) {
        User user = SpringSecurityUtil.getUser();
        args.setCompany(user.getCurrCompanyId());
        List<HrAtteSchedulDetail> schedulDetailListData = hrAtteSchedulDetailDao.getSchedulDetailListData(args);
        List<HrAtteSchedul> scheduls = rebuildList(schedulDetailListData);
        if (schedulDetailListData.size()>0){
            int max = DateTimeUtil.getLastDateNumOfMonth(args.getStartDate());
            for(int i=0, size=scheduls.size(); i<size; i++){
                HrAtteSchedul schedul = scheduls.get(i);
                copySchedulDetail(max,schedul.getDetails());
            }
//            Iterator<HrAtteSchedul> iterator = scheduls.iterator();
//            while (iterator.hasNext()){
//                HrAtteSchedul schedul = iterator.next();
//                copySchedulDetail(max,schedul.getDetails());
//            }
        }
        return scheduls;
    }

    private List<HrAtteSchedul> rebuildList(List<HrAtteSchedulDetail> schedulDetailListData){
        List<HrAtteSchedul> schedules = new ArrayList<>();
        List<HrAtteSchedulDetail> details = null;
        HrAtteSchedul schedul;
//        Iterator<HrAtteSchedulDetail> iterator = schedulDetailListData.iterator();
        Integer lastSchedulId = null;
        for(int i=0, size=schedulDetailListData.size(); i<size; i++){
            HrAtteSchedulDetail detail = schedulDetailListData.get(i);
            if (lastSchedulId == null || lastSchedulId != detail.getSchedulId()){
                details = new ArrayList<>();
                schedul = new HrAtteSchedul();
                schedul.setEmpId(detail.getEmpId());
                schedul.setStartDate(detail.getStartDate());
                schedul.setEndDate(detail.getEndDate());
                schedul.setCycle(detail.getCycle());
                schedul.setForced(detail.getForced());
                schedul.setDetails(details);
                schedules.add(schedul);
            }
            details.add(detail);
            lastSchedulId = detail.getSchedulId();
        }
//        while (iterator.hasNext()){
//            HrAtteSchedulDetail detail = iterator.next();
//            if (lastSchedulId == null || lastSchedulId != detail.getSchedulId()){
//                details = new ArrayList<>();
//                schedul = new HrAtteSchedul();
//                schedul.setEmpId(detail.getEmpId());
//                schedul.setStartDate(detail.getStartDate());
//                schedul.setEndDate(detail.getEndDate());
//                schedul.setCycle(detail.getCycle());
//                schedul.setForced(detail.getForced());
//                schedul.setDetails(details);
//                schedules.add(schedul);
//            }
//            details.add(detail);
//            lastSchedulId = detail.getSchedulId();
//        }
        return schedules;
    }

    private void  copySchedulDetail(int max, List<HrAtteSchedulDetail> details){
        if (!CollectionUtils.isEmpty(details)){
            int count = details.size();
            List<HrAtteSchedulDetail> tmpDetails = new ArrayList<>();
            for(int k = 0,round =max/details.size()+1; k < round ; k++){
                for (HrAtteSchedulDetail tmp : details){
                    if (count<max){
                        HrAtteSchedulDetail t = new HrAtteSchedulDetail();
                        t.setShiftName(tmp.getShiftName());
                        tmpDetails.add(t);
                        count++;
                    }
                }
            }
            details.addAll(tmpDetails);
        }
    }

    /**
     * 添加出差
     * @param travel
     */
    @Transactional
    public void addHrAtteTravel(HrAtteTravel travel){
        travel.setCreateTime(new Date());
        travel.setStatus(0);
        if (hrAtteTravelDao.addHrAtteTravel(travel)!=1){
            throw new BusinessException("新增出差事项失败");
        }
    }

    @Transactional
    public void updateHrAtteTravel(HrAtteTravel travel) {
        travel.setUpdateTime(new Date());
        if (hrAtteTravelDao.updateHrAtteTravel(travel)!=1){
            throw new BusinessException("修改出差事项失败");
        }
    }

    public Page<HrAtteTravel> travelListData(HrAtteTravel travel, Pageable pageable) {
        return hrAtteTravelDao.getTravelListData(travel,pageable);
    }

    public HrAtteTravel getTravelOne(HrAtteTravel travel) {

        return hrAtteTravelDao.getTravelOne(travel);
    }


    /**
     * 添加加班
     * @param entity
     */
    @Transactional
    public void addHrAtteOvertime(HrAtteOvertime entity){
        entity.setCreateTime(new Date());
        entity.setStatus(0);
        if (hrAtteOvertimeDao.addHrAtteOvertime(entity)!=1){
            throw new BusinessException("新增出差事项失败");
        }
    }

    @Transactional
    public void updateHrAtteOvertime(HrAtteOvertime entity) {
        entity.setUpdateTime(new Date());
        if (hrAtteOvertimeDao.updateHrAtteOvertime(entity)!=1){
            throw new BusinessException("修改出差事项失败");
        }
    }

    public Page<HrAtteOvertime> overtimelListData(HrAtteOvertime entity, Pageable pageable) {
        return hrAtteOvertimeDao.getOvertimeListData(entity,pageable);
    }

    public HrAtteOvertime getOvertimeOne(HrAtteOvertime entity) {

        return hrAtteOvertimeDao.getOvertimeOne(entity);
    }

    /**
     * 从考勤机器上获取 考勤数据
     * @param args
     * @return
     */
    public List<HrAtteAttDetail> getAttDetailListFromSQLServer(HrAtteAttDetailArgs args){

        return hrAtteAttDetailDao.getAttDetailListFromSQLServer(args);
    }

    /**
     * 检查是否存在考勤数据
     * @param detail
     * @return
     */
    @Transactional
    public boolean checkAttDetailExist(HrAtteAttDetailArgs detail){

        return hrAtteAttDetailDao.checkAttDetailExist(detail)>0;
    }

    @Transactional
    public void addAttDetail(HrAtteAttDetailArgs args, List<HrAtteAttDetail> detailList){
        SqlParameterSource[] sqlParameterSources;
        int size =detailList.size();
        sqlParameterSources = new SqlParameterSource[size];
        Date now = new Date();
        Map<Integer, Integer> idMap = args.getBadgeEmpIdMap();
        for(int i=0; i<size; i++){
            HrAtteAttDetail hrAtteAttDetail = detailList.get(i);
            hrAtteAttDetail.setEmpId(idMap.get(hrAtteAttDetail.getBadgenumber()));
            hrAtteAttDetail.setCreatetime(now);
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(hrAtteAttDetail);
        }
        int[] flags = hrAtteAttDetailDao.batchAddAttDetail(sqlParameterSources);
        for (int k = 0; k < flags.length; k++){
            if (flags[k]==0){
                throw new BusinessException("导入考勤数据失败");
            }
        }
    }

    public Page<HrAtteAttDetail> attDetailListData(HrAtteAttDetailArgs entity, Pageable pageable) {

        return hrAtteAttDetailDao.getAttDetailList(entity,pageable);
    }

    /**
     * 统计 公司/部门的人员考勤
     * @param entity
     * @param pageable
     * @return
     */
    public Page<HrAtteAttDetail> statisticsAttResult(HrAtteAttDetailArgs entity, Pageable pageable){

        return  hrAtteAttDetailDao.statisticsAttDetailList(entity, pageable);
    }

    /**
     * 获取异常考勤数据
     * @param entity
     * @param pageable
     * @return
     */
    public Page<HrAtteAttDetail> statisticsExceptionAttResult(HrAtteAttDetailArgs entity, Pageable pageable){

        //TODO 这个地方 还没写方法
        return  hrAtteAttDetailDao.statisticsAttDetailList(entity, pageable);
    }

    /**
     * 统计单个人员考勤
     *
     */
    public HrAtteAttDetail statisticsOneAttResult(Integer empId, Date startDate, Date endDate){
        Assert.assertNotNull("人员ID不能为空",empId);
        Assert.assertNotNull("开始时间不能为空",startDate);
        Assert.assertNotNull("结束时间不能为空",endDate);
        HrAtteAttDetailArgs entity = new HrAtteAttDetailArgs();
        entity.setEmpId(empId);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        List<HrAtteAttDetail> details = hrAtteAttDetailDao.getAttDetailList(entity);
        HrAtteAttDetail result = new HrAtteAttDetail();
        for (int i = 0, size = details.size(); i<size; i++){
            HrAtteAttDetail detail = details.get(i);
            if (i==0){
                result.setCompanyName(detail.getCompanyName());
                result.setDepartmentName(detail.getDepartmentName());
                result.setEmpName(detail.getEmpName());
            }
            result.addWorkDay(detail.getWorkDay());
            result.addRealWorkDay(detail.getRealWorkDay());
            result.addLate(detail.getLate());
            result.addEarly(detail.getEarly());
            result.addNoIn(detail.getNoIn());
            result.addNoOut(detail.getNoOut());
        }
        return result;
    }

    public List<HrAtteHolidays> getAllHoliday(HrAtteHolidays hrAtteHolidays) {
        return hrAtteHolidaysDao.getAllHoliday(hrAtteHolidays);
    }


}
