package com.huayu.hr.dao;

import com.huayu.hr.domain.HrEmployeePsnChg;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.ly.dao.base.BasePageDao;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/2/23.
 */
@Repository
public class HrEmployeePsnChgDao extends BasePageDao<HrEmployeePsnChg,Integer>{

    @Override
    public int post(HrEmployeePsnChg entity) {
        Long longKey = super.getKey("hr_employee_psn_chg",entity);
        entity.setId(longKey.intValue());
        entity.setIsDelete(0);
        return super.post(entity);
    }

    public List<HrEmployeePsnChg> getPsnChgList(HrEmployeePsnChg entity){
        StringBuilder stringBuilder  = new StringBuilder("select hepc.*,c.name companyName  from hr_employee_psn_chg  hepc");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = hepc.company");
        stringBuilder.append(" where hepc.empId=:empId ");
        User user = SpringSecurityUtil.getUser();
        stringBuilder.append(" and c.code REGEXP ");
        if (StringUtils.isBlank(user.getDataAuthorityRegexp())){
            stringBuilder.append("'NULL'");
        }else {
            stringBuilder.append(user.getDataAuthoritiesRegexp());
        }
        stringBuilder.append(" and hepc.isDelete = 0");
        stringBuilder.append(" order by hepc.createtime desc, hepc.chgFlag asc");
        return super.get(stringBuilder.toString(),entity);
    }


    public Page<HrEmployeePsnChg> getEmpChgData(HrEmployeePsnChg psnChg, Pageable pageable) {
        StringBuilder stringBuilder  = new StringBuilder("select hepc.*  from hr_employee_psn_chg  hepc");
        stringBuilder.append(" INNER JOIN hr_company c ON c.id = hepc.companyId");
        stringBuilder.append(" where hepc.empId=:empId ");
        User user = SpringSecurityUtil.getUser();
        stringBuilder.append(" and c.code REGEXP ");
        if (StringUtils.isBlank(user.getDataAuthorityRegexp())){
            stringBuilder.append("'NULL'");
        }else {
            stringBuilder.append(user.getDataAuthoritiesRegexp());
        }
        stringBuilder.append(" and hepc.isDelete = 0");
        return super.get(stringBuilder.toString(),psnChg,pageable);
    }

    public HrEmployeePsnChg getPsnChgInOne(HrEmployeePsnChg hrEmployeePsnChg) {
        StringBuilder stringBuilder  = new StringBuilder("select hepc.*  from hr_employee_psn_chg  hepc");
        stringBuilder.append(" where hepc.empId=:empId and hepc.chgFlag = 1 and lastFlag=0 and hepc.isDelete = 0");
        return super.getOne(stringBuilder.toString(),hrEmployeePsnChg);
    }

    public int endEmpChg(HrEmployeePsnChg entity){
        StringBuilder stringBuilder  = new StringBuilder("update hr_employee_psn_chg  set endDate=:endDate,lastFlag=:lastFlag ");
        stringBuilder.append(" where empId=:empId and id=:id and isDelete = 0");
        return super.put(stringBuilder.toString(),entity);
    }
}
