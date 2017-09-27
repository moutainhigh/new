package com.huayu.cost.service;

import com.huayu.cost.dao.CostItemDao;
import com.huayu.cost.domain.CostItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 费用项目（科目）
 * @author ZXL 2017-07-07 10:09
 **/
@Service
public class CostItemService {

    @Autowired
    private CostItemDao costItemDao;

    /**
     * 获取初始化数据
     * @param costItem
     * @return
     */
    public List<CostItem> getDateToInitialize(){
        return costItemDao.getDateToInitialize(new CostItem());
    }

    /**
     * 获取待补充的数据
     * @param companyId 公司ID
     * @param departmentId 部门ID
     * @param year 年度
     * @return
     */
    public List<CostItem> getDateToSupplement(Long companyId,Long departmentId,Integer year){
        CostItem costItem = new CostItem();
        costItem.setCompanyId(companyId);
        costItem.setDepartmentId(departmentId);
        costItem.setYear(year);
        return costItemDao.getDateToSupplement(costItem);
    }

}
