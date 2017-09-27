package com.huayu.hr.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.util.Date;

/**
* Created by DengPeng on 2016-12-28 10:38:14.
*/
@Table(name = "hr_recruitment_content")
public class HrRecruitmentContent extends BaseDomain  implements Serializable{

    @TableField
    private static final long serialVersionUID = 553113338633401116L;
    private Integer id;
    private String title;
    private String shortText;
    private String imgUrl;
    private String allText;
    private Integer sort;
    private Integer status;
    private Integer plateId;
    private Integer company;
    private Integer createCompany;
    private Integer area;
    private Integer type;
    private Integer count;
    private String addr;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private Date updatetime;
    private Date deletetime;
    private Long createUser;

    public HrRecruitmentContent() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public String getAreaStr(){
        if (null==area){
            return "";
        }
        switch (area){
            case 1:
                return "重庆区域";
            case 2:
                return "四川区域";
            case 3:
                return "华东区域";
            case 4:
                return "上海区域";
            case 5:
                return "长沙城市公司";
            case 6:
                return "沈阳城市公司";
            default:
                return "";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAllText() {
        return allText;
    }

    public void setAllText(String allText) {
        this.allText = allText;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getCreateCompany() {
        return createCompany;
    }

    public void setCreateCompany(Integer createCompany) {
        this.createCompany = createCompany;
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

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
