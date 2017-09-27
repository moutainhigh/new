package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostCompany;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 所属公司
 * @author ZXL 2017-06-27 16:33
 **/
@Repository
public class MarketCostCompanyDao extends BasePageDao<MarketCostCompany,Serializable> {

    /**
     * 获取全部数据
     * @param marketCostCompany
     * @return
     */
    public List<MarketCostCompany> getAll(MarketCostCompany marketCostCompany){
        String sql = "SELECT * FROM market_cost_company";
        return super.get(sql,marketCostCompany);
    }

}
