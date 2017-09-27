package com.huayu.bill.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.bill.dao.BillContentDao;
import com.huayu.bill.domain.BillContent;
import com.huayu.bill.domain.BillDic;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by DengPeng on 2016/11/16.
 */
@Service
public class BillContentService {


    @Autowired
    private BillContentDao billContentDao;

    @Autowired
    private BillDictService billDictService;


    public Page<BillContent> getBillList(BillContent billContent, Pageable pageable) {
        User user = SpringSecurityUtil.getUser();
        billContent.setCreateuser(user.getUserId());
        Page<BillContent> page = billContentDao.getBillList(user,billContent, pageable);
        page.getContent().forEach(this::buildDictStr);
        return  page;
    }

    private void buildDictStr(BillContent content){
        BillDic area = billDictService.getDictOne(content.getAreaId());
        content.setArea(area.getValue());
        BillDic company = billDictService.getDictOne(content.getCompanyId());
        content.setCompany(company.getValue());
        if(null!=content.getProjectId()){
            BillDic project = billDictService.getDictOne(content.getProjectId());
            content.setProject(project.getValue());
        }
       if (null!=content.getStageId()){
           BillDic stage = billDictService.getDictOne(content.getStageId());
           content.setStage(stage.getValue());
       }
    }


    public int saveBill(BillContent billContent) {
        int result ;
        if (null == billContent.getId()){
            billContent.setCreateuser(SpringSecurityUtil.getUser().getUserId());
            billContent.setId(billContentDao.getKey("bill_content",billContent));
            billContent.setCreatetime(new Date());
            result = billContentDao.post(billContent);
        }else {
            billContent.setUpdateuser(SpringSecurityUtil.getUser().getUserId());
            billContent.setUpdatetime(new Date());
            result = billContentDao.put(billContent);
        }
        return result;
    }


    public BillContent getBillOne(Long id) {
        BillContent billContent = new BillContent();
        billContent.setId(id);
        return billContentDao.getBillOne(billContent);
    }

    public int saveBillCertCode(Long id ,String code) {
        BillContent billContent = new BillContent();
        billContent.setId(id);
        billContent.setCertificateCode(code);
        return billContentDao.saveBillCertCode(billContent);
    }

    public int delBillOne(Long id) {
        BillContent billContent = new BillContent();
        billContent.setId(id);
        billContent.setIdName("id");
        return billContentDao.delete(billContent);
    }

    public Map<String,Object> getUserCompany() {
        Map<String,Object> map = new HashMap();
        List<BillContent> billContents = billContentDao.get("select DISTINCT handleUser,targetCompany from bill_content", new BillContent());
        Set<String> userSet = new HashSet<>(billContents.size());
        Set<String> companySet = new HashSet<>(billContents.size());
        for(BillContent content : billContents){
            companySet.add(content.getTargetCompany());
            userSet.add(content.getHandleUser());
        }
        map.put("userSet",userSet);
        map.put("companySet",companySet);
        return map;
    }

    public long validateInvoiceNum(String num) {
        String sql = "select count(0) from bill_content where invoiceNum=:invoiceNum";
        BillContent billContent = new BillContent();
        billContent.setInvoiceNum(num);
        return  billContentDao.getLong(sql, billContent);
    }

    /**
     * 查询当前用户 最后一条添加的数据
     * @return
     */
    public BillContent getLastBillOne() {
        BillContent billContent = new BillContent();
        billContent.setCreateuser(SpringSecurityUtil.getUser().getUserId());
        String sql = "SELECT * from bill_content where id = (SELECT max(id) FROM bill_content WHERE createuser = :createuser)";
        BillContent billOne = billContentDao.getOne(sql, billContent);
        if (null!=billOne){
            billOne.setUsername(SpringSecurityUtil.getUser().getName());
        }
        return billOne;
    }
}
