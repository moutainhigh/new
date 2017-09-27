package com.huayu.hr.service;

import com.alibaba.fastjson.JSON;
import com.huayu.hr.dao.HrDictDao;
import com.huayu.hr.dao.HrDictDataDao;
import com.huayu.hr.domain.HrDict;
import com.huayu.hr.domain.HrDictData;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2016/12/6.
 */
@Service
public class HrDictService {

    @Autowired
    private HrDictDao hrDictDao;
    @Autowired
    private HrDictDataDao hrDictDataDao;

    /**
     * 根据字典值 获取字典数据
     * @param dictValue
     * @return
     */
    @Cacheable(value = "hr-dict-cache",key = "methodName+'_'+#dictValue")
    public List<HrDictData> getDictDataByDictValue(String dictValue){
        HrDictData dict = new HrDictData();
        dict.setDictValue(dictValue);
        return hrDictDataDao.getDictDataByDictValue(dict);
    }

    /**
     * 根据字典id获取字典数据
     * @param dictId
     * @return
     */
    @Cacheable(value = "hr-dict-cache",key = "methodName+'_'+#dictId")
    public List<HrDictData> getDictDataByDictId(Integer dictId){
        HrDictData dict = new HrDictData();
        dict.setDictId(String.valueOf(dictId));
        return hrDictDataDao.getDictDataByDictId(dict);
    }

    /**
     * 根据字典value 或者所有子级字典
     * @param dictValue
     * @return
     */
    @Cacheable(value = "hr-dict-cache",key = "methodName+'_'+#dictValue")
    public List<HrDict> getChildDictByDictValue(String dictValue){
        HrDict dict = new HrDict();
        dict.setDictValue(dictValue);
        return hrDictDao.getChildDictByDictValue(dict);
    }

    /**
     * 根据字典value 获取所有子级字典数据
     * @param dictValue
     * @return
     */
    @Cacheable(value = "hr-dict-cache",key = "methodName+'_'+#dictValue")
    public  Map<String,List<HrDictData>> getAllDictDataByDictValue(String dictValue){
        Map<String,List<HrDictData>> mapList = null;
        List<HrDict> childDict = this.getChildDictByDictValue(dictValue);
        if (null!=childDict){
            mapList = new HashMap<>();
            for (HrDict dict : childDict){
                HrDictData dictData = new HrDictData();
                dictData.setDictId(String.valueOf(dict.getId()));
                mapList.put(dict.getDictValue(),hrDictDataDao.getDictDataByDictId(dictData));
            }
        }
        return mapList;
    }

    /**
     * 根据字典数据值 和 字典值 获取单个 字典数据
     * @param dictValue
     * @param dictDataValue
     * @return
     */
    @Cacheable(value = "hr-dict-cache",key = "methodName+'_'+#dictValue+'_'+#dictDataValue")
    public HrDictData getDictDataOne(String dictValue, Integer dictDataValue) {
        if (null==dictDataValue){
            return null;
        }
        HrDictData dictData = new HrDictData();
        dictData.setDictValue(dictValue);
        dictData.setDictDataValue(dictDataValue);
        return hrDictDataDao.getDictDataOne(dictData);
    }

    @CacheEvict(value = "hr-dict-cache",allEntries=true)
    @Transactional
    public BaseResult addDictData(String dictDataArray, Integer dictId) {
        BaseResult baseResult = new BaseResult();
        List<HrDictData> list = JSON.parseArray(dictDataArray, HrDictData.class);
        if (!CollectionUtils.isEmpty(list)){
            for (HrDictData dictData : list){
                dictData.setDictId(String.valueOf(dictId));
                dictData.setStatus(0);
                if (StringUtils.isEmpty(dictData.getDictDataKey())||StringUtils.isEmpty(dictData.getDictDataValue())){
                    throw new BusinessException("添加字典数据错误");
                }
                if (hrDictDataDao.addDictData(dictData)==0){
                    throw new BusinessException("添加字典数据失败");
                }
            }
            baseResult.setSuccess();
        }
        return baseResult;
    }

    @CacheEvict(value = "hr-dict-cache",allEntries=true)
    @Transactional
    public BaseResult deleteDictData(Integer id, Integer dictId) {
        BaseResult baseResult = new BaseResult();
        HrDictData dictData = new HrDictData();
        dictData.setId(id);
        dictData.setDictId(String.valueOf(dictId));
        if (hrDictDataDao.deleteDictData(dictData)==1){
            baseResult.setSuccess();
        }
        return baseResult;
    }

    public Map<Integer,String> convert(List<HrDictData> contractCompany){
        Map<Integer,String> stringMap = new HashMap<>();
        for (HrDictData c : contractCompany){
            stringMap.put(c.getDictDataValue(),c.getDictDataKey());
        }
        return stringMap;
    }

}
