package com.huayu.cost.dao;

import com.huayu.cost.domain.CostDepartmentDetail;
import com.ly.dao.base.BasePageDao;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 部门费用预算详细表
 * @author ZXL 2017-07-07 10:10
 **/
@Repository
public class CostDepartmentDetailDao extends BasePageDao<CostDepartmentDetail,Serializable> {

    /**
     * 批量添加数据
     * @param costDepartmentDetails
     * @return
     */
    public int[] batchPost(List<CostDepartmentDetail> costDepartmentDetails){
        String sql = "INSERT INTO cost_department_detail (ddId,code,companyId,departmentId,year,firstCode,secondCode,planMoney,freezeMoney,usedMoney,usableMoney,createDate,did,planMoney2,planMoney3,planMoney4,planMoney5,planMoney6,planMoney7,planMoney8,planMoney9,planMoney10,planMoney11,planMoney12) VALUES (:ddId,:code,:companyId,:departmentId,:year,:firstCode,:secondCode,:planMoney,:freezeMoney,:usedMoney,:usableMoney,:createDate,:did,:planMoney2,:planMoney3,:planMoney4,:planMoney5,:planMoney6,:planMoney7,:planMoney8,:planMoney9,:planMoney10,:planMoney11,:planMoney12)";
        return super.batchUpdate(sql, SqlParameterSourceUtils.createBatch(costDepartmentDetails.toArray()));
    }

}
