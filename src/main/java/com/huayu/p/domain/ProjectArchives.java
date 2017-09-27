package com.huayu.p.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 项目档案
* Created by ZXL on 2017-5-19 9:55:03.
*/
@Table(name = "project_archives")
public class ProjectArchives extends BaseDomain  implements Serializable{

    private Long projectId;//ID
    private Short status;//是否删除 1删除 2正常
    private String projectCode;//项目编码
    private String projectName;//项目名称
    private Short isParent;//是否父类1 否 2是
    private Long parentId;//父ID
    private Integer areaId;//所属区域ID
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;//计划开始日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;//计划截至日期
    private Short isPlan;//计划编制项目 1否 2是
    private Short isCompile;//是否编制项目 1否 2是
    private String remark;//备注
    private Date createDate;//创建时间
    private Long createUserId;//创建人员
    private Date updateDate;//修改时间
    private Long updateUserId;//修改人员
    private Date deleteDate;//删除时间
    private Long deleteUserId;//删除时间
    private Long sort;//排序
    //20170523 ADD
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date documentsDate;//单据日期
    private Integer versions;//版本
    private Integer sufVersions;//版本
    private Short isOn;//是否生效 1否 2是
    private Date onDate;//生效日期
    private Long onUserId;//生效操作人员ID
    private Date planUpdateDate;//计划修改日期
    private Long planUpdateUserId;//计划修改人
    private Integer associatedNum;//关联次数
    private Integer nodeNum;//节点个数
    private Integer successNodeNum;//完成节点个数
    private Integer allWeight;//节点权重
    private Integer successWeight;//完成节点权重
    private Short isComplete;//是否完成 1否2是

    @TableField
    private String parentName;//上级项目名称
    @TableField
    private String parentCode;//上级项目名称
    @TableField
    private String areaName;//所属区域名称
    @TableField
    private String isPlanName;//计划编制项目 1否 2是
    @TableField
    private String dataAuthority;//权限
    @TableField
    private String successNodeNumRate;//节点完成率
    @TableField
    private String successWeightRate;//权重得分率
    @TableField
    private String successNodeNumRate2;//节点完成率
    @TableField
    private String successWeightRate2;//权重得分率
    @TableField
    private String isCompleteName;//是否完成 1否2是

    public ProjectArchives() {
        this.dt = "desc";
        this.dtn = "projectId";
        this.pageSize = 20;
    }

    public String getIsCompleteName() {
        return null!=getIsComplete()?(getIsComplete()==2?"已完成":"未完成"):isCompleteName;
    }

    public void setIsCompleteName(String isCompleteName) {
        this.isCompleteName = isCompleteName;
    }

    public String getSuccessNodeNumRate() {
        if (null!=getNodeNum()&&null!=getSuccessNodeNum()){
            //return new BigDecimal((float)getSuccessNodeNum()/getNodeNum()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%";
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String num = df.format((float)getSuccessNodeNum()/getNodeNum()*100);//返回的是String类型
            return num+"%";
        }
        return successNodeNumRate;
    }

    public void setSuccessNodeNumRate(String successNodeNumRate) {
        this.successNodeNumRate = successNodeNumRate;
    }

    public String getSuccessWeightRate() {
        if (null!=getAllWeight()&&null!=getSuccessWeight()){
            //return new BigDecimal((float)getSuccessWeight()/getAllWeight()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%";
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String num = df.format((float)getSuccessWeight()/getAllWeight()*100);//返回的是String类型
            return num+"%";
        }
        return successWeightRate;
    }

    public void setSuccessWeightRate(String successWeightRate) {
        this.successWeightRate = successWeightRate;
    }

    public String getSuccessNodeNumRate2() {
        if (null!=getNodeNum()&&null!=getSuccessNodeNum()){
            //return new BigDecimal((float)getSuccessNodeNum()/getNodeNum()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%";
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String num = df.format((float)getSuccessNodeNum()/getNodeNum()*100);//返回的是String类型
            return num;
        }
        return successNodeNumRate2;
    }

    public void setSuccessNodeNumRate2(String successNodeNumRate2) {
        this.successNodeNumRate2 = successNodeNumRate2;
    }

    public String getSuccessWeightRate2() {
        if (null!=getAllWeight()&&null!=getSuccessWeight()){
            //return new BigDecimal((float)getSuccessWeight()/getAllWeight()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"%";
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String num = df.format((float)getSuccessWeight()/getAllWeight()*100);//返回的是String类型
            return num;
        }
        return successWeightRate2;
    }

    public void setSuccessWeightRate2(String successWeightRate2) {
        this.successWeightRate2 = successWeightRate2;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public Integer getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Integer getSuccessNodeNum() {
        return successNodeNum;
    }

    public void setSuccessNodeNum(Integer successNodeNum) {
        this.successNodeNum = successNodeNum;
    }

    public Integer getAllWeight() {
        return allWeight;
    }

    public void setAllWeight(Integer allWeight) {
        this.allWeight = allWeight;
    }

    public Integer getSuccessWeight() {
        return successWeight;
    }

    public void setSuccessWeight(Integer successWeight) {
        this.successWeight = successWeight;
    }

    public String getDataAuthority() {
        return dataAuthority;
    }

    public void setDataAuthority(String dataAuthority) {
        this.dataAuthority = dataAuthority;
    }

    public Integer getAssociatedNum() {
        return associatedNum;
    }

    public void setAssociatedNum(Integer associatedNum) {
        this.associatedNum = associatedNum;
    }

    public Integer getVersions() {
        return versions;
    }

    public void setVersions(Integer versions) {
        this.versions = versions;
    }

    public Integer getSufVersions() {
        return sufVersions;
    }

    public void setSufVersions(Integer sufVersions) {
        this.sufVersions = sufVersions;
    }

    public Short getIsCompile() {
        return isCompile;
    }

    public void setIsCompile(Short isCompile) {
        this.isCompile = isCompile;
    }

    public Date getDocumentsDate() {
        return documentsDate;
    }

    public void setDocumentsDate(Date documentsDate) {
        this.documentsDate = documentsDate;
    }

    public Short getIsOn() {
        return isOn;
    }

    public void setIsOn(Short isOn) {
        this.isOn = isOn;
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

    public Date getPlanUpdateDate() {
        return planUpdateDate;
    }

    public void setPlanUpdateDate(Date planUpdateDate) {
        this.planUpdateDate = planUpdateDate;
    }

    public Long getPlanUpdateUserId() {
        return planUpdateUserId;
    }

    public void setPlanUpdateUserId(Long planUpdateUserId) {
        this.planUpdateUserId = planUpdateUserId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Short getIsParent() {
        return isParent;
    }

    public void setIsParent(Short isParent) {
        this.isParent = isParent;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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

    public Short getIsPlan() {
        return isPlan;
    }

    public void setIsPlan(Short isPlan) {
        this.isPlan = isPlan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Long getDeleteUserId() {
        return deleteUserId;
    }

    public void setDeleteUserId(Long deleteUserId) {
        this.deleteUserId = deleteUserId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIsPlanName() {
        return null!=getIsPlan()?(getIsPlan()==1?"否":(getIsPlan()==2?"是":isPlanName)):isPlanName;
    }

    public void setIsPlanName(String isPlanName) {
        this.isPlanName = isPlanName;
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
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectArchives that = (ProjectArchives) o;
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (isPlan != null ? !isPlan.equals(that.isPlan) : that.isPlan != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (documentsDate != null ? !documentsDate.equals(that.documentsDate) : that.documentsDate != null) return false;
        return true;
    }

}
