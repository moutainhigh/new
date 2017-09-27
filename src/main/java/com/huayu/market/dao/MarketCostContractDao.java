package com.huayu.market.dao;

import com.huayu.market.domain.MarketCostContract;
import com.huayu.p.util.CommonUtil;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 营销合同审批表
 * @author ZXL 2017-07-27 15:49
 **/
@Repository
public class MarketCostContractDao extends BasePageDao<MarketCostContract,Serializable>{

    /**
     * 获取数据为台账
     * @param marketCostContract
     * @return
     */
    public MarketCostContract getOneOfLedger(MarketCostContract marketCostContract){
        String sql = "SELECT a.companyName,a.projectName,t.* FROM market_cost_contract t LEFT JOIN (SELECT t.projectId,t.`name` projectName,y.`name` companyName FROM market_cost_project t LEFT JOIN market_cost_company y ON t.companyId=y.companyId) a ON t.projectId=a.projectId WHERE `status`=2 AND contractId=:contractId";
        return super.getOne(sql,marketCostContract);
    }

    /**
     * 营销费用发生明细
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public List<MarketCostContract> getHappenDetailOfItemAndMonth(MarketCostContract marketCostContract){
        StringBuilder sql = new StringBuilder("SELECT aaa.contractId,aaa.contractCode,aaa.type,aaa.contractName,aaa.agentName,aaa.currentDate,aaa.money,bbb.companyName,bbb.projectName,ccc.secondName,ccc.threeName,aaa.contractMoney,aaa.applyDate FROM ");
        sql.append(" (SELECT * FROM (SELECT t.contractId,t.contractCode,1 type,t.contractName,t.agentName,l.threeCode,l.projectId,l.currentDate,l.money,t.contractMoney,t.applyDate FROM (SELECT * FROM market_cost_contract WHERE type=1 AND `status`=2) t LEFT JOIN market_cost_contract_split_detail l ON l.contractId=t.contractId) aa ");
        sql.append(" UNION ALL ");
        sql.append(" (SELECT a.contractId,a.contractCode,a.type,a.contractName,a.agentName,l.threeCode,l.projectId,l.happenDate currentDate,l.finalMoney money,a.contractMoney,a.applyDate FROM (SELECT CASE WHEN m.affirmId IS NULL THEN e.executeId ELSE m.affirmId END executeId,t.contractId,t.contractCode,2 type,t.contractName,t.agentName,CASE WHEN m.affirmId IS NULL THEN e.applyMoney ELSE m.applyMoney END contractMoney,t.applyDate FROM (SELECT * FROM market_cost_contract_execute WHERE `status`=2) e LEFT JOIN (SELECT * FROM market_cost_contract_affirm WHERE `status`=2) m ON e.executeId=m.executeId LEFT JOIN (SELECT * FROM market_cost_contract WHERE type=2 AND `status`=2) t ON e.contractId=t.contractId ORDER BY e.contractId ASC) a LEFT JOIN market_cost_contract_execute_detail l ON a.executeId=l.executeId) ");
        sql.append(" UNION ALL ");
        sql.append(" (SELECT a.payId contractId,a.serialsNumber contractCode,a.type,a.title contractName,a.applyName agentName,l.threeCode,l.projectId,l.currentDate,l.money,a.applyMoney contractMoney,a.applyDate FROM (SELECT * FROM market_cost_apply_pay WHERE type IN(3,4) AND `status` IN(2,4,5)) a LEFT JOIN market_cost_contract_split_detail l ON a.payId=l.contractId)) aaa ");
        sql.append(" LEFT JOIN (SELECT t.projectId,t.`name` projectName,y.`name` companyName FROM market_cost_project t LEFT JOIN market_cost_company y ON t.companyId=y.companyId) bbb ON aaa.projectId=bbb.projectId ");
        sql.append(" LEFT JOIN (SELECT a.code,b.`name` secondName,a.`name` threeName FROM (SELECT * FROM market_cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=2) b ON a.parentId=b.id) ccc ON aaa.threeCode=ccc.`code` ");
        sql.append(" WHERE DATE_FORMAT(aaa.currentDate,'%Y-%m')=:belongMonth AND ccc.`code`=:threeCode AND aaa.projectId=:projectId");
        return super.get(sql.toString(),marketCostContract);
    }

    /**
     * 台账列表
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public Page<MarketCostContract> getOfLedger(Pageable pageable,MarketCostContract marketCostContract){
        StringBuilder sql = new StringBuilder("SELECT t.*,a.companyName,a.projectName FROM (SELECT * FROM market_cost_contract WHERE `status`=2 ");
        if (StringUtils.isNotBlank(marketCostContract.getS1())){//合同编号/合同名称
            sql.append(" AND (INSTR(contractName,:s1)>0 OR INSTR(contractCode,:s1)>0) ");
        }
        if (StringUtils.isNotBlank(marketCostContract.getD3())&&StringUtils.isNotBlank(marketCostContract.getD4())) {//申请日期
            sql.append(" AND applyDate BETWEEN :d3 AND :d4 ");
        }
        if (CommonUtil.isInt(marketCostContract.getI1())){
            sql.append(" AND type=:i1 ");
        }
        sql.append(") t LEFT JOIN (SELECT t.projectId,t.`name` projectName,y.`name` companyName FROM market_cost_project t LEFT JOIN market_cost_company y ON t.companyId=y.companyId) a ON t.projectId=a.projectId");
        return super.get(sql.toString(),marketCostContract,pageable);
    }

    /**
     * 获取已支付金额
     * @param marketCostContract
     * @return
     */
    public MarketCostContract getOneToAllPayMoney(MarketCostContract marketCostContract){
        String sql = "SELECT IFNULL(SUM(money),0) paidMoney FROM cost_reimbursement_pay_detail WHERE payId=:payId AND `status`=0";
        return super.getOne(sql,marketCostContract);
    }

    /**
     * 更新已支付
     * @param marketCostContract
     * @return
     */
    public int putToPaidMoney(MarketCostContract marketCostContract){
        String sql = "UPDATE market_cost_contract SET paidMoney=paidMoney-:paidMoney+:payMoney WHERE contractId=:contractId";
        return super.put(sql,marketCostContract);
    }

    /*报表*/

    /**
     * 报表功能
     * 营销费用发生明细
     * @param pageable
     * @param marketCostContract
     * @return
     */
    public Page<MarketCostContract> getHappenDetailOfReport(Pageable pageable, MarketCostContract marketCostContract){
        StringBuilder sql = new StringBuilder("SELECT aaa.contractId,aaa.contractCode,aaa.type,aaa.contractName,aaa.agentName,aaa.currentDate,aaa.money,bbb.companyName,bbb.projectName,ccc.secondName,ccc.threeName FROM ");
        sql.append(" (SELECT * FROM (SELECT t.contractId,t.contractCode,1 type,t.contractName,t.agentName,l.threeCode,l.projectId,l.currentDate,l.money FROM (SELECT * FROM market_cost_contract WHERE type=1 AND `status`=2) t LEFT JOIN market_cost_contract_split_detail l ON l.contractId=t.contractId) aa ");
        sql.append(" UNION ALL ");
        sql.append(" (SELECT a.contractId,a.contractCode,a.type,a.contractName,a.agentName,l.threeCode,l.projectId,l.happenDate currentDate,l.finalMoney money FROM (SELECT CASE WHEN m.affirmId IS NULL THEN e.executeId ELSE m.affirmId END executeId,t.contractId,t.contractCode,2 type,t.contractName,t.agentName FROM (SELECT * FROM market_cost_contract_execute WHERE `status`=2) e LEFT JOIN (SELECT * FROM market_cost_contract_affirm WHERE `status`=2) m ON e.executeId=m.executeId LEFT JOIN (SELECT * FROM market_cost_contract WHERE type=2 AND `status`=2) t ON e.contractId=t.contractId ORDER BY e.contractId ASC) a LEFT JOIN market_cost_contract_execute_detail l ON a.executeId=l.executeId) ");
        sql.append(" UNION ALL ");
        sql.append(" (SELECT a.payId contractId,a.serialsNumber contractCode,a.type,a.title contractName,a.applyName agentName,l.threeCode,l.projectId,l.currentDate,l.money FROM (SELECT * FROM market_cost_apply_pay WHERE type IN(3,4) AND `status` IN(2,4,5)) a LEFT JOIN market_cost_contract_split_detail l ON a.payId=l.contractId)) aaa ");
        sql.append(" LEFT JOIN (SELECT t.projectId,t.`name` projectName,y.`name` companyName FROM market_cost_project t LEFT JOIN market_cost_company y ON t.companyId=y.companyId) bbb ON aaa.projectId=bbb.projectId ");
        sql.append(" LEFT JOIN (SELECT a.code,b.`name` secondName,a.`name` threeName FROM (SELECT * FROM market_cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM market_cost_item WHERE `level`=2) b ON a.parentId=b.id) ccc ON aaa.threeCode=ccc.`code` ");
        sql.append("  WHERE aaa.currentDate BETWEEN :d3 AND :d4 ");
        if (StringUtils.isNotBlank(marketCostContract.getS1())){
            sql.append(" AND INSTR(aaa.contractName,:s1)>0 ");
        }
        if (CommonUtil.isInt(marketCostContract.getI1())){
            sql.append(" AND aaa.type=:i1 ");
        }
        return super.get(sql.toString(),marketCostContract,pageable);
    }

    /*导入数据*/

    /**
     * 导入数据-自定义添加数据
     * @param marketCostContract
     * @return
     */
    public int definitionPostOfImport(MarketCostContract marketCostContract){
        String sql = "insert into market_cost_contract (`contractId`, `type`,`status`, `contractCode`, `contractName`, `aunit`, `aunitId`, `bunit`, `projectId`, `pid`, `contractMoney`,`agentUserId`, `agentName`, `applyDate`, `startDate`, `endDate`, `remark`, `isAutoSplit`, `months`) VALUES " +
                "(:contractId,:type,:status,:contractCode,:contractName,:aunit,:aunitId,:bunit,:projectId,:pid,:contractMoney,:agentUserId,:agentName,:applyDate,:startDate,:endDate,:remark,:isAutoSplit,:months)";
        return super.post(sql,marketCostContract);
    }

}
