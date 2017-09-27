package com.huayu.a.service;

import com.huayu.a.dao.CommonDictDao;
import com.huayu.a.domain.CommonDict;
import com.huayu.common.BusinessException;
import com.huayu.inventory.dao.PrMaterialDao;
import com.huayu.inventory.domain.PrMaterial;
import com.huayu.inventory.web.args.CommonDictArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/14.
 */
@Service
public class CommonDictService {

    @Autowired
    private CommonDictDao commonDictDao;
    @Autowired
    private PrMaterialDao prMaterialDao;

    public List<CommonDict> getDictList(String group){
        CommonDict dict = new CommonDict();
        dict.setDictGroup(group);
        return commonDictDao.getDictList(dict);
    }

    public CommonDict getDictByGroupAndValue(String unit, String dictValue) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictGroup(unit);
        commonDict.setDictValue(dictValue);
        return commonDictDao.getOneByUnitAndDictValue(commonDict);
    }

    //TODO 检查单位是否关联材料
    @Transactional
    public void saveDict(String dictValue) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictGroup("unit");
        commonDict.setDictValue(dictValue);
        Long common_dict = commonDictDao.getKey("common_dict", new CommonDict());
        commonDict.setId(common_dict.intValue());
        commonDict.setDictKey(common_dict.toString());
        commonDict.setRank(common_dict.intValue());
        commonDict.setStatus(0);
        commonDictDao.post(commonDict);
    }

    public Page<CommonDict> dictListData(CommonDictArgs commonDictArgs, Pageable pageable) {
        return commonDictDao.dictListData(commonDictArgs, pageable);
    }

    @Transactional
    public void deleteDict(Integer id) {
        this.checkHasUnit(id);
        commonDictDao.deleteDict(id);
    }

    public CommonDict getDictById(Integer id) {
        return commonDictDao.getDictById(id);
    }

    @Transactional
    public void checkHasUnit(Integer id) {
        List<PrMaterial> prMaterials = prMaterialDao.get(new PrMaterial());
        for (PrMaterial prMaterial : prMaterials) {
            if (prMaterial.getUnit() == id) {
                throw  new BusinessException("该单位已经关联材料,不能操作!");
            }
        }
    }

    @Transactional
    public void updateDictValue(String dictValue, Integer id) {
        this.checkHasUnit(id);
        CommonDict commonDict = new CommonDict();
        commonDict.setDictValue(dictValue);
        commonDict.setId(id);
        commonDictDao.updateDict(commonDict);
    }
}
