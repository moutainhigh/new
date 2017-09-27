package com.huayu.cost.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 部门费用预算详细表
* Created by DengPeng on 2017-7-7 9:35:41.
*/
@Table(name = "cost_department_detail")
public class CostDepartmentDetail extends BaseDomain  implements Serializable{

 //private String updateFiledKey = ddId,code,companyId,departmentId,year,firstCode,secondCode,planMoney,freezeMoney,usedMoney,usableMoney,createDate,did,planMoney2,planMoney3,planMoney4,planMoney5,planMoney6,planMoney7,planMoney8,planMoney9,planMoney10,planMoney11,planMoney12;
 //private String updateFiledValue = ddId=:ddId,code=:code,companyId=:companyId,departmentId=:departmentId,year=:year,firstCode=:firstCode,secondCode=:secondCode,planMoney=:planMoney,freezeMoney=:freezeMoney,usedMoney=:usedMoney,usableMoney=:usableMoney,createDate=:createDate,did=:did,planMoney2=:planMoney2,planMoney3=:planMoney3,planMoney4=:planMoney4,planMoney5=:planMoney5,planMoney6=:planMoney6,planMoney7=:planMoney7,planMoney8=:planMoney8,planMoney9=:planMoney9,planMoney10=:planMoney10,planMoney11=:planMoney11,planMoney12=:planMoney12;
    private Long ddId;
    private String code;
    private Long companyId;
    private Long departmentId;
    private Integer year;
    private String firstCode;
    private String secondCode;
    private BigDecimal planMoney;
    private BigDecimal freezeMoney;
    private BigDecimal usedMoney;
    private BigDecimal usableMoney;
    private Date createDate;
    private Long did;
    private BigDecimal planMoney2;
    private BigDecimal planMoney3;
    private BigDecimal planMoney4;
    private BigDecimal planMoney5;
    private BigDecimal planMoney6;
    private BigDecimal planMoney7;
    private BigDecimal planMoney8;
    private BigDecimal planMoney9;
    private BigDecimal planMoney10;
    private BigDecimal planMoney11;
    private BigDecimal planMoney12;

    public CostDepartmentDetail() {
        this.dt = "desc";
        this.dtn = "ddId";
        this.pageSize = 10;
    }

    public Long getDdId() {
        return ddId;
    }

    public void setDdId(Long ddId) {
        this.ddId = ddId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public BigDecimal getPlanMoney() {
        return planMoney;
    }

    public void setPlanMoney(BigDecimal planMoney) {
        this.planMoney = planMoney;
    }

    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public BigDecimal getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }

    public BigDecimal getUsableMoney() {
        return usableMoney;
    }

    public void setUsableMoney(BigDecimal usableMoney) {
        this.usableMoney = usableMoney;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public BigDecimal getPlanMoney2() {
        return planMoney2;
    }

    public void setPlanMoney2(BigDecimal planMoney2) {
        this.planMoney2 = planMoney2;
    }

    public BigDecimal getPlanMoney3() {
        return planMoney3;
    }

    public void setPlanMoney3(BigDecimal planMoney3) {
        this.planMoney3 = planMoney3;
    }

    public BigDecimal getPlanMoney4() {
        return planMoney4;
    }

    public void setPlanMoney4(BigDecimal planMoney4) {
        this.planMoney4 = planMoney4;
    }

    public BigDecimal getPlanMoney5() {
        return planMoney5;
    }

    public void setPlanMoney5(BigDecimal planMoney5) {
        this.planMoney5 = planMoney5;
    }

    public BigDecimal getPlanMoney6() {
        return planMoney6;
    }

    public void setPlanMoney6(BigDecimal planMoney6) {
        this.planMoney6 = planMoney6;
    }

    public BigDecimal getPlanMoney7() {
        return planMoney7;
    }

    public void setPlanMoney7(BigDecimal planMoney7) {
        this.planMoney7 = planMoney7;
    }

    public BigDecimal getPlanMoney8() {
        return planMoney8;
    }

    public void setPlanMoney8(BigDecimal planMoney8) {
        this.planMoney8 = planMoney8;
    }

    public BigDecimal getPlanMoney9() {
        return planMoney9;
    }

    public void setPlanMoney9(BigDecimal planMoney9) {
        this.planMoney9 = planMoney9;
    }

    public BigDecimal getPlanMoney10() {
        return planMoney10;
    }

    public void setPlanMoney10(BigDecimal planMoney10) {
        this.planMoney10 = planMoney10;
    }

    public BigDecimal getPlanMoney11() {
        return planMoney11;
    }

    public void setPlanMoney11(BigDecimal planMoney11) {
        this.planMoney11 = planMoney11;
    }

    public BigDecimal getPlanMoney12() {
        return planMoney12;
    }

    public void setPlanMoney12(BigDecimal planMoney12) {
        this.planMoney12 = planMoney12;
    }
}
