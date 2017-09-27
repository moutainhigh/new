package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupQuery;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-19 19:28:37.
*/
public class HrSignUnusualStatistics {

    private Integer id;
    private Integer userid;
    private String pactCompany;
    private Integer plateId;
    private Integer companyId;
    private Integer departmentId;
    private String userNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date birthdate;
    private Integer age;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date joinCompDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date inDueFormDate;
    private String useStatus;
    private String name;
    private String sex;
    private String position;
    private String idCard;
    private String technicalLevel;
    private String userCategory;
    private String workStatus;
    private String performanceSystem;
    private String late;//迟到
    private String leaveEarly;//早退
    private String noWork;//旷工

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空", groups = {GroupQuery.class})
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空", groups = {GroupQuery.class})
    private Date endDate;
    private Integer status;
    private Date createTime;
    private BigDecimal egressDayNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date statisticsDate;
    private String pactCompanyName;
    private String plateName;
    private String companyName;
    private String departmentName;
    private String groupName;
    private BigDecimal punishMoney;
    private String remark;
    private Integer index;
    private String company;
    private String department;
    private String code;
    private String departmentIds;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BigDecimal getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        this.punishMoney = punishMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigDecimal getEgressDayNum() {
        return egressDayNum;
    }

    public void setEgressDayNum(BigDecimal egressDayNum) {
        this.egressDayNum = egressDayNum;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getUserid() {
      return userid;
   }

   public void setUserid(Integer userid) {
      this.userid = userid;
   }

   public String getPactCompany() {
      return pactCompany;
   }

   public void setPactCompany(String pactCompany) {
      this.pactCompany = pactCompany;
   }

   public Integer getPlateId() {
      return plateId;
   }

   public void setPlateId(Integer plateId) {
      this.plateId = plateId;
   }

   public Integer getCompanyId() {
      return companyId;
   }

   public void setCompanyId(Integer companyId) {
      this.companyId = companyId;
   }

   public Integer getDepartmentId() {
      return departmentId;
   }

   public void setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
   }

   public String getUserNum() {
      return userNum;
   }

   public void setUserNum(String userNum) {
      this.userNum = userNum;
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

    public Date getInDueFormDate() {
        return inDueFormDate;
    }

    public void setInDueFormDate(Date inDueFormDate) {
        this.inDueFormDate = inDueFormDate;
    }

    public String getUseStatus() {
        return useStatus;
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

    public String getPosition() {
      return position;
   }

   public void setPosition(String position) {
      this.position = position;
   }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getLeaveEarly() {
        return leaveEarly;
    }

    public void setLeaveEarly(String leaveEarly) {
        this.leaveEarly = leaveEarly;
    }

    public String getNoWork() {
        return noWork;
    }

    public void setNoWork(String noWork) {
        this.noWork = noWork;
    }

    public String getIdCard() {
      return idCard;
   }

   public void setIdCard(String idCard) {
      this.idCard = idCard;
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
}
