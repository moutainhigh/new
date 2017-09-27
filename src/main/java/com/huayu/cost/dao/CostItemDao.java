package com.huayu.cost.dao;

import com.huayu.cost.domain.CostItem;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 费用项目（科目）
 * @author ZXL 2017-07-07 10:01
 **/
@Repository
public class CostItemDao extends BasePageDao<CostItem,Serializable>{

    /**
     * 获取初始化数据
     * @param costItem
     * @return
     */
    public List<CostItem> getDateToInitialize(CostItem costItem){
        String sql = "SELECT a.code,b.code secondCode,c.code firstCode FROM (SELECT * FROM cost_item WHERE `level`=3) a LEFT JOIN (SELECT * FROM cost_item WHERE `level`=2) b ON a.parentId=b.id LEFT JOIN (SELECT * FROM cost_item WHERE `level`=1) c ON b.parentId=c.id";
        return super.get(sql,costItem);
    }

    /**
     * 获取待补充的数据
     * @param costItem
     * @return
     */
    public List<CostItem> getDateToSupplement(CostItem costItem){
        String sql = "SELECT * FROM (SELECT a.code,b.code secondCode,c.code firstCode FROM (SELECT * FROM cost_item WHERE `level`=3) a LEFT JOIN " +
                "(SELECT * FROM cost_item WHERE `level`=2) b ON a.parentId=b.id LEFT JOIN (SELECT * FROM cost_item WHERE `level`=1) c ON b.parentId=c.id) aa " +
                "WHERE NOT EXISTS(SELECT * FROM cost_department_detail bb WHERE bb.companyId=:companyId AND bb.departmentId=:departmentId AND bb.`year`=:year AND aa.`code`=bb.`code`)";
        return super.get(sql,costItem);
    }

}
