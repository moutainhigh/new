package com.huayu.cost.dao;

import com.huayu.cost.domain.CostApplyLoan;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/4/21.
 */
@Repository
public class CostApplyLoanDao extends BasePageDao<CostApplyLoan,Long>{

    /**
     * 获取申请借款列表
     * @param costApplyLoan
     * @param pageable
     * @return
     */
    public Page<CostApplyLoan> getCostApplyData(CostApplyLoan costApplyLoan, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select loanId,status,serialsNumber,remark,applyDate,applyName,");
        sql.append("case when loanMoney-repayMoney-balanceMoney  = loanMoney then  0 WHEN loanMoney - repayMoney - balanceMoney > 0 and loanMoney-repayMoney-balanceMoney < loanMoney then  1");
        sql.append(" when  loanMoney-repayMoney-balanceMoney = 0 then 2 ELSE -1 end repayStatus ");
        sql.append(",loanMoney,loanMoney-repayMoney-balanceMoney needRepayMoney from cost_apply_loan ");
        if (null != costApplyLoan.getStatus()){
            sql.append(" where status=:status");
        }else{
            sql.append(" where status in (2,4)");
        }
        if (StringUtils.isNotBlank(costApplyLoan.getSerialsNumber())){
            sql.append(" and  position(:serialsNumber in serialsNumber) ");
        }
        return super.get(sql.toString(),costApplyLoan,pageable);
    }

    /**
     * 冲账未完成统计表
     * @param pageable
     * @param costApplyLoan
     * @return
     */
    public Page<CostApplyLoan> getOutOfAccountOfReport(Pageable pageable,CostApplyLoan costApplyLoan){
        StringBuilder sql = new StringBuilder("SELECT loanId,serialsNumber,loanMoney,balanceMoney,repayMoney,loanMoney-repayMoney-balanceMoney waitbalanceMoney,applyDate,applyName,remark FROM cost_apply_loan WHERE `status` IN(2,4,5) HAVING waitbalanceMoney>0 ");
        sql.append(" AND applyDate BETWEEN :d3 AND :d4 ");
        sql.append(" ORDER BY applyDate ASC");
        return super.get(sql.toString(),costApplyLoan,pageable);
    }

    @Override
    public CostApplyLoan getOne(CostApplyLoan entity) {
        StringBuilder sql = new StringBuilder("select m1.name companyName, m2.name departmentName, f.name payWayStr, c.* from cost_apply_loan c ");
        sql.append(" left join company_map m1 on  m1.id = c.companyId");
        sql.append(" left join company_map m2 on  m2.id = c.departmentId");
        sql.append(" left join cost_select_field f on  f.id = c.payWay");
        sql.append(" where c.loanId = :loanId and (c.status =2 or c.status =4)");
        return super.getOne(sql.toString(),entity);
    }

    public int  updatePayStatus(CostApplyLoan entity){
        StringBuilder sql  = new StringBuilder("update cost_apply_loan set status = :status where loanId = :loanId ");
        return super.put(sql.toString(),entity);
    }

    public int  updateRepayMoney(CostApplyLoan entity){
        StringBuilder sql  = new StringBuilder("update cost_apply_loan set repayMoney = repayMoney+ :repayMoney, repayDate = :repayDate where loanId = :loanId ");
        return super.put(sql.toString(),entity);
    }

}
