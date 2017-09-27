package com.huayu.hr.dao;

import com.huayu.hr.domain.HrContract;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/3/13.
 */
@Repository
public class HrContractDao  extends BasePageDao<HrContract,Integer>{

    @Override
    public int post(HrContract a) {
        Long key = super.getKey("hr_contract",a);
        a.setId(key.intValue());
        a.setCreatetime(new Date());
        return super.post(a);
    }

    @Override
    public int put(HrContract a) {
        StringBuilder sql  = new StringBuilder("update hr_contract set contractNo=:contractNo,periodType=:periodType,periodCount=:periodCount,");
        sql.append("contractStartTime=:contractStartTime,contractEndTime=:contractEndTime,isProb=:isProb,probCount=:probCount,");
        sql.append("probStartTime=:probStartTime,probEndTime=:probEndTime,contractType=:contractType,isSecret=:isSecret,contractCompany=:contractCompany,note=:note,");
        sql.append("updatetime=:updatetime where empId=:empId and id=:id");
        return super.put(sql.toString(),a);
    }

    public Page<HrContract> getContractList(HrContract contract, Pageable pageable) {
        StringBuilder sql  = new StringBuilder("select hc.*,c.name companyName from hr_contract hc");
        sql.append(" INNER JOIN hr_company c ON c.id = hc.company");
        sql.append(" WHERE hc.empId=:empId");
        return super.get(sql.toString(),contract,pageable);
    }

    public List<HrContract> getAllContractList(HrContract contract) {
        StringBuilder sql  = new StringBuilder("select hc.*,c.name companyName from hr_contract hc");
        sql.append(" INNER JOIN hr_company c ON c.id = hc.company");
        sql.append(" WHERE hc.empId=:empId");
        return super.get(sql.toString(),contract);
    }


    public HrContract getLastEffectiveContractOne(HrContract contract) {
        StringBuilder sql  = new StringBuilder("select hc.*,c.name companyName from hr_contract hc");
        sql.append(" INNER JOIN hr_company c ON c.id = hc.company");
        sql.append(" WHERE hc.empId=:empId and operType in (1,2,3,6) ");
        sql.append(" AND hc.status = 1");
        return super.getOne(sql.toString(),contract);
    }

    public HrContract getContractById(HrContract contract){
        return super.getOne("select hc.* from hr_contract hc where hc.id=:id and hc.status = 1",contract);
    }

    public int disableContractById(HrContract contract){
        return super.put("update hr_contract hc set hc.status=0, hc.updatetime=:updatetime where hc.id=:id",contract);
    }

    public Page<HrContract> getContractEmpListData(HrContract contract, Pageable pageable) {
        StringBuilder stringBuilder  = new StringBuilder("select e.id empId,e.empName,c.name companyName,d.NAME departmentName,ej.empNo,j.name jobName,ej.startTime arrivalDate");
        stringBuilder.append(" FROM hr_employee e");
        stringBuilder.append(" INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = ej.company");
        stringBuilder.append(" LEFT JOIN hr_department d ON d.id = ej.department");
        stringBuilder.append(" LEFT JOIN hr_job j ON j.id = ej.job");
        stringBuilder.append(" LEFT JOIN hr_contract hc ON hc.empId = e.id");
        stringBuilder.append(" where e.isDelete = 0 and ej.onGuard=1");
        if (StringUtils.isNotBlank( contract.getEmpNo())){
            stringBuilder.append(" and ej.empNo = :empNo");
        }
        if (StringUtils.isNotBlank(contract.getEmpName())){
            stringBuilder.append("  and position(:empName in e.empName)");
        }
        if (null != contract.getDepartment()){
            stringBuilder.append(" and ej.department = :department");
        }
        User user = SpringSecurityUtil.getUser();
        if (StringUtils.isBlank(user.getDataAuthorityRegexp())){
            stringBuilder.append(" and c.code REGEXP 'NULL'");
        }else {
          stringBuilder.append(" and c.code REGEXP ");
          stringBuilder.append(user.getDataAuthorityRegexp());
        }
        if (null!=contract.getOperType()){
            stringBuilder.append(" and hc.operType=:operType ");
        }
        if (null!=contract.getStartTime1()&&null!=contract.getStartTime2()){
            stringBuilder.append(" and hc.contractStartTime>=:startTime1 and hc.contractStartTime<=:startTime2");
        }
        if (null!=contract.getEndTime1()&&null!=contract.getEndTime2()){
            stringBuilder.append(" and hc.contractEndTime>=:startTime1 and hc.contractEndTime<=:startTime2");
        }
        stringBuilder.append(" group by e.id");
        return super.get(stringBuilder.toString(),contract,pageable);
    }

    public HrContract getContractOne(HrContract contract) {
        StringBuilder stringBuilder  = new StringBuilder( "select hc.*,c.name companyName from hr_contract hc  INNER JOIN hr_company c ON c.id = hc.company where hc.id=:id  and hc.empId=:empId");
        if (null!=contract.getStatus()){
            stringBuilder.append(" and hc.status = :status");
        }
        return super.getOne(stringBuilder.toString(),contract);
    }

    public List<HrContract> getTurnFormalRemindData(HrContract contract) {
        String sql = "SELECT DISTINCT c.empId,c.probEndTime,c.company from hr_contract c INNER JOIN hr_employee_job ej ON c.empId = ej.empId  where DATE_FORMAT(c.probEndTime,'%Y%m%d') >= :startDate and  DATE_FORMAT(c.probEndTime,'%Y%m%d') <= :endDate AND c.`status` = 1 AND ej.onGuard = 1 AND ej.isFormal = 0";
        return super.get(sql,contract);
    }

    public List<HrContract> getContractEndRemindData(HrContract contract) {
        String sql = "SELECT DISTINCT c.empId,c.contractEndTime,c.company from hr_contract c INNER JOIN hr_employee_job ej ON c.empId = ej.empId where DATE_FORMAT(c.contractEndTime,'%Y%m%d') >= :startDate and  DATE_FORMAT(c.contractEndTime,'%Y%m%d') <= :endDate AND c.`status` = 1 AND ej.onGuard = 1";
        return super.get(sql,contract);
    }
}
