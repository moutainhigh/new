package com.huayu.material.service;

import com.alibaba.fastjson.JSON;
import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.NumberUtil;
import com.huayu.material.dao.PriceSeedlingDao;
import com.huayu.material.dao.PriceSeedlingPriceDao;
import com.huayu.material.domain.MaterialPriceTableResult;
import com.huayu.material.domain.PriceSeedling;
import com.huayu.material.domain.PriceSeedlingPrice;
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
public class PriceSeedlingService {

    private static Logger logger = LoggerFactory.getLogger(PriceSeedlingService.class);

    @Resource
    private PriceSeedlingDao priceSeedlingDao;

    @Autowired
    private PriceSeedlingPriceDao priceSeedlingPriceDao;

    @Autowired
    private CommSequenceDao commSequenceDao;


    @Transactional
    public void updateMaterial(PriceSeedling materialResult) {
        if (priceSeedlingDao.updateMaterial(materialResult)!=1){
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
            PriceSeedlingPrice priceResult = new PriceSeedlingPrice();
            priceResult.setMatId(id);
            List<PriceSeedlingPrice> materialPriceList = priceSeedlingPriceDao.getMaterialPrice(priceResult);
            if (!CollectionUtils.isEmpty(materialPriceList)){
                throw new BusinessException("第"+(i+1)+"项已经绑定了价格");
            }
            PriceSeedling priceSeedling = new PriceSeedling();
            priceSeedling.setId(id);
            priceSeedling.setDeleteUser(userId);
            priceSeedling.setDeletetime(now);
            if (priceSeedlingDao.deleteMaterial(priceSeedling)!=1){
                logger.info("删除材料失败,id：{}",id);
                throw new BusinessException("删除第"+(i+1)+"个材料失败");
            }
        }
    }

    public Page<PriceSeedling> materialListData(PriceSeedling materialResult, Pageable pageable,Integer city) {
        materialResult.setCity(city);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.id,m.*,mc.`name` categoryName,mp.city,mp.price,mp.reportYearMonth newTime" );
        sql.append(" FROM price_seedling m " );
        sql.append(" LEFT JOIN price_seedling_category mc ON m.categoryId = mc.id ");
        sql.append(" LEFT JOIN (SELECT * FROM (SELECT * FROM price_seedling_price WHERE city = :city ORDER BY reportYearMonth DESC ) t  GROUP BY matId) mp ON m.id = mp.matId");
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

        if (StringUtils.isNotBlank(materialResult.getSpecification2())){
            sql.append(" and  position( :specification2 in m.specification2)");
        }
        if (StringUtils.isNotBlank(materialResult.getSpecification3())){
            sql.append(" and  position( :specification3 in m.specification3)");
        }

        if (StringUtils.isNotBlank(materialResult.getSpecification4())){
            sql.append(" and  position( :specification4 in m.specification4)");
        }

        if (StringUtils.isNotBlank(materialResult.getSpecification5())){
            sql.append(" and  position( :specification5 in m.specification5)");
        }

        if (StringUtils.isNotBlank(materialResult.getSpecification6())){
            sql.append(" and  position( :specification6 in m.specification6)");
        }

        if (StringUtils.isNotBlank(materialResult.getSpecification7())){
            sql.append(" and  position( :specification7 in m.specification7)");
        }

        if (StringUtils.isNotBlank(materialResult.getExtend())){
            sql.append(" and  position( :extend in m.extend)");
        }

        if (StringUtils.isNotBlank(materialResult.getExtend2())){
            sql.append(" and  position( :extend2 in m.extend2)");
        }

        if (materialResult.getCategoryId() != null ){
            sql.append(" AND mc.code REGEXP (SELECT CONCAT('^',`code`) FROM price_seedling_category WHERE id = :categoryId)");
        }
        Page<PriceSeedling> materialResults = priceSeedlingDao.get(sql.toString(), materialResult, pageable);
        return materialResults;
    }

    /**
     * 获取材料详情
     * @param id
     * @return
     */
    public PriceSeedling getMaterialResultById(Integer id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.*,mc.`code`,mc.`name` categoryName " +
                "from price_seedling m " +
                "LEFT JOIN price_seedling_category mc ON m.categoryId = mc.id ");
        sql.append(" WHERE m.id = "+id);
        PriceSeedling materialResult = new PriceSeedling();
        materialResult.setId(id);
        return priceSeedlingDao.getOne(sql.toString(), materialResult);
    }

    /**
     * 获取材料价格
     * @param id
     * @param city
     * @return
     */
    public PriceSeedling getMaterialPriceById(Integer id,Integer city) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT mc.`code`,mc.`name` categoryName,m.*,mp.price,t.price historyPrice " );
        sql.append("FROM price_seedling m " );
        sql.append("LEFT JOIN price_seedling_category mc ON m.categoryId = mc.id " );
        sql.append(" LEFT JOIN (SELECT price,matId FROM price_seedling_price WHERE city = :city AND matId =:id ORDER BY createtime DESC LIMIT 0,1) mp ON mp.matId = m.id");
        sql.append(" LEFT JOIN (SELECT price,matId FROM price_seedling_history_price WHERE city = :city AND matId =:id ORDER BY createtime DESC LIMIT 1,1) t ON t.matId = m.id");
        sql.append(" WHERE m.id = :id");
        PriceSeedling materialResult = new PriceSeedling();
        materialResult.setId(id);
        materialResult.setCity(city);
        return priceSeedlingDao.getOne(sql.toString(), materialResult);
    }

    @Transactional
    public void deleteOneMaterial(Integer id) {
        PriceSeedlingPrice priceResult = new PriceSeedlingPrice();
        priceResult.setMatId(id);
        List<PriceSeedlingPrice> materialPriceList = priceSeedlingPriceDao.getMaterialPrice(priceResult);
        if (!CollectionUtils.isEmpty(materialPriceList)){
            throw new BusinessException("该材料已经绑定了价格，不能删除");
        }
        PriceSeedling material = new PriceSeedling();
        material.setId(id);
        material.setDeletetime(new Date());
        material.setDeleteUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (priceSeedlingDao.deleteMaterial(material)!=1){
            logger.info("删除材料失败，id：{}",id);
            throw new BusinessException("删除材料失败");
        }
    }

    @Transactional
    public void insertMaterial(PriceSeedling materialResult) {
        String code = priceSeedlingDao.getCatCodeByCatId(materialResult.getCategoryId());
        List<PriceSeedling> seedlings = JSON.parseArray(materialResult.getSpecificationArrayStr(), PriceSeedling.class);
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[seedlings.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("price_seedling", seedlings.size()));
        Date now = new Date();
        int userId = SpringSecurityUtil.getUser().getUserId().intValue();
        int count = priceSeedlingDao.getMaterialCountByCatId(materialResult.getCategoryId());
        for (int i =0; i<seedlings.size(); i++){
            Long key = ids[0] + i + 1;
            PriceSeedling tmp = new PriceSeedling();
            BeanUtils.copyProperties(seedlings.get(i),tmp);
            tmp.setId(key.intValue());
            tmp.setMcode(code+NumberUtil.buildNum(++count,5));
            tmp.setName(materialResult.getName());
            tmp.setCategoryId(materialResult.getCategoryId());
            tmp.setUnit(materialResult.getUnit());
            tmp.setStatus(0);
            tmp.setCreateUser(userId);
            tmp.setCareatetime(now);
            tmp.setRemark(materialResult.getRemark());
            tmp.setExtend(materialResult.getExtend());
            tmp.setExtend2(materialResult.getExtend2());
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(tmp);
        }
        try {
            int[] flags = priceSeedlingDao.batchUpdate(sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("添加失败");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public PriceSeedling getMaterialById(Integer id) {

        return priceSeedlingDao.getMaterialById(id);
    }

    public List<MaterialPriceTableResult> getMaterialHistoryTable(Integer id, String startTime, String endTime, Integer city) {
        List<MaterialPriceTableResult> results = priceSeedlingDao.getMaterialHistoryTable(id,startTime,endTime,city);
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
