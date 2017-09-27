package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2016-12-19 10:35:55.
*/
@Table(name = "hr_education_info")
public class HrEducationInfo extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 7657827566164961728L;
    //private String updateFiledKey = id,empId,admissionDate,graduationDate,school,profession,education,degree,note,degreeDate,degreeOrg;
 //private String updateFiledValue = id=:id,empId=:empId,admissionDate=:admissionDate,graduationDate=:graduationDate,school=:school,profession=:profession,education=:education,degree=:degree,note=:note,degreeDate=:degreeDate,degreeOrg=:degreeOrg;
    private Integer id;
    private Integer empId;
    private Date admissionDate;
    private Date graduationDate;
    private Integer topEducation;
    private String school;
    private String profession;
    @TableField
    private String professionStr;

    private Integer education;
    @TableField
    private String educationStr;
    private Integer degree;

    @TableField
    private String degreeStr;
    private String note;
    private Date degreeDate;
    private String degreeOrg;
    private Integer status;
    private Date createtime;
    private Date updatetime;
    private Date deletetime;

    public HrEducationInfo() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrEducationInfo(Integer empId) {
        this.empId = empId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public String getProfessionStr() {
        return professionStr;
    }

    public void setProfessionStr(String professionStr) {
        this.professionStr = professionStr;
    }

    public String getEducationStr() {
        return educationStr;
    }

    public void setEducationStr(String educationStr) {
        this.educationStr = educationStr;
    }

    public String getDegreeStr() {
        return degreeStr;
    }

    public void setDegreeStr(String degreeStr) {
        this.degreeStr = degreeStr;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Integer getTopEducation() {
        return topEducation;
    }

    public void setTopEducation(Integer topEducation) {
        this.topEducation = topEducation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDegreeDate() {
        return degreeDate;
    }

    public void setDegreeDate(Date degreeDate) {
        this.degreeDate = degreeDate;
    }

    public String getDegreeOrg() {
        return degreeOrg;
    }

    public void setDegreeOrg(String degreeOrg) {
        this.degreeOrg = degreeOrg;
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
}
