package com.huayu.signWechat.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.RandomUtil;
import com.huayu.hr.dao.CompanyDao;
import com.huayu.hr.dao.DepartmentDao;
import com.huayu.hr.dao.HrEmployeeDao;
import com.huayu.hr.domain.HrAtteHolidays;
import com.huayu.hr.domain.HrCompany;
import com.huayu.hr.domain.HrDepartment;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.service.HrAttendanceService;
import com.huayu.signWechat.dao.*;
import com.huayu.signWechat.domain.*;
import com.huayu.signWechat.utils.HolidayUitl;
import com.huayu.signWechat.utils.OASignUtil;
import com.huayu.signWechat.web.args.*;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/7/10.
 */
@Service
public class HrSignService {

    private static Logger logger = LoggerFactory.getLogger(HrSignService.class);

    @Autowired
    private HrSignDetailDao hrSignDetailDao;
    @Autowired
    private HrSignDepartmentDao hrSignDepartmentDao;
    @Autowired
    private HrSignUserDao hrSignUserDao;
    @Autowired
    private HrSignUnusualDao hrSignUnusualDao;
    @Autowired
    private HrSignEgressDao hrSignEgressDao;
    @Autowired
    private HrSignLeaveDao hrSignLeaveDao;
    @Autowired
    private HrEmployeeDao hrEmployeeDao;
    @Autowired
    private HrSignStatisticsDao hrSignStatisticsDao;
    @Autowired
    private CommSequenceDao commSequenceDao;
    @Autowired
    private HrAttendanceService hrAttendanceService;
    @Autowired
    private HrSignPatchSonDao hrSignPatchSonDao;
    @Autowired
    private HrSignPatchFormmainDao hrSignPatchFormmainDao;
    @Autowired
    private HrSignWorktimeDao hrSignWorktimeDao;
    @Autowired
    private HrSignEgressMainDao hrSignEgressMainDao;
    @Autowired
    private HrSignEgressSonDao hrSignEgressSonDao;
    @Autowired
    private HrSignLeaveMainDao hrSignLeaveMainDao;
    @Autowired
    private HrSignLeaveSonDao hrSignLeaveSonDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private HrSignTodayTempDao hrSignTodayTempDao;
    @Autowired
    private HrSignOutsideDao hrSignOutsideDao;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Page<HrSignDetail> signDetailListData(HrSignDetailArgs hrSignDetailArgs, Pageable pageable) {
        return hrSignDetailDao.signDetailListData(hrSignDetailArgs, pageable);
    }

    /**
     * 请假列表
     * @param hrSignLeaveArgs
     * @param pageable
     * @return
     */
    public Page<HrSignLeave> leaveDetailListData(HrSignLeaveArgs hrSignLeaveArgs, Pageable pageable) {
        Page<HrSignLeave> hrSignLeaves = hrSignLeaveDao.signLeaveListData(hrSignLeaveArgs, pageable);
        for (HrSignLeave h :hrSignLeaves) {
            h.setLeaveType(LeaveTypeEnum.getName(h.getLeaveType()));
        }
        return hrSignLeaves;
    }

    @Transactional
    public void saveStatisticsData(List<HrSignStatistics> statistics, Date startDate, Date endDate) {
        int size = statistics.size();
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_sign_statistics", size));
        SqlParameterSource[] sqlParameterSource = new SqlParameterSource[size];
        for (int i = 0; i<size;i++) {
            Long key = ids[0] + i + 1;
            HrSignStatistics hrSignStatistics = statistics.get(i);
            hrSignStatistics.setId(key.intValue());
            hrSignStatistics.setStartDate(startDate);
            hrSignStatistics.setEndDate(endDate);
            hrSignStatistics.setStatus(0);
            hrSignStatistics.setCreateTime(new Date());
            sqlParameterSource[i] = new BeanPropertySqlParameterSource(hrSignStatistics);
        }
        hrSignStatisticsDao.batchUpdate(sqlParameterSource);
    }

    /**
     * 获取该时间段该月所有已休的法定假和周末
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public List<Calendar> getHolidayList(Date startDate, Date endDate) throws Exception {
        List<Calendar> holidayList = new ArrayList<>();
        HrAtteHolidays hrAtteHolidays = new HrAtteHolidays();
        hrAtteHolidays.setStatus(0);
        List<HrAtteHolidays> hrAtteHolidaysList = hrAttendanceService.getAllHoliday(hrAtteHolidays);
        for (HrAtteHolidays holiday : hrAtteHolidaysList) {
            Date startTime = holiday.getStartTime();
            Date endTime = holiday.getEndTime();
            List<Calendar> holidayList1 = HolidayUitl.getDayListInScope(startTime, endTime);
            holidayList.addAll(holidayList1);
        }
        List<Calendar> weekendAndHoliday = HolidayUitl.getWeekendAndHoliday(startDate,endDate, holidayList);
        return weekendAndHoliday;
    }

    /**
     * 报表
     * @param entity
     * @return
     * @throws Exception
     */
    public List<HrSignStatistics> signStatisticsData(HrSignStatistics entity) throws Exception {
        Date startDate = entity.getStartDate();
        Date endDate = entity.getEndDate();
        List<HrSignStatistics> statisticsList = hrSignStatisticsDao.getSignStatisticsData(entity);
        HrSignUser signUser = new HrSignUser();
        signUser.setCode(entity.getCode());
        signUser.setDepartmentId(entity.getDepartmentIds());
        int holiday = this.getHolidayList(startDate, endDate).size();

        if (CollectionUtils.isEmpty(statisticsList)) {
            List<HrSignUser> userList = hrSignUserDao.getUserDetailList("searchNotAll", signUser);
            List<HrSignStatistics> statistics = new ArrayList<>();
            for (HrSignUser hrSignUser :userList) {
                HrSignStatistics hrSignStatistics = new HrSignStatistics();
                hrSignStatistics.setPactCompanyName(hrSignUser.getPactCompanyName());
                hrSignStatistics.setPlateName(hrSignUser.getPlateName());
                hrSignStatistics.setCompanyName(hrSignUser.getCompanyName());
                hrSignStatistics.setDepartmentName(hrSignUser.getDepartmentName());
                hrSignStatistics.setUserNum(hrSignUser.getUserNum());
                hrSignStatistics.setBirthdate(hrSignUser.getBirthdate());
                hrSignStatistics.setAge(DateTimeUtil.getYearSpace(hrSignStatistics.getBirthdate(), new Date()));
                hrSignStatistics.setJoinCompDate(hrSignUser.getJoinCompDate());
                hrSignStatistics.setUseStatus(hrSignUser.getUseStatus());
                hrSignStatistics.setName(hrSignUser.getName());
                hrSignStatistics.setSex(hrSignUser.getSex());
                hrSignStatistics.setPosition(hrSignUser.getPosition());
                hrSignStatistics.setRestDayNum(new BigDecimal(holiday));
                hrSignStatistics.setIdCard(hrSignUser.getIdCard());
                hrSignStatistics.setInDueFormDate(hrSignUser.getInDueFormDate());
                hrSignStatistics.setUseStatus(hrSignUser.getUseStatus());
                hrSignStatistics.setTechnicalLevel(hrSignUser.getTechnicalLevel());
                hrSignStatistics.setUserCategory(hrSignUser.getUserCategory());
                hrSignStatistics.setWorkStatus(hrSignUser.getWorkStatus());
                hrSignStatistics.setPerformanceSystem(hrSignUser.getPerformanceSystem());
                String userid = hrSignUser.getUserid();

                //查异常表
                //冲销异常 放在每天的晚上定时执行

                //查询详情表
                HrSignDetail hrSignDetail = new HrSignDetail();
                hrSignDetail.setUserid(userid);
                hrSignDetail.setStartDate(startDate);
                hrSignDetail.setEndDate(endDate);
                List<HrSignDetail> hrSignDetails = hrSignDetailDao.getWorkDutyDaysSign(hrSignDetail);//完全正常的打卡记录(包含迟到早退的记录 因为迟到早退要记发工资日)

                hrSignStatistics.setWorkDutyDayNum(new BigDecimal(hrSignDetails.size()).multiply(new BigDecimal(0.5)));

                //请假
                HrSignLeaveArgs hrSignLeave1= new HrSignLeaveArgs();
                hrSignLeave1.setUserid(userid);
                hrSignLeave1.setStartTime(startDate);
                hrSignLeave1.setEndTime(endDate);
                List<HrSignLeave> leaveList1 = hrSignLeaveDao.getHrSignLeaveByUseridAndDate(hrSignLeave1);
                BigDecimal restChange = new BigDecimal(0);
                BigDecimal affairsLeave = new BigDecimal(0);
                BigDecimal sickLeave = new BigDecimal(0);
                BigDecimal elseDeduct = new BigDecimal(0);
                for (HrSignLeave h : leaveList1) {
                    if ("-1135316255604717531".equals(h.getLeaveType())) {
                        //事假
                        restChange = restChange.add(h.getRestChanged() == null ? new BigDecimal(0) : h.getRestChanged());
                        BigDecimal leaveDayInScope = this.getLeaveDayInScope(h, startDate, endDate);
                        affairsLeave = affairsLeave.add(leaveDayInScope);
                    } else if ("5705361015223765326".equals(h.getLeaveType())) {
                        //病假
                        restChange = restChange.add(h.getRestChanged() == null ? new BigDecimal(0) : h.getRestChanged());
                        BigDecimal leaveDayInScope = this.getLeaveDayInScope(h, startDate, endDate);
                        sickLeave = sickLeave.add(leaveDayInScope);
                    } else if ("-3265967495408038335".equals(h.getLeaveType())) {
                        //换休
                        restChange = restChange.add(h.getRestChanged() == null ? new BigDecimal(0) : h.getRestChanged());
                        BigDecimal leaveDayInScope = this.getLeaveDayInScope(h, startDate, endDate);
                        restChange = restChange.add(leaveDayInScope);
                    } else {
                        //其他
                        restChange = restChange.add(h.getRestChanged() == null ? new BigDecimal(0) : h.getRestChanged());
                        BigDecimal leaveDayInScope = this.getLeaveDayInScope(h, startDate, endDate);
                        elseDeduct = elseDeduct.add(leaveDayInScope);
                    }
                }
                hrSignStatistics.setAffairsLeave(affairsLeave);
                hrSignStatistics.setSickLeave(sickLeave);
                hrSignStatistics.setRestChanged(restChange);
                hrSignStatistics.setElseDeduct(elseDeduct);

                //外出
                HrSignEgress hrSignEgress = new HrSignEgress();
                hrSignEgress.setUsername(hrSignUser.getName());
                hrSignEgress.setTelephone(hrSignUser.getMobile());
                hrSignEgress.setStartTime(startDate);
                hrSignEgress.setEndTime(endDate);
                List<HrSignEgress> hrSignEgresses = hrSignEgressDao.getHrSignEgressDayList(hrSignEgress);
                Map<String,HrSignEgress> map = new HashMap<>();
                for (HrSignEgress signEgress :hrSignEgresses) {
                    String s = dateFormat.format(signEgress.getStartTime()) + "-" + dateFormat.format(signEgress.getEndTime());
                    if (!map.containsKey(s)) {
                        map.put(s, signEgress);
                    }
                }
                Collection<HrSignEgress> values = map.values();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                BigDecimal egress = new BigDecimal(0.0);
                for (HrSignEgress signEgress :values) {
                    Date startTime = signEgress.getStartTime();
                    Date endTime = signEgress.getEndTime();
                    List<Calendar> dayListInScope = HolidayUitl.getDayListInScope(startTime, endTime);
//                    List<Calendar> holidayList = this.getHolidayList(startTime, endTime);
                    if (dayListInScope != null && dayListInScope.size()==1) {
                        //外出一天
                        int i = DateTimeUtil.cutTwoDateToHour(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, DateTimeUtil.dateToString(startTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), DateTimeUtil.dateToString(endTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                        int i1 = i % 24;
                        if (i1 <= 7) { //半天
                            egress = egress.add(new BigDecimal(0.5));
                        } else {
                            egress = egress.add(new BigDecimal(1.0));
                        }
                    } else {
                        if (sdf.parse(sdf.format(endTime)).before(sdf.parse("09:10:00"))) {
                            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, this.dateFormat.format(endTime), DateTimeUtil.PLUSTYPE_DAY, -1);
                            endTime = this.sdf.parse(nowDate+" 18:00:00");
                        }
                        Date lastDateOfMonth = DateTimeUtil.getLastDateOfMonth(DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(startTime)+" 23:59:59",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                        if (endTime.after(lastDateOfMonth)) {
                            if (endDate.before(lastDateOfMonth)) {
                                endTime = endDate;
                            }else if (startDate.after(startTime)){
                                startTime = this.sdf.parse(dateFormat.format(startDate)+" 09:00:00");
                            } else if (endDate.before(endTime)) {
                                endTime = this.sdf.parse(dateFormat.format(endDate)+" 18:00:00");
                            }
                        }
                        List<Calendar> holidayList = this.getHolidayList(startTime, endTime);
                        int i = DateTimeUtil.cutTwoDateToHour(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, this.sdf.format(startTime), this.sdf.format(endTime));
                        Double count = 0.0;
                        int i1 = i % 24;
                        int day = i/24;
                        count += day;
                        if (i1 <= 7 && i1 > 0) { //半天
                            egress = egress.add(new BigDecimal(0.5)).add(new BigDecimal(count));
                        } else if (i1>7 && i1 <= 24){
                            egress = egress.add(new BigDecimal(1.0)).add(new BigDecimal(count));
                        } else if (i1 == 0) {
                            //刚好一整天
                            egress = egress.add(new BigDecimal(1));
                        }
                        egress = egress.subtract(new BigDecimal(holidayList.size()));
                    }
                }
                hrSignStatistics.setEgressDayNum(egress);
                //实际发放日
                if (null == hrSignStatistics.getWorkDutyDayNum()) {
                    hrSignStatistics.setWorkDutyDayNum(new BigDecimal(0));
                }
                //外出半天 算实际计薪日记一天 因为外出半天 剩下半天是正常的
                BigDecimal egressDayNum = hrSignStatistics.getEgressDayNum();

                //其他假
                BigDecimal elseDeduct1 = hrSignStatistics.getElseDeduct();

                BigDecimal add = hrSignStatistics.getWorkDutyDayNum().add(hrSignStatistics.getRestDayNum()
                        .add(egressDayNum).add(elseDeduct1).add(hrSignStatistics.getRestChanged()));

                hrSignStatistics.setFactPayDayNum(add);
                int lastDateNumOfMonth = DateTimeUtil.getLastDateNumOfMonth(startDate);
                List<Calendar> dayListInScope = HolidayUitl.getDayListInScope(startDate, endDate);
                hrSignStatistics.setAbsenceDayNum(new BigDecimal(dayListInScope.size()).subtract(hrSignStatistics.getFactPayDayNum()));

                HrSignUnusual hrSignUnusual = new HrSignUnusual();
                hrSignUnusual.setUserid(hrSignUser.getUserid());
                hrSignUnusual.setStartDate(startDate);
                hrSignUnusual.setEndDate(endDate);
                Boolean falg = false;
                HrSignUnusualStatistics signUnusualStatistics = new HrSignUnusualStatistics();
                List<HrSignUnusual> hrSignUnusuals = hrSignUnusualDao.getHrSignUnusualStatisticsList(hrSignUnusual);
                for (HrSignUnusual h :hrSignUnusuals) {
                    if ("1".equals(h.getStatus())) {
                        //迟到
//                    this.setNoWorkByLateOrLeaveEarly(h);
                        falg = getaBoolean(signUnusualStatistics, falg, h);
                    } else if ("2".equals(h.getStatus())) {
                        //早退
//                    this.setNoWorkByLateOrLeaveEarly(h);//根据早退尺度判定旷工
                        falg = getaBoolean(signUnusualStatistics, falg, h);
                    } else if ("3".equals(h.getStatus()) || "4".equals(h.getStatus()) || "5".equals(h.getStatus()) || "6".equals(h.getStatus()) || "7".equals(h.getStatus())) {
                        //未签到 未签退 旷工半天 旷工一天
                        falg = getaBoolean(signUnusualStatistics, falg, h);
                    }
                }
                hrSignStatistics.setNoWork(signUnusualStatistics.getNoWork());
                statistics.add(hrSignStatistics);
            }
            return statistics;
        } else {
            return statisticsList;
        }

    }

    /**
     * 冲销异常
     * @param hrSignUnusuals
     */
    @Transactional
    public void offsetUnusual(List<HrSignUnusual> hrSignUnusuals, Boolean isToday) throws ParseException {
        for (HrSignUnusual hrSignUnusual :hrSignUnusuals) {
            String checkin_type = hrSignUnusual.getCheckin_type();
            String checkin_date = DateTimeUtil.dateToString(hrSignUnusual.getCheckin_time(), DateTimeUtil.YYYY_MM_DD);
            //冲销外出
            HrSignEgress hrSignEgress = new HrSignEgress();
            hrSignEgress.setUserid(hrSignUnusual.getUserid());
            if ("1".equals(hrSignUnusual.getCheckin_type())) {
                hrSignEgress.setStartTime(this.sdf.parse(checkin_date+" 09:30:00"));
            } else if ("2".equals(hrSignUnusual.getCheckin_type())) {
                hrSignEgress.setStartTime(this.sdf.parse(checkin_date+" 17:30:00"));
            }

            List<HrSignEgress> egressList = hrSignEgressDao.getEgressByUsernameAndTel(hrSignEgress, checkin_date);
            if (!CollectionUtils.isEmpty(egressList)) {
                //只需要吧异常表状态改成外出
                hrSignUnusual.setStatus("9");
                if (isToday) {
                    HrSignTodayTemp temp = new HrSignTodayTemp();
                    BeanUtils.copyProperties(hrSignUnusual, temp);
                    temp.setStatus(9);
                    if (1 != hrSignTodayTempDao.updateSignStatus(temp, checkin_date)) {
                        logger.error("冲销外出时更新异常状态失败--临时表!");
                        throw new BusinessException("冲销外出时更新异常状态失败");
                    }
                } else {
                    int i = hrSignUnusualDao.updateHrSignUnusualStatus(hrSignUnusual, checkin_date);
                    if (i != 1) {
                        logger.error("冲销外出时更新异常状态失败!  "+i);
                    }
                }
            }

            //冲销请假
            HrSignLeave hrSignLeave = new HrSignLeaveArgs();
            hrSignLeave.setUserid(hrSignUnusual.getUserid());
            if ("1".equals(hrSignUnusual.getCheckin_type())) {
                hrSignLeave.setStartLeave(this.sdf.parse(checkin_date+" 09:30:00"));
            } else if ("2".equals(hrSignUnusual.getCheckin_type())) {
                hrSignLeave.setStartLeave(this.sdf.parse(checkin_date+" 17:30:00"));
            }
            List<HrSignLeave> leaveList = hrSignLeaveDao.getHrSignLeaveByUsernameAndTelAndDate(hrSignLeave,checkin_date);
            if (!CollectionUtils.isEmpty(leaveList)) {
                //把异常表的状态改成请假
                hrSignUnusual.setStatus("8");
                if (isToday) {
                    HrSignTodayTemp temp = new HrSignTodayTemp();
                    BeanUtils.copyProperties(hrSignUnusual, temp);
                    temp.setStatus(8);
                    if (1 != hrSignTodayTempDao.updateSignStatus(temp, checkin_date)) {
                        logger.error("冲销请假时更新异常状态失败--临时表!");
                        throw new BusinessException("冲销外出时更新异常状态失败");
                    }
                } else {
                    int i = hrSignUnusualDao.updateHrSignUnusualStatus(hrSignUnusual, checkin_date);
                    if (1 != i) {
                        logger.error(i+" ");
                        logger.error("冲销请假时更新异常状态失败!");
                        throw new BusinessException("冲销请假时更新异常状态失败");
                    }
                }
            }

                //冲销补登
                HrSignPatchSon patchSon = new HrSignPatchSon();
                patchSon.setUserid(hrSignUnusual.getUserid());
                patchSon.setPatchDate(dateFormat.parse(dateFormat.format(hrSignUnusual.getCheckin_time())));
                if (null != hrSignUnusual.getCheckin_type() && !"".equals(hrSignUnusual.getCheckin_type())) {
                    patchSon.setPatchType(Integer.parseInt(hrSignUnusual.getCheckin_type()));
                }
                List<HrSignPatchSon> hrSignPatchSons =hrSignPatchSonDao.getHrSignPatchByUsernameAndTelAndDate(patchSon);
                //补登后把异常表状态改成0 并且 把详情表的异常状态改成0
                this.updateUnusualAndDetail(hrSignUnusual, checkin_date, hrSignPatchSons,isToday);
        }
    }

    @Transactional
    private void updateUnusualAndDetail(HrSignUnusual hrSignUnusual, String checkin_date, List List,Boolean isToday) {
        if (!CollectionUtils.isEmpty(List)) {
            hrSignUnusual.setStatus("10");
            if (!isToday) {
                if (1 != hrSignUnusualDao.updateHrSignUnusualStatus(hrSignUnusual, checkin_date)) {
                    logger.error("冲销补登时更新异常状态失败!");
                    throw new BusinessException("冲销补登时更新异常状态失败");
                }
            }
            HrSignDetail hrSignDetail = new HrSignDetail();
            hrSignDetail.setUserid(hrSignUnusual.getUserid());
            hrSignDetail.setCheckin_type(hrSignUnusual.getCheckin_type());
            hrSignDetail.setCheckDate(hrSignUnusual.getCheckin_time());
            hrSignDetail.setUpdateTime(new Date());
            hrSignDetail.setException_type("0");
            hrSignDetail.setStatus(10);
            HrSignTodayTemp temp =  new HrSignTodayTemp();
            BeanUtils.copyProperties(hrSignDetail, temp);
            temp.setStatus(10);
            if (isToday) {
                if (1 != hrSignTodayTempDao.updateHrSignDetailOffset(temp,checkin_date)) {
                    logger.error("冲销补登时更新详情表的异常类型失败");
                    throw new BusinessException("冲销补登时更新详情表的异常类型失败");
                }
            } else {
                if (1 != hrSignDetailDao.updateHrSignDetailOffset(hrSignDetail,checkin_date)) {
                    logger.error("冲销补登时更新详情表的异常类型失败");
                    throw new BusinessException("冲销补登时更新详情表的异常类型失败");
                }
            }
        }
    }

    /**
     * 请假
     * @param leave
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getLeaveDayInScope(HrSignLeave leave, Date startDate, Date endDate) {
        Double count = 0.0;
        int weekend = 0;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            DateTime startLeaveDateTime = new DateTime(leave.getStartLeave()); //开始请假时间
            Date startLeave = null;
            Date endLeave = leave.getEndLeave(); //结束请假时间
            BigDecimal restChanged = leave.getRestChanged() == null ? new BigDecimal(0) : leave.getRestChanged();

            if (restChanged.compareTo(new BigDecimal(0)) != 0) {    //有填写换休
                if (restChanged.compareTo(leave.getLeaveDayCount()) != 0) {
                    BigDecimal multiply = restChanged.multiply(new BigDecimal(24));
                    startLeaveDateTime = startLeaveDateTime.plusHours(multiply.intValue());
                    startLeave = startLeaveDateTime.toDate();
                } else {
                    startLeave = endLeave;
                }

            } else {
                startLeave = leave.getStartLeave();
            }
            Date lastDateOfMonth = DateTimeUtil.getLastDateOfMonth(DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(startLeave)+" 23:59:59",DateTimeUtil.YYYY_MM_DD_HH_MM_SS)); //开始请假的月份的最后一天
            if (endLeave.after(lastDateOfMonth)) {
                //跨月请假
                if (endDate.before(lastDateOfMonth)) {
                    endLeave = endDate;
                }else if (startDate.after(startLeave)){
                    startLeave = this.sdf.parse(dateFormat.format(startDate)+" 09:00:00");
                } else if (endDate.before(endLeave)) {
                    endLeave = this.sdf.parse(dateFormat.format(endDate)+" 18:00:00");
                }
            }
            String format = timeFormat.format(startLeave);
            if ("00:00:00".equals(timeFormat.format(startLeave))) {
                startLeave = sdf.parse(dateFormat.format(startLeave)+" 09:00:00");
            }
            if ("00:00:00".equals(timeFormat.format(endLeave))) {
                endLeave = sdf.parse(DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, dateFormat.format(endLeave), DateTimeUtil.PLUSTYPE_DAY, -1)+" 18:00:00");
            }
            List<Calendar> holidayList = this.getHolidayList(startLeave, endLeave);
            weekend = holidayList.size();
            int i = 0;
            if (endDate.before(endLeave)) {
                //统计时间在请假结束之前
                i = DateTimeUtil.cutTwoDateToHour(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, sdf.format(startLeave), sdf.format(endDate));
            } else {
                i = DateTimeUtil.cutTwoDateToHour(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, sdf.format(startLeave), sdf.format(endLeave));
            }
            int i1 = i % 24;
            int day = i/24;
            count += day;
            if (i1 != 0) {
                if (i1 <= 7) {
                    count += 0.5;
                } else {
                    count +=1;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigDecimal(count).subtract(new BigDecimal(weekend));
    }

    public Page<HrSignEgress> egressListData(HrSignEgressArgs hrSignEgressArgs, Pageable pageable) {
        return hrSignEgressDao.egressListData(hrSignEgressArgs, pageable);
    }

    public Page<HrSignUnusual> unusualListData(HrSignUnusualArgs hrSignUnusualArgs, Pageable pageable) {
        return hrSignUnusualDao.unusualListData(hrSignUnusualArgs, pageable);
    }

    /**
     * 补签
     * @param hrSignUnusual
     */
    @Transactional
    public void updateUnusual(HrSignUnusual hrSignUnusual) {
        hrSignUnusual.setUpdate_time(new Date());
        hrSignUnusual.setUpdate_user(SpringSecurityUtil.getUser().getUserId().intValue());
        hrSignUnusual.setStatus("0");
        if (1 != hrSignUnusualDao.updateHrSignUnusualByUserid(hrSignUnusual)) {
            throw new BusinessException("补签失败");
        }
        HrSignDetail hrSignDetail = new HrSignDetail();
        hrSignDetail.setUserid(hrSignUnusual.getUserid());
        hrSignDetail.setGroupname("补签");
        hrSignDetail.setCheckin_time(hrSignUnusual.getUpdate_checkin_time());
        hrSignDetail.setException_type("0");
        hrSignDetail.setCheckin_type(hrSignUnusual.getCheckin_type());
        hrSignDetail.setCheckDate(hrSignUnusual.getCheckDate());
        hrSignDetail.setUpdateTime(new Date());
        hrSignDetail.setUpdateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (1 != hrSignDetailDao.updateHrSignDetail(hrSignDetail)) {
            throw new BusinessException("补签失败");
        }

    }

    public Page<HrSignLeave> listLeavePatchData(HrSignLeaveArgs hrSignLeaveArgs, Pageable pageable) {
        Page<HrSignLeave> hrSignLeaves = hrSignLeaveDao.listLeavePatchData(hrSignLeaveArgs, pageable);
        for (HrSignLeave h :hrSignLeaves) {
            h.setLeaveType(LeaveTypeEnum.getName(h.getLeaveType()));
        }
        return hrSignLeaves;
    }

    public HrSignLeave getHrSignLeaveById(String id) {
        HrSignLeave hrSignLeave = new HrSignLeaveArgs();
        hrSignLeave.setId(id);
        HrSignLeave hrSignLeaveById = hrSignLeaveDao.getHrSignLeaveById(hrSignLeave);
        hrSignLeaveById.setLeaveType(LeaveTypeEnum.getName(hrSignLeaveById.getLeaveType()));
        return hrSignLeaveById;
    }

    @Transactional
    public void updateLeave(HrSignLeave hrSignLeave) {
        if (1 != hrSignLeaveDao.updateLeave(hrSignLeave)) {
            throw new BusinessException("补录请假信息失败");
        }
    }

    /**
     * 异常报表
     * @param entity
     * @return
     */
    public List<HrSignUnusualStatistics> listUnususlStatisticsData(HrSignUnusualStatistics entity) throws ParseException {
        Date startDate = entity.getStartDate();
        Date endDate = entity.getEndDate();
        HrSignUser signUser = new HrSignUser();
        signUser.setCode(entity.getCode());
        signUser.setDepartmentId(entity.getDepartmentIds());
        List<HrSignUser> userList = hrSignUserDao.getUserDetailList("searchNotAll", signUser);
        List<HrSignUnusualStatistics> signUnusualStatisticsList = new ArrayList<>();
        for (HrSignUser hrSignUser :userList) {
            HrSignUnusualStatistics signUnusualStatistics = new HrSignUnusualStatistics();
            signUnusualStatistics.setPactCompanyName(hrSignUser.getPactCompanyName());
            signUnusualStatistics.setPlateName(hrSignUser.getPlateName());
            signUnusualStatistics.setCompanyName(hrSignUser.getCompanyName());
            signUnusualStatistics.setDepartmentName(hrSignUser.getDepartmentName());
            signUnusualStatistics.setUserNum(hrSignUser.getUserNum());
            signUnusualStatistics.setBirthdate(hrSignUser.getBirthdate());
            signUnusualStatistics.setAge(DateTimeUtil.getYearSpace(hrSignUser.getBirthdate(), new Date()));
            signUnusualStatistics.setJoinCompDate(hrSignUser.getJoinCompDate());
            signUnusualStatistics.setUseStatus(hrSignUser.getUseStatus());
            signUnusualStatistics.setName(hrSignUser.getName());
            signUnusualStatistics.setSex(hrSignUser.getSex());
            signUnusualStatistics.setPosition(hrSignUser.getPosition());
            signUnusualStatistics.setIdCard(hrSignUser.getIdCard());
            signUnusualStatistics.setGroupName(hrSignUser.getGroupName());
            signUnusualStatistics.setUseStatus(hrSignUser.getUseStatus());
            signUnusualStatistics.setTechnicalLevel(hrSignUser.getTechnicalLevel());
            signUnusualStatistics.setUserCategory(hrSignUser.getUserCategory());
            signUnusualStatistics.setWorkStatus(hrSignUser.getWorkStatus());
            signUnusualStatistics.setPerformanceSystem(hrSignUser.getPerformanceSystem());
            signUnusualStatistics.setInDueFormDate(hrSignUser.getInDueFormDate());


            Boolean falg = false;//标记 -- 如果该人有迟到,早退,旷工任意一项就返回TRUE
            HrSignUnusual hrSignUnusual = new HrSignUnusual();
            hrSignUnusual.setUserid(hrSignUser.getUserid());
            hrSignUnusual.setStartDate(startDate);
            hrSignUnusual.setEndDate(endDate);
            List<HrSignUnusual> hrSignUnusuals = hrSignUnusualDao.getHrSignUnusualStatisticsList(hrSignUnusual);
            for (HrSignUnusual h :hrSignUnusuals) {
                if ("1".equals(h.getStatus())) {
                    //迟到
//                    this.setNoWorkByLateOrLeaveEarly(h);
                    falg = getaBoolean(signUnusualStatistics, falg, h);
                } else if ("2".equals(h.getStatus())) {
                    //早退
//                    this.setNoWorkByLateOrLeaveEarly(h);//根据早退尺度判定旷工
                    falg = getaBoolean(signUnusualStatistics, falg, h);
                } else if ("3".equals(h.getStatus()) || "4".equals(h.getStatus()) || "5".equals(h.getStatus()) || "6".equals(h.getStatus()) || "7".equals(h.getStatus())) {
                    //未签到 未签退 旷工半天 旷工一天
                    falg = getaBoolean(signUnusualStatistics, falg, h);
                }
            }

            if (falg){
                //如果该人有迟到早退旷工的任何一项就set到集合中
                signUnusualStatisticsList.add(signUnusualStatistics);
            }
        }
        return signUnusualStatisticsList;
    }

    /**
     * 根据早退尺度判定旷工
     * @param h
     */
    @Transactional
    public void setNoWorkByLateOrLeaveEarly(HrSignUnusual h) throws ParseException {
        HrSignWorktime SignWorktime = new HrSignWorktime();
        SignWorktime.setGroupname(h.getGroupname());
        HrSignWorktime hrSignWorktime = hrSignWorktimeDao.getWorktimeByGroupname(SignWorktime);
        String checkDate = dateFormat.format(h.getCheckin_time());
        Date checktime = timeFormat.parse(timeFormat.format(h.getCheckin_time()));//打卡时间
        long time = checktime.getTime();
        long checkInTime = hrSignWorktime.getCheckInTime().getTime();//上班时间
        long checkOutTime = hrSignWorktime.getCheckOutTime().getTime();//下班时间500221198608056617
        if ("2".equals(h.getException_type())) {
            //时间异常
            Long l = 0L;
            if ("1".equals(h.getStatus())) {
                //迟到
                l = (time - checkInTime) / (1000 * 60);
            } else if ("2".equals(h.getStatus())) {
                //早退
                l = (checkOutTime - time) / (1000 * 60);
            }

            if (l.intValue() > 30 && l.intValue() < 60) {
                //迟到大于半小时小于一个小时
                h.setStatus("5");
            } else if (l.intValue() > 60) {
                //迟到一个小时以上
                h.setStatus("6");
            }
            hrSignUnusualDao.updateHrSignUnusualStatus(h, checkDate);
        }
    }

    /**
     * 判断迟到,早退,旷工字段有没有值
     * @param signUnusualStatistics
     * @param falg
     * @param hrSignUnusual
     * @return
     */
    private Boolean getaBoolean(HrSignUnusualStatistics signUnusualStatistics, Boolean falg, HrSignUnusual hrSignUnusual) {
        String day = DateTimeUtil.dateToString(hrSignUnusual.getCheckin_time(), DateTimeUtil.YYYY_MM_DD);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date startTime = hrSignUnusual.getStartTime();
            Date endTime = hrSignUnusual.getEndTime();
            String s = "";
            String s1 ="";
            if (null != startTime && null != endTime) {
                String startTimeStr = sdf.format(startTime);
                String endTimeStr = sdf.format(endTime);
                HrSignWorktime hrSignWorktime = new HrSignWorktime();
                if (StringUtils.isNotBlank(hrSignUnusual.getGroupname())) {
                    hrSignWorktime.setGroupname(hrSignUnusual.getGroupname());
                }
                HrSignWorktime worktime = hrSignWorktimeDao.getWorktimeByGroupname(hrSignWorktime);
                if (null != worktime) {
                    Time checkInTime = worktime.getCheckInTime();
                    Time checkOutTime = worktime.getCheckOutTime();
                    if (startTimeStr.equals(checkInTime.toString())) {
                        s = "#";
                    } else if ("09:00:01".equals(checkInTime.toString()) ||"08:30:01".equals(checkInTime.toString())) {
                        s = "#";
                    } else {
                        if ("7".equals(hrSignUnusual.getStatus()) && this.sdf.format(hrSignUnusual.getCheckin_time()).equals(this.sdf.format(hrSignUnusual.getStartTime()))){
                            s = startTimeStr+"(地点异常)";
                        } else {
                            s = startTimeStr;
                        }
                    }

                    if (endTimeStr.equals(checkOutTime.toString())) {
                        s1 = "#";
                    } else if ("18:00:01".equals(checkOutTime.toString())) {
                        s1 = "#";
                    } else {
                        if ("7".equals(hrSignUnusual.getStatus()) && this.sdf.format(hrSignUnusual.getCheckin_time()).equals(this.sdf.format(hrSignUnusual.getEndTime()))){
                            s1 = endTimeStr+"(地点异常)";
                        } else {
                            s1 = endTimeStr;
                        }
                    }
                } else {
                    throw new BusinessException("worktime为空");
                }
            }

            if ("1".equals(hrSignUnusual.getStatus())) {
                StringBuilder late = new StringBuilder(null == signUnusualStatistics.getLate()?"" : signUnusualStatistics.getLate());
                if (!late.toString().contains(day)) {
                    String s3 = DateTimeUtil.dateToString(startTime, DateTimeUtil.YYYY_MM_DD);
                    if (late.length() == 0) {
                        late.append(s3+" "+s+"-"+s1);
                    } else {
                        late.append(", "+s3+" "+s+"-"+s1);
                    }
                    signUnusualStatistics.setLate(late.toString());
                    falg = true;
                }
            } else if ("2".equals(hrSignUnusual.getStatus())) {
                StringBuilder late = new StringBuilder(null == signUnusualStatistics.getLeaveEarly()?"" : signUnusualStatistics.getLeaveEarly());
                if (!late.toString().contains(day)) {
                    String s3 = DateTimeUtil.dateToString(startTime, DateTimeUtil.YYYY_MM_DD);
                    if (late.length() == 0) {
                        late.append(s3+" "+s+"-"+s1);
                    } else {
                        late.append(", "+s3+" "+s+"-"+s1);
                    }
                    signUnusualStatistics.setLeaveEarly(late.toString());
                    falg = true;
                }
            } else if ("3".equals(hrSignUnusual.getStatus()) || "4".equals(hrSignUnusual.getStatus()) || "5".equals(hrSignUnusual.getStatus()) || "6".equals(hrSignUnusual.getStatus()) || "7".equals(hrSignUnusual.getStatus())) {
                StringBuilder late = new StringBuilder(null == signUnusualStatistics.getNoWork()?"" : signUnusualStatistics.getNoWork());
                if (!late.toString().contains(day)) {
                    String s3 = DateTimeUtil.dateToString(startTime, DateTimeUtil.YYYY_MM_DD);
                    if (late.length() == 0) {
                        late.append(s3+" "+s+"-"+s1);
                    } else {
                        late.append(", "+s3+" "+s+"-"+s1);
                    }

                    signUnusualStatistics.setNoWork(late.toString());
                    falg = true;
                }
            }


        return falg;
    }

    @Transactional
    public void saveLeave(HrSignLeave hrSignLeave) {
        hrSignLeave.setFormmainId(RandomUtil.generateUUID());
        hrSignLeave.setStatus(2);
        hrSignLeave.setCreateDate(new Date());
        if (1 != hrSignLeaveDao.saveLeaveMainData(hrSignLeave)) {
            throw new BusinessException("保存请假主表失败");
        }
        if (1 != hrSignLeaveDao.saveLeaveSonData(hrSignLeave)) {
            throw new BusinessException("保存请假子表失败");
        }
    }

    public Page<HrSignEgress> listUnusualPatchData(HrSignEgressArgs hrSignEgressArgs, Pageable pageable) {
        return hrSignEgressDao.listUnusualPatchData(hrSignEgressArgs, pageable);
    }

    public HrSignEgress getHrSignEgressById(String id) {
        HrSignEgress hrSignEgress = new HrSignEgress();
        hrSignEgress.setId(id);
        return hrSignEgressDao.getHrSignEgressById(hrSignEgress);
    }

    @Transactional
    public void updateEgressByid(HrSignEgress hrSignEgress) {
        if (1 != hrSignEgressDao.updateEgressByid(hrSignEgress)) {
            throw new BusinessException("更新外出异常失败");
        }
    }

    @Transactional
    public void changeLeaveFlag(HrSignLeave hrSignLeave) {
        hrSignLeave.setStatus(2);
        if (1 != hrSignLeaveDao.changeLeaveMainFlag(hrSignLeave)) {
            throw new BusinessException("更新主表请假状态失败!");
        }
        if (1 != hrSignLeaveDao.changeLeaveSonFlag(hrSignLeave)) {
            throw new BusinessException("更新子表请假状态失败!");
        }
    }

    @Transactional
    public void changeEgressFlag(HrSignEgress hrSignEgress) {
        hrSignEgress.setStatus(2);
        if (1 != hrSignEgressDao.changeEgressMainFlag(hrSignEgress)) {
            throw new BusinessException("更新主表外出状态失败!");
        }
        if (1 != hrSignEgressDao.changeEgressSonFlag(hrSignEgress)) {
            throw new BusinessException("更新子表外出状态失败!");
        }
    }

    public Page<HrSignPatchSon> patchUnusualListData(HrSignPatchSonArgs hrSignPatchSonArgs, Pageable pageable) {
        return hrSignPatchSonDao.patchUnusualListData(hrSignPatchSonArgs, pageable);
    }

    @Transactional
    public void changePatchUnusualStatus(HrSignPatchSon hrSignPatchSon) {
        hrSignPatchSon.setStatus(2);
        if (1 != hrSignPatchSonDao.changePatchUnusualStatus(hrSignPatchSon)) {
            throw new BusinessException("更新补登状态失败!");
        }
        HrSignPatchSon patchSon = hrSignPatchSonDao.getOneById(hrSignPatchSon);
        List<HrSignPatchSon> patchSons = hrSignPatchSonDao.getHrSignPatchByStatus(patchSon);
        if (CollectionUtils.isEmpty(patchSons)) {
            //子表没有status=1的数据,更新主表的status
            HrSignPatchFormmain hrSignPatchFormmain = new HrSignPatchFormmain();
            hrSignPatchFormmain.setFormmainId(patchSon.getFormmainId());
            hrSignPatchFormmain.setStatus(1);
            if (1 != hrSignPatchFormmainDao.updateByFormmainIdAndStatus(hrSignPatchFormmain)) {
                throw new BusinessException("更新补登状态失败");
            }
        }

    }

    public Page<HrSignPatchSon> listPatchDetailData(HrSignPatchSonArgs hrSignPatchSonArgs, Pageable pageable) {
        return hrSignPatchSonDao.listPatchDetailData(hrSignPatchSonArgs, pageable);
    }

    /**
     * 抓数据
     */
    @Transactional
    public void getSignData(HrSignUnusualArgs hrSignUnusualArgs, Boolean isToday) throws Exception {
        Date startdate = hrSignUnusualArgs.getStartTime();
        Date enddate = hrSignUnusualArgs.getEndTime();
//        List<HrSignUser> users = hrSignUserDao.getAllUserRegx();
        List<HrSignDetail> hrSignDetails = new ArrayList<>();	//所有考勤记录
        List<HrSignDepartment> departmentList = OASignUtil.getDepartmentList();
        List<HrSignUser> users = new ArrayList<>();		//所有用户
        for (HrSignDepartment d : departmentList) {
            Integer deptid = d.getId();
            List<HrSignUser> userList = OASignUtil.getUserList(deptid+"");
            users.addAll(userList);
        }
        List<String> userIdList = new ArrayList<>();
        for (int i = 0;i< users.size();i++) {
            HrSignUser hrSignUser = users.get(i);
            if (i == users.size()-1 || userIdList.size() == 100) {
                List<HrSignDetail> signList = OASignUtil.getSignList(startdate, enddate, userIdList, 1);
                hrSignDetails.addAll(signList);
                userIdList.clear();
            }
            userIdList.add(hrSignUser.getUserid());
        }
        checkSignList(new HrSignUser(), hrSignDetails, isToday);
        /*List<HrSignUser> userList ;
        if (isToday) {
            userList = hrSignUserDao.getAllUserRegx();
        } else {
            userList = hrSignUserDao.getAllUserDetailList(1);
        }
        for (int i = 0;i<userList.size();i++) {
            List<String> userIdList = new ArrayList<>();
            HrSignUser u = userList.get(i);
            userIdList.add(u.getUserid());
            List<HrSignDetail> signList = OASignUtil.getSignList(startdate, enddate, userIdList, 1);
            checkSignList(u, signList, isToday);
        }*/
    }

    @Transactional
    public void checkSignList(HrSignUser u, List<HrSignDetail> signList, Boolean isToday) throws Exception {
        for (HrSignDetail s : signList) {
            s.setUsername(u.getName());
            s.setTelephone(u.getMobile());
            s.setCheckin_time(new Date(s.getCheckin_time().getTime()*1000));

            String[] split = s.getException_type().split(";|；");
            StringBuilder stringBuilder = new StringBuilder();
            if ("".equals(s.getException_type())) {
                stringBuilder.append("0");
                s.setStatus(0);
            } else {
                for (String str : split) {
                    if ("未打卡".equals(str)) {
                        String st = stringBuilder.length() == 0 ? "1" : ";1";
                        stringBuilder.append(st);
                        if ("上班打卡".equals(s.getCheckin_type())) {
                            s.setStatus(3);
                        } else if ("下班打卡".equals(s.getCheckin_type())){
                            s.setStatus(4);
                        }
                    } else if ("时间异常".equals(str)) {
                        String st = stringBuilder.length() == 0 ? "2" : ";2";
                        stringBuilder.append(st);
                        if ("上班打卡".equals(s.getCheckin_type())) {
                            s.setStatus(1);
                        } else if ("下班打卡".equals(s.getCheckin_type())){
                            s.setStatus(2);
                        }
                    } else if ("地点异常".equals(str)) {
                        String st = stringBuilder.length() == 0 ? "3" : ";3";
                        stringBuilder.append(st);
                        s.setStatus(7);
                    } else if ("非常用设备".equals(str)) {
                        String st = stringBuilder.length() == 0 ? "4" : ";4";
                        stringBuilder.append(st);
                        s.setStatus(11);
                    }
                }
            }

            /*if (!isToday) {

                if (!"".equals(s.getException_type())) {
                    //获取异常打卡
                    HrSignUnusual hrSignUnusual = new HrSignUnusual();
                    hrSignUnusual.setUserid(s.getUserid());
                    hrSignUnusual.setUsername(s.getUsername());
                    hrSignUnusual.setGroupname(s.getGroupname());
                    hrSignUnusual.setTelephone(s.getTelephone());
                    if ("上班打卡".equals(s.getCheckin_type())) {
                        hrSignUnusual.setCheckin_type("1");
                    } else if ("下班打卡".equals(s.getCheckin_type())) {
                        hrSignUnusual.setCheckin_type("2");
                    } else {
                        hrSignUnusual.setCheckin_type(s.getCheckin_type());
                    }
                    hrSignUnusual.setException_type(stringBuilder.toString());
                    hrSignUnusual.setCheckin_time(s.getCheckin_time());
                    hrSignUnusual.setLocation_detail(s.getLocation_detail());
                    String[] exceptions = s.getException_type().split(";|；");
                    if ("上班打卡".equals(s.getCheckin_type())) {
                        //上班打卡
                        for (String type :exceptions) {
                            if ("未打卡".equals(type)) {
                                hrSignUnusual.setStatus("3");//未签到
                            } else if ("时间异常".equals(type)) {
                                hrSignUnusual.setStatus("1");
                                break;
                            } else if ("地点异常".equals(type)) {
                                hrSignUnusual.setStatus("7");
                            } else {
                                hrSignUnusual.setStatus("0");
                            }
                        }

                    } else if ("下班打卡".equals(s.getCheckin_type())) {
                        //下班打卡
                        for (String type :exceptions) {
                            if ("未打卡".equals(type)) {
                                //如果上午也未打卡就是旷工
                                List<HrSignUnusual> hrSignUnusualOneDay = hrSignUnusualDao.getSignUnusualByUseridOneDay(dateFormat.format(s.getCheckin_time()),s.getUserid());
                                Boolean flag = true;
                                for (HrSignUnusual hrSignUnusual1: hrSignUnusualOneDay) {
                                    if ("上班打卡".equals(hrSignUnusual1.getCheckin_type())) {
                                        flag = false;
                                        if ("未打卡".equals(s.getException_type())) {
                                            //上午未签到
                                            hrSignUnusual.setStatus("6");
                                            break;
                                        }
                                    }
                                }
                                if (flag) {
                                    hrSignUnusual.setStatus("4");
                                }
                            } else if ("时间异常".equals(type)) {
                                hrSignUnusual.setStatus("2");
                                break;
                            } else if ("地点异常".equals(type)) {
                                hrSignUnusual.setStatus("7");
                            }else {
                                hrSignUnusual.setStatus("0");
                            }
                        }
                    }
                    if (1 != hrSignUnusualDao.postData(hrSignUnusual)) {
                        throw new BusinessException("保存异常打卡记录失败");
                    }
                }
            }*/

            if ("上班打卡".equals(s.getCheckin_type())) {
                s.setCheckin_type("1");
            } else if ("下班打卡".equals(s.getCheckin_type())) {
                s.setCheckin_type("2");
            }
            s.setException_type(stringBuilder.toString());
            s.setCreateTime(new Date());
            //保存打卡详情
            if (isToday) {
                HrSignTodayTemp todayTemp = new HrSignTodayTemp();
                BeanUtils.copyProperties(s, todayTemp);
                if (1 != hrSignTodayTempDao.saveSign(todayTemp));
            } else {
                if ( 1 != hrSignDetailDao.saveSign(s)) {
                    throw new BusinessException("保存打卡记录失败");
                }
            }
        }
    }

    @Transactional
    public void saveSignUser(HrSignUser u, HrEmployee hrEmployee) {
        u.setDepartment(u.getDepartment().replace("[","").replace("]",""));
        if (null != hrEmployee) {
            u.setUserNum(hrEmployee.getId()+"");
            u.setIdCard(hrEmployee.getIdCard());
            u.setIsMatched(1);
        } else {
            u.setIsMatched(0);
        }
        if (1 != hrSignUserDao.postData(u)) {
            throw new BusinessException("保存用户失败");
        }



    }


    public void handleException(HrSignDetailArgs hrSignDetailArgs) throws ParseException {
        Date startTime = hrSignDetailArgs.getStartTime();
        Date endTime = hrSignDetailArgs.getEndTime();
        //查异常表
        HrSignUnusual hrSignUnusual = new HrSignUnusual();
        hrSignUnusual.setStartDate(startTime);
        hrSignUnusual.setEndDate(endTime);
        List<HrSignUnusual> hrSignUnusuals = hrSignUnusualDao.getSignUnusualByUseridAndCheckin_time(hrSignUnusual);
        this.offsetUnusual(hrSignUnusuals, false);//冲销异常
    }

    public void setNoWork(HrSignUnusualArgs hrSignUnusualArgs) throws ParseException {
        Date startTime = hrSignUnusualArgs.getStartTime();
        Date endTime = hrSignUnusualArgs.getEndTime();
        List<HrSignUser> userList = hrSignUserDao.getAllUserDetailList(1);
        for (HrSignUser hrSignUser :userList) {
            HrSignUnusual hrSignUnusual = new HrSignUnusual();
            hrSignUnusual.setUserid(hrSignUser.getUserid());
            hrSignUnusual.setStartDate(startTime);
            hrSignUnusual.setEndDate(endTime);
            List<HrSignUnusual> hrSignUnusuals = hrSignUnusualDao.getHrSignUnusualStatisticsList(hrSignUnusual);
            for (HrSignUnusual h :hrSignUnusuals) {
                if ("1".equals(h.getStatus())) {
                    //迟到
                    this.setNoWorkByLateOrLeaveEarly(h);
                } else if ("2".equals(h.getStatus())) {
                    //早退
                    this.setNoWorkByLateOrLeaveEarly(h);
                }
            }
        }
    }

    @Transactional
    public void getEgressData() {
        List<HrSignEgress> egressList = hrSignEgressDao.getAll();
        List<HrSignEgressMain> egressMainList = new ArrayList<>();
        List<HrSignEgressSon> egressSonList = new ArrayList<>();
        Map<String, String> temp = new HashMap<>();
        for (HrSignEgress e :egressList) {
            HrSignEgressMain hrSignEgressMain = new HrSignEgressMain();
            hrSignEgressMain.setFormmainid(e.getFormmainId());
            hrSignEgressMain.setUsername(e.getUsername());
            hrSignEgressMain.setCompany(e.getCompany());
            hrSignEgressMain.setDepartment(e.getDepartment());
            hrSignEgressMain.setTelephone(e.getTelephone());
            hrSignEgressMain.setPosition(e.getPosition());
            Integer status ;
            if (e.getFinishedFlag() == 0) {
                status = 1;
            } else if (e.getFinishedFlag() == 1) {
                status = 2;
            } else {
                status = 3;
            }
            hrSignEgressMain.setStatus(status);
            if (!temp.containsKey(e.getFormmainId())) {
                egressMainList.add(hrSignEgressMain);
                temp.put(e.getFormmainId(),"a");
            }

            HrSignEgressSon hrSignEgressSon = new HrSignEgressSon();
            hrSignEgressSon.setFormmainid(e.getFormmainId());
            hrSignEgressSon.setStartTime(e.getStartTime());
            hrSignEgressSon.setEndTime(e.getEndTime());
            hrSignEgressSon.setLocationDetail(e.getLocation_detail());
            hrSignEgressSon.setNotes(e.getNotes());
            hrSignEgressSon.setStatus(status);
            egressSonList.add(hrSignEgressSon);
        }

        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[egressMainList.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_sign_egress_main", egressMainList.size()));
        for (int i =0; i<egressMainList.size(); i++){
            Long key = ids[0] + i + 1;
            HrSignEgressMain tmp = egressMainList.get(i);
            tmp.setId(key.intValue());
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = hrSignEgressMainDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        SqlParameterSource[] sqlParameterSources1 = new SqlParameterSource[egressSonList.size()];
        Long[] ids1 = commSequenceDao.getKey(new CommSequence("hr_sign_egress_son", egressSonList.size()));
        for (int i =0; i<egressSonList.size(); i++){
            Long key = ids1[0] + i + 1;
            HrSignEgressSon tmp = egressSonList.get(i);
            tmp.setId(key.intValue());
            sqlParameterSources1[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = hrSignEgressSonDao.batchUpdate(sqlParameterSources1);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void getLeaveData() {
        List<HrSignLeave> leaveList = hrSignLeaveDao.getAll();
        List<HrSignLeaveMain> leaveMainList = new ArrayList<>();
        List<HrSignLeaveSon> leaveSonList = new ArrayList<>();
        Map<String, String> temp = new HashMap<>();
        for (HrSignLeave h : leaveList) {
            HrSignLeaveMain hrSignLeaveMain = new HrSignLeaveMain();

            hrSignLeaveMain.setFormmainid(h.getFormmainId());
            hrSignLeaveMain.setCompany(h.getCompany());
            hrSignLeaveMain.setDepartment(h.getDepartment());
            hrSignLeaveMain.setPosition(h.getPosition());
            hrSignLeaveMain.setUsername(h.getUsername());
            hrSignLeaveMain.setTelephone(h.getTelephone());
            hrSignLeaveMain.setReason(h.getReason());
            Integer status ;
            if (h.getFinishedFlag() == 0) {
                status = 1;
            } else if (h.getFinishedFlag() == 1) {
                status = 2;
            } else {
                status = 3;
            }
            hrSignLeaveMain.setStatus(status);
            //如果主表包含了该formmainid就不存
            if (!temp.containsKey(h.getFormmainId())) {
                leaveMainList.add(hrSignLeaveMain);
                temp.put(h.getFormmainId(), "a");
            }

            HrSignLeaveSon hrSignLeaveSon = new HrSignLeaveSon();
            hrSignLeaveSon.setFormmainid(h.getFormmainId());
            hrSignLeaveSon.setLeaveType(h.getLeaveType());
            hrSignLeaveSon.setStartLeave(h.getStartLeave());
            hrSignLeaveSon.setEndLeave(h.getEndLeave());
            hrSignLeaveSon.setLeaveDayCount(h.getLeaveDayCount());
            hrSignLeaveSon.setRestChanged(h.getRestChanged());
            hrSignLeaveSon.setNotes(h.getNotes());
            hrSignLeaveSon.setStatus(status);
            leaveSonList.add(hrSignLeaveSon);
        }

        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[leaveMainList.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_sign_leave_main", leaveMainList.size()));
        for (int i =0; i<leaveMainList.size(); i++){
            Long key = ids[0] + i + 1;
            HrSignLeaveMain tmp = leaveMainList.get(i);
            tmp.setId(key.intValue());
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = hrSignLeaveMainDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SqlParameterSource[] sqlParameterSources1 = new SqlParameterSource[leaveSonList.size()];
        Long[] ids1 = commSequenceDao.getKey(new CommSequence("hr_sign_leave_son", leaveSonList.size()));
        for (int i =0; i<leaveSonList.size(); i++){
            Long key = ids1[0] + i + 1;
            HrSignLeaveSon tmp = leaveSonList.get(i);
            tmp.setId(key.intValue());
            sqlParameterSources1[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = hrSignLeaveSonDao.batchUpdate(sqlParameterSources1);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void handleLostData(HrSignUnusualArgs hrSignUnusualArgs) throws Exception {
        Date startTime = hrSignUnusualArgs.getStartTime();
        Date endTime = hrSignUnusualArgs.getEndTime();
        List<HrSignUser> userList = hrSignUserDao.getAllUserDetailList(1);
        for (HrSignUser u : userList) {
            HrSignDetail hrSignDetail = new HrSignDetail();
            hrSignDetail.setUserid(u.getUserid());
            hrSignDetail.setUsername(u.getName());
            hrSignDetail.setTelephone(u.getMobile());
            hrSignDetail.setGroupname("集团总部办公");
            hrSignDetail.setCheckin_type("2");
            hrSignDetail.setCheckin_time(DateTimeUtil.convertStringToDate("2017-08-29 18:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            hrSignDetail.setLocation_title("");
            hrSignDetail.setLocation_detail("重庆市渝北区泰山大道东段118号");
            hrSignDetail.setNotes("");
            hrSignDetail.setWifiname("");
            hrSignDetail.setWifimac("");
            hrSignDetail.setMediaids("");

            HrSignEgress hrSignEgress = new HrSignEgress();
            hrSignEgress.setUserid(u.getUserid());
            hrSignEgress.setStartTime(this.sdf.parse("2017-08-29 17:30:00"));
            List<HrSignEgress> egressList = hrSignEgressDao.checkHaveEgressOneDay(hrSignEgress);

            if (CollectionUtils.isEmpty(egressList)) {
                HrSignLeaveArgs hrSignLeave = new HrSignLeaveArgs();
                hrSignLeave.setUserid(u.getUserid());
                hrSignLeave.setStartLeave(this.sdf.parse("2017-08-29 17:30:00"));
                List<HrSignLeave> leaveList = hrSignLeaveDao.checkHaveLeaveOneDay(hrSignLeave);
                //该天没有外出
                if (CollectionUtils.isEmpty(leaveList)) {
                    //该天没有请假
                    hrSignDetail.setException_type("0");
                } else {
                    System.out.println(leaveList.size());
                    hrSignDetail.setException_type("1");
                }
            } else {
                System.out.println(egressList.size());
                hrSignDetail.setException_type("1");
            }
            //保存
            if (1 != hrSignDetailDao.saveSign(hrSignDetail)) {
                throw new BusinessException("保存人造数据hrSignDetail失败");
            }

            if ("1".equals(hrSignDetail.getException_type())) {
                HrSignUnusual hrSignUnusual = new HrSignUnusual();
                hrSignUnusual.setUserid(u.getUserid());
                hrSignUnusual.setUsername(u.getName());
                hrSignUnusual.setIdCard(u.getIdCard());
                hrSignUnusual.setTelephone(u.getMobile());
                hrSignUnusual.setCheckin_type("2");
                hrSignUnusual.setException_type("1");
                hrSignUnusual.setCheckin_time(DateTimeUtil.convertStringToDate("2017-08-29 18:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignUnusual.setGroupname("集团总部办公");
                hrSignUnusual.setLocation_detail("重庆市渝北区泰山大道东段118号");
                hrSignUnusual.setStatus("4");
                if (1 != hrSignUnusualDao.postData(hrSignUnusual)) {
                    throw new BusinessException("保存人造数据hrSignUnusual失败");
                }
            }
        }
    }


    /**
     * 处理少一条数据问题
     * @param hrSignDetailArgs
     */
    @Transactional
    public void handleNoData(HrSignDetailArgs hrSignDetailArgs, Boolean isToday) throws Exception {

        List<HrSignDetail> hrSignDetails1 = hrSignDetailDao.getSignDetailByUseridAndDateA(hrSignDetailArgs);
        for (HrSignDetail hrSignDetail : hrSignDetails1) {
            List<HrSignDetail> hrSignDetails = hrSignDetailDao.getSignDetailByUseridAndCheckDate(hrSignDetail);
            if (null != hrSignDetails) {
                if (hrSignDetails.size() == 1) {
                    HrSignDetail hrSignDetail1 = hrSignDetails.get(0);
                    HrSignWorktime hrSignWorktime = new HrSignWorktime();
                    hrSignWorktime.setGroupname(hrSignDetail1.getGroupname());
                    HrSignWorktime worktime = hrSignWorktimeDao.getWorktimeByGroupname(hrSignWorktime);
                    String checkInTime = "";
                    String checkOutTime = "";
                    if (null != worktime) {
                        checkInTime = worktime.getCheckInTime().toString();
                        checkOutTime = worktime.getCheckOutTime().toString();
                    }
                    String s = DateTimeUtil.dateToString(hrSignDetail1.getCheckin_time(), DateTimeUtil.YYYY_MM_DD);
                    if ("1".equals(hrSignDetail1.getCheckin_type())) {
                        saveSignDetail(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), hrSignDetail1.getGroupname(), "2", DateTimeUtil.convertStringToDate(s + " " + checkOutTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                        saveSignUnusual(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), "2", DateTimeUtil.convertStringToDate(s + " " + checkOutTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), hrSignDetail1.getGroupname(), "4",isToday,"1");
                    } else if ("2".equals(hrSignDetail1.getCheckin_type())) {
                        saveSignDetail(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), hrSignDetail1.getGroupname(), "1", DateTimeUtil.convertStringToDate(s+" "+checkInTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                        saveSignUnusual(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), "1", DateTimeUtil.convertStringToDate(s+" "+checkInTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), hrSignDetail1.getGroupname(), "3",isToday,"1");
                    }
                }/* else if (hrSignDetails.size() == 0){
                    //没有数据
                    String groupname = "";
                    List<HrSignDetail> hrSignDetails1 = hrSignDetailDao.getSignDetailByUserid(hrSignDetailArgs);
                    for (HrSignDetail h : hrSignDetails1) {
                        if (StringUtils.isNotBlank(h.getGroupname())) {
                            groupname = h.getGroupname();
                            break;
                        }
                    }
                    HrSignWorktime hrSignWorktime = new HrSignWorktime();
                    hrSignWorktime.setGroupname(groupname);
                    HrSignWorktime worktime = hrSignWorktimeDao.getWorktimeByGroupname(hrSignWorktime);
                    String checkInTime = worktime.getCheckInTime().toString();
                    String checkOutTime = worktime.getCheckOutTime().toString();
                    saveSignDetail(u.getUserid(), u.getName(), u.getMobile(), groupname, "1", DateTimeUtil.convertStringToDate(s+" "+checkInTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                    saveSignUnusual(u.getUserid(), u.getName(), u.getMobile(), "1", DateTimeUtil.convertStringToDate(s+" "+checkInTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "3");
                    saveSignDetail(u.getUserid(), u.getName(), u.getMobile(), groupname, "2", DateTimeUtil.convertStringToDate(s+" "+checkOutTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                    saveSignUnusual(u.getUserid(), u.getName(), u.getMobile(), "2", DateTimeUtil.convertStringToDate(s+" "+checkOutTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "4");
                }*/
            }
        }

    }

    @Transactional
    private void saveSignUnusual(String userid, String name, String mobile, String checkin_type, Date checkin_time, String groupname, String status,Boolean isToday,String exception_type) {
        if (!isToday) {
            HrSignUnusual hrSignUnusual = new HrSignUnusual();
            hrSignUnusual.setUserid(userid);
            hrSignUnusual.setUsername(name);
            hrSignUnusual.setTelephone(mobile);
            hrSignUnusual.setCheckin_type(checkin_type);
            hrSignUnusual.setException_type(exception_type);
            hrSignUnusual.setCheckin_time(checkin_time);
            hrSignUnusual.setGroupname(groupname);
            hrSignUnusual.setLocation_detail("");
            hrSignUnusual.setStatus(status);
            hrSignUnusualDao.postData(hrSignUnusual);
        }
    }

    @Transactional
    private void saveSignDetail(String userid, String name, String mobile, String groupname, String checkin_type, Date checkin_time,Boolean isToday,String exception_type) {
        HrSignDetail hrSignDetail2 = new HrSignDetail();
        hrSignDetail2.setUserid(userid);
        hrSignDetail2.setUsername(name);
        hrSignDetail2.setTelephone(mobile);
        hrSignDetail2.setGroupname(groupname);
        hrSignDetail2.setCheckin_type(checkin_type);
        hrSignDetail2.setException_type(exception_type);
        hrSignDetail2.setCheckin_time(checkin_time);
        hrSignDetail2.setLocation_title("");
        hrSignDetail2.setLocation_detail("");
        hrSignDetail2.setStatus(3);
        if (isToday) {
            HrSignTodayTemp hrSignTodayTemp = new HrSignTodayTemp();
            BeanUtils.copyProperties(hrSignDetail2, hrSignTodayTemp);
            hrSignTodayTempDao.saveSign(hrSignTodayTemp);
        } else {
            hrSignDetailDao.saveSign(hrSignDetail2);
        }
    }

    public void handleLeave(HrSignLeaveSon hrSignLeaveSon) throws Exception {
        if (hrSignLeaveSon.getStartTime().before(dateFormat.parse("2017-08-31")) || hrSignLeaveSon.getEndTime().before(dateFormat.parse("2017-08-31"))) {
            throw new BusinessException("请选择2017-8-31之后的日期");
        }
        HrSignLeaveSon hrSignLeaveSon1 = new HrSignLeaveSon();
        hrSignLeaveSon1.setStartTime(hrSignLeaveSon.getStartTime());
        hrSignLeaveSon1.setEndTime(hrSignLeaveSon.getEndTime());
        List<HrSignLeaveSon> hrSignLeaveSonList = hrSignLeaveSonDao.getTodayLeave(hrSignLeaveSon1);
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[hrSignLeaveSonList.size()];
        for (int i = 0; i<hrSignLeaveSonList.size();i++) {
            HrSignLeaveSon son = hrSignLeaveSonList.get(i);
            Date startLeave = son.getStartLeave();
            Date endLeave = son.getEndLeave();
            BigDecimal leaveDayCount = son.getLeaveDayCount();

            String startLeaveDay = dateFormat.format(startLeave);
            String endLeaveDay = dateFormat.format(endLeave);
            BigDecimal countDay = new BigDecimal(DateTimeUtil.cutTwoDateToDay(DateTimeUtil.YYYY_MM_DD, startLeaveDay, endLeaveDay, 10));
            HrSignLeaveSon temp = new HrSignLeaveSon();
            temp.setId(son.getId());
            temp.setFormmainid(son.getFormmainid());
            temp.setLeaveType(son.getLeaveType());
            temp.setLeaveDayCount(son.getLeaveDayCount());
            temp.setRestChanged(son.getRestChanged());
            temp.setNotes(son.getNotes());
            temp.setCreateDate(son.getCreateDate());
            temp.setStatus(son.getStatus());

            if (startLeaveDay.equals(endLeaveDay)) {
                //同一天
                if (leaveDayCount.compareTo(new BigDecimal(0.5)) == 0) {
                    temp.setStartLeave(sdf.parse(startLeaveDay + " " + "09:00:00"));
                    temp.setEndLeave(sdf.parse(startLeaveDay + " " + "12:00:00"));
                } else if (leaveDayCount.compareTo(new BigDecimal(1)) == 0) {
                    temp.setStartLeave(sdf.parse(startLeaveDay + " " + "09:00:00"));
                    temp.setEndLeave(sdf.parse(startLeaveDay + " " + "18:00:00"));
                }
            } else {
                if (endLeave.after(startLeave)) {
                    if (leaveDayCount.compareTo(countDay) == 0) {
                        //请假天数等于结束-开始
                        temp.setStartLeave(sdf.parse(startLeaveDay + " " + "12:00:00"));
                        temp.setEndLeave(sdf.parse(endLeaveDay + " " + "12:00:00"));
                    } else if (leaveDayCount.compareTo(countDay) == 1) {
                        //请假天数大于结束-开始
                        if ((leaveDayCount.floatValue()+"").contains(".5")) {
                            temp.setStartLeave(sdf.parse(startLeaveDay + " " + "09:00:00"));
                            temp.setEndLeave(sdf.parse(endLeaveDay + " " + "12:00:00"));
                        } else {
                            temp.setStartLeave(sdf.parse(startLeaveDay + " " + "09:00:00"));
                            temp.setEndLeave(sdf.parse(endLeaveDay + " " + "18:00:00"));
                        }
                    }
                }
            }

            sqlParameterSources[i] = new BeanPropertySqlParameterSource(temp);
        }
        try {
            int[] flags = hrSignLeaveSonDao.batchUpdateLeave(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("处理请假数据失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void handleThirdData(HrSignDetailArgs hrSignDetailArgs) throws Exception {
        List<HrSignDetail> hrSignDetails1 = hrSignDetailDao.getSignTolateList(hrSignDetailArgs);
        for (HrSignDetail hrSignDetail1 : hrSignDetails1) {
            String day = dateFormat.format(hrSignDetail1.getCheckin_time());
            Date parse1 = sdf.parse(day + " 00:00:00");
            Date parse2 = sdf.parse(day + " 04:00:00");
            if (hrSignDetail1.getCheckin_time().after(parse1) && hrSignDetail1.getCheckin_time().before(parse2) && "2".equals(hrSignDetail1.getCheckin_type())) {
                //加班到凌晨后才打了下班卡
                HrSignDetail signDetail = new HrSignDetail();
                BeanUtils.copyProperties(hrSignDetail1,signDetail);
                String lastDay = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, day, DateTimeUtil.PLUSTYPE_DAY, -1);
                signDetail.setCheckin_time(DateTimeUtil.convertStringToDate(lastDay+" 23:50:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                signDetail.setException_type("0");
                if (null == signDetail.getGroupname() || (null != signDetail.getGroupname() && "".equals(signDetail.getGroupname()))) {
                    HrSignDetail temp = hrSignDetailDao.getGroupname(signDetail);
                    if (null != temp) {
                        signDetail.setGroupname(temp.getGroupname());
                    }
                }
                hrSignDetailDao.saveSign(signDetail);
                //删除原数据
                if (1 != hrSignDetailDao.deleteSignData(hrSignDetail1)) {
                    throw new BusinessException("删除多余打卡数据失败");
                }
            } else if (hrSignDetail1.getCheckin_time().after(parse1) && hrSignDetail1.getCheckin_time().before(parse2) && "1".equals(hrSignDetail1.getCheckin_type())) {
                //加班到凌晨后才打了上班卡
                HrSignDetail signDetail = new HrSignDetail();
                BeanUtils.copyProperties(hrSignDetail1,signDetail);
                signDetail.setException_type("2");
                String lastDay = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, day, DateTimeUtil.PLUSTYPE_DAY, -1);
                signDetail.setCheckin_time(DateTimeUtil.convertStringToDate(lastDay+" 23:40:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignDetailDao.saveSign(signDetail);
                //删除原打卡数据
                if (1 != hrSignDetailDao.deleteSignData(hrSignDetail1)) {
                    throw new BusinessException("删除多余打卡数据失败");
                }

                HrSignUnusual hrSignUnusual = new HrSignUnusual();
                hrSignUnusual.setUserid(hrSignDetail1.getUserid());
                hrSignUnusual.setUsername(hrSignDetail1.getUsername());
                hrSignUnusual.setTelephone(hrSignDetail1.getTelephone());
                hrSignUnusual.setCheckin_type("1");
                hrSignUnusual.setException_type("2");
                hrSignUnusual.setGroupname(hrSignDetail1.getGroupname());
                hrSignUnusual.setLocation_detail(hrSignDetail1.getLocation_detail());
                hrSignUnusual.setCheckin_time(DateTimeUtil.convertStringToDate(lastDay+" 23:40:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignUnusual.setStatus("1");
                hrSignUnusualDao.postData(hrSignUnusual);
            } else if (hrSignDetail1.getCheckin_time().after(parse1) && hrSignDetail1.getCheckin_time().before(parse2) && StringUtils.isBlank(hrSignDetail1.getCheckin_type())) {
                //周末加班到凌晨后才打了班卡
                HrSignDetail signDetail = new HrSignDetail();
                BeanUtils.copyProperties(hrSignDetail1,signDetail);
                signDetail.setException_type("0");
                String lastDay = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, day, DateTimeUtil.PLUSTYPE_DAY, -1);
                signDetail.setCheckin_time(DateTimeUtil.convertStringToDate(lastDay+" 23:40:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignDetailDao.saveSign(signDetail);
                //删除原打卡数据
                if (1 != hrSignDetailDao.deleteSignData(hrSignDetail1)) {
                    throw new BusinessException("删除多余打卡数据失败");
                }
            }
        }

    }



    @Transactional
    public void initUserTelephone(HrSignUser hrSignUser) {
        //把一个用户的所有电话查出来 userid放在map里
        //遍历hr_sign_user
        //map里有user就判断
        Map<String, List<HrSignDetail>> map = new HashMap<>();
        List<HrSignDetail> detailList = hrSignDetailDao.getSignDetailUserTelephone();
        for (HrSignDetail h : detailList) {
            if ("f-6624699410853572733".equals(h.getUserid())) {
                System.out.println("aa");
            }
            if (!map.containsKey(h.getUserid())) {
                List<HrSignDetail> list = new ArrayList<>();
                list.add(h);
                map.put(h.getUserid(), list);
            } else {
                List<HrSignDetail> hrSignDetails = map.get(h.getUserid());
                hrSignDetails.add(h);
                map.put(h.getUserid(), hrSignDetails);
            }
        }

        List<HrSignUser> userList = hrSignUserDao.getAllUserDetailList(-1);
        List<HrSignUser> list = new ArrayList<>();
        for (HrSignUser u : userList) {
            if (map.containsKey(u.getUserid())) {
                List<HrSignDetail> hrSignDetails = map.get(u.getUserid());
                String oldMobile = "";
                for (int i = 0;i<hrSignDetails.size();i++) {
                    HrSignDetail detail = hrSignDetails.get(i);
                    oldMobile = oldMobile +","+ detail.getTelephone();
                }
                u.setOldMobile(oldMobile);
                list.add(u);
            }
        }

        //批量更新hr_sign_user
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[list.size()];
        for (int i =0; i<list.size(); i++){
            HrSignUser tmp = list.get(i);
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = hrSignUserDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("修改用户电话失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void handleNoDataOneDay(HrSignDetailArgs hrSignDetailArgs, Boolean isToday) {
        List<HrSignUser> userList;
        if (isToday) {
            userList = hrSignUserDao.getAllUserRegx();
        } else {
            userList = hrSignUserDao.getAllUserDetailList(1);
        }
        for (HrSignUser u : userList) {
            hrSignDetailArgs.setUserid(u.getUserid());
            List<HrSignDetail> signDetailByUseridAndDate;
            if (isToday) {
                HrSignTodayTempArgs hrSignTodayTempArgs = new HrSignTodayTempArgs();
                BeanUtils.copyProperties(hrSignDetailArgs, hrSignTodayTempArgs);
                List<HrSignTodayTemp> list = hrSignTodayTempDao.getSignDetailByUseridAndDate(hrSignTodayTempArgs);
                List<HrSignDetail> detailList = new ArrayList<>();
                for (HrSignTodayTemp h :list) {
                    HrSignDetail hrSignDetail = new HrSignDetailArgs();
                    BeanUtils.copyProperties(h, hrSignDetail);
                    detailList.add(hrSignDetail);
                }
                signDetailByUseridAndDate=detailList;
            } else {
                signDetailByUseridAndDate = hrSignDetailDao.getSignDetailByUseridAndDate(hrSignDetailArgs);
            }
            if (CollectionUtils.isEmpty(signDetailByUseridAndDate)) {
                if ("296".equals(u.getDepartment()) || "366".equals(u.getDepartment()) || "66589469155606640".equals(u.getUserid())
                        || "3772100866039107318".equals(u.getUserid())|| "f-2597357796695848037".equals(u.getUserid())||
                        "f-6484254936021907530".equals(u.getUserid())|| "1367450467269559252".equals(u.getUserid())||
                        "f-8733000582996015428".equals(u.getUserid())|| "f-505317375595884846".equals(u.getUserid())
                        || "f-8257427658706762517".equals(u.getUserid()) || "6495547487456348309".equals(u.getUserid())
                        || "6627617755605123440".equals(u.getUserid()) || "f-3700624286433128490".equals(u.getUserid())) {
                    String groupname = "建设集团御璟湖山项目部打卡";
                    String s = DateTimeUtil.dateToString(hrSignDetailArgs.getStartTime(), DateTimeUtil.YYYY_MM_DD);
                    this.saveSignDetail(u.getUserid(), u.getName(), u.getMobile(), groupname, "1", DateTimeUtil.convertStringToDate(s+" 08:30:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                    this.saveSignUnusual(u.getUserid(), u.getName(), u.getMobile(), "1", DateTimeUtil.convertStringToDate(s+" 08:30:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "3",isToday,"1");
                    this.saveSignDetail(u.getUserid(), u.getName(), u.getMobile(), groupname, "2", DateTimeUtil.convertStringToDate(s+" 18:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                    this.saveSignUnusual(u.getUserid(), u.getName(), u.getMobile(), "2", DateTimeUtil.convertStringToDate(s+" 18:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "4",isToday,"1");
                } else {
                    String groupname = "集团总部办公";
                    String s = DateTimeUtil.dateToString(hrSignDetailArgs.getStartTime(), DateTimeUtil.YYYY_MM_DD);
                    this.saveSignDetail(u.getUserid(), u.getName(), u.getMobile(), groupname, "1", DateTimeUtil.convertStringToDate(s+" 09:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                    this.saveSignUnusual(u.getUserid(), u.getName(), u.getMobile(), "1", DateTimeUtil.convertStringToDate(s+" 09:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "3",isToday,"1");
                    this.saveSignDetail(u.getUserid(), u.getName(), u.getMobile(), groupname, "2", DateTimeUtil.convertStringToDate(s+" 18:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                    this.saveSignUnusual(u.getUserid(), u.getName(), u.getMobile(), "2", DateTimeUtil.convertStringToDate(s+" 18:00:01", DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "4",isToday,"1");
                }
            }
        }
    }

    @Transactional
    public void confilictSignData(HrSignDetailArgs hrSignDetailArgs) {
        //处理打卡与外出重合情况
        HrSignEgressArgs hrSignEgressArgs = new HrSignEgressArgs();
        hrSignEgressArgs.setStartTime(hrSignDetailArgs.getStartTime());
        hrSignEgressArgs.setEndTime(hrSignDetailArgs.getEndTime());
        List<HrSignEgress> egressList = hrSignEgressDao.checkHaveEgressOneDay(hrSignEgressArgs);
        for (HrSignEgress e : egressList) {
            HrSignDetailArgs hrSignDetailArgs1 = new HrSignDetailArgs();
            hrSignDetailArgs1.setUserid(e.getUserid());
            hrSignDetailArgs1.setStartTime(e.getStartTime());
            hrSignDetailArgs1.setEndTime(e.getEndTime());
            List<HrSignDetail> signDetailByUseridAndDate = hrSignDetailDao.getSignDetailByUseridAndDate(hrSignDetailArgs1);
            handleConfilictSignWithEgress(signDetailByUseridAndDate);
        }

        //处理打卡与请假重合  -- 请假不能这么处理
        /*HrSignLeaveArgs hrSignLeaveArgs = new HrSignLeaveArgs();
        hrSignLeaveArgs.setStartTime(hrSignDetailArgs.getStartTime());
        hrSignLeaveArgs.setEndTime(hrSignDetailArgs.getEndTime());
        List<HrSignLeave> leaveList = hrSignLeaveDao.checkHaveLeaveOneDay(hrSignLeaveArgs);
        for (HrSignLeave leave : leaveList) {
            HrSignDetailArgs hrSignDetailArgs1 = new HrSignDetailArgs();
            hrSignDetailArgs1.setUserid(leave.getUserid());
            hrSignDetailArgs1.setStartTime(leave.getStartLeave());
            hrSignDetailArgs1.setEndTime(leave.getEndLeave());
            List<HrSignDetail> signDetailByUseridAndDate = hrSignDetailDao.getSignDetailByUseridAndDate(hrSignDetailArgs1);
            handleConfilictSignWithEgress(signDetailByUseridAndDate);
        }*/
    }

    @Transactional
    private void handleConfilictSignWithEgress(List<HrSignDetail> signDetailByUseridAndDate) {
        if (null != signDetailByUseridAndDate && signDetailByUseridAndDate.size()>0) {
            int count = 0;
            for (HrSignDetail h :signDetailByUseridAndDate) {
                if ("0".equals(h.getException_type())) {
                    count++;
                }
            }

            if (count > 0) {
                SqlParameterSource[] sqlParameterSources = new SqlParameterSource[count];
                SqlParameterSource[] sqlParameterSources1 = new SqlParameterSource[count];
                Long[] ids = commSequenceDao.getKey(new CommSequence("hr_sign_unusual", count));
                String flag = "-1";
                for (int i =0; i<count; i++){
                    HrSignDetail tmp = signDetailByUseridAndDate.get(i);
                    flag = tmp.getException_type();
                    tmp.setException_type("1");
                    if ("0".equals(flag)) {
                        sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
                    }

                    HrSignDetail hrSignDetail = signDetailByUseridAndDate.get(i);
                    HrSignUnusual tmp1 = new HrSignUnusual();
                    BeanUtils.copyProperties(hrSignDetail, tmp1);
                    Long key = ids[0] + i + 1;
                    tmp1.setId(key.intValue());
                    if ("1".equals(hrSignDetail.getCheckin_type())) {
                        tmp1.setStatus("3");
                    } else if ("2".equals(hrSignDetail.getCheckin_type())) {
                        tmp1.setStatus("4");
                    }
                    if ("0".equals(flag)) {
                        sqlParameterSources1[i] = new BeanPropertySqlParameterSource(tmp1);
                    }
                }

                try {
                    int[] flags = hrSignDetailDao.batchUpdate(sqlParameterSources);
                    for (int i=0;i<flags.length;i++){
                        if (flags[i]==0){
                            throw new BusinessException("处理外出和打卡重复时,批量修改打卡详情失败");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //在异常表hr_sign_unusual中插入数据
                try {
                    int[] flags = hrSignUnusualDao.batchUpdate(sqlParameterSources1);
                    for (int i=0;i<flags.length;i++){
                        if (flags[i]==0){
                            throw new BusinessException("处理外出和打卡重复时,批量新增hr_sign_unusual失败");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public List<HrDepartment> getdepartments(String[] companyIds) {
        User user = SpringSecurityUtil.getUser();
        List<HrDepartment> departments = new ArrayList<>();
        for (String companyId : companyIds) {
            HrDepartment hrDepartment = new HrDepartment();
            hrDepartment.setCompanyId(Integer.parseInt(companyId));
            hrDepartment.setParentId(0);
            List<HrDepartment> tempList = departmentDao.getDepartments(hrDepartment, user.getDataAuthoritiesRegexp());
            departments.addAll(tempList);
        }
        return departments;
    }

    /**
     * 获取当日考勤
     */
    @Transactional
    public void getTodaySignData() throws Exception {
        try {
            logger.info("开始抓取当日的数据!");
            String nowDate = dateFormat.format(new Date());     //获取执行时的时间
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            HrSignUnusualArgs hrSignUnusual = new HrSignUnusualArgs();
            hrSignUnusual.setStartTime(startDate);
            hrSignUnusual.setEndTime(endDate);
            this.getSignData(hrSignUnusual,true);
            logger.info("抓取当日考勤数据完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.getSignData",e);
        }
    }

    @Transactional
    public void handleToDaytThirdData() {
        try {
            logger.info("开始处理当日凌晨打卡!");
            String nowDate = dateFormat.format(new Date());     //获取执行时的时间
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            HrSignDetailArgs hrSignDetailArgs = new HrSignDetailArgs();
            hrSignDetailArgs.setStartTime(startDate);
            hrSignDetailArgs.setEndTime(endDate);
            this.handleThirdData(hrSignDetailArgs);
            logger.info("处理当日凌晨打卡完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.getSignData",e);
        }
    }

    /**
     * 处理当日打卡只有一条
     * @param hrSignTodayTempArgs
     * @param isToday
     */
    public void handleTodayNoData(HrSignTodayTempArgs hrSignTodayTempArgs, Boolean isToday) {
        List<HrSignTodayTemp> hrSignTodayTemps = hrSignTodayTempDao.getSignDetailByUseridAndDateA(hrSignTodayTempArgs);
        for (HrSignTodayTemp h : hrSignTodayTemps) {
            List<HrSignTodayTemp> list = hrSignTodayTempDao.getSignDetailByUseridAndCheckDate(h);
            if (null != list) {
                if (list.size() == 1) {
                    HrSignTodayTemp hrSignDetail1 = list.get(0);
                    HrSignWorktime hrSignWorktime = new HrSignWorktime();
                    hrSignWorktime.setGroupname(hrSignDetail1.getGroupname());
                    HrSignWorktime worktime = hrSignWorktimeDao.getWorktimeByGroupname(hrSignWorktime);
                    String checkInTime = "";
                    String checkOutTime = "";
                    if (null != worktime) {
                        checkInTime = worktime.getCheckInTime().toString();
                        checkOutTime = worktime.getCheckOutTime().toString();
                    }
                    String s = DateTimeUtil.dateToString(hrSignDetail1.getCheckin_time(), DateTimeUtil.YYYY_MM_DD);
                    if ("1".equals(hrSignDetail1.getCheckin_type())) {
                        saveSignDetail(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), hrSignDetail1.getGroupname(), "2", DateTimeUtil.convertStringToDate(s + " " + checkOutTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                        saveSignUnusual(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), "2", DateTimeUtil.convertStringToDate(s + " " + checkOutTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), hrSignDetail1.getGroupname(), "4",isToday,"1");
                    } else if ("2".equals(hrSignDetail1.getCheckin_type())) {
                        saveSignDetail(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), hrSignDetail1.getGroupname(), "1", DateTimeUtil.convertStringToDate(s+" "+checkInTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS),isToday,"1");
                        saveSignUnusual(hrSignDetail1.getUserid(), hrSignDetail1.getUsername(), hrSignDetail1.getTelephone(), "1", DateTimeUtil.convertStringToDate(s+" "+checkInTime, DateTimeUtil.YYYY_MM_DD_HH_MM_SS), hrSignDetail1.getGroupname(), "3",isToday,"1");
                    }
                }
            }
        }
    }

    public Page<HrSignTodayTemp> getTodaySignDataList(HrSignTodayTempArgs hrSignTodayTempArgs, Pageable pageable) {
        return hrSignTodayTempDao.getTodaySignDataList(hrSignTodayTempArgs, pageable);
    }

    @Transactional
    public void handleException(HrSignTodayTempArgs hrSignTodayTempArgs) throws Exception {
        List<HrSignTodayTemp> hrSignTodayTemps = hrSignTodayTempDao.getTodaySignUnusual();
        List<HrSignUnusual> list = new ArrayList<>();
        for (HrSignTodayTemp h :hrSignTodayTemps) {
            HrSignUnusual hrSignUnusual = new HrSignUnusual();
            BeanUtils.copyProperties(h,hrSignUnusual);
            list.add(hrSignUnusual);
        }
        this.offsetUnusual(list, true);
    }

    @Transactional
    public void deleteTodayTemp() {
        hrSignTodayTempDao.deleteTodayTemp();
        CommSequence commSequence = new CommSequence();
        commSequence.setName("hr_sign_today_temp");
        commSequence.setId(new Long(0));
        commSequenceDao.updateKey(commSequence);
    }

    public HrCompany getOneCompany(Integer currCompanyId) {
        HrCompany hrCompany = new HrCompany();
        hrCompany.setId(currCompanyId);
        hrCompany.setStatus(0);
        return companyDao.getOne(hrCompany);
    }

    @Transactional
    public void getOutsideSign(HrSignOutsideArgs hrSignOutsideArgs) {
        Date startdate = hrSignOutsideArgs.getStartTime();
        Date enddate = hrSignOutsideArgs.getEndTime();
        List<HrSignDepartment> departmentList = OASignUtil.getDepartmentList();
        List<HrSignDetail> hrSignDetails = new ArrayList<>();	//所有考勤记录
        List<HrSignUser> users = new ArrayList<>();		//所有用户
        for (HrSignDepartment d : departmentList) {
            Integer deptid = d.getId();
            List<HrSignUser> userList = OASignUtil.getUserList(deptid+"");
            users.addAll(userList);
        }
        List<String> userIdList = new ArrayList<>();
        for (int i = 0;i< users.size();i++) {
            HrSignUser hrSignUser = users.get(i);
            if (i == users.size()-1 || userIdList.size() == 100) {
                List<HrSignDetail> signList = OASignUtil.getSignList(startdate, enddate, userIdList, 2);
                hrSignDetails.addAll(signList);
                userIdList.clear();
            }
            userIdList.add(hrSignUser.getUserid());
        }
        this.checkOutSignList(hrSignDetails);
        /*List<HrSignUser> userList  = hrSignUserDao.getAllUserDetailList(1);

        for (int i = 0;i<userList.size();i++) {
            List<String> userIdList = new ArrayList<>();
            HrSignUser u = userList.get(i);
            userIdList.add(u.getUserid());
            List<HrSignDetail> signList = OASignUtil.getSignList(startdate, enddate, userIdList, 2);
            checkOutSignList(signList);
        }*/
    }

    @Transactional
    private void checkOutSignList(List<HrSignDetail> signList) {
        List<HrSignOutside> list = new ArrayList<>();
        for (HrSignDetail h :signList) {
            HrSignOutside temp = new HrSignOutside();
            BeanUtils.copyProperties(h,temp);
            temp.setCheckin_time(new Date(temp.getCheckin_time().getTime()*1000));
            temp.setStatus(0);
            temp.setCreateTime(new Date());
            list.add(temp);
        }
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[list.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_sign_outside", list.size()));
        for (int i =0; i<list.size(); i++){
            Long key = ids[0] + i + 1;
            HrSignOutside tmp = list.get(i);
            tmp.setId(key.intValue());
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = hrSignOutsideDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Page<HrSignOutside> getOutsideSignDataList(HrSignOutsideArgs hrSignOutsideArgs, Pageable pageable) {
        return hrSignOutsideDao.getOutsideSignDataList(hrSignOutsideArgs, pageable);
    }

    /**
     * 将外出打卡用做考勤
     * @param id
     */
    public void outSignDataToSignData(Integer id) throws ParseException {
        //修改hr_sign_outside的status位1
        HrSignOutside hrSignOutside = new HrSignOutside();
        hrSignOutside.setId(id);
        hrSignOutside.setStatus(1);
        if (1 != hrSignOutsideDao.updateOutside(hrSignOutside)) {
            throw new BusinessException("修改外出打卡数据状态失败");
        }
        //如果
        HrSignOutside signOutside = hrSignOutsideDao.getOneOutside(hrSignOutside);
        Date checkTime = timeFormat.parse(timeFormat.format(signOutside.getCheckin_time()));
        String checkin_type = "";
        if (checkTime.after(timeFormat.parse("18:00:00"))) {
            checkin_type = "2";
        } else if (checkTime.before(timeFormat.parse("09:00:00"))) {
            checkin_type = "1";
        }
        HrSignDetail hrSignDetail = new HrSignDetail();
        hrSignDetail.setUserid(signOutside.getUserid());
        hrSignDetail.setCheckin_time(signOutside.getCheckin_time());
        hrSignDetail.setCheckin_type(checkin_type);
        List<HrSignDetail> signDetailList = hrSignDetailDao.getSignDetailByUseridAndCheckDate(hrSignDetail);
        if (null != signDetailList && signDetailList.size() == 1) {
            HrSignDetail detail1 = signDetailList.get(0);
            if (!"0".equals(detail1.getCheckin_exception())) {
                detail1.setException_type("0");
                hrSignDetailDao.updateHrSignDetailByUseridTimeType(detail1);
                HrSignUnusual hrSignUnusual = new HrSignUnusual();
                BeanUtils.copyProperties(detail1, hrSignUnusual);
                hrSignUnusual.setStatus("0");
                hrSignUnusual.setUpdate_time(new Date());
                hrSignUnusual.setUpdate_user(SpringSecurityUtil.getUser().getUserId().intValue());
                hrSignUnusual.setCheckDate(detail1.getCheckin_time());
                hrSignUnusualDao.updateHrSignUnusualByUserid(hrSignUnusual);
            }
        } else if (CollectionUtils.isEmpty(signDetailList)) {
            String s = dateFormat.format(signOutside.getCheckin_time());
            String groupname = "集团总部办公";
            if ("1".equals(checkin_type)) {
                this.saveSignDetail(signOutside.getUserid(), signOutside.getUsername(), signOutside.getTelephone(), groupname, "1", DateTimeUtil.convertStringToDate(s+" 08:00:00", DateTimeUtil.YYYY_MM_DD_HH_MM_SS),false,"0");
                this.saveSignUnusual(signOutside.getUserid(), signOutside.getUsername(), signOutside.getTelephone(), "1", DateTimeUtil.convertStringToDate(s+" 08:00:00", DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "0",false,"0");
            } else if ("2".equals(checkin_type)) {
                this.saveSignDetail(signOutside.getUserid(), signOutside.getUsername(), signOutside.getTelephone(), groupname, "2", DateTimeUtil.convertStringToDate(s+" 18:00:00", DateTimeUtil.YYYY_MM_DD_HH_MM_SS),false,"0");
                this.saveSignUnusual(signOutside.getUserid(), signOutside.getUsername(), signOutside.getTelephone(), "2", DateTimeUtil.convertStringToDate(s+" 18:00:00", DateTimeUtil.YYYY_MM_DD_HH_MM_SS), groupname, "0",false,"0");
            }
        }
    }
}
