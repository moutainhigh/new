package com.huayu.material.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.material.dao.WorkMaterialPriceDao;
import com.huayu.material.domain.WorkMaterial;
import com.huayu.material.domain.WorkMaterialPrice;
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
public class WorkMaterialPriceService {
    @Resource
    private WorkMaterialPriceDao workMaterialPriceDao;

    public Page<WorkMaterialPrice> materialPriceListData(WorkMaterialPrice priceResult, Pageable pageable) {
        if (!StringUtils.hasText(priceResult.getReportYearMonth())){
            priceResult.setReportYearMonth(DateTimeUtil.dateTimeToString("yyyyMM"));
        }
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT mp.id,m.mcode,m.`name`,m.specification,m.unit,mp.price pcq,mp1.price pcd,mp2.price pjs,mp3.price phf,mp4.price psz FROM work_material m");
        sql.append(" LEFT JOIN (SELECT * FROM work_material_price WHERE city = 1 AND reportYearMonth=:reportYearMonth) mp ON m.id = mp.matId ");
        sql.append(" LEFT JOIN (SELECT * FROM work_material_price WHERE city = 2 AND reportYearMonth=:reportYearMonth) mp1 ON m.id = mp1.matId");
        sql.append(" LEFT JOIN (SELECT * FROM work_material_price WHERE city = 3 AND reportYearMonth=:reportYearMonth) mp2 ON m.id = mp2.matId");
        sql.append(" LEFT JOIN (SELECT * FROM work_material_price WHERE city = 4 AND reportYearMonth=:reportYearMonth) mp3 ON m.id = mp3.matId ");
        sql.append(" LEFT JOIN (SELECT * FROM work_material_price WHERE city = 5 AND reportYearMonth=:reportYearMonth) mp4 ON m.id = mp4.matId");
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
        return workMaterialPriceDao.get(sql.toString(), priceResult, pageable);
    }

    public Page<WorkMaterialPrice> materialHistoryPriceListData(WorkMaterialPrice priceResult, Pageable pageable, Integer city) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT mhp.id,m.`name`,m.specification,mhp.price,mhp.createtime historyPriceTime from work_material_history_price mhp");
        sb.append(" LEFT OUTER JOIN work_material m ON m.id = mhp.matId");
        sb.append(" where mhp.matId = :id AND mhp.city = "+city);
        if(priceResult.getStartTime() != null && priceResult.getEndTime() != null){
            sb.append(" AND mhp.createtime BETWEEN :startTime AND :endTime");
        }
        Page<WorkMaterialPrice> results = workMaterialPriceDao.get(sb.toString(), priceResult, pageable);
        return results;
    }

    @Transactional
    public void updateMaterial(WorkMaterial materialResult, Integer city) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        WorkMaterialPrice priceResult = new WorkMaterialPrice();
        priceResult.setMatId(materialResult.getId());
        priceResult.setCity(city);
        priceResult.setReportYearMonth(sdf.format(now));
        WorkMaterialPrice old = workMaterialPriceDao.getMaterialPriceByYM(priceResult);
        int userId = SpringSecurityUtil.getUser().getUserId().intValue();
        priceResult.setPrice(materialResult.getPrice());
        priceResult.setCreateUser(userId);
        priceResult.setCreatetime(now);
        if(workMaterialPriceDao.insertHistoryPrice(priceResult)!=1){
            throw new BusinessException("添加历史价格失败");
        }
        if (null==old){
            if (workMaterialPriceDao.initPrice(priceResult)!=1){
                throw new BusinessException("添加价格失败");
            }
        }else{
            old.setUpdateTime(now);
            old.setUpdateUser(userId);
            old.setPrice(materialResult.getPrice());
            if (workMaterialPriceDao.updateMaterial(old)!=1){
                throw new BusinessException("修改价格失败");
            }
        }
    }
}
