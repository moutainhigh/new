package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-8-15 15:26:49.
*/
@Table(name = "hr_recruitment_needs")
public class HrRecruitmentNeeds extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,company,department,job,addNum,askDate,companyLevel,manageGroup,departmentLevel,dutyLevel,planJoinDate,education,profession,sex,age,foreignLang,profesRequire,managePower,workExperience,acceptOutWork,jobResponsi,reportRelationship,salaryRange,jobPrepared,onDutyNum,planAdd,reason,outPlanAdd,reason2,note,status,createtime;
 //private String updateFiledValue = id=:id,company=:company,department=:department,job=:job,addNum=:addNum,askDate=:askDate,companyLevel=:companyLevel,manageGroup=:manageGroup,departmentLevel=:departmentLevel,dutyLevel=:dutyLevel,planJoinDate=:planJoinDate,education=:education,profession=:profession,sex=:sex,age=:age,foreignLang=:foreignLang,profesRequire=:profesRequire,managePower=:managePower,workExperience=:workExperience,acceptOutWork=:acceptOutWork,jobResponsi=:jobResponsi,reportRelationship=:reportRelationship,salaryRange=:salaryRange,jobPrepared=:jobPrepared,onDutyNum=:onDutyNum,planAdd=:planAdd,reason=:reason,outPlanAdd=:outPlanAdd,reason2=:reason2,note=:note,status=:status,createtime=:createtime;
    private String id;
    private String company;
    private String department;
    private String job;
    private Integer addNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date askDate;
    private String companyLevel;
    private String manageGroup;
    private String departmentLevel;
    private String dutyLevel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date planJoinDate;
    private String education;
    private String profession;
    private String sex;
    private String age;
    private String foreignLang;
    private String profesRequire;
    private String managePower;
    private String workExperience;
    private String acceptOutWork;
    private String jobResponsi;
    private String reportRelationship;
    private String salaryRange;
    private Integer jobPrepared;
    private Integer onDutyNum;
    private String planAdd;
    private String reason;
    private String outPlanAdd;
    private String reason2;
    private String note;
    private Integer status;
    private Date createtime;

    @TableField
    private Integer index;
    @TableField
    private Integer topManage;
    @TableField
    private Integer subsidiaryTopManage;
    @TableField
    private Integer topInspector;
    @TableField
    private Integer subsidiaryTopInspector;
    @TableField
    private Integer manage;
    @TableField
    private Integer subsidiaryManage;
    @TableField
    private Integer charge;
    @TableField
    private Integer director;
    @TableField
    private Integer staff;
    @TableField
    private Integer summation;
    @TableField
    private Integer manageGroupNum;

    public HrRecruitmentNeeds() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getManageGroupNum() {
        return manageGroupNum;
    }

    public void setManageGroupNum(Integer manageGroupNum) {
        this.manageGroupNum = manageGroupNum;
    }

    public Integer getSummation() {
        return summation;
    }

    public void setSummation(Integer summation) {
        this.summation = summation;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getTopManage() {
        return topManage;
    }

    public void setTopManage(Integer topManage) {
        this.topManage = topManage;
    }

    public Integer getSubsidiaryTopManage() {
        return subsidiaryTopManage;
    }

    public void setSubsidiaryTopManage(Integer subsidiaryTopManage) {
        this.subsidiaryTopManage = subsidiaryTopManage;
    }

    public Integer getTopInspector() {
        return topInspector;
    }

    public void setTopInspector(Integer topInspector) {
        this.topInspector = topInspector;
    }

    public Integer getSubsidiaryTopInspector() {
        return subsidiaryTopInspector;
    }

    public void setSubsidiaryTopInspector(Integer subsidiaryTopInspector) {
        this.subsidiaryTopInspector = subsidiaryTopInspector;
    }

    public Integer getManage() {
        return manage;
    }

    public void setManage(Integer manage) {
        this.manage = manage;
    }

    public Integer getSubsidiaryManage() {
        return subsidiaryManage;
    }

    public void setSubsidiaryManage(Integer subsidiaryManage) {
        this.subsidiaryManage = subsidiaryManage;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Integer getDirector() {
        return director;
    }

    public void setDirector(Integer director) {
        this.director = director;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
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

   public String getJob() {
      return job;
   }

   public void setJob(String job) {
      this.job = job;
   }

   public Integer getAddNum() {
      return addNum;
   }

   public void setAddNum(Integer addNum) {
      this.addNum = addNum;
   }

   public Date getAskDate() {
      return askDate;
   }

   public void setAskDate(Date askDate) {
      this.askDate = askDate;
   }

   public String getCompanyLevel() {
      return companyLevel;
   }

   public void setCompanyLevel(String companyLevel) {
      this.companyLevel = companyLevel;
   }

   public String getManageGroup() {
      return manageGroup;
   }

   public void setManageGroup(String manageGroup) {
      this.manageGroup = manageGroup;
   }

   public String getDepartmentLevel() {
      return departmentLevel;
   }

   public void setDepartmentLevel(String departmentLevel) {
      this.departmentLevel = departmentLevel;
   }

   public String getDutyLevel() {
      return dutyLevel;
   }

   public void setDutyLevel(String dutyLevel) {
      this.dutyLevel = dutyLevel;
   }

   public Date getPlanJoinDate() {
      return planJoinDate;
   }

   public void setPlanJoinDate(Date planJoinDate) {
      this.planJoinDate = planJoinDate;
   }

   public String getEducation() {
      return education;
   }

   public void setEducation(String education) {
      this.education = education;
   }

   public String getProfession() {
      return profession;
   }

   public void setProfession(String profession) {
      this.profession = profession;
   }

   public String getSex() {
      return sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getForeignLang() {
      return foreignLang;
   }

   public void setForeignLang(String foreignLang) {
      this.foreignLang = foreignLang;
   }

   public String getProfesRequire() {
      return profesRequire;
   }

   public void setProfesRequire(String profesRequire) {
      this.profesRequire = profesRequire;
   }

   public String getManagePower() {
      return managePower;
   }

   public void setManagePower(String managePower) {
      this.managePower = managePower;
   }

   public String getWorkExperience() {
      return workExperience;
   }

   public void setWorkExperience(String workExperience) {
      this.workExperience = workExperience;
   }

   public String getAcceptOutWork() {
      return acceptOutWork;
   }

   public void setAcceptOutWork(String acceptOutWork) {
      this.acceptOutWork = acceptOutWork;
   }

   public String getJobResponsi() {
      return jobResponsi;
   }

   public void setJobResponsi(String jobResponsi) {
      this.jobResponsi = jobResponsi;
   }

   public String getReportRelationship() {
      return reportRelationship;
   }

   public void setReportRelationship(String reportRelationship) {
      this.reportRelationship = reportRelationship;
   }

   public String getSalaryRange() {
      return salaryRange;
   }

   public void setSalaryRange(String salaryRange) {
      this.salaryRange = salaryRange;
   }

   public Integer getJobPrepared() {
      return jobPrepared;
   }

   public void setJobPrepared(Integer jobPrepared) {
      this.jobPrepared = jobPrepared;
   }

   public Integer getOnDutyNum() {
      return onDutyNum;
   }

   public void setOnDutyNum(Integer onDutyNum) {
      this.onDutyNum = onDutyNum;
   }

   public String getPlanAdd() {
      return planAdd;
   }

   public void setPlanAdd(String planAdd) {
      this.planAdd = planAdd;
   }

   public String getReason() {
      return reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }

   public String getOutPlanAdd() {
      return outPlanAdd;
   }

   public void setOutPlanAdd(String outPlanAdd) {
      this.outPlanAdd = outPlanAdd;
   }

   public String getReason2() {
      return reason2;
   }

   public void setReason2(String reason2) {
      this.reason2 = reason2;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
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
}
