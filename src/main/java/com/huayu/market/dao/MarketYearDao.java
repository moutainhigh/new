package com.huayu.market.dao;

import com.huayu.market.domain.MarketYear;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 年度
 * @author ZXL 2017-06-27 17:23
 **/
@Repository
public class MarketYearDao extends BasePageDao<MarketYear,Serializable> {

    /**
     * 获取全部数据
     * @param marketYear
     * @return
     */
    public List<MarketYear> getAll(MarketYear marketYear){
        String sql = "SELECT * FROM market_year";
        return super.get(sql,marketYear);
    }

}
