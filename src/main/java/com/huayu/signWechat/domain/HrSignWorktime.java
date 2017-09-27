package com.huayu.signWechat.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import java.io.Serializable;
import java.sql.Time;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-8-4 15:28:31.
*/
@Table(name = "hr_sign_worktime")
public class HrSignWorktime extends BaseDomain  implements Serializable{

 //private String updateFiledKey = id,groupname,checkInTime,checkOutTime,location;
 //private String updateFiledValue = id=:id,groupname=:groupname,checkInTime=:checkInTime,checkOutTime=:checkOutTime,location=:location;
    private Integer id;
    private String groupname;
    private Time checkInTime;
    private Time checkOutTime;
    private String location;

    public HrSignWorktime() {
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

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
