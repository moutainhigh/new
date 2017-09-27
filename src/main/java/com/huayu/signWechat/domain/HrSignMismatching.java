package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-7-19 15:54:14.
*/
@Table(name = "hr_sign_mismatching")
public class HrSignMismatching extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,userid,userNum,name,IDcard,department,position,mobile,gender,email,isLeader,avatar,telephone,english_name,status,extattr;
 //private String updateFiledValue = id=:id,userid=:userid,userNum=:userNum,name=:name,IDcard=:IDcard,department=:department,position=:position,mobile=:mobile,gender=:gender,email=:email,isLeader=:isLeader,avatar=:avatar,telephone=:telephone,english_name=:english_name,status=:status,extattr=:extattr;
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

    public HrSignMismatching() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
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
}
