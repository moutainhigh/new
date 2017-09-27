package com.huayu.cost.dao;


import com.huayu.cost.domain.CostApplyPay;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/4/25.
 */
@Repository
public class CostApplyPayDao extends BasePageDao<CostApplyPay,Long>{

    public Page<CostApplyPay> getCostApplyPayData(CostApplyPay costApplyPay, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT ap.*,SUM(pd.money) actualPay FROM cost_apply_pay ap ");
        sql.append(" LEFT JOIN  cost_reimbursement_pay_detail pd ON pd.payId =ap.payId and pd.status=0");
        if (null != costApplyPay.getStatus()){
            sql.append(" WHERE ap.status=:status");
        }else{
            sql.append(" WHERE ap.status in (2,4,5)");
        }
        if (StringUtils.isNotBlank(costApplyPay.getSerialsNumber())){
            sql.append(" AND  position(:serialsNumber IN ap.serialsNumber) ");
        }
        sql.append(" GROUP BY ap.payId");
        return super.get(sql.toString(),costApplyPay,pageable);
    }

    public CostApplyPay getOneDetail(CostApplyPay costApplyPay){
        StringBuilder sql = new StringBuilder("select m1.name companyName, m2.name departmentName,p.projectName,c.* from cost_apply_pay  c");
        sql.append(" left join company_map m1 on  m1.id = c.companyId");
        sql.append(" left join company_map m2 on  m2.id = c.departmentId");
        sql.append(" left join cost_project p on  p.projectId = c.projectId");
        sql.append(" where payId = :payId and status in (2,4,5)");
        return super.getOne(sql.toString(),costApplyPay);
    }

    public int updatePayStatus(CostApplyPay costApplyPay) {
        String sql = "update cost_apply_pay set status =:status,payUserId=:payUserId,payDate=:payDate where payId=:payId";
        return super.put(sql,costApplyPay);
    }
}
