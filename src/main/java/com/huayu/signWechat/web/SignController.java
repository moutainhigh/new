package com.huayu.signWechat.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.domain.HrCompany;
import com.huayu.hr.domain.HrDepartment;
import com.huayu.hr.service.OrgService;
import com.huayu.inventory.domain.validate.GroupQuery;
import com.huayu.signWechat.domain.*;
import com.huayu.signWechat.service.HrSignService;
import com.huayu.signWechat.service.HrSignTaskService;
import com.huayu.signWechat.utils.OASignUtil;
import com.huayu.signWechat.web.args.*;
import com.huayu.user.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2017/7/10.
 */
@Controller
@RequestMapping("/admin/sign")
public class SignController {


//    private static String companyName = "人力行政中心";
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private HrSignService hrSignService;
    @Autowired
    private HrSignTaskService hrSignTaskService;
    @Autowired
    private OrgService orgService;

    @RequestMapping("/toSignList")
    public String toSignList(Model model) {
        User user = SpringSecurityUtil.getUser();

        model.addAttribute("company",user.getCompanyName());
        model.addAttribute("companyId",user.getCurrCompanyId());/////////////////////////////
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.signDetailList";
    }

    /**
     * 获取公司
     * @return
     */
    @RequestMapping("/getCompanys")
    @ResponseBody
    public BaseResult getCompanys() {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            User user = SpringSecurityUtil.getUser();
            String currCompanyCode = user.getCurrCompanyCode();
            Integer currCompanyId = user.getCurrCompanyId();

            HrCompany hrCompany = hrSignService.getOneCompany(currCompanyId);
            List<HrCompany> companyList = null;
            if (null != hrCompany) {
                if (hrCompany.getIsParent()== 1) {
                    //是父节点
                    companyList = orgService.getAllCompanies(currCompanyId, currCompanyCode);
                } else {
                    companyList = new ArrayList<>();
                    companyList.add(hrCompany);
                }
            }
            baseResult.setRdata(companyList);
            baseResult.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCompanys",e);
        }
        return baseResult;
    }

    @RequestMapping("/getdepartments")
    @ResponseBody
    public BaseResult getdepartments(@RequestParam(value = "companyId[]") String[] companyId) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            List<HrDepartment> departmentList = hrSignService.getdepartments(companyId);
            baseResult.setRdata(departmentList);
            baseResult.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getdepartments", e);
        }
        return baseResult;
    }

    /**
     * 打卡明细列表
     * @param param
     * @param hrSignDetailArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/listSignDetailData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listSignDetailData(@RequestParam Map<String,Object> param, HrSignDetailArgs hrSignDetailArgs, PageArgs args){
        String companyName = SpringSecurityUtil.getUser().getCompanyName();
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"c.name,d.name,su.userid,sd.checkin_time,sd.checkin_type");
        Page<HrSignDetail> page = hrSignService.signDetailListData(hrSignDetailArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 请假列表
     * @param param
     * @param hrSignLeaveArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/listLeaveDetailData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listLeaveDetailData(@RequestParam Map<String,Object> param, HrSignLeaveArgs hrSignLeaveArgs, PageArgs args){
        String companyName = SpringSecurityUtil.getUser().getCompanyName();
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.username, s.startLeave");
        Page<HrSignLeave> page = hrSignService.leaveDetailListData(hrSignLeaveArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 补录请假数据
     * @param param
     * @param hrSignLeaveArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/listLeavePatchData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listLeavePatchData(@RequestParam Map<String,Object> param, HrSignLeaveArgs hrSignLeaveArgs, PageArgs args){
        String companyName = SpringSecurityUtil.getUser().getCompanyName();
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"s.startLeave");
        Page<HrSignLeave> page = hrSignService.listLeavePatchData(hrSignLeaveArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/toStatisticsListPage")
    public String toStatisticsListPage(Model model) {
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("company",user.getCompanyName());
        model.addAttribute("companyId",user.getCurrCompanyId());
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.statisticsList";
    }

    @RequestMapping("/toLeavePatchListPage")
    public String toLeavePatchListPage() {
        return ".sign.check.patchLeaveRecord";
    }

    /**
     * 考勤报表
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/listStatisticsData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsData(@Validated(GroupQuery.class) HrSignStatistics entity, BindingResult result) throws Exception {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            List<HrSignStatistics> statistics = hrSignService.signStatisticsData(entity);
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出考勤报表
     * @param response
     * @param entity
     * @return
     */
    @RequestMapping("/exportSignStatistics")
    public String exportSignStatistics(HttpServletResponse response, HrSignStatistics entity) {
        try {
            List<HrSignStatistics> statistics = hrSignService.signStatisticsData(entity);
            for (int i = 0;i < statistics.size();i++) {
                statistics.get(i).setIndex(i+1);
            }
            String fileName = "考勤报表";
            String[] columnNames = {"序号","合同单位","管理模块","管理单位","管理部门","人员编码","出生日期","年龄","入司时间","转正时间","用工状态",
                    "姓名","性别","岗位","缺勤日","实际发放日","出勤","休息","外出","其他假","事假","病假","换休","身份证号","职级","分类","在职状态","绩效体系","未出勤"};

            String[] keys = {"index","pactCompanyName","plateName","companyName","departmentName","userNum","birthdate","age","joinCompDate","InDueFormDate",
                    "useStatus","name","sex","position","absenceDayNum","factPayDayNum","workDutyDayNum","restDayNum","egressDayNum","elseDeduct",
                    "affairsLeave","sickLeave","restChanged","idCard", "technicalLevel","userCategory","workStatus","performanceSystem","noWork"};
            ExcelUtil.downloadExcel(response, statistics,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 外出列表
     * @param param
     * @param hrSignEgressArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/egressListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> egressListData(@RequestParam Map<String,Object> param, HrSignEgressArgs hrSignEgressArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.username,egressDate");
        Page<HrSignEgress> page = hrSignService.egressListData(hrSignEgressArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 异常列表
     * @param param
     * @param hrSignUnusualArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/unusualListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> unusualListData(@RequestParam Map<String,Object> param, HrSignUnusualArgs hrSignUnusualArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"su.userid,sul.checkin_time,sul.checkin_type");
        Page<HrSignUnusual> page = hrSignService.unusualListData(hrSignUnusualArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 补签
     * @param hrSignUnusual
     * @return
     */
    @RequestMapping("/updateUnusual")
    @ResponseBody
    public BaseResult updateUnusual(HrSignUnusual hrSignUnusual) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.updateUnusual(hrSignUnusual);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 到请假补录页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toLeaveEditPage/{id}")
    public String toLeaveEditPage(@PathVariable String id, Model model){
        HrSignLeave hrSignLeave = hrSignService.getHrSignLeaveById(id);
        model.addAttribute("entity", hrSignLeave);
        return ".sign.check.patchLeaveRecordEdit";
    }

    /**
     * 到新增请假页面
     * @return
     */
    @RequestMapping("/toleaveEditPage")
    public String toleaveEditPage() {
        return ".sign.check.patchLeaveRecordEdit";
    }

    /**
     * 新增请假
     * @return
     */
    @RequestMapping("/leaveInsert")
    @ResponseBody
    public BaseResult leaveInsert( HrSignLeave hrSignLeave) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.saveLeave(hrSignLeave);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 补录请假信息
     * @param hrSignLeave
     * @return
     */
    @RequestMapping("/leaveUpdate")
    @ResponseBody
    public BaseResult leaveUpdate(HrSignLeave hrSignLeave) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.updateLeave(hrSignLeave);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/toStatisticsUnusualListPage")
    public String toStatisticsUnusualListPage(HrSignUnusualStatistics hrSignUnusualStatistics, Model model) {
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("company",user.getCompanyName());
        model.addAttribute("companyId",user.getCurrCompanyId());
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.statisticsUnusualList";
    }

    /**
     * 异常报表
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/listUnususlStatisticsData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listUnususlStatisticsData(@Validated(GroupQuery.class) HrSignUnusualStatistics entity, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            List<HrSignUnusualStatistics> statistics = hrSignService.listUnususlStatisticsData(entity);
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出异常考勤报表
     * @param response
     * @param entity
     * @return
     */
    @RequestMapping(value = "/exportUnusualSignStatistics", method = RequestMethod.POST)
    public String exportSignStatistics(HttpServletResponse response, HrSignUnusualStatistics entity) {
        try {
            List<HrSignUnusualStatistics> statistics = hrSignService.listUnususlStatisticsData(entity);
            for (int i = 0;i < statistics.size();i++) {
                statistics.get(i).setIndex(i+1);
            }
            String fileName = "异常考勤报表";
            String[] columnNames = {"序号","合同单位","管理模块","管理单位","管理部门","人员编码","出生日期","年龄","入司时间","转正时间","用工状态",
                    "姓名","性别","岗位","身份证号","职级","分类","在职状态","绩效体系","迟到","早退","旷工","处罚金额","备注"};

            String[] keys = {"index","pactCompanyName","plateName","companyName","departmentName","userNum","birthdate","age","joinCompDate","InDueFormDate",
                    "useStatus","name","sex","position","idCard", "technicalLevel","userCategory","workStatus","performanceSystem"
                    ,"late","leaveEarly","noWork","age","age"};
            ExcelUtil.downloadExcel(response, statistics,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    @RequestMapping("/toUnusualPage")
    public String toUnusualPage(Model model){
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.unusualDetailList";
    }

    /**
     * 外出异常管理
     * @param param
     * @param hrSignEgressArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/listUnusualPatchData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listUnusualPatchData(@RequestParam Map<String,Object> param, HrSignEgressArgs hrSignEgressArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.username,s.startTime");
        Page<HrSignEgress> page = hrSignService.listUnusualPatchData(hrSignEgressArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 外出登记补录数据
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toEgressEditPage/{id}")
    public String toEgressEditPage(@PathVariable String id, Model model) {
        HrSignEgress hrSignEgress = hrSignService.getHrSignEgressById(id);
        model.addAttribute("entity", hrSignEgress);
        return ".sign.check.patchEgressRecordEdit";
    }

    /**
     * 更新外出 姓名和手机
     * @param hrSignEgress
     * @return
     */
    @RequestMapping("/egressUpdate")
    @ResponseBody
    public BaseResult egressUpdate(HrSignEgress hrSignEgress) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.updateEgressByid(hrSignEgress);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 更新请假流程状态
     * @param hrSignLeave
     * @return
     */
    @RequestMapping(value = "/changeLeaveFlag", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changeLeaveFlag(HrSignLeave hrSignLeave) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.changeLeaveFlag(hrSignLeave);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 更新外出流程状态
     * @param hrSignEgress
     * @return
     */
    @RequestMapping(value = "/changeEgressFlag", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changeEgressFlag(HrSignEgress hrSignEgress) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.changeEgressFlag(hrSignEgress);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出异常明细
     * @param response
     * @param hrSignUnusualArgs
     * @param args
     * @return
     */
    @RequestMapping("/exportUnusualSignDetail")
    public String exportUnusualSignDetail(HttpServletResponse response, HrSignUnusualArgs hrSignUnusualArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"su.userid,sul.checkin_time,sul.checkin_type");
            Page<HrSignUnusual> page = hrSignService.unusualListData(hrSignUnusualArgs,pageable);
            List<HrSignUnusual> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrSignUnusual unusual = list.get(i);
                unusual.setExportCheckinTime(DateTimeUtil.dateToString(unusual.getCheckin_time(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                unusual.setIndex(i+1);
                if ("1".equals(unusual.getException_type())) {
                    unusual.setExportCheckinTime("无");
                }
                if ("1".equals(unusual.getCheckin_type())) {
                    unusual.setCheckin_type("上班打卡");
                } else {
                    unusual.setCheckin_type("下班打卡");
                }
                if ("1".equals( unusual.getStatus())) {
                    unusual.setStatus("迟到");
                } else if ("2".equals( unusual.getStatus())) {
                    unusual.setStatus("迟到");
                } else if ("3".equals( unusual.getStatus())) {
                    unusual.setStatus("未签到");
                } else if ("4".equals( unusual.getStatus())) {
                    unusual.setStatus("未签退");
                } else if ("5".equals( unusual.getStatus())) {
                    unusual.setStatus("旷工半天");
                } else if ("6".equals( unusual.getStatus())) {
                    unusual.setStatus("旷工一天");
                } else if ("7".equals( unusual.getStatus())) {
                    unusual.setStatus("地点异常");
                } else if ("8".equals( unusual.getStatus())) {
                    unusual.setStatus("请假");
                } else if ("9".equals( unusual.getStatus())) {
                    unusual.setStatus("外出");
                } else if ("10".equals( unusual.getStatus())) {
                    unusual.setStatus("补登");
                }
            }
            String fileName = "异常考勤明细";
            String[] columnNames = {"序号","员工编码","姓名","单位","部门","职位","身份证号码","手机号码","日期","打卡时间","打卡状态",
                    "异常状态"};
            String[] keys = {"index","userNum","username","company","department","position","idCard","mobile","checkDate","exportCheckinTime",
                    "checkin_type","status"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 导出补登明细
     * @param response
     * @param hrSignPatchSonArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/exportPatchSignDetail",method = RequestMethod.POST)
    public String exportPatchSignDetail(HttpServletResponse response, HrSignPatchSonArgs hrSignPatchSonArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.`name`,s.patchTime,s.patchType");
            Page<HrSignPatchSon> page = hrSignService.listPatchDetailData(hrSignPatchSonArgs,pageable);
            List<HrSignPatchSon> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrSignPatchSon patchSon = list.get(i);
                patchSon.setPatchTimeStr(DateTimeUtil.dateToString(patchSon.getPatchTime(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                patchSon.setIndex(i+1);
                if ("1".equals(patchSon.getPatchType())) {
                    patchSon.setPatchTypeName("上班打卡");
                } else {
                    patchSon.setPatchTypeName("下班打卡");
                }
            }
            String fileName = "补登明细";
            String[] columnNames = {"序号","员工编码","姓名","单位","部门","职位","手机号码","补登日期","补登类型",
                    "补登说明"};
            String[] keys = {"index","userNum","username","company","department","position","telephone","patchDate","patchTypeName",
                    "notes"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 导出请假明细
     * @param response
     * @param hrSignLeaveArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/exportLeaveSignDetail",method = RequestMethod.POST)
    public String exportLeaveSignDetail(HttpServletResponse response, HrSignLeaveArgs hrSignLeaveArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"s.startLeave");
            Page<HrSignLeave> page = hrSignService.leaveDetailListData(hrSignLeaveArgs,pageable);
            List<HrSignLeave> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrSignLeave hrSignLeave = list.get(i);
                hrSignLeave.setStartLeaveStr(DateTimeUtil.dateToString(hrSignLeave.getStartLeave(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignLeave.setEndLeaveStr(DateTimeUtil.dateToString(hrSignLeave.getEndLeave(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignLeave.setIndex(i+1);
            }
            String fileName = "请假明细";
            String[] columnNames = {"序号","员工编码","姓名","单位","部门","身份证号码","请假类别","开始时间","结束时间","请假天数",
                    "请假事由","备注"};
            String[] keys = {"index","userNum","username","company","department","idCard","leaveType","startLeaveStr","endLeaveStr",
                    "leaveDayCount","reason","notes"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/exportEgressSignDetail",method = RequestMethod.POST)
    public String exportEgressSignDetail(HttpServletResponse response, HrSignEgressArgs hrSignEgressArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"egressDate");
            Page<HrSignEgress> page = hrSignService.egressListData(hrSignEgressArgs,pageable);
            List<HrSignEgress> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrSignEgress hrSignEgress = list.get(i);
                hrSignEgress.setStartTimeStr(DateTimeUtil.dateToString(hrSignEgress.getStartTime(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignEgress.setEndTimeStr(DateTimeUtil.dateToString(hrSignEgress.getEndTime(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignEgress.setIndex(i+1);
            }
            String fileName = "外出明细";
            String[] columnNames = {"序号","员工编码","姓名","单位","部门","身份证号码","手机","外出日期","外出时间","返回时间",
                    "外出地点","外出摘要"};
            String[] keys = {"index","userNum","username","company","department","idCard","telephone","egressDate","startTimeStr",
                    "endTimeStr","location_detail","notes"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/exportSignDetail",method = RequestMethod.POST)
    public String exportSignDetail(HttpServletResponse response, HrSignDetailArgs hrSignDetailArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"a.username,a.checkin_time,a.checkin_type");
            Page<HrSignDetail> page = hrSignService.signDetailListData(hrSignDetailArgs,pageable);
            List<HrSignDetail> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrSignDetail hrSignDetail = list.get(i);
                hrSignDetail.setCheckinTimeStr(DateTimeUtil.dateToString(hrSignDetail.getCheckin_time(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                hrSignDetail.setIndex(i+1);
                if ("1".equals(hrSignDetail.getCheckin_type())) {
                    hrSignDetail.setCheckin_type("上班打卡");
                } else if ("2".equals(hrSignDetail.getCheckin_type())) {
                    hrSignDetail.setCheckin_type("下班打卡");
                }
            }
            String fileName = "打卡明细";
            String[] columnNames = {"序号","员工编码","姓名","管理单位","管理部门","手机","打卡时间","打卡类型","星期",
                    "描述"};
            String[] keys = {"index","userNum","username","company","department","telephone","checkinTimeStr",
                    "checkin_type","week","notes"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 补登异常
     * @param param
     * @param hrSignPatchSonArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/patchUnusualListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> patchUnusualListData(@RequestParam Map<String,Object> param, HrSignPatchSonArgs hrSignPatchSonArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.name,s.patchDate");
        Page<HrSignPatchSon> page = hrSignService.patchUnusualListData(hrSignPatchSonArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 更新补登异常
     * @param hrSignPatchSon
     * @return
     */
    @RequestMapping(value = "/changePatchUnusualStatus", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changePatchUnusualStatus(HrSignPatchSon hrSignPatchSon) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.changePatchUnusualStatus(hrSignPatchSon);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
            logger.info(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 补登列表
     * @param param
     * @param hrSignPatchSonArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/listPatchDetailData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listPatchDetailData(@RequestParam Map<String,Object> param, HrSignPatchSonArgs hrSignPatchSonArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.`name`,s.patchTime,s.patchType");
        Page<HrSignPatchSon> page = hrSignService.listPatchDetailData(hrSignPatchSonArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 更新人员信息  不带参
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(Model model) {
        try {
            hrSignTaskService.getAndSaveSign();
            model.addAttribute("msg", "更新人员信息完成");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "更新人员信息失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 手动抓数据
     * @param hrSignUnusualArgs
     * @param model
     * @return
     */
    @RequestMapping("/getSignData")
    public String getSignData(HrSignUnusualArgs hrSignUnusualArgs, Model model) {
        try {
            logger.info("开始手动抓取考勤数据");
            hrSignService.getSignData(hrSignUnusualArgs,false);
            System.out.println(DateTimeUtil.dateToString(hrSignUnusualArgs.getStartTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            System.out.println(DateTimeUtil.dateToString(hrSignUnusualArgs.getEndTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            logger.info("完成手动抓取考勤数据");
            model.addAttribute("msg", "数据抓取完成完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.getSignData",e);
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 处理凌晨打卡的问题的问题  参数?startTime=&endTime=   可以是多天的时间段
     * @param model
     * @param hrSignDetailArgs
     * @return
     */
    @RequestMapping("/handleThirdData")
    public String handleThirdData(Model model, HrSignDetailArgs hrSignDetailArgs) {
        try {
            hrSignService.handleThirdData(hrSignDetailArgs);
            model.addAttribute("msg", "处理凌晨打卡问题成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "处理凌晨打卡问题失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 处理只有一条数据的问题 --参数?startTime=&endTime=   只能是一天
     * @param hrSignDetailArgs
     * @param model
     * @return
     */
    @RequestMapping("/handleNoData")
    public String handleNoData(HrSignDetailArgs hrSignDetailArgs, Model model) {
        try {
            Date startTime = hrSignDetailArgs.getStartTime();
            Date endTime = hrSignDetailArgs.getEndTime();
            List<Calendar> holidayList = hrSignService.getHolidayList(startTime, endTime);
            if (!DateTimeUtil.dateToString(startTime,DateTimeUtil.YYYY_MM_DD).equals(DateTimeUtil.dateToString(endTime,DateTimeUtil.YYYY_MM_DD))) {
                //开始时间和结束时间不在同一天
                throw new BusinessException("开始时间和结束时间应该为一天");
            }
            if (CollectionUtils.isEmpty(holidayList)) {
                System.out.println("工作日");
                hrSignService.handleNoData(hrSignDetailArgs,false);
            } else {
                System.out.println("周末");
            }
            model.addAttribute("msg", "处理一条数据问题完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleNoData", e);
            model.addAttribute("msg", "处理一条数据问题失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 处理某一天没有数据  -- 带参 只能是某一天
     * @param hrSignDetailArgs
     * @param model
     * @return
     */
    @RequestMapping("/handleNoDataOneDay")
    public String handleNoDataOneDay(HrSignDetailArgs hrSignDetailArgs, Model model) {
        try {
            Date startTime = hrSignDetailArgs.getStartTime();
            Date endTime = hrSignDetailArgs.getEndTime();
            List<Calendar> holidayList = hrSignService.getHolidayList(startTime, endTime);
            if (!DateTimeUtil.dateToString(startTime,DateTimeUtil.YYYY_MM_DD).equals(DateTimeUtil.dateToString(endTime,DateTimeUtil.YYYY_MM_DD))) {
                //开始时间和结束时间不在同一天
                throw new BusinessException("开始时间和结束时间应该为一天");
            }
            if (CollectionUtils.isEmpty(holidayList)) {
                System.out.println("工作日");
                hrSignService.handleNoDataOneDay(hrSignDetailArgs,false);
            } else {
                System.out.println("周末");
            }
            model.addAttribute("msg", "处理无数据问题完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleNoData", e);
            model.addAttribute("msg", "处理无数据问题失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 处理请假数据  参数?startTime=&endTime=   可以是多天时间段
     * @param model
     * @param hrSignLeaveSon
     * @return
     */
    @RequestMapping("/handleLeave")
    public String handleLeave(Model model, HrSignLeaveSon hrSignLeaveSon) {
        try {
            hrSignService.handleLeave(hrSignLeaveSon);
            model.addAttribute("msg", "处理请假时间完成");
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BusinessException){
                model.addAttribute("msg", "失败！请选择2017-8-31之后的日期");
            } else {
                model.addAttribute("msg", "处理请假时间失败");
            }
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 解决有些人外出了又打了卡或请假了又打了卡
     * @param hrSignDetailArgs
     * @param model
     * @return
     */
    @RequestMapping("/confilictSignData")
    public String confilictSignData(HrSignDetailArgs hrSignDetailArgs, Model model) {
        try {
            logger.info("开始处理打卡数据和请假外出冲突");
            hrSignService.confilictSignData(hrSignDetailArgs);
            model.addAttribute("msg", "处理打卡数据和请假外出冲突完成");
            logger.info("完成处理打卡数据和请假外出冲突");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.confilictSignData",e);
            model.addAttribute("msg", "处理打卡数据和请假外出冲突失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 手动处理异常       参数?startTime=&endTime=   可以是多天时间段
     * @param hrSignDetailArgs
     * @param model
     * @return
     */
    @RequestMapping("/handleException")
    public String handleException(HrSignDetailArgs hrSignDetailArgs, Model model) {
        try {
            logger.info("开始手动处理异常");
            hrSignService.handleException(hrSignDetailArgs);
            System.out.println(DateTimeUtil.dateToString(hrSignDetailArgs.getStartTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            System.out.println(DateTimeUtil.dateToString(hrSignDetailArgs.getEndTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            model.addAttribute("msg", "手动处理异常完成");
            logger.info("手动处理异常完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleException", e);
            model.addAttribute("msg", "手动处理异常失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 手动 根据迟到早退尺度处理异常状态   参数?startTime=&endTime=   可以是多天时间段
     * @param hrSignUnusualArgs
     * @param model
     * @return
     */
    @RequestMapping("/setNoWork")
    public String setNoWork(HrSignUnusualArgs hrSignUnusualArgs, Model model) {
        try {
            hrSignService.setNoWork(hrSignUnusualArgs);
            System.out.println(DateTimeUtil.dateToString(hrSignUnusualArgs.getStartTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            System.out.println(DateTimeUtil.dateToString(hrSignUnusualArgs.getEndTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            model.addAttribute("msg", "手动处理迟到早退状态完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.setNoWork",e);
            model.addAttribute("msg", "手动处理迟到早退状态失败");
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 处理OA同步无法打卡的情况
     * 具体情况具体
     * @return
     */
    @RequestMapping("/handleLostData")
    public String handleLostData(HrSignUnusualArgs hrSignUnusualArgs, Model model) {
        try {
            hrSignService.handleLostData(hrSignUnusualArgs);
            model.addAttribute("msg", "处理OA同步无法打卡问题完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleLostData", e);
            model.addAttribute("msg", "处理OA同步无法打卡问题失败");
        }
        return ".sign.check.signDetailList";
    }


    /**
     * 迁移外出数据
     * @return
     */
    @RequestMapping("/getEgressData")
    public String getEgressData() {
        try {
            hrSignService.getEgressData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 迁移请假数据
     * @return
     */
//    @RequestMapping("/getLeaveData")
    public String getLeaveData() {
        try {
            hrSignService.getLeaveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ".sign.check.signDetailList";
    }

    /**
     * 初始化人员电话 -- 把人员的打卡记录里的电话存到hr_sign_user表的oldMobile字段里去
     * @param model
     * @param hrSignUser
     * @return
     */
    @RequestMapping("/initUserTelephone")
    public String initUserTelephone(Model model, HrSignUser hrSignUser) {
        try {
            logger.info("开始初始化人员电话");
            hrSignService.initUserTelephone(hrSignUser);
            model.addAttribute("msg", "初始化人员电话成功");
            logger.info("完成初始化人员电话");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.initUserTelephone", e);
            model.addAttribute("msg", "初始化人员电话失败");
        }
        return ".sign.check.signDetailList";
    }

    @RequestMapping("/getToken")
    public String getToken(Model model) {
        String accessToken = OASignUtil.getAccessToken();
        model.addAttribute("msg",accessToken);
        return ".sign.check.signDetailList";
    }

    @RequestMapping("/toTodaySign")
    public String toTodaySign(Model model) {
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("company",user.getCompanyName());
        model.addAttribute("companyId",user.getCurrCompanyId());
        return ".sign.check.todaySign";
    }

    /**
     * 获取当日考勤数据
     * @return
     */
    @RequestMapping(value = "/getTodaySignData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getTodaySignData() {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            //先删除临时表
            hrSignService.deleteTodayTemp();
            hrSignService.getTodaySignData();
            //只有一条
            HrSignDetailArgs hrSignDetailArgs =  new HrSignDetailArgs();
            HrSignTodayTempArgs hrSignTodayTempArgs = new HrSignTodayTempArgs();

            Date startDate = DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(new Date(), DateTimeUtil.YYYY_MM_DD) + " 00:00:00", DateTimeUtil.YYYY_MM_DD_HH_MM_SS);
            Date endDate = DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(new Date(), DateTimeUtil.YYYY_MM_DD) + " 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS);

            hrSignDetailArgs.setStartTime(startDate);
            hrSignDetailArgs.setEndTime(endDate);
            hrSignTodayTempArgs.setStartTime(startDate);
            hrSignTodayTempArgs.setEndTime(endDate);
            hrSignService.handleTodayNoData(hrSignTodayTempArgs,true);
            hrSignService.handleNoDataOneDay(hrSignDetailArgs,true);
            hrSignService.handleException(hrSignTodayTempArgs);
            baseResult.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.handleLostData", e);
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 当日考勤数据列表
     * @param param
     * @param hrSignTodayTempArgs
     * @param args
     * @return
     */
    @RequestMapping("/getTodaySignDataList")
    @ResponseBody
    public Map<String,Object> getTodaySignDataList(@RequestParam Map<String,Object> param, HrSignTodayTempArgs hrSignTodayTempArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"userid,checkin_time,checkin_type");
        Page<HrSignTodayTemp> page = hrSignService.getTodaySignDataList(hrSignTodayTempArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping(value = "/exportTodaySign",method = RequestMethod.POST)
    public String exportTodaySign(HttpServletResponse response, HrSignTodayTempArgs hrSignTodayTempArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"userid,checkin_time,checkin_type");
            Page<HrSignTodayTemp> page = hrSignService.getTodaySignDataList(hrSignTodayTempArgs,pageable);
            List<HrSignTodayTemp> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrSignTodayTemp hrSignTodayTemp = list.get(i);
                if ("1".equals(hrSignTodayTemp.getException_type())) {
                    hrSignTodayTemp.setCheckinTimeStr("未打卡");
                } else {
                    hrSignTodayTemp.setCheckinTimeStr(DateTimeUtil.dateToString(hrSignTodayTemp.getCheckin_time(),DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
                }
                hrSignTodayTemp.setIndex(i+1);
                if ("1".equals(hrSignTodayTemp.getCheckin_type())) {
                    hrSignTodayTemp.setCheckin_type("上班打卡");
                } else if ("2".equals(hrSignTodayTemp.getCheckin_type())) {
                    hrSignTodayTemp.setCheckin_type("下班打卡");
                }
            }
            String fileName = "即时打卡明细";
            String[] columnNames = {"序号","员工编码","姓名","管理单位","管理部门","手机","打卡时间","打卡类型","星期",
                    "描述"};
            String[] keys = {"index","userNum","username","company","department","telephone","checkinTimeStr",
                    "checkin_type","week","notes"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 抓外出打卡数据
     * @param model
     * @param hrSignOutsideArgs
     * @return
     */
    @RequestMapping("/getOutsideSign")
    public String getOutsideSign(Model model,HrSignOutsideArgs hrSignOutsideArgs) {
        try {
            logger.info("开始手动抓取外出数据");
            hrSignService.getOutsideSign(hrSignOutsideArgs);
            model.addAttribute("msg", "抓取外出数据完成");
            logger.info("抓取外出数据完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SignController.getOutsideSign", e);
            model.addAttribute("msg", "抓取外出数据失败");
        }
        return ".sign.check.outsideSign";
    }

    @RequestMapping("/toOutsideSignPage")
    public String toOutsideSignPage(Model model) {
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.outsideSign";
    }

    /**
     * 外出打卡数据
     * @param param
     * @param hrSignOutsideArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/getOutsideSignDataList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getOutsideSignDataList(@RequestParam Map<String,Object> param, HrSignOutsideArgs hrSignOutsideArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"u.`name`,so.checkin_time");
        Page<HrSignOutside> page = hrSignService.getOutsideSignDataList(hrSignOutsideArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping(value = "/outsideDataToSignData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult outsideDataToSignData(Integer id) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrSignService.outSignDataToSignData(id);
            baseResult.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/toWorkSignPage")
    public String toWorkSignPage(Model model) {
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.workSign";
    }

<<<<<<< HEAD
    @RequestMapping("/toLeavePageOa")
    public String toLeavePageOa(Model model) {
        model.addAttribute("startTime",DateTimeUtil.convertStringToDate(DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date()),DateTimeUtil.YYYY_MM_DD)+" 00:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        model.addAttribute("endTime",DateTimeUtil.convertStringToDate( DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD, DateTimeUtil.dateToString(new Date()), DateTimeUtil.PLUSTYPE_DAY, -1)+" 23:59:59", DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        return ".sign.check.leavePageOa";
    }
=======
>>>>>>> 19c30c550e1ad9e34be72607eb74c5930a12d79f

}
