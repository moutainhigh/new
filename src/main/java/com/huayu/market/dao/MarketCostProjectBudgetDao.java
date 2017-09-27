package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostProjectBudget;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目明细
 *
 * @author ZXL 2017-06-15 14:18
 **/
@Repository
public class MarketCostProjectBudgetDao extends BasePageDao<MarketCostProjectBudget,Serializable> {

    /**
     * 条件获取预算数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public Page<MarketCostProjectBudget> getToBudget(Pageable pageable, MarketCostProjectBudget marketCostProjectBudget){
        String sql = "SELECT b.*,p.`name` projectName,CASE WHEN p.isOwn=2 THEN '是' ELSE '否' END isOwnName,p.isOwn FROM market_cost_project_budget b LEFT JOIN market_cost_project p ON b.projectId=p.projectId WHERE b.`status`=2 AND b.companyId=:i1 AND b.`year`=:i2";
        return super.get(sql,marketCostProjectBudget,pageable);
    }

    /**
     * 条件获取预算数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public List<MarketCostProjectBudget> getToBudget(MarketCostProjectBudget marketCostProjectBudget){
        String sql = "SELECT b.*,p.`name` projectName,CASE WHEN p.isOwn=2 THEN '是' ELSE '否' END isOwnName,p.isOwn FROM market_cost_project_budget b LEFT JOIN market_cost_project p ON b.projectId=p.projectId WHERE b.`status`=2 AND b.companyId=:i1 AND b.`year`=:i2";
        return super.get(sql,marketCostProjectBudget);
    }

    /**
     * 条件获取实际发生数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public List<MarketCostProjectBudget> getToHappen(MarketCostProjectBudget marketCostProjectBudget){
        String sql = "SELECT b.*,p.`name` projectName FROM market_cost_project_budget b LEFT JOIN market_cost_project p ON b.projectId=p.projectId WHERE b.`status`=2 AND b.companyId=:i1 AND b.`year`=:i2 ORDER BY b.projectId ASC";
        return super.get(sql,marketCostProjectBudget);
    }

    /**
     * 条件获取费效分析数据
     * @param pageable
     * @param marketCostProjectBudget
     * @return
     */
    public List<MarketCostProjectBudget> getToEfficiency(MarketCostProjectBudget marketCostProjectBudget){
        String sql = "SELECT p.`name` projectName,s.signMoney,IFNULL(b.happenMoney,0) happenMoney FROM (SELECT * FROM market_cost_project_sign WHERE companyId=:i1 AND `year`=:i2) s LEFT JOIN (SELECT * FROM market_cost_project_budget WHERE companyId=:i1 AND `year`=:i2) b ON s.projectId=b.projectId LEFT JOIN market_cost_project p ON s.projectId=p.projectId";
        return super.get(sql,marketCostProjectBudget);
    }

    /**
     * 获取是否已有数据
     * @param marketCostProjectBudget
     * @return
     */
    public MarketCostProjectBudget getOneByIdAndYearAndCompanyId(MarketCostProjectBudget marketCostProjectBudget){
        String sql = "SELECT * FROM market_cost_project_budget WHERE companyId=:companyId AND `year`=:year AND projectId=:projectId";
        return super.getOne(sql,marketCostProjectBudget);
    }

    /**
     * 编制预算修改项目明细预算
     * @param marketCostProjectBudget
     * @return
     */
    public int addDefaultMoney(MarketCostProjectBudget marketCostProjectBudget){
        String sql = "UPDATE market_cost_project_budget SET currentMoney=currentMoney+:defaultMoney,defaultMoney=defaultMoney+:defaultMoney WHERE pbId=:pbId";
        return super.put(sql,marketCostProjectBudget);
    }

    /**
     * 更新立项未用完数据
     * @param marketCostProjectBudget
     * @return
     */
    public int putToTimingOfApproval(MarketCostProjectBudget marketCostProjectBudget){
        String sql = "UPDATE market_cost_project_budget SET usedMoney=usedMoney-:restoreMoney,restoreMoney=restoreMoney+:restoreMoney WHERE pbId=:pbId";
        return super.put(sql,marketCostProjectBudget);
    }

}
