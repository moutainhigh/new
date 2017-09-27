package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.Authority;
import com.huayu.signWechat.domain.HrSignUser;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Repository
public class HrSignUserDao extends BasePageDao<HrSignUser, Serializable>{

    public HrSignUser getSignUserByuserid(HrSignUser u) {
        String sql = "select * from hr_sign_user where userid = :userid";
        HrSignUser one ;
        try {
            one = super.getOne(sql, u);
        } catch (Exception e) {
            throw new BusinessException("未查询到用户");
        }
        if (null == one) {
            return null;
        } else {
            return one;
        }
    }

    public int postData(HrSignUser u) {
        Long key = super.getKey("hr_sign_user", new HrSignUser());
        u.setId(key.intValue());
        String sql = "insert into hr_sign_user (id,userid,userNum,name,idCard,department,position,mobile,gender,email,isleader,avatar,telephone,english_name,status,extattr,isMatched)" +
                " values (:id,:userid,:userNum,:name,:idCard,:department,:position,:mobile,:gender,:email,:isleader,:avatar,:telephone,:english_name,:status,:extattr,:isMatched)";
        return super.post(sql, u);
    }

    public List<HrSignUser> getUserDetailList(String searchAll, HrSignUser entity) {
        StringBuilder sql = new StringBuilder("SELECT u.userid,e.managerGroup groupName, d4.dictDataKey pactCompanyName," +
                " c.plate plateName, c.`name` companyName, dep.`name` departmentName, ej.empNo userNum, u.mobile, e.birthdate," +
                " e.idCard, e.joinCompDate, ej.turnFormalDate inDueFormDate, d1.dictDataKey useStatus, d2.dictDataKey technicalLevel," +
                " d3.dictDataKey userCategory, CASE ej.onGuard WHEN 1 THEN '在职' WHEN 0 THEN '离职' ELSE '' END workStatus, e.performanceSys performanceSystem," +
                " e.empName NAME, CASE e.sex WHEN 1 THEN '男' WHEN 0 THEN '女' ELSE '' END sex, u.position" +
                " FROM hr_sign_user u INNER JOIN hr_employee e ON e.empName = u.`name` AND e.mobile IS NOT NULL AND e.mobile <> '' AND POSITION(e.mobile IN u.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " LEFT JOIN hr_department dep ON dep.id = e.department" +
                " INNER JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id" +
                " LEFT JOIN hr_contract hc ON hc.empJobId = ej.id AND hc. STATUS = 1 AND hc.operType IN (1, 2, 3)" +
                " LEFT JOIN hr_dict_data d1 ON d1.dictDataValue = ej.empGroup AND d1.dictId = 6 and d1.status = 0" +
                " LEFT JOIN hr_dict_data d2 ON d2.dictDataValue = ej.dutyLevel AND d2.dictId = 11 and d2.status = 0" +
                " LEFT JOIN hr_dict_data d3 ON d3.dictDataValue = ej.jobSequence AND d3.dictId = 8 and d3.status = 0" +
                " LEFT JOIN hr_dict_data d4 ON d4.dictDataValue = hc.contractCompany AND d4.dictId = 26 and d4.status = 0");
        if (!"searchAll".equals(searchAll)) {
            User user = SpringSecurityUtil.getUser();
            Authority authority = user.getDataAuthorityMap();
            sql.append(" where c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//            sql.append(" and c.code REGEXP").append("'^204'");
        }
        if (null != entity) {
            HrSignSqlUtil.setCodeDepartmentId(sql,entity.getCode(),entity.getDepartmentId(),"c","d");
        }
        return super.get(sql.toString(), entity);
    }

    public void updateUser() {
        String sql = "DELETE FROM hr_sign_user";
        super.delete(sql, new HrSignUser());
    }

    public List<HrSignUser> getAllUserDetailList(Integer status) {
        StringBuilder sql = new StringBuilder("select * from hr_sign_user");
        if (1 == status) {
            sql.append(" where `status` = 1");
        }
        return super.get(sql.toString(), new HrSignUser());
    }

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources) {
        String sql = "update hr_sign_user set name = :name, department = :department, position = :position, mobile = :mobile," +
                " oldMobile=:oldMobile,isleader=:isleader, status = :status, isMatched = :isMatched where userid = :userid";
        return super.batchUpdate(sql, sqlParameterSources);
    }

    public List<HrSignUser> getAllUserRegx() {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.* from hr_sign_user u");
        sql.append(" LEFT JOIN hr_employee e ON e.empName = u.`name` AND e.mobile IS NOT NULL AND e.mobile <> '' AND POSITION(e.mobile IN u.oldMobile)");
        sql.append(" LEFT JOIN hr_company c ON c.id = e.company");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" where c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        return super.get(sql.toString(), new HrSignUser());
    }
}
