package com.huayu.cost.service;

import com.huayu.common.tool.DateTimeUtil;
import com.huayu.cost.dao.CostApplyLoanDao;
import com.huayu.cost.domain.CostApplyLoan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 费用申请
 * @author ZXL 2017-08-29 11:35
 **/
@Service
public class CostApplyLoanService {

    @Resource
    private CostApplyLoanDao costApplyLoanDao;

    /**
     * 冲账未完成统计表
     * @param pageable
     * @param costApplyLoan
     * @return
     */
    public Page<CostApplyLoan> getOutOfAccountOfReport(Pageable pageable, CostApplyLoan costApplyLoan){
        if (StringUtils.isBlank(costApplyLoan.getD3())||StringUtils.isBlank(costApplyLoan.getD4())){
            costApplyLoan.setD3(DateTimeUtil.getMinOrMaxDate(DateTimeUtil.min,DateTimeUtil.YYYY_MM_DD));
            costApplyLoan.setD4(DateTimeUtil.getMinOrMaxDate(DateTimeUtil.max,DateTimeUtil.YYYY_MM_DD));
        }
        return costApplyLoanDao.getOutOfAccountOfReport(pageable, costApplyLoan);
    }

}
