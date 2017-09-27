package com.huayu.inventory.dao;

import com.huayu.inventory.domain.PrMaterialSpecification;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/13.
 */
@Repository
public class PrMaterialSpecificationDao extends BasePageDao<PrMaterialSpecification,Integer> {

    public int[] batchUpdate( SqlParameterSource[] a2) {
        String sql = "insert into pr_material_specification (id,matId,specification,status,budget) values (:id,:matId,:specification,:status,:budget) ";
        return super.batchUpdate(sql, a2);
    }

    public int update(PrMaterialSpecification entity){
        String sql = "update pr_material_specification set specification=:specification where id=:id and matId=:matId";
        return super.put(sql,entity);
    }


    public List<PrMaterialSpecification> getMaterialSpecification(PrMaterialSpecification specification) {
        String sql = "SELECT * from pr_material_specification where matId=:matId and status=0";
        return super.get(sql,specification);
    }

    public PrMaterialSpecification getMaterialBySpecification(PrMaterialSpecification specification) {
        StringBuilder sql =new StringBuilder( "SELECT ms.* from pr_material_specification ms INNER JOIN pr_material m ON m.id = ms.matId ");
        sql.append(" WHERE ms.matId=:matId AND ms.specification=:specification AND ms.status=0 LIMIT 0,1");
        return super.getOne(sql.toString(),specification);
    }


    public int deleteOne(PrMaterialSpecification specification) {
        String sql = "update pr_material_specification set status=1,deletetime=:deletetime,deleteUser=:deleteUser where id=:id and matId=:matId";
        return super.put(sql,specification);
    }
}
