package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.signWechat.domain.HrSignEgress;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignEgressArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Repository
public class HrSignEgressDao extends BasePageDao<HrSignEgress, Serializable> {

    public Page<HrSignEgress> egressListData(HrSignEgressArgs hrSignEgressArgs, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ej.empNo userNum, m.username, e.idCard, m.telephone, c.`name` company, d.`name` department," +
                " DATE_FORMAT(s.startTime, '%Y-%m-%d') egressDate, s.startTime, s.endTime, s.locationDetail, s.notes" +
                " FROM hr_sign_egress_son s INNER JOIN hr_sign_egress_main m ON s.formmainId = m.formmainid" +
                " INNER JOIN hr_sign_user hsu ON m.username = hsu.`name` and POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = hsu.`name` AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " INNER JOIN hr_department d ON d.id = e.department" +
                " INNER JOIN hr_employee_job ej ON ej.empId = e.id AND ej.onGuard = 1" +
                " WHERE m. STATUS = 2 AND s. STATUS = 2");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        stringBuilder.append(" and c.code REGEXP").append("'^204'");
        if (null != hrSignEgressArgs.getStartTime() && null != hrSignEgressArgs.getEndTime()) {
            stringBuilder.append(" and ((s.startTime BETWEEN :startTime AND :endTime) or (s.endTime BETWEEN :startTime and :endTime) or (:startTime BETWEEN s.startTime and s.endTime))");
        }
        if (StringUtils.isNotBlank(hrSignEgressArgs.getUserNum())) {
            stringBuilder.append(" and ej.empNo = :userNum");
        }
        if (StringUtils.isNotBlank(hrSignEgressArgs.getIdCard())) {
            stringBuilder.append(" and e.idCard = :idCard");
        }
        if (StringUtils.isNotBlank(hrSignEgressArgs.getUsername())) {
            stringBuilder.append(" and LOCATE(:username, m.username)>0");
        }
        if (StringUtils.isNotBlank(hrSignEgressArgs.getMobile())) {
            stringBuilder.append(" and m.telephone = :mobile");
        }

        HrSignSqlUtil.setCodeDepartmentId(stringBuilder,hrSignEgressArgs.getCode(),hrSignEgressArgs.getDepartmentId(),"c","d");

        return super.get(stringBuilder.toString(), hrSignEgressArgs, pageable);
    }

    public List<HrSignEgress> getHrSignEgressDayList(HrSignEgress hrSignEgress) {
        String sql = "SELECT startTime,endTime FROM hr_sign_egress_son s INNER JOIN hr_sign_egress_main m ON s.formmainid = m.formmainid" +
                " WHERE m.telephone = :telephone and m.username = :username and ((s.startTime BETWEEN :startTime AND :endTime) or (s.endTime BETWEEN :startTime and :endTime) or (:startTime BETWEEN s.startTime and s.endTime))" +
                " AND s.status = 2 AND m.status = 2";
        return super.get(sql, hrSignEgress);
    }

    public List<HrSignEgress> getEgressByUsernameAndTel(HrSignEgress hrSignEgress, String checkinDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.id FROM hr_sign_egress_son s");
        sql.append(" INNER JOIN hr_sign_egress_main m ON s.formmainid = m.formmainid");
        sql.append(" INNER JOIN hr_sign_user u ON u.`name` = m.username AND POSITION(m.telephone IN u.oldMobile)");
        sql.append(" WHERE u.userid = :userid");
        sql.append(" AND :startTime BETWEEN s.startTime AND s.endTime AND m.`status` = 2 and s.`status` = 2");
        return super.get(sql.toString(), hrSignEgress);
    }

    public Page<HrSignEgress> listUnusualPatchData(HrSignEgressArgs hrSignEgressArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT s.id, ej.empNo userNum, m.username, e.idCard, m.telephone, c.`name`" +
                " company, d.`name` department, DATE_FORMAT(s.startTime, '%Y-%m-%d') egressDate, s.startTime, s.endTime," +
                " s.locationDetail, s.notes, s.status FROM hr_sign_egress_son s" +
                " INNER JOIN hr_sign_egress_main m ON s.formmainid = m.formmainid" +
                " INNER JOIN hr_sign_user hsu ON m.username = hsu.`name` and POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = hsu.`name` AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " INNER JOIN hr_department d ON d.id = e.department" +
                " INNER JOIN hr_employee_job ej ON ej.empId = e.id AND ej.onGuard = 1" +
                " WHERE ((m.username IS NULL OR m.telephone IS NULL) OR (m.status = 1 and s.status = 1))");

        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");

        if (null != hrSignEgressArgs.getStartTime() && null != hrSignEgressArgs) {
            sql.append(" and s.startTime BETWEEN :startTime and :endTime");
        }
        return super.get(sql.toString(), hrSignEgressArgs, pageable);
    }

    public HrSignEgress getHrSignEgressById(HrSignEgress hrSignEgress) {
        String sql = "SELECT s.id, s.startTime,s.endTime,s.locationDetail,s.notes,m.company,m.department,m.position,m.username,m.telephone " +
                " FROM hr_sign_egress_son s" +
                " INNER JOIN hr_sign_egress_main m ON s.formmainid = m.formmainid" +
                " WHERE s.id = :id";
        return getOne(sql, hrSignEgress);
    }

    public int updateEgressByid(HrSignEgress hrSignEgress) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE hr_sign_egress_main m SET m.username = :username,m.telephone = :telephone");
        sql.append(" WHERE m.formmainid = (SELECT s.formmainid FROM hr_sign_egress_son s WHERE s.id = :id)");
        return super.put(sql.toString(), hrSignEgress);
    }

    public int changeEgressSonFlag(HrSignEgress hrSignEgress) {
        String sql = "update hr_sign_egress_son set status = :status where id = :id";
        return super.put(sql, hrSignEgress);
    }

    public int changeEgressMainFlag(HrSignEgress hrSignEgress) {
        String sql = "UPDATE hr_sign_egress_main m SET `status` = :status WHERE m.formmainid = (SELECT s.formmainid FROM hr_sign_egress_son s WHERE s.id = :id)";
        return super.put(sql, hrSignEgress);
    }


    public List<HrSignEgress> getAll() {
        String sql = "select * from hr_sign_egress";
        return super.get(sql, new HrSignEgress());
    }

    public List<HrSignEgress> checkHaveEgressOneDay(HrSignEgress hrSignEgress) {
        StringBuilder sql = new StringBuilder("SELECT m.username, hsu.userid, hsu.mobile telephone, DATE_FORMAT(s.startTime, '%Y-%m-%d') egressDate, s.startTime, s.endTime, s.locationDetail, s.notes");
        sql.append(" FROM hr_sign_egress_son s");
        sql.append(" INNER JOIN hr_sign_egress_main m ON s.formmainId = m.formmainid");
        sql.append(" INNER JOIN hr_sign_user hsu ON hsu.`name` = m.username AND POSITION(m.telephone IN hsu.oldMobile)");
        sql.append(" WHERE m. STATUS = 2 AND s. STATUS = 2");
        if (null == hrSignEgress.getEndTime()) {
            sql.append(" AND hsu.userid=:userid and :startTime BETWEEN s.startTime AND s.endTime");
        } else {
            sql.append(" and ((s.startTime BETWEEN :startTime AND :endTime) or (s.endTime BETWEEN :startTime and :endTime) or (:startTime BETWEEN s.startTime and s.endTime))");
        }
        return super.get(sql.toString(), hrSignEgress);
    }
}
