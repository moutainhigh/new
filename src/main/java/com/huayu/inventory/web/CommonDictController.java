package com.huayu.inventory.web;

import com.huayu.a.domain.CommonDict;
import com.huayu.a.service.CommonDictService;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.inventory.web.args.CommonDictArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29.
 * 单位维护Controller
 */
@Controller
@RequestMapping("/admin/inventory/dict")
public class CommonDictController {

    @Autowired
    private CommonDictService commonDictService;

    @RequestMapping("/insert")
    public String insert() {

        return null;
    }

    /**
     * 校验是否有该单位
     * @param dictValue
     * @return
     */
    @RequestMapping("/checkValue")
    @ResponseBody
    public BaseResult checkValue(String dictValue) {
        CommonDict commonDict = commonDictService.getDictByGroupAndValue("unit", dictValue);
        BaseResult baseResult = BaseResult.initBaseResult();
        if (commonDict != null) {
            baseResult.setRmsg("该单位已经存在!");
        } else {
            baseResult.setSuccess();
        }
        return baseResult;
    }

    /**
     * 跳转到新增
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String toCommonDictPage(Model model) {
        List<CommonDict> list = commonDictService.getDictList("unit");
        model.addAttribute("list", list);
        return ".inventory.storageManage.commonDictEdit";
    }

    /**
     * 点击新增保存
     * @param dictValue
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(String dictValue, Integer id) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (id == null) {
                CommonDict commonDict = commonDictService.getDictByGroupAndValue("unit", dictValue);
                if (commonDict != null) {
                    baseResult.setRmsg("该单位已经存在!");
                    return baseResult;
                } else {
                    commonDictService.saveDict(dictValue);
                    baseResult.setSuccess();
                }
            } else {
                try {
                    commonDictService.updateDictValue(dictValue, id);
                    baseResult.setSuccess();
                } catch (Exception e) {
                    baseResult.setRmsg(e.getMessage());
                }
            }
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 获取单位列表
     * @param param
     * @param commonDictArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/dictListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> dictListData(@RequestParam Map<String,Object> param, CommonDictArgs commonDictArgs, PageArgs args){
        args.initPageInfo();
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"rank");
        Page<CommonDict> page = commonDictService.dictListData(commonDictArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 跳转到单位列表页面
     * @return
     */
    @RequestMapping("/index")
    public String toDictListPage() {
        return ".inventory.storageManage.commonDictList";
    }

    /**
     * 删除单位
     * @param id
     * @return
     */
    @RequestMapping("/deleteDict")
    @ResponseBody
    public BaseResult deleteDict(Integer id) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            commonDictService.deleteDict(id);
            baseResult.setSuccess();
        } catch (Exception e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/toUpdateDictPage/{id}")
    public String toUpdateDictPage(@PathVariable Integer id, Model model) {
        CommonDict commonDict = commonDictService.getDictById(id);
        model.addAttribute("commonDict", commonDict);
        return ".inventory.storageManage.commonDictEdit";
    }


}
