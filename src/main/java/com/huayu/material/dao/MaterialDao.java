package com.huayu.material.dao;

import com.huayu.material.domain.Material;
import com.huayu.material.domain.MaterialPriceTableResult;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Repository
public class MaterialDao extends BasePageDao<Material, Serializable> {
    @Resource
    private com.ly.dao.base.BaseNamedParameterJdbcTemplate jdbcTemplate;


    public int[] batchUpdate(SqlParameterSource[] a2) {
        String sql ="insert into material (id,mcode,name,specification,specification2,categoryId,unit,area,status,remark,careatetime,createUser)" +
                " values (:id,:mcode,:name,:specification,:specification2,:categoryId,:unit,:area,:status,:remark,:careatetime,:createUser)";
        return super.batchUpdate(sql, a2);
    }

    public int updateMaterial(Material materialResult) {
        String sql = "UPDATE material set categoryId=:categoryId,`name`=:name,specification=:specification,specification2=:specification2,unit=:unit,area=:area,remark=:remark WHERE id = :id";
        return super.put(sql,materialResult);
    }

    public int deleteMaterial(Material material) {
        String sql = "UPDATE material SET `status`= 1,deletetime=:deletetime,deleteUser=:deleteUser WHERE id = :id";
        return super.put(sql,material);
    }

    public List<Material> getMaterials(Material material) {
        StringBuilder stringBuilder = new StringBuilder("SELECT m.*");
        stringBuilder.append(" FROM material m");
        stringBuilder.append(" WHERE m.categoryId=:categoryId AND m.status=0 ");
        if (StringUtils.isNotBlank(material.getName())){
            stringBuilder.append(" AND m.name = :name ");
        }
        if (null!=material.getSpecification()){
            stringBuilder.append(" AND m.specification = :specification ");
        }
        return super.get(stringBuilder.toString(),material);
    }

    public int getMaterialCountByCatId(Integer id) {
        String sql = "SELECT count(1) FROM material where categoryId = ? ";
        return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, Integer.class);
    }

    public Material getMaterialById(Integer id) {
        String sql = "SELECT name,specification from material where id = ?";
        Object[] params = {id};
        return jdbcTemplate.getJdbcOperations().query(sql,params , resultSet -> {
            Material material = null;
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String specification = resultSet.getString("specification");
                material = new Material();
                material.setName(name);
                material.setSpecification(specification);
            }
            return material;
        });
    }

    public List<MaterialPriceTableResult> getMaterialHistoryTable(Integer id, String startTime, String endTime, Integer city) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT matId,price,reportYearMonth FROM material_price WHERE matId =? AND city = ?");
        sb.append(" ORDER BY reportYearMonth ASC");
        return jdbcTemplate.getJdbcOperations().query(sb.toString(),  ps -> {
            ps.setInt(1,id);
            ps.setInt(2,city);
        },(resultSet, i) -> {
            String price = resultSet.getString("price");
            String updateTime = resultSet.getString("reportYearMonth");
            MaterialPriceTableResult priceTableResult = new MaterialPriceTableResult();
            priceTableResult.setPrice(price);
            priceTableResult.setUpdateTime(updateTime);
            return priceTableResult;
        });
    }

    public String getCatCodeByCatId(Integer categoryId) {
        String sql1 = "SELECT `code` from material_category WHERE id = "+categoryId;
        return jdbcTemplate.getJdbcOperations().queryForObject(sql1, String.class);
    }

}
