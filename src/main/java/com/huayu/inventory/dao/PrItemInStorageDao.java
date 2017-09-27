package com.huayu.inventory.dao;


import com.huayu.common.ConstantsHolder;
import com.huayu.inventory.domain.PrItemInStorage;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
@Repository
public class PrItemInStorageDao extends BasePageDao<PrItemInStorage, Serializable> {

    public Page<PrItemInStorage> listData(PrItemInStorage inStorage, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT s.id, s.inStorageNum, s.inStoreDate, s.billNum, d.itemProviderName, r.`name` repositoryName, s.inStoreUser repositoryPersonName,s.remark,s.billReceiver,s.billReceiveDate ");
        stringBuilder.append(" FROM pr_item_in_storage s");
        stringBuilder.append(" INNER JOIN pr_item_in_storage_list sl ON sl.inStorageId = s.id ");
        stringBuilder.append(" INNER JOIN pr_item_delivery d ON d.id = sl.deliveryId ");
        stringBuilder.append(" INNER JOIN pr_material m ON d.materialId = m.id ");
        stringBuilder.append(" INNER JOIN pr_material_specification pms ON pms.id = d.specificationId and pms.status=0 ");
        stringBuilder.append(" INNER JOIN pr_repository r ON d.repositoryId = r.id ");
        stringBuilder.append(" WHERE s.projectId =").append(ConstantsHolder.getContext().getCurrDataId());
        if (StringUtils.isNotBlank(inStorage.getInStorageNum())) {
            stringBuilder.append(" and  position(:inStorageNum in s.inStorageNum) ");
        }

        if (StringUtils.isNotBlank(inStorage.getBillNum())) {
            stringBuilder.append(" and s.billNum = :billNum");
        }

        if (StringUtils.isNotBlank(inStorage.getMaterialName())){
            stringBuilder.append(" AND  position( :materialName in m.name)");
        }

        if (StringUtils.isNotBlank(inStorage.getSpecification())){
            stringBuilder.append(" AND  position( :specification in pms.specification)");
        }

        if (StringUtils.isNotBlank(inStorage.getItemProviderName())) {
            stringBuilder.append(" and POSITION( :itemProviderName in d.itemProviderName)");
        }

        if (inStorage.getRepositoryId() != null && inStorage.getRepositoryId() != -1) {
            stringBuilder.append(" and r.id = "+inStorage.getRepositoryId());
        }

        if (inStorage.getStartTime() != null && inStorage.getEndTime() != null) {
            stringBuilder.append(" and s.inStoreDate BETWEEN :startTime and :endTime ");
        }

        stringBuilder.append(" GROUP BY s.id ORDER BY s.id desc");
        Page<PrItemInStorage> inStorages = super.get(stringBuilder.toString(),inStorage,pageable);
        return inStorages;
    }

    public PrItemInStorage getInStorageById(Integer inStorageId) {
        String sql = "SELECT s.id, s.inStorageNum, s.billNum,s.inStoreUser, d.itemProviderName, s.inStoreDate, s.remark, r.name repositoryName,s.billReceiver,s.billReceiveDate FROM" +
                " pr_item_in_storage s INNER JOIN pr_item_in_storage_list sl ON sl.inStorageId = s.id INNER JOIN" +
                " pr_item_delivery d ON d.id = sl.deliveryId INNER JOIN pr_repository r" +
                " ON r.id = d.repositoryId WHERE s.id = "+inStorageId+" GROUP BY s.id";
        return super.getOne(sql,new PrItemInStorage());
    }

    public int saveInStorage(PrItemInStorage inStorage) {
        Long storageId = this.getKey("pr_item_in_storage", new PrItemInStorage());
        inStorage.setId(storageId.intValue());
        inStorage.setCreatetime(new Date());
        return super.post(inStorage);
    }

    public List<PrItemInStorage> getInStorages() {
        String sql = "SELECT * FROM pr_item_in_storage GROUP BY inStoreUser";
        return super.get(sql, new PrItemInStorage());
    }

    public Long getInStorageCount(PrItemInStorage inStorage) {
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(t.id) FROM (SELECT i.id FROM pr_item_in_storage i INNER JOIN pr_item_in_storage_list l ON l.inStorageId = i.id INNER JOIN pr_item_delivery d ON l.deliveryId = d.id WHERE d.repositoryId =:repositoryId GROUP BY i.id ) t ");
        return super.getLong(stringBuilder.toString(),inStorage);
    }

    public int saveBillReceiveInfo(PrItemInStorage prItemInStorage) {
        String sql = "update pr_item_in_storage set billReceiver=:billReceiver,billReceiveDate=:billReceiveDate,updatetime=:updatetime,updateUser=:updateUser where  id=:id and projectId = "+ConstantsHolder.getContext().getCurrDataId();
        return super.put(sql,prItemInStorage);
    }

    public int updateInStorage(PrItemInStorage inStorage) {
        String sql  = "update pr_item_in_storage set inStoreUser=:inStoreUser,inStoreDate=:inStoreDate,billNum=:billNum,remark=:remark where id=:id and inStorageNum=:inStorageNum";
        return super.put(sql,inStorage);
    }
}
