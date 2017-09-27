package com.huayu.hr.web;

import com.huayu.hr.domain.HrDictData;
import com.huayu.hr.service.HrDictService;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/20.
 */
@Controller
@RequestMapping("/hr/hrDict")
public class HrDictController {

    @Autowired
    private HrDictService dictService;

    @RequestMapping("/editDict")
    public String editDict(Model model){
        model.addAttribute("dictList",dictService.getChildDictByDictValue("hrBaseInfo"));
        return ".hr.common.dictEdit";
    }

    @RequestMapping("/getDictData")
    @ResponseBody
    public List<HrDictData> editDict(Integer dictId){
        return dictService.getDictDataByDictId(dictId);
    }

    @RequestMapping("/addDictData")
    @ResponseBody
    public BaseResult addDictData(String dictDataArray, Integer dictId){
        BaseResult baseResult;
        try {
            baseResult = dictService.addDictData(dictDataArray,dictId);
        } catch (BusinessException e) {
            baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/deleteDictData")
    @ResponseBody
    public BaseResult deleteDictData(Integer id, Integer dictId){
        BaseResult baseResult;
        try {
            baseResult = dictService.deleteDictData(id,dictId);
        } catch (BusinessException e) {
            baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }


}
