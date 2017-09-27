package com.huayu.signWechat.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.signWechat.domain.HrSignPatchSon;
import com.huayu.signWechat.utils.HrSignSqlUtil;
import com.huayu.signWechat.web.args.HrSignPatchSonArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Repository
public class HrSignPatchSonDao extends BasePageDao<HrSignPatchSon, Serializable> {

    public Page<HrSignPatchSon> patchUnusualListData(HrSignPatchSonArgs hrSignPatchSonArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.id, m. NAME, c.`name` company, d.`name` department, m.position, m.telephone, s.patchDate," +
                " s.patchTime, s.patchType, s.notes, s. STATUS FROM hr_sign_patch_son s" +
                " INNER JOIN hr_sign_patch_formmain m ON m.formmainId = s.formmainId" +
                " INNER JOIN hr_sign_user hsu ON hsu.`name` = m.`name` AND POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = hsu.`name` AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " INNER JOIN hr_department d ON d.id = e.department" +
                " WHERE m.`status` = 1 AND s.`status` = 1");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");
        if (hrSignPatchSonArgs.getStartTime() != null && hrSignPatchSonArgs.getEndTime() != null) {
            sql.append(" AND s.patchDate BETWEEN DATE_FORMAT(:startTime, '%Y-%m-%d') AND DATE_FORMAT(:endTime, '%Y-%m-%d')");
        }
        return super.get(sql.toString(), hrSignPatchSonArgs, pageable);
    }

    public int changePatchUnusualStatus(HrSignPatchSon hrSignPatchSon) {
        String sql = "update hr_sign_patch_son set status = :status where id = :id";
        return super.put(sql, hrSignPatchSon);
    }

    public HrSignPatchSon getOneById(HrSignPatchSon hrSignPatchSon) {
        String sql = "select * from hr_sign_patch_son where id = :id";
        return super.getOne(sql, hrSignPatchSon);
    }

    public Page<HrSignPatchSon> listPatchDetailData(HrSignPatchSonArgs hrSignPatchSonArgs, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ej.empNo userNum,m.`name` username,c.`name` company,d.`name` department,m.position,m.telephone,s.patchDate," +
                " s.patchType,s.patchTime,s.notes FROM hr_sign_patch_son s" +
                " INNER JOIN hr_sign_patch_formmain m ON s.formmainId = m.formmainId" +
                " INNER JOIN hr_sign_user hsu ON hsu.`name` = m.`name` AND POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = hsu.`name` AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " INNER JOIN hr_department d ON d.id = e.department" +
                " INNER JOIN hr_employee_job ej ON ej.empId = e.id AND ej.onGuard = 1" +
                " WHERE s.`status` = 2 AND m.`status` = 2");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
//        sql.append(" and c.code REGEXP").append("'^204'");
        if (null != hrSignPatchSonArgs.getStartTime() && null != hrSignPatchSonArgs.getEndTime()) {
            sql.append(" AND s.patchDate BETWEEN DATE_FORMAT(:startTime, '%Y-%m-%d') AND DATE_FORMAT(:endTime, '%Y-%m-%d')");
        }
        if (StringUtils.isNotBlank(hrSignPatchSonArgs.getUserNum())) {
            sql.append(" and e.id = :userNum");
        }
        if (StringUtils.isNotBlank(hrSignPatchSonArgs.getUsername())) {
            sql.append(" and LOCATE(:username, m.`name`)>0");
        }
        if (StringUtils.isNotBlank(hrSignPatchSonArgs.getTelephone())) {
            sql.append(" and m.telephone = :mobile");
        }
        HrSignSqlUtil.setCodeDepartmentId(sql,hrSignPatchSonArgs.getCode(),hrSignPatchSonArgs.getDepartmentId(),"c","d");

        return super.get(sql.toString(), hrSignPatchSonArgs, pageable);
    }

    public List<HrSignPatchSon> getHrSignPatchByUsernameAndTelAndDate(HrSignPatchSon patchSon) {
        String sql = "SELECT ej.empNo userNum, m.`name` username, c.`name` company, d.`name` department, m.position," +
                " m.telephone, s.patchDate, s.patchType, s.patchTime, s.notes FROM hr_sign_patch_son s" +
                " INNER JOIN hr_sign_patch_formmain m ON s.formmainId = m.formmainId" +
                " INNER JOIN hr_sign_user hsu ON hsu.`name` = m.`name` AND POSITION(m.telephone IN hsu.oldMobile)" +
                " INNER JOIN hr_employee e ON e.empName = hsu.`name` AND POSITION(e.mobile IN hsu.oldMobile)" +
                " INNER JOIN hr_company c ON c.id = e.company" +
                " INNER JOIN hr_department d ON d.id = e.department" +
                " INNER JOIN hr_employee_job ej ON ej.empId = e.id AND ej.onGuard = 1" +
                " WHERE s.`status` = 2 AND m.`status` = 2 AND" +
                " hsu.userid = :userid AND s.patchDate =:patchDate AND patchType = :patchType";
        return super.get(sql, patchSon);
    }

    public List<HrSignPatchSon> getHrSignPatchByStatus(HrSignPatchSon patchSon) {
        String sql = "SELECT * FROM hr_sign_patch_son where formmainId = :formmainId AND `status` = 1";
        return super.get(sql, patchSon);
    }
}
