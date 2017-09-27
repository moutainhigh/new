package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;

/**
* Created by DengPeng on 2016-12-6 17:36:52.
*/
@Table(name = "hr_dict_data")
public class HrDictData extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -7659044046816235156L;
    private Integer id;
    private String dictDataKey;
    private Integer dictDataValue;
    private String  dictId;
    private Integer rank;
    private Integer status;

    @TableField
    private String dictValue;


    public HrDictData() {
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

    public String getDictDataKey() {
        return dictDataKey;
    }

    public void setDictDataKey(String dictDataKey) {
        this.dictDataKey = dictDataKey;
    }

    public Integer getDictDataValue() {
        return dictDataValue;
    }

    public void setDictDataValue(Integer dictDataValue) {
        this.dictDataValue = dictDataValue;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
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
