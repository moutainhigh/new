package com.huayu.inventory.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.inventory.domain.*;
import com.huayu.inventory.domain.validate.*;
import com.huayu.inventory.service.PrBaseInfoService;
import com.huayu.inventory.service.PrItemInStorageService;
import com.huayu.inventory.service.PrMaterialService;
import com.huayu.inventory.service.PrStorageService;
import com.huayu.inventory.web.args.PrItemDeliveryArgs;
import com.huayu.inventory.web.args.PrItemRejectedArgs;
import com.huayu.inventory.web.args.PrMaterialArgs;
import com.huayu.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by DengPeng on 2017/6/13.
 */
@Controller
@RequestMapping("/admin/inventory")
public class PrStorageManageController {

    private static Logger logger = LoggerFactory.getLogger(PrStorageManageController.class);

    @Autowired
    private PrBaseInfoService prBaseInfoService;

    @Autowired
    private PrStorageService prStorageService;

    @Autowired
    private PrItemInStorageService repositoryInStorageService;

    @Autowired
    private PrMaterialService prMaterialService;

    /**
     * 收货列表
     * @param model
     * @return
     */
    @RequestMapping("/receipt/index")
    public String receiptIndex(Model model){
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return ".inventory.storageManage.receiptList";
    }


    /**
     * 收货数据
     * @param param
     * @param delivery
     * @param args
     * @return
     */
    @RequestMapping(value = "/receipt/receiptListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> receiptListData(@RequestParam Map<String,Object> param, PrItemDeliveryArgs delivery, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"d.receiptDate");//d.itemProviderName,
        Page<PrItemDelivery> page = prStorageService.receiptListData(delivery,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    /**
     * 新增收货
     * @param model
     * @return
     */
    @RequestMapping("/receipt/edit")
    public String receiptEdit(Model model){
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("receiptUsers",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),3));
        model.addAttribute("checkUsers",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),4));
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        model.addAttribute("person11",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),11));
        model.addAttribute("editAble",true);
        model.addAttribute("now",new Date());
        return ".inventory.storageManage.receiptEdit";
    }

    /**
     * 编辑收货
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/receipt/edit/{id}")
    public String receiptEdit(Model model, @PathVariable("id") Integer id,Boolean editAble){
        model.addAttribute("entity",prStorageService.getReceiptOne(id));
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("receiptUsers",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),3));
        model.addAttribute("checkUsers",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),4));
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        model.addAttribute("person11",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),11));
        model.addAttribute("editAble",null==editAble?true:editAble);
        return ".inventory.storageManage.receiptEdit";
    }

    /**
     * 查看收货
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/receipt/show/{id}")
    public String receiptShow(Model model, @PathVariable("id") Integer id){
        return receiptEdit(model,id,false);
    }

    /**
     * 收货
     * @param delivery
     * @param result
     * @return
     */
    @RequestMapping("/receipt/insert")
    @ResponseBody
    public BaseResult insertReceipt(@Validated(GroupInsert.class) PrItemDelivery delivery, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.insertReceipt(delivery);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 更新收货记录
     * @param args
     * @param result
     * @return
     */
    @RequestMapping("/receipt/update")
    @ResponseBody
    public BaseResult updateReceipt(@Validated(GroupDelete.class) PrItemDeliveryArgs args, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.updateReceipt(args);
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
     * 删除收货记录
     * @param args
     * @param result
     * @return
     */
    @RequestMapping(value = "/receipt/deleteReceipt",method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public BaseResult deleteReceipt(@Validated(GroupDelete.class) PrItemDeliveryArgs args, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try{
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.deleteReceipt(args);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }


    /**
     * 已收货数据 (加出库数量)
     * @param param
     * @param delivery
     * @param args
     * @return
     */
    @RequestMapping(value = "/receipt/receiptMaterialListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> receiptMaterialListData(@RequestParam Map<String,Object> param, PrItemDelivery delivery, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"d.receiptDate");
        Page<PrItemDelivery> page = prStorageService.receiptDeliveryListData(delivery,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    /**
     * 弹窗  获取已经入库的材料
     * @param model
     * @return
     */
    @RequestMapping(value = "/inStorage/getReceiptedMaterial",method = RequestMethod.GET)
    public String getReceiptedMaterial(Model model){
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("person1",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),1));
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        return "/inventory/storageManage/receiptedMaterial";
    }

    /**
     * 暂时没用
     * 获取已经入库材料
     * @param param
     * @param materialArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/inStorage/getInStorageMaterialListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getInStorageMaterialListData(@RequestParam Map<String,Object> param, PrMaterialArgs materialArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"m.createtime");
        Page<PrMaterial> page = prMaterialService.getInStorageMaterialListData(materialArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 到出库列表页面
     * @param model
     * @return
     */
    @RequestMapping("/outStorage/index")
    public String toOutStorageList(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("person1",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),1));
        model.addAttribute("person7",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),7));
        return ".inventory.storageManage.itemOutStorageList";
    }

    /**
     * 出库新增
     * @param model
     * @return
     */
    @RequestMapping("/outStorage/edit")
    public String outStorageEdit(Model model,PrItemDelivery delivery){
        if (null != delivery.getIds() && delivery.getIds().length>0){
            List<PrItemDelivery> deliveryList = prStorageService.receiptDeliveryListData(delivery);
            model.addAttribute("deliveryList",deliveryList);
            model.addAttribute("repositoryId",delivery.getRepositoryId());
        }
        model.addAttribute("now",new Date());
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("person1",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),1));
        model.addAttribute("person7",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),7));
        model.addAttribute("person8",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),8));
        model.addAttribute("person9",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),9));
        model.addAttribute("person12",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),12));
        User user = SpringSecurityUtil.getUser();
        List<PrProject> allProjects = prBaseInfoService.getAllProjects(user.getDataAuthoritiesRegexp());
        model.addAttribute("currProject", ConstantsHolder.getContext().getCurrDataId());
        model.addAttribute("allProjects",allProjects);
        return ".inventory.storageManage.itemOutStorageEdit";
    }

    /**
     * 出库列表
     * @param param
     * @param outStorage
     * @param args
     * @return
     */
    @RequestMapping(value = "/outStorage/listData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> outStorageListData(@RequestParam Map<String,Object> param, PrItemOutStorage outStorage, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"os.outStorageDate");
        Page<PrItemOutStorage> page = prStorageService.outStorageListData(outStorage,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 出库详情
     * @param outStorageId
     * @param model
     * @return
     */
    @RequestMapping("/outStorage/detail/{outStorageId}")
    public String showOutStorageDetail(@PathVariable Integer outStorageId, Model model) {
        PrItemOutStorage prItemOutStorage = prStorageService.getOutStorageDetail(outStorageId);
        List<PrItemOutStorageList> list = prStorageService.getOutStorageListsByOutStorageId(outStorageId);
        model.addAttribute("prItemOutStorage", prItemOutStorage);
        model.addAttribute("list", list);
        model.addAttribute("person1",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),1));
        model.addAttribute("person7",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),7));
        model.addAttribute("person8",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),8));
        model.addAttribute("person9",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),9));
        return ".inventory.storageManage.itemOutStorageDetail";
    }


    /**
     * 修改出库单
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/outStorage/updateOutStorageDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateOutStorageDetail(@Validated(GroupUpdate.class) PrItemOutStorageList entity, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.updateOutStorageDetail(entity);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 新增出库详情
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/outStorage/addOutStorageDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addOutStorageDetail(@Validated(GroupBatchUpdate.class) PrItemOutStorageList entity, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.addOutStorageDetail(entity);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }


    /**
     * 出库明细
     * @param model
     * @return
     */
    @RequestMapping("/outStorage/listDetailIndex")
    public String outStorageDetailIndex(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        return ".inventory.storageManage.outStorageDetailIndex";
    }


    /**
     * 出库明细
     * @return
     */
    @RequestMapping(value = "/outStorage/listDetailData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> outStorageDetailData(@RequestParam Map<String,Object> param, PrItemOutStorageList outStorage, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"o.outStorageDate");
        Page<PrItemOutStorageList> page = prStorageService.getOutStorageListDetailData(outStorage,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 导出出库明细
     * @param response
     * @param outStorage
     * @param args
     * @return
     */
    @RequestMapping(value = "/outStorage/exportDetailData",method = RequestMethod.POST)
    @ResponseBody
    public String exportOutStorageDetailData(HttpServletResponse response, PrItemOutStorageList outStorage, PageArgs args){
        Page<PrItemOutStorageList> page = prStorageService.getOutStorageListDetailData(outStorage,null);
        String fileName = "出库明细";
        String[] columnNames = {"出库单号","出库日期","仓库名称","项目","领用人","领用单位","栋号","用途","分类","材料名称","规格","计量单位","出库数量","出库单价","税率","税额","不含税金额","含税金额","备注"};
        String[] keys = {"outStorageNo","outStorageDate","repositoryName","projectName","itemReceiver","itemReceiverUnit","projectHouseNum","use","categoryName","materialName","specification","unit","num","price","taxRate","tax","excTaxSumPrice","sumPrice","remark"};
        try {
            ExcelUtil.downloadExcel(response, page.getContent(),fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 入库明细
     * @param model
     * @return
     */
    @RequestMapping("/inStorage/listDetailIndex")
    public String inStorageDetailIndex(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("person2",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),2));
        return ".inventory.storageManage.inStorageDetailIndex";
    }


    /**
     * 入库明细
     * @return
     */
    @RequestMapping(value = "/inStorage/listDetailData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> inStorageDetailData(@RequestParam Map<String,Object> param, PrItemInStorageList storage, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"i.inStoreDate");
        Page<PrItemInStorageList> page = prStorageService.getInStorageListDetailData(storage,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 导出 入库明细
     * @param response
     * @param storage
     * @return
     */
    @RequestMapping(value = "/inStorage/exportDetailData",method = RequestMethod.POST)
    @ResponseBody
    public String exportInStorageDetailData(HttpServletResponse response,PrItemInStorageList storage){
        Page<PrItemInStorageList> page = prStorageService.getInStorageListDetailData(storage,null);
        String fileName = "入库明细";
        String[] columnNames = {"入库单号","入库日期","库管员","仓库名称","发票号","供货商","分类","材料名称","规格","计量单位","入库数量","入库单价","税率","税额","不含税金额","含税金额","备注"};
        String[] keys = {"inStorageNum","inStoreDate","inStoreUser","repositoryName","billNum","itemProviderName","materialCategory","materialName","specification","unit","num","price","taxRate","rateMoney","moneyNoRate","inStorageMoney","remark"};
        try {
            ExcelUtil.downloadExcel(response, page.getContent(),fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取欲退货商品
     * @param deliveryId
     * @return
     */
//    @RequestMapping(value = "/rejected/edit/{deliveryId}", method = RequestMethod.GET)
    public String toRejectedPage(@PathVariable Integer deliveryId, Model model){
        PrItemDelivery prItemDelivery = prStorageService.getRejectedItem(deliveryId);
        model.addAttribute("prItemDelivery", prItemDelivery);
        model.addAttribute("person6",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),6));
        return ".inventory.storageManage.rejectedEdit";
    }

    /**
     * 提交退货
     * @param prItemRejected
     * @param result
     * @return
     */
//    @RequestMapping(value = "/rejected/insert", method = RequestMethod.POST)
//    @ResponseBody
    public BaseResult rejectedItem(@Validated(GroupInsert.class) PrItemRejected prItemRejected, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.rejectedItem(prItemRejected);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 提交出库
     * @param outStorage
     * @param result
     * @return
     */
    @RequestMapping(value = "/outStorage/post",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postOutStorage(@Validated(GroupInsert.class) PrItemOutStorage outStorage, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.postOutStorage(outStorage);
            baseResult.setRdata(outStorage.getId());
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 修改出库
     * @param outStorage
     * @param result
     * @return
     */
    @RequestMapping(value = "/outStorage/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateOutStorage(@Validated(GroupUpdate.class) PrItemOutStorage outStorage, BindingResult result){
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.updateOutStorage(outStorage);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 出库打印
     * @param model
     * @return
     */
    @RequestMapping(value = "/outStorage/print/{outStorageId}", method = RequestMethod.POST)
    public String toOutStoragePrintPage(@PathVariable Integer outStorageId, Model model) {
        PrItemOutStorage prItemOutStorage = prStorageService.getOutStorageDetail(outStorageId);
        List<PrItemOutStorageList> list = prStorageService.getOutStorageListsByOutStorageId(outStorageId);
        model.addAttribute("outStorage", prItemOutStorage);
        model.addAttribute("outStoragelist", list);
        model.addAttribute("pageCount", (list.size()+4)/5);
        return "/inventory/storageManage/printOutStoragePage";
    }

    /**
     * 跳转到退货列表
     * @return
     */
    @RequestMapping("/rejected/toRejectedListPage")
    public String toRejectedListPage() {
        return ".inventory.storageManage.rejectedList";
    }

    /**
     * 退货列表
     * @param param
     * @param itemRejected
     * @param args
     * @return
     */
    @RequestMapping(value = "/rejected/rejectedListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> rejectedListData(@RequestParam Map<String,Object> param, PrItemRejectedArgs itemRejected, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"ir.returnedDate");
        Page<PrItemRejected> page = prStorageService.rejectedListData(itemRejected,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 物资盘点
     * @return
     */
    @RequestMapping("/statistics/index")
    public String statisticsIndex(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return ".inventory.storageManage.statisticsIndex";
    }


    /**
     * 统计
     * @return
     */
    @RequestMapping(value = "/statistics/statisticsData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsData(@Validated(GroupQuery.class) PrItemStatistics entity, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            List<PrItemStatistics> statistics = prStorageService.statistics(entity);
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/statistics/print",method = RequestMethod.POST)
    public String printStatistics(Model model, @Validated(GroupQuery.class) PrItemStatistics entity, BindingResult result) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        if (result.hasErrors()){
            model.addAttribute("error", result.getFieldError().getDefaultMessage());
            return "/inventory/storageManage/printStatistics";
        }
        model.addAttribute("entity",entity);
        List<PrItemStatistics> statistics = prStorageService.statistics(entity);
        model.addAttribute("statisticsData",statistics);
        model.addAttribute("pageCount", (statistics.size()+19)/20);
        return "/inventory/storageManage/printStatistics";
    }

    /**
     * 收货未入库首页
     * @return
     */
    @RequestMapping("/receiptStatistics/index")
    public String receiptStatistics(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return ".inventory.storageManage.receiptStatistics";
    }

    /**
     * 统计收货未入库
     * @return
     */
    @RequestMapping(value = "/receiptStatistics/statisticsData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult receiptStatisticsData(PrItemDeliveryArgs entity) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            List<PrItemDelivery> list = prStorageService.receiptStatistics(entity, ConstantsHolder.getContext().getCurrDataId());
            baseResult.setRdata(list);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出收货未入库详情
     * @param response
     * @return
     */
    @RequestMapping("/receiptStatistics/exportExcel")
    @ResponseBody
    public String exportExcel(HttpServletResponse response, PrItemDeliveryArgs entity) {
        List<PrItemDelivery> list = prStorageService.receiptStatistics(entity, ConstantsHolder.getContext().getCurrDataId());
        List<PrItemDelivery> prItemDeliveryList = new ArrayList<>();
        prItemDeliveryList.add(list.get(0));

        BigDecimal num = list.get(0).getNum();
        BigDecimal excTaxSumPrice = list.get(0).getExcTaxSumPrice();
        BigDecimal sumPrice = list.get(0).getSumPrice();
        BigDecimal numAll = list.get(0).getNum();
        BigDecimal excTaxSumPriceAll = list.get(0).getExcTaxSumPrice();
        BigDecimal sumPriceAll = list.get(0).getSumPrice();

        for (int i = 1;i < list.size();i++) {
            String itemProviderName = list.get(i).getItemProviderName();
            if (itemProviderName.equals(list.get(i-1).getItemProviderName())) {
                prItemDeliveryList.add(list.get(i));
                //累加数据
                num = num.add(list.get(i).getNum());
                excTaxSumPrice = excTaxSumPrice.add(list.get(i).getExcTaxSumPrice());
                sumPrice = sumPrice.add(list.get(i).getSumPrice());
            } else {
                //追加一条记录到prItemDeliveryList  并设置小结数据

                PrItemDelivery prItemDelivery = new PrItemDelivery();
                prItemDelivery.setItemProviderName("小计");

                prItemDelivery.setNum(num);
                prItemDelivery.setExcTaxSumPrice(excTaxSumPrice);
                prItemDelivery.setSumPrice(sumPrice);
                num = list.get(i).getNum();
                excTaxSumPrice = list.get(i).getExcTaxSumPrice();
                sumPrice = list.get(i).getSumPrice();
                prItemDeliveryList.add(prItemDelivery);
                prItemDeliveryList.add(list.get(i));
            }
            //累加累计数据
            numAll = numAll.add(list.get(i).getNum());
            excTaxSumPriceAll = excTaxSumPriceAll.add(list.get(i).getExcTaxSumPrice());
            sumPriceAll = sumPriceAll.add(list.get(i).getSumPrice());
        }

        PrItemDelivery delivery = new PrItemDelivery();
        delivery.setItemProviderName("总计");
        delivery.setNum(numAll);
        delivery.setExcTaxSumPrice(excTaxSumPriceAll);
        delivery.setSumPrice(sumPriceAll);
        prItemDeliveryList.add(delivery);

        String fileName = "收货未入库明细表";
        String[] columnNames = {"供货单位","收货日期","材料名称","规格","计量单位","收货数量","收货单价","收货税率","收货不含税金额","收货含税金额"};

        String[] keys = {"itemProviderName","receiptDate","materialName","specification","unit","num","price","taxRate","excTaxSumPrice","sumPrice"};
        try {
            ExcelUtil.downloadExcel(response, prItemDeliveryList,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 金额调平首页
     * @param model
     * @return
     */
    @RequestMapping("/statistics/balanceEdit")
    public String balanceEdit(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return ".inventory.storageManage.balanceEdit";
    }

    @RequestMapping("/statistics/balanceEdit/{id}")
    public String balanceEdit(Model model,@PathVariable Integer id) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("entity",prStorageService.balanceOne(id));
        return ".inventory.storageManage.balanceEdit";
    }

    /**
     * 金额调平
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/statistics/insertBalance",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertBalance(@Validated(GroupInsert.class) PrItemBalance entity, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.insertBalance(entity);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 调教页面
     * @param model
     * @return
     */
    @RequestMapping("/statistics/balanceIndex")
    public String balanceIndex(Model model) {
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        return ".inventory.storageManage.balanceList";
    }

    /**
     * 调价数据
     * @param param
     * @param entity
     * @param args
     * @return
     */
    @RequestMapping(value = "/statistics/balanceListData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> balanceListData(@RequestParam Map<String,Object> param, PrItemBalance entity, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength());
        Page<PrItemBalance> page = prStorageService.balanceListData(entity,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 调价
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/statistics/updateBalance",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateBalance(@Validated(GroupUpdate.class) PrItemBalance entity, BindingResult result) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (result.hasErrors()){
                baseResult.setRmsg( result.getFieldError().getDefaultMessage());
                return baseResult;
            }
            prStorageService.updateBalance(entity);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 收票页面
     * @param model
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/toReceiveBill/detailIndex/{type}/{id}")
    public String receiveDetail(Model model,@PathVariable("type") String type,@PathVariable("id") Integer id) {
        model.addAttribute("id",id);
        model.addAttribute("person",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),10));
        model.addAttribute("now",new Date());
        if ("1".equals(type)){
            List<PrItemOutStorageList> list = prStorageService.getOutStorageListsByOutStorageId(id);
            model.addAttribute("entity", prStorageService.getOutStorageDetail(id));
            model.addAttribute("list", list);
            return "/inventory/storageManage/outStorageReceiveBill";
        }else{
            List<PrItemInStorageList> list = repositoryInStorageService.showInStorageLists(id);
            model.addAttribute("entity", repositoryInStorageService.getInStorageByid(id));
            model.addAttribute("list", list);
            return "/inventory/storageManage/inStorageReceiveBill";
        }
    }

    /**
     * 保存收票信息
     * @param outStorage
     * @return
     */
    @RequestMapping(value = "/toReceiveBill/saveBillReceiveInfo/{type}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveBillReceiveInfo(PrItemOutStorage outStorage, PrItemInStorage prItemInStorage, @PathVariable("type") Integer type) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            if (outStorage.getId()==null){
                baseResult.setRmsg("参数错误");
                return baseResult;
            }
            prStorageService.saveBillReceiveInfo(outStorage,prItemInStorage,type);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 收票列表
     * @param model
     * @return
     */
    @RequestMapping("/receiveBill/detailIndex")
    public String detailIndex(Model model) {
        model.addAttribute("person",prStorageService.getAllPerson(ConstantsHolder.getContext().getCurrDataId(),10));
        model.addAttribute("repositories",prBaseInfoService.getAllRepositories(ConstantsHolder.getContext().getCurrDataId()));
        model.addAttribute("startDate", DateTimeUtil.getFirstDateOfMonth(new Date()));
        model.addAttribute("endDate", DateTimeUtil.getLastDateOfMonth(new Date()));
        return ".inventory.storageManage.receiveBill";
    }

    /**
     * 收票列表数据
     * @return
     */
    @RequestMapping(value = "/receiveBill/detailListData", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult detailListData(PrItemDelivery delivery){
        BaseResult baseResult = prStorageService.receiveBillListData(delivery);
        return baseResult;
    }

    /**
     * 打印收票
     * @param model
     * @param delivery
     * @return
     */
    @RequestMapping("/receiveBill/printDetail")
    public String printDetail(Model model, PrItemDelivery delivery) {
        if (null==delivery.getRepositoryId()||null==delivery.getBillReceiveDate()){
            model.addAttribute("dataList",Collections.emptyList());
        }else{
            model.addAttribute("repositoryName",delivery.getRepositoryName());
            model.addAttribute("billReceiveDate",delivery.getBillReceiveDate());
            BaseResult baseResult = prStorageService.receiveBillListData(delivery);
            List<PrItemDelivery> outBillListData = (List<PrItemDelivery>) baseResult.getRdataMapValue("outBillListData");
            List<PrItemDelivery> inBillListData = (List<PrItemDelivery>) baseResult.getRdataMapValue("inBillListData");
            List<Map<String,PrItemDelivery>> list = new ArrayList<>();
            if (outBillListData.size()>inBillListData.size()){
                for (int i = 0; i<outBillListData.size(); i++){
                    PrItemDelivery prItemDelivery = outBillListData.get(i);
                    Map<String,PrItemDelivery> data = new HashMap<>();
                    data.put("outBillListData",prItemDelivery);
                    if (i<inBillListData.size()){
                        data.put("inBillListData",inBillListData.get(i));
                    }
                    list.add(data);
                }
            }else{
                for (int i = 0; i<inBillListData.size(); i++){
                    PrItemDelivery prItemDelivery = inBillListData.get(i);
                    Map<String,PrItemDelivery> data = new HashMap<>();
                    data.put("outBillListData",prItemDelivery);
                    if (i<outBillListData.size()){
                        data.put("inBillListData",outBillListData.get(i));
                    }
                    list.add(data);
                }
            }
            model.addAttribute("pageCount", (list.size()+64)/65);
            model.addAttribute("listData",list);
        }
        return "/inventory/storageManage/printReceiveBillDetail";
    }

}
