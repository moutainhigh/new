package com.huayu.hr.web;

import com.alibaba.fastjson.JSON;
import com.huayu.hr.domain.*;
import com.huayu.hr.service.HrAttendanceService;
import com.huayu.hr.web.args.HrAtteAttDetailArgs;
import com.huayu.hr.web.args.HrAtteSchedulArgs;
import com.huayu.hr.web.args.HrAtteSchedulDetailArgs;
import com.huayu.hr.web.args.HrAtteShiftArgs;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.DBContextHolder;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by DengPeng on 2017/4/1.
 */
@Controller
@RequestMapping("/hr")
public class HrAttendanceController {

    @Autowired
    private HrAttendanceService hrAttendanceService;

    /**
     * 添加班次
     * @return
     */
    @RequestMapping("/atte/shiftEdit")
    public String shiftEdit(){

        return ".hr.attendance.shiftEdit";
    }

    /**
     * 编辑班次
     * @param id
     * @param shift
     * @param model
     * @return
     */
    @RequestMapping("/atte/shiftEdit/{id}")
    public String shiftEdit(@PathVariable("id") Integer id,  HrAtteShift shift, Model model){
        shift.setId(id);
        model.addAttribute("entity",hrAttendanceService.getOneShift(shift));
        return ".hr.attendance.shiftEdit";
    }

    /**
     * 保存班次
     * @param shift
     * @return
     */
    @RequestMapping(value = "/atte/postShiftEdit",method = RequestMethod.POST)
    @ResponseBody
    public  BaseResult postShiftEdit(HrAtteShift shift){
        User user = SpringSecurityUtil.getUser();
        try {
            if (null==shift.getId()){
                shift.setCreateUser(user.getUserId().intValue());
                return hrAttendanceService.addShift(shift);
            }else{
                shift.setUpdateUser(user.getUserId().intValue());
                return hrAttendanceService.updateShift(shift);
            }
        } catch (BusinessException e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    /**
     * 班次列表页面
     * @return
     */
    @RequestMapping("/atte/shiftList")
    public String shiftList(){

        return ".hr.attendance.shiftList";
    }

    /**
     * 班次列表数据
     * @param param
     * @param args
     * @return
     */
    @RequestMapping(value = "/atte/shiftListData")
    @ResponseBody
    public Map<String,Object> shiftListData(@RequestParam Map<String,Object> param, HrAtteShiftArgs args,PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        Page<HrAtteShift> page = hrAttendanceService.shiftListData(args, pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 排班列表
     * @return
     */
    @RequestMapping("/atte/schedulList")
    public String schedulList(Model model){
        Date date = new Date();
        List<String> dateList = hrAttendanceService.buildCalendarDate(date);
        model.addAttribute("dateList",dateList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        model.addAttribute("month",sdf.format(date));
        return ".hr.attendance.schedulList";
    }

    /**
     * 排班列表数据
     * @param param
     * @param args
     * @return
     */
    @RequestMapping(value = "/atte/schedulListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> schedulListData(@RequestParam Map<String,Object> param, HrAtteSchedulDetailArgs args){
        args.setStartDate(new Date());
        List<HrAtteSchedul> data = hrAttendanceService.schedulListData(args);
        param.put("data",data);
        return param;
    }


    /**
     * 单人月排班
     * @return
     */
    @RequestMapping("/atte/monthSchedulOne")
    public String monthSchedulOne(){

        return ".hr.attendance.monthSchedulOne";
    }

    @RequestMapping("/atte/schedulEdit")
    public String schedulEdit(Model model){
        model.addAttribute("shiftList",hrAttendanceService.shiftListData());
        return ".hr.attendance.schedulEdit";
    }


    /**
     * 提交排班
     * @param args
     * @return
     */
    @RequestMapping(value = "/atte/postSchedulEdit",method = RequestMethod.POST)
    @ResponseBody
    public  BaseResult postSchedulEdit(HrAtteSchedulArgs args){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrAttendanceService.addSchedul(args);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 新增假日
     * @return
     */
    @RequestMapping("/atte/holidayEdit")
    public String holidayEdit(){

        return ".hr.attendance.holidayEdit";
    }

    /**
     * 提交假日数据
     * @param holiday
     * @return
     */
    @RequestMapping(value = "/atte/postHolidayEdit",method = RequestMethod.POST)
    @ResponseBody
    public  BaseResult postHolidayEdit(HrAtteHolidays holiday){
        User user = SpringSecurityUtil.getUser();
        try {
            if (null==holiday.getId()){
                holiday.setCreateUser(user.getUserId().intValue());
                return hrAttendanceService.addHoliday(holiday);
            }else{
                holiday.setUpdateUser(user.getUserId().intValue());
                return hrAttendanceService.updateHoliday(holiday);
            }
        } catch (BusinessException e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    /**
     * 编辑假日
     * @param id
     * @param holiday
     * @param model
     * @return
     */
    @RequestMapping("/atte/holidayEdit/{id}")
    public String holidayEdit(@PathVariable("id") Integer id,  HrAtteHolidays holiday, Model model){
        holiday.setId(id);
        model.addAttribute("entity",hrAttendanceService.getHolidayOne(holiday));
        return ".hr.attendance.holidayEdit";
    }

    @RequestMapping("/atte/holidayList")
    public String holidayList(){

        return ".hr.attendance.holidayList";
    }

    @RequestMapping(value = "/atte/holidayListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> holidayListData(@RequestParam Map<String,Object> param, HrAtteHolidays holidays, PageArgs args){

        Pageable pageable = new PageRequest(args.getPageIndex(), args.getLength());
        Page<HrAtteHolidays> page = hrAttendanceService.holidayListData(holidays,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    @RequestMapping(value = "/atte/deleteHoliday",method = RequestMethod.POST)
    @ResponseBody
    public  BaseResult deleteHoliday(HrAtteHolidays holiday){
        BaseResult result = BaseResult.initBaseResult();
        try {
            if (null==holiday.getId()){
                result.setRmsg("参数错误！");
                return result;
            }
            User user = SpringSecurityUtil.getUser();
            holiday.setDeleteUser(user.getUserId().intValue());
            hrAttendanceService.deleteHoliday(holiday);
            return result.setSuccess();
        } catch (BusinessException e) {
            result.setRmsg(e.getMessage());
            return result;
        }
    }


    /**
     * 新增出差
     * @return
     */
    @RequestMapping("/atte/travelEdit")
    public String travelEdit(){

        return ".hr.attendance.travelEdit";
    }

    @RequestMapping(value = "/atte/postTravelEdit",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postTravelEdit(HrAtteTravel travel){
        User user = SpringSecurityUtil.getUser();
        BaseResult result = BaseResult.initBaseResult();
        try {
            if (null==travel.getId()){
                travel.setCreateUser(user.getUserId().intValue());
                hrAttendanceService.addHrAtteTravel(travel);
            }else{
                travel.setUpdateUser(user.getUserId().intValue());
                hrAttendanceService.updateHrAtteTravel(travel);
            }
            return result.setSuccess();
        } catch (BusinessException e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    @RequestMapping("/atte/travelList")
    public String travelList(){

        return ".hr.attendance.travelList";
    }

    @RequestMapping(value = "/atte/travelListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> travelListData(@RequestParam Map<String,Object> param, HrAtteTravel travel, PageArgs args){

        Pageable pageable = new PageRequest(args.getPageIndex(), args.getLength());
        Page<HrAtteTravel> page = hrAttendanceService.travelListData(travel,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/atte/travelEdit/{id}")
    public String travelEdit(@PathVariable("id") Integer id,  HrAtteTravel travel, Model model){
        travel.setId(id);
        model.addAttribute("entity",hrAttendanceService.getTravelOne(travel));
        return ".hr.attendance.travelEdit";
    }


    /**
     * 新增加班
     * @return
     */
    @RequestMapping("/atte/overtimeEdit")
    public String overtimeEdit(){

        return ".hr.attendance.overtimeEdit";
    }

    @RequestMapping(value = "/atte/postOvertimeEdit",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postOvertimeEdit(HrAtteOvertime entity){
        User user = SpringSecurityUtil.getUser();
        BaseResult result = BaseResult.initBaseResult();
        try {
            if (null== entity.getId()){
                entity.setCreateUser(user.getUserId().intValue());
                hrAttendanceService.addHrAtteOvertime(entity);
            }else{
                entity.setUpdateUser(user.getUserId().intValue());
                hrAttendanceService.updateHrAtteOvertime(entity);
            }
            return result.setSuccess();
        } catch (BusinessException e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    @RequestMapping("/atte/overtimeList")
    public String overtimeList(){

        return ".hr.attendance.overtimeList";
    }

    @RequestMapping(value = "/atte/overtimeListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> overtimeListData(@RequestParam Map<String,Object> param, HrAtteOvertime entity, PageArgs args){

        Pageable pageable = new PageRequest(args.getPageIndex(), args.getLength());
        Page<HrAtteOvertime> page = hrAttendanceService.overtimelListData(entity,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/atte/overtimeEdit/{id}")
    public String overtimeEdit(@PathVariable("id") Integer id,  HrAtteOvertime entity, Model model){
        entity.setId(id);
        model.addAttribute("entity",hrAttendanceService.getOvertimeOne(entity));
        return ".hr.attendance.overtimeEdit";
    }

    /**
     * 新增调班
     * @return
     */
    @RequestMapping("/atte/chgSchedulEdit")
    public String chgSchedulEdit(Model model){
        model.addAttribute("shiftList",hrAttendanceService.shiftListData());
        return ".hr.attendance.chgSchedulEdit";
    }


    /***
     * 导入数据页面
     * @return
     */
    @RequestMapping("/atte/attDetailImport")
    public String attDetailImport(){

        return ".hr.attendance.attDetailImport";
    }

    /**
     * 导入考勤机数据
     * @param args
     * @return
     */
    @RequestMapping(value = "/atte/importAttDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getAttShifts(HrAtteAttDetailArgs args){
        BaseResult result = BaseResult.initBaseResult();
        List<Integer> empIds = JSON.parseArray(args.getEmpIdsStr(), Integer.class);
        List<Integer> badge = JSON.parseArray(args.getBadgenumbersStr(), Integer.class);
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0,size = empIds.size();i<size;i++){
            Integer num = badge.get(i);
            if (null == num){
                result.setRmsg("没有找到列表中第"+(i+1)+"个人的考勤号");
                return result;
            }else{
                map.put(num,empIds.get(i));
            }
        }
        args.setBadgeEmpIdMap(map);
        args.setEmpIds(empIds);
        args.setBadgenumbers(badge);
        try {
            if (hrAttendanceService.checkAttDetailExist(args)){
                result.setRmsg("考勤数据已经导入了！");
                return result;
            }
            DBContextHolder.setDBType(DBContextHolder.SQLServerDATA_SOURCE);
            List<HrAtteAttDetail> attDetailList = hrAttendanceService.getAttDetailListFromSQLServer(args);
            if (CollectionUtils.isEmpty(attDetailList)){
                result.setRmsg("没有查询到考勤数据！");
                return result;
            }
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE);
            hrAttendanceService.addAttDetail(args,attDetailList);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/atte/attDetailList")
    public String atteAttDetailList(){

        return ".hr.attendance.attDetailList";
    }

    /**
     * 打卡详情
     * @param param
     * @param entity
     * @param args
     * @return
     */
    @RequestMapping(value = "/atte/attDetailListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> attDetailListData(@RequestParam Map<String,Object> param, HrAtteAttDetailArgs entity, PageArgs args){

        Pageable pageable = new PageRequest(args.getPageIndex(), args.getLength());
        Page<HrAtteAttDetail> page = hrAttendanceService.attDetailListData(entity,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     *  考勤汇总
     * @return
     */
    @RequestMapping("/atte/attReportList")
    public String attReportList(Model model){
        Date now = new Date();
        model.addAttribute("startDate", DateTimeUtil.getFirstDateOfMonth(now));
        model.addAttribute("endDate", now);
        return ".hr.attendance.attReportList";
    }


    @RequestMapping(value = "/atte/attReportListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> attReportListData(@RequestParam Map<String,Object> param, HrAtteAttDetailArgs entity, PageArgs args){
        if (null==entity.getStartDate()||null==entity.getEndDate()){
            param.put("data", Collections.EMPTY_LIST);
            param.put("recordsTotal",0);
            param.put("recordsFiltered",0);
        }else{

            Pageable pageable = new PageRequest(args.getPageIndex(), args.getLength());
            Page<HrAtteAttDetail> page = hrAttendanceService.statisticsAttResult(entity,pageable);
            param.put("data",page.getContent());
            param.put("recordsTotal",page.getTotalElements());
            param.put("recordsFiltered",page.getTotalElements());
        }
        return param;
    }

    /**
     * 考勤异常
     * @param model
     * @return
     */
    @RequestMapping("/atte/attExceptionReportList")
    public String attExceptionReportList(Model model){
        Date now = new Date();
        model.addAttribute("startDate", DateTimeUtil.getFirstDateOfMonth(now));
        model.addAttribute("endDate", now);
        return ".hr.attendance.attExceptionReportList";
    }

    @RequestMapping(value = "/atte/attExceptionReportListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> attExceptionReportListData(@RequestParam Map<String,Object> param, HrAtteAttDetailArgs entity, PageArgs args){
        if (null==entity.getStartDate()||null==entity.getEndDate()){
            param.put("data", Collections.EMPTY_LIST);
            param.put("recordsTotal",0);
            param.put("recordsFiltered",0);
        }else{

            Pageable pageable = new PageRequest(args.getPageIndex(), args.getLength());
            Page<HrAtteAttDetail> page = hrAttendanceService.statisticsExceptionAttResult(entity,pageable);
            param.put("data",page.getContent());
            param.put("recordsTotal",page.getTotalElements());
            param.put("recordsFiltered",page.getTotalElements());
        }
        return param;
    }


}
