package com.huayu.user.web.args;

import com.huayu.user.domain.User;

/**
 * Created by DengPeng on 2017/5/19.
 */
public class UserArgs extends User {

    private String system;


    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
