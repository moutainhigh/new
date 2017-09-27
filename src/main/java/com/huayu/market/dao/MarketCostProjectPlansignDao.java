package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostProjectPlansign;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目预计签约信息
 * @author ZXL 2017-08-04 16:19
 **/
@Repository
public class MarketCostProjectPlansignDao extends BasePageDao<MarketCostProjectPlansign,Serializable> {

    /**
     * 以项目ID和年度和公司ID获取数据
     * @param marketCostProjectSign
     * @return
     */
    public MarketCostProjectPlansign getOneByProjectIdAndYear(MarketCostProjectPlansign marketCostProjectSign){
        String sql = "SELECT * FROM market_cost_project_plansign WHERE companyId=:companyId AND projectId=:projectId AND `year`=:year";
        return super.getOne(sql,marketCostProjectSign);
    }

    /**
     * 项目签约信息
     * @param marketCostProjectSign
     * @return
     */
    public List<MarketCostProjectPlansign> getToSign(MarketCostProjectPlansign marketCostProjectSign){
        String sql = "SELECT p.`name` projectName,p.companyId,p.projectId,p.type,s.signId,s.signMoney,s.signMoney01,s.signMoney02,s.signMoney03,s.signMoney04,s.signMoney05,s.signMoney06,s.signMoney07,s.signMoney08,s.signMoney09,s.signMoney10,s.signMoney11,s.signMoney12 FROM market_cost_project p LEFT JOIN (SELECT * FROM market_cost_project_plansign WHERE companyId=:i1 AND `year`=:i2) s ON p.projectId=s.projectId WHERE p.companyId=:i1 ORDER BY p.projectId ASC";
        return super.get(sql,marketCostProjectSign);
    }

    /**
     * 批量添加数据
     * @param marketCostProjectSigns
     * @return
     */
    public int[] bathPost(List<MarketCostProjectPlansign> marketCostProjectSigns){
        String sql = "INSERT INTO market_cost_project_plansign (signId,companyId,projectId,year,type,signMoney,signMoney01,signMoney02,signMoney03,signMoney04,signMoney05,signMoney06,signMoney07,signMoney08,signMoney09,signMoney10,signMoney11,signMoney12,createUserId,createDate) VALUES (:signId,:companyId,:projectId,:year,:type,:signMoney,:signMoney01,:signMoney02,:signMoney03,:signMoney04,:signMoney05,:signMoney06,:signMoney07,:signMoney08,:signMoney09,:signMoney10,:signMoney11,:signMoney12,:createUserId,:createDate)";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(marketCostProjectSigns.toArray()));
    }

    /**
     * 批量修改数据
     * @param marketCostProjectSigns
     * @return
     */
    public int[] bathPut(List<MarketCostProjectPlansign> marketCostProjectSigns){
        String sql = "UPDATE market_cost_project_plansign SET signMoney=:signMoney,signMoney01=:signMoney01,signMoney02=:signMoney02,signMoney03=:signMoney03,signMoney04=:signMoney04,signMoney05=:signMoney05,signMoney06=:signMoney06,signMoney07=:signMoney07,signMoney08=:signMoney08,signMoney09=:signMoney09,signMoney10=:signMoney10,signMoney11=:signMoney11,signMoney12=:signMoney12 WHERE signId=:signId";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(marketCostProjectSigns.toArray()));
    }

}
