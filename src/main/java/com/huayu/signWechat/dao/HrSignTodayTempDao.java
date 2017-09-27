package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.signWechat.domain.HrSignTodayTemp;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignTodayTempArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Administrator on 2017/9/21.
 */
@Repository
public class HrSignTodayTempDao extends BasePageDao<HrSignTodayTemp, Serializable> {
    public int saveSign(HrSignTodayTemp todayTemp) {
        Long key = super.getKey("hr_sign_today_temp", new HrSignTodayTemp());
        todayTemp.setId(key.intValue());
        return super.post(todayTemp);
    }

    public Page<HrSignTodayTemp> getTodaySignDataList(HrSignTodayTempArgs hrSignTodayTempArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ej.empNo userNum, su.`name` username, su.userid, d.`name` department,tt.exception_type, c.`name` company," +
                " su.mobile telephone, tt.checkin_type,DATE_FORMAT(tt.checkin_time,'%Y-%m-%d') checkDate," +
                " DATE_FORMAT(tt.checkin_time,'%H:%i:%s') checkinTimeStr, checkin_time," +
                " CASE DATE_FORMAT(tt.checkin_time, '%w') WHEN '1' THEN '星期一' WHEN '2' THEN '星期二' WHEN '3' THEN '星期三'" +
                " WHEN '4' THEN '星期四' WHEN '5' THEN '星期五' WHEN '6' THEN '星期六' WHEN '0' THEN '星期日' ELSE '' END WEEK, tt.groupname, tt.`status`" +
                " FROM hr_sign_today_temp tt" +
                " LEFT JOIN hr_sign_user su ON tt.userid = su.userid" +
                " LEFT JOIN hr_employee e ON e.empName = su.name AND POSITION(e.mobile IN su.oldMobile) AND e.isDelete = 0" +
                " LEFT JOIN hr_company c ON c.id = e.company INNER JOIN hr_department d ON d.id = e.department" +
                " LEFT JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" where c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());

        if (StringUtils.isNotBlank(hrSignTodayTempArgs.getUsername())) {
            sql.append(" and LOCATE(:username, username)>0");
        }

        if (StringUtils.isNotBlank(hrSignTodayTempArgs.getCheckin_type()) && !"0".equals(hrSignTodayTempArgs.getCheckin_type())) {
            sql.append(" and tt.checkin_type = :checkin_type");
        }

        HrSignSqlUtil.setCodeDepartmentId(sql,hrSignTodayTempArgs.getCode(),hrSignTodayTempArgs.getDepartmentId(),"c","d");
        return super.get(sql.toString(), hrSignTodayTempArgs, pageable);
    }

    public List<HrSignTodayTemp> getTodaySignUnusual() {
        String sql = "SELECT * FROM hr_sign_today_temp WHERE exception_type <> 0";
        return super.get(sql, new HrSignTodayTemp());
    }

    public int updateSignStatus(HrSignTodayTemp temp, String date) {
        String sql = "update hr_sign_today_temp SET status = :status WHERE userid = :userid" +
                " AND DATE_FORMAT(checkin_time, '%Y-%m-%d') ='"+date+"' AND checkin_type =:checkin_type";
        return super.put(sql, temp);
    }

    public void deleteTodayTemp() {
        String sql = "delete from hr_sign_today_temp";
        super.delete(sql,new HrSignTodayTemp());
    }

    public List<HrSignTodayTemp> getSignDetailByUseridAndDateA(HrSignTodayTempArgs hrSignTodayTempArgs) {
        String sql = "SELECT * FROM ( SELECT userid, username, checkin_time, COUNT(userid) num FROM hr_sign_today_temp" +
                " GROUP BY DATE_FORMAT(checkin_time, '%Y-%m-%d'), userid ) a WHERE a.num = 1 AND a.checkin_time BETWEEN :startTime AND :endTime";
        return super.get(sql, hrSignTodayTempArgs);
    }

    public List<HrSignTodayTemp> getSignDetailByUseridAndCheckDate(HrSignTodayTemp h) {
        String sql = "SELECT * FROM hr_sign_today_temp WHERE userid = :userid AND DATE_FORMAT(checkin_time, '%Y-%m-%d') =" +
                " DATE_FORMAT(:checkin_time, '%Y-%m-%d') ORDER BY checkin_time,checkin_type";
        return super.get(sql, h);
    }

    public List<HrSignTodayTemp> getSignDetailByUseridAndDate(HrSignTodayTempArgs hrSignTodayTempArgs) {
        String sql = "SELECT * FROM hr_sign_today_temp WHERE userid = :userid AND checkin_time BETWEEN :startTime AND :endTime";
        return super.get(sql, hrSignTodayTempArgs);
    }

    public int updateHrSignDetailOffset(HrSignTodayTemp temp, String checkin_date) {
        String sql = "UPDATE hr_sign_today_temp SET exception_type =:exception_type, updateTime =:updateTime,status = :status WHERE userid = :userid AND" +
                " DATE_FORMAT(checkin_time,'%Y-%m-%d') ='"+checkin_date+"' AND checkin_type =:checkin_type ";
        return super.put(sql, temp);
    }
}
