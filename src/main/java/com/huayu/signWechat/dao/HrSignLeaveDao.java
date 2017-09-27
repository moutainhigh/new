package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.signWechat.domain.HrSignLeave;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignLeaveArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
@Repository
public class HrSignLeaveDao extends BasePageDao<HrSignLeave, Serializable> {

    public Page<HrSignLeave> signLeaveListData(HrSignLeaveArgs hrSignLeaveArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT  ej.empNo userNum, c.`name` company, d.`name` department, m.position, m.username, e.idCard, m.telephone mobile, s.leaveType, s.startLeave, s.endLeave, s.leaveDayCount, s.restChanged, s.notes, m.reason" +
                " FROM hr_sign_leave_son s" +
                " INNER JOIN hr_sign_leave_main m ON s.formmainid = m.formmainid" +
                " INNER JOIN hr_sign_user hsu ON m.username = hsu.`name` AND POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = m.username AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " INNER JOIN hr_department d ON d.id = e.department" +
                " INNER JOIN hr_employee_job ej ON ej.empId = e.id AND ej.onGuard = 1" +
                " WHERE s.status = 2 AND m.status = 2");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");
        if (null != hrSignLeaveArgs.getStartTime() && null != hrSignLeaveArgs.getEndTime()) {
            sql.append(" AND ((startLeave BETWEEN :startTime AND :endTime) or (endLeave BETWEEN :startTime and :endTime) or (:startTime BETWEEN startLeave and endLeave))");
        }
        if (StringUtils.isNotBlank(hrSignLeaveArgs.getUserNum())) {
            sql.append(" and ej.empNo = :userNum");
        }
        if (StringUtils.isNotBlank(hrSignLeaveArgs.getIdCard())) {
            sql.append(" and e.idCard = :idCard");
        }
        if (StringUtils.isNotBlank(hrSignLeaveArgs.getUsername())) {
            sql.append(" and LOCATE(:username, m.username)>0");
        }
        if (StringUtils.isNotBlank(hrSignLeaveArgs.getMobile())) {
            sql.append(" and m.telephone = :mobile");
        }
        HrSignSqlUtil.setCodeDepartmentId(sql,hrSignLeaveArgs.getCode(),hrSignLeaveArgs.getDepartmentId(),"c","d");

        return super.get(sql.toString(), hrSignLeaveArgs, pageable);
    }

    public List<HrSignLeave> getHrSignLeaveByUsernameAndTelAndDate(HrSignLeave hrSignLeave, String checkin_date) {
        String sql = "SELECT s.id FROM hr_sign_leave_son s" +
                " INNER JOIN hr_sign_leave_main m ON s.formmainid = m.formmainid" +
                " INNER JOIN hr_sign_user u ON u.`name` = m.username AND POSITION(m.telephone IN u.oldMobile)" +
                " WHERE u.userid = :userid and(:startLeave BETWEEN s.startLeave AND s.endLeave)" +
                " and m.`status` = 2 and s.`status` = 2";
        return super.get(sql, hrSignLeave);
    }

    public Page<HrSignLeave> listLeavePatchData(HrSignLeaveArgs hrSignLeaveArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.id, m.username, c.`name` company, d.`name` department, m.position, e.idCard, m.telephone, s.leaveType, s.startLeave, s.endLeave, s.leaveDayCount, m.`status`" +
                " FROM hr_sign_leave_son s" +
                " INNER JOIN hr_sign_leave_main m ON s.formmainid = m.formmainid" +
                " INNER JOIN hr_sign_user hsu ON m.username = hsu.`name` AND POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = m.username AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON e.company = c.id INNER JOIN hr_department d ON e.department = d.id" +
                " WHERE s.status = 1 AND m.`status` = 1");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");
        if (hrSignLeaveArgs.getStartTime() != null && hrSignLeaveArgs.getEndTime() != null) {
            sql.append(" and s.startLeave BETWEEN :startTime and :endTime");
        }
        return super.get(sql.toString(), hrSignLeaveArgs, pageable);
    }

    public HrSignLeave getHrSignLeaveById(HrSignLeave hrSignLeave) {
        String sql = "SELECT s.id, s.leaveType, s.startLeave, s.endLeave, s.leaveDayCount, s.restChanged, s.notes," +
                " m.company, m.department, m.username, m.telephone, m.position, m.reason" +
                " FROM hr_sign_leave_son s LEFT JOIN hr_sign_leave_main m ON s.formmainid = m.formmainid where s.id=:id";
        return super.getOne(sql, hrSignLeave);
    }

    public int updateLeave(HrSignLeave hrSignLeave) {
        StringBuilder sql = new StringBuilder("UPDATE hr_sign_leave_main m SET m.username = :username");
        /*if (StringUtils.isNotBlank(hrSignLeave.getIdCard())) {
            sql.append(" ,idCard = :idCard");
        }*/
        if (StringUtils.isNotBlank(hrSignLeave.getTelephone())) {
            sql.append(" ,m.telephone=:telephone");
        }
        sql.append(" WHERE m.formmainid = (SELECT s.formmainid FROM hr_sign_leave_son s WHERE s.id = :id)");
        return super.put(sql.toString(), hrSignLeave);
    }

    public int saveLeaveMainData(HrSignLeave hrSignLeave) {
        String sql = "INSERT INTO hr_sign_leave_main (formmainId,company,department,position,username,telephone," +
                " reason,createDate,status) VALUES (:formmainId, :company, :department, :position, :username, :telephone, " +
                " :notes, :createDate, :status)";
        return super.post(sql, hrSignLeave);
    }

    public int saveLeaveSonData(HrSignLeave hrSignLeave) {
        String sql = "INSERT INTO hr_sign_leave_son (formmainId,leaveType,startLeave,endLeave,leaveDayCount,restChanged," +
                " createDate, status) VALUES (:formmainId, :leaveType, :startLeave, :endLeave, :leaveDayCount, :restChanged, " +
                " :createDate, :status)";
        return super.post(sql, hrSignLeave);
    }


    public int changeLeaveMainFlag(HrSignLeave hrSignLeave) {
        String sql = "update hr_sign_leave_son set status = :status where id = :id";
        return super.put(sql, hrSignLeave);
    }

    public int changeLeaveSonFlag(HrSignLeave hrSignLeave) {
        String sql = "UPDATE hr_sign_leave_main m SET m.status = :status WHERE m.formmainid = (SELECT s.formmainid FROM hr_sign_leave_son s WHERE s.id = :id)";
        return super.put(sql, hrSignLeave);
    }

    public List<HrSignLeave> getHrSignLeaveByUseridAndDate(HrSignLeave hrSignLeave) {
        String sql = "SELECT m.username,m.telephone,s.startLeave,s.endLeave,s.restChanged,s.leaveDayCount,s.leaveType" +
                " FROM hr_sign_leave_son s" +
                " INNER JOIN hr_sign_leave_main m ON s.formmainid = m.formmainid" +
                " INNER JOIN hr_sign_user u ON m.username = u.`name` AND POSITION(m.telephone IN u.oldMobile)" +
                " WHERE u.userid = :userid AND ((startLeave BETWEEN :startTime AND :endTime) or (endLeave BETWEEN :startTime and :endTime) or (:startTime BETWEEN startLeave and endLeave)) AND m.status = 2 and s.status = 2";
        return super.get(sql,hrSignLeave);
    }

    public List<HrSignLeave> getAll() {
        String sql = "select * from hr_sign_leave";
        return super.get(sql, new HrSignLeaveArgs());
    }

    public List<HrSignLeave> checkHaveLeaveOneDay(HrSignLeaveArgs hrSignLeave) {
        StringBuilder sql = new StringBuilder("SELECT s.leaveType,s.startLeave,s.endLeave,m.username,m.telephone,hsu.userid FROM hr_sign_leave_son s");
        sql.append(" INNER JOIN hr_sign_leave_main m ON s.formmainId = m.formmainid");
        sql.append(" INNER JOIN hr_sign_user hsu ON hsu.`name` = m.username AND POSITION(m.telephone IN hsu.oldMobile)");
        sql.append(" WHERE m. STATUS = 2 AND s. STATUS = 2");
        if (null == hrSignLeave.getStartTime() && null == hrSignLeave.getEndTime()) {
            sql.append("AND hsu.userid=:userid and :startLeave BETWEEN s.startLeave AND s.endLeave");
        } else if (null != hrSignLeave.getStartTime() && null != hrSignLeave.getEndTime()) {
            sql.append(" and ((startLeave BETWEEN :startTime AND :endTime) or (endLeave BETWEEN :startTime and :endTime) or (:startTime BETWEEN startLeave and endLeave))");
        }
        return super.get(sql.toString(), hrSignLeave);
    }
}
