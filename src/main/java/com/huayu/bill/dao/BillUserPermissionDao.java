package com.huayu.bill.dao;

import com.huayu.bill.domain.BillUserPermission;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DengPeng on 2016/11/21.
 */
@Repository
public class BillUserPermissionDao extends BasePageDao<BillUserPermission,Serializable> {


    public boolean checkUserPermissionExist(BillUserPermission permission) {
        String sql  = "select count(0) from bill_user_permission where userId=:userId and permissionId=:permissionId";
        if (super.getLong(sql,permission)>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int delete(BillUserPermission a) {
        String sql  = "update bill_user_permission set status=:status,updateUserId=:updateUserId,updatetime=:updatetime where id=:id and userId=:userId ";
        return super.delete(sql,a);
    }

    public List<BillUserPermission> getUserPermissionList(BillUserPermission permission) {
        String sql  = "select * from bill_user_permission where userId=:userId and status=0";
        return super.get(sql,permission);
    }
}
