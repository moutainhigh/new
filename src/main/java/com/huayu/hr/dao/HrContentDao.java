package com.huayu.hr.dao;

import com.huayu.hr.domain.HrRecruitmentContent;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2016/12/28.
 */
@Repository
public class HrContentDao extends BasePageDao<HrRecruitmentContent,Integer>{

    public Page<HrRecruitmentContent> getContentList(HrRecruitmentContent content, Pageable pageable){
        StringBuilder sql = new StringBuilder("select rc.id,rc.title,rc.shortText,rc.imgUrl,rc.allText,rc.sort,rc.status,rc.addr,rc.type,rc.plateId,rc.area,rc.count,rc.createtime,rc.updatetime,rc.createUser ");
        sql.append(" FROM hr_recruitment_content rc WHERE");
        if (null==content.getStatus()){
            sql.append("  rc.status<2");
        }else {
            sql.append("  rc.status=:status ");
        }
        if (null!=content.getArea()){
            sql.append(" and rc.area=:area");
        }
        if (null!=content.getType()){
            sql.append(" and rc.type=:type");
        }
        if (null!=content.getPlateId()){
            sql.append(" and rc.plateId = :plateId ");
        }
        return super.get(sql.toString(),content,pageable);
    }

    public int addContent(HrRecruitmentContent content) {
        Long longKey = super.getKey("hr_recruitment_content",content);
        content.setId(longKey.intValue());
        return super.post(content);
    }

    public int updateContent(HrRecruitmentContent content) {
        String sql = "update hr_recruitment_content set title=:title,shortText=:shortText,imgUrl=:imgUrl,allText=:allText,sort=:sort,addr=:addr,type=:type,area=:area,count=:count,status=:status,updatetime=:updatetime where  id=:id";
        return super.put(sql,content);
    }

    public int delContent(HrRecruitmentContent content) {
        String sql = "update hr_recruitment_content set status=:status where id=:id";
        return super.put(sql,content);
    }

    public List<HrRecruitmentContent> getContentList(HrRecruitmentContent content) {
        String sql = "select id,title,shortText,imgUrl,allText,sort,status,addr,type,plateId,area,createtime,updatetime,createUser,count from hr_recruitment_content where status=:status and plateId=:plateId and area=:area";
        return super.get(sql,content);
    }

    public List<HrRecruitmentContent> getContentTitleList(HrRecruitmentContent content) {
        String sql = "select id,title from hr_recruitment_content where status=:status  and plateId=:plateId and area = :area";
        return super.get(sql,content);
    }
}
