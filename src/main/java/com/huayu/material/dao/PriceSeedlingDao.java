package com.huayu.material.dao;

import com.huayu.material.domain.MaterialPriceTableResult;
import com.huayu.material.domain.PriceSeedling;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Repository
public class PriceSeedlingDao extends BasePageDao<PriceSeedling, Serializable> {
    @Resource
    private com.ly.dao.base.BaseNamedParameterJdbcTemplate jdbcTemplate;


    public int[] batchUpdate(SqlParameterSource[] a2) {
        String sql ="insert into price_seedling (id,mcode,name,specification,specification2,specification3,specification4,specification5,specification6,specification7,extend,extend2,categoryId,unit,status,remark,careatetime,createUser)" +
                " values (:id,:mcode,:name,:specification,:specification2,:specification3,:specification4,:specification5,:specification6,:specification7,:extend,:extend2,:categoryId,:unit,:status,:remark,:careatetime,:createUser)";
        return super.batchUpdate(sql, a2);
    }

    public int updateMaterial(PriceSeedling materialResult) {
        String sql = "UPDATE price_seedling set categoryId=:categoryId,`name`=:name,specification=:specification,specification2=:specification2,specification3=:specification3,specification4=:specification4,specification5=:specification5," +
                "specification6=:specification6,specification7=:specification7,extend=:extend,extend2=:extend2,unit=:unit,remark=:remark WHERE id = :id";
       return super.put(sql,materialResult);
    }

    public int deleteMaterial(PriceSeedling material) {
        String sql = "UPDATE price_seedling SET `status`= 1,deletetime=:deletetime,deleteUser=:deleteUser WHERE id = :id";
        return super.put(sql,material);
    }

    public int getMaterialCountByCatId(Integer id) {
        String sql = "SELECT count(1) FROM price_seedling where categoryId = ? and mcode !=''";
        return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, Integer.class);
    }

    public PriceSeedling getMaterialById(Integer id) {
        String sql = "SELECT name,specification from price_seedling where id = ?";
        Object[] params = {id};
        return jdbcTemplate.getJdbcOperations().query(sql,params , resultSet -> {
            PriceSeedling material = null;
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String specification = resultSet.getString("specification");
                material = new PriceSeedling();
                material.setName(name);
                material.setSpecification(specification);
            }
            return material;
        });
    }

    public List<MaterialPriceTableResult> getMaterialHistoryTable(Integer id, String startTime, String endTime, Integer city) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT matId,price,reportYearMonth FROM price_seedling_price WHERE matId = ? AND city = ?");
        sb.append(" ORDER BY reportYearMonth ASC");
        return jdbcTemplate.getJdbcOperations().query(sb.toString(), ps -> {
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
        String sql1 = "SELECT `code` from price_seedling_category WHERE id = "+categoryId;
        return jdbcTemplate.getJdbcOperations().queryForObject(sql1, String.class);
    }

}
