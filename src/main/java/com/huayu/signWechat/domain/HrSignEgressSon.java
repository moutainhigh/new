package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2017-8-25 17:08:20.
*/
@Table(name = "hr_sign_egress_son")
public class HrSignEgressSon extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,formmainid,startTime,endTime,locationDetail,notes,createDate,status;
 //private String updateFiledValue = id=:id,formmainid=:formmainid,startTime=:startTime,endTime=:endTime,locationDetail=:locationDetail,notes=:notes,createDate=:createDate,status=:status;
    private Integer id;
    private String formmainid;
    private Date startTime;
    private Date endTime;
    private String locationDetail;
    private String notes;
    private Date createDate;
    private Integer status;

    public HrSignEgressSon() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormmainid() {
        return formmainid;
    }

    public void setFormmainid(String formmainid) {
        this.formmainid = formmainid;
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

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
