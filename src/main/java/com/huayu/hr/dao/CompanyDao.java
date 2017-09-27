package com.huayu.hr.dao;

import com.huayu.hr.domain.HrCompany;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/5.
 */
@Repository
public class CompanyDao  extends BasePageDao<HrCompany, Integer> {


    public List<HrCompany> getCompanyListByParentId(HrCompany company, String queryAllCondition) {
        StringBuilder sql  = new StringBuilder("select * from hr_company where parentId=:parentId and status=0");
        sql.append(" and code REGEXP ").append(queryAllCondition);
        sql.append(" order by rank asc ");
        return super.get(sql.toString(),company);
    }

    public List<HrCompany> getCompanyList(String queryAllCondition) {
        StringBuilder sql  = new StringBuilder("select * from hr_company where status=0");
        sql.append(" and code REGEXP ").append(queryAllCondition.replace("!",""));
        sql.append(" order by rank asc ");
        return super.get(sql.toString(),new HrCompany());
    }

    public List<HrCompany> getCompanyListByParentId(HrCompany company) {
        StringBuilder sql  = new StringBuilder("select * from hr_company where parentId=:parentId and status=0");
        sql.append(" order by rank asc ");
        return super.get(sql.toString(),company);
    }

    public List<HrCompany> getAllBaseCompanies(HrCompany company) {
        StringBuilder sql  = new StringBuilder("select * from hr_company where status=0");
        sql.append(" and isParent = 0 ");
        sql.append(" order by rank asc ");
        return super.get(sql.toString(),company);
    }


    public List<HrCompany> getAllCompanies(HrCompany company) {
        StringBuilder sql  = new StringBuilder("select * from hr_company where status=0");
        sql.append(" order by rank asc ");
        return super.get(sql.toString(),company);
    }

    public int addCompany(HrCompany company) {
        Long longKey = super.getKey("hr_company",company);
        company.setId(longKey.intValue());
        return super.post(company);
    }

    public int updateCompany(HrCompany company) {
        StringBuilder sql = new StringBuilder("update hr_company set updatetime=:updatetime");
        if (StringUtils.isNotBlank(company.getName())){
            sql.append(", name=:name");
        }
        if (null != company.getIsParent()){
            sql.append(", isParent=:isParent");
        }
        sql.append(" where id=:id");
        return super.put(sql.toString(),company);
    }

    public int delCompany(HrCompany company) {
        String sql = "update hr_company set deletetime=:deletetime, status=:status where id=:id";
        return super.put(sql,company);
    }

    public HrCompany getOne(HrCompany company) {
        String sql = "select id,name,isParent,parentId,code,plateId,isParent,status from hr_company where id=:id and status=:status";
        return super.getOne(sql,company);
    }

    public List<HrCompany> getAllPlat() {
        String sql = "select * from hr_company WHERE `status` = 0 GROUP BY plate ";
        return super.get(sql, new HrCompany());
    }
}
