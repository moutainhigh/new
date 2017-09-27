package com.huayu.hr.service;

import com.huayu.common.tool.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * 定时任务类
 * Created by DengPeng on 2017/3/22.
 */
@Service
public class TaskService {

    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private HrRemindService hrRemindService;

    @Autowired
    private HrStatisticsService hrStatisticsService;

    @Autowired
    private OrgService orgService;

    @Scheduled(cron="0 30 0 20 * ?")
    @Transactional
    public void disableLastData(){
        hrRemindService.disableLastData();
    }

    @Scheduled(cron="0 31 0 20 * ?")
    @Transactional
    public void remindBirthDate(){
        hrRemindService.remindBirthDate();
    }

    @Scheduled(cron="0 32 0 20 * ?")
    @Transactional
    public void remindTurnFormalDate(){
        hrRemindService.remindTurnFormalDate();
    }

    @Scheduled(cron="0 33 0 20 * ?")
    @Transactional
    public void remindContractEndDate(){
        hrRemindService.remindContractEndDate();
    }

//    @Scheduled(cron="30 32 14 * * *")
    public void updatePlaitStore(){
//        orgService.updatePlaitStore();
    }


    @Scheduled(cron="0 40 1 * * ?")
    public void statisticsAllEveryDay(){
        try {
            Date date = new Date();
            Date now = DateTimeUtil.getYesterdayEnd(date);
            hrStatisticsService.manualStatisticsAllInv(true,true,true,now);
            hrStatisticsService.manualStatisticsAllChg(null,null,now);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar.get(calendar.DAY_OF_MONTH)==1){
                hrStatisticsService.copyStatisticsData(now);
            }
        } catch (Exception e) {
            logger.error("执行定时任务(statisticsAll1)异常!",e);
        }
    }

}
