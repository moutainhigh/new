package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.TableField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 *项目计划编制
* Created by ZXL on 2017-5-19 9:57:32.
*/
public class BaseProjectPlanCompile extends BaseDomain  implements Serializable{

    private Long compileId;//项目计划编制ID
    private Long projectId;//项目档案ID
    private Integer templateId;//模板ID
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;//开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;//结束时间
    private Integer weight;//权重分值
    private Integer preWarnDay;//开始前预警天数
    private Integer sufWarnDay;//结束前预警天数
    private String departmentId;//部门ID
    private String department;//部门名称
    private String warnUserId;//预警人员ID
    private String warnName;//预警人员名称
    private String remark;//备注
    private Short isComplete;//是否完成 1空2审核成功3审核中4审核失败
   // @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date completeDate;//完成时间
    private String completeRemark;//完成情况描述
    private String noCompleteRemark;//未完成情况说明
    private Date updateDate;//修改时间
    private Long updateUserId;//修改人员ID
    private Integer versions;//版本号
    private Date createDate;//创建时间
    private Long createUserId;//创建人员
    private Date onDate;//创建时间
    private Long onUserId;//创建人员
    private String warnPhone;//预警电话
    private Date reCompleteDate;//最近完成时间

    @TableField
    private String stepName;//阶段名称
    @TableField
    private String taskName;//任务名称
    @TableField
    private Short iskeyNode;//是否关键节点 1否2是
    @TableField
    private String shortDepartment;//部门名称缩写
    @TableField
    private String shortWarnName;//预警人员名称缩写
    @TableField
    private String projectName;//项目档案名称
    @TableField
    private String iskeyNodeName;//是否关键节点中文名称
    @TableField
    private Integer warnDay;//预警天数
    @TableField
    private String areaName;//区域名称
    @TableField
    private String successWeightRate;//权重得分率
    @TableField
    private Integer allWeight;//节点权重
    @TableField
    private String d3;//时间
    @TableField
    private String d4;//时间

    public BaseProjectPlanCompile() {
        this.dt = "desc";
        this.dtn = "compileId";
        this.pageSize = 20;
    }

    public String getSuccessWeightRate() {
        if (null!=getAllWeight()&&null!=getWeight()){
            //return new BigDecimal((float)getSuccessNodeNum()/getNodeNum()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%";
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String num = df.format((float)getWeight()/getAllWeight()*100);//返回的是String类型
            return num+"%";
        }
        return successWeightRate;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public void setSuccessWeightRate(String successWeightRate) {
        this.successWeightRate = successWeightRate;
    }

    public Integer getAllWeight() {
        return allWeight;
    }

    public void setAllWeight(Integer allWeight) {
        this.allWeight = allWeight;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getWarnDay() {
        return warnDay;
    }

    public void setWarnDay(Integer warnDay) {
        this.warnDay = warnDay;
    }

    public String getIskeyNodeName() {
        return null!=getIskeyNode()?(getIskeyNode()==2?"关键":"一级"):iskeyNodeName;
    }

    public void setIskeyNodeName(String iskeyNodeName) {
        this.iskeyNodeName = iskeyNodeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getReCompleteDate() {
        return reCompleteDate;
    }

    public void setReCompleteDate(Date reCompleteDate) {
        this.reCompleteDate = reCompleteDate;
    }

    public String getWarnPhone() {
        return warnPhone;
    }

    public void setWarnPhone(String warnPhone) {
        this.warnPhone = warnPhone;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Long getOnUserId() {
        return onUserId;
    }

    public void setOnUserId(Long onUserId) {
        this.onUserId = onUserId;
    }

    public Integer getVersions() {
        return versions;
    }

    public void setVersions(Integer versions) {
        this.versions = versions;
    }

    public String getShortDepartment() {
        return StringUtils.isNotBlank(getDepartment())?(getDepartment().length()>8?(getDepartment().substring(0,8)+"..."):getDepartment()):shortDepartment;
    }

    public void setShortDepartment(String shortDepartment) {
        this.shortDepartment = shortDepartment;
    }

    public String getShortWarnName() {
        return StringUtils.isNotBlank(getWarnName())?(getWarnName().length()>8?(getWarnName().substring(0,8)+"..."):getWarnName()):shortWarnName;
    }

    public void setShortWarnName(String shortWarnName) {
        this.shortWarnName = shortWarnName;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Short getIskeyNode() {
        return iskeyNode;
    }

    public void setIskeyNode(Short iskeyNode) {
        this.iskeyNode = iskeyNode;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Long getCompileId() {
        return compileId;
    }

    public void setCompileId(Long compileId) {
        this.compileId = compileId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPreWarnDay() {
        return preWarnDay;
    }

    public void setPreWarnDay(Integer preWarnDay) {
        this.preWarnDay = preWarnDay;
    }

    public Integer getSufWarnDay() {
        return sufWarnDay;
    }

    public void setSufWarnDay(Integer sufWarnDay) {
        this.sufWarnDay = sufWarnDay;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWarnUserId() {
        return warnUserId;
    }

    public void setWarnUserId(String warnUserId) {
        this.warnUserId = warnUserId;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteRemark() {
        return completeRemark;
    }

    public void setCompleteRemark(String completeRemark) {
        this.completeRemark = completeRemark;
    }

    public String getNoCompleteRemark() {
        return noCompleteRemark;
    }

    public void setNoCompleteRemark(String noCompleteRemark) {
        this.noCompleteRemark = noCompleteRemark;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString(String s) {
        StringBuilder sb = new StringBuilder("?");
        if(dt!=null && s.contains("dt")) {
            sb.append("dt="+dt);
        }
        if(pageSize!=null && s.contains("pageSize")) {
            sb.append("&pageSize="+pageSize);
        }
        if(pageNo!=null && s.contains("pageNo")) {
            sb.append("&pageNo="+pageNo);
        }
        if(dtn!=null && s.contains("dtn")) {
            sb.append("&dtn="+dtn);
        }
        if(s1!=null && s.contains("s1")) {
            sb.append("&s1="+s1);
        }
        if(i1!=null && s.contains("i1")) {
            sb.append("&i1="+i1);
        }
        if(getD3()!=null && s.contains("d3")) {
            sb.append("&d3="+getD3());
        }
        if(getD4()!=null && s.contains("d4")) {
            sb.append("&d4="+getD4());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseProjectPlanCompile that = (BaseProjectPlanCompile) o;

        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (preWarnDay != null ? !preWarnDay.equals(that.preWarnDay) : that.preWarnDay != null) return false;
        if (sufWarnDay != null ? !sufWarnDay.equals(that.sufWarnDay) : that.sufWarnDay != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (warnUserId != null ? !warnUserId.equals(that.warnUserId) : that.warnUserId != null) return false;
        if (warnName != null ? !warnName.equals(that.warnName) : that.warnName != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        return true;
    }

    public boolean equals2(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseProjectPlanCompile that = (BaseProjectPlanCompile) o;

        if (completeRemark != null ? !completeRemark.equals(that.completeRemark) : that.completeRemark != null) return false;
        if (noCompleteRemark != null ? !noCompleteRemark.equals(that.noCompleteRemark) : that.noCompleteRemark != null) return false;
        return true;
    }

}
