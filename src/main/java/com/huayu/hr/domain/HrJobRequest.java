package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2016-12-30 14:01:23.
*/
@Table(name = "hr_job_request")
public class HrJobRequest extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = 5852430711803461610L;
   //private String updateFiledKey = id,name,sex,birth,mobile,homeAddress,company,job,recentCompany,recentJobTime,recentJob,attachment,status,createtime,updatetime,deletetime,operateuser;
 //private String updateFiledValue = id=:id,name=:name,sex=:sex,birth=:birth,mobile=:mobile,homeAddress=:homeAddress,company=:company,job=:job,recentCompany=:recentCompany,recentJobTime=:recentJobTime,recentJob=:recentJob,attachment=:attachment,status=:status,createtime=:createtime,updatetime=:updatetime,deletetime=:deletetime,operateuser=:operateuser;
    private Integer id;
    private String name;
    private String sex;
    private String birth;
    private String mobile;
    private String school;
    private String edu;
    private String profession;
    private Integer company;
    private Integer plateId;
    private Integer job;
    private Integer area;
    private String recentCompany;
    private String recentJobTime;
    private String recentJob;
    private String attachment;
    private String note;
    private Integer way;
    private Integer status;
   @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private Date updatetime;
    private Date deletetime;
    private Long operateuser;

    public HrJobRequest() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private String jobStr;


   public Integer getPlateId() {
      return plateId;
   }

   public void setPlateId(Integer plateId) {
      this.plateId = plateId;
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

   public String getSex() {
      return sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public String getBirth() {
      return birth;
   }

   public void setBirth(String birth) {
      this.birth = birth;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getSchool() {
      return school;
   }

   public void setSchool(String school) {
      this.school = school;
   }

   public String getEdu() {
      return edu;
   }

   public void setEdu(String edu) {
      this.edu = edu;
   }

   public String getProfession() {
      return profession;
   }

   public void setProfession(String profession) {
      this.profession = profession;
   }

   public Integer getCompany() {
      return company;
   }

   public void setCompany(Integer company) {
      this.company = company;
   }

   public Integer getJob() {
      return job;
   }

   public void setJob(Integer job) {
      this.job = job;
   }

   public String getRecentCompany() {
      return recentCompany;
   }

   public void setRecentCompany(String recentCompany) {
      this.recentCompany = recentCompany;
   }

   public String getRecentJobTime() {
      return recentJobTime;
   }

   public void setRecentJobTime(String recentJobTime) {
      this.recentJobTime = recentJobTime;
   }

   public Integer getWay() {
      return way;
   }

   public void setWay(Integer way) {
      this.way = way;
   }

   public String getRecentJob() {
      return recentJob;
   }

   public void setRecentJob(String recentJob) {
      this.recentJob = recentJob;
   }

   public String getAttachment() {
      return attachment;
   }

   public void setAttachment(String attachment) {
      this.attachment = attachment;
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

   public Date getUpdatetime() {
      return updatetime;
   }

   public void setUpdatetime(Date updatetime) {
      this.updatetime = updatetime;
   }

   public Date getDeletetime() {
      return deletetime;
   }

   public void setDeletetime(Date deletetime) {
      this.deletetime = deletetime;
   }

   public Long getOperateuser() {
      return operateuser;
   }

   public void setOperateuser(Long operateuser) {
      this.operateuser = operateuser;
   }

   public String getJobStr() {
      return jobStr;
   }

   public void setJobStr(String jobStr) {
      this.jobStr = jobStr;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public Integer getArea() {
      return area;
   }

   public void setArea(Integer area) {
      this.area = area;
   }
}
