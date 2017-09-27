package com.huayu.hr.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.hr.domain.HrDictData;
import com.huayu.hr.domain.HrEmployeeJob;
import com.huayu.hr.domain.HrEmployeePsnChg;
import com.huayu.hr.service.HrChgService;
import com.huayu.hr.service.HrDictService;
import com.huayu.hr.service.OrgService;
import com.huayu.hr.web.args.HrChgArgs;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员变动
 * Created by DengPeng on 2017/2/22.
 */
@Controller
@RequestMapping("/hr")
public class HrChgController {

    @Autowired
    private HrDictService hrDictService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrChgService hrChgService;


    @RequestMapping("/chg/list")
    public String empChgList(Model model) {
        User user = SpringSecurityUtil.getUser();
        Integer companyId = user.getCurrCompanyId();
        model.addAttribute("departmentList",orgService.getAllBaseDepartments(companyId));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.empChg.empChgList";
    }

    /**
     * 人员调动页面
     * @param model
     * @return
     */
    @RequestMapping("/chg/edit")
    public String empChgEdit(Model model) {
        List<HrDictData> dutyLevel = hrDictService.getDictDataByDictValue("dutyLevel");
        List<HrDictData> jobSequence = hrDictService.getDictDataByDictValue("jobSequence");
        model.addAttribute("dutyLevel",dutyLevel);
        model.addAttribute("jobSequence",jobSequence);
        model.addAttribute("monthFirstDay", DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date())));
        return ".hr.empChg.empChgEdit";
    }


    /**
     * 人员调动
     * @param args
     * @return
     */
    @RequestMapping("/chg/postChg")
    @ResponseBody
    public BaseResult postChg(HrChgArgs args) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrChgService.postChgBatch(args);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 流动情况
     * @param empId
     * @param args
     * @param model
     * @return
     */
    @RequestMapping("/empChg/{empId}")
    public String educationInfo(@PathVariable("empId") Integer empId, HrChgArgs args, Model model){
        model.addAttribute("empId",empId);
        args.setEmpId(empId);
        model.addAttribute("detailList", hrChgService.getPsnChgList(args));
        return "/hr/emp/detail/workChgInfo";
    }

    @RequestMapping("/getEmpChgData")
    @ResponseBody
    public Map<String,Object> getEmpChgData(@RequestParam Map<String,Object> param, HrChgArgs args, PageArgs pageArgs){

        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        Page<HrEmployeePsnChg> page = hrChgService.getEmpChgData(args,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }



    /**
     * 任职信息
     * @param empId
     * @param model
     * @return
     */
    @RequestMapping("/indutyInfo/{empId}")
    public String indutyInfo(@PathVariable("empId") Integer empId, Model model){
        model.addAttribute("empId",empId);
        List<HrDictData> empGroup = hrDictService.getDictDataByDictValue("empGroup");
        List<HrDictData> jobSequence = hrDictService.getDictDataByDictValue("jobSequence");
        List<HrDictData> dutyLevel = hrDictService.getDictDataByDictValue("dutyLevel");
        List<HrDictData> chgStatus = hrDictService.getDictDataByDictValue("chgStatus");
        List<HrDictData> deployReason = hrDictService.getDictDataByDictValue("deployReason");
        model.addAttribute("empGroupData",empGroup);
        model.addAttribute("dutyLevelData",dutyLevel);
        model.addAttribute("jobSequenceData",jobSequence);
        model.addAttribute("chgStatusData",chgStatus);
        model.addAttribute("deployReasonData",deployReason);
        model.addAttribute("detailList",hrChgService.getPsnIndutyList(empId));
        return "/hr/emp/detail/indutyInfo";
    }


    /**
     * 离职页面
     * @param model
     * @return
     */
    @RequestMapping("/outDuty/edit")
    public String outDutyEdit(Model model) {
        List<HrDictData> outDutyType = hrDictService.getDictDataByDictValue("outDutyType");
        List<HrDictData> outDutyReason = hrDictService.getDictDataByDictValue("outDutyReason");
        model.addAttribute("outDutyType",outDutyType);
        model.addAttribute("outDutyReason",outDutyReason);
        model.addAttribute("monthFirstDay", DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date())));
        return ".hr.empChg.empOutDutyEdit";
    }


    /**
     * 人员离职
     * @param args
     * @return
     */
    @RequestMapping("/chg/postOutDuty")
    @ResponseBody
    public BaseResult postOutDuty(HrChgArgs args) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrChgService.postOutDutyBatch(args);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/chg/testOutDuty")
    @ResponseBody
    public BaseResult buildEmpOutDuty() {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            HrChgArgs hrChgArgs = hrChgService.buildEmpOutDuty();
            hrChgService.postOutDutyBatch(hrChgArgs);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }



    /**
     * 离职信息
     * @param empId
     * @param model
     * @return
     */
    @RequestMapping("/outdutyInfo/{empId}")
    public String outdutyInfo(@PathVariable("empId") Integer empId, Model model){
        model.addAttribute("empId",empId);
        List<HrDictData> empGroup = hrDictService.getDictDataByDictValue("empGroup");
        List<HrDictData> outDutyReason = hrDictService.getDictDataByDictValue("outDutyReason");
        List<HrDictData> dutyLevel = hrDictService.getDictDataByDictValue("dutyLevel");
        List<HrDictData> chgStatus = hrDictService.getDictDataByDictValue("chgStatus");
        List<HrDictData> deploymentReason = hrDictService.getDictDataByDictValue("deploymentReason");
        List<HrDictData> outDutyType = hrDictService.getDictDataByDictValue("outDutyType");
        model.addAttribute("outDutyType",outDutyType);
        model.addAttribute("empGroupData",empGroup);
        model.addAttribute("outDutyReason",outDutyReason);
        model.addAttribute("dutyLevelData",dutyLevel);
        model.addAttribute("chgStatus",chgStatus);
        model.addAttribute("deploymentReasonData",deploymentReason);
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("detailList",hrChgService.getPsnOutdutyList(empId,user.getCurrCompanyId()));
        return "/hr/emp/detail/outdutyInfo";
    }


    /**
     * 人员返聘
     * @param model
     * @return
     */
    @RequestMapping("/chg/hireBack")
    public String  empHireBack(Model model){
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("recruitmentSource",hrDictService.getDictDataByDictValue("recruitmentSource"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        model.addAttribute("monthFirstDay", DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date())));
        return ".hr.empChg.empHireBack";
    }

    /**
     * 提交返聘请求
     * @param employeeJob
     * @return
     */
    @RequestMapping(value = "/chg/postHireBack",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postHireBack(HrEmployeeJob employeeJob) {
        BaseResult baseResult;
        try {
            return hrChgService.postHireBack(employeeJob);
        } catch (BusinessException e) {
            baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
            return baseResult;
        }
    }

    @RequestMapping("/chg/turnFormal")
    public String  turnFormal(Model model){
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("recruitmentSource",hrDictService.getDictDataByDictValue("recruitmentSource"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.empChg.turnFormal";
    }

    /**
     * 提交转正数据
     * @param args
     * @return
     */
    @RequestMapping(value = "/chg/postTurnFormal",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postTurnFormal(HrChgArgs args) {
        BaseResult baseResult;
        try {
            return hrChgService.postTurnFormal(args);
        } catch (BusinessException e) {
            baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
            return baseResult;
        }
    }



}
