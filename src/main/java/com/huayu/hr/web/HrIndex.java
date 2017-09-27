package com.huayu.hr.web;

import com.alibaba.fastjson.JSON;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.common.tool.FileUtil;
import com.huayu.hr.domain.HrDictData;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.domain.HrEmployeeJob;
import com.huayu.hr.domain.HrJob;
import com.huayu.hr.service.*;
import com.huayu.user.domain.User;
import com.ly.service.base.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2016/12/2.
 */
@Controller
@RequestMapping("/hr")
public class HrIndex {

    private static Logger logger = LoggerFactory.getLogger(HrIndex.class);


    @Autowired
    private HrDictService hrDictService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrService hrService;

    @Autowired
    private HrStatisticsService hrStatisticsService;

    @Autowired
    private HrRemindService hrRemindService;


    /**
     * 切换选择的公司
     * @return
     */
    @RequestMapping("/switchOperCompany")
    @ResponseBody
    public BaseResult switchOperCompany(Integer companyId,String companyName){

        return hrService.switchOperCompany(companyId,companyName);
    }

    @RequestMapping("/empList")
    public String empList(Model model){
        User user = SpringSecurityUtil.getUser();
        Integer companyId = user.getCurrCompanyId();
        model.addAttribute("departmentList",orgService.getAllDepartments(companyId));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));

        return ".hr.emp.empList";
    }

    @RequestMapping("/getEmpListData")
    @ResponseBody
    public Map<String,Object> getEmpListData(@RequestParam Map<String,Object> param, HrEmployee employee, PageArgs pageArgs){
        BaseResult baseResult = hrService.getEmpListData(employee, pageArgs);
        baseResult.putAllRdataMap(param);
        return baseResult.getRdataMap();
    }

    /**
     * 获取在职人员
     * @param company
     * @param department
     * @param employee
     * @return
     */
    @RequestMapping("/getAllEmpListData")
    @ResponseBody
    public List<HrEmployee> getAllEmpListData(Integer company, Integer department,HrEmployee employee){
        if (null!=company&&null!=department){
            employee.setCompany(company);
            employee.setDepartment(department);
            employee.setOnGuard(1);
            List<HrEmployee> allEmpListData = hrService.getAllEmpListData(employee);
            return allEmpListData;
        }else{
            return null;
        }
    }

    /**
     * 获取离职人员
     * @param company
     * @param department
     * @return
     */
    @RequestMapping("/getAllOutDutyEmpListData")
    @ResponseBody
    public List<HrEmployee> getAllOutDutyEmpListData(Integer company, Integer department,HrEmployee employee){
        if (null!=company&&null!=department){
            return hrService.getAllOutDutyEmpListData(employee);
        }else{
            return null;
        }
    }

    /**
     * 获取非正式 在职人员
     * @param company
     * @param department
     * @param employee
     * @return
     */
    @RequestMapping("/getNotFormalEmpData")
    @ResponseBody
    public List<HrEmployee> getNotFormalEmpData(Integer company, Integer department,HrEmployee employee){
        if (null!=company&&null!=department){
            return hrService.getNotFormalEmpData(employee);
        }else{
            return null;
        }
    }

    /**
     * 编辑、新增
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,Integer id, HttpServletRequest request){
        getDataEmp(model, id);
        model.addAttribute("enableEdit",true);
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        model.addAttribute("photoServerUrl",commConfig.get("hr_emp_photo_url"));
        model.addAttribute("monthFirstDay",DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(new Date())));
        model.addAttribute("education",hrDictService.getDictDataByDictValue("education"));
        return ".hr.emp.edit";
    }

    @RequestMapping("/show/{id}")
    public String show(Model model,@PathVariable("id") Integer id, HttpServletRequest request){
        getDataEmp(model, id);
        model.addAttribute("enableEdit",false);
        model.addAttribute("now",new Date());
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        model.addAttribute("photoServerUrl",commConfig.get("hr_emp_photo_url"));
        model.addAttribute("education",hrDictService.getDictDataByDictValue("education"));
        return ".hr.emp.edit";
    }

    private void getDataEmp(Model model,Integer id){
        Map<String, List<HrDictData>> hrBaseInfo = hrDictService.getAllDictDataByDictValue("hrBaseInfo");
        model.addAttribute("dictMap",hrBaseInfo);
        if (null!=id){
            BaseResult result = hrService.getEmployeeInfo(id);
            if (result.isSuccess()){
                model.addAttribute("employeeInfo",result.getRdataMapValue("employeeInfo"));
                HrEmployeeJob jobInfo = (HrEmployeeJob) result.getRdataMapValue("jobInfo");
                model.addAttribute("jobInfo",jobInfo);
                List<HrJob> jobs = orgService.getAllJobByDepartmentId(jobInfo.getDepartment());
                model.addAttribute("jobs",jobs);
                if (null!=jobInfo.getCreatetime()){
                    if (DateTimeUtil.getTimeHourDifference(new Date(),jobInfo.getCreatetime())<24){
                        model.addAttribute("enableEditJob",true);
                    }
                }else{
                    model.addAttribute("enableEditJob",false);
                }
            }
        }else{
            model.addAttribute("enableEditJob",true);
        }
    }


    /**
     * 检查身份证号重复
     * @param idCardNo
     * @return
     */
    @RequestMapping("/checkIdCardRepeat")
    @ResponseBody
    public BaseResult checkIdCardRepeat(String idCardNo){

        return hrService.checkIdCardRepeat(idCardNo);
    }


    /**
     * 检查员工编号重复
     * @param empNo
     * @return
     */
    @RequestMapping("/checkEmpNoRepeat")
    @ResponseBody
    public BaseResult checkEmpNoRepeat(String empNo){

        return hrService.checkEmpNoRepeat(empNo);
    }

//    /**
//     * 检查岗位编制
//     * @param jobId
//     * @return
//     */
//    @RequestMapping("/checkJobPlait")
//    @ResponseBody
//    public BaseResult checkJobPlait(Integer jobId){
//
//        return hrService.checkJobPlait(empNo);
//    }



    /**
     * 保存人员信息
     * @param employee
     * @param jobInfo
     * @param empId
     * @param empJobId
     * @return
     */
    @RequestMapping("/saveEmpInfo")
    @ResponseBody
    public BaseResult saveEmpInfo(HrEmployee employee, HrEmployeeJob jobInfo,Integer empId, Integer empJobId){
        BaseResult result;
        try {
            if (null!=empId&&null!=empJobId){
                employee.setId(empId);
                jobInfo.setId(empJobId);
            }
            result = hrService.saveEmployeeInfoAndJobInfo(employee,jobInfo);
        } catch (BusinessException e) {
            result = BaseResult.initBaseResult();
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 家庭信息
     * @param enableEdit
     * @param empId
     * @param model
     * @return
     */
    @RequestMapping("/homeInfo/{enableEdit}/{empId}")
    public String homeInfo(@PathVariable("enableEdit") boolean enableEdit, @PathVariable("empId") Integer empId, Model model){
        model.addAttribute("empId",empId);
        model.addAttribute("homeInfoList", hrService.getHomeInfoAll(empId));
        model.addAttribute("enableEdit",enableEdit);
        return "/hr/emp/detail/homeInfo";
    }

    /**
     * 保存家庭信息
     * @param homeRelationArray
     * @return
     */
    @RequestMapping("/saveHomeInfo")
    @ResponseBody
    public BaseResult saveHomeInfo(String homeRelationArray){
        BaseResult result ;
        try {
            result = hrService.saveHomeInfo(homeRelationArray);
        } catch (BusinessException e) {
            result = BaseResult.initBaseResult();
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delHomeInfo")
    @ResponseBody
    public BaseResult delHomeInfo(Integer[] detailArray, Integer empId){
        return hrService.delHomeInfo(detailArray,empId);
    }


    /**
     * 学历信息
     * @param empId
     * @param model
     * @return
     */
    @RequestMapping("/educationInfo/{enableEdit}/{empId}")
    public String educationInfo(@PathVariable("enableEdit") boolean enableEdit, @PathVariable("empId") Integer empId, Model model){
        model.addAttribute("empId",empId);
        model.addAttribute("detailList", hrService.getEducationInfoAll(empId));
        model.addAttribute("enableEdit",enableEdit);
        model.addAttribute("education",hrDictService.getDictDataByDictValue("education"));
        model.addAttribute("profession",hrDictService.getDictDataByDictValue("profession"));
        model.addAttribute("degree",hrDictService.getDictDataByDictValue("degree"));
        return "/hr/emp/detail/educationInfo";
    }

    @RequestMapping("/saveEducationInfo")
    @ResponseBody
    public BaseResult saveEducationInfo(String detailArray){
        BaseResult result ;
        try {
            result = hrService.saveEducationInfo(detailArray);
        } catch (BusinessException e) {
            result = BaseResult.initBaseResult();
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delEducationInfo")
    @ResponseBody
    public BaseResult delEducationInfo(Integer[] detailArray, Integer empId){
        return hrService.delEducationInfo(detailArray,empId);
    }

    /**
     * 上传照片
     * @param imgFile
     * @param request
     * @return
     */
    @RequestMapping("/uploadPhoto")
    @ResponseBody
    public BaseResult uploadPhoto(@RequestParam MultipartFile imgFile, HttpServletRequest request) {
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        BaseResult baseResult = BaseResult.initBaseResult(true);
        String fileName = FileUtil.saveFile(imgFile, commConfig.get("hr_emp_photo_path"), DateUtil.getYmd());
        if (StringUtils.isNotBlank(fileName)) {
            baseResult.setSuccess();
            baseResult.setRdata(fileName);
        }
        return baseResult;
    }

    /**
     * 履历记录
     * @param empId
     * @param model
     * @return
     */
    @RequestMapping("/resumeInfo/{enableEdit}/{empId}")
    public String resumeInfo(@PathVariable("enableEdit") boolean enableEdit, @PathVariable("empId") Integer empId, Model model){
        model.addAttribute("empId",empId);
        model.addAttribute("detailList", hrService.getResumeInfoAll(empId));
        model.addAttribute("enableEdit",enableEdit);
        return "/hr/emp/detail/resumeInfo";
    }

    @RequestMapping("/saveResumeInfo")
    @ResponseBody
    public BaseResult saveResumeInfo(String detailArray){
        BaseResult result ;
        try {
            result = hrService.saveResumeInfo(detailArray);
        } catch (BusinessException e) {
            result = BaseResult.initBaseResult();
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delResumeInfo")
    @ResponseBody
    public BaseResult delResumeInfo(Integer[] detailArray, Integer empId){
        return hrService.delResumeInfo(detailArray,empId);
    }


    /**
     * 导出个人详细信息
     * @param ids
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/downloadEmpInfo")
    public String downloadEmpInfo(String ids, String type, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(ids)){
            return null;
        }
        List<Integer> integers = JSON.parseArray(ids, Integer.class);
        if ("list".equals(type)){
            List<HrEmployee> list = hrService.getEmpListData(null,null,integers);
            Workbook workbook=hrService.buildEmpInfo2Download(list);
            ExcelUtil.writeExcel(response,workbook,"人员列表");
        }else{
            hrService.buildEmpInfo2Download(response, integers,type);
        }
        return null;
    }

    /**
     * 打印页面
     * @param empId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/print/{empId}")
    public String printEmpInfo(@PathVariable("empId") Integer empId, Model model,HttpServletRequest request){
        model.addAttribute("empId",empId);
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        model.addAttribute("photoServerUrl",commConfig.get("hr_emp_photo_url"));
        hrService.buildEmpInfo2Download(model, empId);
        return "/hr/emp/detail/exportEmpInfo";
    }

    /**
     * 人员速查
     * @param model
     * @return
     */
    @RequestMapping("/emp/quickShow")
    public String quickShow(Model model) throws SQLDataException {
        return ".hr.emp.quickShow";
    }

    /**
     * 根据员工编号 获取 部分员工信息
     * @param emp
     * @return
     */
    @RequestMapping("/findEmpByEmpNo")
    @ResponseBody
    public BaseResult findEmpByEmpNo(HrEmployee emp){
        if (StringUtils.isBlank(emp.getEmpNo())){
            return BaseResult.initBaseResult("参数错误");
        }
        return hrService.findEmpByEmpNo(emp);
    }

    @RequestMapping("/exportEmpInfo")
    public String exportEmpInfo( Integer company, Integer department, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<HrEmployee> list = hrService.getEmpListData(company,department,null);
        Workbook workbook=hrService.buildEmpInfo2Download(list);
        ExcelUtil.writeExcel(response,workbook,"公司人员列表");
        return null;
    }

    /**
     * 导出生日提醒
     * @param type
     * @return
     */
    @RequestMapping(value = "/exportRemindData/{type}",method = RequestMethod.GET)
    public String exportRemindData(HttpServletResponse response,@PathVariable Integer type) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName;
        if (type==1){
            fileName = "生日提醒.xls";
        }else if (type==2){
            fileName = "转正提醒.xls";
        }else if (type==3){
            fileName = "合同到期提醒.xls";
        }else{
            return null;
        }
        Workbook workbook =  hrRemindService.exportRemindData(type);
        ExcelUtil.writeExcel(response,workbook,fileName);
        return null;
    }

    /**
     * 手动统计 hr 提醒
     * @param type
     * @return
     */
    @RequestMapping(value = "/manualRemindData/{type}",method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable Integer type){
        if (type==1){
            hrRemindService.remindBirthDate();
        }else if (type==2){
            hrRemindService.remindTurnFormalDate();
        }else if (type==3){
            hrRemindService.remindContractEndDate();
        }
        return "success";
    }

    /**
     * 手动统计所有盘点
     * @return
     */
    @RequestMapping(value = "/manualStatisticsAllInv/{expression}",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult manualStatisticsAllInv(@PathVariable String expression){
        BaseResult result = BaseResult.initBaseResult();
        try {
            if (expression.length()==3){
                char[] chars = expression.toCharArray();
                hrStatisticsService.manualStatisticsAllInv(chars[0]=='1'?true:false,chars[1]=='1'?true:false,chars[2]=='1'?true:false,new Date());
                result.setSuccess();
            }
        } catch (Exception e) {
            logger.error("调用异常",e);
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 统计所有异动
     * @return
     */
    @RequestMapping(value = "/manualStatisticsAllChg",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult manualStatisticsAllChg(){

        return hrStatisticsService.manualStatisticsAllChg(null,null,new Date());
    }

    @RequestMapping(value = "/manualStatisticsAllChg/{yyyy}/{mm}",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult manualStatisticsAllChg(@PathVariable String yyyy,@PathVariable String mm){

        return hrStatisticsService.manualStatisticsAllChg(yyyy,mm,new Date());
    }
}
