package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostApproval;
import com.huayu.p.util.CommonUtil;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 月度立项审批单
 * @author ZXL 2017-07-03 13:06
 **/
@Repository
public class MarketCostApprovalDao extends BasePageDao<MarketCostApproval,Serializable>{

    /**
     * 获取
     * @param pageable
     * @param marketCostApproval
     * @return
     */
    public Page<MarketCostApproval> get(Pageable pageable, MarketCostApproval marketCostApproval){
        StringBuilder sql = new StringBuilder("SELECT c.`name` companyName,p.`name` projectName,a.* FROM market_cost_approval a LEFT JOIN market_cost_company c ON a.companyId=c.companyId LEFT JOIN market_cost_project p ON a.projectId=p.projectId WHERE 1=1 ");
        if (CommonUtil.isInt(marketCostApproval.getI1())){//状态 1申请中 2已完成 3已撤销
            sql.append(" AND a.`status` =:i1");
        }
        if (CommonUtil.isInt(marketCostApproval.getI2())){//公司ID
            sql.append(" AND a.companyId =:i2");
        }
        if (CommonUtil.isLong(marketCostApproval.getL1())){//项目ID
            sql.append(" AND a.projectId =:l1");
        }
        if (StringUtils.isNotBlank(marketCostApproval.getD3())&&StringUtils.isNotBlank(marketCostApproval.getD4())) {//创建时间
            sql.append(" AND a.createDate BETWEEN :d3 AND :d4 ");
        }
        return super.get(sql.toString(),marketCostApproval,pageable);
    }

    /**
     * 获取金额未用完立项
     * @return
     */
    public List<MarketCostApproval> getToTimingOfApproval(){
        String sql = "SELECT * FROM market_cost_approval WHERE `status`=2 AND belongMonth=DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 MONTH),'%Y-%m') AND isRestore=1 AND freezeMoney=0 AND finalMoney>usedMoney";
        return super.get(sql,new MarketCostApproval());
    }

    /**
     * 修改未用完立项为已恢复
     * @param marketCostApproval
     * @return
     */
    public int putToTimingOfApproval(MarketCostApproval marketCostApproval){
        String sql = "UPDATE market_cost_approval SET isRestore=2 WHERE pid=:pid AND isRestore=1";
        return super.put(sql,marketCostApproval);
    }

}
