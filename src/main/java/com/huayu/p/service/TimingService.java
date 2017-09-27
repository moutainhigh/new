package com.huayu.p.service;

import com.huayu.a.service.CommConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * 定时任务
 * @author ZXL 2017-05-27 15:42
 **/
@Service
public class TimingService {

    @Autowired
    private CommConfigService commConfigService;
    @Autowired
    private ProjectPlanCompileService projectPlanCompileService;

    /**
     * 预警发送短信
     */
    @Scheduled(cron="0 0 9 * * ?")
    public void projectPlanWarning(){
        if(("1").equals(commConfigService.getVal("sms_send_open"))){//发送短信控制1启动，0关闭
            ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
            Map<String, String> commConfig = (Map<String, String>) servletContext.getAttribute("commConfig");
            Integer projectPlanWarnDay = NumberUtils.toInt(StringUtils.trim(commConfig.get("projectplanwarnday")));
            projectPlanCompileService.getOfStartDateToSms(projectPlanWarnDay);
            projectPlanCompileService.getOfEndDateToSms(projectPlanWarnDay);
        }
    }


}
