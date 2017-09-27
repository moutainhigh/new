package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;

/**
 * Created by DengPeng on 2017/1/9.
 */
@Table(name = "data_conversion")
public class DataConversion extends BaseDomain implements Serializable {

    private Integer id;
    private String pk_old;
    private Integer pk_new ;
    private Integer pk_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPk_old() {
        return pk_old;
    }

    public void setPk_old(String pk_old) {
        this.pk_old = pk_old;
    }

    public Integer getPk_new() {
        return pk_new;
    }

    public void setPk_new(Integer pk_new) {
        this.pk_new = pk_new;
    }

    public Integer getPk_type() {
        return pk_type;
    }

    public void setPk_type(Integer pk_type) {
        this.pk_type = pk_type;
    }

}
