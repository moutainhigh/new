package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostApplyRepair;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 营销费用发生登记
 * @author ZXL 2017-09-04 11:15
 **/
@Repository
public class MarketCostApplyRepairDao extends BasePageDao<MarketCostApplyRepair,Serializable>{

    /**
     * 台账
     * 获取数据
     * @param marketCostApplyPay
     * @return
     */
    public MarketCostApplyRepair getOneOfLedger(MarketCostApplyRepair marketCostApplyRepair){
        String sql = "SELECT payId,title,applyName,applyDate,affiliationDate,remark FROM market_cost_apply_repair WHERE payId=:payId";
        return super.getOne(sql,marketCostApplyRepair);
    }

    /**
     * 台账列表
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public Page<MarketCostApplyRepair> getOfLedger(Pageable pageable, MarketCostApplyRepair marketCostApplyRepair){
        StringBuilder sql = new StringBuilder("SELECT payId,title,applyName,applyDate,affiliationDate FROM market_cost_apply_repair WHERE `status` IN(2,4,5) ");
        if (StringUtils.isNotBlank(marketCostApplyRepair.getS1())){//标题
            sql.append(" AND INSTR(title,:s1)>0 ");
        }
        if (StringUtils.isNotBlank(marketCostApplyRepair.getD3())&&StringUtils.isNotBlank(marketCostApplyRepair.getD4())) {//申请日期
            sql.append(" AND applyDate BETWEEN :d3 AND :d4 ");
        }
        return super.get(sql.toString(),marketCostApplyRepair,pageable);
    }

}
