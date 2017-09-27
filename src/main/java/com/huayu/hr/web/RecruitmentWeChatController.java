package com.huayu.hr.web;

import com.huayu.hr.domain.HrJobRequest;
import com.huayu.hr.domain.HrRecruitmentContent;
import com.huayu.hr.service.HrContentService;
import com.huayu.common.BaseResult;
import com.huayu.common.tool.FileUtil;
import com.ly.service.base.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 微信
 * 招聘求职 前端
 * Created by DengPeng on 2016/12/29.
 */
@Controller
@RequestMapping("/position")
public class RecruitmentWeChatController {

    @Autowired
    private HrContentService contentService;

    @RequestMapping("/list")
    public String contentList(Model model, Integer cid,Integer area){
        HrRecruitmentContent content = new HrRecruitmentContent();
        if (null==cid){
            cid = 0;
        }
        content.setPlateId(cid);
        if (null==area){
            area=1;
        }
        content.setArea(area);
        model.addAttribute("contentList",contentService.getContentList(content));
        model.addAttribute("action",cid);
        model.addAttribute("area",area);
        return "/hr/front/weChatRecuit/list";
    }

    @RequestMapping("/request")
    public String request(Model model, Integer jid, Integer cid, Integer area, HrRecruitmentContent content){
        if (null==cid){
            cid = 0;
        }
        if (null==area){
            area = 1;
        }
        model.addAttribute("cid",cid);
        model.addAttribute("jid",jid);
        model.addAttribute("area",area);
        content.setId(jid);
        content.setPlateId(cid);
        content.setArea(area);
        model.addAttribute("titleList", contentService.getContentTitleList(content));
        return "/hr/front/weChatRecuit/request";
    }

    @RequestMapping("/getJobList")
    @ResponseBody
    public List<HrRecruitmentContent> getJobList(HrRecruitmentContent content){
        return contentService.getContentTitleList(content);
    }

    /**
     * 提交
     * @param jobRequest
     * @return
     */
    @RequestMapping(value = "/postJobRequest",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postJobRequest(HrJobRequest jobRequest){
        jobRequest.setWay(0);
        return contentService.postJobRequest(jobRequest);
    }



    /**
     * 附件上传
     * @param imgFile
     * @param request
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public BaseResult saveCommImg(@RequestParam MultipartFile imgFile, HttpServletRequest request) {
        Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
        BaseResult baseResult = BaseResult.initBaseResult(true);
        String fileName = FileUtil.saveFile(imgFile, commConfig.get("hr_job_request_path"), DateUtil.getYmd());
        if (StringUtils.isNoneBlank(fileName)) {
            baseResult.setSuccess();
            baseResult.setRdata(fileName);
        }
        return baseResult;
    }

}
