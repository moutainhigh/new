package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* Created by DengPeng on 2017-1-13 11:01:14.
*/
@Table(name = "hr_statistics_data")
public class HrStatisticsData extends BaseDomain  implements Serializable{

    private Integer id;
    private Integer sum=0;
    private Integer edu1=0;
    private Integer edu2=0;
    private Integer edu3=0;
    private Integer edu4=0;
    private Integer age1=0;
    private Integer age2=0;
    private Integer age3=0;
    private Integer age4=0;
    private Integer age5=0;
    private Integer ctime1=0;
    private Integer ctime2=0;
    private Integer ctime3=0;
    private Integer ctime4=0;
    private Integer ctime5=0;
    private Integer male=0;
    private Integer female=0;
    private Integer statisticsType;
    private String year;
    private String month;
    private Integer company;
    private Integer department;
    private Integer dutyLevel;
    private Integer status;
    private Date createtime;

    @TableField
    private String deptName;
    public HrStatisticsData() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrStatisticsData(String year, String month) {
        this.year = year;
        this.month = month;
    }

    @TableField
    private String companyStr;

    @TableField
    private boolean isContain;

    @TableField
    private Integer isParent;

    @TableField
    private Integer departmentPid;

    @TableField
    private Integer companyPid;


    @TableField
    private List<HrStatisticsData> childStatisticsData;

    @TableField
    private List<HrStatisticsData> detailStatisticsData;

    @TableField
    private String authorityRegexp;

    @TableField
    private String startDate;
    @TableField
    private String endDate;

    @TableField
    private Integer jobSequence;
    @TableField
    private Integer empId5;
    @TableField
    private Integer empId6;
    @TableField
    private Integer empIdGatherM;
    @TableField
    private Integer dutyLevelId;
    @TableField
    private Date date;

    public Integer getCompanyPid() {
        return companyPid;
    }

    public void setCompanyPid(Integer companyPid) {
        this.companyPid = companyPid;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Integer getDepartmentPid() {
        return departmentPid;
    }

    public void setDepartmentPid(Integer departmentPid) {
        this.departmentPid = departmentPid;
    }

    public boolean isContain() {
        return isContain;
    }

    public void setContain(boolean contain) {
        isContain = contain;
    }

    public void addSum(int data){
        this.sum = this.sum+data;
    }

    public void addEdu1(int data){
        this.edu1 = this.edu1+data;
    }

    public void addEdu2(int data){
        this.edu2 = this.edu2+data;
    }

    public void addEdu3(int data){
        this.edu3 = this.edu3+data;
    }

    public void addEdu4(int data){
        this.edu4 = this.edu4+data;
    }

    public void addAge1(int data){
        this.age1 = this.age1+data;
    }

    public void addAge2(int data){
        this.age2 = this.age2+data;
    }

    public void addAge3(int data){
        this.age3 = this.age3+data;
    }

    public void addAge4(int data){
        this.age4 = this.age4+data;
    }

    public void addAge5(int data){
        this.age5 = this.age5+data;
    }

    public void addCtime1(int data){
        this.ctime1 = this.ctime1+data;
    }

    public void addCtime2(int data){
        this.ctime2 = this.ctime2+data;
    }

    public void addCtime3(int data){
        this.ctime3 = this.ctime3+data;
    }

    public void addCtime4(int data){
        this.ctime4 = this.ctime4+data;
    }

    public void addCtime5(int data){
        this.ctime5 = this.ctime5+data;
    }

    public void addMale(int data){
        this.male = this.male+data;
    }

    public void addFemale(int data){
        this.female = this.female+data;
    }

    public Integer getDutyLevelId() {
        return dutyLevelId;
    }

    public void setDutyLevelId(Integer dutyLevelId) {
        this.dutyLevelId = dutyLevelId;
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

    public String getAuthorityRegexp() {
        return authorityRegexp;
    }

    public void setAuthorityRegexp(String authorityRegexp) {
        this.authorityRegexp = authorityRegexp;
    }

    public Integer getJobSequence() {
        return jobSequence;
    }

    public void setJobSequence(Integer jobSequence) {
        this.jobSequence = jobSequence;
    }

    public List<HrStatisticsData> getDetailStatisticsData() {
        return detailStatisticsData;
    }

    public void setDetailStatisticsData(List<HrStatisticsData> detailStatisticsData) {
        this.detailStatisticsData = detailStatisticsData;
    }

    public Integer getEmpId5() {
        return empId5;
    }

    public void setEmpId5(Integer empId5) {
        this.empId5 = empId5;
    }

    public Integer getEmpId6() {
        return empId6;
    }

    public void setEmpId6(Integer empId6) {
        this.empId6 = empId6;
    }

    public Integer getEmpIdGatherM() {
        return empIdGatherM;
    }

    public void setEmpIdGatherM(Integer empIdGatherM) {
        this.empIdGatherM = empIdGatherM;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCompanyStr() {
        return companyStr;
    }

    public void setCompanyStr(String companyStr) {
        this.companyStr = companyStr;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getSum() {
      return sum;
   }

   public void setSum(Integer sum) {
      this.sum = sum;
   }

   public Integer getEdu1() {
      return edu1;
   }

   public void setEdu1(Integer edu1) {
      this.edu1 = edu1;
   }

   public Integer getEdu2() {
      return edu2;
   }

   public void setEdu2(Integer edu2) {
      this.edu2 = edu2;
   }

   public Integer getEdu3() {
      return edu3;
   }

   public void setEdu3(Integer edu3) {
      this.edu3 = edu3;
   }

   public Integer getEdu4() {
      return edu4;
   }

   public void setEdu4(Integer edu4) {
      this.edu4 = edu4;
   }

   public Integer getAge1() {
      return age1;
   }

   public void setAge1(Integer age1) {
      this.age1 = age1;
   }

   public Integer getAge2() {
      return age2;
   }

   public void setAge2(Integer age2) {
      this.age2 = age2;
   }

   public Integer getAge3() {
      return age3;
   }

   public void setAge3(Integer age3) {
      this.age3 = age3;
   }

   public Integer getAge4() {
      return age4;
   }

   public void setAge4(Integer age4) {
      this.age4 = age4;
   }

   public Integer getAge5() {
      return age5;
   }

   public void setAge5(Integer age5) {
      this.age5 = age5;
   }

   public Integer getCtime1() {
      return ctime1;
   }

   public void setCtime1(Integer ctime1) {
      this.ctime1 = ctime1;
   }

   public Integer getCtime2() {
      return ctime2;
   }

   public void setCtime2(Integer ctime2) {
      this.ctime2 = ctime2;
   }

   public Integer getCtime3() {
      return ctime3;
   }

   public void setCtime3(Integer ctime3) {
      this.ctime3 = ctime3;
   }

   public Integer getCtime4() {
      return ctime4;
   }

   public void setCtime4(Integer ctime4) {
      this.ctime4 = ctime4;
   }

    public Integer getCtime5() {
        return ctime5;
    }

    public void setCtime5(Integer ctime5) {
        this.ctime5 = ctime5;
    }

    public Integer getMale() {
      return male;
   }

   public void setMale(Integer male) {
      this.male = male;
   }

   public Integer getFemale() {
      return female;
   }

   public void setFemale(Integer female) {
      this.female = female;
   }

    public Integer getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(Integer statisticsType) {
        this.statisticsType = statisticsType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public List<HrStatisticsData> getChildStatisticsData() {
        return childStatisticsData;
    }

    public void setChildStatisticsData(List<HrStatisticsData> childStatisticsData) {
        this.childStatisticsData = childStatisticsData;
    }

    public String getDutyLevelStr(){
        String str;
        if(null!=dutyLevel){
            switch (dutyLevel){
                case 1:str="总裁级"; break;
                case 2:str="副总裁级"; break;
                case 3:str="总经理级"; break;
                case 4:str="副总经理级"; break;
                case 5:str="总监级"; break;
                case 6:str="副总监级"; break;
                case 7:str="经理级"; break;
                case 8:str="副经理级"; break;
                case 9:str="主管级"; break;
                case 10:str="主办级"; break;
                case 11:str="员级"; break;
                default:str = "";
            }
        }else{
            str = null;
        }
        return str;
    }

}
