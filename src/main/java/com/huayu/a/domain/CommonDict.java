package com.huayu.a.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-6-14 16:07:56.
*/
@Table(name = "common_dict")
public class CommonDict extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,dictKey,dictValue,dictGroup,rank,status;
 //private String updateFiledValue = id=:id,dictKey=:dictKey,dictValue=:dictValue,dictGroup=:dictGroup,rank=:rank,status=:status;
    private Integer id;
    private String dictKey;
    private String dictValue;
    private String dictGroup;
    private Integer rank;
    private Integer status;

    public CommonDict() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictGroup() {
        return dictGroup;
    }

    public void setDictGroup(String dictGroup) {
        this.dictGroup = dictGroup;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
