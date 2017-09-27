package com.huayu.market.web;

import com.alibaba.fastjson.JSON;
import com.huayu.cost.domain.CostReimbursementPayDetail;
import com.huayu.market.domain.MarketCostApplyPay;
import com.huayu.market.service.MarketCostApplyPayService;
import com.huayu.p.util.CommonUtil;
import com.huayu.p.web.tools.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 营销费用付款
 * @author ZXL 2017-07-19 9:29
 **/
@Controller
@RequestMapping("/admin/marketcost")
public class MarketCostController {

    @Autowired
    private MarketCostApplyPayService marketCostApplyPayService;

    /**
     * uri:/admin/marketcost/get
     * 报销
     * @param model
     * @return
     */
    @RequestMapping("/get")
    public String get(ModelMap modelMap,@ModelAttribute("entity") MarketCostApplyPay entity){
        Pageable pageable = new PageRequest(entity.getPageNo(),entity.getPageSize());
        Page<MarketCostApplyPay> page = marketCostApplyPayService.get(entity,pageable);
        modelMap.addAttribute("page",page);
        return ".admin.marketcost.reimbPayList";
    }

    /**
     * uri:/admin/marketcost/getdetail/{payId}
     * 获取详情
     * @param payId
     * @return
     */
    @RequestMapping(value = "/getdetail/{payId}",method = RequestMethod.POST)
    public @ResponseBody String getdetail(@PathVariable("payId") Long payId){
        Map<String,Object> map = CommonUtil.result();
        Map<String,Object> result = new HashMap<>();
        if(null == payId){
            map.put("code",1);
            map.put("msg","参数错误");
        } else {
            try {
                MarketCostApplyPay marketCostApplyPay = marketCostApplyPayService.getOneToDetail(payId);
                result.put("detail",marketCostApplyPay);
                map.put("data",result);
            } catch (CustomException c) {
                map.put("code",1);
                map.put("msg",c.getMessage());
            } catch (Exception e) {
                map.put("code",1);
                map.put("msg","获取数据出错");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * uri:/admin/marketcost/pay
     *报销 付款
     * @param details
     * @return
     */
    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    @ResponseBody
    public String pay(Long payId,String details){
        Map<String,Object> map = CommonUtil.result();
        Map<String,Object> result = new HashMap<>();
        if(null == payId){
            map.put("code",1);
            map.put("msg","参数错误");
        } else {
            try {
                List<CostReimbursementPayDetail> payDetails = JSON.parseArray(details, CostReimbursementPayDetail.class);
                marketCostApplyPayService.pay(payId,payDetails);
            } catch (CustomException c) {
                map.put("code",1);
                map.put("msg",c.getMessage());
            } catch (Exception e) {
                map.put("code",1);
                map.put("msg","获取数据出错");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * uri:/admin/marketcost/delete
     * 删除付款详情
     * @param detail
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(CostReimbursementPayDetail detail){
        Map<String,Object> map = CommonUtil.result();
        Map<String,Object> result = new HashMap<>();
        if(null == detail.getPayId()){
            map.put("code",1);
            map.put("msg","参数错误");
        } else {
            try {
                marketCostApplyPayService.delete(detail);
            } catch (CustomException c) {
                map.put("code",1);
                map.put("msg",c.getMessage());
            } catch (Exception e) {
                map.put("code",1);
                map.put("msg","获取数据出错");
            }
        }
        return JSON.toJSONString(map);
    }

}
