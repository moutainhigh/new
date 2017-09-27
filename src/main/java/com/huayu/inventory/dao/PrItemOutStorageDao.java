package com.huayu.inventory.dao;

import com.huayu.common.ConstantsHolder;
import com.huayu.inventory.domain.PrItemOutStorage;
import com.ly.dao.base.BasePageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by DengPeng on 2017/6/19.
 */
@Repository
public class PrItemOutStorageDao extends BasePageDao<PrItemOutStorage,Integer>{

    public int postData(PrItemOutStorage storage) {
        Long key = super.getKey("pr_item_out_storage",storage);
        storage.setId(key.intValue());
        return super.post(storage);
    }

    public int updateData(PrItemOutStorage storage) {
        StringBuilder stringBuilder = new StringBuilder("update pr_item_out_storage");
        stringBuilder.append(" set checkUser=:checkUser,outStorageDate=:outStorageDate,projectHouseNum=:projectHouseNum,itemReceiverUnit=:itemReceiverUnit,itemReceiver=:itemReceiver,remark=:remark");
        stringBuilder.append(" where id=:id and outStorageNo=:outStorageNo");
        return super.put(stringBuilder.toString(),storage);
    }

    public Page<PrItemOutStorage> getOutStorageListData(PrItemOutStorage outStorage, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT os.id, os.outStorageNo, os.outStorageDate, p.`name` projectName, os.projectHouseNum, r.`name` repositoryName,os.checkUser, os.remark,os.billReceiver,os.billReceiveDate ");
        stringBuilder.append(" FROM pr_item_out_storage os ");
        stringBuilder.append(" INNER JOIN pr_item_out_storage_list ol ON os.id = ol.outStorageId");
        stringBuilder.append(" INNER JOIN pr_material m ON ol.materialId = m.id ");
        stringBuilder.append(" INNER JOIN pr_material_specification pms ON pms.id = ol.specificationId and pms.status=0 ");
        stringBuilder.append(" INNER JOIN pr_project p ON os.projectId = p.id");
        stringBuilder.append(" INNER JOIN pr_repository r ON os.repositoryId = r.id");
        stringBuilder.append(" WHERE os.projectId = ").append(ConstantsHolder.getContext().getCurrDataId());
        if (StringUtils.isNotBlank(outStorage.getOutStorageNo())) {
            stringBuilder.append(" AND position( :outStorageNo in os.outStorageNo) ");
        }

        if (StringUtils.isNotBlank(outStorage.getMaterialName())){
            stringBuilder.append(" AND  position( :materialName in m.name)");
        }

        if (StringUtils.isNotBlank(outStorage.getSpecification())){
            stringBuilder.append(" AND  position( :specification in pms.specification)");
        }

        if (null !=outStorage.getCheckUser() && !outStorage.getCheckUser().equals("-1")) {
            stringBuilder.append(" and os.checkUser = :checkUser");
        }

        if (null !=outStorage.getItemReceiver() && !outStorage.getItemReceiver().equals("-1")) {
            stringBuilder.append(" and os.itemReceiver = :itemReceiver");
        }

        if (outStorage.getRepositoryId() != null && outStorage.getRepositoryId() != -1) {
            stringBuilder.append(" and r.id = :repositoryId");
        }

        if (outStorage.getStartTime() != null && outStorage.getEndTime() != null) {
            stringBuilder.append(" and os.outStorageDate >= :startTime AND os.outStorageDate <=:endTime");
        }

        stringBuilder.append(" GROUP BY os.id");
        Page<PrItemOutStorage> prItemOutStorages = super.get(stringBuilder.toString(), outStorage, pageable);
        return prItemOutStorages;
    }


    public PrItemOutStorage getOutStorageDetail(PrItemOutStorage prItemOutStorage) {
        String sql = "SELECT os.id, os.outStorageNo, os.outStorageDate, r. NAME repositoryName, os.checkUser," +
                " p. NAME projectName, os.projectHouseNum, os.itemReceiver, os.itemReceiverUnit, os.remark,os.repositoryId,os.billReceiver,os.billReceiveDate" +
                " FROM pr_item_out_storage os " +
                "INNER JOIN pr_project p ON os.projectId = p.id " +
                "INNER JOIN pr_repository r ON os.repositoryId = r.id " +
                "WHERE os.id = :id and os.projectId ="+ConstantsHolder.getContext().getCurrDataId();
        return super.getOne(sql,prItemOutStorage);
    }

    public Long getOutStorageCount(PrItemOutStorage prItemOutStorage) {
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(1) FROM pr_item_out_storage WHERE repositoryId = :repositoryId");
        return super.getLong(stringBuilder.toString(),prItemOutStorage);
    }

    public int saveBillReceiveInfo(PrItemOutStorage outStorage) {
        String sql = "update pr_item_out_storage set billReceiver=:billReceiver,billReceiveDate=:billReceiveDate,updatetime=:updatetime,updateUser=:updateUser where id=:id and projectId = "+ConstantsHolder.getContext().getCurrDataId();
        return super.put(sql,outStorage);
    }
}
