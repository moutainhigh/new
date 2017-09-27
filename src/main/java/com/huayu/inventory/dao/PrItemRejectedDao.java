package com.huayu.inventory.dao;

import com.huayu.inventory.domain.PrItemRejected;
import com.huayu.inventory.web.args.PrItemRejectedArgs;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/20.
 */
@Repository
public class PrItemRejectedDao extends BasePageDao<PrItemRejected,Integer> {

    public int postItemRejected(PrItemRejected prItemRejected) {
        Long key = super.getKey("pr_item_rejected", prItemRejected);
        prItemRejected.setId(key.intValue());
        return super.post(prItemRejected);
    }

    public Page<PrItemRejected> getRejectedListData(PrItemRejectedArgs itemRejected, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT m.`name` materialName, ms.specification, cd.dictValue unit, r.`name` repositoryName, ir.returnedUser," +
                " ir.returnedDate, ir.num, ir.price, ir.sumPrice, ir.tax, ir.excTaxSumPrice, ir.reason FROM pr_item_rejected ir" +
                " INNER JOIN pr_material m ON m.id = ir.materialId INNER JOIN pr_item_delivery d ON d.id = ir.deliveryId" +
                " INNER JOIN pr_repository r ON r.id = ir.repositoryId INNER JOIN pr_material_specification ms" +
                " ON ms.id = d.specificationId INNER JOIN common_dict cd ON cd.dictKey = m.unit");
        if (StringUtils.isNotBlank(itemRejected.getMaterialName())) {
            sb.append(" and m.`name` = :materialName");
        }
        if (itemRejected.getStartTime() != null && itemRejected.getEndTime() != null) {
            sb.append(" and ir.returnedDate >= :startTime AND ir.returnedDate <=:endTime");
        }
        Page<PrItemRejected> prItemRejecteds = super.get(sb.toString(), itemRejected, pageable);
        return prItemRejecteds;
    }
}
