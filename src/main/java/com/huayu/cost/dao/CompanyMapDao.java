package com.huayu.cost.dao;

import com.huayu.cost.domain.CompanyMap;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 组织架构 映射
 * @author ZXL 2017-07-07 9:38
 **/
@Repository
public class CompanyMapDao extends BasePageDao<CompanyMap,Serializable> {

    /**
     * 获取全部公司
     * @param companyMap
     * @return
     */
    public List<CompanyMap> getAllOfParent(CompanyMap companyMap){
        String sql = "SELECT * FROM company_map WHERE type=1;";
        return super.get(sql,companyMap);
    }

    /**
     * 以父ID为条件获取数据
     * @param companyMap
     * @return
     */
    public List<CompanyMap> getAllByParentId(CompanyMap companyMap){
        String sql = "SELECT * FROM company_map WHERE type=2 AND parentId=:parentId";
        return super.get(sql,companyMap);
    }

    /**
     * 以父ID为条件获取数据
     * @param companyMap
     * @return
     */
    public List<CompanyMap> getAllToAddByParentId(CompanyMap companyMap){
        String sql = "SELECT * FROM company_map p WHERE p.type=2 AND p.parentId=:parentId AND NOT EXISTS(SELECT * FROM cost_department t WHERE t.companyId=:parentId AND t.`year`=:year AND t.departmentId=p.id)";
        return super.get(sql,companyMap);
    }

}
