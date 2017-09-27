package com.huayu.bill.web;

import com.huayu.bill.domain.BillDic;
import com.huayu.bill.service.BillDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Controller
@RequestMapping("/admin/bill")
public class BillDictController {

    @Autowired
    private BillDictService billDicService;


    @RequestMapping(value = "/dictList",method = RequestMethod.GET)
    public String dictList(Model model,BillDic billDic){
        model.addAttribute("entity",billDic);
        Pageable pageable = new PageRequest(billDic.getPageNo(), billDic.getPageSize(),billDic.getDts());
        if (StringUtils.isEmpty(billDic.getType())){
            billDic.setType("area");
        }
        model.addAttribute("page",billDicService.getDictList(billDic,pageable));
        return ".admin.bill.dictList";
    }

    @RequestMapping(value = "/parentDictList",method = RequestMethod.POST)
    @ResponseBody
    public List parentDictList(BillDic billDic){
        Integer level = billDic.getLevel();
        if (null != level && level > 0){
            return billDicService.getAllDict(level-1);
        }
       return null;
    }

    @RequestMapping(value = "/childDictList",method = RequestMethod.POST)
    @ResponseBody
    public List childDictList(Integer id){
        if (null!=id){
            return billDicService.getChildDictList(id);
        }
        return null;
    }

    @RequestMapping(value = "/saveBillDic",method = RequestMethod.POST)
    @ResponseBody
    public int saveBillDic(BillDic billDic){
        return billDicService.saveDict(billDic);
    }

    /**
     * 获取单个发票参数
     * @param id
     * @return
     */
    @RequestMapping(value = "/getBillDicOne",method = RequestMethod.POST)
    @ResponseBody
    public BillDic getBillDicOne(Integer id){
        return billDicService.getDictOne(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delBillDicOne",method = RequestMethod.POST)
    @ResponseBody
    public int delBillDicOne(Integer id){
        return billDicService.delBillDicOne(id);
    }


}
