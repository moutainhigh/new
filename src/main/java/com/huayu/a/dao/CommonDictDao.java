package com.huayu.a.dao;

import com.huayu.a.domain.CommonDict;
import com.huayu.inventory.web.args.CommonDictArgs;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/14.
 */
@Repository
public class CommonDictDao extends BasePageDao<CommonDict,Integer>{

    public List<CommonDict> getDictList(CommonDict dict){
        String sql = "select * from common_dict where dictGroup =:dictGroup and status = 0";
        return super.get(sql,dict);
    }

    public CommonDict getOneByUnitAndDictValue(CommonDict commonDict) {
        String sql = "select * from common_dict where dictGroup =:dictGroup and dictValue =:dictValue and status = 0";
        return super.getOne(sql, commonDict);
    }

    public Page<CommonDict> dictListData(CommonDictArgs commonDictArgs, Pageable pageable) {
        StringBuffer sql = new StringBuffer("select * from common_dict where status = 0");
        if (StringUtils.isNotBlank(commonDictArgs.getDictGroup())) {
            sql.append(" and dictGroup =:dictGroup");
        }
        if (StringUtils.isNotBlank(commonDictArgs.getDictValue())) {
            sql.append(" and position(:dictValue in dictValue)");
        }
        Page<CommonDict> commonDicts = super.get(sql.toString(), commonDictArgs, pageable);
        return commonDicts;
    }

    public void deleteDict(Integer id) {
        String sql = "update common_dict set status = 1 where id =:id";
        CommonDict commonDict = new CommonDict();
        commonDict.setId(id);
        super.put(sql, commonDict);
    }

    public CommonDict getDictById(Integer id) {
        String sql = "select * from common_dict where id = :id";
        CommonDict commonDict = new CommonDict();
        commonDict.setId(id);
        return super.getOne(sql, commonDict);
    }

    public void updateDict(CommonDict commonDict) {
        String sql = "update common_dict set dictValue =:dictValue where id =:id";
        super.put(sql, commonDict);
    }
}
