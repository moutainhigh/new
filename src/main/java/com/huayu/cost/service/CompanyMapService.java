package com.huayu.cost.service;

import com.huayu.cost.dao.CompanyMapDao;
import com.huayu.cost.domain.CompanyMap;
import com.huayu.p.web.tools.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织架构 映射
 * @author ZXL 2017-07-07 9:44
 **/
@Service
public class CompanyMapService {

    @Autowired
    private CompanyMapDao companyMapDao;

    public CompanyMap getOne(String id){
        CompanyMap companyMap = new CompanyMap();
        companyMap.setId(id);
        companyMap.setIdName("id");
        return companyMapDao.getOne(companyMap);
    }

    /**
     * 获取全部公司
     * @param companyMap
     * @return
     */
    public List<CompanyMap> getAllOfParent(){
        return companyMapDao.getAllOfParent(new CompanyMap());
    }

    /**
     * 以父ID为条件获取数据
     * @param parentId
     * @return
     */
    public List<CompanyMap> getAllByParentId(String parentId){
        CompanyMap companyMap = new CompanyMap();
        companyMap.setParentId(parentId);
        return companyMapDao.getAllByParentId(companyMap);
    }

    /**
     * 以父ID为条件获取数据
     * @param parentId
     * @return
     */
    public List<CompanyMap> getAllToAddByParentId(String parentId){
        CompanyMap companyMap = new CompanyMap();
        companyMap.setParentId(parentId);
        return companyMapDao.getAllByParentId(companyMap);
    }

    /**
     * 添加新数据
     * @param id
     * @param type
     * @param parentId
     * @param name
     * @param hrCompany
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int post(CompanyMap companyMap) throws Exception{
        if (null!=this.getOne(companyMap.getId())){
            throw new CustomException("ID："+companyMap.getId()+"已添加，不能重复添加");
        }
        return companyMapDao.post(companyMap);
    }

}
