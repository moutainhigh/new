package com.huayu.p.dao;

import com.huayu.hr.domain.HrEmployee;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 职员表
 * @author ZXL 2017-05-31 9:37
 **/
@Repository
public class PHrEmployeeDao extends BasePageDao<HrEmployee,Serializable>{

    public List<HrEmployee> getAllInId(HrEmployee hrEmployee){
        String sql = "SELECT * FROM hr_employee WHERE id IN("+hrEmployee.getEmpName()+")";
        return super.get(sql,hrEmployee);
    }

}
