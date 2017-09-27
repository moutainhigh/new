package com.huayu.inventory.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.huayu.inventory.domain.validate.GroupDelete;
import com.huayu.inventory.domain.validate.GroupInsert;
import com.huayu.inventory.domain.validate.GroupUpdate;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-6-12 13:58:55.
*/
@Table(name = "pr_item_out_storage")
public class PrItemOutStorage extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -7140419691601459634L;
    //private String updateFiledKey = id,outStorageNum,repositoryId,outStorageDate,projectHouseNum,itemReceiver,remark,createtime,createUser;
 //private String updateFiledValue = id=:id,outStorageNum=:outStorageNum,repositoryId=:repositoryId,outStorageDate=:outStorageDate,projectHouseNum=:projectHouseNum,itemReceiver=:itemReceiver,remark=:remark,createtime=:createtime,createUser=:createUser;
    @NotNull(message = "出库Id不能为空", groups = {GroupDelete.class, GroupUpdate.class})
    private Integer id;
    @NotNull(message = "出库单号不能为空", groups = {GroupUpdate.class})
    private String outStorageNo;
    private Integer projectId;
    @NotNull(message = "仓库Id不能为空", groups = {GroupDelete.class})
    private Integer repositoryId;
    @NotNull(message = "出库日期不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date outStorageDate;
    private String projectHouseNum;
    @NotEmpty(message = "库管员不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private String checkUser;
    @NotEmpty(message = "领用人不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    private String itemReceiver;
    private String itemReceiverUnit;
    private String remark;
    private Date createtime;
    private Integer createUser;
    private Date updatetime;
    private Integer updateUser;

    @TableField
    private String projectName;
    @TableField
    private String repositoryName;
    @TableField
    private String itemReceiverName;
    @TableField
    private String itemReceiverUnitName;
    @TableField
    private String projectHouseNumName;
    @TableField
    private String checkUserName;
    @TableField
    private String materialName;
    @TableField
    private String specification;
    @TableField
    private String billReceiver;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date billReceiveDate;
    @TableField
    @NotEmpty(message = "出库对象不能为空", groups = {GroupInsert.class})
    private String listStr;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @TableField
    private String oldBillReceiver;
    @TableField
    private String useStr;
    @TableField
    private String use;

    public PrItemOutStorage() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }


    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getUseStr() {
        return useStr;
    }

    public void setUseStr(String useStr) {
        this.useStr = useStr;
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

    public String getItemReceiverUnitName() {
        return itemReceiverUnitName;
    }

    public void setItemReceiverUnitName(String itemReceiverUnitName) {
        this.itemReceiverUnitName = itemReceiverUnitName;
    }

    public String getItemReceiverUnit() {
        return itemReceiverUnit;
    }

    public void setItemReceiverUnit(String itemReceiverUnit) {
        this.itemReceiverUnit = itemReceiverUnit;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getListStr() {
        return listStr;
    }

    public void setListStr(String listStr) {
        this.listStr = listStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getOutStorageNo() {
        return outStorageNo;
    }

    public void setOutStorageNo(String outStorageNo) {
        this.outStorageNo = outStorageNo;
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Date getOutStorageDate() {
        return outStorageDate;
    }

    public void setOutStorageDate(Date outStorageDate) {
        this.outStorageDate = outStorageDate;
    }

    public String getProjectHouseNum() {
        return projectHouseNum;
    }

    public void setProjectHouseNum(String projectHouseNum) {
        this.projectHouseNum = projectHouseNum;
    }

    public String getItemReceiverName() {
        return itemReceiverName;
    }

    public void setItemReceiverName(String itemReceiverName) {
        this.itemReceiverName = itemReceiverName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getItemReceiver() {
        return itemReceiver;
    }

    public void setItemReceiver(String itemReceiver) {
        this.itemReceiver = itemReceiver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getProjectHouseNumName() {
        return projectHouseNumName;
    }

    public void setProjectHouseNumName(String projectHouseNumName) {
        this.projectHouseNumName = projectHouseNumName;
    }
}
