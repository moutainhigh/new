package com.huayu.cost.web;

import com.alibaba.fastjson.JSON;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.cost.domain.*;
import com.huayu.cost.service.CostService;
import com.huayu.cost.web.args.CostApplyLoanArgs;
import com.huayu.cost.web.args.CostApplyPayArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2017/4/21.
 */
@Controller
@RequestMapping("/admin/cost")
public class CostController {

    @Autowired
    private CostService costService;

    @RequestMapping("/costApplyList")
    public String costApplyList(Model model){
//        User user = SpringSecurityUtil.getUser();
//        model.addAttribute("user",user);
        return ".admin.cost.costApplyList";
    }

    @RequestMapping("/costApplyListData")
    @ResponseBody
    public Map<String, Object> costApplyListData(@RequestParam Map<String,Object> param, CostApplyLoanArgs costApplyLoan, PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.DESC,"applyDate");
        Page<CostApplyLoan> page = costService.getCostApplyData(costApplyLoan,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping(value = "/getLoanPayOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getLoanPayOne(CostApplyLoan costApplyLoan){
        BaseResult result = BaseResult.initBaseResult();
        if(null == costApplyLoan.getLoanId()){
            result.setRmsg("参数错误");
            return result;
        }
        result.setRdata(costService.getApplyLoanOne(costApplyLoan));
        return result.setSuccess();
    }

    /**
     * 付款
     * @param details
     * @return
     */
    @RequestMapping(value = "/saveLoanPayDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveLoanPayDetail(Long loanId, String details){
        BaseResult result = BaseResult.initBaseResult();
        try {
            List<CostLoanPayDetail> payDetails = JSON.parseArray(details, CostLoanPayDetail.class);
            costService.saveLoanPayDetail(loanId,payDetails);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 删除付款 详情
     * @param detail
     * @return
     */
    @RequestMapping(value = "/deleteLoanPayDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteLoanPayDetail(CostLoanPayDetail detail){
        BaseResult result = BaseResult.initBaseResult();
        try {
            costService.deleteLoanPayDetail(detail);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 取消付款
     * @param costApplyLoan
     * @return
     */
    @RequestMapping(value = "/cancelPay",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult cancelPay(CostApplyLoan costApplyLoan){
        BaseResult result = BaseResult.initBaseResult();
        if(null == costApplyLoan.getLoanId()){
            result.setRmsg("参数错误");
            return result;
        }
        try {
            costService.cancelPay(costApplyLoan);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result.setSuccess();
    }

    /**
     * 获取还款 信息
     * @param costApplyLoan
     * @return
     */
    @RequestMapping(value = "/getLoanRePayOne",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getLoanRePayOne(CostApplyLoan costApplyLoan){
        BaseResult result = BaseResult.initBaseResult();
        if(null == costApplyLoan.getLoanId()){
            result.setRmsg("参数错误");
            return result;
        }
        result.setRdata(costService.getRepayLoanOne(costApplyLoan));
        return result.setSuccess();
    }

    /**
     * 提交还款信息
     * @param loanId
     * @param details
     * @return
     */
    @RequestMapping(value = "/saveLoanRePayDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveLoanRePayDetail(Long loanId, String details){
        BaseResult result = BaseResult.initBaseResult();
        try {
            List<CostBalanceDetail> rePayDetails = JSON.parseArray(details, CostBalanceDetail.class);
            costService.saveLoanRePayDetail(loanId,rePayDetails);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }


    /**
     * 报销
     * @param model
     * @return
     */
    @RequestMapping("/reimbPayList")
    public String reimbPayList(Model model){

        return ".admin.cost.reimbPayList";
    }

    /**
     * 报销数据
     * @param param
     * @param costApplyPay
     * @param pageArgs
     * @return
     */
    @RequestMapping("/reimbPayListData")
    @ResponseBody
    public Map<String, Object> reimbPayListData(@RequestParam Map<String,Object> param, CostApplyPayArgs costApplyPay, PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(),Sort.Direction.DESC,"applyDate");
        costApplyPay.setCategory(1);
        Page<CostApplyPay> page = costService.getCostApplyPayData(costApplyPay,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping(value = "/getCostReimbPayOneDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getCostReimbPayOneDetail(CostApplyPay costApplyPay){
        BaseResult result = BaseResult.initBaseResult();
        if(null == costApplyPay.getPayId()){
            result.setRmsg("参数错误");
            return result;
        }
        result.setRdata(costService.getCostApplyPayOneDetail(costApplyPay));
        return result.setSuccess();
    }

    /**
     *报销 付款
     * @param details
     * @return
     */
    @RequestMapping(value = "/saveReimbPayDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveReimbPayDetail(Long payId, Long contractId, String details){
        BaseResult result = BaseResult.initBaseResult();
        try {
            if(null == payId){
                result.setRmsg("参数错误");
                return result;
            }
            List<CostReimbursementPayDetail> payDetails = JSON.parseArray(details, CostReimbursementPayDetail.class);
            costService.saveReimbPayDetail(payId, contractId, payDetails);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    /**
     * 删除付款详情
     * @param detail
     * @return
     */
    @RequestMapping(value = "/deleteReimbPayDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteReimbPayDetail(CostReimbursementPayDetail detail){
        BaseResult result = BaseResult.initBaseResult();
        try {
            if(null == detail.getPayId() || null==detail.getId()){
                result.setRmsg("参数错误");
                return result;
            }
            costService.deleteReimbPayDetail(detail);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

}
