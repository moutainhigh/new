package com.huayu.cost.dao;


import com.huayu.cost.domain.CostContract;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/4/25.
 */
@Repository
public class CostContractDao extends BasePageDao<CostContract,Long>{

    public int updatePaidMoney(CostContract costContract){
        String sql  = "update cost_contract set paidMoney=paidMoney + :paidMoney,payTime=:payTime,payUser=:payUser where contractId=:contractId";
        return super.put(sql,costContract);
    }
}
