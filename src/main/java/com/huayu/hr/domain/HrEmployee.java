package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.common.tool.DateTimeUtil;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2016/11/21.
 */
@Table(name = "hr_employee")
public class HrEmployee extends BaseDomain implements Serializable{

    @TableField
    private static final long serialVersionUID = -1642731714403500625L;

    private Integer id;
    private String empName;
    private String idCard;
    private String ssNum;
    private Integer sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date birthdate;
    private Integer nationId;
    private Integer maritalStatus;
    private String healthStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinWorkDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date joinCompDate;
    private String zipcode;
    private String liveAddress;
    private String registrationAddress;
    private String email;
    private String compPhone;
    private String mobile;
    private String homePhone;
    private String usedName;
    private String origin;
    private String householdAddress;
    private Integer householdType;
    private String province;
    private String city;
    private Integer technicalLevel;
    private Integer politicalStatus;
    private String personalIdentity;
    private String socialInsuranceCompany;
    private String socialInsuranceAddress;
    private String houseProvidentFundCompany;
    private String houseProvidentFundAddress;
    private String wagePayCompany;
    private String managerGroup;
    private String auxiliaryCheck;
    private String bankName;
    private String bankCardNo;
    private String assessmentGroup;
    private String performanceSys;
    private String emergencyName;
    private String emergencyPhone;
    private Integer twoPlaces;
    private String photoUrl;
    private Integer company;
    private Integer department;
    private Integer lastEmpJobId;//最后一条工作记录
    private Integer badgenumber;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;
    private Integer isDelete;
    private Integer deleteUser;
    private Date deletetime;


    public HrEmployee() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrEmployee(Integer id) {
        this.id = id;
    }

    public HrEmployee(Integer company, Integer department) {
        this.company = company;
        this.department = department;
    }

    @TableField
    private List<Integer> ids;

    @TableField
    private List<Integer> badgenumbers;

    @TableField
    private String name;
    @TableField
    private String empNo;

    @TableField
    private List<Integer> companyIds;

    @TableField
    private Integer workTypeId;
    @TableField
    private Integer jobId;

    @TableField
    private String companyStr;
    @TableField
    private String departmentStr;
    @TableField
    private String workType;
    @TableField
    private String job;
    @TableField
    private String empAndJobName;

    @TableField
    private String companyStr2;
    @TableField
    private String departmentStr2;
    @TableField
    private String job2;

    @TableField
    private Integer dutyLevelId;
    @TableField
    private String dutyLevel;

    @TableField
    private Integer levelCompare;
    @TableField
    private Integer levelData;
    @TableField
    private Integer onGuard;
    @TableField
    private Integer jobSequence;
    @TableField
    private String jobSequenceStr;
    @TableField
    private Integer showChildren;
    @TableField
    private Integer empJobId;
    @TableField
    private Integer isFormal;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date turnFormalDate;

    @TableField
    private Integer contractCompany;//合同主体单位
    @TableField
    private Integer contractType;
    @TableField
    private String periodCount;
    @TableField
    private Date contractStartTime;
    @TableField
    private Date contractEndTime;
    @TableField
    private Integer isSecret;
    @TableField
    private String contractNo;
    @TableField
    private String contractNote;

    @TableField
    private Integer education;
    @TableField
    private String profession;
    @TableField
    private String school;
    @TableField
    private String educationNote;
    @TableField
    private Date graduationDate;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    private Date leavedate;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    private Date chgDate;
    @TableField
    private Integer chgType;
    @TableField
    private Integer reason;
    @TableField
    private String description;
    @TableField
    private String startDate;

    @TableField
    private String endDate;

    @TableField
    private String area;

    @TableField
    private String plate;

    @TableField
    private Integer businessId;

    @TableField
    private String oldMobile;

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public String getEmpAndJobName() {
        return empAndJobName;
    }

    public void setEmpAndJobName(String empAndJobName) {
        this.empAndJobName = empAndJobName;
    }
    public String getAuxiliaryCheck() {
        return auxiliaryCheck;
    }

    public void setAuxiliaryCheck(String auxiliaryCheck) {
        this.auxiliaryCheck = auxiliaryCheck;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getAssessmentGroup() {
        return assessmentGroup;
    }

    public void setAssessmentGroup(String assessmentGroup) {
        this.assessmentGroup = assessmentGroup;
    }

    public String getPerformanceSys() {
        return performanceSys;
    }

    public void setPerformanceSys(String performanceSys) {
        this.performanceSys = performanceSys;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public Integer getTwoPlaces() {
        return twoPlaces;
    }

    public void setTwoPlaces(Integer twoPlaces) {
        this.twoPlaces = twoPlaces;
    }
    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
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

    public String getContractTypeStr(){
        if (null==contractType){
            return "";
        }
        switch (contractType){
            case 0:
                return "劳动合同";
            case 1:
                return "聘用协议";
            case 2:
                return "实习协议";
            default:
                return "";
        }
    }

    public int getAge(){
        if (null==birthdate){
            return 0;
        }
        return DateTimeUtil.getYearSpace(birthdate,new Date());
    }

    public int getCompAge(){
        if (null==joinCompDate){
            return 0;
        }
        return DateTimeUtil.getMonthSpace(joinCompDate,new Date());
    }

    public String getIsFormalStr(){
        if (null==isFormal){
            return "";
        }
        switch (isFormal){
            case 0:
                return "试用";
            case 1:
                return "转正";
            default:
                return "";
        }
    }

    public String getSexStr(){
        if (null==sex){
            return "";
        }
        switch (sex){
            case 0:
                return "女";
            case 1:
                return "男";
            default:
                return "";
        }
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


    public String getJobSequenceStr() {
        return jobSequenceStr;
    }

    public void setJobSequenceStr(String jobSequenceStr) {
        this.jobSequenceStr = jobSequenceStr;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public List<Integer> getBadgenumbers() {
        return badgenumbers;
    }

    public void setBadgenumbers(List<Integer> badgenumbers) {
        this.badgenumbers = badgenumbers;
    }

    public Integer getBadgenumber() {
        return badgenumber;
    }

    public void setBadgenumber(Integer badgenumber) {
        this.badgenumber = badgenumber;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getChgType() {
        return chgType;
    }

    public void setChgType(Integer chgType) {
        this.chgType = chgType;
    }

    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

    public String getCompanyStr2() {
        return companyStr2;
    }

    public void setCompanyStr2(String companyStr2) {
        this.companyStr2 = companyStr2;
    }

    public String getDepartmentStr2() {
        return departmentStr2;
    }

    public void setDepartmentStr2(String departmentStr2) {
        this.departmentStr2 = departmentStr2;
    }

    public String getJob2() {
        return job2;
    }

    public void setJob2(String job2) {
        this.job2 = job2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(Date leavedate) {
        this.leavedate = leavedate;
    }

    public Integer getLastEmpJobId() {
        return lastEmpJobId;
    }

    public void setLastEmpJobId(Integer lastEmpJobId) {
        this.lastEmpJobId = lastEmpJobId;
    }

    public Integer getIsFormal() {
        return isFormal;
    }

    public void setIsFormal(Integer isFormal) {
        this.isFormal = isFormal;
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

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEducationNote() {
        return educationNote;
    }

    public void setEducationNote(String educationNote) {
        this.educationNote = educationNote;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Integer getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(Integer contractCompany) {
        this.contractCompany = contractCompany;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(String periodCount) {
        this.periodCount = periodCount;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Integer getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(Integer isSecret) {
        this.isSecret = isSecret;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNote() {
        return contractNote;
    }

    public void setContractNote(String contractNote) {
        this.contractNote = contractNote;
    }

    public Date getTurnFormalDate() {
        return turnFormalDate;
    }

    public void setTurnFormalDate(Date turnFormalDate) {
        this.turnFormalDate = turnFormalDate;
    }

    public Integer getEmpJobId() {
        return empJobId;
    }

    public void setEmpJobId(Integer empJobId) {
        this.empJobId = empJobId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getJobSequence() {
        return jobSequence;
    }

    public void setJobSequence(Integer jobSequence) {
        this.jobSequence = jobSequence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSsNum() {
        return ssNum;
    }

    public void setSsNum(String ssNum) {
        this.ssNum = ssNum;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Date getJoinWorkDate() {
        return joinWorkDate;
    }

    public void setJoinWorkDate(Date joinWorkDate) {
        this.joinWorkDate = joinWorkDate;
    }

    public Date getJoinCompDate() {
        return joinCompDate;
    }

    public void setJoinCompDate(Date joinCompDate) {
        this.joinCompDate = joinCompDate;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompPhone() {
        return compPhone;
    }

    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getUsedName() {
        return usedName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getHouseholdAddress() {
        return householdAddress;
    }

    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress;
    }

    public Integer getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(Integer householdType) {
        this.householdType = householdType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getTechnicalLevel() {
        return technicalLevel;
    }

    public void setTechnicalLevel(Integer technicalLevel) {
        this.technicalLevel = technicalLevel;
    }

    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getPersonalIdentity() {
        return personalIdentity;
    }

    public void setPersonalIdentity(String personalIdentity) {
        this.personalIdentity = personalIdentity;
    }

    public String getSocialInsuranceCompany() {
        return socialInsuranceCompany;
    }

    public void setSocialInsuranceCompany(String socialInsuranceCompany) {
        this.socialInsuranceCompany = socialInsuranceCompany;
    }


    public String getSocialInsuranceAddress() {
        return socialInsuranceAddress;
    }

    public void setSocialInsuranceAddress(String socialInsuranceAddress) {
        this.socialInsuranceAddress = socialInsuranceAddress;
    }

    public String getHouseProvidentFundCompany() {
        return houseProvidentFundCompany;
    }

    public void setHouseProvidentFundCompany(String houseProvidentFundCompany) {
        this.houseProvidentFundCompany = houseProvidentFundCompany;
    }

    public String getHouseProvidentFundAddress() {
        return houseProvidentFundAddress;
    }

    public void setHouseProvidentFundAddress(String houseProvidentFundAddress) {
        this.houseProvidentFundAddress = houseProvidentFundAddress;
    }

    public Integer getShowChildren() {
        return showChildren;
    }

    public void setShowChildren(Integer showChildren) {
        this.showChildren = showChildren;
    }

    public String getWagePayCompany() {
        return wagePayCompany;
    }

    public void setWagePayCompany(String wagePayCompany) {
        this.wagePayCompany = wagePayCompany;
    }

    public String getManagerGroup() {
        return managerGroup;
    }

    public void setManagerGroup(String managerGroup) {
        this.managerGroup = managerGroup;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
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

    public String getCompanyStr() {
        return companyStr;
    }

    public void setCompanyStr(String companyStr) {
        this.companyStr = companyStr;
    }

    public String getDepartmentStr() {
        return departmentStr;
    }

    public void setDepartmentStr(String departmentStr) {
        this.departmentStr = departmentStr;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getLevelCompare() {
        return levelCompare;
    }

    public void setLevelCompare(Integer levelCompare) {
        this.levelCompare = levelCompare;
    }

    public Integer getLevelData() {
        return levelData;
    }

    public void setLevelData(Integer levelData) {
        this.levelData = levelData;
    }

    public List<Integer> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<Integer> companyIds) {
        this.companyIds = companyIds;
    }

    public Integer getDutyLevelId() {
        return dutyLevelId;
    }

    public void setDutyLevelId(Integer dutyLevelId) {
        this.dutyLevelId = dutyLevelId;
    }

    public String getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(String dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public Integer getOnGuard() {
        return onGuard;
    }

    public void setOnGuard(Integer onGuard) {
        this.onGuard = onGuard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
