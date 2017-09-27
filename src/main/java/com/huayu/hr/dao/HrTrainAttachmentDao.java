package com.huayu.hr.dao;

import com.huayu.hr.domain.HrTrainAttachment;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/1/18.
 */
@Repository
public class HrTrainAttachmentDao extends BasePageDao<HrTrainAttachment,Integer> {


    @Override
    public int post(HrTrainAttachment attachment) {
        Long longKey = super.getKey("hr_train_attachment",attachment);
        attachment.setId(longKey.intValue());
        attachment.setCreatetime(new Date());
        return super.post(attachment);
    }

    public List<HrTrainAttachment> getAttachmentList(HrTrainAttachment attach) {
        String sql  = "select id,trainId,attachUrl,sourceName from hr_train_attachment where trainId=:trainId and status=:status";
        return super.get(sql,attach);
    }

    public int delAttachment(HrTrainAttachment attach) {
        String sql  = "update  hr_train_attachment set status=:status where id=:id and  trainId=:trainId";
        return super.put(sql,attach);
    }
}
