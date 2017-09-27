package com.huayu.inventory.service;

import com.huayu.common.tool.DateTimeUtil;
import com.huayu.inventory.domain.PrItemStatistics;
import com.huayu.inventory.domain.PrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/7/3.
 */
@Service
public class InventoryTaskService {

    @Autowired
    private PrStorageService storageService;

    @Autowired
    private PrBaseInfoService prBaseInfoService;


    @Scheduled(cron="0 15 0 1 * ?")// 每月1号 0点15分
    @Transactional
    public void statisticsData(){
        Date now = new Date();
        PrItemStatistics entity = new PrItemStatistics();
        Date startDate  = DateTimeUtil.getFirstDateOfMonth(now);
        Date endDate  = DateTimeUtil.getLastDateOfMonth(now);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        List<PrRepository>  repositories = prBaseInfoService.getAllRepositories();
        for (PrRepository repository : repositories) {
            entity.setRepositoryId(repository.getId());
            List<PrItemStatistics> statistics = storageService.statistics(entity);
            storageService.saveStatisticsData(statistics,repository.getId(),startDate,endDate);
        }
    }

}
