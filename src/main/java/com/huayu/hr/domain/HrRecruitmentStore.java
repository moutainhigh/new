package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-20 13:22:46.
*/
@Table(name = "hr_recruitment_store")
public class HrRecruitmentStore extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 8803116245935144863L;
   //private String updateFiledKey = id,name,idCard,sex,school,education,profession,company,joinDate,job,resumeSrc,askJob,refuseReason,auditionDate,mobile,email,note,status,createtime,createUser,updatetime,updateUser,deletetime,deleteUser;
 //private String updateFiledValue = id=:id,name=:name,idCard=:idCard,sex=:sex,school=:school,education=:education,profession=:profession,company=:company,joinDate=:joinDate,job=:job,resumeSrc=:resumeSrc,askJob=:askJob,refuseReason=:refuseReason,auditionDate=:auditionDate,mobile=:mobile,email=:email,note=:note,status=:status,createtime=:createtime,createUser=:createUser,updatetime=:updatetime,updateUser=:updateUser,deletetime=:deletetime,deleteUser=:deleteUser;
    private Integer id;
    private String name;
    private String idCard;
    private String sex;
    private String school;
    private String education;
    private String profession;
    private Integer company;
    private String companyName;
    private String joinWorkDate;
    private String birth;
    private String job;
    private String resumeSrc;
    private String askJob;
    private String auditionDate;
    private String mobile;
    private String email;
    private String liveAddress;
    private String leaveReason;
    private String recentWorkTime;
    private String auditionUser;
    private String note;
    private String otherExperience;
    private Integer status;
   @JSONField(format="yyyy-MM-dd")
    private Date createtime;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;
    private Date deletetime;
    private Integer deleteUser;

    public HrRecruitmentStore() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date startDate;
   @TableField
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date endDate;

   @TableField
   private String plat;


   public String getLiveAddress() {
      return liveAddress;
   }

   public void setLiveAddress(String liveAddress) {
      this.liveAddress = liveAddress;
   }

   public String getLeaveReason() {
      return leaveReason;
   }

   public void setLeaveReason(String leaveReason) {
      this.leaveReason = leaveReason;
   }

   public String getRecentWorkTime() {
      return recentWorkTime;
   }

   public void setRecentWorkTime(String recentWorkTime) {
      this.recentWorkTime = recentWorkTime;
   }

   public String getAuditionUser() {
      return auditionUser;
   }

   public void setAuditionUser(String auditionUser) {
      this.auditionUser = auditionUser;
   }

   public String getOtherExperience() {
      return otherExperience;
   }

   public void setOtherExperience(String otherExperience) {
      this.otherExperience = otherExperience;
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

   public String getIdCard() {
      return idCard;
   }

   public void setIdCard(String idCard) {
      this.idCard = idCard;
   }

   public String getSex() {
      return sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public String getSchool() {
      return school;
   }

   public void setSchool(String school) {
      this.school = school;
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

   public String getPlat() {
      return plat;
   }

   public void setPlat(String plat) {
      this.plat = plat;
   }

   public Integer getCompany() {
      return company;
   }

   public void setCompany(Integer company) {
      this.company = company;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }


   public String getJob() {
      return job;
   }

   public void setJob(String job) {
      this.job = job;
   }

   public String getResumeSrc() {
      return resumeSrc;
   }

   public void setResumeSrc(String resumeSrc) {
      this.resumeSrc = resumeSrc;
   }

   public String getAskJob() {
      return askJob;
   }

   public void setAskJob(String askJob) {
      this.askJob = askJob;
   }

   public String getJoinWorkDate() {
      return joinWorkDate;
   }

   public void setJoinWorkDate(String joinWorkDate) {
      this.joinWorkDate = joinWorkDate;
   }

   public String getBirth() {
      return birth;
   }

   public void setBirth(String birth) {
      this.birth = birth;
   }

   public String getAuditionDate() {
      return auditionDate;
   }

   public void setAuditionDate(String auditionDate) {
      this.auditionDate = auditionDate;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
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

   public Integer getCreateUser() {
      return createUser;
   }

   public void setCreateUser(Integer createUser) {
      this.createUser = createUser;
   }

   public Date getUpdatetime() {
      return updatetime;
   }

   public void setUpdatetime(Date updatetime) {
      this.updatetime = updatetime;
   }

   public Integer getUpdateUser() {
      return updateUser;
   }

   public void setUpdateUser(Integer updateUser) {
      this.updateUser = updateUser;
   }

   public Date getDeletetime() {
      return deletetime;
   }

   public void setDeletetime(Date deletetime) {
      this.deletetime = deletetime;
   }

   public Integer getDeleteUser() {
      return deleteUser;
   }

   public void setDeleteUser(Integer deleteUser) {
      this.deleteUser = deleteUser;
   }
}
