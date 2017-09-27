package com.huayu.signWechat.dao;

import com.huayu.signWechat.domain.HrSignLeaveSon;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
@Repository
public class HrSignLeaveSonDao extends BasePageDao<HrSignLeaveSon, Serializable> {

    public int[] batchUpdate(SqlParameterSource[] sqlParameterSources1) {
        String sql = "insert into hr_sign_leave_son (`id`,`formmainid`,`leaveType`,`startLeave`,`endLeave`,`leaveDayCount`,`restChanged`,`notes`,`status`)" +
                " values(:id,:formmainid,:leaveType,:startLeave,:endLeave,:leaveDayCount,:restChanged,:notes,:status)";
        return super.batchUpdate(sql, sqlParameterSources1);
    }

    public List<HrSignLeaveSon> getTodayLeave(HrSignLeaveSon hrSignLeaveSon) {
        String sql = "SELECT id,formmainid,leaveType,startLeave,endLeave,leaveDayCount,restChanged,notes,createDate,`status` FROM hr_sign_leave_son WHERE createDate BETWEEN :startTime AND :endTime";
        return super.get(sql, hrSignLeaveSon);
    }

    public int[] batchUpdateLeave(SqlParameterSource[] sqlParameterSources) {
        String sql = "UPDATE hr_sign_leave_son SET startLeave = :startLeave ,endLeave = :endLeave WHERE id =:id";
        return super.batchUpdate(sql, sqlParameterSources);
    }
}
