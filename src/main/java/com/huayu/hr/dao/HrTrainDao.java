package com.huayu.hr.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.hr.domain.HrTrain;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/1/18.
 */
@Repository
public class HrTrainDao extends BasePageDao<HrTrain,Integer> {

    @Override
    public int post(HrTrain train) {
        Long longKey = super.getKey("hr_train",train);
        train.setId(longKey.intValue());
        train.setCreatetime(new Date());
        return super.post(train);
    }

    @Override
    public int put(HrTrain train) {
        StringBuilder sql = new StringBuilder(" update hr_train set");
        sql.append(" code=:code,name=:name,lv=:lv,year=:year,quarter=:quarter,month=:month,type=:type,way=:way,planCount=:planCount,actualCount=:actualCount,content=:content,target=:target,addr=:addr," +
                "compLv1=:compLv1,compLv1Str=:compLv1Str,compLv2=:compLv2,compLv2Str=:compLv2Str,compLv3=:compLv3,compLv3Str=:compLv3Str,"+
                "company=:company,department=:department,principal=:principal,mobile=:mobile,planCost=:planCost,actualCost=:actualCost,timeCount=:timeCount,totalTimeCount=:totalTimeCount," +
                "startTime=:startTime,endTime=:endTime,teacherName=:teacherName,teacherMobile=:teacherMobile,teacherComp=:teacherComp,note=:note");
        sql.append(" where id=:id");
        return super.put(sql.toString(),train);
    }

    public Page<HrTrain> getTrainListData(HrTrain train, Pageable pageable) {
        StringBuilder sql = buildSQL(train);
        return super.get(sql.toString(),train,pageable);
    }

    public List<HrTrain> getTrainListData2Statistics(HrTrain train) {
        StringBuilder sql  = new StringBuilder("SELECT t.*  FROM hr_train t");
        sql.append(" INNER JOIN hr_company c ON c.id = t.company");
        sql.append(" WHERE");
        sql.append(" DATE_FORMAT(t.startTime,'%m%d') = DATE_FORMAT(NOW(),'%m%d')");
        sql.append(" AND c.plateId=:plateId");
        return super.get(sql.toString(),train);
    }

    private StringBuilder buildSQL(HrTrain train){
        StringBuilder sql  = new StringBuilder("select t.* ");
        sql.append("  FROM hr_train t");
        sql.append(" INNER JOIN hr_company c ON c.id = t.company");
        sql.append("  WHERE 1=1");
        if (StringUtils.isNotBlank(train.getName())){
            sql.append("  AND position(:name in t.name)");
        }
        if (null!=train.getCompany()){
            sql.append(" AND t.company=:company");
        }
        if (null!=train.getDepartment()){
            sql.append(" AND t.department=:department");
        }
        if (null!=train.getType()){
            sql.append(" AND t.type=:type");
        }
        User user = SpringSecurityUtil.getUser();
        sql.append(" AND c.code REGEXP ");
        Authority authority = user.getDataAuthorityMap();
        sql.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        return sql;
    }

    @Override
    public HrTrain getOne(HrTrain train) {
        StringBuilder sql = new StringBuilder("select t.*, e.empName principalStr, d.name departmentStr");
        sql.append(" from hr_train t");
        sql.append(" inner join hr_department d on d.id = t.department ");
        sql.append(" inner join hr_employee e on e.id = t.principal ");
        sql.append(" where t.id=:id and t.status=:status");
        return super.getOne(sql.toString(),train);
    }

    public Page<HrTrain> getStatisticsCompData(HrTrain train, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select c.id,c.name companyName,d.name departmentName,t.type,t.compLv1Str,t.compLv2Str,t.compLv3Str,t.name,t.way,t.lv,t.startTime,t.endTime,t.timeCount,t.actualCost,t.note,");
        sql.append("COUNT(CASE WHEN te.jobSequence=5 THEN 1 ELSE NULL end) countM,COUNT(CASE WHEN te.jobSequence=6 THEN 1 ELSE NULL end) countW,SUM(te.studyTime) totalTime");
        sql.append(" from hr_train t ");
        sql.append(" inner join  hr_company c on c.id = t.company");
        sql.append(" inner join  hr_department d on d.id = t.department");
        sql.append(" inner join  hr_train_emp te on te.trainId = t.id and te.status=0");
        sql.append(" where t.status=:status");
        if (null!=train.getCompany()){
            sql.append(" and t.company=:company");
        }
        if (null!=train.getDepartment()){
            sql.append(" and t.department=:department");
        }
        if (null!=train.getStartTime() && null!=train.getEndTime()){
            sql.append(" and t.startTime>=:startTime and t.endTime<=:endTime ");
        }
        User user = SpringSecurityUtil.getUser();
        sql.append(" and c.code REGEXP ");
        if (StringUtils.isBlank(user.getDataAuthorityRegexp())){
            sql.append("'NULL'");
        }else {
            Authority authority = user.getDataAuthorityMap();
            sql.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        }
        sql.append(" GROUP BY t.id ");
        return super.get(sql.toString(),train,pageable);
    }

    public long getNextTrainNo() {
        return super.getKey("hr_train_no",new HrTrain());
    }


    public List<HrTrain> getTrainsByEmpId(HrTrain train) {
        StringBuilder sql = new StringBuilder("select t.* from hr_train t");
        sql.append(" inner join  hr_train_emp te on te.trainId = t.id");
        sql.append(" where t.status=:status");
        sql.append(" and te.empId=:empId");
        return super.get(sql.toString(),train);
    }
}
