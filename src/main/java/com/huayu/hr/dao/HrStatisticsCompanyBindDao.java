package com.huayu.hr.dao;

import com.huayu.hr.domain.HrStatisticsCompanyBind;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/2/16.
 */
@Repository
public class HrStatisticsCompanyBindDao extends BasePageDao<HrStatisticsCompanyBind,Integer>{

    public List<HrStatisticsCompanyBind> getByParentId(HrStatisticsCompanyBind bind) {
        String sql  = "select b.id,b.name,b.companyId,b.parentId,b.isParent,b.status,c.code companyCode from hr_statistics_company_bind b " +
                " LEFT JOIN hr_company c ON c.id = b.companyId where b.parentId=:parentId and b.status=0";
        return super.get(sql,bind);
    }
}
