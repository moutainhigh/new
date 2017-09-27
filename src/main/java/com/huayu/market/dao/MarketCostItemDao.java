package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostItem;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 营销费用项目（科目）
 *
 * @author ZXL 2017-06-15 14:17
 **/
@Repository
public class MarketCostItemDao extends BasePageDao<MarketCostItem,Serializable> {

    public List<MarketCostItem> getAll(MarketCostItem marketCostItem){
        String sql = "SELECT a.code,a.code2 secondCode,m3.`code` firstCode,a.`name`,a.secondName,m3.`name` firstName FROM (SELECT m1.*,m2.parentId parentId2,m2.`code` code2,m2.`level` level2,m2.`name` secondName FROM market_cost_item m1 LEFT JOIN market_cost_item m2 ON m1.parentId=m2.id WHERE m1.`level`=3) a LEFT JOIN market_cost_item m3 ON a.parentId2=m3.id";
        return super.get(sql,marketCostItem);
    }

}
