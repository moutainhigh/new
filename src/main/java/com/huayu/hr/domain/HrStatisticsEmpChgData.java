package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
* Created by DengPeng on 2017-1-16 20:01:39.
*/
@Table(name = "hr_statistics_emp_chg_data")
public class HrStatisticsEmpChgData extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -6941633181513432795L;
   //private String updateFiledKey = id,onDutyM,onDutyW,inDutyM,inDutyW,inDutyGatherM,outDutyM,outDutyW,outDutyGatherM,turnInComp,turnOutComp,practice,channel,carm,note,companyId,department,formatMonth,createtime;
 //private String updateFiledValue = id=:id,onDutyM=:onDutyM,onDutyW=:onDutyW,inDutyM=:inDutyM,inDutyW=:inDutyW,inDutyGatherM=:inDutyGatherM,outDutyM=:outDutyM,outDutyW=:outDutyW,outDutyGatherM=:outDutyGatherM,turnInComp=:turnInComp,turnOutComp=:turnOutComp,practice=:practice,channel=:channel,carm=:carm,note=:note,companyId=:companyId,department=:department,formatMonth=:formatMonth,createtime=:createtime;
    private Integer id;
   private Integer beginOnDutyM = 0;
   private Integer beginOnDutyW = 0;
    private Integer onDutyM = 0;
    private Integer onDutyW = 0;
    private Integer inDutyM = 0;
    private Integer inDutyW = 0;
    private Integer inDutyGatherM = 0;
    private Integer outDutyM = 0;
    private Integer outDutyW = 0;
    private Integer outDutyGatherM = 0;
   private Integer turnInDeptM = 0;
   private Integer turnInDeptW = 0;
   private Integer turnOutDeptM = 0;
   private Integer turnOutDeptW = 0;
    private Integer turnInCompM = 0;
    private Integer turnInCompW = 0;
    private Integer turnOutCompM = 0;
    private Integer turnOutCompW = 0;
    private Integer practice = 0;
    private Integer channel = 0;
    private Integer carM = 0;
    private String note;
    private Integer company;
    private Integer department;
    private String year;
    private String month;
    private Integer status;
    private Date createtime;



    public HrStatisticsEmpChgData() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public HrStatisticsEmpChgData(String year, String month) {
      this.year = year;
      this.month = month;
   }

   public HrStatisticsEmpChgData(String year, String month, Integer company, Integer department) {
       this.year = year;
       this.month = month;
       this.company= company;
       this.department = department;
    }

   public void addBeginOnDutyM(int data){
      this.beginOnDutyM = this.beginOnDutyM+data;
   }
   public void addBeginOnDutyW(int data){
      this.beginOnDutyW = this.beginOnDutyW+data;
   }
   public void addOnDutyM(int data){
      this.onDutyM = this.onDutyM+data;
   }
   public void addOnDutyW(int data){
      this.onDutyW = this.onDutyW+data;
   }
   public void addInDutyM(int data){
      this.inDutyM = this.inDutyM+data;
   }
   public void addInDutyW(int data){
      this.inDutyW = this.inDutyW+data;
   }
   public void addInDutyGatherM(int data){
      this.inDutyGatherM = this.inDutyGatherM+data;
   }
   public void addOutDutyM(int data){
      this.outDutyM = this.outDutyM+data;
   }
   public void addOutDutyW(int data){
      this.outDutyW = this.outDutyW+data;
   }
   public void addOutDutyGatherM(int data){
      this.outDutyGatherM = this.outDutyGatherM+data;
   }
   public void addTurnInCompM(int data){
      this.turnInCompM = this.turnInCompM+data;
   }
   public void addTurnInCompW(int data){
      this.turnInCompW = this.turnInCompW+data;
   }
   public void addTurnOutCompM(int data){
      this.turnOutCompM = this.turnOutCompM+data;
   }
   public void addTurnOutCompW(int data){
      this.turnOutCompW = this.turnOutCompW+data;
   }

   public void addTurnInDeptM(int data){
      this.turnInDeptM = this.turnInDeptM+data;
   }
   public void addTurnInDeptW(int data){
      this.turnInDeptW = this.turnInDeptW+data;
   }
   public void addTurnOutDeptM(int data){
      this.turnOutDeptM = this.turnOutDeptM+data;
   }
   public void addTurnOutDeptW(int data){
      this.turnOutDeptW = this.turnOutDeptW+data;
   }
   public void addPractice(int data){
      this.practice = this.practice+data;
   }
   public void addChannel(int data){
      this.channel = this.channel+data;
   }
   public void addCarM(int data){
      this.carM = this.carM+data;
   }

   @TableField
   private String deptName;
   @TableField
   private String companyName;
   @TableField
   private String authorityRegexp;

   @TableField
   private List<HrStatisticsEmpChgDataDetail> chgDataDetails;

   @TableField
   private List<HrStatisticsEmpChgData> childChgData;

   @TableField
   private boolean isContain;

   @TableField
   private Integer isParent;

   @TableField
   private Integer departmentPid;


   public Integer getBeginOnDutyM() {
      return beginOnDutyM;
   }

   public void setBeginOnDutyM(Integer beginOnDutyM) {
      this.beginOnDutyM = beginOnDutyM;
   }

   public Integer getBeginOnDutyW() {
      return beginOnDutyW;
   }

   public void setBeginOnDutyW(Integer beginOnDutyW) {
      this.beginOnDutyW = beginOnDutyW;
   }

   public List<HrStatisticsEmpChgData> getChildChgData() {
      return childChgData;
   }

   public void setChildChgData(List<HrStatisticsEmpChgData> childChgData) {
      this.childChgData = childChgData;
   }

   public boolean isContain() {
      return isContain;
   }

   public void setContain(boolean contain) {
      isContain = contain;
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

   public List<HrStatisticsEmpChgDataDetail> getChgDataDetails() {
      return chgDataDetails;
   }

   public void setChgDataDetails(List<HrStatisticsEmpChgDataDetail> chgDataDetails) {
      this.chgDataDetails = chgDataDetails;
   }

   public Integer getTurnInDeptM() {
      return turnInDeptM;
   }

   public void setTurnInDeptM(Integer turnInDeptM) {
      this.turnInDeptM = turnInDeptM;
   }

   public Integer getTurnInDeptW() {
      return turnInDeptW;
   }

   public void setTurnInDeptW(Integer turnInDeptW) {
      this.turnInDeptW = turnInDeptW;
   }

   public Integer getTurnOutDeptM() {
      return turnOutDeptM;
   }

   public void setTurnOutDeptM(Integer turnOutDeptM) {
      this.turnOutDeptM = turnOutDeptM;
   }

   public Integer getTurnOutDeptW() {
      return turnOutDeptW;
   }

   public void setTurnOutDeptW(Integer turnOutDeptW) {
      this.turnOutDeptW = turnOutDeptW;
   }

   public String getAuthorityRegexp() {
      return authorityRegexp;
   }

   public void setAuthorityRegexp(String authorityRegexp) {
      this.authorityRegexp = authorityRegexp;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getOnDutyM() {
      return onDutyM;
   }

   public void setOnDutyM(Integer onDutyM) {
      this.onDutyM = onDutyM;
   }

   public Integer getOnDutyW() {
      return onDutyW;
   }

   public void setOnDutyW(Integer onDutyW) {
      this.onDutyW = onDutyW;
   }

   public Integer getInDutyM() {
      return inDutyM;
   }

   public void setInDutyM(Integer inDutyM) {
      this.inDutyM = inDutyM;
   }

   public Integer getInDutyW() {
      return inDutyW;
   }

   public void setInDutyW(Integer inDutyW) {
      this.inDutyW = inDutyW;
   }

   public Integer getInDutyGatherM() {
      return inDutyGatherM;
   }

   public void setInDutyGatherM(Integer inDutyGatherM) {
      this.inDutyGatherM = inDutyGatherM;
   }

   public Integer getOutDutyM() {
      return outDutyM;
   }

   public void setOutDutyM(Integer outDutyM) {
      this.outDutyM = outDutyM;
   }

   public Integer getOutDutyW() {
      return outDutyW;
   }

   public void setOutDutyW(Integer outDutyW) {
      this.outDutyW = outDutyW;
   }

   public Integer getOutDutyGatherM() {
      return outDutyGatherM;
   }

   public void setOutDutyGatherM(Integer outDutyGatherM) {
      this.outDutyGatherM = outDutyGatherM;
   }

   public Integer getTurnInCompM() {
      return turnInCompM;
   }

   public void setTurnInCompM(Integer turnInCompM) {
      this.turnInCompM = turnInCompM;
   }

   public Integer getTurnInCompW() {
      return turnInCompW;
   }

   public void setTurnInCompW(Integer turnInCompW) {
      this.turnInCompW = turnInCompW;
   }

   public Integer getTurnOutCompM() {
      return turnOutCompM;
   }

   public void setTurnOutCompM(Integer turnOutCompM) {
      this.turnOutCompM = turnOutCompM;
   }

   public Integer getTurnOutCompW() {
      return turnOutCompW;
   }

   public void setTurnOutCompW(Integer turnOutCompW) {
      this.turnOutCompW = turnOutCompW;
   }

   public Integer getPractice() {
      return practice;
   }

   public void setPractice(Integer practice) {
      this.practice = practice;
   }

   public Integer getChannel() {
      return channel;
   }

   public void setChannel(Integer channel) {
      this.channel = channel;
   }

   public Integer getCarM() {
      return carM;
   }

   public void setCarM(Integer carM) {
      this.carM = carM;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
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

   public String getDeptName() {
      return deptName;
   }

   public void setDeptName(String deptName) {
      this.deptName = deptName;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }



}
