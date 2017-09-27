package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * Created by DengPeng on 2017/5/2.
 */
@Table(name = "user_qx_role_resource")
public class UserQxRoleResource extends BaseDomain implements java.io.Serializable{

    @TableField
    private static final long serialVersionUID = -8402165156856733745L;

    private Integer rid;
    private Integer roleId;

    @TableField
    private boolean checked;

    public UserQxRoleResource() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
