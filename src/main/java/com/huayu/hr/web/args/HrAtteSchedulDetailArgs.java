package com.huayu.hr.web.args;

import com.huayu.hr.domain.HrAtteSchedulDetail;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by DengPeng on 2017/4/10.
 */
public class HrAtteSchedulDetailArgs extends HrAtteSchedulDetail {

    private Integer company;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }
}
