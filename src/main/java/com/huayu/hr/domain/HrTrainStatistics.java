package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* Created by DengPeng on 2017-2-21 14:42:44.
*/
@Table(name = "hr_train_statistics")
public class HrTrainStatistics extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,companyId,empSum,studyTimeSum,perStudyTime,note,year,month,status,createtime;
 //private String updateFiledValue = id=:id,companyId=:companyId,empSum=:empSum,studyTimeSum=:studyTimeSum,perStudyTime=:perStudyTime,note=:note,year=:year,month=:month,status=:status,createtime=:createtime;
    private Integer id;
    private Integer plateId;
    private Integer empSum;
    private BigDecimal studyTimeSum;
    private String note;
    private String year;
    private String month;
    private Integer quarter;
    private Integer status;
    private Date createtime;

    public HrTrainStatistics() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private List<HrTrainStatistics> children;

    @TableField
    private String companyName;

    @TableField
    private BigDecimal perStudyTime;

    public void  addEmpSum(Integer data){
        if (null==this.empSum){
            this.empSum = 0;
        }
        this.empSum=this.empSum+data;
    }

    public void  addStudyTimeSum(BigDecimal data){
        if (null==this.studyTimeSum){
            this.studyTimeSum = new BigDecimal(0);
        }
        this.studyTimeSum=this.studyTimeSum.add(data);
    }

    public String getPlate(){
        if (null!=plateId){
            switch (plateId){
                case 0:
                    return "集团";
                case 1:
                    return "商业";
                case 2:
                    return "金控";
                case 3:
                    return "建筑";
                case 4:
                    return "开发";
                case 5:
                    return "物业";
            }
        }
        return "";
    }

    public BigDecimal getPerStudyTime() {
        return perStudyTime;
    }

    public void setPerStudyTime(BigDecimal perStudyTime) {
        this.perStudyTime = perStudyTime;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpSum() {
        return empSum;
    }

    public void setEmpSum(Integer empSum) {
        this.empSum = empSum;
    }

    public BigDecimal getStudyTimeSum() {
        return studyTimeSum;
    }

    public void setStudyTimeSum(BigDecimal studyTimeSum) {
        this.studyTimeSum = studyTimeSum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public List<HrTrainStatistics> getChildren() {
        return children;
    }

    public void setChildren(List<HrTrainStatistics> children) {
        this.children = children;
    }
}
