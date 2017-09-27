package com.huayu.hr.web;

import com.alibaba.fastjson.JSON;
import com.huayu.hr.domain.HrTrain;
import com.huayu.hr.domain.HrTrainAttachment;
import com.huayu.hr.domain.HrTrainEmp;
import com.huayu.hr.service.TrainingService;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.FileUtil;
import com.ly.service.base.DateUtil;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 培训
 * Created by DengPeng on 2017/1/18.
 */
@Controller
@RequestMapping("/hr")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    /**
     * 培训活动列表
     * @return
     */
    @RequestMapping("/train/list")
    public String index(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("currCmpId",user.getCurrCompanyId());
        return ".hr.train.list";
    }

    @RequestMapping(value = "/train/getListData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getListData(@RequestParam Map<String,Object> param, HrTrain train, PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.DESC,"t.createtime");
        Page<HrTrain> pageData = trainingService.getTrainListData(train,pageable);
        param.put("data",pageData.getContent());
        param.put("recordsTotal",pageData.getTotalElements());
        param.put("recordsFiltered",pageData.getTotalElements());
        return param;
    }


    /**
     * 培训编辑
     * @return
     */
    @RequestMapping("/train/edit")
    public String edit(Model model,HttpServletRequest request){
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        model.addAttribute("fileUrl",commConfig.get("hr_train_file_url"));
        Date now = new Date();
        User user = SpringSecurityUtil.getUser();
        String code =  trainingService.getNextTrainNo(user.getCurrCompanyCode());
        model.addAttribute("code",code);
        model.addAttribute("companyId",user.getCurrCompanyId());
        model.addAttribute("year",DateTimeUtil.getYear(now));
        model.addAttribute("month",DateTimeUtil.getMonth(now));
        model.addAttribute("quarter",DateTimeUtil.getSeason(now));
        return ".hr.train.edit";
    }

    @RequestMapping("/train/edit/{id}")
    public String edit(Model model,@PathVariable Integer id,HttpServletRequest request){
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        model.addAttribute("fileUrl",commConfig.get("hr_train_file_url"));
        HrTrain train = trainingService.getTrainOne(id);
        model.addAttribute("entity",train);
        List<HrTrainAttachment> attachList = trainingService.getAttachmentByTrain(id);
        model.addAttribute("attachList",attachList);
        List<HrTrainEmp> empList = trainingService.getTrainEmpListByTrainId(id);
        model.addAttribute("empList",empList);
        return ".hr.train.edit";
    }


    /**
     * 附件上传
     * @param imgFile
     * @param request
     * @return
     */
    @RequestMapping("/train/uploadFile")
    @ResponseBody
    public BaseResult saveCommImg(@RequestParam MultipartFile imgFile, HttpServletRequest request) {
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        BaseResult baseResult = BaseResult.initBaseResult(true);
        String fileName = FileUtil.saveFile(imgFile, commConfig.get("hr_train_file_path"), DateUtil.getYmd());
        if (StringUtils.isNotBlank(fileName)) {
            baseResult.setSuccess();
            baseResult.setRdata(fileName);
        }
        return baseResult;
    }

    @RequestMapping(value = "/train/delAttachment",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delAttachment(Integer id,Integer trainId){
        BaseResult baseResult = BaseResult.initBaseResult();
        if (null==id){
            baseResult.setRmsg("参数错误");
        }else{
            try {
                trainingService.delAttachment(id, trainId);
                baseResult.setSuccess();
            } catch (BusinessException e) {
                baseResult.setRmsg(e.getMessage());
            }
        }
        return baseResult;
    }

    @RequestMapping(value = "/train/saveAttachment",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveAttachment(Integer trainId,String attachmentListStr){
        BaseResult baseResult = BaseResult.initBaseResult();
        if (null==trainId){
            baseResult.setRmsg("参数错误");
        }else{
            List<HrTrainAttachment> attachments = JSON.parseArray(attachmentListStr, HrTrainAttachment.class);
            try {
                trainingService.saveAttachment(trainId,attachments);
                baseResult.setSuccess();
            } catch (BusinessException e) {
                baseResult.setRmsg(e.getMessage());
            }
        }
        return baseResult;
    }


    @RequestMapping(value = "/train/postOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postOne(HrTrain train,String empListStr,String attachmentListStr){
        List<HrTrainEmp> empList = JSON.parseArray(empListStr, HrTrainEmp.class);
        List<HrTrainAttachment> attachments = JSON.parseArray(attachmentListStr, HrTrainAttachment.class);
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            trainingService.post(train,empList,attachments);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/train/putOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult putOne(HrTrain train){
        BaseResult baseResult  = BaseResult.initBaseResult();
        try {
            trainingService.putOne(train);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }


    @RequestMapping(value = "/train/saveTrainEmp",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveTrainEmp(Integer trainId,String empListStr){
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrTrainEmp> empList = JSON.parseArray(empListStr, HrTrainEmp.class);
        if (null==trainId){
            baseResult.setRmsg("参数错误");
        }else{
            try {
                trainingService.saveTrainEmp(trainId,empList);
                baseResult.setSuccess();
            } catch (BusinessException e) {
                baseResult.setRmsg(e.getMessage());
            }
        }
        return baseResult;
    }

    @RequestMapping(value = "/train/delOneTrainEmp",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delOneTrainEmp(Integer id,Integer trainId){
        BaseResult baseResult = BaseResult.initBaseResult();
        if (null==id){
            baseResult.setRmsg("参数错误");
        }else{
            try {
                trainingService.delOneTrainEmp(id, trainId);
                baseResult.setSuccess();
            } catch (BusinessException e) {
                baseResult.setRmsg(e.getMessage());
            }
        }
        return baseResult;
    }

    @RequestMapping(value = "/train/putTrainEmp",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult putTrainEmp(Integer id, Integer trainId,HrTrainEmp trainEmp){
        BaseResult baseResult = BaseResult.initBaseResult();
        if (null==id || null == trainId){
            baseResult.setRmsg("参数错误");
        }
        try {
            trainingService.putTrainEmp(trainEmp);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/train/statisticsComp")
    public String statisticsComp(){

        return ".hr.train.statisticsComp";
    }

    @RequestMapping(value = "/train/getStatisticsCompData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getStatisticsCompData(@RequestParam Map<String,Object> param, HrTrain train, PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        Page<HrTrain> pageData = trainingService.getStatisticsCompData(train,pageable);
        param.put("data",pageData.getContent());
        param.put("recordsTotal",pageData.getTotalElements());
        param.put("recordsFiltered",pageData.getTotalElements());
        return param;
    }


    @RequestMapping("/train/statisticsEmp")
    public String statisticsEmp(){

        return ".hr.train.statisticsEmp";
    }

    @RequestMapping(value = "/train/getStatisticsEmpData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getStatisticsEmpData(@RequestParam Map<String,Object> param, HrTrainEmp trainEmp, PageArgs pageArgs){

        Pageable pageable ;
        if (StringUtils.isBlank(trainEmp.getEmpName())){
            param.put("data", Collections.EMPTY_LIST);
            param.put("recordsTotal",0);
            param.put("recordsFiltered",0);
        }else{
            pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
            Page<HrTrainEmp> pageData = trainingService.getStatisticsEmpData(trainEmp,pageable);
            param.put("data",pageData.getContent());
            param.put("recordsTotal",pageData.getTotalElements());
            param.put("recordsFiltered",pageData.getTotalElements());
        }
        return param;
    }

    @RequestMapping(value = "/train/downloadTrainCompStatisticsData",method = RequestMethod.POST)
    public String downloadTrainStatisticsData(HrTrain param, PageArgs pageArgs, HttpServletResponse response){

        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        trainingService.downloadTrainCompStatisticsData(param,pageable,response);
        return null;
    }

    @RequestMapping(value = "/train/downloadTrainEmpStatisticsData",method = RequestMethod.POST)
    public String downloadTrainEmpStatisticsData(HrTrainEmp param, PageArgs pageArgs, HttpServletResponse response){

        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        trainingService.downloadTrainEmpStatisticsData(param,pageable,response);
        return null;
    }


    @RequestMapping(value = "/train/trainStatistics",method = RequestMethod.GET)
    public String trainStatistics( Model model){
        return ".hr.train.trainStatistics";
    }

    @RequestMapping(value = "/train/getTrainStatisticsOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getTrainStatisticsOne(String yearMonth, Integer quarter, String year){
        if (StringUtils.isBlank(yearMonth)&&null==quarter&&StringUtils.isBlank(year)){
            return BaseResult.initBaseResult();
        }else{
            return trainingService.getTrainStatisticsOne(yearMonth, quarter, year);
        }
    }

    /**
     * 统计当前时间截至的数据
     * @return
     */
    @RequestMapping(value = "/train/trainStatistics",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsTrainData(){
        try{
            return trainingService.statisticsTrainData();
        } catch (BusinessException e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }


    @RequestMapping(value = "/train/trainInfo/{empId}",method = RequestMethod.GET)
    public String trainInfo(Model model, @PathVariable Integer empId ,HrTrainEmp trainEmp){
        trainEmp.setEmpId(empId);
        List<HrTrainEmp> list = trainingService.getAllEmpTrainData(trainEmp);
        model.addAttribute("detailList",list);
        return "/hr/emp/detail/trainInfo";
    }

}
