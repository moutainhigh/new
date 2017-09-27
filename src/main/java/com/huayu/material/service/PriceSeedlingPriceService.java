package com.huayu.material.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.material.dao.PriceSeedlingPriceDao;
import com.huayu.material.domain.PriceSeedling;
import com.huayu.material.domain.PriceSeedlingPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/3.
 */
@Service
public class PriceSeedlingPriceService {

    @Resource
    private PriceSeedlingPriceDao priceSeedlingPriceDao;


    public Page<PriceSeedlingPrice> materialPriceListData(PriceSeedlingPrice priceResult, Pageable pageable) {
        if (!StringUtils.hasText(priceResult.getReportYearMonth())){
            priceResult.setReportYearMonth(DateTimeUtil.dateTimeToString("yyyyMM"));
        }
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.*,mc.`name` categoryName,mp.price pcq,mp1.price pcd,mp2.price pjs,mp3.price phf,mp4.price psz FROM price_seedling m");
        sql.append(" LEFT JOIN price_seedling_category mc ON m.categoryId = mc.id ");
        sql.append(" LEFT JOIN (SELECT * FROM price_seedling_price WHERE city = 1 AND reportYearMonth=:reportYearMonth) mp ON m.id = mp.matId ");
        sql.append(" LEFT JOIN (SELECT * FROM price_seedling_price WHERE city = 2 AND reportYearMonth=:reportYearMonth) mp1 ON m.id = mp1.matId");
        sql.append(" LEFT JOIN (SELECT * FROM price_seedling_price WHERE city = 3 AND reportYearMonth=:reportYearMonth) mp2 ON m.id = mp2.matId");
        sql.append(" LEFT JOIN (SELECT * FROM price_seedling_price WHERE city = 4 AND reportYearMonth=:reportYearMonth) mp3 ON m.id = mp3.matId ");
        sql.append(" LEFT JOIN (SELECT * FROM price_seedling_price WHERE city = 5 AND reportYearMonth=:reportYearMonth) mp4 ON m.id = mp4.matId");
        sql.append(" where m.`status` = 0");
        if (StringUtils.hasText(priceResult.getName())){
            sql.append(" and position(:name in m.name)");
        }

        if (StringUtils.hasText(priceResult.getMcode())){
            sql.append(" and  position( :mcode in m.mcode)");
        }

        if (StringUtils.hasText(priceResult.getSpecification())){
            sql.append(" and  position( :specification in m.specification)");
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getSpecification2())){
            sql.append(" and  position( :specification2 in m.specification2)");
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getSpecification3())){
            sql.append(" and  position( :specification3 in m.specification3)");
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getSpecification4())){
            sql.append(" and  position( :specification4 in m.specification4)");
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getSpecification5())){
            sql.append(" and  position( :specification5 in m.specification5)");
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getSpecification6())){
            sql.append(" and  position( :specification6 in m.specification6)");
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getSpecification7())){
            sql.append(" and  position( :specification7 in m.specification7)");
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getExtend())){
            sql.append(" and  position( :extend in m.extend)");
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(priceResult.getExtend2())){
            sql.append(" and  position( :extend2 in m.extend2)");
        }

        if (priceResult.getCategoryId() != null ){
            sql.append(" AND mc.code REGEXP (SELECT CONCAT('^',`code`) FROM price_seedling_category WHERE id = :categoryId)");
        }
        Page<PriceSeedlingPrice> results = priceSeedlingPriceDao.get(sql.toString(), priceResult, pageable);
        return results;
    }

    public Page<PriceSeedlingPrice> materialHistoryPriceListData(PriceSeedlingPrice priceResult, Pageable pageable,Integer city) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT mhp.id,m.`name`,m.specification,mhp.price,mhp.createtime historyPriceTime from price_seedling_history_price mhp");
        sb.append(" LEFT JOIN price_seedling m ON m.id = mhp.matId");
        sb.append(" where mhp.matId =:id AND mhp.city = "+city);
        if(priceResult.getStartTime() != null && priceResult.getEndTime() != null){
            sb.append(" AND mhp.createtime BETWEEN :startTime AND :endTime");
        }
        Page<PriceSeedlingPrice> results = priceSeedlingPriceDao.get(sb.toString(), priceResult, pageable);
        return results;
    }

    @Transactional
    public void updateMaterial(PriceSeedling priceSeedling, Integer city) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        PriceSeedlingPrice priceResult = new PriceSeedlingPrice();
        priceResult.setMatId(priceSeedling.getId());
        priceResult.setCity(city);
        priceResult.setReportYearMonth(sdf.format(now));
        PriceSeedlingPrice old = priceSeedlingPriceDao.getMaterialPriceByYM(priceResult);
        int userId = SpringSecurityUtil.getUser().getUserId().intValue();
        priceResult.setPrice(priceSeedling.getPrice());
        priceResult.setCreateUser(userId);
        priceResult.setCreatetime(now);
        if(priceSeedlingPriceDao.insertHistoryPrice(priceResult)!=1){
            throw new BusinessException("添加历史价格失败");
        }
        if (null==old){
            if (priceSeedlingPriceDao.initPrice(priceResult)!=1){
                throw new BusinessException("添加价格失败");
            }
        }else{
            old.setUpdateTime(now);
            old.setUpdateUser(userId);
            old.setPrice(priceSeedling.getPrice());
            if (priceSeedlingPriceDao.updateMaterial(old)!=1){
                throw new BusinessException("修改价格失败");
            }
        }
    }
}
