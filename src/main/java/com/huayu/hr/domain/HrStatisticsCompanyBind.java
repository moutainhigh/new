package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
* Created by DengPeng on 2017-2-16 15:24:52.
*/
@Table(name = "hr_statistics_company_bind")
public class HrStatisticsCompanyBind extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 568542333174963170L;
    //private String updateFiledKey = id,name,companyId,parentId;
 //private String updateFiledValue = id=:id,name=:name,companyId=:companyId,parentId=:parentId;
    private Integer id;
    private String name;
    private Integer companyId;
    private Integer companyCode;
    private Integer parentId;
    private Integer isParent;
    private Integer status;

    public HrStatisticsCompanyBind() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private HrStatisticsData statisticsData;

    @TableField
    private HrStatisticsEmpChgData statisticsChgData;

    @TableField
    private List<HrStatisticsCompanyBind> childBindData;

    public HrStatisticsEmpChgData getStatisticsChgData() {
        return statisticsChgData;
    }

    public void setStatisticsChgData(HrStatisticsEmpChgData statisticsChgData) {
        this.statisticsChgData = statisticsChgData;
    }

    public HrStatisticsData getStatisticsData() {
        return statisticsData;
    }

    public void setStatisticsData(HrStatisticsData statisticsData) {
        this.statisticsData = statisticsData;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<HrStatisticsCompanyBind> getChildBindData() {
        return childBindData;
    }

    public void setChildBindData(List<HrStatisticsCompanyBind> childBindData) {
        this.childBindData = childBindData;
    }
}
