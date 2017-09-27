package com.huayu.signWechat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupQuery;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-7-19 19:28:37.
*/
@Table(name = "hr_sign_statistics")
public class HrSignStatistics extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,userid,pactCompany,plateId,companyId,departmentId,userNum,birthdate,joinCompDate,InDueFormDate,useStatus,name,sex,position,absenceDayNum,factPayDayNum,workDutyDayNum,restDayNum,elseDeduct,affairsLeave,sickLeave,restChanged,idCard,technicalLevel,userCategory,workStatus,performanceSystem;
 //private String updateFiledValue = id=:id,userid=:userid,pactCompany=:pactCompany,plateId=:plateId,companyId=:companyId,departmentId=:departmentId,userNum=:userNum,birthdate=:birthdate,joinCompDate=:joinCompDate,InDueFormDate=:InDueFormDate,useStatus=:useStatus,name=:name,sex=:sex,position=:position,absenceDayNum=:absenceDayNum,factPayDayNum=:factPayDayNum,workDutyDayNum=:workDutyDayNum,restDayNum=:restDayNum,elseDeduct=:elseDeduct,affairsLeave=:affairsLeave,sickLeave=:sickLeave,restChanged=:restChanged,idCard=:idCard,technicalLevel=:technicalLevel,userCategory=:userCategory,workStatus=:workStatus,performanceSystem=:performanceSystem;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date joinCompDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date inDueFormDate;
    private String useStatus;
    private String name;
    private Integer age;
    private String sex;
    private String position;
    private BigDecimal absenceDayNum;
    private BigDecimal factPayDayNum;
    private BigDecimal workDutyDayNum;
    private BigDecimal restDayNum;
    private BigDecimal elseDeduct;
    private BigDecimal affairsLeave;
    private BigDecimal sickLeave;
    private BigDecimal restChanged;
    private String idCard;
    private String technicalLevel;
    private String userCategory;
    private String workStatus;
    private String performanceSystem;
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

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date statisticsDate;
    @TableField
    private String pactCompanyName;
    @TableField
    private String plateName;
    @TableField
    private String companyName;
    @TableField
    private String departmentName;
    @TableField
    private Integer index;
    @TableField
    private String noWork;
    @TableField
    private String company;
    @TableField
    private String department;
    @TableField
    private String code;
    @TableField
    private String departmentIds;


    public HrSignStatistics() {
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

    public String getNoWork() {
        return noWork;
    }

    public void setNoWork(String noWork) {
        this.noWork = noWork;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

   public BigDecimal getAbsenceDayNum() {
      return absenceDayNum;
   }

   public void setAbsenceDayNum(BigDecimal absenceDayNum) {
      this.absenceDayNum = absenceDayNum;
   }

   public BigDecimal getFactPayDayNum() {
      return factPayDayNum;
   }

   public void setFactPayDayNum(BigDecimal factPayDayNum) {
      this.factPayDayNum = factPayDayNum;
   }

   public BigDecimal getWorkDutyDayNum() {
      return workDutyDayNum;
   }

   public void setWorkDutyDayNum(BigDecimal workDutyDayNum) {
      this.workDutyDayNum = workDutyDayNum;
   }

   public BigDecimal getRestDayNum() {
      return restDayNum;
   }

   public void setRestDayNum(BigDecimal restDayNum) {
      this.restDayNum = restDayNum;
   }

   public BigDecimal getElseDeduct() {
      return elseDeduct;
   }

   public void setElseDeduct(BigDecimal elseDeduct) {
      this.elseDeduct = elseDeduct;
   }

   public BigDecimal getAffairsLeave() {
      return affairsLeave;
   }

   public void setAffairsLeave(BigDecimal affairsLeave) {
      this.affairsLeave = affairsLeave;
   }

   public BigDecimal getSickLeave() {
      return sickLeave;
   }

   public void setSickLeave(BigDecimal sickLeave) {
      this.sickLeave = sickLeave;
   }

   public BigDecimal getRestChanged() {
      return restChanged;
   }

   public void setRestChanged(BigDecimal restChanged) {
      this.restChanged = restChanged;
   }

   public String getIdCard() {
      return idCard;
   }

   public void setIdCard(String idCard) {
      this.idCard = idCard;
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
}
