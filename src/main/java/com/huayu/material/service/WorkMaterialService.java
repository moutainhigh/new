package com.huayu.material.service;

import com.alibaba.fastjson.JSON;
import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.NumberUtil;
import com.huayu.material.dao.WorkMaterialDao;
import com.huayu.material.dao.WorkMaterialPriceDao;
import com.huayu.material.domain.MaterialPriceTableResult;
import com.huayu.material.domain.WorkMaterial;
import com.huayu.material.domain.WorkMaterialPrice;
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

import java.util.*;

/**
 * Created by Administrator on 2017/4/28.
 */
@Service
public class WorkMaterialService {

    private static Logger logger = LoggerFactory.getLogger(WorkMaterialService.class);

    @Autowired
    private WorkMaterialDao workMaterialDao;

    @Autowired
    private WorkMaterialPriceDao workMaterialPriceDao;

    @Autowired
    private CommSequenceDao commSequenceDao;


    @Transactional
    public void updateMaterial(WorkMaterial materialResult) {
        List<WorkMaterial> materials = workMaterialDao.getMaterials(materialResult);
        if (!CollectionUtils.isEmpty(materials)&&!materials.get(0).getId().equals(materialResult.getId())){
            throw new BusinessException("材料规格重复");
        }
        if (workMaterialDao.updateMaterial(materialResult)!=1){
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
            WorkMaterialPrice priceResult = new WorkMaterialPrice();
            priceResult.setMatId(id);
            List<WorkMaterialPrice> materialPriceList = workMaterialPriceDao.getMaterialPrice(priceResult);
            if (!CollectionUtils.isEmpty(materialPriceList)){
                throw new BusinessException("第"+(i+1)+"项已经绑定了价格");
            }
            WorkMaterial material = new WorkMaterial();
            material.setId(id);
            material.setDeleteUser(userId);
            material.setDeletetime(now);
            if (workMaterialDao.deleteMaterial(material)!=1){
                logger.info("删除分项分类失败，id：{}",id);
                throw new BusinessException("删除第"+(i+1)+"个分项分类失败");
            }
        }
    }


    public Page<WorkMaterial> materialListData(WorkMaterial materialResult, Pageable pageable, Integer city) {
        materialResult.setCity(city);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id,m.`mcode` ,m.`name`,m.specification,m.unit,mc.`name` categoryName,mp.city,mp.price,mp.reportYearMonth newTime,m.remark" );
        sql.append(" FROM work_material m " );
        sql.append(" LEFT JOIN work_material_category mc ON m.categoryId = mc.id ");
        sql.append(" LEFT JOIN (SELECT * FROM (SELECT * FROM work_material_price WHERE city = :city ORDER BY reportYearMonth DESC ) t  GROUP BY matId ) mp ON m.id = mp.matId");
        sql.append(" WHERE  m.status=0 ");
        if (StringUtils.isNotBlank(materialResult.getMcode())){
            sql.append(" AND  position(:mcode in m.mcode) ");
        }

        if (StringUtils.isNotBlank(materialResult.getName())){
            sql.append(" AND  position( :name in m.name)");
        }
        if (StringUtils.isNotBlank(materialResult.getSpecification())){
            sql.append(" AND  position( :specification in m.specification)");
        }
        if (materialResult.getCategoryId() != null ){
            sql.append(" AND mc.code REGEXP (SELECT CONCAT('^',`code`) FROM work_material_category WHERE id = :categoryId)");
        }
        Page<WorkMaterial> materialResults = workMaterialDao.get(sql.toString(), materialResult, pageable);
        return materialResults;
    }

    /**
     * 获取材料详情
     * @param id
     * @return
     */
    public WorkMaterial getMaterialResultById(Integer id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.*,mc.`code`,mc.`name` categoryName" +
                "FROM work_material m " +
                "LEFT OUTER JOIN work_material_category mc ON m.categoryId = mc.id " );
        sql.append(" WHERE m.id = "+id);
        WorkMaterial materialResult = new WorkMaterial();
        materialResult.setId(id);
        return workMaterialDao.getOne(sql.toString(), materialResult);
    }

    /**
     * 获取材料价格详情
     * @param id
     * @param city
     * @return
     */
    public WorkMaterial getMaterialPriceById(Integer id, Integer city) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT mc.`code`,mc.`name` categoryName,m.*,mp.price,t.price historyPrice " );
        sql.append("FROM work_material m " );
        sql.append("LEFT JOIN work_material_category mc ON m.categoryId = mc.id " );
        sql.append(" LEFT JOIN (SELECT price,matId FROM work_material_price WHERE city = :city AND matId =:id ORDER BY createtime DESC LIMIT 0,1) mp ON mp.matId = m.id");
        sql.append(" LEFT JOIN (SELECT price,matId FROM work_material_history_price WHERE city = :city AND matId =:id ORDER BY createtime DESC LIMIT 1,1) t ON t.matId = m.id");
        sql.append(" WHERE m.id = :id");
        WorkMaterial materialResult = new WorkMaterial();
        materialResult.setId(id);
        materialResult.setCity(city);
        return workMaterialDao.getOne(sql.toString(), materialResult);
    }

    @Transactional
    public void deleteOneMaterial(Integer id) {
        WorkMaterialPrice priceResult = new WorkMaterialPrice();
        priceResult.setMatId(id);
        List<WorkMaterialPrice> materialPriceList = workMaterialPriceDao.getMaterialPrice(priceResult);
        if (!CollectionUtils.isEmpty(materialPriceList)){
            throw new BusinessException("该分项分类已经绑定了价格，不能删除");
        }
        WorkMaterial material = new WorkMaterial();
        material.setId(id);
        material.setDeletetime(new Date());
        material.setDeleteUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (workMaterialDao.deleteMaterial(material)!=1){
            logger.info("删除分项分类失败，id：{}",id);
            throw new BusinessException("删除分项分类失败");
        }
    }

    @Transactional
    public void insertMaterial(WorkMaterial materialResult) {
        String code = workMaterialDao.getCatCodeByCatId(materialResult.getCategoryId());
        List<String> strings = JSON.parseArray(materialResult.getSpecificationArrayStr(), String.class);
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[strings.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("work_material", strings.size()));
        materialResult.setCareatetime(new Date());
        materialResult.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        materialResult.setStatus(0);
        int count = workMaterialDao.getMaterialCountByCatId(materialResult.getCategoryId());
        for (int i =0; i<strings.size(); i++){
            Long key = ids[0] + i + 1;
            WorkMaterial tmp = new WorkMaterial();
            if (!CollectionUtils.isEmpty(workMaterialDao.getMaterials(tmp))){
                throw new BusinessException("材料规格重复");
            }
            BeanUtils.copyProperties(materialResult,tmp);
            tmp.setId(key.intValue());
            tmp.setMcode(code+ NumberUtil.buildNum(++count,5));
            tmp.setSpecification(strings.get(i));
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[]  flags = workMaterialDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    public WorkMaterial getMaterialById(Integer id) {

        return workMaterialDao.getMaterialById(id);
    }

    public List<MaterialPriceTableResult> getMaterialHistoryTable(Integer id, String startTime, String endTime, Integer city) {
        List<MaterialPriceTableResult> results = workMaterialDao.getMaterialHistoryTable(id,startTime,endTime,city);
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
