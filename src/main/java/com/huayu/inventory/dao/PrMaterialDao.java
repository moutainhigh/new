package com.huayu.inventory.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.inventory.domain.PrMaterial;
import com.huayu.inventory.web.args.PrMaterialArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class PrMaterialDao extends BasePageDao<PrMaterial, Serializable> {

    public int postData(PrMaterialArgs material) {
        Long key = super.getKey("pr_material",material);
        material.setId(key.intValue());
        return super.post(material);
    }

    public int updateMaterial(PrMaterial materialResult) {
        String sql = "UPDATE pr_material set name=:name,alias=:alias,brand=:brand,unit=:unit,remark=:remark WHERE  id=:id AND projectId=:projectId";
        return super.put(sql,materialResult);
    }

    public int deleteMaterial(PrMaterial material) {
        String sql = "UPDATE pr_material SET `status`= 1,deletetime=:deletetime,deleteUser=:deleteUser WHERE id = :id AND projectId=:projectId";
        return super.put(sql,material);
    }

    public int updateMaterialCode(PrMaterial materialResult) {
        String sql = "UPDATE pr_material set mcode=:mcode WHERE  id=:id AND projectId=:projectId";
        return super.put(sql,materialResult);
    }

    public Page<PrMaterial> materialSpecificationListData(PrMaterialArgs material, Pageable pageable){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id,m.`mcode` ,m.`name`,m.brand,pms.specification,pms.id specificationId,m.alias,di.dictValue unitStr,mc.`name` categoryName,m.remark");
        sql.append(" FROM pr_material m");
        sql.append(" INNER JOIN pr_material_specification pms ON pms.matId = m.id AND pms.status=0 ");
        sql.append(" INNER JOIN pr_material_category mc ON m.categoryId = mc.id");
        sql.append(" INNER JOIN pr_project pp ON m.projectId = pp.id");
        sql.append(" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit' ");
        sql.append(" WHERE ");
        User user = SpringSecurityUtil.getUser();
        sql.append(" pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());
        if (null != material.getRepositoryId()){
            sql.append(" AND repositoryId=:repositoryId");
        }

        if (StringUtils.isNotBlank(material.getMcode())){
            sql.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(material.getName())){
            sql.append(" AND  position( :name in m.name)");
        }

        if (StringUtils.isNotBlank(material.getSpecification())){
            sql.append(" AND  position( :specification in pms.specification)");
        }

        if (StringUtils.isNotBlank(material.getMcCode())){
            sql.append(" AND mc.code REGEXP '^"+material.getMcCode()+"'");
        }
        sql.append(" AND m.status=0");
        return super.get(sql.toString(),material,pageable);
    }

    public Page<PrMaterial> materialListData(PrMaterialArgs material, Pageable pageable){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id,m.`mcode` ,m.`name`,m.brand,m.alias,di.dictValue unitStr,mc.`name` categoryName,m.remark");
        sql.append(" FROM pr_material m");
        sql.append(" INNER JOIN pr_material_category mc ON m.categoryId = mc.id");
        sql.append(" INNER JOIN pr_project pp ON m.projectId = pp.id");
        sql.append(" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit' ");
        sql.append(" WHERE ");
        User user = SpringSecurityUtil.getUser();
        sql.append(" pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());
        if (null != material.getRepositoryId()){
            sql.append(" AND repositoryId=:repositoryId");
        }

        if (StringUtils.isNotBlank(material.getMcode())){
            sql.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(material.getName())){
            sql.append(" AND  position( :name in m.name)");
        }

        if (StringUtils.isNotBlank(material.getMcCode())){
            sql.append(" AND mc.code REGEXP '^"+material.getMcCode()+"'");
        }
        sql.append(" AND m.status=0");
        return super.get(sql.toString(),material,pageable);
    }

    public PrMaterial getOneMaterial(PrMaterial material) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.*,mc.`code`,mc.`name` categoryName,pms.specification,pms.id specificationId,pms.budget ");
        sql.append("FROM pr_material m " );
        sql.append("INNER JOIN pr_material_specification pms ON pms.matId = m.id ");
        sql.append("LEFT JOIN pr_material_category mc ON m.categoryId = mc.id ");
        sql.append("LEFT JOIN pr_project pp ON pp.id = m.projectId " );
        sql.append(" WHERE m.id = :id AND pms.id=:specificationId AND pp.pcode REGEXP");
        User user = SpringSecurityUtil.getUser();
        sql.append(user.getDataAuthorityRegexp());
        return super.getOne(sql.toString(),material);
    }

    public int getMaterialCountByCatId(Integer id) {
        String sql = "SELECT count(1) FROM pr_material where categoryId = ? and mcode !=''";
        return  jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, Integer.class);
    }


    public List<PrMaterial> getMaterials(PrMaterial material) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder stringBuilder = new StringBuilder("SELECT m.*");
        stringBuilder.append(" FROM pr_material m");
        stringBuilder.append(" LEFT JOIN pr_project pp ON pp.id = m.projectId");
        stringBuilder.append(" WHERE m.categoryId=:categoryId AND m.status=0 AND pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());
        if (StringUtils.isNotBlank(material.getName())){
            stringBuilder.append(" AND m.name = :name ");
        }
        if (null != material.getUnit()){
            stringBuilder.append(" AND m.unit=:unit ");
        }
        return super.get(stringBuilder.toString(),material);
    }

    public List<PrMaterial> getMaterials(PrMaterial material,String code) {
        StringBuilder stringBuilder = new StringBuilder("SELECT m.*");
        stringBuilder.append(" FROM pr_material m");
        stringBuilder.append(" LEFT JOIN pr_project pp ON pp.id = m.projectId");
        stringBuilder.append(" WHERE m.categoryId=:categoryId AND m.status=0 AND pp.pcode REGEXP ").append(code);
        stringBuilder.append(" ORDER BY m.mcode DESC");
        return super.get(stringBuilder.toString(),material);
    }

    /**
     * 查询已经入库材料
     * @param material
     * @param pageable
     * @return
     */
    public Page<PrMaterial> getInStorageMaterial(PrMaterialArgs material, Pageable pageable) {
        StringBuffer sql = new StringBuffer("SELECT m.id,pmc.name categoryName,m.mcode,m.name, m.alias,pms.id specificationId,pms.specification, m.unit,s.num balanceNum,s.sumPrice balanceSumPrice,s.excTaxSumPrice balanceExcTaxSumPrice " +
                "FROM pr_item_storage_sum s " +
                "INNER JOIN  pr_material m  ON s.materialId = m.id  "+
                "INNER JOIN  pr_material_category pmc  ON pmc.id = m.categoryId "+
                "INNER JOIN pr_material_specification pms on pms.matId = m.id AND s.specificationId = pms.id  "+
                "INNER JOIN pr_project pp ON m.projectId = pp.id ");
        sql.append(" WHERE  s.repositoryId = :repositoryId AND s.num >0");
        User user = SpringSecurityUtil.getUser();
        sql.append(" AND pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());

        if (StringUtils.isNotBlank(material.getMcode())){
            sql.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(material.getName())){
            sql.append(" AND  position( :name in m.name)");
        }

        if (StringUtils.isNotBlank(material.getSpecification())){
            sql.append(" AND  position( :specification in pms.specification)");
        }

        if (StringUtils.isNotBlank(material.getMcCode())){
            sql.append(" AND mc.code REGEXP '^"+material.getMcCode()+"'");
        }
        return super.get(sql.toString(),material,pageable);
    }

    public PrMaterial getInStorageMaterialOne(PrMaterial material) {
        StringBuffer sql = new StringBuffer("SELECT m.* ,s.num balanceNum,s.sumPrice balanceSumPrice" +
                "FROM pr_item_storage_sum s " +
                "INNER JOIN  pr_material m  ON s.materialId = m.id  "+
                "INNER JOIN pr_material_specification pms on pms.matId = m.id AND s.specificationId = pms.id  "+
                "INNER JOIN pr_project pp ON m.projectId = pp.id ");
        sql.append(" WHERE  s.repositoryId = :repositoryId");
        sql.append(" AND s.materialId=:id AND s.specificationId=:specificationId");
        User user = SpringSecurityUtil.getUser();
        sql.append(" AND pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());
        return super.getOne(sql.toString(),material);
    }

    private StringBuilder buildSql(PrMaterialArgs material){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.id,m.`mcode` ,m.`name`,pms.specification,pms.id specificationId,m.alias,di.dictValue unitStr,mc.`name` categoryName");
        sql.append(",m.remark,s.num balanceNum,s.sumPrice balanceSumPrice,s.excTaxSumPrice balanceExcTaxSumPrice,d.taxRate,d.tax");
        sql.append(" FROM pr_material m");
        sql.append(" INNER JOIN pr_material_specification pms ON pms.matId = m.id AND pms.status=0 ");
        sql.append(" INNER JOIN pr_material_category mc ON m.categoryId = mc.id");
        sql.append(" INNER JOIN pr_project pp ON m.projectId = pp.id");
        sql.append(" INNER JOIN common_dict di ON di.dictKey = m.unit AND di.dictGroup='unit' ");
        sql.append(" INNER JOIN pr_item_storage_sum s ON s.materialId= pms.matId AND s.specificationId = pms.id AND s.repositoryId =:repositoryId");
        sql.append(" INNER JOIN pr_item_delivery d ON d.materialId = pms.matId AND d.specificationId = pms.id ");
        sql.append(" WHERE ");
        User user = SpringSecurityUtil.getUser();
        sql.append(" pp.pcode REGEXP ").append(user.getDataAuthorityRegexp());
        if (StringUtils.isNotBlank(material.getMcode())){
            sql.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(material.getName())){
            sql.append(" AND  position( :name in m.name)");
        }

        if (StringUtils.isNotBlank(material.getSpecification())){
            sql.append(" AND  position( :specification in pms.specification)");
        }

        if (StringUtils.isNotBlank(material.getMcCode())){
            sql.append(" AND mc.code REGEXP '^"+material.getMcCode()+"'");
        }
        sql.append(" AND m.status=0 AND d.`status` = 0 GROUP BY pms.matId,pms.specification");
        return sql;
    }

    public Page<PrMaterial> receiptMaterialListData(PrMaterialArgs material, Pageable pageable) {
        StringBuilder sql = buildSql(material);
        return super.get(sql.toString(),material,pageable);
    }

    public List<PrMaterial> receiptMaterialListData(PrMaterialArgs material) {
        StringBuilder sql = buildSql(material);
        return super.get(sql.toString(),material);
    }

}
