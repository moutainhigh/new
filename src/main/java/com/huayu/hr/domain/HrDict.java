package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2016-12-6 17:36:32.
*/
@Table(name = "hr_dict")
public class HrDict extends BaseDomain implements Serializable {

    @TableField
    private static final long serialVersionUID = 7793153387703103049L;
    private Integer id;
    private String dictName;
    private String dictValue;
    private Integer parentId;

    public HrDict() {
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

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
