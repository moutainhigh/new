package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-1-18 15:17:34.
*/
@Table(name = "hr_train_attachment")
public class HrTrainAttachment extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -5653245285036683218L;
    //private String updateFiledKey = id,trainId,attachUrl,createtime,status;
 //private String updateFiledValue = id=:id,trainId=:trainId,attachUrl=:attachUrl,createtime=:createtime,status=:status;
    private Integer id;
    private Integer trainId;
    private String sourceName;
    private String attachUrl;
    private Date createtime;
    private Integer status;

    public HrTrainAttachment() {
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

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
