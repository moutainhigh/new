package com.huayu.hr.web;

import com.huayu.common.BaseResult;
import com.huayu.hr.domain.HrJobRequest;
import com.huayu.hr.domain.HrRecruitmentContent;
import com.huayu.hr.service.HrContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公司招聘
 * Created by DengPeng on 2017/1/17.
 */
@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {

    @Autowired
    private HrContentService contentService;

    @RequestMapping({"","/","index"})
    public String index(){

        return "redirect:http://www.cqhyrc.com.cn/talent";
    }

    @RequestMapping("/society/{p}/{a}/{pageIndex}")
    public String society(@PathVariable Integer p,@PathVariable Integer a,@PathVariable Integer pageIndex, Model model, HrRecruitmentContent content){
        content.setPlateId(p);
        content.setArea(a);
        content.setStatus(0);
        content.setType(1);
        Pageable pageable  = new PageRequest(pageIndex, 10,Sort.Direction.ASC,"sort");
        Page<HrRecruitmentContent> page =  contentService.getContentList(content,pageable);
        model.addAttribute("page",page);
        model.addAttribute("p",p);
        model.addAttribute("a",a);
        return "/hr/front/webRecruit/society";
    }

    @RequestMapping("/society")
    public String society(Model model, HrRecruitmentContent content){
        return society(0,1,0,model,content);
    }

    @RequestMapping("/school/{p}/{a}/{pageIndex}")
    public String school(@PathVariable Integer p,@PathVariable Integer a,@PathVariable Integer pageIndex,Model model, HrRecruitmentContent content){
        content.setPlateId(p);
        content.setArea(a);
        content.setStatus(0);
        content.setType(2);
        Pageable pageable  = new PageRequest(pageIndex, 10,Sort.Direction.ASC,"sort");
        Page<HrRecruitmentContent> page =  contentService.getContentList(content,pageable);
        model.addAttribute("page",page);
        model.addAttribute("p",p);
        model.addAttribute("a",a);
        return "/hr/front/webRecruit/school";
    }

    @RequestMapping("/school")
    public String school(Model model, HrRecruitmentContent content){
        return school(0,1,0,model,content);
    }

    @RequestMapping("/practice/{p}/{a}/{pageIndex}")
    public String practice(@PathVariable Integer p,@PathVariable Integer a,@PathVariable Integer pageIndex, Model model, HrRecruitmentContent content){
        content.setPlateId(p);
        content.setArea(a);
        content.setStatus(0);
        content.setType(3);
        Pageable pageable  = new PageRequest(pageIndex, 10,Sort.Direction.ASC,"sort");
        Page<HrRecruitmentContent> page =  contentService.getContentList(content,pageable);
        model.addAttribute("page",page);
        model.addAttribute("p",p);
        model.addAttribute("a",a);
        return "/hr/front/webRecruit/practice";
    }

    @RequestMapping("/practice")
    public String practice(Model model, HrRecruitmentContent content){
        return practice(0,1,0,model,content);
    }


    @RequestMapping("/aboutUs")
    public String aboutUs(){

        return "/hr/front/webRecruit/aboutUs";
    }

    /**
     * 应聘页面
     * @param model
     * @param content
     * @param cid
     * @return
     */
    @RequestMapping("/applyJob")
    public String applyJob(Model model, HrRecruitmentContent content, Integer cid, Integer area, Integer jid){
        if (cid==null || cid <0 || cid >6){
            cid = 0;
        }
        content.setPlateId(cid);
        content.setArea(area);
        model.addAttribute("cid",cid);
        model.addAttribute("area",area);
        model.addAttribute("jid",jid);
        model.addAttribute("titleList", contentService.getContentTitleList(content));
        return "/hr/front/webRecruit/applyJob";
    }

    @RequestMapping(value = "/postJobRequest",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postJobRequest(HrJobRequest jobRequest){
        jobRequest.setWay(1);
        return contentService.postJobRequest(jobRequest);
    }

}
