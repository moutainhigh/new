package com.huayu.market.service;

import com.huayu.market.dao.MarketCostCompanyDao;
import com.huayu.market.domain.MarketCostCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 所属公司
 *
 * @author ZXL 2017-06-27 16:39
 **/
@Service
public class MarketCostCompanyService {

    @Autowired
    private MarketCostCompanyDao marketCostCompanyDao;

    @Cacheable(value="org-cache",key = "'MARKETCOSTCOMPANYSERVICE_GETONE_'+#companyId")
    public MarketCostCompany getOne(Integer companyId){
        MarketCostCompany marketCostCompany = new MarketCostCompany();
        marketCostCompany.setCompanyId(companyId);
        marketCostCompany.setIdName("companyId");
        return marketCostCompanyDao.getOne(marketCostCompany);
    }

    /**
     * 获取全部数据
     * @return
     */
    @Cacheable(value="org-cache",key = "'MARKETCOSTCOMPANYSERVICE_GETALL'")
    public List<MarketCostCompany> getAll(){
        return marketCostCompanyDao.getAll(new MarketCostCompany());
    }

}
