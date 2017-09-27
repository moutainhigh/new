package com.huayu.inventory.web;

import com.alibaba.fastjson.JSON;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.inventory.domain.PrItemDelivery;
import com.huayu.inventory.domain.PrItemInStorage;
import com.huayu.inventory.domain.PrItemInStorageList;
import com.huayu.inventory.service.PrBaseInfoService;
import com.huayu.inventory.service.PrItemInStorageService;
import com.huayu.inventory.service.PrStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
@Controller
@RequestMapping("/admin/inventory")
public class PrItemInStorageController {

    @Autowired
    private PrItemInStorageService repositoryInStorageService;

    @Autowired
    private PrStorageService prStorageService;
    @Autowired
    private PrBaseInfoService prBaseInfoService;


    @RequestMapping(value = "/inStorage/toInStorageList", method = RequestMethod.GET)
    public String toInStorageList(Model model){
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return ".inventory.storageManage.itemInStorageList";
    }

    /**
     * 点击收货进入新增入库单
     * @param ids
     * @return
     */
    @RequestMapping(value = "/inStorage/toInStorageAddPage",method = RequestMethod.POST)
    public String toInStorageAddPage(Integer[] ids, Model model){
        if (ids.length==0){
            throw new BusinessException("参数错误");
        }
        List<PrItemDelivery> list = repositoryInStorageService.getItemDeliveryByIds(ids);
        List<PrItemInStorage> PrItemInStorages = repositoryInStorageService.getInStorages();
        model.addAttribute("repositoryPersonId",list.get(0).getInStoreUser());
        model.addAttribute("PrItemInStorages",PrItemInStorages);
        model.addAttribute("person1",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),1));
        model.addAttribute("list",list);
        model.addAttribute("now",new Date());
        return ".inventory.storageManage.itemInStorageAdd";
    }

    /**
     * 提交入库
     */
    @RequestMapping(value = "/inStorage/inStorageSubmit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveTrainEmp(PrItemInStorage inStorage,String inStorageListStr){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            List<PrItemInStorageList> list = JSON.parseArray(inStorageListStr, PrItemInStorageList.class);
            Integer aLong = repositoryInStorageService.InStorageSubmit(inStorage, list);
            baseResult.setRdata(aLong);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 修改入库
     * @param inStorage
     * @return
     */
    @RequestMapping(value = "/inStorage/updateInStorage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateInStorage(PrItemInStorage inStorage){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            repositoryInStorageService.updateInStorage(inStorage);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 修改入库详情
     * @param detail
     * @return
     */
    @RequestMapping(value = "/inStorage/updateInStorageDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateInStorageDetail(PrItemInStorageList detail){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            repositoryInStorageService.updateInStorageDetail(detail);
            baseResult.setSuccess();
        } catch (BusinessException e) {
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 入库列表查询
     */
    @RequestMapping(value = "/inStorage/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listData(@RequestParam Map<String,Object> param, PrItemInStorage inStorage, PageArgs args) {
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength());
        Page<PrItemInStorage> page = repositoryInStorageService.listData(inStorage,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }
    /**
     * 查看入库详情
     */
    @RequestMapping(value = "/InStorage/itemInStorageDetail/{inStorageId}", method = RequestMethod.GET)
    public String showInStorageLists(@PathVariable Integer inStorageId, Model model) {
        List<PrItemInStorageList> list = repositoryInStorageService.showInStorageLists(inStorageId);
        PrItemInStorage inStorage = repositoryInStorageService.getInStorageByid(inStorageId);
        model.addAttribute("inStorage" ,inStorage);
        model.addAttribute("list",list);
        model.addAttribute("person1",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),1));
        return ".inventory.storageManage.itemInStorageDetail";
    }

    /**
     * 根据收货ID查询入库详情单
     */
    @RequestMapping("/inStorage/getItemInStorageList")
    @ResponseBody
    public PrItemInStorageList getItemInStorageListByDeliveryId(Integer deliveryId){
        PrItemInStorageList prItemInStorageList = repositoryInStorageService.getItemInStorageListByDeliveryId(deliveryId);
        return prItemInStorageList;
    }

    /**
     * 打印入库
     * @param inStorageListStr
     * @param inStorageStr
     * @param model
     * @return
     */
    @RequestMapping(value = "/inStorage/print/{inStorageId}" ,method = RequestMethod.POST)
    public String toPrintPage(@PathVariable Integer inStorageId,String inStorageListStr, String inStorageStr, Model model) {
        List<PrItemInStorageList> list = repositoryInStorageService.showInStorageLists(inStorageId);
        PrItemInStorage inStorage = repositoryInStorageService.getInStorageByid(inStorageId);
        model.addAttribute("inStorage" ,inStorage);
        model.addAttribute("inStoragelist",list);
        model.addAttribute("pageCount", (list.size()+4)/5);
        return "/inventory/storageManage/printInStoragePage";
    }

}
