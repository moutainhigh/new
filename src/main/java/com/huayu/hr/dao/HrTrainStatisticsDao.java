package com.huayu.hr.dao;

import com.huayu.hr.domain.HrTrainStatistics;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/2/21.
 */
@Repository
public class HrTrainStatisticsDao extends BasePageDao<HrTrainStatistics,Integer>{

    @Override
    public int post(HrTrainStatistics a) {
        Long longId = super.getKey("hr_train_statistics",a);
        a.setId(longId.intValue());
        return super.post(a);
    }

    public int deleteData(HrTrainStatistics entity){
        String sql = "update hr_train_statistics set status=1 where year=:year and month=:month and plateId=:plateId";
        return super.put(sql,entity);
    }

    public List<HrTrainStatistics> getList(HrTrainStatistics entity){
        StringBuilder stringBuilder = new StringBuilder("select sum(hts.empSum) empSum,sum(hts.studyTimeSum) studyTimeSum, hts.plateId from hr_train_statistics hts");
        stringBuilder.append(" where  hts.status=:status ");
        if (StringUtils.isNotBlank(entity.getMonth())){
            stringBuilder.append(" and hts.month=:month");
        }
        if (StringUtils.isNotBlank(entity.getYear())){
            stringBuilder.append(" and hts.year=:year");
        }
        if (null!=entity.getQuarter()){
            stringBuilder.append(" and hts.quarter=:quarter");
        }
        stringBuilder.append(" group by hts.plateId ");
        return super.get(stringBuilder.toString(),entity);
    }

}
