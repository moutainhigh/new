package com.huayu.cost.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;
import java.lang.Integer;

/**
 * 部门费用预算主表
* Created by DengPeng on 2017-7-7 9:35:19.
*/
@Table(name = "cost_department")
public class CostDepartment extends BaseDomain  implements Serializable{

 //private String updateFiledKey = did,companyId,departmentId,year,planMoney,createDate;
 //private String updateFiledValue = did=:did,companyId=:companyId,departmentId=:departmentId,year=:year,planMoney=:planMoney,createDate=:createDate;
    private Long did;
    private Long companyId;
    private Long departmentId;
    private Integer year;
    private BigDecimal planMoney;
    private Date createDate;

    public CostDepartment() {
        this.dt = "desc";
        this.dtn = "did";
        this.pageSize = 10;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getPlanMoney() {
        return planMoney;
    }

    public void setPlanMoney(BigDecimal planMoney) {
        this.planMoney = planMoney;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
