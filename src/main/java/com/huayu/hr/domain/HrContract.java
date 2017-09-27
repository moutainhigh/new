package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-3-13 11:45:51.
*/
@Table(name = "hr_contract")
public class HrContract extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -7516044782351912127L;
   //private String updateFiledKey = id,contractNo,empId,company,operType,periodType,periodCount,contractStartTime,contractEndTime,isProb,probCount,probStartTime,probEndTime,contractType,isSecret,contractCompany,note,createtime,updatetime;
 //private String updateFiledValue = id=:id,contractNo=:contractNo,empId=:empId,company=:company,operType=:operType,periodType=:periodType,periodCount=:periodCount,contractStartTime=:contractStartTime,contractEndTime=:contractEndTime,isProb=:isProb,probCount=:probCount,probStartTime=:probStartTime,probEndTime=:probEndTime,contractType=:contractType,isSecret=:isSecret,contractCompany=:contractCompany,note=:note,createtime=:createtime,updatetime=:updatetime;
    private Integer id;
    private String contractNo;
    private Integer empId;
    private String empNo;
    private String empName;
    private Integer company;
    private Integer empJobId;
    private Integer operType;
    private Integer periodType;
    private String periodCount;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractStartTime;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractEndTime;
    private String isProb;
    private String probCount;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date probStartTime;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date probEndTime;
    private Integer contractType;
    private Integer isSecret;
    private Integer contractCompany;
    private Integer propose;
    private Integer reason;
    private String proveContractNo;
    private BigDecimal compensate;
    private BigDecimal indemnify;
    private String note;
    private Integer status;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private Date updatetime;
    private Integer operUser;


    public HrContract() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrContract(Integer empId) {
        this.empId = empId;
    }

    @TableField
    private Date startTime1;
    @TableField
    private Date startTime2;


    @TableField
    private Date endTime1;
    @TableField
    private Date endTime2;
    @TableField
    private String companyName;

    @TableField
    private Integer department;
    @TableField
    private String departmentName;
    @TableField
    private String jobName;
    @JSONField(format="yyyy-MM-dd")
    @TableField
    private Date arrivalDate;

    @TableField
    private String startDate;
    @TableField
    private String endDate;



    public String getOperTypeStr(){
        if (null==operType){
            return "";
        }
        switch (operType){
            case 1:
                return "签订";
            case 2:
                return "变更";
            case 3:
                return "续签";
            case 4:
                return "解除";
            case 5:
                return "终止";
             default:
                 return "";
        }
    }

    public String getPeriodTypeStr(){
        if (null==periodType){
            return "";
        }
        switch (periodType){
            case 1:
                return "固定期限";
            case 2:
                return "无固定期限";
            case 3:
                return "以完成一定工作任务期限";
            default:
                return "";
        }
    }

    public String getContractTypeStr(){
        if (null==contractType){
            return "";
        }
        switch (contractType){
            case 0:
                return "劳动合同";
            case 1:
                return "聘用协议";
            case 2:
                return "实习协议";
            default:
                return "";
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getEmpJobId() {
        return empJobId;
    }

    public void setEmpJobId(Integer empJobId) {
        this.empJobId = empJobId;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(Date startTime1) {
        this.startTime1 = startTime1;
    }

    public Date getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(Date startTime2) {
        this.startTime2 = startTime2;
    }

    public Date getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(Date endTime1) {
        this.endTime1 = endTime1;
    }

    public Date getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(Date endTime2) {
        this.endTime2 = endTime2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(String periodCount) {
        this.periodCount = periodCount;
    }

    public String getProbCount() {
        return probCount;
    }

    public void setProbCount(String probCount) {
        this.probCount = probCount;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public String getIsProb() {
        return isProb;
    }

    public void setIsProb(String isProb) {
        this.isProb = isProb;
    }


    public Date getProbStartTime() {
        return probStartTime;
    }

    public void setProbStartTime(Date probStartTime) {
        this.probStartTime = probStartTime;
    }

    public Date getProbEndTime() {
        return probEndTime;
    }

    public void setProbEndTime(Date probEndTime) {
        this.probEndTime = probEndTime;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(Integer isSecret) {
        this.isSecret = isSecret;
    }

    public Integer getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(Integer contractCompany) {
        this.contractCompany = contractCompany;
    }

    public Integer getPropose() {
        return propose;
    }

    public void setPropose(Integer propose) {
        this.propose = propose;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getProveContractNo() {
        return proveContractNo;
    }

    public void setProveContractNo(String proveContractNo) {
        this.proveContractNo = proveContractNo;
    }

    public BigDecimal getCompensate() {
        return compensate;
    }

    public void setCompensate(BigDecimal compensate) {
        this.compensate = compensate;
    }

    public BigDecimal getIndemnify() {
        return indemnify;
    }

    public void setIndemnify(BigDecimal indemnify) {
        this.indemnify = indemnify;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getOperUser() {
        return operUser;
    }

    public void setOperUser(Integer operUser) {
        this.operUser = operUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }
}
