package com.huayu.material.web;

import com.huayu.common.BaseResult;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.material.domain.PriceSeedling;
import com.huayu.material.service.PriceSeedlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 苗木管理
 * Created by Administrator on 2017/4/28.
 */
@Controller
@RequestMapping("/admin/priceSeedling")
public class PriceSeedlingController {

    private static Logger logger = LoggerFactory.getLogger(PriceSeedlingController.class);

    @Resource
    private PriceSeedlingService priceSeedlingService;


    /**
     * 材料页面
     * @return
     */
    @RequestMapping("/index")
    public String materialList(){

        return ".admin.priceSeedling.materialList";
    }


    @RequestMapping("/insert")
    @ResponseBody
    public BaseResult insertMaterial(PriceSeedling priceSeedling){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            priceSeedlingService.insertMaterial(priceSeedling);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public BaseResult updateMaterial(PriceSeedling priceSeedling){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            priceSeedlingService.updateMaterial(priceSeedling);
            baseResult.setSuccess();
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                baseResult.setRmsg("材料规格重复");
            }else{
                baseResult.setRmsg(e.getMessage());
                logger.error("修改材料失败,{}",e);
            }
        }
        return baseResult;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult deleteMaterial(String ids){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            priceSeedlingService.deleteMaterial(ids);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public BaseResult deleteMaterial(Integer id){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            priceSeedlingService.deleteOneMaterial(id);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listData(@RequestParam Map<String,Object> param, PriceSeedling priceSeedling, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.mcode");
        Page<PriceSeedling> page = priceSeedlingService.materialListData(priceSeedling,pageable, ConstantsHolder.getContext().getCurrDataId());
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping("/materialEdit")
    public String materialEdit(Model model){
        return ".admin.priceSeedling.materialEdit";
    }

    @RequestMapping("/materialEdit/{id}")
    public String materialEdit(Model model, @PathVariable("id") Integer id){
        PriceSeedling priceSeedling = priceSeedlingService.getMaterialResultById(id);
        model.addAttribute("materialResult",priceSeedling);
        return ".admin.priceSeedling.materialEdit";
    }


}
