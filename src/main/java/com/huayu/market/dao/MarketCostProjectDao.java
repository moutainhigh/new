package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostProject;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目
 * @author ZXL 2017-06-27 16:41
 **/
@Repository
public class MarketCostProjectDao extends BasePageDao<MarketCostProject,Serializable> {

    public MarketCostProject getOneByProject(MarketCostProject marketCostProject){
        String sql = "SELECT t.*,y.`name` companyName FROM market_cost_project t LEFT JOIN market_cost_company y ON t.companyId=y.companyId WHERE t.projectId=:projectId";
        return super.getOne(sql,marketCostProject);
    }

    /**
     * 以公司ID获取项目数据
     * @param marketCostProject
     * @return
     */
    public List<MarketCostProject> getAllByCompanyId(MarketCostProject marketCostProject){
        String sql = "SELECT * FROM market_cost_project WHERE companyId=:companyId AND `status`=2";
        return super.get(sql,marketCostProject);
    }

    /**
     * 以公司ID和年度获取项目数据
     * @param marketCostProject
     * @return
     */
    public List<MarketCostProject> getAllByCompanyIdAndYear(MarketCostProject marketCostProject){
        String sql = "SELECT p.*,c.`name` companyName,CASE WHEN p.isOwn=2 THEN '是' ELSE '否' END isOwnName FROM market_cost_project p LEFT JOIN market_cost_company c ON p.companyId=c.companyId WHERE p.companyId=:companyId AND NOT EXISTS(SELECT * FROM market_cost_project_budget b WHERE b.companyId=:companyId AND b.`year`=:year AND b.projectId=p.projectId)";
        return super.get(sql,marketCostProject);
    }

}
