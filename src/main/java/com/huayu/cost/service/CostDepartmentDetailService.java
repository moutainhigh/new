package com.huayu.cost.service;

import com.huayu.cost.dao.CostDepartmentDetailDao;
import com.huayu.cost.domain.CostDepartmentDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 部门费用预算详细表
 * @author ZXL 2017-07-07 10:15
 **/
@Service
public class CostDepartmentDetailService {

    @Autowired
    private CostDepartmentDetailDao costDepartmentDetailDao;

    /**
     * 批量添加新数据
     * @param costDepartmentDetails
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public int[] batchPost(List<CostDepartmentDetail> costDepartmentDetails) throws Exception{
        return costDepartmentDetailDao.batchPost(costDepartmentDetails);
    }

    /**
     * 创建新数据
     * @param code
     * @param companyId
     * @param departmentId
     * @param year
     * @param firstCode
     * @param secondCode
     * @param did
     * @return
     */
    public CostDepartmentDetail createDomain(String code,Long companyId,Long departmentId,Integer year,String firstCode,String secondCode,Long did){
        CostDepartmentDetail costDepartmentDetail = new CostDepartmentDetail();
        costDepartmentDetail.setDdId(costDepartmentDetailDao.getKey("cost_department_detail",costDepartmentDetail));
        costDepartmentDetail.setCode(code);
        costDepartmentDetail.setCompanyId(companyId);
        costDepartmentDetail.setDepartmentId(departmentId);
        costDepartmentDetail.setYear(year);
        costDepartmentDetail.setFirstCode(firstCode);
        costDepartmentDetail.setSecondCode(secondCode);
        costDepartmentDetail.setPlanMoney(new BigDecimal("0"));
        costDepartmentDetail.setFreezeMoney(new BigDecimal("0"));
        costDepartmentDetail.setUsedMoney(new BigDecimal("0"));
        costDepartmentDetail.setUsableMoney(new BigDecimal("0"));
        costDepartmentDetail.setCreateDate(new Date());
        costDepartmentDetail.setDid(did);
        costDepartmentDetail.setPlanMoney2(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney3(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney4(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney5(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney6(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney7(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney8(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney9(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney10(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney11(new BigDecimal("0"));
        costDepartmentDetail.setPlanMoney12(new BigDecimal("0"));
        return costDepartmentDetail;
    }

}
