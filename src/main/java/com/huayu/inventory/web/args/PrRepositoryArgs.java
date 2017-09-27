package com.huayu.inventory.web.args;

import com.huayu.inventory.domain.PrRepository;

/**
 * Created by DengPeng on 2017/6/12.
 */
public class PrRepositoryArgs extends PrRepository {

    private String projectName;
    private String pcode;
    private String authoritiesRegexp;
    private Integer index;


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getAuthoritiesRegexp() {
        return authoritiesRegexp;
    }

    public void setAuthoritiesRegexp(String authoritiesRegexp) {
        this.authoritiesRegexp = authoritiesRegexp;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}
