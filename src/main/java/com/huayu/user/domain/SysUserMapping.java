package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
* Created by DengPeng on 2017-4-18 13:25:08.
*/
@Table(name = "sys_user_mapping")
public class SysUserMapping extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = -5928950683016163929L;
    private Integer id;
    private String userName;
    private String seeyon;
    private String mingyuan;

    public SysUserMapping() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSeeyon() {
        return seeyon;
    }

    public void setSeeyon(String seeyon) {
        this.seeyon = seeyon;
    }

    public String getMingyuan() {
        return mingyuan;
    }

    public void setMingyuan(String mingyuan) {
        this.mingyuan = mingyuan;
    }

}
