package com.huayu.inventory.web.args;

import com.huayu.inventory.domain.PrMaterial;

/**
 * Created by Administrator on 2017/5/2.
 */
public class PrMaterialArgs extends PrMaterial {

    private String historyPriceTime;
    private String mcCode;
    private String specificationArrayStr;
    private String[] ids;


    public String getHistoryPriceTime() {
        return historyPriceTime;
    }

    public void setHistoryPriceTime(String historyPriceTime) {
        this.historyPriceTime = historyPriceTime;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getSpecificationArrayStr() {
        return specificationArrayStr;
    }

    public void setSpecificationArrayStr(String specificationArrayStr) {
        this.specificationArrayStr = specificationArrayStr;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
