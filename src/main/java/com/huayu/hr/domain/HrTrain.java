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
* Created by DengPeng on 2017-1-18 15:17:07.
*/
@Table(name = "hr_train")
public class HrTrain extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -6449179015756264896L;
   //private String updateFiledKey = id,code,name,lv,year,quarter,month,type,way,planCount,actualCount,content,target,addr,department,principal,mobile,planCost,actualCost,timeCount,startTime,endTime,teacherName,teacherMobile,teacherComp,note,createtime;
 //private String updateFiledValue = id=:id,code=:code,name=:name,lv=:lv,year=:year,quarter=:quarter,month=:month,type=:type,way=:way,planCount=:planCount,actualCount=:actualCount,content=:content,target=:target,addr=:addr,department=:department,principal=:principal,mobile=:mobile,planCost=:planCost,actualCost=:actualCost,timeCount=:timeCount,startTime=:startTime,endTime=:endTime,teacherName=:teacherName,teacherMobile=:teacherMobile,teacherComp=:teacherComp,note=:note,createtime=:createtime;
    private Integer id;
    private String code;
    private String name;
    private Integer lv;
    private String year;
    private Integer quarter;
    private String month;
    private Integer type;
    private String way;
    private Integer planCount;
    private Integer actualCount;
    private String content;
    private String target;
    private String addr;
    private Integer compLv1;
    private String compLv1Str;
    private Integer compLv2;
    private String compLv2Str;
    private Integer compLv3;
    private String compLv3Str;
    private Integer company;
    private Integer department;
    private String principal;
    private String mobile;
    private BigDecimal planCost;
    private BigDecimal actualCost;
    private BigDecimal timeCount;
    private BigDecimal totalTimeCount;
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JSONField(format="yyyy-MM-dd")
    private Date startTime;
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JSONField(format="yyyy-MM-dd")
    private Date endTime;
    private String teacherName;
    private String teacherMobile;
    private String teacherComp;
    private String note;
    private Integer status;
   private Integer createUser;
   @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
   private Integer updateUser;
   @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

   public HrTrain(Integer empId) {
      this.empId = empId;
   }

   public HrTrain() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getLvStr(){
       String str = null;
       switch (lv){
          case 0:
             str="集团级";
             break;
          case 1:
             str="公司级";
             break;
          case 2:
             str="部门级";
             break;
       }
       return str;
    }

   public String getTypeStr(){
      String str = null;
      switch (type){
         case 0:
            str="内训";
            break;
         case 1:
            str="外训";
            break;
      }
      return str;
   }

   @TableField
   private String companyName;

   @TableField
   private String departmentName;

   @TableField
   private String departmentStr;

   @TableField
   private String principalStr;

   @TableField
   private Integer countM;

   @TableField
   private Integer countW;

   @TableField
   private Integer totalTime;

   @TableField
   private Integer empId;

   @TableField
   private Integer plateId;


   public Integer getCreateUser() {
      return createUser;
   }

   public void setCreateUser(Integer createUser) {
      this.createUser = createUser;
   }

   public Integer getUpdateUser() {
      return updateUser;
   }

   public void setUpdateUser(Integer updateUser) {
      this.updateUser = updateUser;
   }

   public Integer getPlateId() {
      return plateId;
   }

   public void setPlateId(Integer plateId) {
      this.plateId = plateId;
   }

   public Integer getEmpId() {
      return empId;
   }

   public void setEmpId(Integer empId) {
      this.empId = empId;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getLv() {
      return lv;
   }

   public void setLv(Integer lv) {
      this.lv = lv;
   }

   public String getYear() {
      return year;
   }

   public void setYear(String year) {
      this.year = year;
   }

   public Integer getQuarter() {
      return quarter;
   }

   public void setQuarter(Integer quarter) {
      this.quarter = quarter;
   }

   public String getMonth() {
      return month;
   }

   public void setMonth(String month) {
      this.month = month;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public String getWay() {
      return way;
   }

   public void setWay(String way) {
      this.way = way;
   }

   public Integer getPlanCount() {
      return planCount;
   }

   public void setPlanCount(Integer planCount) {
      this.planCount = planCount;
   }

   public Integer getActualCount() {
      return actualCount;
   }

   public void setActualCount(Integer actualCount) {
      this.actualCount = actualCount;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getTarget() {
      return target;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public String getAddr() {
      return addr;
   }

   public void setAddr(String addr) {
      this.addr = addr;
   }

   public String getCompLv1Str() {
      return compLv1Str;
   }

   public void setCompLv1Str(String compLv1Str) {
      this.compLv1Str = compLv1Str;
   }

   public String getCompLv2Str() {
      return compLv2Str;
   }

   public void setCompLv2Str(String compLv2Str) {
      this.compLv2Str = compLv2Str;
   }

   public String getCompLv3Str() {
      return compLv3Str;
   }

   public void setCompLv3Str(String compLv3Str) {
      this.compLv3Str = compLv3Str;
   }

   public Integer getCompLv1() {
      return compLv1;
   }

   public void setCompLv1(Integer compLv1) {
      this.compLv1 = compLv1;
   }

   public Integer getCompLv2() {
      return compLv2;
   }

   public void setCompLv2(Integer compLv2) {
      this.compLv2 = compLv2;
   }

   public Integer getCompLv3() {
      return compLv3;
   }

   public void setCompLv3(Integer compLv3) {
      this.compLv3 = compLv3;
   }

   public Integer getCompany() {
      return company;
   }

   public void setCompany(Integer company) {
      this.company = company;
   }

   public Integer getDepartment() {
      return department;
   }

   public void setDepartment(Integer department) {
      this.department = department;
   }

   public String getPrincipal() {
      return principal;
   }

   public void setPrincipal(String principal) {
      this.principal = principal;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public BigDecimal getPlanCost() {
      return planCost;
   }

   public void setPlanCost(BigDecimal planCost) {
      this.planCost = planCost;
   }

   public BigDecimal getActualCost() {
      return actualCost;
   }

   public void setActualCost(BigDecimal actualCost) {
      this.actualCost = actualCost;
   }


   public Date getStartTime() {
      return startTime;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public Date getEndTime() {
      return endTime;
   }

   public void setEndTime(Date endTime) {
      this.endTime = endTime;
   }

   public String getTeacherName() {
      return teacherName;
   }

   public void setTeacherName(String teacherName) {
      this.teacherName = teacherName;
   }

   public String getTeacherMobile() {
      return teacherMobile;
   }

   public void setTeacherMobile(String teacherMobile) {
      this.teacherMobile = teacherMobile;
   }

   public String getTeacherComp() {
      return teacherComp;
   }

   public void setTeacherComp(String teacherComp) {
      this.teacherComp = teacherComp;
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

   public Date getUpdatetime() {
      return updatetime;
   }

   public void setUpdatetime(Date updatetime) {
      this.updatetime = updatetime;
   }

   public String getDepartmentStr() {
      return departmentStr;
   }

   public void setDepartmentStr(String departmentStr) {
      this.departmentStr = departmentStr;
   }

   public String getPrincipalStr() {
      return principalStr;
   }

   public void setPrincipalStr(String principalStr) {
      this.principalStr = principalStr;
   }

   public BigDecimal getTimeCount() {
      return timeCount;
   }

   public void setTimeCount(BigDecimal timeCount) {
      this.timeCount = timeCount;
   }

   public BigDecimal getTotalTimeCount() {
      return totalTimeCount;
   }

   public void setTotalTimeCount(BigDecimal totalTimeCount) {
      this.totalTimeCount = totalTimeCount;
   }

   public Integer getCountM() {
      return countM;
   }

   public void setCountM(Integer countM) {
      this.countM = countM;
   }

   public Integer getCountW() {
      return countW;
   }

   public void setCountW(Integer countW) {
      this.countW = countW;
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

   public Integer getTotalTime() {
      return totalTime;
   }

   public void setTotalTime(Integer totalTime) {
      this.totalTime = totalTime;
   }
}
