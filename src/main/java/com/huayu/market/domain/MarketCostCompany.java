package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-6-27 16:18:55.
 * 所属公司
*/
@Table(name = "market_cost_company")
public class MarketCostCompany extends BaseDomain  implements Serializable{

 //private String updateFiledKey = companyId,name,companyCode;
 //private String updateFiledValue = companyId=:companyId,name=:name,companyCode=:companyCode;
    private Integer companyId;
    private String name;
    private String companyCode;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
