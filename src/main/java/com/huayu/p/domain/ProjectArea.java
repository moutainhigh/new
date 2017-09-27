package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;

/**
 * 项目进度管理区域
* Created by ZXL on 2017-5-19 9:56:49.
*/
@Table(name = "project_area")
public class ProjectArea extends BaseDomain  implements Serializable{

    private Integer id;
    private String name;//区域名称
    private Short sort;//排序
    private Short status;//状态1隐藏2显示

    public ProjectArea() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 20;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString(String s) {
        StringBuilder sb = new StringBuilder("?");
        if(dt!=null && s.contains("dt")) {
            sb.append("dt="+dt);
        }
        if(pageSize!=null && s.contains("pageSize")) {
            sb.append("&pageSize="+pageSize);
        }
        if(pageNo!=null && s.contains("pageNo")) {
            sb.append("&pageNo="+pageNo);
        }
        if(dtn!=null && s.contains("dtn")) {
            sb.append("&dtn="+dtn);
        }
        if(s1!=null && s.contains("s1")) {
            sb.append("&s1="+s1);
        }
        return sb.toString();
    }
}
