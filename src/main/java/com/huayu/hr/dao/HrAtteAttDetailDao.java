package com.huayu.hr.dao;

import com.huayu.hr.domain.HrAtteAttDetail;
import com.huayu.hr.web.args.HrAtteAttDetailArgs;
import com.huayu.common.BusinessException;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by DengPeng on 2017/4/13.
 */
@Repository
public class HrAtteAttDetailDao extends BasePageDao<HrAtteAttDetail,Integer>{

    /**
     * 从考勤机器上获取 考勤数据
     * @param args
     * @return
     */
    public List<HrAtteAttDetail> getAttDetailListFromSQLServer(HrAtteAttDetailArgs args){
        StringBuilder sql = new StringBuilder("select u.badgenumber,a.* from attshifts a");
        sql.append(" INNER JOIN userinfo u ON a.userid = u.userid where convert(INT,u.badgenumber) ");
        if (!CollectionUtils.isEmpty(args.getBadgenumbers())){
            sql.append(" in (").append(StringUtils.collectionToDelimitedString(args.getBadgenumbers(),",")).append(")");
        }else if(null!= args.getBadgenumber()){
            sql.append(" = :badgenumber");
        }else{
            throw new BusinessException("参数错误");
        }
        if (null!= args.getStartTime()&&null!= args.getEndTime()){
            sql.append(" and startTime>=:startTime ").append(" and endTime<=:endTime");
        }
        return super.get(sql.toString(), args);
    }

    /**
     * 检查是否存在考勤数据
     * @param detail
     * @return
     */
    public Long checkAttDetailExist(HrAtteAttDetailArgs detail){
        StringBuilder sql = new StringBuilder("select count(id) from hr_atte_att_detail where ");
        if (null!=detail.getEmpId()){
            sql.append(" empId=:empId");
        }else if (!CollectionUtils.isEmpty(detail.getEmpIds())){
            sql.append(" empId in (").append(StringUtils.collectionToDelimitedString(detail.getEmpIds(),",")).append(")");
        }else{
            throw new BusinessException("参数错误");
        }
        if (null!=detail.getStartTime()&&null!=detail.getEndTime()){
            sql.append(" and startTime>=:startTime ").append(" and endTime<=:endTime");
        }
        return super.getLong(sql.toString(),detail);
    }

    /**
     * 考勤 汇总
     * @param detail
     * @param pageable
     * @return
     */
    public Page<HrAtteAttDetail> statisticsAttDetailList(HrAtteAttDetailArgs detail, Pageable pageable){
        StringBuilder sql = new StringBuilder("select ad.empId,c.name companyName,d.name departmentName,e.empName,");
        sql.append(" sum(ad.workDay) workDay,sum(ad.realWorkDay) realWorkDay,sum(ad.noIn) noIn,sum(ad.noOut) noOut,sum(ad.late) late,sum(ad.early) early,");
        sql.append("sum(ad.absent) absent,sum(ad.lateCount) lateCount,sum(ad.earlyCount) earlyCount,sum(ad.absentCount) absentCount");
        sql.append(" FROM hr_atte_att_detail ad");
        sql.append(" INNER JOIN hr_employee e ON e.id = ad.empId");
        sql.append(" INNER JOIN hr_company c ON c.id = e.company");
        sql.append(" INNER JOIN hr_department d ON d.id = e.department");
        sql.append(" WHERE 1=1");
        if (null!=detail.getCompany()){
            sql.append(" AND c.id=:company");
        }
        if (null!=detail.getDepartment()){
            sql.append(" AND d.id=:department");
        }
        if (null!=detail.getEmpId()){
            sql.append(" AND ad.empId=:empId");
        }else if (!CollectionUtils.isEmpty(detail.getEmpIds())){
            sql.append(" AND ad.empId in (").append(StringUtils.collectionToDelimitedString(detail.getEmpIds(),",")).append(")");
        }
        sql.append(" AND ad.attDate>=:startDate AND ad.attDate<=:endDate");
        sql.append(" GROUP BY ad.empId");
        return super.get(sql.toString(),detail,pageable);
    }

    public List<HrAtteAttDetail> getAttDetailList(HrAtteAttDetailArgs detail){
        StringBuilder sql = new StringBuilder("select ad.empId,c.name companyName,d.name departmentName,e.empName,");
        sql.append(" sum(ad.workDay),sum(ad.realWorkDay),sum(ad.noIn),sum(ad.noOut),sum(ad.late),sum(ad.early),sum(ad.absent),sum(ad.lateCount),");
        sql.append("sum(ad.earlyCount),sum(ad.absentCount)");
        sql.append(" FROM hr_atte_att_detail ad");
        sql.append(" INNER JOIN hr_employee e ON e.id = ad.empId");
        sql.append(" INNER JOIN hr_company c ON c.id = e.company");
        sql.append(" INNER JOIN hr_department d ON d.id = e.department");
        sql.append(" WHERE empId =:empId ");
        sql.append(" AND ad.attDate>=:startDate AND ad.attDate<=:endDate");
        sql.append(" GROUP BY ad.empId");
        return super.get(sql.toString(),detail);
    }

    public Page<HrAtteAttDetail> getAttDetailList(HrAtteAttDetailArgs detail, Pageable pageable){
        StringBuilder sql = new StringBuilder("select c.name companyName,d.name departmentName,e.empName,ad.* from hr_atte_att_detail ad");
        sql.append(" INNER JOIN hr_employee e ON e.id = ad.empId");
        sql.append(" INNER JOIN hr_company c ON c.id = e.company");
        sql.append(" INNER JOIN hr_department d ON d.id = e.department");
        sql.append(" WHERE 1=1");
        if (null!=detail.getEmpId()){
            sql.append(" AND ad.empId=:empId");
        }
        if (null != detail.getCompany()){
            sql.append(" AND c.id = :company");
        }
        if (null != detail.getDepartment()){
            sql.append(" AND d.id = :department");
        }
        if (null!=detail.getStartDate()&&null!=detail.getEndDate()){
            sql.append(" AND ad.attDate>=:startDate ").append(" AND ad.attDate<=:endDate");
        }
        return super.get(sql.toString(),detail,pageable);
    }

    public int[] batchAddAttDetail(SqlParameterSource[] sqlParameterSources){
        StringBuilder sql = new StringBuilder("INSERT INTO hr_atte_att_detail ");
        sql.append("(id, empId, badgenumber, userid, attDate, clockInTime, clockOutTime, startTime, endTime, workDay, realWorkDay, noIn, noOut, late, early, absent, lateCount, earlyCount, absentCount, workTime, symbol, attTime, absentR, isRead, exception, createtime)");
        sql.append("VALUES");
        sql.append("(:id, :empId, :badgenumber, :userid, :attDate, :clockInTime, :clockOutTime, :startTime, :endTime, :workDay, :realWorkDay, :noIn, :noOut, :late, :early, :absent, :lateCount, :earlyCount, :absentCount, :workTime, :symbol, :attTime, :absentR, :isRead, :exception, :createtime)");
        return super.batchUpdate(sql.toString(),sqlParameterSources);
    }

}
