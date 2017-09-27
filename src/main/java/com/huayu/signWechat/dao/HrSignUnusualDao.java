package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.signWechat.domain.HrSignUnusual;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignUnusualArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Repository
public class HrSignUnusualDao extends BasePageDao<HrSignUnusual, Serializable> {
    public int postData(HrSignUnusual hrSignUnusual) {
        Long key = super.getKey("hr_sign_unusual", new HrSignUnusual());
        hrSignUnusual.setId(key.intValue());
        return super.post(hrSignUnusual);
    }

    public List<HrSignUnusual> getSignUnusualList() {
        String sql = "SELECT * FROM hr_sign_unusual where exception_type in (1,2)";
        return super.get(sql, new HrSignUnusual());
    }

    public List<HrSignUnusual> getSignUnusualByUseridAndCheckin_time(HrSignUnusual hrSignUnusual) {
        String sql = "SELECT userid,checkin_time,checkin_type,`status` from hr_sign_detail" +
                " WHERE checkin_time BETWEEN :startDate AND :endDate and exception_type in (1,2,3)";
        return super.get(sql, hrSignUnusual);
    }

    public List<HrSignUnusual> getSignUnusualByUseridOneDay(String format, String userid) {
        String sql = "SELECT * from hr_sign_unusual WHERE userid = '"+userid+"' AND DATE_FORMAT(checkin_time,'%Y-%m-%d') ='"+format+"'";
        return super.get(sql, new HrSignUnusual());
    }

    public Page<HrSignUnusual> unusualListData(HrSignUnusualArgs hrSignUnusualArgs, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT su.userid, ej.empNo userNum, sul.username,sul.exception_type, cp.`name` company, dep.`name` department," +
                " su.position, e.idCard, su.mobile, DATE_FORMAT(   sul.checkin_time,   '%Y-%m-%d' ) checkDate," +
                " sul.checkin_time, sul.checkin_type, sul.`status` FROM hr_sign_unusual sul" +
                " INNER JOIN hr_sign_user su ON su.userid = sul.userid" +
                " INNER JOIN hr_employee e ON su. NAME = e.empName AND POSITION(e.mobile IN su.oldMobile)" +
                " INNER JOIN hr_company cp ON cp.id = e.company" +
                " INNER JOIN hr_department dep ON dep.id = e.department" +
                " INNER JOIN hr_employee_job ej ON ej.empId = e.id AND ej.onGuard = 1" +
                " WHERE sul.`status` <> 0");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(" and cp.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        stringBuilder.append(" and cp.code REGEXP").append("'^204'");
        if (null != hrSignUnusualArgs.getStartTime() && null != hrSignUnusualArgs.getEndTime()) {
            stringBuilder.append(" AND sul.checkin_time BETWEEN :startTime AND :endTime");
        }
        if (StringUtils.isNotBlank(hrSignUnusualArgs.getUserNum())) {
            stringBuilder.append(" and ej.empNo = :userNum");
        }
        if (StringUtils.isNotBlank(hrSignUnusualArgs.getIdCard())) {
            stringBuilder.append(" and e.idCard = :idCard");
        }
        if (StringUtils.isNotBlank(hrSignUnusualArgs.getUsername())) {
            stringBuilder.append(" and LOCATE(:username, sul.username)>0");
        }
        if (StringUtils.isNotBlank(hrSignUnusualArgs.getMobile())) {
            stringBuilder.append(" and su.mobile=:mobile");
        }
        HrSignSqlUtil.setCodeDepartmentId(stringBuilder,hrSignUnusualArgs.getCode(),hrSignUnusualArgs.getDepartmentId(),"cp","dep");

        return super.get(stringBuilder.toString(), hrSignUnusualArgs, pageable);
    }

    public int updateHrSignUnusualByUserid(HrSignUnusual hrSignUnusual) {
        String s = DateTimeUtil.dateToString(hrSignUnusual.getCheckDate(), DateTimeUtil.YYYY_MM_DD);
        String sql = "UPDATE hr_sign_unusual u SET u.update_time =:update_time, u.update_user =:update_user,u.update_checkin_time=:update_checkin_time," +
                " u.`status` =:status, exception_type = :exception_type WHERE u.userid =:userid AND u.checkin_type =:checkin_type AND DATE_FORMAT(u.checkin_time,'%Y-%m-%d')='"+s+"'";
        return super.put(sql, hrSignUnusual);
    }

    public int updateHrSignUnusualStatus(HrSignUnusual hrSignUnusual, String checkin_date) {
        String sql = "update hr_sign_detail set status = :status where userid = :userid" +
                " and DATE_FORMAT(checkin_time,'%Y-%m-%d') = '"+checkin_date+"' and checkin_type=:checkin_type";
        return super.put(sql, hrSignUnusual);
    }

    public List<HrSignUnusual> getHrSignUnusualStatisticsList(HrSignUnusual hrSignUnusual) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.id,u.checkin_type,u.exception_type,u.`status`,u.groupname,u.checkin_time, d.userid, d.username, d.telephone, d.checkin_time startTime, dd.checkin_time endTime" +
                " FROM hr_sign_unusual u" +
                " LEFT JOIN hr_sign_detail d ON u.userid = d.userid AND DATE_FORMAT(u.checkin_time,'%Y-%m-%d') = DATE_FORMAT(d.checkin_time,'%Y-%m-%d') AND d.checkin_type=1" +
                " LEFT JOIN hr_sign_detail dd ON u.userid = dd.userid AND DATE_FORMAT(u.checkin_time,'%Y-%m-%d') = DATE_FORMAT(dd.checkin_time,'%Y-%m-%d') AND dd.checkin_type=2" +
                " WHERE u.userid = :userid AND u.checkin_time BETWEEN :startDate AND :endDate GROUP BY DATE_FORMAT(u.checkin_time, '%Y-%m-%d')");
        /*if ("1".equals(hrSignUnusual.getStatus())) {
            //迟到
            sql.append(" AND u.`status` = 1");
        } else if ("2".equals(hrSignUnusual.getStatus())) {
            //早退
            sql.append(" AND u.`status` = 2");
        } else if ("3".equals(hrSignUnusual.getStatus())) {
            sql.append(" AND u.`status` IN (3,4,5,6) GROUP BY DATE_FORMAT(u.checkin_time, '%Y-%m-%d')");
        }*/
        return super.get(sql.toString(), hrSignUnusual);
    }

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources) {
        String sql = "insert into hr_sign_unusual (id,userid,username,idCard,telephone,checkin_type,exception_type,checkin_time,groupname,location_detail,update_checkin_time,update_time,update_user,status) values (:id,:userid,:username,:idCard,:telephone,:checkin_type,:exception_type,:checkin_time,:groupname,:location_detail,:update_checkin_time,:update_time,:update_user,:status)";
        return super.batchUpdate(sql, sqlParameterSources);
    }
}
