package com.huayu.signWechat.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.hr.dao.HrEmployeeDao;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.signWechat.dao.*;
import com.huayu.signWechat.domain.*;
import com.huayu.signWechat.utils.OASignUtil;
import com.huayu.signWechat.web.args.HrSignDetailArgs;
import com.huayu.signWechat.web.args.HrSignOutsideArgs;
import com.huayu.signWechat.web.args.HrSignUnusualArgs;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/7/20.
 */
@Service
public class HrSignTaskService {

    private static Logger logger = LoggerFactory.getLogger(HrSignTaskService.class);

    @Autowired
    private HrSignDetailDao hrSignDetailDao;
    @Autowired
    private HrSignDepartmentDao hrSignDepartmentDao;
    @Autowired
    private HrSignUserDao hrSignUserDao;
    @Autowired
    private HrSignUnusualDao hrSignUnusualDao;
    @Autowired
    private HrEmployeeDao hrEmployeeDao;
    @Autowired
    private HrSignService hrSignService;
    @Autowired
    private CommSequenceDao commSequenceDao;
    @Autowired
    private HrSignLeaveSonDao hrSignLeaveSonDao;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 更新用户信息
     * @throws ParseException
     */
    @Scheduled(cron="0 30 23 * * ?")
    @Transactional
    public void getAndSaveSign() throws ParseException {
        try {
            logger.info("开始更新打卡人员");
            List<HrSignDepartment> departmentList = OASignUtil.getDepartmentList();
            hrSignDepartmentDao.updateDepartment();
            List<HrSignUser> signUserList = hrSignUserDao.getAllUserDetailList(-1);
            Map<String, HrSignUser> map = new HashMap<>();

            List<HrSignUser> updateUser = new ArrayList<>();
            for (HrSignUser user : signUserList) {
                map.put(user.getUserid(),user);
            }

            for (HrSignDepartment d : departmentList) {
                Integer depid = d.getId();
                //把部门信息存到数据库
                HrSignDepartment one = hrSignDepartmentDao.getOneDepartment(new HrSignDepartment(d.getId()));
                if (null == one) {
                    hrSignDepartmentDao.postDepartment(d);
                }
                List<HrSignUser> userList = OASignUtil.getUserList(depid+"");
                for (HrSignUser u : userList) {
                    if (!map.containsKey(u.getUserid())) {
                        //新用户直接存到数据库
                        HrEmployee employee = new HrEmployee();
                        employee.setEmpName(u.getName());
                        employee.setOldMobile(u.getMobile());
                        HrEmployee hrEmployee = hrEmployeeDao.getEmployeeByNameAndMobile(employee);
                        hrSignService.saveSignUser(u, hrEmployee);
                    } else {
                        //老用户
                        u.setDepartment(u.getDepartment().replace("[","").replace("]",""));
                        if (!u.equals(map.get(u.getUserid()))) {
                            //用户信息有改变
                            HrSignUser hrSignUser = map.get(u.getUserid());
                            if (!u.getMobile().equals(map.get(u.getUserid()).getMobile())) {
                                //手机有变
                                String oldMobile = hrSignUser.getMobile();
                                String oldMobiles = hrSignUser.getOldMobile();
                                if (StringUtils.isNotBlank(oldMobiles)) {
                                    oldMobiles = oldMobiles+","+oldMobile+","+u.getMobile();
                                } else {
                                    oldMobiles = oldMobile+","+u.getMobile();
                                }
                                u.setOldMobile(oldMobiles);
                            } else {
                                u.setOldMobile(map.get(u.getUserid()).getOldMobile());
                            }
                        } else {
                            u.setOldMobile(map.get(u.getUserid()).getOldMobile());
                        }
                        HrEmployee employee = new HrEmployee();
                        employee.setEmpName(u.getName());
                        employee.setOldMobile(u.getOldMobile());
                        HrEmployee hrEmployee = hrEmployeeDao.getEmployeeByNameAndMobile(employee);
                        if (null != hrEmployee) {
                            u.setIsMatched(1);
                        } else {
                            u.setIsMatched(0);
                        }
                        updateUser.add(u);
                    }
                }
            }

            //批量更新hr_sign_user
            SqlParameterSource[] sqlParameterSources = new SqlParameterSource[updateUser.size()];
            Long[] ids = commSequenceDao.getKey(new CommSequence("hr_sign_user", updateUser.size()));
            for (int i =0; i<updateUser.size(); i++){
                Long key = ids[0] + i + 1;
                HrSignUser tmp = updateUser.get(i);
                tmp.setId(key.intValue());
                sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
            }
            try {
                int[] flags = hrSignUserDao.batchUpdate(sqlParameterSources);
                for (int i=0;i<flags.length;i++){
                    if (flags[i]==0){
                        throw new BusinessException("添加失败");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("完成更新打卡人员");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.getAndSaveSign",e);
        }
    }

    /**
     * 抓取当日打卡数据
     * @throws ParseException
     */
    @Scheduled(cron="0 0 4 * * ?")
    public void getSignData() {
        try {
            logger.info("开始抓取前一天的数据!");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startdate = sdf.parse(nowDate + " 04:00:00");
            Date enddate  = sdf.parse(forceDate + " 04:00:00");
            HrSignUnusualArgs hrSignUnusual = new HrSignUnusualArgs();
            hrSignUnusual.setStartTime(startdate);
            hrSignUnusual.setEndTime(enddate);
            hrSignService.getSignData(hrSignUnusual,false);
            logger.info("抓取前一天考勤数据完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.getSignData",e);
        }
    }

    /**
     * 处理上一天凌晨打卡数据
     */
    @Scheduled(cron = "0 0 5 * * ?")
    @Transactional
    public void handleThirdDataTask() {
        try {
            logger.info("开始处理前日多条打卡数据");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(forceDate + " 00:00:00");    //当天零点
            Date endDate  = sdf.parse(forceDate + " 04:00:00");     //当天4点
            HrSignDetailArgs hrSignDetailArgs = new HrSignDetailArgs();
            hrSignDetailArgs.setStartTime(startDate);
            hrSignDetailArgs.setEndTime(endDate);
            hrSignService.handleThirdData(hrSignDetailArgs);
            logger.info("完成处理前日多条打卡数据");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("handleThirdDataTask",e);
        }
    }

    /**
     * 处理少一条数据
     * 要在处理凌晨打卡完成后执行
     */
//    @Scheduled(cron = "0 10 5 * * ?")
    @Transactional
    public void handleNoData() {
        try {
            logger.info("开始处理少一条数据情况");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            List<Calendar> holidayList = hrSignService.getHolidayList(startDate, endDate);
            if (!DateTimeUtil.dateToString(startDate,DateTimeUtil.YYYY_MM_DD).equals(DateTimeUtil.dateToString(endDate,DateTimeUtil.YYYY_MM_DD))) {
                //开始时间和结束时间不在同一天
                throw new BusinessException("开始时间和结束时间应该为一天");
            }
            if (CollectionUtils.isEmpty(holidayList)) {
                System.out.println("工作日");
                HrSignDetailArgs hrSignDetailArgs = new HrSignDetailArgs();
                hrSignDetailArgs.setStartTime(startDate);
                hrSignDetailArgs.setEndTime(endDate);
                hrSignService.handleNoData(hrSignDetailArgs,false);
            } else {
                System.out.println("周末");
            }
            logger.info("完成处理少一条数据情况");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleNoData", e);
        }
    }

    /**
     * 处理一天没有数据的问题
     * 要在 只有一条数据执行完成后才执行
     */
//    @Scheduled(cron = "0 15 5 * * ?")
    @Transactional
    public void handleNoDataOneDay() {
        try {
            logger.info("开始处理少没有数据情况");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            List<Calendar> holidayList = hrSignService.getHolidayList(startDate, endDate);
            if (!DateTimeUtil.dateToString(startDate,DateTimeUtil.YYYY_MM_DD).equals(DateTimeUtil.dateToString(endDate,DateTimeUtil.YYYY_MM_DD))) {
                //开始时间和结束时间不在同一天
                throw new BusinessException("开始时间和结束时间应该为一天");
            }
            if (CollectionUtils.isEmpty(holidayList)) {
                System.out.println("工作日");
                HrSignDetailArgs hrSignDetailArgs = new HrSignDetailArgs();
                hrSignDetailArgs.setStartTime(startDate);
                hrSignDetailArgs.setEndTime(endDate);
                hrSignService.handleNoDataOneDay(hrSignDetailArgs,false);
            } else {
                System.out.println("周末");
            }
            logger.info("完成处理没有数据情况");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleNoData", e);
        }
    }

    /**
     * 处理请假数据
     */
    @Scheduled(cron = "0 20 5 * * ?")
    @Transactional
    public void handleLeaveTask() {
        try {
            logger.info("开始处理当日请假数据");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            HrSignLeaveSon hrSignLeaveSon = new HrSignLeaveSon();
            hrSignLeaveSon.setStartTime(startDate);
            hrSignLeaveSon.setEndTime(endDate);
            hrSignService.handleLeave(hrSignLeaveSon);
            logger.info("完成处理当日请假数据");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.handleLeaveTask",e);
        }
    }

    @Scheduled(cron = "0 25 5 * * ?")
    @Transactional
    public void confilictSignDataTask() {
        try {
            logger.info("开始处理当日外出数据和打卡重复");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            HrSignDetailArgs hrSignDetailArgs = new HrSignDetailArgs();
            hrSignDetailArgs.setStartTime(startDate);
            hrSignDetailArgs.setEndTime(endDate);
            hrSignService.confilictSignData(hrSignDetailArgs);
            logger.info("完成处理当日外出数据和打卡重复");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.confilictSignDataTask",e);
        }
    }


    /**
     * 冲销每日的异常数据
     */
    @Scheduled(cron = "0 30 5 * * ?") //每天一点定时冲销异常
    @Transactional
    public void offsetUnusualTask() {
        try {
            logger.info("开始冲销异常");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate1  = DateTimeUtil.getFirstDateOfMonth(new Date());     //本月第一天的 01:00:00
            Date startDate  = sdf.parse(dateFormat.format(startDate1)+" 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");       //当前执行时间的前一天23:59:59
            System.out.println(startDate);
            System.out.println(endDate);

            //查异常表
            HrSignUnusual hrSignUnusual = new HrSignUnusual();
            hrSignUnusual.setStartDate(startDate);
            hrSignUnusual.setEndDate(endDate);
            List<HrSignUnusual> hrSignUnusuals = hrSignUnusualDao.getSignUnusualByUseridAndCheckin_time(hrSignUnusual);
            hrSignService.offsetUnusual(hrSignUnusuals, false);//冲销异常

            logger.info("冲销异常结束");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.offsetUnusualTask",e);
        }
    }

    /**
     * 根据迟到早退尺度处理异常状态
     */
    @Scheduled(cron = "0 40 5 * * ?")
    @Transactional
    public void setNoWorkByLateOrLeaveEarlyTask() {
        try {
            logger.info("开始处理迟到异常状态");
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            List<HrSignUser> userList = hrSignUserDao.getUserDetailList("searchAll", new HrSignUser());
            for (HrSignUser hrSignUser :userList) {
                HrSignUnusual hrSignUnusual = new HrSignUnusual();
                hrSignUnusual.setUserid(hrSignUser.getUserid());
                hrSignUnusual.setStartDate(startDate);
                hrSignUnusual.setEndDate(endDate);
                List<HrSignUnusual> hrSignUnusuals = hrSignUnusualDao.getHrSignUnusualStatisticsList(hrSignUnusual);
                for (HrSignUnusual h :hrSignUnusuals) {
                    if ("1".equals(h.getStatus())) {
                        //迟到
                        hrSignService.setNoWorkByLateOrLeaveEarly(h);
                    } else if ("2".equals(h.getStatus())) {
                        //早退
                        hrSignService.setNoWorkByLateOrLeaveEarly(h);
                    }
                }
            }
            logger.info("完成处理迟到异常状态");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.setNoWorkByLateOrLeaveEarlyTask",e);
        }
    }

    @Scheduled(cron = "0 50 5 * * ?")
    @Transactional
    public void getOutsideSignTask() {
        try{
            logger.info("开始获取前一天的外出数据");
            HrSignOutsideArgs hrSignOutsideArgs = new HrSignOutsideArgs();
            String forceDate = dateFormat.format(new Date());     //获取执行时的时间
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);      //00:01抓前一天的数据
            Date startDate = sdf.parse(nowDate + " 00:00:00");
            Date endDate  = sdf.parse(nowDate + " 23:59:59");
            hrSignOutsideArgs.setStartTime(startDate);
            hrSignOutsideArgs.setEndTime(endDate);
            hrSignService.getOutsideSign(hrSignOutsideArgs);
            logger.info("完成获取前一天的外出数据");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getOutsideSignTask",e);
        }
    }

    @Scheduled(cron = "0 0 1 1 * ?") //每月一号 01:00获取上月报表
    @Transactional
    public void signStatisticsData() {
        try {
            logger.info("开始生成上月报表");
            String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, dateFormat.format(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1);
            Date startTime = DateTimeUtil.convertStringToDate(nowDate + " 00:00:00", DateTimeUtil.YYYY_MM_DD_HH_MM_SS);
            Date endTime = DateTimeUtil.convertStringToDate(nowDate + " 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS);
            HrSignStatistics entity = new HrSignStatistics();
            Date startDate  = DateTimeUtil.getFirstDateOfMonth(startTime);
            Date endDate  = DateTimeUtil.getLastDateOfMonth(endTime);
            entity.setStartDate(startDate);
            entity.setEndDate(endDate);
            List<HrSignStatistics> statistics = hrSignService.signStatisticsData(entity);
            hrSignService.saveStatisticsData(statistics, startDate, endDate);
            logger.info("生成上月报表完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HrSignTaskService.signStatisticsData",e);
        }
    }


}
