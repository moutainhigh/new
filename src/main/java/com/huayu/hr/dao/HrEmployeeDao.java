package com.huayu.hr.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.Authority;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2016/12/7.
 */
@Repository
public class HrEmployeeDao extends BasePageDao<HrEmployee,Integer>{

    public int addSalaryStandard(HrEmployee employee) {
        Long id = super.getKey("payroll_salary_standard", employee);
        String sql = "INSERT INTO payroll_salary_standard (id, empId, empNo, empName, idCard, management, department, manageModule, emoJob, bankNo, contractName, assistCheck, " +
                "manageGroup, assortment, yearAllStandard, workStatus, guaranteeStandard, communStandard, overtimeStandard, yearAwardStandard, auotaAwardStandard, nodeStandard, " +
                "yearEndStandard, yearTestStandard, commissionStandard, allPostStandard, paymentBase, pensionIns, unemploymentIns, medicalIns, providentFund, remark, delStatus, stopStatus, " +
                "saveStatus, examineGroup) VALUES (:id, :empId, '', '', '', '', '', '', '', '', '', '', '', '', '0', '', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '', '0', '0', '0', '')";
        Map<String,Object> data = new HashMap<>();
        data.put("id",id);
        data.put("empId",employee.getId());
        return super.jdbcTemplate.update(sql,data);
    }

    public int addEmployeeBaseInfo(HrEmployee employee) {
        Long long_Id = super.getKey("hr_employee", employee);
        employee.setId(long_Id.intValue());
        employee.setIsDelete(0);
        return super.post(employee);
    }

    public HrEmployee getEmployeeInfo(Integer id) {
        HrEmployee employee = new HrEmployee();
        User user = SpringSecurityUtil.getUser();
        employee.setId(id);
        Authority authority = user.getDataAuthorityMap();
        String sql  = "select e.*,ej.empNo,ei.education from hr_employee e " +
                "INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id  " +
                "INNER JOIN hr_company c ON c.id = ej.company " +
                "LEFT JOIN hr_education_info ei ON ei.empId = e.id AND ei.topEducation=1 " +
                "where e.id = :id and e.isDelete=0 "+
                "and c.code REGEXP "
                + Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp();
        return super.getOne(sql,employee);
    }

    public Page<HrEmployee> getEmpListData(HrEmployee employee, PageArgs pageArgs) {
        StringBuilder stringBuilder  = new StringBuilder("select e.id,e.empName,e.createtime,c.name companyStr,d.NAME departmentStr,ej.dutyLevel dutyLevelId,ej.jobSequence,ej.empNo,j.name job");
        stringBuilder.append(" FROM hr_employee e");
        stringBuilder.append(" INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id ");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = ej.company");
        stringBuilder.append(" LEFT JOIN hr_department d ON d.id = ej.department");
        stringBuilder.append(" LEFT JOIN hr_job j ON j.id = ej.job");
        if (null!=employee.getLevelCompare()&&null!=employee.getLevelData()){
            stringBuilder.append(" INNER JOIN hr_dict_data dd on dd.dictDataValue = ej.dutyLevel and dd.dictId=11 ");
            if (employee.getLevelCompare()==0){
                stringBuilder.append(" and dd.rank =:levelData ");
            }else if (employee.getLevelCompare()==1){
                stringBuilder.append(" and dd.rank <:levelData ");
            }else if (employee.getLevelCompare()==10){
                stringBuilder.append(" and dd.rank <=:levelData ");
            }else if (employee.getLevelCompare()==2){
                stringBuilder.append(" and dd.rank >:levelData ");
            }else if (employee.getLevelCompare()==20){
                stringBuilder.append(" and dd.rank >=:levelData ");
            }else {
                throw new BusinessException("参数有误");
            }
        }
        stringBuilder.append(" where ");
        stringBuilder.append(" e.isDelete = 0");
        if (null != employee.getOnGuard()){
            stringBuilder.append(" and ej.onGuard=:onGuard");
        }else{
            stringBuilder.append(" and ej.onGuard=1");
        }
        if (StringUtils.isNotBlank(employee.getEmpNo())){
            stringBuilder.append(" and ej.empNo=:empNo");
        }
        if (StringUtils.isNotBlank(employee.getEmpName())){
            stringBuilder.append("  and position(:empName in e.empName)");
        }
        if (null!=employee.getJobSequence()){
            if (employee.getJobSequence()==0){
                stringBuilder.append(" and ej.jobSequence not in (5,6,7,8,9,10)");
            }else{
                stringBuilder.append(" and ej.jobSequence=:jobSequence");
            }
        }
        if (null != employee.getDepartment()){
            stringBuilder.append(" and ej.department = :department");
        }
        User user = SpringSecurityUtil.getUser();
        if (null==employee.getShowChildren()||0==employee.getShowChildren()){
            stringBuilder.append(" and c.code REGEXP ");
            stringBuilder.append(user.getDataAuthorityRegexp());
        }else{
            stringBuilder.append(" and c.code REGEXP ");
            Authority authority = user.getDataAuthorityMap();
            stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        }
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.ASC,"d.rank,ej.dutyLevel");
        return super.get(stringBuilder.toString(),employee,pageable);
    }


    public List<HrEmployee> getEmpListData(HrEmployee employee) {
        StringBuilder stringBuilder  = new StringBuilder("select e.*,c.area,c.plate,c.name companyStr,d.NAME departmentStr,ej.dutyLevel dutyLevelId,ej.empNo,j.name job,ej.turnFormalDate");
        stringBuilder.append(",hc.contractCompany,hc.contractType,hc.periodCount,hc.contractStartTime,hc.contractEndTime,hc.isSecret,hc.contractNo,hc.note contractNote");
        stringBuilder.append(",hei.graduationDate,hei.school,hei.profession,hei.education,hei.note educationNote,ej.isFormal,ej.jobSequence");
        stringBuilder.append(" FROM hr_employee e");
        stringBuilder.append(" INNER JOIN hr_employee_job ej ON ej.id = e.lastEmpJobId");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = ej.company");
        stringBuilder.append(" LEFT JOIN hr_department d ON d.id = ej.department");
        stringBuilder.append(" LEFT JOIN hr_contract hc ON hc.empJobId = ej.id and hc.status=1 and operType in (1,2,3)");
        stringBuilder.append(" LEFT JOIN hr_education_info hei ON hei.empId=e.id and hei.status = 0 and hei.topEducation=1");
        stringBuilder.append(" LEFT JOIN hr_job j ON j.id = ej.job");
        stringBuilder.append(" where  ej.onGuard=1");
        if (null != employee.getDepartment()){
            stringBuilder.append(" and ej.department = :department");
        }
        if (null != employee.getCompany()){
            stringBuilder.append(" and ej.company = :company");
        }
        if (null!=employee.getIds()){
            stringBuilder.append(" and e.id in (").append( StringUtils.join(employee.getIds().toArray(), ",")).append(")");
        }
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(" and c.code REGEXP ").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        stringBuilder.append(" and e.isDelete = 0 ");
        stringBuilder.append(" GROUP BY e.id");
        return super.get(stringBuilder.toString(),employee);
    }

    public int updateEmployeeBaseInfo(HrEmployee employee) {
        StringBuilder sql  = new StringBuilder( "update hr_employee set " );
                            sql.append("empName=:empName,idCard=:idCard,ssNum=:ssNum,sex=:sex,birthdate=:birthdate,nationId=:nationId,maritalStatus=:maritalStatus,healthStatus=:healthStatus,joinWorkDate=:joinWorkDate,");
                            sql.append("joinCompDate=:joinCompDate,zipcode=:zipcode,liveAddress=:liveAddress,registrationAddress=:registrationAddress,email=:email,compPhone=:compPhone,mobile=:mobile," );
                            sql.append("homePhone=:homePhone,usedName=:usedName,origin=:origin,householdAddress=:householdAddress,householdType=:householdType,province=:province," );
                            sql.append("city=:city,technicalLevel=:technicalLevel,politicalStatus=:politicalStatus,personalIdentity=:personalIdentity,socialInsuranceCompany=:socialInsuranceCompany,socialInsuranceAddress=:socialInsuranceAddress," );
                            sql.append("houseProvidentFundCompany=:houseProvidentFundCompany,houseProvidentFundAddress=:houseProvidentFundAddress,auxiliaryCheck=:auxiliaryCheck,bankName=:bankName,bankCardNo=:bankCardNo,");
                            sql.append("assessmentGroup=:assessmentGroup,performanceSys=:performanceSys,emergencyName=:emergencyName,emergencyPhone=:emergencyPhone,twoPlaces=:twoPlaces,");
                            sql.append("wagePayCompany=:wagePayCompany,managerGroup=:managerGroup,photoUrl=:photoUrl,updatetime=:updatetime");
                            sql.append(" where id=:id AND isDelete=0");
        return super.put(sql.toString(),employee);
    }

    public int updateEmployeeWorkInfo(HrEmployee employee) {
        StringBuilder sql  = new StringBuilder( "update hr_employee set ");
        boolean flag = false;
        if (null!=employee.getCompany()&&null!=employee.getDepartment()){
            sql.append("company=:company,department=:department");
            flag = true;
        }
        if (null!=employee.getLastEmpJobId()){
            if (flag){
                sql.append(",");
            }
            sql.append("lastEmpJobId=:lastEmpJobId");
        }
        sql.append(" where id=:id");
        sql.append("  AND isDelete=0");
        return super.put(sql.toString(),employee);
    }


    /**
     * 学历统计
     * @param level
     * @return
     */
    public List<HrEmployee> countEdu(Integer level, Integer dutyLevel, Integer jobSequence, Integer company, Integer department) {
        StringBuilder sql  = new StringBuilder("SELECT j.empId,e.empName,i.education ");
        sql.append(" FROM hr_employee_job j ");
        sql.append(" INNER JOIN hr_employee e ON e.id = j.empId");
        sql.append(" INNER JOIN hr_education_info i ON e.id = i.empId AND i.topEducation =1 AND i.education");
        if (level==1){//研究生
            sql.append(" in (10,13)");
        }else if (level==2){//本科
            sql.append(" = 22");
        }else if (level==3){//专科
            sql.append(" = 50");
        }else if (level==4){//专科以下
            sql.append("  in (4,84,89,94,101)");
        }else {
            throw new BusinessException("参数错误");
        }
        sql.append(" WHERE  j.onGuard = 1 and i.status=0 ");
        if (null!=company && null!=dutyLevel){
            sql.append(" and j.company = "+company).append(" and j.dutyLevel =").append(dutyLevel);
        }else  if (null!=company&&null!=department&& null != jobSequence) {
            sql.append(" and j.company = "+company).append(" and j.department = "+department).append(" and j.jobSequence =").append(jobSequence);
        }else {
            throw new BusinessException("参数错误");
        }
        sql.append("  AND j.isDelete=0");
        return super.get(sql.toString(),new HrEmployee());
    }

    /**
     * 统计入职
     * 新人入职  preparetype 为空
     * @param companyId
     * @param department
     *@param formatMonth
     * @return
     */
    public List<HrEmployee> countInDutyMW(Integer companyId, Integer department, String formatMonth) {
        StringBuilder sql  = new StringBuilder("SELECT DISTINCT i.empId id,i.id businessId,i.dutyLevel dutyLevelId,i.jobSequence");
        sql.append(" FROM hr_employee_induty i");
        sql.append(" INNER JOIN hr_company c ON i.company = c.id");
        sql.append(" WHERE ");
        sql.append("  i.company = ").append(companyId).append("  AND i.jobSequence REGEXP ").append("'5|6'");
        sql.append(" AND i.posttype = 1  AND  DATE_FORMAT(i.begindate,'%Y%m') = ").append(" '"+formatMonth+"' ").append(" AND i.department = ").append(department);
        sql.append("  AND i.isDelete=0");
        return super.get(sql.toString(),new HrEmployee());
    }


    /**
     * 当月离职统计
     * @param companyId
     * @param department
     * @param formatMonth
     * @return
     */
    public List<HrEmployee> countOutDutyMW(Integer companyId, Integer department, String formatMonth) {
        StringBuilder sql  = new StringBuilder("SELECT DISTINCT o.empId id,o.id businessId,j.dutyLevel dutyLevelId,j.jobSequence");
        sql.append(" FROM hr_employee_outduty o");
        sql.append(" INNER JOIN hr_employee_job j ON j.id=o.empJobId");
        sql.append(" WHERE  o.type <8 AND  DATE_FORMAT(o.leavedate,'%Y%m') = ").append(" '"+formatMonth+"' ");
        sql.append(" AND o.companyBefore = ").append(companyId).append(" AND o.deptBefore = ").append(department).append("  AND j.jobSequence REGEXP ").append("'5|6'");
        sql.append("  AND o.isDelete=0");
        return super.get(sql.toString(),new HrEmployee());
    }


    /**
     * 调动（调入）统计
     * @param companyId
     * @param department
     * @param formatMonth
     * @return
     */
    public List<HrEmployee> countCompChgInMW(Integer companyId, Integer department,String formatMonth) {
        StringBuilder sql  = new StringBuilder("SELECT chg.chgType,chg.empId id,chg.id businessId,ej.dutyLevel dutyLevelId,ej.jobSequence");
        sql.append(" FROM hr_employee_psn_chg chg INNER JOIN hr_employee_job ej ON ej.id = chg.empJobId");
        sql.append(" WHERE chg.chgFlag = 1");
        sql.append(" AND  DATE_FORMAT(chg.chgDate,'%Y%m') = ").append(" '"+formatMonth+"' ");
        sql.append(" AND ej.company= ").append(companyId).append(" AND ej.department = ").append(department).append("  AND ej.jobSequence REGEXP '5|6'");
        sql.append(" AND chg.isDelete=0");
        return super.get(sql.toString(),new HrEmployee());
    }


    /**
     * 调动（调出）统计
     * @param companyId
     * @param department
     * @param formatMonth
     * @return
     */
    public List<HrEmployee> countCompChgOutMW(Integer companyId, Integer department,String formatMonth) {
        StringBuilder sql  = new StringBuilder("SELECT chg.chgType,chg.empId id,chg.id businessId,ej.dutyLevel dutyLevelId,ej.jobSequence");
        sql.append(" FROM hr_employee_psn_chg chg INNER JOIN hr_employee_job ej ON ej.id = chg.empJobId");
        sql.append(" WHERE chg.chgFlag = 2");
        sql.append(" AND  DATE_FORMAT(chg.chgDate,'%Y%m') = ").append(" '"+formatMonth+"' ");
        sql.append(" AND ej.company= ").append(companyId).append(" AND ej.department = ").append(department).append("  AND ej.jobSequence REGEXP '5|6'");
        sql.append(" AND chg.isDelete=0");
        return super.get(sql.toString(),new HrEmployee());
    }


    public long countEmpByDept(HrEmployee employee){
        StringBuilder sql  = new StringBuilder( "select count(e.id)");
        sql.append(" from hr_employee e");
        sql.append(" inner join hr_employee_job j ON j.id = e.lastEmpJobId");
        sql.append(" where j.onGuard = 1 and j.company=:company and j.department=:department");
        sql.append("  AND e.isDelete=0");
        return super.getLong(sql.toString(),employee);
    }

    public List<HrEmployee> getAllEmpListData(HrEmployee employee) {
        StringBuilder sql  = new StringBuilder( "select e.id,e.empName name,e.badgenumber,ej.jobSequence,ej.job,CONCAT(e.empName,'-',j.name) empAndJobName");
        sql.append(" from hr_employee e");
        sql.append(" inner join hr_employee_job ej ON ej.id = e.lastEmpJobId");
        sql.append(" inner join hr_job j ON j.id = ej.job");
        sql.append(" where ej.onGuard = :onGuard and ej.company=:company and ej.department=:department");
        sql.append("  AND e.isDelete=0 order by ej.dutyLevel ASC");
        return super.get(sql.toString(),employee);
    }

    public Long countByIdCardNo(HrEmployee employee) {
        String sql = "select count(e.id) from hr_employee e where e.idCard = :idCard and e.isDelete = 0";
        return super.getLong(sql,employee);
    }

    public HrEmployee getEmployeeByEmpNo(HrEmployee employee) {
        String sql = "SELECT  e.id,e.empName,e.company ,c.name companyStr FROM hr_employee e INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id INNER JOIN hr_company c ON c.id = ej.company where ej.empNo=:empNo  and e.isDelete = 0 and ej.onGuard=1";
        return super.getOne(sql,employee);
    }

    public HrEmployee getEmpBaseInfoAndCompById(HrEmployee employee){
        String sql = "SELECT  e.id,e.empName,e.company ,c.name companyStr,ej.empNo empNo,ej.id empJobId FROM hr_employee e INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id INNER JOIN hr_company c ON c.id = ej.company where e.id=:id  and e.isDelete = 0 and ej.onGuard=1";
        return super.getOne(sql,employee);
    }

    public List<HrEmployee> getBirthRemindData(HrEmployee employee){
        String sql = "SELECT e.id,e.birthdate,e.company from hr_employee e INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id  where DATE_FORMAT(e.birthdate,'%m%d') >= :startDate and  DATE_FORMAT(e.birthdate,'%m%d') <= :endDate  and e.isDelete=0 AND ej.onGuard = 1";
        return super.get(sql,employee);
    }

    public List<HrEmployee> getOutDutyEmpList(HrEmployee employee) {
        String sql ="SELECT e.id,e.empName FROM hr_employee e INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id   where e.company =:company  AND e.department =:department and ej.onGuard=0 and e.isDelete=0";
        return super.get(sql,employee);
    }
    public List<HrEmployee> getNotFormalEmpData(HrEmployee employee) {
        String sql ="SELECT e.id,e.empName,e.lastEmpJobId FROM hr_employee e INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id   where e.company =:company  AND e.department =:department and ej.onGuard=1 and ej.isFormal=0 and e.isDelete=0";
        return super.get(sql,employee);
    }

    /**
     * 入职统计
     * @param entity
     * @param searchAll
     * @return
     */
    public List<HrEmployee> countCurrentInDutyDaily(HrEmployee entity, boolean searchAll) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql  = new StringBuilder("SELECT  e.id,c.area,c.name companyStr,d.NAME departmentStr,e.managerGroup,ej.empNo,e.birthdate,i.begindate joinCompDate,");
        sql.append("ej.isFormal,e.empName,e.sex,j.name job,i.dutyLevel dutyLevelId,e.idCard,i.jobSequence,i.createtime");
        sql.append(" FROM hr_employee_induty i");
        sql.append(" INNER JOIN hr_employee e on e.id = i.empId");
        sql.append(" INNER JOIN hr_employee_job ej on ej.id = i.empJobId");
        sql.append(" INNER JOIN hr_company c ON c.id = ej.company");
        sql.append(" INNER JOIN hr_department d ON d.id = ej.department");
        sql.append(" INNER JOIN hr_job j ON j.id = ej.job");
        sql.append(" WHERE c.code REGEXP ");
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        if (searchAll){
            sql.append(dataAuthorityMap.authoritiesRegexp());
        }else{
            sql.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        }
        sql.append(" AND i.posttype = 1 AND  i.begindate >= :startDate").append(" AND i.begindate <= :endDate");
        if (null!=entity.getDepartment()){
            sql.append(" AND i.department = :department");
        }
        if (null!=entity.getJobSequence()){
            sql.append(" AND i.jobSequence=:jobSequence");
        }
        if (null != entity.getCompany()){
            sql.append(" AND c.id=:company");
        }
        sql.append("  AND i.isDelete=0");
        sql.append(" ORDER BY c.code,d.rank asc");
        return super.get(sql.toString(),entity);
    }

    /**
     * 查询统计历史数据
     * @param entity
     * @param searchAll
     * @return
     */
    public List<HrEmployee> countInDutyDaily(HrEmployee entity, boolean searchAll) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql  = new StringBuilder("SELECT  e.id,c.area,c.name companyStr,d.NAME departmentStr,e.managerGroup,ej.empNo,");
        sql.append("e.birthdate,ej.startTime joinCompDate,ej.isFormal,e.empName,e.sex,");
        sql.append("j.name job,ej.dutyLevel dutyLevelId,e.idCard,ej.jobSequence,ej.createtime,ei.school,ei.education");
        sql.append(" FROM hr_statistics_emp_chg_data_detail dd");
        sql.append(" INNER JOIN hr_employee_induty i on i.id = dd.businessId");
        sql.append(" INNER JOIN hr_employee e on e.id = dd.empId ");
        sql.append(" INNER JOIN hr_employee_job ej on ej.id = i.empJobId ");
        sql.append(" INNER JOIN hr_company c ON c.id = ej.company");
        sql.append(" INNER JOIN hr_department d ON d.id = ej.department");
        sql.append(" INNER JOIN hr_job j ON j.id = ej.job ");
        sql.append(" LEFT JOIN hr_education_info ei ON ei.empId = e.id and ei.topEducation = 1");
        sql.append(" WHERE c.code REGEXP ");
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        if (searchAll){
            sql.append(dataAuthorityMap.authoritiesRegexp());
        }else{
            sql.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        }
        sql.append(" AND  dd.year = DATE_FORMAT(:startDate,'%Y') ").append(" AND dd.month = DATE_FORMAT(:startDate,'%m')");
        if (null!=entity.getDepartment()){
            sql.append(" AND dd.department = :department");
        }
        if (null!=entity.getJobSequence()){
            sql.append(" AND ej.jobSequence=:jobSequence");
        }
        if (null != entity.getCompany()){
            sql.append(" AND dd.company=:company");
        }
        sql.append("  AND dd.status=0");
        sql.append("  AND dd.statisticsType=0");
        sql.append(" ORDER BY c.code,d.rank asc");
        return super.get(sql.toString(),entity);
    }

    /**
     * 离职统计
     * @param entity
     * @return
     */
    public List<HrEmployee> countCurrentOutDutyDaily(HrEmployee entity, boolean searchAll) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql  = new StringBuilder("SELECT e.id,c.area,c.name companyStr,d.NAME departmentStr,e.managerGroup,ej.empNo,e.birthdate,e.joinCompDate,ej.turnFormalDate,");
        sql.append("ej.isFormal,e.empName,e.sex,j.name job,ej.dutyLevel dutyLevelId,e.idCard,ej.jobSequence,o.leavedate,o.description,o.createtime");
        sql.append(" FROM hr_employee_outduty o");
        sql.append(" INNER JOIN hr_employee e on e.id = o.empId");
        sql.append(" INNER JOIN hr_employee_job ej ON ej.id=o.empJobId");
        sql.append(" INNER JOIN hr_company c ON c.id = ej.company");
        sql.append(" INNER JOIN hr_department d ON d.id = ej.department");
        sql.append(" INNER JOIN hr_job j ON j.id = ej.job");
        sql.append(" WHERE c.code REGEXP ");
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        if (searchAll){
            sql.append(dataAuthorityMap.authoritiesRegexp());
        }else{
            sql.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        }
        sql.append(" AND o.type <8 AND  o.leavedate >= :startDate").append(" AND o.leavedate <= :endDate");
        if (null!=entity.getDepartment()){
            sql.append(" AND o.deptBefore == :department");
        }
        if (null!=entity.getJobSequence()){
            sql.append(" AND ej.jobSequence=:jobSequence");
        }
        if (null != entity.getCompany()){
            sql.append(" AND c.id=:company");
        }
        sql.append("  AND o.isDelete=0");
        sql.append(" order by c.code,d.rank desc");
        return super.get(sql.toString(),entity);
    }

    /**
     * 查询统计历史数据
     * @param entity
     * @param searchAll
     * @return
     */
    public List<HrEmployee> countOutDutyDaily(HrEmployee entity,boolean searchAll) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql  = new StringBuilder("SELECT e.id,c.area,c.name companyStr,d.NAME departmentStr,e.managerGroup,ej.empNo,e.birthdate,e.joinCompDate,ej.turnFormalDate,");
        sql.append("ej.isFormal,e.empName,e.sex,j.name job,ej.dutyLevel dutyLevelId,e.idCard,ej.jobSequence,o.leavedate,o.description,o.createtime,o.reason,ei.school,ei.education");
        sql.append(" FROM hr_statistics_emp_chg_data_detail dd");
        sql.append(" INNER JOIN  hr_employee_outduty o on o.id=dd.businessId");
        sql.append(" INNER JOIN hr_employee e on e.id = o.empId");
        sql.append(" INNER JOIN hr_employee_job ej ON ej.id=o.empJobId");
        sql.append(" INNER JOIN hr_company c ON c.id = ej.company");
        sql.append(" INNER JOIN hr_department d ON d.id = ej.department");
        sql.append(" INNER JOIN hr_job j ON j.id = ej.job");
        sql.append(" LEFT JOIN hr_education_info ei ON ei.empId = e.id and ei.topEducation = 1");
        sql.append(" WHERE c.code REGEXP ");
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        if (searchAll){
            sql.append(dataAuthorityMap.authoritiesRegexp());
        }else{
            sql.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        }
        sql.append(" AND  dd.year = DATE_FORMAT(:startDate,'%Y') ").append(" AND dd.month = DATE_FORMAT(:startDate,'%m')");
        if (null!=entity.getDepartment()){
            sql.append(" AND o.deptBefore == :department");
        }
        if (null!=entity.getJobSequence()){
            sql.append(" AND ej.jobSequence=:jobSequence");
        }
        if (null != entity.getCompany()){
            sql.append(" AND c.id=:company");
        }
        sql.append("  AND o.isDelete=0");
        sql.append("  AND dd.status=0");
        sql.append("  AND dd.statisticsType=1");
        sql.append(" order by c.code,d.rank desc");
        return super.get(sql.toString(),entity);
    }

    /**
     * 查询当前异动详情
     * @param entity
     * @param type
     * @param searchAll
     * @return
     */
    public List<HrEmployee> countCurrentPsnChgDaily(HrEmployee entity, Integer type, boolean searchAll ,Integer chgType) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql  = new StringBuilder("SELECT e.id,e.empName,e.sex,e.joinCompDate,ej.turnFormalDate,ej.isFormal,c.name companyStr,d.NAME departmentStr,j.name job,c2.name companyStr2,d2.NAME departmentStr2,j2.name job2,");
        sql.append("ej.dutyLevel dutyLevelId,e.idCard,ej.jobSequence,chg.chgType,chg.chgDate,chg.createtime,ei.school,ei.education");
        sql.append(" FROM hr_employee_psn_chg chg");
        if (type == 1){
            sql.append(" INNER JOIN hr_employee_psn_chg chg2 ON chg2.id = chg.id - 1");
        }else{
            sql.append(" INNER JOIN hr_employee_psn_chg chg2 ON chg2.id = chg.id + 1");
        }
        sql.append(" INNER JOIN hr_employee e on e.id = chg.empId");

        sql.append(" INNER JOIN hr_employee_job ej ON ej.id=chg.empJobId");
        sql.append(" INNER JOIN hr_company c ON c.id = ej.company");
        sql.append(" INNER JOIN hr_department d ON d.id = ej.department");
        sql.append(" INNER JOIN hr_job j ON j.id = ej.job");

        sql.append(" INNER JOIN hr_employee_job ej2 ON ej2.id=chg2.empJobId");
        sql.append(" INNER JOIN hr_company c2 ON c2.id = chg2.company");
        sql.append(" INNER JOIN hr_department d2 ON d2.id = chg2.department");
        sql.append(" INNER JOIN hr_job j2 ON j2.id = ej2.job");
        sql.append(" LEFT JOIN hr_education_info ei ON ei.empId = e.id and ei.topEducation = 1");

        if (type==1){
            sql.append(" WHERE c2.code REGEXP ");
        }else{
            sql.append(" WHERE c.code REGEXP ");
        }
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        sql.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        if (!searchAll){
            if (type==1) {
                sql.append(" AND chg.company=:company");
            }else{
                sql.append(" AND chg2.company=:company");
            }
            if (null == entity.getCompany()){
                entity.setCompany(user.getCurrCompanyId());
            }
        }
        sql.append(" AND  chg.chgDate >= :startDate").append(" AND chg.chgDate <= :endDate");
        if (null!=entity.getJobSequence()){
            sql.append(" AND ej.jobSequence=:jobSequence");
        }
        if (chgType == 1){
            sql.append(" AND chg.chgType = 1"); //垮公司调动
        }else{
            sql.append(" AND chg.chgType = 2"); //内部调动
        }
        sql.append(" AND chg.chgFlag = ").append(type);
        sql.append("  AND chg.isDelete=0");
        sql.append(" ORDER BY c.code,d.rank desc");
        return super.get(sql.toString(),entity);
    }

    /**
     *
     * @param entity
     * @param type 1调入 2 调出
     * @param searchAll
     * @return
     */
    public List<HrEmployee> countPsnChgDaily(HrEmployee entity, Integer type, boolean searchAll ,Integer chgType) {
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql  = new StringBuilder("SELECT e.id,c.area,e.empName,e.sex,e.joinCompDate,ej.turnFormalDate,ej.isFormal,c.name companyStr,e.managerGroup,ej.empNo,e.birthdate,");
        sql.append("d.NAME departmentStr,j.name job,c2.name companyStr2,d2.NAME departmentStr2,j2.name job2,");
        sql.append("ej.dutyLevel dutyLevelId,e.idCard,ej.jobSequence,chg.chgType,chg.chgDate,chg.createtime,ei.school,ei.education");
        sql.append(" FROM hr_statistics_emp_chg_data_detail dd");
        sql.append(" INNER JOIN hr_employee_psn_chg chg on dd.businessId = chg.id");
        if (type == 1){
            sql.append(" INNER JOIN hr_employee_psn_chg chg2 ON chg2.id = chg.id - 1");
        }else{
            sql.append(" INNER JOIN hr_employee_psn_chg chg2 ON chg2.id = chg.id + 1");
        }
        sql.append(" INNER JOIN hr_employee e on e.id = dd.empId");

        sql.append(" INNER JOIN hr_employee_job ej ON ej.id=chg.empJobId");
        sql.append(" INNER JOIN hr_company c ON c.id = ej.company");
        sql.append(" INNER JOIN hr_department d ON d.id = ej.department");
        sql.append(" INNER JOIN hr_job j ON j.id = ej.job");

        sql.append(" INNER JOIN hr_employee_job ej2 ON ej2.id=chg2.empJobId");
        sql.append(" INNER JOIN hr_company c2 ON c2.id = chg2.company");
        sql.append(" INNER JOIN hr_department d2 ON d2.id = chg2.department");
        sql.append(" INNER JOIN hr_job j2 ON j2.id = ej2.job");
        sql.append(" LEFT JOIN hr_education_info ei ON ei.empId = e.id and ei.topEducation = 1");

        if (type==1){
            sql.append(" WHERE c2.code REGEXP ");
        }else{
            sql.append(" WHERE c.code REGEXP ");
        }
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        sql.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        if (!searchAll){
            if (type==1) {
                sql.append(" AND chg.company=:company");
            }else{
                sql.append(" AND chg2.company=:company");
            }
            if (null == entity.getCompany()){
                entity.setCompany(user.getCurrCompanyId());
            }
        }
        sql.append(" AND  chg.chgDate >= :startDate").append(" AND chg.chgDate <= :endDate");
        if (null!=entity.getJobSequence()){
            sql.append(" AND ej.jobSequence=:jobSequence");
        }
        if (chgType == 1){
            sql.append(" AND chg.chgType = 1"); //垮公司调动
        }else{
            sql.append(" AND chg.chgType = 2"); //内部调动
        }
        sql.append(" AND chg.chgFlag = ").append(type);
        sql.append("  AND chg.isDelete=0");
        sql.append("  AND dd.status=0");
        sql.append(" ORDER BY c.code,d.rank desc");
        return super.get(sql.toString(),entity);
    }

    public List<HrEmployee> getAllNoEducationEmp(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT t.empId,t.empName,c.plate,c.`name` companyStr,d.`name` departmentStr FROM");
        stringBuilder.append(" (SELECT j.empId,e.empName,j.company,j.department");
        stringBuilder.append(" FROM hr_employee_job j");
        stringBuilder.append(" INNER JOIN hr_employee e ON e.id = j.empId");
        stringBuilder.append(" WHERE  j.onGuard = 1 AND j.isDelete=0");
        stringBuilder.append(" ORDER BY e.empName ) t");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = t.company");
        stringBuilder.append(" INNER JOIN hr_department d ON d.id = t.department");
        stringBuilder.append(" LEFT JOIN");
        stringBuilder.append(" ( SELECT j.empId,e.empName,j.company,j.department,i.education  FROM hr_employee_job j");
        stringBuilder.append(" INNER JOIN hr_employee e ON e.id = j.empId");
        stringBuilder.append("  INNER JOIN hr_education_info i ON e.id = i.empId AND i.topEducation =1");
        stringBuilder.append(" WHERE  j.onGuard = 1 and i.status=0 AND j.isDelete=0");
        stringBuilder.append(" ORDER BY e.empName) t2");
        stringBuilder.append(" ON t.empId = t2.empId AND t.company = t2.company AND t.department = t2.department");
        stringBuilder.append(" WHERE t2.empId IS NULL AND c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority dataAuthorityMap = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(dataAuthorityMap,user.getCurrCompanyCode()).authoritiesRegexp());
        return super.get(stringBuilder.toString(),new HrEmployee());
    }

    /**
     * 根据姓名和电话查询个人信息
     * @param hrEmployee
     * @return
     */
    public HrEmployee getEmployeeByNameAndMobile(HrEmployee hrEmployee) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * from hr_employee WHERE empName = :empName");
        String mobileStr = hrEmployee.getOldMobile();
        if (StringUtils.isNotBlank(mobileStr)) {
            sql.append(" and (mobile = :mobile");
            String[] mobiles = mobileStr.split(",|，");
            for (int i = 0;i<mobiles.length;i++) {
                String s = mobiles[i];
                if (StringUtils.isNotBlank(s)) {
                    if (i == mobiles.length - 1) {
                        sql.append(" or mobile="+ s+")");
                    } else {
                        sql.append(" or mobile="+ s);
                    }
                }
            }
        } else {
            sql.append(" and mobile = :mobile");
        }
        return super.getOne(sql.toString(), hrEmployee);
    }


}
