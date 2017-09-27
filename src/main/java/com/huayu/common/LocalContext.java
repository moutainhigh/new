package com.huayu.common;

/**
 * Created by DengPeng on 2017/5/19.
 */
public class LocalContext {

    private Integer currDataId;
    private String currDataValue;
    private String currSystem;


    public LocalContext() {
    }

    public LocalContext(Integer currDataId, String currSystem) {
        this.currDataId = currDataId;
        this.currSystem = currSystem;
    }

    public Integer getCurrDataId() {
        return currDataId;
    }

    public void setCurrDataId(Integer currDataId) {
        this.currDataId = currDataId;
    }

    public String getCurrSystem() {
        return currSystem;
    }

    public void setCurrSystem(String currSystem) {
        this.currSystem = currSystem;
    }

    public String getCurrDataValue() {
        return currDataValue;
    }

    public void setCurrDataValue(String currDataValue) {
        this.currDataValue = currDataValue;
    }
}
