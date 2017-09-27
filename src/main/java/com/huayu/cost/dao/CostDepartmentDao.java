package com.huayu.cost.dao;

import com.huayu.cost.domain.CostDepartment;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 部门费用预算主表
 * @author ZXL 2017-07-07 9:53
 **/
@Repository
public class CostDepartmentDao extends BasePageDao<CostDepartment,Serializable> {

    public CostDepartment getOneByUnique(CostDepartment costDepartment){
        String sql = "SELECT * FROM cost_department WHERE companyId=:companyId AND departmentId=:departmentId AND `year`=:year";
        return super.getOne(sql,costDepartment);
    }

    /**
     * 以年度为条件获取数据
     * @param costDepartment
     * @return
     */
    public List<CostDepartment> getAllDateByYear(CostDepartment costDepartment){
        String sql = "SELECT * FROM cost_department WHERE `year`=:year";
        return super.get(sql,costDepartment);
    }


}
