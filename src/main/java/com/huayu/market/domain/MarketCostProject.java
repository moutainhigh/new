package com.huayu.market.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.Long;
import java.lang.Short;
import java.lang.String;
import java.lang.Integer;

/**
 * 项目
* Created by DengPeng on 2017-6-27 16:27:55.
*/
@Table(name = "market_cost_project")
public class MarketCostProject extends BaseDomain  implements Serializable{

 //private String updateFiledKey = projectId,companyId,projectCode,name,type,isOwn;
 //private String updateFiledValue = projectId=:projectId,companyId=:companyId,projectCode=:projectCode,name=:name,type=:type,isOwn=:isOwn;
    private Long projectId;
    private Integer companyId;
    private String projectCode;
    private String name;
    private Short type;
    private Short isOwn;

    @TableField
    private Integer year;//年度
    @TableField
    private String companyName;
    @TableField
    private String isOwnName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsOwnName() {
        return isOwnName;
    }

    public void setIsOwnName(String isOwnName) {
        this.isOwnName = isOwnName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(Short isOwn) {
        this.isOwn = isOwn;
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
        if(i1!=null && s.contains("i1")) {
            sb.append("&i1="+i1);
        }
        if(i2!=null && s.contains("i2")) {
            sb.append("&i2="+i2);
        }
        return sb.toString();
    }
}
