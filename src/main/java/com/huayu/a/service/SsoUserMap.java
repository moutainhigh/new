package com.huayu.a.service;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DengPeng on 2017/4/28.
 */
@Component
public class SsoUserMap {

    private Map<String,SecurityContext>  contextMap = new HashMap();
    private Map<String,String> userTicketMap = new HashMap();

    public SecurityContext getAuthen(String ticket){
        return contextMap.get(ticket);
    }

    public void addContext(String ticket,String userName, SecurityContext context){
        contextMap.put(ticket,context);
        userTicketMap.put(userName,ticket);
    }

    public String getTicket(String userName){
        return userTicketMap.get(userName);
    }

    public void clear(String ticket,String userName){
        contextMap.remove(ticket);
        userTicketMap.remove(userName);
    }
}
