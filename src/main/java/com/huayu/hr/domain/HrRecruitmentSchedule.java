package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-5 17:04:17.
*/
@Table(name = "hr_recruitment_schedule")
public class HrRecruitmentSchedule extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,position,plat,region,company,department,numberOfHiring,level,hireDate,numberOfResumes,receiveResumeDate,resumePassedNumber,resumePassedDate,personNumByCall,firstAuditionDate,firstPassedPerson,secondAuditonNumber,secondAuditionDate,secondPassedPerson,finalAuditionNumber,finalAuditionDate,PersonNameToBeHired,telephone,planComePositionDate,comePositionDate,responsiblePerson,remark;
 //private String updateFiledValue = id=:id,position=:position,plat=:plat,region=:region,company=:company,department=:department,numberOfHiring=:numberOfHiring,level=:level,hireDate=:hireDate,numberOfResumes=:numberOfResumes,receiveResumeDate=:receiveResumeDate,resumePassedNumber=:resumePassedNumber,resumePassedDate=:resumePassedDate,personNumByCall=:personNumByCall,firstAuditionDate=:firstAuditionDate,firstPassedPerson=:firstPassedPerson,secondAuditonNumber=:secondAuditonNumber,secondAuditionDate=:secondAuditionDate,secondPassedPerson=:secondPassedPerson,finalAuditionNumber=:finalAuditionNumber,finalAuditionDate=:finalAuditionDate,PersonNameToBeHired=:PersonNameToBeHired,telephone=:telephone,planComePositionDate=:planComePositionDate,comePositionDate=:comePositionDate,responsiblePerson=:responsiblePerson,remark=:remark;
 @NotNull(message = "Id不能为空", groups = {GroupInsert.class, GroupDelete.class})
    private Integer id;
    private String position;
    private String plat;
    private Integer companyId;
    private String company;
    private String department;
    private String name;
    private String telephone;
    private Integer numberOfHiring;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date planOnDutyDate;
    private Integer level;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date hireDate;
    private Integer numberOfResumes;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date receiveResumeDate;
    private Integer resumePassedNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date resumePassedDate;
    private Integer personNumByCall;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date firstAuditionDate;
    private String firstPassedPerson;
    private Integer secondAuditonNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date secondAuditionDate;
    private String secondPassedPerson;
    private Integer finalAuditionNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date finalAuditionDate;
    private String personNameToBeHired;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date planComePositionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date comePositionDate;
    private String responsiblePerson;
    private String remark;
    private String description;
    private Integer processStatus;
    private Integer status;
    private Date createDate;
    private Integer createUser;
    private Date deleteDate;
    private Integer deleteUser;

    @TableField
    private Integer index;
    @TableField
    private String processStatusStr;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public HrRecruitmentSchedule() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPlanOnDutyDate() {
        return planOnDutyDate;
    }

    public void setPlanOnDutyDate(Date planOnDutyDate) {
        this.planOnDutyDate = planOnDutyDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }

    public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getPosition() {
      return position;
   }

   public void setPosition(String position) {
      this.position = position;
   }

   public String getPlat() {
      return plat;
   }

   public void setPlat(String plat) {
      this.plat = plat;
   }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getNumberOfHiring() {
      return numberOfHiring;
   }

   public void setNumberOfHiring(Integer numberOfHiring) {
      this.numberOfHiring = numberOfHiring;
   }

   public Integer getLevel() {
      return level;
   }

   public void setLevel(Integer level) {
      this.level = level;
   }

   public Date getHireDate() {
      return hireDate;
   }

   public void setHireDate(Date hireDate) {
      this.hireDate = hireDate;
   }

   public Integer getNumberOfResumes() {
      return numberOfResumes;
   }

   public void setNumberOfResumes(Integer numberOfResumes) {
      this.numberOfResumes = numberOfResumes;
   }

   public Date getReceiveResumeDate() {
      return receiveResumeDate;
   }

   public void setReceiveResumeDate(Date receiveResumeDate) {
      this.receiveResumeDate = receiveResumeDate;
   }

   public Integer getResumePassedNumber() {
      return resumePassedNumber;
   }

   public void setResumePassedNumber(Integer resumePassedNumber) {
      this.resumePassedNumber = resumePassedNumber;
   }

   public Date getResumePassedDate() {
      return resumePassedDate;
   }

   public void setResumePassedDate(Date resumePassedDate) {
      this.resumePassedDate = resumePassedDate;
   }

   public Integer getPersonNumByCall() {
      return personNumByCall;
   }

   public void setPersonNumByCall(Integer personNumByCall) {
      this.personNumByCall = personNumByCall;
   }

   public Date getFirstAuditionDate() {
      return firstAuditionDate;
   }

   public void setFirstAuditionDate(Date firstAuditionDate) {
      this.firstAuditionDate = firstAuditionDate;
   }

   public String getFirstPassedPerson() {
      return firstPassedPerson;
   }

   public void setFirstPassedPerson(String firstPassedPerson) {
      this.firstPassedPerson = firstPassedPerson;
   }

   public Integer getSecondAuditonNumber() {
      return secondAuditonNumber;
   }

   public void setSecondAuditonNumber(Integer secondAuditonNumber) {
      this.secondAuditonNumber = secondAuditonNumber;
   }

   public Date getSecondAuditionDate() {
      return secondAuditionDate;
   }

   public void setSecondAuditionDate(Date secondAuditionDate) {
      this.secondAuditionDate = secondAuditionDate;
   }

   public String getSecondPassedPerson() {
      return secondPassedPerson;
   }

   public void setSecondPassedPerson(String secondPassedPerson) {
      this.secondPassedPerson = secondPassedPerson;
   }

   public Integer getFinalAuditionNumber() {
      return finalAuditionNumber;
   }

   public void setFinalAuditionNumber(Integer finalAuditionNumber) {
      this.finalAuditionNumber = finalAuditionNumber;
   }

   public Date getFinalAuditionDate() {
      return finalAuditionDate;
   }

   public void setFinalAuditionDate(Date finalAuditionDate) {
      this.finalAuditionDate = finalAuditionDate;
   }

    public String getPersonNameToBeHired() {
        return personNameToBeHired;
    }

    public void setPersonNameToBeHired(String personNameToBeHired) {
        this.personNameToBeHired = personNameToBeHired;
    }

    public String getTelephone() {
      return telephone;
   }

   public void setTelephone(String telephone) {
      this.telephone = telephone;
   }

   public Date getPlanComePositionDate() {
      return planComePositionDate;
   }

   public void setPlanComePositionDate(Date planComePositionDate) {
      this.planComePositionDate = planComePositionDate;
   }

   public Date getComePositionDate() {
      return comePositionDate;
   }

   public void setComePositionDate(Date comePositionDate) {
      this.comePositionDate = comePositionDate;
   }

   public String getResponsiblePerson() {
      return responsiblePerson;
   }

   public void setResponsiblePerson(String responsiblePerson) {
      this.responsiblePerson = responsiblePerson;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessStatusStr() {
        return processStatusStr;
    }

    public void setProcessStatusStr(String processStatusStr) {
        this.processStatusStr = processStatusStr;
    }
}
