package com.huayu.material.service;

import com.alibaba.fastjson.JSON;
import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.NumberUtil;
import com.huayu.material.dao.MaterialDao;
import com.huayu.material.dao.MaterialPriceDao;
import com.huayu.material.domain.Material;
import com.huayu.material.domain.MaterialPrice;
import com.huayu.material.domain.MaterialPriceTableResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/4/28.
 */
@Service
public class MaterialService {

    private static Logger logger = LoggerFactory.getLogger(MaterialService.class);

    @Resource
    private MaterialDao materialDao;

    @Autowired
    private MaterialPriceDao materialPriceDao;

    @Autowired
    private CommSequenceDao commSequenceDao;


    @Transactional
    public void updateMaterial(Material materialResult) {
        List<Material> materials = materialDao.getMaterials(materialResult);
        if (!CollectionUtils.isEmpty(materials)&&!materials.get(0).getId().equals(materialResult.getId())){
            throw new BusinessException("材料规格重复");
        }
        if (materialDao.updateMaterial(materialResult)!=1){
            throw new BusinessException("更新失败");
        }
    }

    @Transactional
    public void deleteMaterial(String ids) {
        String[] idArr = ids.split(",");
        Integer id;
        Date now = new Date();
        int userId = SpringSecurityUtil.getUser().getUserId().intValue();
        for (int i =0; i<idArr.length; i++) {
            id = Integer.parseInt(idArr[i]);
            MaterialPrice priceResult = new MaterialPrice();
            priceResult.setMatId(id);
            List<MaterialPrice> materialPriceList = materialPriceDao.getMaterialPrice(priceResult);
            if (!CollectionUtils.isEmpty(materialPriceList)){
                throw new BusinessException("第"+(i+1)+"项已经绑定了价格");
            }
            Material material = new Material();
            material.setId(id);
            material.setDeleteUser(userId);
            material.setDeletetime(now);
            if (materialDao.deleteMaterial(material)!=1){
                logger.info("删除材料失败,id：{}",id);
                throw new BusinessException("删除第"+(i+1)+"个材料失败");
            }
        }
    }

    public Page<Material> materialListData(Material materialResult, Pageable pageable, Integer city) {
        materialResult.setCity(city);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id,m.`mcode` ,m.`name`,m.specification,m.unit,mc.`name` categoryName,mp.city,mp.price,mp.reportYearMonth newTime,m.remark" );
        sql.append(" FROM material m " );
        sql.append(" LEFT JOIN material_category mc ON m.categoryId = mc.id ");
        sql.append(" LEFT JOIN (SELECT * FROM (SELECT * FROM material_price WHERE city = :city ORDER BY reportYearMonth DESC ) t  GROUP BY matId) mp ON m.id = mp.matId");
        sql.append(" where m.status=0  ");
        if (StringUtils.isNotBlank(materialResult.getMcode())){
            sql.append(" and  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(materialResult.getName())){
            sql.append(" and  position( :name in m.name)");
        }

        if (StringUtils.isNotBlank(materialResult.getSpecification())){
            sql.append(" and  position( :specification in m.specification)");
        }

        if (materialResult.getCategoryId() != null ){
            sql.append(" AND mc.code REGEXP (SELECT CONCAT('^',`code`) FROM material_category WHERE id = :categoryId)");
        }
        Page<Material> materialResults = materialDao.get(sql.toString(), materialResult, pageable);
        return materialResults;
    }

    /**
     * 获取材料详情
     * @param id
     * @return
     */
    public Material getMaterialResultById(Integer id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT mc.`code`,mc.`name` categoryName,m.* " +
                "FROM material m " +
                "LEFT OUTER JOIN material_category mc ON m.categoryId = mc.id " );
        sql.append(" WHERE m.id = "+id);
        Material materialResult = new Material();
        materialResult.setId(id);
        return materialDao.getOne(sql.toString(), materialResult);
    }

    /**
     * 获取材料价格
     * @param id
     * @param city
     * @return
     */
    public Material getMaterialPriceById(Integer id, Integer city) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT mc.`code`,mc.`name` categoryName,m.*,mp.price,t.price historyPrice " );
        sql.append("FROM material m " );
        sql.append("LEFT JOIN material_category mc ON m.categoryId = mc.id " );
        sql.append(" LEFT JOIN (SELECT price,matId FROM material_price WHERE city = :city AND matId =:id ORDER BY createtime DESC LIMIT 0,1) mp ON mp.matId = m.id");
        sql.append(" LEFT JOIN (SELECT price,matId FROM material_history_price WHERE city = :city AND matId =:id ORDER BY createtime DESC LIMIT 1,1) t ON t.matId = m.id");
        sql.append(" WHERE m.id = :id");
        Material materialResult = new Material();
        materialResult.setId(id);
        materialResult.setCity(city);
        return  materialDao.getOne(sql.toString(), materialResult);
    }

    @Transactional
    public void deleteOneMaterial(Integer id) {
        MaterialPrice priceResult = new MaterialPrice();
        priceResult.setMatId(id);
        List<MaterialPrice> materialPriceList = materialPriceDao.getMaterialPrice(priceResult);
        if (!CollectionUtils.isEmpty(materialPriceList)){
            throw new BusinessException("该材料已经绑定了价格，不能删除");
        }
        Material material = new Material();
        material.setId(id);
        material.setDeletetime(new Date());
        material.setDeleteUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (materialDao.deleteMaterial(material)!=1){
            logger.info("删除材料失败，id：{}",id);
            throw new BusinessException("删除材料失败");
        }
    }

    @Transactional
    public void insertMaterial(Material materialResult) {
        String code = materialDao.getCatCodeByCatId(materialResult.getCategoryId());
        List<Material> materials = JSON.parseArray(materialResult.getSpecificationArrayStr(), Material.class);
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[materials.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("material", materials.size()));
        materialResult.setCareatetime(new Date());
        materialResult.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        materialResult.setStatus(0);
        int count = materialDao.getMaterialCountByCatId(materialResult.getCategoryId());
        for (int i =0; i<materials.size(); i++){
            Long key = ids[0] + i + 1;
            Material tmp = materials.get(i);
            if (!CollectionUtils.isEmpty(materialDao.getMaterials(tmp))){
                throw new BusinessException("材料规格重复");
            }
            BeanUtils.copyProperties(materialResult,tmp,"specification","specification2");
            tmp.setId(key.intValue());
            tmp.setMcode(code+ NumberUtil.buildNum(++count,5));
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = materialDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Material getMaterialById(Integer id) {

        return materialDao.getMaterialById(id);
    }

    public List<MaterialPriceTableResult> getMaterialHistoryTable(Integer id, String startTime, String endTime, Integer city) {
        List<MaterialPriceTableResult> results = materialDao.getMaterialHistoryTable(id,startTime,endTime,city);
        Map<String,MaterialPriceTableResult> map = new HashMap<>();
        for (MaterialPriceTableResult m :results){
            map.put(m.getUpdateTime(),m);
        }

        List<MaterialPriceTableResult> allMouth = new ArrayList<>();
        String ii;
        MaterialPriceTableResult data ;
        for (int i = 1;i<=12;i++) {
            if (i < 10){
                ii = "0"+i;
            }else {
                ii = i+"";
            }
            data = new MaterialPriceTableResult();
            data.setUpdateTime(ii);
            if (map.containsKey(DateTimeUtil.getYear(new Date())+ii)){
                data.setPrice(map.get(DateTimeUtil.getYear(new Date())+ii).getPrice());
            }
            allMouth.add(data);
        }
        return allMouth;
    }

}
