package com.huayu.cost.web.args;

import com.huayu.cost.domain.CostApplyPay;

/**
 * Created by DengPeng on 2017/4/27.
 */
public class CostApplyPayArgs extends CostApplyPay {

    private Integer payStatus;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
        if (null!=payStatus){
            if (payStatus==0){
                super.setStatus(2);
            }else if(payStatus==1){
                super.setStatus(4);
            }else{
                super.setStatus(5);
            }
        }
    }
}
