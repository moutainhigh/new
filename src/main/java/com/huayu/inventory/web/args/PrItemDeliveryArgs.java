package com.huayu.inventory.web.args;

import com.huayu.inventory.domain.PrItemDelivery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by DengPeng on 2017/6/13.
 */
public class PrItemDeliveryArgs extends PrItemDelivery {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String mcode;;
    private String mcCode;;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }
}
