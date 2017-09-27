package com.huayu.a.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

/**
* Created by DengPeng on 2017-3-7 13:53:25.
*/
@Table(name = "comm_sequence")
public class CommSequence extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -5827342364712138195L;
    private String name;
    private Long id;

    @TableField
    private Integer offset;

    public CommSequence(String name, Integer offset) {
        this.name = name;
        this.offset = offset;
    }

    public CommSequence() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public CommSequence(String name) {
        this.name = name;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
