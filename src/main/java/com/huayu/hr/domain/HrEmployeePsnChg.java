package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-2-23 16:50:52.
*/
@Table(name = "hr_employee_psn_chg")
public class HrEmployeePsnChg extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -3963179552158335695L;
   //private String updateFiledKey = id,beginDate,chgCause,chgDate,chgFlag,chgState,chgType,endDate,lastFlag,memo,personRole,company,empId,empJobId,relaCompanyName,createtime;
 //private String updateFiledValue = id=:id,beginDate=:beginDate,chgCause=:chgCause,chgDate=:chgDate,chgFlag=:chgFlag,chgState=:chgState,chgType=:chgType,endDate=:endDate,lastFlag=:lastFlag,memo=:memo,personRole=:personRole,company=:company,empId=:empId,empJobId=:empJobId,relaCompanyName=:relaCompanyName,createtime=:createtime;
    private Integer id;
    private Date beginDate;
    private Integer chgCause;
    private Date chgDate;
    private Integer chgFlag;
    private Integer chgState;
    private Integer chgType;
    private Date endDate;
    private Integer lastFlag;
    private String memo;
    private Integer company;
    private Integer department;
    private Integer empId;
    private Integer empJobId;
    private String relaCompanyName;
    private Date createtime;
   private Integer isDelete;
   private Integer deleteUser;
   private Date deletetime;

    public HrEmployeePsnChg() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

   public HrEmployeePsnChg(Integer empId) {
      this.empId = empId;
   }

   public String getChgFlagStr(){
       if(null!=chgFlag){
         if (chgFlag==1){
            return "调入";
         }else if (chgFlag==2){
            return "调出";
         }
       }
       return "";
    }

   public String getChgStateStr(){
      if(null!=chgState){
         if (chgState==1){
            return "降职";
         }else if (chgState==2){
            return "平调";
         }else if (chgState==3){
            return "轮岗";
         }else if (chgState==5){
            return "晋升";
         }
      }
      return "";
   }

   public String getChgTypeStr(){
      if(null!=chgType){
         if (chgType==1){
            return "跨公司调动";
         }else if (chgType==2){
            return "内部调动";
         }
      }
      return "";
   }

   public String getChgCauseStr(){
      if(null!=chgType){
         if (chgCause==1){
            return "人员编制变化";
         }else if (chgCause==2){
            return "工作岗位变化";
         }else if (chgCause==2){
            return "工作地点变化";
         }else if (chgCause==2){
            return "组织结构调整";
         }
      }
      return "";
   }

   @TableField
   private String companyName;

   public Integer getIsDelete() {
      return isDelete;
   }

   public void setIsDelete(Integer isDelete) {
      this.isDelete = isDelete;
   }

   public Integer getDeleteUser() {
      return deleteUser;
   }

   public void setDeleteUser(Integer deleteUser) {
      this.deleteUser = deleteUser;
   }

   public Date getDeletetime() {
      return deletetime;
   }

   public void setDeletetime(Date deletetime) {
      this.deletetime = deletetime;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getBeginDate() {
      return beginDate;
   }

   public void setBeginDate(Date beginDate) {
      this.beginDate = beginDate;
   }

   public Integer getChgCause() {
      return chgCause;
   }

   public void setChgCause(Integer chgCause) {
      this.chgCause = chgCause;
   }

   public Date getChgDate() {
      return chgDate;
   }

   public void setChgDate(Date chgDate) {
      this.chgDate = chgDate;
   }

   public Integer getChgFlag() {
      return chgFlag;
   }

   public void setChgFlag(Integer chgFlag) {
      this.chgFlag = chgFlag;
   }

   public Integer getChgState() {
      return chgState;
   }

   public void setChgState(Integer chgState) {
      this.chgState = chgState;
   }

   public Integer getChgType() {
      return chgType;
   }

   public void setChgType(Integer chgType) {
      this.chgType = chgType;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public Integer getLastFlag() {
      return lastFlag;
   }

   public void setLastFlag(Integer lastFlag) {
      this.lastFlag = lastFlag;
   }

   public String getMemo() {
      return memo;
   }

   public void setMemo(String memo) {
      this.memo = memo;
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

   public Integer getEmpId() {
      return empId;
   }

   public void setEmpId(Integer empId) {
      this.empId = empId;
   }

   public Integer getEmpJobId() {
      return empJobId;
   }

   public void setEmpJobId(Integer empJobId) {
      this.empJobId = empJobId;
   }

   public String getRelaCompanyName() {
      return relaCompanyName;
   }

   public void setRelaCompanyName(String relaCompanyName) {
      this.relaCompanyName = relaCompanyName;
   }

   public Date getCreatetime() {
      return createtime;
   }

   public void setCreatetime(Date createtime) {
      this.createtime = createtime;
   }
}
