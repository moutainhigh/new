package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostApplyPay;
import com.huayu.p.util.CommonUtil;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 营销费用报销申请
 * @author ZXL 2017-07-19 10:25
 **/
@Repository
public class MarketCostApplyPayDao extends BasePageDao<MarketCostApplyPay,Serializable>{

    /**
     * 台账
     * 获取数据
     * @param marketCostApplyPay
     * @return
     */
    public MarketCostApplyPay getOneOfLedger(MarketCostApplyPay marketCostApplyPay){
        String sql = "SELECT y.*,t.`name` projectName FROM (SELECT * FROM market_cost_apply_pay WHERE payId=:payId) y LEFT JOIN market_cost_project t ON y.projectId=t.projectId";
        return super.getOne(sql,marketCostApplyPay);
    }

    /**
     * 台账列表
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public Page<MarketCostApplyPay> getOfLedger(Pageable pageable, MarketCostApplyPay marketCostApplyPay){
        StringBuilder sql = new StringBuilder("SELECT t.*,a.companyName,a.projectName FROM (SELECT payId,type,serialsNumber,title,applyName,applyDate,applyMoney,projectId,`status` FROM market_cost_apply_pay WHERE type IN(3,4) AND `status` IN(2,4,5) ");
        if (StringUtils.isNotBlank(marketCostApplyPay.getS1())){//标题/流水号
            sql.append(" AND (INSTR(serialsNumber,:s1)>0 OR INSTR(title,:s1)>0) ");
        }
        if (StringUtils.isNotBlank(marketCostApplyPay.getD3())&&StringUtils.isNotBlank(marketCostApplyPay.getD4())) {//申请日期
            sql.append(" AND applyDate BETWEEN :d3 AND :d4 ");
        }
        if (CommonUtil.isInt(marketCostApplyPay.getI1())){
            sql.append(" AND type=:i1 ");
        }
        sql.append(") t LEFT JOIN (SELECT t.projectId,t.`name` projectName,y.`name` companyName FROM market_cost_project t LEFT JOIN market_cost_company y ON t.companyId=y.companyId) a ON t.projectId=a.projectId");
        return super.get(sql.toString(),marketCostApplyPay,pageable);
    }

    /**
     * 分页获取营销费用
     * @param marketCostApplyPay
     * @param pageable
     * @return
     */
    public Page<MarketCostApplyPay> get(MarketCostApplyPay marketCostApplyPay, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT ap.*,SUM(IFNULL(pd.money,0.00)) actualPay FROM market_cost_apply_pay ap ");
        sql.append(" LEFT JOIN  cost_reimbursement_pay_detail pd ON pd.payId =ap.payId and pd.status=0 ");
        if (CommonUtil.isInt(marketCostApplyPay.getI1())){
            sql.append(" WHERE ap.status=:i1");
        }else{
            sql.append(" WHERE ap.status in (2,4,5)");
        }
        if (StringUtils.isNotBlank(marketCostApplyPay.getS1())){
            sql.append(" AND INSTR(ap.serialsNumber,:s1)>0 ");
        }
        sql.append(" GROUP BY ap.payId");
        return super.get(sql.toString(),marketCostApplyPay,pageable);
    }

    /**
     * 获取付款详情
     * @param marketCostApplyPay
     * @return
     */
    public MarketCostApplyPay getOneToDetail(MarketCostApplyPay marketCostApplyPay){
        String sql = "SELECT p.*,m.`name` departmentName,m2.`name` companyName,t.`name` projectName,u.`name` unitName,y.`name` projectCompanyName FROM market_cost_apply_pay p " +
                "LEFT JOIN company_map m ON p.departmentId=m.id " +
                "LEFT JOIN company_map m2 ON m.parentId=m2.id " +
                "LEFT JOIN market_cost_project t ON p.projectId=t.projectId " +
                "LEFT JOIN market_cost_company y ON t.companyId=y.companyId " +
                "LEFT JOIN cost_accounting_unit u ON p.accountUnitId=u.uid WHERE p.payId=:payId";
        return super.getOne(sql,marketCostApplyPay);
    }

    /**
     * 修改付款状态
     * @param marketCostApplyPay
     * @return
     */
    public int updatePayStatus(MarketCostApplyPay marketCostApplyPay) {
        String sql = "UPDATE market_cost_apply_pay SET status =:status,payUserId=:payUserId,payDate=:payDate WHERE payId=:payId";
        return super.put(sql,marketCostApplyPay);
    }

}
