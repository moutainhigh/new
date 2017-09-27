package com.huayu.hr.web.args;

import com.huayu.hr.domain.HrAtteSchedul;

/**
 * Created by DengPeng on 2017/4/5.
 */
public class HrAtteSchedulArgs extends HrAtteSchedul {

    private String empIds;

    private String cycleDetail;

    private Integer cover;

    public String getEmpIds() {
        return empIds;
    }

    public void setEmpIds(String empIds) {
        this.empIds = empIds;
    }

    public String getCycleDetail() {
        return cycleDetail;
    }

    public void setCycleDetail(String cycleDetail) {
        this.cycleDetail = cycleDetail;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }
}
