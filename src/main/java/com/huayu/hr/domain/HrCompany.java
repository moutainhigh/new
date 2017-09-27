package com.huayu.hr.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
* Created by DengPeng on 2016-12-2 11:38:04.
*/
@Table(name = "hr_company")
public class HrCompany extends BaseDomain implements Serializable{

    @TableField
    private static final long serialVersionUID = -1359183731753168074L;
    private Integer id;
    private String name;
    private String plate;
    private Integer plateId;
    private String area;
    private Integer isParent;
    private Integer parentId;
    private String code;
    private Date createtime;
    private Date updatetime;
    private Date deletetime;
    private Integer status;



    public HrCompany() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public HrCompany(Integer id) {
        this.id = id;
        this.status = 0;
    }

    @TableField
    private List<HrCompany> childList;
    @TableField
    private boolean isContain;

    public boolean isContain() {
        return isContain;
    }

    public void setContain(boolean contain) {
        isContain = contain;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTreeNodeName() {
        return name+"-"+code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<HrCompany> getChildList() {
        return childList;
    }

    public void setChildList(List<HrCompany> childList) {
        this.childList = childList;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }
}
