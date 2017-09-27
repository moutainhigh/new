package com.huayu.bill.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.util.Date;

/**
 * Created by DengPeng on 2016/11/21.
 */
@Table(name = "bill_user_permission")
public class BillUserPermission extends BaseDomain {

    private Integer id;
    private Long userId;
    private Integer permissionId;
    private Integer status;
    private Long createUserId;
    private Long updateUserId;
    private Date createtime;
    private Date updatetime;


    public BillUserPermission() {
    }

    public BillUserPermission(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
