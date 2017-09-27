package com.huayu.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by DengPeng on 2017/3/29.
 */
//@Service
public class CommonService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private HrDictService hrDictService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
