package com.huayu.market.service;

import com.huayu.market.dao.MarketCostProjectDao;
import com.huayu.market.domain.MarketCostProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目
 * @author ZXL 2017-06-27 16:42
 **/
@Service
public class MarketCostProjectService {

    @Autowired
    private MarketCostProjectDao marketCostProjectDao;

    public MarketCostProject getOne(Long projectId){
        MarketCostProject marketCostProject = new MarketCostProject();
        marketCostProject.setProjectId(projectId);
        marketCostProject.setIdName("projectId");
        return marketCostProjectDao.getOne(marketCostProject);
    }

    public MarketCostProject getOneByProject(Long projectId){
        MarketCostProject marketCostProject = new MarketCostProject();
        marketCostProject.setProjectId(projectId);
        return marketCostProjectDao.getOneByProject(marketCostProject);
    }

    /**
     * 以公司ID获取项目数据
     * @param companyId 公司ID
     * @return
     */
    public List<MarketCostProject> getAllByCompanyId(Integer companyId){
        MarketCostProject marketCostProject = new MarketCostProject();
        marketCostProject.setCompanyId(companyId);
        return marketCostProjectDao.getAllByCompanyId(marketCostProject);
    }

    /**
     * 以公司ID和年度获取项目数据
     * @param companyId
     * @return
     */
    public List<MarketCostProject> getAllByCompanyIdAndYear(Integer companyId, Integer year){
        MarketCostProject marketCostProject = new MarketCostProject();
        marketCostProject.setCompanyId(companyId);
        marketCostProject.setYear(year);
        return marketCostProjectDao.getAllByCompanyIdAndYear(marketCostProject);
    }



}
