package com.huayu.hr.web;

import com.huayu.hr.domain.HrContract;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.service.HrContractService;
import com.huayu.hr.service.HrDictService;
import com.huayu.hr.service.HrService;
import com.huayu.hr.service.OrgService;
import com.huayu.hr.web.args.HrContractArgs;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2017/3/13.
 */
@Controller
@RequestMapping("/hr")
public class HrContractController {

    @Autowired
    private HrContractService hrContractService;

    @Autowired
    private HrDictService hrDictService;

    @Autowired
    private HrService hrService;

    @Autowired
    private OrgService orgService;


    /**
     * 获取人员
     * @param param
     * @param hrContractArgs
     * @param pageArgs
     * @return
     */
    @RequestMapping("/contract/getContractEmpListData")
    @ResponseBody
    public Map<String,Object> getContractEmpListData(@RequestParam Map<String,Object> param, HrContractArgs hrContractArgs, PageArgs pageArgs){

        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        Page<HrContract> page = hrContractService.getContractEmpListData(hrContractArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 合同查询
     * @param model
     * @return
     */
    @RequestMapping("/contract/list")
    public String contractList(Model model){
        User user = SpringSecurityUtil.getUser();
        Integer companyId = user.getCurrCompanyId();
        model.addAttribute("departmentList",orgService.getAllBaseDepartments(companyId));
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        return ".hr.contract.contractList";
    }


    /**
     * 查询人员所有合同
     * @param param
     * @param hrContractArgs
     * @param pageArgs
     * @return
     */
    @RequestMapping("/contract/getContractListData")
    @ResponseBody
    public Map<String,Object> getContractListData(@RequestParam Map<String,Object> param, HrContractArgs hrContractArgs, PageArgs pageArgs){

        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.DESC,"hc.contractStartTime");
        if (null!=hrContractArgs){
            Page<HrContract> page = hrContractService.getContractListData(hrContractArgs,pageable);
            param.put("data",page.getContent());
            param.put("recordsTotal",page.getTotalElements());
            param.put("recordsFiltered",page.getTotalElements());
        }else{
            param.put("data", Collections.EMPTY_LIST);
            param.put("recordsTotal",0);
            param.put("recordsFiltered",0);
        }
        return param;
    }


    /**
     * 合同签订
     * @param model
     * @return
     */
    @RequestMapping("/contract/register/{empId}")
    public String contractRegister(Model model, @PathVariable("empId") Integer empId){
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
        model.addAttribute("entity",contract);
        HrEmployee employee = hrService.getEmpBaseInfoAndCompById(empId);
        model.addAttribute("employee",employee);
        return ".hr.contract.contractRegister";
    }


    /**
     * 签订合同
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractRegister",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractRegister(HrContract contract){
        BaseResult result =BaseResult.initBaseResult();
        contract.setOperType(1);
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        try {
            hrContractService.postContractRegister(contract);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }


    /**
     * 修改合同
     * @param model
     * @param empId
     * @param id
     * @return
     */
    @RequestMapping("/contract/edit/{empId}/{id}")
    public String contractEdit(Model model, @PathVariable("empId") Integer empId,@PathVariable("id") Integer id){
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        HrContract contract = hrContractService.getEffectiveContractOne(empId,id);
        model.addAttribute("entity",contract);
        return ".hr.contract.contractEdit";
    }

    /**
     * 保存修改
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractEdit",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractEdit(HrContract contract){
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        return hrContractService.postContractEdit(contract);
    }

    @RequestMapping("/contract/show/{empId}/{id}")
    public String contractShow(Model model, @PathVariable("empId") Integer empId,@PathVariable("id") Integer id){
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        HrContract contract = hrContractService.getContractOne(empId,id);
        model.addAttribute("entity",contract);
        return ".hr.contract.contractShow";
    }

    /**
     * 检查是否签订合同
     * @return
     */
    @RequestMapping(value = "/contract/checkContractRegister",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult checkContractRegister(Integer empId){
        BaseResult result =BaseResult.initBaseResult();
        if (null==empId){
            result.setRmsg("参数错误");
        }else{
            HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
            if (null==contract){
                result = result.setSuccess();
            }
        }
        return result;
    }


    /**
     * 合同变更
     * @param model
     * @param empId
     * @return
     */
    @RequestMapping("/contract/change/{empId}")
    public String contractChange(Model model, @PathVariable("empId") Integer empId){
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
        model.addAttribute("entity",contract);
        return ".hr.contract.contractChange";
    }


    /**
     * 提交合同变更
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractChange",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractChange(HrContract contract){
        BaseResult result = BaseResult.initBaseResult();
        if(null==contract.getId()){
            result.setRmsg("参数错误");
            return result;
        }
        contract.setOperType(2);
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        try {
            hrContractService.coverContract(contract);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 合同续签
     * @param model
     * @param empId
     * @return
     */
    @RequestMapping("/contract/renewed/{empId}")
    public String contractRenewed(Model model, @PathVariable("empId") Integer empId){
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
        model.addAttribute("entity",contract);
        return ".hr.contract.contractRenewed";
    }

    /**
     * 提交续签
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractRenewed",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractRenewed(HrContract contract){
        BaseResult result = BaseResult.initBaseResult();
        if(null==contract.getId()){
            result.setRmsg("参数错误");
            return result;
        }
        contract.setOperType(3);
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        try {
            hrContractService.coverContract(contract);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 解除合同
     * @param model
     * @param empId
     * @return
     */
    @RequestMapping("/contract/release/{empId}")
    public String contractRelease(Model model, @PathVariable("empId") Integer empId){
        HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
        model.addAttribute("entity",contract);
        return ".hr.contract.contractRelease";
    }

    /**
     * 解除合同
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractRelease",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractRelease(HrContract contract){
        BaseResult result = BaseResult.initBaseResult();
        if(null==contract.getId()){
            result.setRmsg("参数错误");
            return result;
        }
        contract.setOperType(4);
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        try {
            hrContractService.coverContract(contract);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 合同终止
     * @param model
     * @param empId
     * @return
     */
    @RequestMapping("/contract/end/{empId}")
    public String contractEnd(Model model, @PathVariable("empId") Integer empId){
        HrContract contract = hrContractService.getLastEffectiveContractOne(empId);
        model.addAttribute("entity",contract);
        return ".hr.contract.contractEnd";
    }

    /**
     * 终止合同
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractEnd",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractEnd(HrContract contract){
        BaseResult result = BaseResult.initBaseResult();
        if(null==contract.getId()){
            result.setRmsg("参数错误");
            return result;
        }
        contract.setOperType(5);
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        try {
            hrContractService.coverContract(contract);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }


    /**
     * 人员信息页面 查看合同信息
     * @param model
     * @param empId
     * @return
     */
    @RequestMapping("/contract/{empId}")
    public String contractInfo(Model model, @PathVariable("empId") Integer empId){
        model.addAttribute("contractCompany", hrDictService.getDictDataByDictValue("contractCompany"));
        List<HrContract> detailList = hrContractService.getAllContractListData(empId);
        model.addAttribute("detailList",detailList);
        return "/hr/emp/detail/contractInfo";
    }

    /**
     *补录合同
     * @param model
     * @param empId
     * @return
     */
    @RequestMapping("/contract/replenish/{empId}")
    public String contractReplenish(Model model, @PathVariable("empId") Integer empId){
        model.addAttribute("contractCompany",hrDictService.getDictDataByDictValue("contractCompany"));
        HrEmployee employee = hrService.getEmpBaseInfoAndCompById(empId);
        model.addAttribute("employee",employee);
        return ".hr.contract.contractReplenish";
    }

    /**
     * 合同补录
     * @param contract
     * @return
     */
    @RequestMapping(value = "/contract/postContractReplenish",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postContractReplenish(HrContract contract){
        BaseResult baseResult = BaseResult.initBaseResult();
        User user = SpringSecurityUtil.getUser();
        contract.setOperUser(user.getUserId().intValue());
        try {
            hrContractService.postContractReplenish(contract);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }
}
