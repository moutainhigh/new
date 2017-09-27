package com.huayu.market.service;

import com.huayu.market.dao.MarketCostItemDao;
import com.huayu.market.domain.MarketCostItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 营销费用项目（科目）
 *
 * @author ZXL 2017-06-15 14:21
 **/
@Service
public class MarketCostItemService {

    @Autowired
    private MarketCostItemDao marketCostItemDao;

    public List<MarketCostItem> getAll(){
        return marketCostItemDao.getAll(new MarketCostItem());
    }

}
