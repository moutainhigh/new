package com.huayu.material.dao;

import com.huayu.material.domain.MaterialPriceTableResult;
import com.huayu.material.domain.WorkMaterial;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Repository
public class WorkMaterialDao extends BasePageDao<WorkMaterial, Serializable> {
    @Resource
    private com.ly.dao.base.BaseNamedParameterJdbcTemplate jdbcTemplate;


    public int[] batchUpdate(SqlParameterSource[] a2) {
        String sql ="insert into work_material (id,mcode,name,specification,categoryId,unit,status,remark,careatetime,createUser)" +
                " values (:id,:mcode,:name,:specification,:categoryId,:unit,:status,:remark,:careatetime,:createUser)";
        return super.batchUpdate(sql, a2);
    }

    public int updateMaterial(WorkMaterial materialResult) {
        String sql = "UPDATE work_material set categoryId=:categoryId,`name`=:name,specification=:specification,unit=:unit,remark=:remark WHERE id = :id";
       return super.put(sql,materialResult);
    }

    public int deleteMaterial(WorkMaterial material) {
        String sql = "UPDATE work_material SET `status`= 1,deletetime=:deletetime,deleteUser=:deleteUser WHERE id = :id";
        return super.put(sql,material);
    }

    public WorkMaterial getMaterialById(Integer id) {
        String sql = "SELECT name,specification from work_material where id = ?";
        Object[] params = {id};
        return jdbcTemplate.getJdbcOperations().query(sql,params , resultSet -> {
            WorkMaterial material = null;
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String specification = resultSet.getString("specification");
                material = new WorkMaterial();
                material.setName(name);
                material.setSpecification(specification);
            }
            return material;
        });
    }

    public List<MaterialPriceTableResult> getMaterialHistoryTable(Integer id, String startTime, String endTime, Integer city) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT matId,price,reportYearMonth FROM work_material_price WHERE  matId = ? AND city = ? ");
        sb.append(" ORDER BY reportYearMonth ASC");
        return jdbcTemplate.getJdbcOperations().query(sb.toString(), ps -> {
            ps.setInt(1,id);
            ps.setInt(2,city);
        }, (resultSet, i) -> {
            String price = resultSet.getString("price");
            String updateTime = resultSet.getString("reportYearMonth");
            MaterialPriceTableResult priceTableResult = new MaterialPriceTableResult();
            priceTableResult.setPrice(price);
            priceTableResult.setUpdateTime(updateTime);
            return priceTableResult;
        });
    }


    public String getCatCodeByCatId(Integer categoryId) {
        String sql1 = "SELECT `code` from work_material_category WHERE id = "+categoryId;
        return jdbcTemplate.getJdbcOperations().queryForObject(sql1, String.class);
    }


    public int getMaterialCountByCatId(Integer categoryId) {
        String sql = "SELECT count(1) FROM work_material_category where categoryId = ? ";
        return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{categoryId}, Integer.class);
    }

    public List<WorkMaterial> getMaterials(WorkMaterial material) {
        StringBuilder stringBuilder = new StringBuilder("SELECT m.*");
        stringBuilder.append(" FROM work_material m");
        stringBuilder.append(" WHERE m.categoryId=:categoryId AND m.status=0 ");
        if (StringUtils.isNotBlank(material.getName())){
            stringBuilder.append(" AND m.name = :name ");
        }
        if (null!=material.getSpecification()){
            stringBuilder.append(" AND m.specification = :specification ");
        }
        return super.get(stringBuilder.toString(),material);
    }
}
