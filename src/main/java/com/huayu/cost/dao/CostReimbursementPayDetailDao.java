package com.huayu.cost.dao;


import com.huayu.cost.domain.CostReimbursementPayDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/25.
 */
@Repository
public class CostReimbursementPayDetailDao extends BasePageDao<CostReimbursementPayDetail,Long>{

    public int addDetail(CostReimbursementPayDetail detail) {
        Long key = super.getKey("cost_reimbursement_pay_detail",detail);
        detail.setId(key);
        return super.post(detail);
    }

    public int updateDetail(CostReimbursementPayDetail detail) {
        String sql  = "update cost_reimbursement_pay_detail set money=:money,payWay=:payWay,payDate=:payDate where id=:id and payId=:payId ";
        return super.put(sql,detail);
    }

    public List<CostReimbursementPayDetail> getDetailList(CostReimbursementPayDetail detail){
        String sql = "select * from cost_reimbursement_pay_detail where status =0 and payId=:payId ";
        return super.get(sql,detail);
    }

    public int deleteDetail(CostReimbursementPayDetail detail) {
        String sql  = "update cost_reimbursement_pay_detail set deletetime=:deletetime, deleteUser=:deleteUser,status = -1 where payId=:payId and id=:id";
        return super.put(sql,detail);
    }

    public int unPaidMoney(CostReimbursementPayDetail detail) {
        String sql  = "update cost_contract c INNER JOIN cost_reimbursement_pay_detail d ON c.contractId =:contractId  set paidMoney = paidMoney - d.money where d.payId=:payId and d.id=:id";
        return super.put(sql,detail);
    }
}
