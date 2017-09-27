package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.signWechat.domain.HrSignOutside;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignOutsideArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/25.
 */
@Repository
public class HrSignOutsideDao extends BasePageDao<HrSignOutside, Serializable> {

    public int postData(HrSignOutside hrSignOutside) {
        Long key = super.getKey("hr_sign_outside", new HrSignOutside());
        hrSignOutside.setId(key.intValue());
        return super.post(hrSignOutside);
    }

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources) {
        String sql = "insert into hr_sign_outside (id,userid,username,telephone,groupname,checkin_type,exception_type," +
                "checkin_time,location_title,location_detail,wifiname,notes,wifimac,mediaids,createTime,status)" +
                " values (:id,:userid,:username,:telephone,:groupname,:checkin_type,:exception_type,:checkin_time,:location_title,:location_detail,:wifiname,:notes,:wifimac,:mediaids,:createTime,:status)";
        return super.batchUpdate(sql, sqlParameterSources);
    }

    public Page<HrSignOutside> getOutsideSignDataList(HrSignOutsideArgs hrSignOutsideArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT so.status,so.id,ej.empNo userNum,u.`name` username,u.userid,d.name department,c.name company,");
        sql.append(" DATE_FORMAT(so.checkin_time,'%Y-%m-%d') checkDate,DATE_FORMAT(so.checkin_time,'%H:%i:%s') checkInTimeStr,");
        sql.append(" CASE DATE_FORMAT(so.checkin_time, '%w') WHEN '1' THEN '星期一' WHEN '2' THEN '星期二' WHEN '3' THEN '星期三'");
        sql.append(" WHEN '4' THEN '星期四' WHEN '5' THEN '星期五' WHEN '6' THEN '星期六' WHEN '0' THEN '星期日' ELSE '' END WEEK,so.location_detail,so.notes ");
        sql.append(" FROM hr_sign_outside so INNER JOIN hr_sign_user u ON so.userid = u.userid");
        sql.append(" LEFT JOIN hr_employee e ON e.empName = u.`name` AND POSITION(e.mobile IN u.oldMobile) AND e.isDelete = 0");
        sql.append(" LEFT JOIN hr_company c ON c.id = e.company LEFT JOIN hr_department d ON d.id = e.department");
        sql.append(" LEFT JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" where c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");
        if (null != hrSignOutsideArgs.getStartTime() && null != hrSignOutsideArgs.getEndTime()) {
            sql.append(" AND so.checkin_time BETWEEN :startTime AND :endTime");
        }
        if (StringUtils.isNotBlank(hrSignOutsideArgs.getUsername())) {
            sql.append(" and LOCATE(:username, u.`name`)>0");
        }
        HrSignSqlUtil.setCodeDepartmentId(sql,hrSignOutsideArgs.getCode(),hrSignOutsideArgs.getDepartmentId(),"c","d");

        return super.get(sql.toString(), hrSignOutsideArgs, pageable);
    }

    public int updateOutside(HrSignOutside hrSignOutside) {
        String sql = "UPDATE hr_sign_outside set `status`  =:status  where id = :id";
        return super.post(sql, hrSignOutside);
    }

    public HrSignOutside getOneOutside(HrSignOutside hrSignOutside) {
        String sql = "SELECT o.userid,o.checkin_time,o.location_detail,u.`name` username,u.mobile telephone FROM hr_sign_outside o INNER JOIN hr_sign_user u ON u.userid = o.userid WHERE o.id =:id";
        return super.getOne(sql, hrSignOutside);
    }
}
