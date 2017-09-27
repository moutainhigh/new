package com.huayu.p.service;

import com.huayu.hr.domain.HrEmployee;
import com.huayu.p.dao.PHrEmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职员表
 * @author ZXL 2017-05-31 9:41
 **/
@Service
public class PHrEmployeeService {

    @Autowired
    private PHrEmployeeDao pHrEmployeeDao;

    public List<HrEmployee> getAllInId(String id){
        HrEmployee hrEmployee = new HrEmployee();
        hrEmployee.setEmpName(id);
        return pHrEmployeeDao.getAllInId(hrEmployee);
    }

    public String getMobilesInId(String id){
        String mobile = "";
        List<HrEmployee> hrEmployeeList = this.getAllInId(id);
        for (int i = 0; i < hrEmployeeList.size(); i++) {
            mobile+=i==0?hrEmployeeList.get(i).getMobile():(","+hrEmployeeList.get(i).getMobile());
        }
        return mobile;
    }

}
