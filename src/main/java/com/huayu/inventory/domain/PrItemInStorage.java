package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_item_in_storage")
public class PrItemInStorage extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -5969313551874096058L;
    //private String updateFiledKey = id,repositoryId,inStorageNum,billNum,remark,inStoreDate,inStoreUser,createtime,createUser;
 //private String updateFiledValue = id=:id,repositoryId=:repositoryId,inStorageNum=:inStorageNum,billNum=:billNum,remark=:remark,inStoreDate=:inStoreDate,inStoreUser=:inStoreUser,createtime=:createtime,createUser=:createUser;
    private Integer id;
    @TableField
    private Integer repositoryId;
    private String inStorageNum;
    private String billNum;
    private String remark;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date inStoreDate;
    private String inStoreUser;
    private Date createtime;
    private Integer createUser;
    private Integer projectId;
    private String billReceiver;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date billReceiveDate;
    private Date updatetime;
    private Integer updateUser;

    @TableField
    private String itemProviderName;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @TableField
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    @TableField
    private String repositoryName;
    @TableField
    private String repositoryPersonName;
    @TableField
    private Integer repositoryPersonId;
    @TableField
    private String plan;
    @TableField
    private String repositoryPerson;

    @TableField
    private String materialName;
    @TableField
    private String specification;
    @TableField
    private String oldBillReceiver;



    public PrItemInStorage() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getOldBillReceiver() {
        return oldBillReceiver;
    }

    public void setOldBillReceiver(String oldBillReceiver) {
        this.oldBillReceiver = oldBillReceiver;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getBillReceiver() {
        return billReceiver;
    }

    public void setBillReceiver(String billReceiver) {
        this.billReceiver = billReceiver;
    }

    public Date getBillReceiveDate() {
        return billReceiveDate;
    }

    public void setBillReceiveDate(Date billReceiveDate) {
        this.billReceiveDate = billReceiveDate;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRepositoryPerson() {
        return repositoryPerson;
    }

    public void setRepositoryPerson(String repositoryPerson) {
        this.repositoryPerson = repositoryPerson;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRepositoryPersonId() {
        return repositoryPersonId;
    }

    public void setRepositoryPersonId(Integer repositoryPersonId) {
        this.repositoryPersonId = repositoryPersonId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getItemProviderName() {
        return itemProviderName;
    }

    public void setItemProviderName(String itemProviderName) {
        this.itemProviderName = itemProviderName;
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

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryPersonName() {
        return repositoryPersonName;
    }

    public void setRepositoryPersonName(String repositoryPersonName) {
        this.repositoryPersonName = repositoryPersonName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getInStorageNum() {
        return inStorageNum;
    }

    public void setInStorageNum(String inStorageNum) {
        this.inStorageNum = inStorageNum;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getInStoreDate() {
        return inStoreDate;
    }

    public void setInStoreDate(Date inStoreDate) {
        this.inStoreDate = inStoreDate;
    }

    public String getInStoreUser() {
        return inStoreUser;
    }

    public void setInStoreUser(String inStoreUser) {
        this.inStoreUser = inStoreUser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
