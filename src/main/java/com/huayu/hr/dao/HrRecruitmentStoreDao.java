package com.huayu.hr.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.hr.domain.HrRecruitmentStore;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/7/20.
 */
@Repository
public class HrRecruitmentStoreDao extends BasePageDao<HrRecruitmentStore,Integer>{

    public int postData(HrRecruitmentStore a) {
        Long key = super.getKey("hr_recruitment_store",a);
        a.setId(key.intValue());
        return super.post(a);
    }

    public HrRecruitmentStore getStoreOne(HrRecruitmentStore store) {
        StringBuilder stringBuilder  = new StringBuilder("select s.* from hr_recruitment_store s inner join hr_company c on c.id = s.company  ");
        stringBuilder.append(" where  s.status=0  and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        stringBuilder.append(" and s.id = :id ");
        return super.getOne(stringBuilder.toString(),store);
    }

    public int updateStore(HrRecruitmentStore store) {
        StringBuilder stringBuilder = new StringBuilder(" update hr_recruitment_store set ");
        stringBuilder.append("name=:name,idCard=:idCard,sex=:sex,birth=:birth,school=:school,education=:education,profession=:profession,companyName=:companyName");
        stringBuilder.append(",joinWorkDate=:joinWorkDate,job=:job,liveAddress=:liveAddress,recentWorkTime=:recentWorkTime,resumeSrc=:resumeSrc,askJob=:askJob,leaveReason=:leaveReason");
        stringBuilder.append(",auditionDate=:auditionDate,auditionUser=:auditionUser,mobile=:mobile,email=:email,");
        stringBuilder.append("note=:note,otherExperience=:otherExperience,updatetime=:updatetime,updateUser=:updateUser");
        stringBuilder.append(" where id=:id and company=:company");
        return super.put(stringBuilder.toString(),store);
    }

    public int deleteStore(HrRecruitmentStore store) {
        StringBuilder stringBuilder = new StringBuilder(" update hr_recruitment_store set");
        stringBuilder.append(" deletetime=:deletetime,deleteUser=:deleteUser,status=:status ");
        stringBuilder.append(" where id=:id");
        return super.put(stringBuilder.toString(),store);
    }

    private StringBuilder buildSql(HrRecruitmentStore entity){
        StringBuilder stringBuilder  = new StringBuilder("select s.*,c.plate plat from hr_recruitment_store s inner join hr_company c on c.id = s.company  ");
        stringBuilder.append(" where   s.status=0 and c.code REGEXP ");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        stringBuilder.append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        if (null!=entity.getStartDate() && null!=entity.getEndDate()){
            stringBuilder.append(" and s.createtime between :startDate and DATE_ADD(:endDate ,INTERVAL 1 DAY)");
        }
        return stringBuilder;
    }

    public Page<HrRecruitmentStore> listData(HrRecruitmentStore entity, Pageable pageable) {

        return super.get(buildSql(entity).toString(),entity,pageable);
    }

    public List<HrRecruitmentStore> listData(HrRecruitmentStore entity) {

        return super.get(buildSql(entity).toString(),entity);
    }

}
