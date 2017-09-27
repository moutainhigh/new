package com.huayu.hr.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.hr.domain.HrCompany;
import com.huayu.hr.domain.HrDepartment;
import com.huayu.hr.domain.HrJob;
import com.huayu.hr.domain.HrJobPlait;
import com.huayu.hr.service.HrDictService;
import com.huayu.hr.service.OrgService;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2016/12/5.
 */
@Controller
@RequestMapping("/hr/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrDictService hrDictService;


    /**
     * 获取公司列表
     * @param parentId
     * @return
     */
    @RequestMapping("/getCompanies")
    @ResponseBody
    public BaseResult getCompanyList(Integer parentId){
        BaseResult result = BaseResult.initBaseResult();
        if (null==parentId){
            result.setArgumentsMiss();
            return result;
        }
        User user = SpringSecurityUtil.getUser();
        List<HrCompany> list = orgService.getCompaniesByParentId(parentId, user.getDataAuthoritiesRegexp());
        if (!CollectionUtils.isEmpty(list)){
            result.setRdata(list);
            result.setSuccess();
        }
        return result;
    }

    @RequestMapping(value = "/getAllCompanies",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getAllCompanies(boolean ignore){
        User user = SpringSecurityUtil.getUser();
        BaseResult result = BaseResult.initBaseResult();
        String auth = user.getDataAuthoritiesRegexp();
        if (ignore){
            auth = "'^2'";
        }
        result.setRdata(orgService.getAllCompanies(0,auth));
        result.setSuccess();
        return result;
    }

    /**
     * 公司管理
     * @return
     */
    @RequestMapping("/companyEdit")
    public String companyEdit(){

        return ".hr.common.companyEdit";
    }

    @RequestMapping("/addCompany")
    @ResponseBody
    public BaseResult addCompany(HrCompany company){

        return orgService.addCompany(company);
    }

    @RequestMapping("/updateCompany")
    @ResponseBody
    public BaseResult updateCompany(HrCompany company){

        return orgService.updateCompany(company);
    }

    @RequestMapping("/delCompany")
    @ResponseBody
    public BaseResult delCompany(HrCompany company){

        return orgService.delCompany(company);
    }


    /**
     * 获取部门列表
     * @param companyId
     * @param parentId
     * @return
     */
    @RequestMapping("/getDepartments")
    @ResponseBody
    public BaseResult getCompanyList(Integer companyId, Integer parentId){
        BaseResult result = orgService.getDepartmentsByCid(companyId,parentId);
        return result;
    }

    @RequestMapping("/getAllDepartments")
    @ResponseBody
    public BaseResult getAllDepartments(Integer companyId,boolean ignore){
        BaseResult result = BaseResult.initBaseResult();
        User user = SpringSecurityUtil.getUser();
        String auth = user.getDataAuthoritiesRegexp();
        if (ignore){
            auth = "'^2'";
        }
        result.setRdata(orgService.getAllTreeDepartments(companyId,0,auth));
        result.setSuccess();
        return result;
    }

    /**
     * 部门管理
     * @return
     */
    @RequestMapping("/departmentEdit")
    public String departmentEdit(){

        return ".hr.common.departmentEdit";
    }


    /**
     * 更新部门信息
     * @param department
     * @return
     */
    @RequestMapping("/updateDepartment")
    @ResponseBody
    public BaseResult updateDepartment(HrDepartment department){

        return orgService.updateDepartment(department);
    }

    @RequestMapping("/addDepartment")
    @ResponseBody
    public BaseResult addDepartment(HrDepartment department){
        BaseResult result = BaseResult.initBaseResult();
        try {
            orgService.addDepartment(department);
            result.setRdata(department);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping("/jobEdit")
    public String jobEdit(Model model){
        model.addAttribute("manageLine",hrDictService.getDictDataByDictValue("manageLine"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.common.jobEdit";
    }

    @RequestMapping("/updateJob")
    @ResponseBody
    public BaseResult updateJob(HrJob job){

        return orgService.updateJob(job);
    }

    /**
     * 添加编制
     * @param hrJobPlait
     * @return
     */
    @RequestMapping("/addJobPlait")
    @ResponseBody
    public BaseResult addJobPlait(HrJobPlait hrJobPlait){
        BaseResult result = BaseResult.initBaseResult();
        try {
            User user = SpringSecurityUtil.getUser();
            hrJobPlait.setCreatetime(new Date());
            hrJobPlait.setCreateUser(user.getUserId().intValue());
            orgService.addJobPlait(hrJobPlait);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }


    /**
     * 修改编制
     * @param hrJobPlait
     * @return
     */
    @RequestMapping("/editJobPlait")
    @ResponseBody
    public BaseResult editJobPlait(HrJobPlait hrJobPlait){
        BaseResult result = BaseResult.initBaseResult();
        try {
            orgService.editJobPlait(hrJobPlait);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 删除编制
     * @param hrJobPlait
     * @return
     */
    @RequestMapping("/delJobPlait")
    @ResponseBody
    public BaseResult delJobPlait(HrJobPlait hrJobPlait){
        BaseResult result = BaseResult.initBaseResult();
        try {
            orgService.delJobPlait(hrJobPlait);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/addJob")
    @ResponseBody
    public BaseResult addJob(HrJob job, HrJobPlait hrJobPlait){
        BaseResult result = BaseResult.initBaseResult();
        try {
            orgService.addJob(job,hrJobPlait);
            result.setRdata(job);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delJob")
    @ResponseBody
    public BaseResult delJob(HrJob job){
        return orgService.delJob(job);
    }

    /**
     * 根据部门Id 获取职位信息
     * @param departmentId
     * @return
     */
    @RequestMapping("/getAllJob")
    @ResponseBody
    public BaseResult getAllJobByDepartmentId(Integer departmentId){
        BaseResult result = BaseResult.initBaseResult();
        try {
            result.setRdata(orgService.getAllJobByDepartmentId(departmentId));
            result.setSuccess();
            return result;
        } catch (Exception e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    /**
     * 获取岗位和剩余编制数
     * @param departmentId
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/getAllJobWithPlait",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getAllJobByDepartWithPlait(@RequestParam Integer departmentId,@RequestParam Integer year,@RequestParam Integer month){
        BaseResult result = BaseResult.initBaseResult();
        try {
            result.setRdata(orgService.getAllJobByDepartWithPlait(departmentId,year,month));
            result.setSuccess();
            return result;
        } catch (Exception e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    @RequestMapping("/getOneJob")
    @ResponseBody
    public BaseResult getOneJob(HrJob job){
        BaseResult result = BaseResult.initBaseResult();
        try {
            result.setRdata(orgService.getOneJob(job));
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 批量复制岗位
     * @param srcDept
     * @param targComp
     * @param targDept
     * @return
     */
    @RequestMapping(value = "/copyJob",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult copyJob(Integer srcDept ,Integer targComp, Integer targDept){
        try {
            return orgService.copyJob(srcDept,targComp,targDept);
        } catch (Exception e) {
            return BaseResult.initBaseResult(e.getMessage());
        }
    }

    @RequestMapping("/getAllPlat")
    @ResponseBody
    public BaseResult getAllPlat() {
        BaseResult result = BaseResult.initBaseResult();
        result.setRdata(orgService.getAllPlat());
        result.setSuccess();
        return result;
    }


    /**
     * 岗位编制列表
     * @param param
     * @param jobPlait
     * @param pageArgs
     * @return
     */
    @RequestMapping("/jobPlaitListData")
    @ResponseBody
    public Map<String,Object> jobPlaitListData(@RequestParam Map<String,Object> param, HrJobPlait jobPlait, PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.ASC,"p.year,p.month");
        Page<HrJobPlait> page = orgService.getJobPlaitListData(jobPlait,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

}
