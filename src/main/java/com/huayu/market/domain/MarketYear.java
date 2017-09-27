package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.Integer;

/**
 * 年度
* Created by DengPeng on 2017-6-27 17:22:09.
*/
@Table(name = "market_year")
public class MarketYear extends BaseDomain  implements Serializable{

 //private String updateFiledKey = year;
 //private String updateFiledValue = year=:year;
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
