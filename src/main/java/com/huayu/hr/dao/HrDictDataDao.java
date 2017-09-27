package com.huayu.hr.dao;

import com.huayu.hr.domain.HrDictData;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/6.
 */
@Repository
public class HrDictDataDao extends BasePageDao<HrDictData, Integer> {


    public List<HrDictData> getDictDataByDictValue(HrDictData dict) {
        String sql  = "select dd.id,dd.dictDataKey,dd.dictDataValue,dd.rank,dd.dictId from hr_dict_data dd inner join hr_dict  d on dd.dictId=d.id where d.dictValue = :dictValue and dd.status=0 ORDER BY dd.rank  ASC";
        return super.get(sql,dict);
    }

    public List<HrDictData> getDictDataByDictId(HrDictData dict) {
        String sql  = "select dd.id,dd.dictDataKey,dd.dictDataValue,dd.rank,dd.dictId from hr_dict_data dd  where dd.dictId=:dictId  and dd.status=0 ORDER BY dd.rank  ASC";
        return super.get(sql,dict);
    }

    public HrDictData getDictDataOne(HrDictData dictData) {
        String sql = "select dd.id,dd.dictDataKey,dd.dictDataValue,dd.rank,dd.dictId from hr_dict_data dd inner join hr_dict  d on dd.dictId=d.id where d.dictValue = :dictValue and dd.dictDataValue = :dictDataValue and dd.status=0 ORDER BY dd.rank  ASC";
        return super.getOne(sql,dictData);
    }

    public int addDictData(HrDictData dictData) {
        Long key = super.getKey("hr_dict_data",dictData);
        dictData.setId(key.intValue());
        return super.post(dictData);
    }

    public int deleteDictData(HrDictData dictData) {
        String sql = "update hr_dict_data set status=1  where dictId=:dictId and id=:id";
        return super.put(sql,dictData);
    }
}
