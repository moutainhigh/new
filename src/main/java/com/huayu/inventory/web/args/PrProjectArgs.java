package com.huayu.inventory.web.args;

import com.huayu.inventory.domain.PrProject;

/**
 * Created by DengPeng on 2017/6/13.
 */
public class PrProjectArgs extends PrProject {

    private String authoritiesRegexp;

    public String getAuthoritiesRegexp() {
        return authoritiesRegexp;
    }

    public void setAuthoritiesRegexp(String authoritiesRegexp) {
        this.authoritiesRegexp = authoritiesRegexp;
    }

}
