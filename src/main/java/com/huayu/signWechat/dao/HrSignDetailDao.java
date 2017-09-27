package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.signWechat.domain.HrSignDetail;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignDetailArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Administrator on 2017/7/10.
 */
@Repository
public class HrSignDetailDao extends BasePageDao<HrSignDetail, Serializable> {

    public int saveSign(HrSignDetail s) {
        Long key = super.getKey("hr_sign_detail",new HrSignDetail());
        s.setId(key.intValue());
        return  super.post(s);
    }

    public Page<HrSignDetail> signDetailListData(HrSignDetailArgs hrSignDetailArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ej.empNo userNum, su.`name` username, su.userid, d.`name` department, c.`name` company, su.mobile" +
                " telephone, sd.checkin_type,DATE_FORMAT(sd.checkin_time,'%Y-%m-%d') checkDate,job.name position," +
                "DATE_FORMAT(sd.checkin_time,'%H:%i:%s') checkinTimeStr, sd.checkin_time, CASE DATE_FORMAT(sd.checkin_time, '%w')" +
                " WHEN '1' THEN '星期一' WHEN '2' THEN '星期二' WHEN '3' THEN '星期三' WHEN '4' THEN '星期四' WHEN '5'" +
                " THEN '星期五' WHEN '6' THEN '星期六' WHEN '0' THEN '星期日' ELSE '' END WEEK, sd.groupname, sd.`status`,hso.id oid" +
                " FROM hr_sign_detail sd" +
                " INNER JOIN hr_sign_user su ON su.userid = sd.userid" +
                " INNER JOIN hr_employee e ON su.`name` = e.empName AND POSITION(e.mobile IN su.oldMobile) AND e.isDelete = 0" +
                " INNER JOIN hr_company c ON c.id = e.company INNER JOIN hr_department d ON d.id = e.department" +
                " LEFT JOIN hr_employee_job ej ON e.lastEmpJobId = ej.id" +
                " LEFT JOIN hr_job job on job.id = ej.job" +
                " LEFT JOIN hr_sign_outside hso on hso.userid=sd.userid and DATE_FORMAT(sd.checkin_time,'%Y-%m-%d')=DATE_FORMAT(hso.checkin_time,'%Y-%m-%d')" +
                " and if (DATE_FORMAT(hso.checkin_time,'%H:%i:%s')<'09:00:00','1',IF(DATE_FORMAT(hso.checkin_time,'%H:%i:%s')>'18:00:00','2',''))=sd.checkin_type" +
                " WHERE sd.checkin_type in (1,2)");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");
        if (null != hrSignDetailArgs.getStartTime() && null != hrSignDetailArgs.getEndTime()) {
            sql.append(" AND sd.checkin_time BETWEEN :startTime AND :endTime");
        }
        if (StringUtils.isNotBlank(hrSignDetailArgs.getUserNum())) {
            sql.append(" and ej.empNo = :userNum");
        }
        if (StringUtils.isNotBlank(hrSignDetailArgs.getUsername())) {
            sql.append(" and LOCATE(:username, su.`name`)>0");
        }
        if (StringUtils.isNotBlank(hrSignDetailArgs.getMobile())) {
            sql.append(" AND LOCATE(:mobile, su.oldMobile)>0");
        }
        if (-1 != hrSignDetailArgs.getStatus()) {
            if (0 == hrSignDetailArgs.getStatus()) {
                sql.append(" and sd.status in (0,11)");
            }else if (1 == hrSignDetailArgs.getStatus()) {
                sql.append(" and sd.status in (3,4)");
            } else if (2 == hrSignDetailArgs.getStatus()) {
                sql.append(" and (hso.id is not null or hso.id <> '')");
            } else if (3 == hrSignDetailArgs.getStatus()) {
                sql.append(" and sd.status = 7");
            } else if (4 == hrSignDetailArgs.getStatus()) {
                sql.append(" and sd.status = 9");
            } else if (5 == hrSignDetailArgs.getStatus()) {
                sql.append(" and sd.status = 10");
            } else if (6 == hrSignDetailArgs.getStatus()) {
                sql.append(" and sd.status = 8");
            }

        }

        HrSignSqlUtil.setCodeDepartmentId(sql,hrSignDetailArgs.getCode(),hrSignDetailArgs.getDepartmentId(),"c","d");
        sql.append(" GROUP BY sd.userid,DATE_FORMAT(sd.checkin_time,'%Y-%m-%d'), sd.checkin_type");


        return super.get(sql.toString(), hrSignDetailArgs, pageable);
    }

    public List<HrSignDetail> getWorkDutyDaysSign(HrSignDetail hrSignDetail) {
        String sql = "(SELECT id, userid, username, telephone, groupname, checkin_type, exception_type, MIN(checkin_time)" +
                " checkin_time, location_title, location_detail FROM hr_sign_detail WHERE userid = :userid" +
                " AND exception_type NOT IN (1, 3) AND checkin_time BETWEEN :startDate AND :endDate" +
                " AND checkin_type = 1 GROUP BY DATE_FORMAT(checkin_time, '%Y-%m-%d') ORDER BY checkin_time )" +
                " UNION" +
                " (SELECT id, userid, username, telephone, groupname, checkin_type, exception_type, MAX(checkin_time)" +
                " checkin_time, location_title, location_detail FROM hr_sign_detail" +
                " WHERE userid = :userid AND exception_type NOT IN (1, 3) AND" +
                " checkin_time BETWEEN :startDate AND :endDate AND checkin_type = 2" +
                " GROUP BY DATE_FORMAT(checkin_time, '%Y-%m-%d') ORDER BY checkin_time )";
        return super.get(sql, hrSignDetail);

    }

    public int updateHrSignDetail(HrSignDetail hrSignDetail) {
        String s = DateTimeUtil.dateToString(hrSignDetail.getCheckDate(), DateTimeUtil.YYYY_MM_DD);
        String sql = "UPDATE hr_sign_detail d SET d.groupname=:groupname , d.exception_type =:exception_type ,d.updateTime=:updateTime," +
                " d.updateUser =:updateUser,d.checkin_time=:checkin_time WHERE d.userid =:userid" +
                " AND d.checkin_type =:checkin_type AND DATE_FORMAT(d.checkin_time,'%Y-%m-%d')='"+s+"'";
        return super.put(sql, hrSignDetail);
    }

    public int updateHrSignDetailOffset(HrSignDetail hrSignDetail,String checkin_date) {
        String sql = "UPDATE hr_sign_detail SET exception_type =:exception_type,status = :status WHERE userid = :userid AND" +
                " DATE_FORMAT(checkin_time,'%Y-%m-%d') ='"+checkin_date+"' AND checkin_type =:checkin_type ";
        return super.put(sql, hrSignDetail);
    }

    public List<HrSignDetail> getSignDetailByUseridAndDate(HrSignDetailArgs hrSignDetail) {
        StringBuilder sql = new StringBuilder("SELECT * FROM hr_sign_detail WHERE userid = :userid AND checkin_time BETWEEN :startTime AND :endTime");
        return super.get(sql.toString(), hrSignDetail);
    }

    public List<HrSignDetail> getSignDetailByUserid(HrSignDetailArgs hrSignDetailArgs) {
        String sql = "select * from hr_sign_detail where userid = :userid";
        return super.get(sql, hrSignDetailArgs);
    }

    public List<HrSignDetail> getSignTolateList(HrSignDetailArgs hrSignDetailArgs) {
        String sql = "SELECT * FROM hr_sign_detail WHERE TIME_FORMAT(checkin_time, '%H:%i:%s') BETWEEN '00:00:00' AND '04:00:00' AND checkin_time BETWEEN :startTime AND :endTime";
        return super.get(sql, hrSignDetailArgs);
    }

    public List<HrSignDetail> getSignDetailByUseridAndCheckDate(HrSignDetail hrSignDetail) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM hr_sign_detail WHERE userid = :userid AND DATE_FORMAT(checkin_time, '%Y-%m-%d') = DATE_FORMAT(:checkin_time, '%Y-%m-%d')");
        if (StringUtils.isNotBlank(hrSignDetail.getCheckin_type())) {
            sql.append(" and checkin_type = :checkin_type");
        }
        sql.append(" ORDER BY checkin_time,checkin_type");
        return super.get(sql.toString(), hrSignDetail);
    }

    public int deleteSignData(HrSignDetail hrSignDetail1) {
        String sql = "DELETE FROM hr_sign_detail WHERE id = :id";
        return super.delete(sql, hrSignDetail1);
    }

    public List<HrSignDetail> getSignDetailByUseridAndDateA(HrSignDetailArgs hrSignDetailArgs) {
        String sql = "SELECT * FROM ( SELECT userid, username, checkin_time, COUNT(userid) num FROM hr_sign_detail" +
                " GROUP BY DATE_FORMAT(checkin_time, '%Y-%m-%d'), userid ) a WHERE a.num = 1 AND a.checkin_time BETWEEN :startTime AND :endTime";
        return super.get(sql, hrSignDetailArgs);
    }

    public List<HrSignDetail> getSignDetailUserTelephone() {
        String sql = "SELECT * FROM hr_sign_detail  GROUP BY telephone, userid  ORDER BY checkin_time DESC";
        return super.get(sql, new HrSignDetail());
    }

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources) {
        String sql = "UPDATE hr_sign_detail SET exception_type = :exception_type WHERE userid = :userid AND checkin_type =" +
                " :checkin_type AND checkin_time = :checkin_time";
        return super.batchUpdate(sql, sqlParameterSources);
    }

    public HrSignDetail getGroupname(HrSignDetail signDetail) {
        String sql = "SELECT * FROM hr_sign_detail WHERE userid=:userid AND groupname <> '' LIMIT 0,1";
        return super.getOne(sql,signDetail);
    }

    public void updateHrSignDetailByUseridTimeType(HrSignDetail detail1) {
        String sql = "UPDATE hr_sign_detail SET exception_type = :exception_type, checkin_time = :checkin_time where userid = :userid" +
                " and DATE_FORMAT(checkin_time, '%Y-%m-%d') = DATE_FORMAT(:checkin_time, '%Y-%m-%d') and checkin_type =:checkin_type";
        super.post(sql, detail1);
    }
}
