package com.huayu.bill.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.bill.domain.BillContent;
import com.huayu.bill.service.BillContentService;
import com.huayu.bill.service.BillDictService;
import com.huayu.common.tool.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Controller
@RequestMapping("/admin/bill")
public class BillController {

    @Autowired
    private BillContentService billService;

    @Autowired
    private BillDictService billDicService;

    @RequestMapping(value = "/billList",method = RequestMethod.GET)
    public String billList(Model model, BillContent billContent){
        model.addAttribute("entity",billContent);
        Pageable pageable = new PageRequest(billContent.getPageNo(), billContent.getPageSize(), billContent.getDts());
        model.addAttribute("page",billService.getBillList(billContent,pageable));
        return ".admin.bill.billList";
    }

    @RequestMapping(value = "/showBillDetail/{id}",method = RequestMethod.GET)
    public String showBillDetail(Model model, @PathVariable Long id){
        BillContent billOne = billService.getBillOne(id);
        model.addAttribute("bill",billOne);
        model.addAttribute("area",billDicService.getAllDict(0));
        model.addAttribute("company",billDicService.getAllDict(1));
        model.addAttribute("project",billDicService.getAllDict(2));
        model.addAttribute("stage",billDicService.getAllDict(3));
        Map<String, Object> objectMap = billService.getUserCompany();
        model.addAttribute("userList",objectMap.get("userSet"));
        model.addAttribute("companyList",objectMap.get("companySet"));
        return ".admin.bill.billDetail";
    }

    @RequestMapping(value = "/editBill",method = RequestMethod.GET)
    public String editBill(Model model, Long id){
        model.addAttribute("id",id);//用于区分 是新增 还是编辑
        BillContent billOne;
        if (null!=id){
            billOne = billService.getBillOne(id);
        }else {
            billOne = billService.getLastBillOne();
        }
        model.addAttribute("bill",billOne);
        model.addAttribute("area",billDicService.getAllDict(0));
        if (null!=billOne){
            model.addAttribute("company",billDicService.getAllDict(1));
            model.addAttribute("project",billDicService.getAllDict(2));
            model.addAttribute("stage",billDicService.getAllDict(3));
        }else {
            model.addAttribute("oper", SpringSecurityUtil.getUser().getName());
        }
        Map<String, Object> objectMap = billService.getUserCompany();
        model.addAttribute("userList",objectMap.get("userSet"));
        model.addAttribute("companyList",objectMap.get("companySet"));
        return ".admin.bill.editBill";
    }



    @RequestMapping(value = "/saveBill",method = RequestMethod.POST)
    @ResponseBody
    public int saveBill(BillContent billContent){
        return billService.saveBill(billContent);
    }

    @RequestMapping(value = "/saveBillCertCode",method = RequestMethod.POST)
    @ResponseBody
    public int saveBillCertCode(@RequestParam Long id ,@RequestParam String code){
        return billService.saveBillCertCode(id,code);
    }

    @RequestMapping(value = "/delBillOne",method = RequestMethod.POST)
    @ResponseBody
    public int delBillOne(Long id){
        return billService.delBillOne(id);
    }

    /**
     * 获取操作人
     * @return
     */
    @RequestMapping(value = "/getUserCompany",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getUserCompany(){
        return billService.getUserCompany();
    }

    @RequestMapping(value = "/validateInvoiceNum",method = RequestMethod.POST)
    @ResponseBody
    public long validateInvoiceNum(String num){
        return billService.validateInvoiceNum(num);
    }


    /**
     * uri:/admin/tenement/order/xls
     * 商品列表数据下载
     * @param billContent
     * @param response
     */
    @RequestMapping(value = "/downloadXLS",method = RequestMethod.POST)
    public String xls(HttpServletResponse response, BillContent billContent) throws Exception {
        String fileName="发票台帐";
        String columnNames[]={"经办人","公司","项目","分期","发票日期","对方单位","发票代码","发票编号","货物及服务","价税合计","价款","税额","税率","认证情况","稽核情况","凭证号","操作员"};
        String keys[]={"handleUser","company","project","stage","invoiceDate","targetCompany","invoiceCode","invoiceNum","subject","totalMoney","price","tax","taxRate","authStatusStr","inspectStatusStr","certificateCode","username"};
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE,billContent.getDts());
        List<BillContent> objList = billService.getBillList(billContent,pageable).getContent();
        ExcelUtil.downloadExcel(response,objList, fileName, columnNames, keys);
        return null;
    }


}
