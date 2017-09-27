package com.huayu.hr.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.hr.domain.HrJobRequest;
import com.huayu.hr.domain.HrRecruitmentContent;
import com.huayu.hr.service.HrContentService;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by DengPeng on 2016/12/28.
 */
//@Controller
//@RequestMapping("/hr")
public class HrContentController {

    @Autowired
    private HrContentService contentService;

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
     * @param empId
     * @return
     */
    @RequestMapping("/recruitmentPlan/index")
    public String recruitmentPlan(Model model, @PathVariable("empId") Integer empId){
//        HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
//        model.addAttribute("entity",contract);
        return ".hr.contract.contractEnd";
    }

}
