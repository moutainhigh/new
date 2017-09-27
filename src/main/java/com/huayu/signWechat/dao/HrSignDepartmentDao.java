package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignDepartment;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/13.
 */
@Repository
public class HrSignDepartmentDao extends BasePageDao<HrSignDepartment, Serializable> {

    public int postDepartment(HrSignDepartment d) {
        String sql = "INSERT INTO hr_sign_department (id,NAME,parentid,status,`order`) VALUES(:id,:name,:parentid,:status,:order)";
        return super.post(sql, d);
    }

    public HrSignDepartment getOneDepartment(HrSignDepartment hrSignDepartment) {
        String sql = "select * from hr_sign_department where id = :id";
        return super.getOne(sql, hrSignDepartment);
    }

    public void updateDepartment() {
        String sql = "DELETE FROM hr_sign_department";
        super.delete(sql,new HrSignDepartment());
    }
}
