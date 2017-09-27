package com.huayu.cost.service;

import com.huayu.cost.dao.CostDepartmentDao;
import com.huayu.cost.domain.CostDepartment;
import com.huayu.cost.domain.CostDepartmentDetail;
import com.huayu.cost.domain.CostItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门费用预算主表
 * @author ZXL 2017-07-07 9:56
 **/
@Service
public class CostDepartmentService {

    @Autowired
    private CostDepartmentDao costDepartmentDao;
    @Autowired
    private CostDepartmentDetailService costDepartmentDetailService;
    @Autowired
    private CostItemService costItemService;

    /**
     * 以年度为条件获取数据
     * @param year 年度
     * @return
     */
    public List<CostDepartment> getAllDateByYear(Integer year){
        CostDepartment costDepartment = new CostDepartment();
        costDepartment.setYear(year);
        return costDepartmentDao.getAllDateByYear(costDepartment);
    }

    /**
     * 初始化数据
     * @param costDepartment
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public int initialize(CostDepartment costDepartment) throws Exception{
        this.createDomain(costDepartment);
        List<CostDepartmentDetail> costDepartmentDetailList = new ArrayList<>();
        List<CostItem> costItemList = costItemService.getDateToInitialize();
        for (CostItem costItem : costItemList) {
            costDepartmentDetailList.add(costDepartmentDetailService.createDomain(costItem.getCode(),costDepartment.getCompanyId(),costDepartment.getDepartmentId(),costDepartment.getYear(),costItem.getFirstCode(),costItem.getSecondCode(),costDepartment.getDid()));
        }
        costDepartmentDetailService.batchPost(costDepartmentDetailList);
        return costDepartmentDao.post(costDepartment);
    }

    /**
     * 生成数据
     * @param costDepartment
     * @return
     */
    private CostDepartment createDomain(CostDepartment costDepartment){
        costDepartment.setDid(costDepartmentDao.getKey("cost_department",costDepartment));
        costDepartment.setPlanMoney(new BigDecimal("0"));
        costDepartment.setCreateDate(new Date());
        return costDepartment;
    }

    /**
     * 初始化部门数据
     * @param companyId 公司ID
     * @param departmentId 部门ID
     * @param year 年度
     * @return
     * @throws Exception
     */
    public int add(Long companyId,Long departmentId,Integer year) throws Exception{
        CostDepartment costDepartment = new CostDepartment();
        costDepartment.setCompanyId(companyId);
        costDepartment.setDepartmentId(departmentId);
        costDepartment.setYear(year);
        return this.initialize(costDepartment);
    }

    /**
     * 补充费项数据
     * @param year
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void supplement(Integer year) throws Exception{
        List<CostDepartment> costDepartmentList = this.getAllDateByYear(year);
        List<CostDepartmentDetail> costDepartmentDetailList = new ArrayList<>();
        for (CostDepartment costDepartment : costDepartmentList) {
            List<CostItem> costItemList = costItemService.getDateToSupplement(costDepartment.getCompanyId(),costDepartment.getDepartmentId(),year);
            for (CostItem costItem : costItemList) {
                costDepartmentDetailList.add(costDepartmentDetailService.createDomain(costItem.getCode(),costDepartment.getCompanyId(),costDepartment.getDepartmentId(),costDepartment.getYear(),costItem.getFirstCode(),costItem.getSecondCode(),costDepartment.getDid()));
            }
        }
        costDepartmentDetailService.batchPost(costDepartmentDetailList);
    }

}
