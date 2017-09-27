package com.huayu.hr.dao;

import com.huayu.hr.domain.HrDepartment;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/5.
 */
@Repository
public class DepartmentDao  extends BasePageDao<HrDepartment, Integer> {


    public List<HrDepartment> getDepartments(HrDepartment department, String queryAllCondition) {
        StringBuilder sql = new StringBuilder("SELECT d.* FROM hr_department d ");
        sql.append(" INNER JOIN hr_company c ON c.id = d.companyId");
        sql.append(" AND c.code REGEXP ");
        sql.append(queryAllCondition);
        sql.append(" WHERE d.companyId=:companyId AND d.status=0");
        if (null != department.getParentId()){
            sql.append(" AND d.parentId=:parentId");
        }
        sql.append(" ORDER BY d.rank ASC");
        return super.get(sql.toString(),department);
    }

    public List<HrDepartment> getDepartmentsIgnoreAuth(HrDepartment department) {
        StringBuilder sql = new StringBuilder("SELECT d.* FROM hr_department d WHERE d.companyId=:companyId AND status=0");
        if (null != department.getParentId()){
            sql.append(" AND d.parentId=:parentId");
        }
        sql.append(" ORDER BY d.rank ASC");
        return super.get(sql.toString(),department);
    }

    public Long countDepartments(HrDepartment department) {
        String sql = "select count(id) from hr_department where companyId=:companyId and parentId=:parentId and status=0";
        return super.getLong(sql,department);
    }

    public int updateDepartment(HrDepartment department) {
        StringBuilder sql = new StringBuilder("update hr_department set updatetime=:updatetime ");
        if (StringUtils.isNotBlank(department.getName())){
            sql.append(",name=:name");
        }
        if (null!=department.getIsParent()){
            sql.append(",isParent=:isParent");
        }
        if (null!=department.getStatus()){
            sql.append(",status=:status");
        }
        sql.append(" where id=:id and companyId=:companyId");
        return super.put(sql.toString(),department);
    }

    public int addDepartment(HrDepartment department) {
        Long longKey = super.getKey("hr_department",department);
        department.setId(longKey.intValue());
        return super.post(department);
    }

    public List<HrDepartment> getAllBaseDepartments(HrDepartment department) {
        String sql = "select * from hr_department where companyId=:companyId and isParent=0 and status=0";
        return super.get(sql,department);
    }

}
