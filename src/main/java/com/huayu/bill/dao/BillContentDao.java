package com.huayu.bill.dao;

import com.huayu.bill.domain.BillContent;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Repository
public class BillContentDao  extends BasePageDao<BillContent,Serializable> {


    public BillContent getBillOne(BillContent billContent){
        String sql = "select  b.*,u.name username from bill_content b inner join user u on b.createuser = u.userId where b.id = :id";
        return super.getOne(sql,billContent);
    }

    @Override
    public int put(BillContent billContent) {
        String sql = "UPDATE bill_content SET handleUser=:handleUser, areaId=:areaId, companyId=:companyId, projectId=:projectId, stageId=:stageId, invoiceDate=:invoiceDate," +
                " targetCompany=:targetCompany, invoiceCode=:invoiceCode, invoiceNum=:invoiceNum, subject=:subject, totalMoney=:totalMoney, price=:price, tax=:tax, taxRate=:taxRate," +
                " authStatus=:authStatus, inspectStatus=:inspectStatus, inspectNote=:inspectNote, updateuser=:updateuser, updatetime=:updatetime WHERE id=:id ";
        return super.put(sql,billContent);
    }

    public int saveBillCertCode(BillContent billContent) {
        String sql = "UPDATE bill_content SET certificateCode=:certificateCode where id = :id";
        return super.put(sql,billContent);
    }

    public Page<BillContent> getBillList(User user, BillContent billContent, Pageable pageable) {
        StringBuilder sb;
        if (user.hasAnyAuth("r5","r8")){
            sb = new StringBuilder("select c.*,u.name username from bill_content c inner join user u on c.createuser = u.userId where 1=1");
        }else {
            sb = new StringBuilder("select c.*,u.name username from bill_content c inner join user u on c.createuser = u.userId where createuser = :createuser");
        }
        if (!StringUtils.isEmpty(billContent.getInvoiceNum())){
            sb.append(" and position(:invoiceNum in c.invoiceNum)");
        }

        if (null != billContent.getInvoiceStartDate()&& null != billContent.getInvoiceEndDate()){
            sb.append(" and :invoiceStartDate <= c.invoiceDate and :invoiceEndDate >= c.invoiceDate");
        }
        if (!StringUtils.isEmpty(billContent.getCertificateCode())){
            sb.append(" and position(:certificateCode in c.certificateCode)");
        }
        if (null != billContent.getAuthStatus()){
            sb.append(" and c.authStatus = :authStatus");
        }
        if (null != billContent.getInspectStatus()){
            sb.append(" and c.inspectStatus = :inspectStatus");
        }
        return super.get(sb.toString(),billContent,pageable);
    }
}
