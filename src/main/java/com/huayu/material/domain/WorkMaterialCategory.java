package com.huayu.material.domain;

import com.huayu.material.domain.validate.CategoryGroupDelete;
import com.huayu.material.domain.validate.CategoryGroupInsert;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* Created by DengPeng on 2017-4-25 18:09:26.
*/
@Table(name = "work_material_category")
public class WorkMaterialCategory extends BaseDomain  implements Serializable{

	@TableField
	private static final long serialVersionUID = -1871703590189378909L;
	@NotNull(message = "Id不能为空", groups = {CategoryGroupDelete.class})
    private Integer id;
	@NotNull(message = "父级分项工程Id不能为空", groups = {CategoryGroupInsert.class,CategoryGroupDelete.class})
    private Integer parentId;
    private Integer isParent;
	@NotEmpty(message = "分项工程名称不能为空", groups = {CategoryGroupInsert.class})
    private String name;
    private Integer status;
    private String code;
    private Date createtime;
    private Integer createUser;
    private Date deletetime;
    private Integer deleteUser;

    public WorkMaterialCategory() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

	@TableField
	private boolean contain;

	@TableField
	private List<WorkMaterialCategory> list;


	public boolean isContain() {
		return contain;
	}

	public void setContain(boolean contain) {
		this.contain = contain;
	}

	public List<WorkMaterialCategory> getList() {
		return list;
	}

	public void setList(List<WorkMaterialCategory> list) {
		this.list = list;
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

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getDeletetime() {
		return deletetime;
	}

	public void setDeletetime(Date deletetime) {
		this.deletetime = deletetime;
	}

	public Integer getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
