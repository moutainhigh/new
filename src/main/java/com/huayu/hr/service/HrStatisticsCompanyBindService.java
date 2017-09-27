package com.huayu.hr.service;

import com.huayu.common.BaseResult;
import com.huayu.hr.dao.HrStatisticsCompanyBindDao;
import com.huayu.hr.domain.HrStatisticsCompanyBind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DengPeng on 2017/3/16.
 */
@Service
public class HrStatisticsCompanyBindService {


    @Autowired
    private HrStatisticsCompanyBindDao bindDao;

    @Cacheable(value = "statistics-bind-cache",key = "methodName+'_'+#parentId")
    public List<HrStatisticsCompanyBind> getBaseBindData(Integer parentId) {
        HrStatisticsCompanyBind bind = new HrStatisticsCompanyBind();
        bind.setParentId(parentId);
        return bindDao.getByParentId(bind);
    }

    @CacheEvict(value = "statistics-bind-cache",allEntries=true)
    public BaseResult clearCache(){

        return BaseResult.initBaseResult().setSuccess();
    }
}
