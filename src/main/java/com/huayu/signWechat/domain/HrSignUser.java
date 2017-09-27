package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-13 11:15:55.
*/
@Table(name = "hr_sign_user")
public class HrSignUser extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,userid,userNum,name,IDcard,departmentId,position,mobile,gender,email,isLeader,avatar,telephone,english_name,status,extattr;
 //private String updateFiledValue = id=:id,userid=:userid,userNum=:userNum,name=:name,IDcard=:IDcard,departmentId=:departmentId,position=:position,mobile=:mobile,gender=:gender,email=:email,isLeader=:isLeader,avatar=:avatar,telephone=:telephone,english_name=:english_name,status=:status,extattr=:extattr;
    private Integer id;
    private String userid;
    private String userNum;
    private String name;
    private String idCard;
    private String department;
    private String position;
    private String mobile;
    private Integer gender;
    private String email;
    private Integer isleader;
    private String avatar;
    private String telephone;
    private String english_name;
    private Integer status;
    private String extattr;
    private Integer isMatched;
    private String oldMobile;

    @TableField
    private String pactCompanyName;
    @TableField
    private String plateName;
    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private Date birthdate;
    @TableField
    private Date joinCompDate;
    @TableField
    private String sex;
    @TableField
    private Date inDueFormDate;
    @TableField
    private String useStatus;
    @TableField
    private String technicalLevel;
    @TableField
    private String userCategory;
    @TableField
    private String workStatus;
    @TableField
    private String performanceSystem;
    @TableField
    private String groupName;
    @TableField
    private String checkDate;
    @TableField
    private String checkTime;
    @TableField
    private String company;
    @TableField
    private String code;
    @TableField
    private String departmentId;

    public HrSignUser() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getInDueFormDate() {
        return inDueFormDate;
    }

    public void setInDueFormDate(Date inDueFormDate) {
        this.inDueFormDate = inDueFormDate;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getTechnicalLevel() {
        return technicalLevel;
    }

    public void setTechnicalLevel(String technicalLevel) {
        this.technicalLevel = technicalLevel;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getPerformanceSystem() {
        return performanceSystem;
    }

    public void setPerformanceSystem(String performanceSystem) {
        this.performanceSystem = performanceSystem;
    }

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public Integer getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(Integer isMatched) {
        this.isMatched = isMatched;
    }

    public String getPactCompanyName() {
        return pactCompanyName;
    }

    public void setPactCompanyName(String pactCompanyName) {
        this.pactCompanyName = pactCompanyName;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getJoinCompDate() {
        return joinCompDate;
    }

    public void setJoinCompDate(Date joinCompDate) {
        this.joinCompDate = joinCompDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getUserid() {
      return userid;
   }

   public void setUserid(String userid) {
      this.userid = userid;
   }

   public String getUserNum() {
      return userNum;
   }

   public void setUserNum(String userNum) {
      this.userNum = userNum;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
      return position;
   }

   public void setPosition(String position) {
      this.position = position;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public Integer getGender() {
      return gender;
   }

   public void setGender(Integer gender) {
      this.gender = gender;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getIsleader() {
        return isleader;
    }

    public void setIsleader(Integer isleader) {
        this.isleader = isleader;
    }

    public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public String getTelephone() {
      return telephone;
   }

   public void setTelephone(String telephone) {
      this.telephone = telephone;
   }

   public String getEnglish_name() {
      return english_name;
   }

   public void setEnglish_name(String english_name) {
      this.english_name = english_name;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getExtattr() {
      return extattr;
   }

   public void setExtattr(String extattr) {
      this.extattr = extattr;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HrSignUser)) return false;

        HrSignUser that = (HrSignUser) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (isleader != null ? !isleader.equals(that.isleader) : that.isleader != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (isleader != null ? isleader.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
