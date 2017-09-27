package com.huayu.market.service;

import com.huayu.market.dao.MarketYearDao;
import com.huayu.market.domain.MarketYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 年度
 * @author ZXL 2017-06-27 17:26
 **/
@Service
public class MarketYearService {

    @Autowired
    private MarketYearDao marketYearDao;

    /**
     * 获取全部数据
     * @return
     */
    @Cacheable(value="org-cache",key = "'MARKETYEARSERVICE_GETALL'")
    public List<MarketYear> getAll(){
        return marketYearDao.getAll(new MarketYear());
    }

}
