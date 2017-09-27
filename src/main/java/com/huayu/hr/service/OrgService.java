package com.huayu.hr.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.hr.dao.*;
import com.huayu.hr.domain.*;
import com.huayu.user.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by DengPeng on 2016/12/5.
 */
@Service
public class OrgService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private HrJobDao jobDao;

    @Autowired
    private HrEmployeeDao employeeDao;

    @Autowired
    private HrEmployeeJobDao employeeJobDao;

    @Autowired
    private CommSequenceDao commSequenceDao;

    @Autowired
    private HrStatisticsEmpChgDataDao hrStatisticsEmpChgDataDao;

    @Autowired
    private HrJobPlaitDao hrJobPlaitDao;


    /**
     * 根据父级Id 获取所有公司
     * @param parentId
     * @param queryAllCondition
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#parentId+'_'+#queryAllCondition")
    public List<HrCompany> getAllCompanies(Integer parentId, String queryAllCondition) {
        List<HrCompany> companyList = companyDao.getCompanyList(queryAllCondition);
        Map<Integer, HrCompany> dataMap = new LinkedHashMap<>();
        companyList.forEach(e -> {
            dataMap.put(e.getId(), e);
        });
        return buildHrCompanyTree(parentId,dataMap);
    }

    private List<HrCompany> buildHrCompanyTree(Integer parentId, Map<Integer,HrCompany> dataMap) {
        List<HrCompany> list = new ArrayList<>();
        for(HrCompany data : dataMap.values()) {
            if (!data.isContain()){
                if (parentId.equals(data.getParentId())){
                    data.setContain(true);
                    list.add(data);
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            for (HrCompany data : list){
                if (data.getIsParent()==1){
                    List<HrCompany> child = buildHrCompanyTree(data.getId(),dataMap);
                    data.setChildList(child);
                }
            }
        }
        return list;
    }

    /**
     * 获取所有基础公司
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName")
    public List<HrCompany> getAllBaseCompanies() {
        HrCompany company = new HrCompany();
        return companyDao.getAllBaseCompanies(company);
    }


    @Cacheable(value = "org-cache",key = "methodName")
    public List<HrCompany> getAllCompanies() {
        return companyDao.getAllCompanies(new HrCompany());
    }

    /**
     * 根据父级Id 获取所有公司
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#parentId")
    public List<HrCompany> getAllCompanies(Integer parentId) {
        HrCompany company = new HrCompany();
        company.setParentId(parentId);
        List<HrCompany> list = companyDao.getCompanyListByParentId(company);
        if (!CollectionUtils.isEmpty(list)){
            for (HrCompany c : list){
                if (c.getIsParent()==1){
                    List<HrCompany> child = getAllCompanies(c.getId());
                    c.setChildList(child);
                }
            }
        }
        return list;
    }


    /**
     * 添加公司
     * @return
     */
    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult addCompany(HrCompany company) {
        BaseResult result = BaseResult.initBaseResult();
        company.setStatus(0);
        company.setCreatetime(new Date());
        if (companyDao.addCompany(company)==1){
            if (company.getParentId()!=0){
                HrCompany parent = new HrCompany();
                parent.setId(company.getParentId());
                parent.setUpdatetime(new Date());
                parent.setIsParent(1);
                if (companyDao.updateCompany(parent)==1){
                    result.setRdata(company);
                    result.setSuccess();
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }else {
                result.setRdata(company);
                result.setSuccess();
            }
        }
        return result;
    }


    /**
     * 修改公司
     * @return
     */
    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult updateCompany(HrCompany company) {
        BaseResult result = BaseResult.initBaseResult();
        company.setUpdatetime(new Date());
        if (companyDao.updateCompany(company)==1){
            result.setSuccess();
        }
        return result;
    }

    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult delCompany(HrCompany company) {
        BaseResult result = BaseResult.initBaseResult();
        HrDepartment department = new HrDepartment();
        department.setParentId(0);
        department.setCompanyId(company.getId());
        if (departmentDao.countDepartments(department)>0){
            result.setRmsg("该公司下面存在部门，请先撤出");
            return result;
        }
        company.setDeletetime(new Date());
        company.setStatus(1);
        if (companyDao.delCompany(company)==1){
            if (null!=company.getParentId()){
                HrCompany parent = new HrCompany();
                parent.setId(company.getParentId());
                parent.setUpdatetime(new Date());
                parent.setIsParent(0);
                if (companyDao.updateCompany(parent)==1){
                    result.setSuccess();
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }else {
                result.setSuccess();
            }
        }
        return result;
    }


    /**
     * 根据 父级Id 获取 公司列表
     * @param parentId
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#parentId")
    public List<HrCompany> getCompaniesByParentId(Integer parentId, String queryCondition) {
        HrCompany company = new HrCompany();
        company.setParentId(parentId);
        return companyDao.getCompanyListByParentId(company, queryCondition);
    }


    /**
     * 获取所有部门
     * @param companyId
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#companyId+'_'+#parentId")
    public List<HrDepartment> getAllTreeDepartments(Integer companyId, Integer parentId, String queryAllCondition) {
        HrDepartment department = new HrDepartment();
        department.setCompanyId(companyId);
        department.setParentId(parentId);
        List<HrDepartment> list = departmentDao.getDepartments(department,queryAllCondition);
        if (!CollectionUtils.isEmpty(list)){
            for (HrDepartment d : list){
                if (d.getIsParent()==1){
                    List<HrDepartment> child = getAllTreeDepartments(companyId,d.getId(),queryAllCondition);
                    d.setChildList(child);
                }
            }
        }
        return list;
    }

    /**
     * 获取所有基础部门
     * @param companyId
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#companyId")
    public List<HrDepartment> getAllBaseDepartments(Integer companyId) {
        HrDepartment department = new HrDepartment();
        department.setCompanyId(companyId);
        department.setIsParent(0);
        return departmentDao.getAllBaseDepartments(department);
    }

    /**
     * 获取所有的部门
     * @param companyId
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#companyId")
    public List<HrDepartment> getAllDepartments(Integer companyId) {
        HrDepartment department = new HrDepartment();
        department.setCompanyId(companyId);
        return departmentDao.getDepartmentsIgnoreAuth(department);
    }


    /**
     * 获取 公司下面 部门
     * @param companyId
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#companyId+'_'+#parentId")
    public BaseResult getDepartmentsByCid(Integer companyId, Integer parentId) {
        BaseResult result = BaseResult.initBaseResult();
        if (null==companyId || null==parentId){
            result.setArgumentsMiss();
            return result;
        }
        HrDepartment department = new HrDepartment();
        department.setCompanyId(companyId);
        department.setParentId(parentId);
        List<HrDepartment> list =  departmentDao.getDepartmentsIgnoreAuth(department);
        if (!CollectionUtils.isEmpty(list)){
            result.setRdata(list);
            result.setSuccess();
        }
        return result;
    }

    /**
     * 根据部门Id 获取职位信息
     * @param departmentId
     * @return
     */
    @Cacheable(value = "org-cache",key = "methodName+'_'+#departmentId")
    public List<HrJob> getAllJobByDepartmentId(Integer departmentId){
        if (null==departmentId){
           throw new BusinessException("参数错误!");
        }
        HrJob job = new HrJob();
        job.setDepartmentId(departmentId);
        return jobDao.getAllJobByDepartmentId(job);
    }

    public List<HrJob> getAllJobByDepartWithPlait(Integer departmentId, Integer year, Integer month){
        if (null==departmentId){
            throw new BusinessException("参数错误!");
        }
        HrJob job = new HrJob();
        job.setDepartmentId(departmentId);
        job.setMonth(month);
        job.setYear(year);
        return jobDao.getAllJobByDepartWithPlait(job);
    }

    public HrCompany getCompanyOne(Integer companyId) {
        HrCompany company = new HrCompany();
        company.setId(companyId);
        company.setIdName("id");
        company.setStatus(0);
        return companyDao.getOne(company);
    }

    public HrDepartment getDepartmentOne(Integer departmentId) {
        HrDepartment department = new HrDepartment();
        department.setId(departmentId);
        department.setIdName("id");
        return departmentDao.getOne(department);
    }

    /**
     * 修改部门
     * @param department
     * @return
     */
    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult updateDepartment(HrDepartment department) {
        BaseResult result = BaseResult.initBaseResult();
        if(null!=department.getStatus()&&department.getStatus()==1){
            HrEmployee employee = new HrEmployee();
            employee.setCompany(department.getCompanyId());
            employee.setDepartment(department.getId());
            if (employeeDao.countEmpByDept(employee)>0){
                result.setRmsg("该部门存在在职人员，不能直接删除");
               return result;
            }
        }
        department.setUpdatetime(new Date());
        if (departmentDao.updateDepartment(department)==1){
            if (null!=department.getParentId()){
                HrDepartment parent = new HrDepartment();
                parent.setId(department.getParentId());
                parent.setUpdatetime(new Date());
                parent.setCompanyId(department.getCompanyId());
                parent.setIsParent(0);
                if (departmentDao.updateDepartment(parent)==1){
                    result.setSuccess();
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }else {
                result.setSuccess();
            }
        }
        return result;
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public void addDepartment(HrDepartment department) {
        Date now = new Date();
        department.setStatus(0);
        department.setCreatetime(now);
        if (departmentDao.addDepartment(department)!=1){
            throw new BusinessException("添加部门失败");
        }
        if (department.getParentId()!=0){
            HrDepartment parent = new HrDepartment();
            parent.setId(department.getParentId());
            parent.setUpdatetime(now);
            parent.setCompanyId(department.getCompanyId());
            parent.setIsParent(1);
            if (departmentDao.updateDepartment(parent)!=1){
                throw new BusinessException("更新上级部门失败");
            }
        }
        String[] ym = DateTimeUtil.getLastMonth(now);
        HrStatisticsEmpChgData data = new HrStatisticsEmpChgData(ym[0],ym[1],department.getCompanyId(),department.getId());
        data.setOnDutyM(0);
        data.setOnDutyW(0);
        data.setNote("新建部门初始化数据");
        data.setCreatetime(now);
        data.setStatus(0);
        if (hrStatisticsEmpChgDataDao.postData(data)!=1){
            throw new BusinessException("添加部门异动初始化数据失败");
        }

    }


    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public void addJob(HrJob job, HrJobPlait hrJobPlait) {
        Date date = new Date();
        User user = SpringSecurityUtil.getUser();
        job.setStatus(0);
        job.setCreatetime(date);
        if (jobDao.addOne(job)!=1){
           throw new BusinessException("添加岗位失败");
        }
        hrJobPlait.setJobId(job.getId());
        hrJobPlait.setCreatetime(date);
        hrJobPlait.setCreateUser(user.getUserId().intValue());
        addJobPlait(hrJobPlait);
    }

    public HrJob getOneJob(HrJob job) {

        return jobDao.getOne(job);
    }


    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult updateJob(HrJob job) {
        BaseResult result = BaseResult.initBaseResult();
        job.setUpdatetime(new Date());
        if (jobDao.updateOne(job)==1){
            result.setSuccess();
        }

        return result;
    }

    @Transactional
    public void editJobPlait(HrJobPlait hrJobPlait) {
        if (hrJobPlaitDao.batchUpdateJobPlait(hrJobPlait)==0){
            throw new BusinessException("修改失败，没有找到编制数据");
        }
    }
    /**
     * 新增编制
     * @param hrJobPlait
     */
    @Transactional
    public void addJobPlait(HrJobPlait hrJobPlait) {
        int size = (hrJobPlait.getEndMonth()-hrJobPlait.getStartMonth()+1)*hrJobPlait.getCount();
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[size];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_job_plait", size));
        for (int i= hrJobPlait.getStartMonth();i<=hrJobPlait.getEndMonth();i++){
            for (int k = 0; k<hrJobPlait.getCount();k++){
                int index =(i-hrJobPlait.getStartMonth())*hrJobPlait.getCount()+k;
                HrJobPlait entity = new HrJobPlait();
                BeanUtils.copyProperties(hrJobPlait,entity);
                entity.setId(ids[0].intValue()+index);
                entity.setMonth(i);
                sqlParameterSources[index]=new BeanPropertySqlParameterSource(entity);
            }
        }
        try {
            hrJobPlaitDao.batchAdd(sqlParameterSources);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 删除岗位
     * @param job
     * @return
     */
    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult delJob(HrJob job) {
        BaseResult result = BaseResult.initBaseResult();
        HrEmployeeJob employeeJob = new HrEmployeeJob();
        employeeJob.setJob(job.getId());
        if (employeeJobDao.countByEmpOnGuard(employeeJob)>0){
            result.setRmsg("该岗位已经绑定在职人员");
            return result;
        }
        job.setDeletetime(new Date());
        if (jobDao.delOne(job)==1){
            result.setSuccess();
        }
        HrJobPlait hrJobPlait = new HrJobPlait();
        User user = SpringSecurityUtil.getUser();
        hrJobPlait.setDeleteUser(user.getUserId().intValue());
        hrJobPlait.setDeletetime(new Date());
        hrJobPlaitDao.batchDelete(hrJobPlait);
        return result;
    }

    @Transactional
    @CacheEvict(value = "org-cache",allEntries=true)
    public BaseResult copyJob(Integer srcDept, Integer targComp, Integer targDept) {
        BaseResult result = BaseResult.initBaseResult();
        Assert.notNull(srcDept,"原部门不能为空");
        Assert.notNull(targComp,"目标公司Id不能为空");
        Assert.notNull(targDept,"目标部门Id不能为空");
        HrJob j = new HrJob();
        j.setDepartmentId(srcDept);
        List<HrJob> jobs = jobDao.getAllJobByDepartmentId(j);
        if (CollectionUtils.isEmpty(jobs)){
            result.setRmsg("没有找到岗位数据");
            return result;
        }else{
            SqlParameterSource[] sqlParameterSources = new SqlParameterSource[jobs.size()];
            Long[] ids = commSequenceDao.getKey(new CommSequence("hr_job", jobs.size()));
            for (int i=0; i<jobs.size();i++){
                HrJob job = jobs.get(i);
                job.setId(ids[0].intValue()+i+1);
                job.setCompanyId(targComp);
                job.setDepartmentId(targDept);
                job.setCreatetime(new Date());
                sqlParameterSources[i] = new BeanPropertySqlParameterSource(job);
            }
            String sql = "INSERT INTO hr_job (id, name, departmentId, companyId, status, createtime, updatetime, buildtime, jobCode, deletetime) VALUES (:id, :name, :departmentId, :companyId, :status, :createtime, :updatetime, :buildtime, :jobCode, :deletetime)";
            int[] flags = jobDao.batchUpdate(sql, sqlParameterSources);
            for (int i=0;i<flags.length;i++){
                if (flags[i]==0){
                    throw new BusinessException("复制岗位失败");
                }
            }
        }
        result.setSuccess();
        return result;
    }

    @Cacheable(value = "org-cache",key = "methodName+'_'+#departmentId")
    public List<HrCompany> getAllPlat() {
        return companyDao.getAllPlat();
    }


    public List<HrJobPlait> getJobPlait(Integer job, Integer year, Integer month) {
        HrJobPlait jobPlait = new HrJobPlait();
        jobPlait.setJobId(job);
        jobPlait.setYear(year);
        jobPlait.setMonth(month);
        return hrJobPlaitDao.getPlaitsSimple(jobPlait);
    }

    /**
     * 占用编制
     * @param jobPlait
     */
    @Transactional
    public void occupyJobPlait(HrJobPlait jobPlait) {
        if (hrJobPlaitDao.occupyJobPlait(jobPlait)!=1){
            throw new BusinessException("编制数不足");
        }
    }

    /**
     * 取消占用
     * @param jobPlait
     */
    @Transactional
    public void cancelOccupyJobPlait(HrJobPlait jobPlait) {
        if (hrJobPlaitDao.cancelOccupyJobPlait(jobPlait)!=1){
            throw new BusinessException("取消占用编制失败");
        }
    }



    @Transactional
    public Page<HrJobPlait> getJobPlaitListData(HrJobPlait jobPlait, Pageable pageable) {

        return hrJobPlaitDao.getPlaits(jobPlait,pageable);
    }

    /**
     * 删除编制
     * @param hrJobPlait
     */
    @Transactional
    public void delJobPlait(HrJobPlait hrJobPlait) {
        hrJobPlait = hrJobPlaitDao.getOneJobPlait(hrJobPlait);
        if (null == hrJobPlait){
            throw new BusinessException("没有找到编制");
        }
        if (hrJobPlait.getEmpId()!=null){
            throw new BusinessException("改编制已经有人员，不能直接删除");
        }
        User user = SpringSecurityUtil.getUser();
        hrJobPlait.setDeleteUser(user.getUserId().intValue());
        hrJobPlait.setDeletetime(new Date());
        if (hrJobPlaitDao.deleteOne(hrJobPlait)!=1){
            throw new BusinessException("删除编制失败");
        }
    }

    public void updatePlaitStore() {
        hrJobPlaitDao.updatePlaitStore();
    }
}
