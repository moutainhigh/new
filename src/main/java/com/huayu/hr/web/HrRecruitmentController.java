package com.huayu.hr.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.ExcelOperateUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.domain.*;
import com.huayu.hr.service.HrContentService;
import com.huayu.hr.service.HrDictService;
import com.huayu.hr.service.HrRecruitmentService;
import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.user.domain.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/5.
 */
@Controller
@RequestMapping("/hr")
public class HrRecruitmentController {

    private static Logger logger = LoggerFactory.getLogger(HrRecruitmentController.class);

    @Autowired
    private HrRecruitmentService hrRecruitmentService;

    @Autowired
    private HrContentService contentService;

    @Autowired
    private HrDictService dictService;

    @RequestMapping("/recruitmentSchedule/edit")
    public String toEditPage() {
        return ".hr.hrRecruitmentSchedule.scheduleManageEdit";
    }

    /**
     * 新增招聘岗位进程
     * @param hrRecruitmentSchedule
     * @return
     */
    @RequestMapping("/recruitmentSchedule/insert")
    @ResponseBody
    public BaseResult insert(HrRecruitmentSchedule hrRecruitmentSchedule) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.insert(hrRecruitmentSchedule);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/recruitmentSchedule/index")
    public String toIndex() {
        return ".hr.hrRecruitmentSchedule.scheduleManageList";
    }

    /**
     * 获取招聘进程列表
     * @param param
     * @param hrRecruitmentSchedule
     * @param args
     * @return
     */
    @RequestMapping(value = "/recruitmentSchedule/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> ListData(@RequestParam Map<String,Object> param, HrRecruitmentSchedule hrRecruitmentSchedule, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"e.createDate");
        Page<HrRecruitmentSchedule> page = hrRecruitmentService.scheduleListData(hrRecruitmentSchedule,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/recruitmentSchedule/getAll")
    @ResponseBody
    public List<HrRecruitmentSchedule> getAll(String field) {
        return hrRecruitmentService.getAllHrRecruitmentSchedule(field);
    }

    /**
     * 点击编辑进入编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentSchedule/edit/{id}")
    public String toEditPage(@PathVariable Integer id, Model model) {
        HrRecruitmentSchedule hrRecruitmentSchedule = hrRecruitmentService.getHrRecruitmentScheduleById(id);
        model.addAttribute("entity", hrRecruitmentSchedule);
        return ".hr.hrRecruitmentSchedule.scheduleManageEdit";
    }

    /**
     * 修改招聘信息
     * @param hrRecruitmentSchedule
     * @return
     */
    @RequestMapping(value = "/recruitmentSchedule/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateMaterial(HrRecruitmentSchedule hrRecruitmentSchedule){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            hrRecruitmentService.updateHrRecruitmentSchedule(hrRecruitmentSchedule);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 单个删除
     * @param hrRecruitmentSchedule
     * @return
     */
    @RequestMapping(value = "/recruitmentSchedule/deleteOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteOneHrRecruitmentSchedule(@Validated(GroupDelete.class) HrRecruitmentSchedule hrRecruitmentSchedule, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            hrRecruitmentService.deleteOneHrRecruitmentSchedule(hrRecruitmentSchedule);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出招聘岗位进程
     * @param response
     * @return
     */
    @RequestMapping("/recruitmentSchedule/exportExcel/{ids}")
    public String exportExcel(HttpServletResponse response, @PathVariable String ids) {
        List<HrRecruitmentSchedule> list = hrRecruitmentService.getHrRecruitmentScheduleByids(ids);
        for (int i = 0;i < list.size();i++) {
            list.get(i).setIndex(i+1);
        }
        String fileName = "招聘岗位进程表";
        String[] columnNames = {"序号","板块","单位","部门","职位名称","姓名","联系方式","人数","优先级","预计到岗日期","招聘需求提出",
                "简历筛选(提交份数)","简历提交时间","简历确认","简历确认时间","电话通知","初试时间","通过人员","复试","复试时间",
                "通过人员","终试","终试时间","拟录用人员","拟到岗时间","实际到岗时间","负责人","进展"};

        String[] keys = {"index","plat","company","department","position","name","telephone","numberOfHiring","level","planComePositionDate","hireDate",
                "numberOfResumes","receiveResumeDate","resumePassedNumber","resumePassedDate","personNumByCall",
                "firstAuditionDate","firstPassedPerson","secondAuditonNumber","secondAuditionDate","secondPassedPerson",
                "finalAuditionNumber","finalAuditionDate","personNameToBeHired","planComePositionDate", "comePositionDate","responsiblePerson","description"};
        try {
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进度统计首页
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentSchedule/statisticsIndex")
    public String statisticsIndex(Model model) {
        return ".hr.hrRecruitmentSchedule.scheduleStatisticsData";
    }

    /**
     * 进度统计数据
     * @param param
     * @param hrRecruitmentSchedule
     * @return
     */
    @RequestMapping(value = "/recruitmentSchedule/statisticsData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> scheduleStatisticsData(@RequestParam Map<String,Object> param, HrRecruitmentSchedule hrRecruitmentSchedule){
        List<HrRecruitmentSchedule> listData = hrRecruitmentService.scheduleStatisticsData(hrRecruitmentSchedule);
        param.put("data",listData);
        param.put("recordsTotal",listData.size());
        param.put("recordsFiltered",listData.size());
        return param;
    }


    /**
     * 导出计划数据
     * @param hrRecruitmentSchedule
     * @return
     */
    @RequestMapping(value = "/recruitmentSchedule/downloadStatisticsData",method = RequestMethod.POST)
    public String downloadStatisticsData(HrRecruitmentSchedule hrRecruitmentSchedule,HttpServletResponse response) {
        try {
            String fileName = "招聘进程统计";
            String[] columnNames = {"序号","板块","人数","简历筛选（提交份数）","简历确认","复试","终试"};
            String[] keys = {"_index","plat","numberOfHiring","numberOfResumes","resumePassedNumber","secondAuditonNumber","personNameToBeHired"};
            List<HrRecruitmentSchedule> plans = hrRecruitmentService.scheduleStatisticsData(hrRecruitmentSchedule);
            ExcelUtil.downloadExcel(response, plans,fileName,columnNames,keys);
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    /**
     * 招聘信息列表
     * @param model
     * @return
     */
    @RequestMapping("/hrContent/list")
    public String contentList(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("user",user);
        return ".hr.content.list";
    }

    @RequestMapping("/hrContent/getContentListData")
    @ResponseBody
    public Map<String,Object> getContentListData(@RequestParam Map<String,Object> param, HrRecruitmentContent content, PageArgs pageArgs){
        Pageable pageable;
        Object orderKey = param.get("order[0][column]");
        Object orderData = param.get("order[0][dir]");
        if (null!=orderKey&&null!=orderData){
            if ("4".equals(orderKey.toString())){
                if ("asc".equals(orderData.toString())){
                    pageable =  new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.ASC,"sort");
                }else{
                    pageable =  new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.DESC,"sort");
                }
            }else if ("6".equals(orderKey.toString())){
                if ("asc".equals(orderData.toString())){
                    pageable =  new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.ASC,"createtime");
                }else{
                    pageable =  new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.DESC,"createtime");
                }
            }else {
                pageable  = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
            }
        }else {
            pageable  = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(),Sort.Direction.ASC,"sort");
        }
        if (null==content.getPlateId()){
            User user = SpringSecurityUtil.getUser();
            content.setPlateId(user.getPlateId());
        }
        if(null==content.getArea()){
            content.setArea(1);
        }
        Page<HrRecruitmentContent> page = contentService.getContentList(content,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/hrContent/edit")
    public String contentEdit(Integer id, Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("user",user);
        if (null!=id){
            HrRecruitmentContent content = contentService.getContentOne(id);
            model.addAttribute("entity",content);
        }
        return ".hr.content.edit";
    }

    @RequestMapping("/hrContent/saveContent")
    @ResponseBody
    public BaseResult saveContent(HrRecruitmentContent content){

        return contentService.saveContent(content);
    }

    @RequestMapping("/hrContent/delContent")
    @ResponseBody
    public BaseResult delContent(@RequestParam Integer id){

        return contentService.delContent(id);
    }


    /**
     * 求职列表
     * @return
     */
    @RequestMapping("/hrContent/jobRequestList")
    public String jobRequestList(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("user",user);
        return ".hr.content.jobRequest";
    }

    @RequestMapping("/hrContent/getJobRequestListData")
    @ResponseBody
    public Map<String,Object> getJobRequestListData(@RequestParam Map<String,Object> param, HrJobRequest hrJobRequest, PageArgs pageArgs){
        if (null==hrJobRequest.getPlateId()){
            User user = SpringSecurityUtil.getUser();
            hrJobRequest.setPlateId(user.getPlateId());
        }
        if (null==hrJobRequest.getArea()){
            hrJobRequest.setArea(1);
        }
        if (null==hrJobRequest.getStatus()){
            hrJobRequest.setStatus(0);
        }
        Page<HrJobRequest> page =  contentService.getJobRequestListData(hrJobRequest,pageArgs);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/hrContent/jobRequestOne")
    public String jobRequestOne(Model model, HrJobRequest hrJobRequest){
        if (null==hrJobRequest.getId()){
            return "/admin/index/404";
        }
        model.addAttribute("entity",contentService.getJobRequestOne(hrJobRequest));
        return ".hr.content.jobRequestDetail";
    }

    @RequestMapping("/hrContent/delJobRequestOne")
    @ResponseBody
    public BaseResult delJobRequestOne(HrJobRequest hrJobRequest){
        if (null==hrJobRequest.getId()){
            return  new BaseResult("ID不能为空");
        }
        return contentService.delJobRequestOne(hrJobRequest);
    }

    /**
     * 下载附件
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/hrContent/downloadFile")
    public String downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
        try {
            Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
            String path = commConfig.get("hr_job_request_path");
            InputStream inputStream = new FileInputStream(new File(path+ File.separator + fileName));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更改求职信息 状态
     * @param hrJobRequest
     * @return
     */
    @RequestMapping("/hrContent/updateJobRequest")
    @ResponseBody
    public BaseResult updateJobRequest(HrJobRequest hrJobRequest){
        return contentService.updateJobRequest(hrJobRequest);
    }


    /**
     * 招聘计划
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentPlan/index")
    public String recruitmentPlan(Model model){
        return ".hr.recruitmentPlan.list";
    }


    /**
     * 招聘计划数据
     * @param param
     * @param plan
     * @param args
     * @return
     */
    @RequestMapping(value = "/recruitmentPlan/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listData(@RequestParam Map<String,Object> param, HrRecruitmentPlan plan, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.ASC,"c.code");
        Page<HrRecruitmentPlan> page = hrRecruitmentService.planListData(plan,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    /**
     * 新增页面
     */
    @RequestMapping("/recruitmentPlan/edit")
    public String planEdit(Model model){
        model.addAttribute("dutyLevel",dictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.recruitmentPlan.edit";
    }

    /**
     * 编辑计划
     * @param id
     * @param plan
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentPlan/edit/{id}")
    public String planEdit(@PathVariable Integer id, HrRecruitmentPlan plan ,Model model){
        plan.setId(id);
        model.addAttribute("entity", hrRecruitmentService.getPlanOne(plan));
        model.addAttribute("dutyLevel",dictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.recruitmentPlan.edit";
    }

    /**
     * 添加计划
     * @param plan
     * @return
     */
    @RequestMapping(value = "/recruitmentPlan/insertPlan",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertPlan(HrRecruitmentPlan plan) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.insertPlan(plan);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 修改计划
     * @param plan
     * @return
     */
    @RequestMapping(value = "/recruitmentPlan/updatePlan",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updatePlan(HrRecruitmentPlan plan) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.updatePlan(plan);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/recruitmentPlan/delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deletePlan(HrRecruitmentPlan plan) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.deletePlan(plan);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出计划数据
     * @param plan
     * @return
     */
    @RequestMapping(value = "/recruitmentPlan/downloadData",method = RequestMethod.POST)
    public String downloadPlanData(HrRecruitmentPlan plan,HttpServletResponse response) {
        try {
            String fileName = "招聘计划";
            String[] columnNames = {"序号","板块","单位","部门","职位名称","职级","空缺人数","申请招聘日期","拟完成日期","填补人数","用人部门需求",
                    "目前进展情况","填补人员姓名","尚空缺人数","拟入职日期","已入职日期","确定年度薪酬（元）","直接汇报上级岗位名称"};

            String[] keys = {"_index","plat","companyName","departmentName","jobName","dutyLevelName","vacancyCount","askDate","overDate","fileCount","requirement",
                    "progress","fileName","vacancyCount2","planJoinDate","overJoinDate","payment","reportJobName"};
            List<HrRecruitmentPlan> plans = hrRecruitmentService.downloadPlanData(plan);
            ExcelUtil.downloadExcel(response, plans,fileName,columnNames,keys);
        } catch (Exception e) {
                return null;
        }
        return null;
    }

    /**
     * 计划统计页面
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentPlan/statisticsIndex")
    public String planStatisticsIndex(Model model) {
        return ".hr.recruitmentPlan.planStatisticsData";
    }

    /**
     * 计划统计数据
     * @param param
     * @param plan
     * @return
     */
    @RequestMapping(value = "/recruitmentPlan/statisticsData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> planStatisticsData(@RequestParam Map<String,Object> param, HrRecruitmentPlan plan){
        List<HrRecruitmentPlan> listData = hrRecruitmentService.planStatisticsData(plan);
        param.put("data",listData);
        param.put("recordsTotal",listData.size());
        param.put("recordsFiltered",listData.size());
        return param;
    }

    @RequestMapping(value = "/recruitmentPlan/downloadPlanStatisticsData",method = RequestMethod.POST)
    public String downloadPlanStatisticsData(HrRecruitmentPlan plan,HttpServletResponse response) {
        try {
            String fileName = "招聘计划统计";
            String[] columnNames = {"序号","板块","空缺人数","填补人数","尚空缺人数"};

            String[] keys = {"_index","plat","vacancyCount","fileCount","vacancyCount2"};
            List<HrRecruitmentPlan> plans = hrRecruitmentService.planStatisticsData(plan);
            ExcelUtil.downloadExcel(response, plans,fileName,columnNames,keys);
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 人才库人员新增
     */
    @RequestMapping("/recruitmentStore/edit")
    public String storeEdit(){
        return ".hr.store.edit";
    }


    /**
     * 编辑
     * @param id
     * @param store
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentStore/edit/{id}")
    public String storeEdit(@PathVariable Integer id, HrRecruitmentStore store, Model model){
        store.setId(id);
        model.addAttribute("entity", hrRecruitmentService.getStoreOne(store));
        model.addAttribute("dutyLevel",dictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.store.edit";
    }

    /**
     * 添加
     * @param store
     * @return
     */
    @RequestMapping(value = "/recruitmentStore/insert",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertStore(HrRecruitmentStore store) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.insertStore(store);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 修改
     * @param store
     * @return
     */
    @RequestMapping(value = "/recruitmentStore/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateStore(HrRecruitmentStore store) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.updateStore(store);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/recruitmentStore/delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteStore(HrRecruitmentStore store) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            hrRecruitmentService.deleteStore(store);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 人才库列表
     * @param model
     * @return
     */
    @RequestMapping("/recruitmentStore/index")
    public String recruitmentStore(Model model){
        return ".hr.store.list";
    }



    /**
     * 人才库数据
     * @param param
     * @param store
     * @param args
     * @return
     */
    @RequestMapping(value = "/recruitmentStore/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> storeListData(@RequestParam Map<String,Object> param, HrRecruitmentStore store, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"s.createtime");
        Page<HrRecruitmentStore> page = hrRecruitmentService.storeListData(store,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 人才库导出
     * @param store
     * @param response
     * @return
     */
    @RequestMapping(value = "/recruitmentStore/downloadData",method = RequestMethod.POST)
    public String downloadStoreData(HrRecruitmentStore store, HttpServletResponse response) {
        try {
            String fileName = "人才库列表";
            String[] columnNames = {"序号","姓名","性别","应聘职位","身份证号码","出生日期","毕业院校","学历","专业","居住地址","开始工作时间","现任职单位","现任职岗位","现任职工作起始时间","离职原因","简历来源","联系电话","邮箱","面试时间","面试官","面试评估意见","其它工作经历"};
            String[] keys = {"_index","name","sex","askJob","idCard","birth","school","education","profession","liveAddress","joinWorkDate","companyName","job","recentWorkTime","leaveReason","resumeSrc","mobile","email","auditionDate","auditionUser","note","otherExperience"};
            List<HrRecruitmentStore> plans = hrRecruitmentService.storeListData(store);
            ExcelUtil.downloadExcel(response, plans,fileName,columnNames,keys);
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 人才库导入
     * @param request
     * @return
     */
    @RequestMapping("/recruitmentStore/upload")
    @ResponseBody
    public BaseResult recruitmentStoreUpload(@RequestParam MultipartFile uploadFile, HttpServletRequest request) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            Workbook workbook = ExcelOperateUtil.readExcel2Workbook(uploadFile.getOriginalFilename(), uploadFile.getInputStream());
            hrRecruitmentService.insertStore(workbook);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg("读取Excel文件失败");
        }
        return baseResult;
    }


}
