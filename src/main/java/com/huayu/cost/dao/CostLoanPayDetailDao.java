package com.huayu.cost.dao;

import com.huayu.cost.domain.CostLoanPayDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/21.
 */
@Repository
public class CostLoanPayDetailDao extends BasePageDao<CostLoanPayDetail,Long>{

    public int addDetail(CostLoanPayDetail entity){
        Long key = super.getKey("cost_loan_pay_detail",entity);
        entity.setId(key);
        return super.post(entity);
    }

    public List<CostLoanPayDetail> getDetailList(CostLoanPayDetail entity){
        String sql  = "select * from cost_loan_pay_detail where loanId=:loanId and status = 0";
        return super.get(sql,entity);
    }

    public int updateDetail(CostLoanPayDetail entity){
        String sql  = "update  cost_loan_pay_detail set  money=:money,payWay=:payWay,payDate=:payDate,updatetime=:updatetime,updateUser=:updateUser where id=:id and loanId=:loanId and status = 0";
        return super.put(sql,entity);
    }

    public int deleteDetail(CostLoanPayDetail entity) {
        String sql  = "update cost_loan_pay_detail set deletetime=:deletetime, deleteUser=:deleteUser,status = -1 where id=:id and loanId=:loanId and status = 0";
        return super.put(sql,entity);
    }

    public int cancelDetailPayStatus(CostLoanPayDetail entity){
        String sql  = "update cost_loan_pay_detail set revokedtime=:revokedtime, revokedUser=:revokedUser,status = 1 where loanId=:loanId and status = 0";
        return super.put(sql,entity);
    }

}
