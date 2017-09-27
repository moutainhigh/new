package com.huayu.cost.web.args;

import com.huayu.cost.domain.CostApplyLoan;

/**
 * Created by DengPeng on 2017/4/27.
 */
public class CostApplyLoanArgs extends CostApplyLoan {

    private Integer payStatus;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
        if (null!=payStatus){
            if (payStatus==0){
                super.setStatus(2);
            }else{
                super.setStatus(4);
            }
        }
    }
}
