package com.huayu.cost.dao;

import com.huayu.cost.domain.CostBalanceDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/24.
 */
@Repository
public class CostBalanceDetailDao extends BasePageDao<CostBalanceDetail,Long>{

    public List<CostBalanceDetail> getDetailList(CostBalanceDetail entity){
        String sql = "select d.*,a.serialsNumber serialsNumber from cost_balance_detail d LEFT JOIN cost_apply_pay a ON d.payId = a.payId where d.status = 2 and d.loanId=:loanId";
        return super.get(sql,entity);
    }

    public int addDetail(CostBalanceDetail detail) {
        Long key = super.getKey("cost_balance_detail",detail);
        detail.setBalanceId(key);
        return super.post(detail);
    }

    public int updateDetail(CostBalanceDetail detail) {
        String sql = "update cost_balance_detail set theMoney=:theMoney,payWay=:payWay,repayDate=:repayDate updatetime=:updatetime, updateUser=:updateUser where status=2 and loanId=:loanId";
        return super.put(sql,detail);
    }
}
