package com.huayu.bill.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Table(name = "bill_dict")
public class BillDic extends BaseDomain implements Serializable {

    @TableField
    private static final long serialVersionUID = -2278191057248390415L;

    @TableField
    private String type;
    private Integer id;
    private Integer parentId;
    private Integer level;
    private String code;
    private String value;
    @TableField
    private String parentName;
    private Date createtime;
    private Long createuser;
    private Date updatetime;
    private Long updateuser;




    public BillDic() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public BillDic(Integer id) {
        this.id = id;
        this.idName = "id";
    }

    public BillDic(Integer id, Integer parentId, Integer level, String code, String value, Date createtime, Long createuser, Date updatetime, Long updateuser) {
        this.id = id;
        this.parentId = parentId;
        this.level = level;
        this.code = code;
        this.value = value;
        this.createtime = createtime;
        this.createuser = createuser;
        this.updatetime = updatetime;
        this.updateuser = updateuser;
    }


    @Override
    public String toString(String s) {
        StringBuilder sb = new StringBuilder("?");
        if(level!=null && s.contains("type"))
            sb.append("type="+type);
        if(pageNo!=null && s.contains("pageNo"))
            sb.append("&pageNo="+pageNo);
        if(pageSize!=null && s.contains("pageSize"))
            sb.append("&pageSize="+pageSize);
        if(dt!=null && s.contains("dt"))
            sb.append("&dt="+dt);
        if(dtn!=null && s.contains("dtn"))
            sb.append("&dtn="+dtn);
        return sb.toString();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getCreateuser() {
        return createuser;
    }

    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(Long updateuser) {
        this.updateuser = updateuser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        if ("area".equals(type)){
            this.level = 0;
        }else  if ("company".equals(type)){
            this.level = 1;
        }else if ("project".equals(type)){
            this.level = 2;
        }else if ("stage".equals(type)){
            this.level = 3;
        }else {
            this.level = 0;
        }
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
