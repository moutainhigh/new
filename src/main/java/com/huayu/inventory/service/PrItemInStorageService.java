package com.huayu.inventory.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.NumberUtil;
import com.huayu.inventory.dao.PrItemDeliveryDao;
import com.huayu.inventory.dao.PrItemInStorageDao;
import com.huayu.inventory.dao.PrItemInStorageListDao;
import com.huayu.inventory.dao.PrRepositoryPersonDao;
import com.huayu.inventory.domain.PrItemDelivery;
import com.huayu.inventory.domain.PrItemInStorage;
import com.huayu.inventory.domain.PrItemInStorageList;
import com.huayu.inventory.domain.PrRepositoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
@Service
public class PrItemInStorageService {

    @Autowired
    private PrItemInStorageListDao repositoryInStorageListDao;
    @Autowired
    private PrItemInStorageDao prItemInStorageDao;
    @Autowired
    private PrItemDeliveryDao itemDeliveryDao;
    @Autowired
    private CommSequenceDao commSequenceDao;
    @Autowired
    private PrRepositoryPersonDao repositoryPersonDao;

    /**
     * 根据ID数组查询收货列表
     * @param arrId
     * @return
     */
    public List<PrItemDelivery> getItemDeliveryByIds(Integer[] arrId) {
        List<PrItemDelivery> list = new ArrayList<>();
        for (Integer id:arrId) {
            PrItemDelivery itemDelivery = new PrItemDelivery();
            itemDelivery.setId(id);
            PrItemDelivery delivery = itemDeliveryDao.getItemDeliveryById(itemDelivery);
            BigDecimal inStorageSumNum = repositoryInStorageListDao.getInStorageSumNum(id);
            BigDecimal rejectedNum = delivery.getRejectedNum() == null ? new BigDecimal(0) : delivery.getRejectedNum();
            BigDecimal num = new BigDecimal((delivery.getNum().subtract(inStorageSumNum).subtract(rejectedNum))+"");
            delivery.setBalanceNum(num);
            delivery.setInStorageSumPrice(delivery.getBalanceNum().multiply(delivery.getPrice()).setScale(2, RoundingMode.HALF_UP));
//            delivery.setInStorageTax(delivery.getTaxRate().multiply(new BigDecimal("0.01")).multiply(delivery.getInStorageSumPrice()).setScale(2, RoundingMode.HALF_UP));
//            delivery.setInStorageExcTaxSumPrice(new BigDecimal(delivery.getInStorageSumPrice().subtract(delivery.getInStorageTax())+"").setScale(2, RoundingMode.HALF_UP));
            list.add(delivery);
        }
        return list;
    }

    /**
     * 提交入库
     * @param inStorage
     * @param list
     */
    @Transactional
    public Integer InStorageSubmit(PrItemInStorage inStorage, List<PrItemInStorageList> list) {
        Integer projectId = ConstantsHolder.getContext().getCurrDataId();
        inStorage.setProjectId(projectId);
        inStorage.setInStorageNum(this.getNextInStorageNo(inStorage));
        if (prItemInStorageDao.saveInStorage(inStorage)!=1){
            throw new BusinessException("提交入库失败");
        }

        //库管员存入repository_person中
        this.savePerson(inStorage, projectId);
        Integer size = list.size();
        Long[] ids = commSequenceDao.getKey(new CommSequence("pr_item_out_storage_list", size));
        for (int i = 0; i<size;i++){
            PrItemInStorageList inStorageList = list.get(i);
            Long key = ids[0] + i + 1;
            inStorageList.setId(key.intValue());
            inStorageList.setInStorageId(inStorage.getId());

            //查询该inStorageList对应的收货数量
            Integer deliveryId = inStorageList.getDeliveryId();
            PrItemDelivery one = itemDeliveryDao.getItemDeliveryById(deliveryId);
            if (one == null ) {
                throw new BusinessException("获取收货数据失败");
            }
            //总已入库数量
            BigDecimal inStorageSumNum = repositoryInStorageListDao.getInStorageSumNum(deliveryId);
            //已退货量
            BigDecimal rejectedNum = one.getRejectedNum() == null ? new BigDecimal(0) : one.getRejectedNum();
            BigDecimal number = one.getNum().subtract(inStorageSumNum).subtract(inStorageList.getNum()).subtract(rejectedNum);
            if (number.compareTo(new BigDecimal(0)) == 1) {
                //部分入库--修改收货表的inStorageStatus状态,'入库状态 0未入库 1部分入库  2全部入库',
                itemDeliveryDao.updateDeliveryInStorageStatus(inStorageList.getDeliveryId(), 1);
            } else if (number.compareTo(new BigDecimal(0)) == 0) {
                //全部入库
                itemDeliveryDao.updateDeliveryInStorageStatus(inStorageList.getDeliveryId(), 2);
            }
            if (repositoryInStorageListDao.post(inStorageList)!=1){
                throw new BusinessException("添加入库详情失败");
            }
        }
        return inStorage.getId();
    }

    @Transactional
    public String getNextInStorageNo(PrItemInStorage inStorage) {
        Long key = prItemInStorageDao.getInStorageCount(inStorage);
        return "RK"+ DateTimeUtil.dateTimeToString("yyyyMMdd")+ NumberUtil.buildNum(key.intValue(),6);
    }

    @Transactional
    public void savePerson(PrItemInStorage itemInStorage, Integer projectId){
        PrRepositoryPerson person;
        if ("-1".equals(itemInStorage.getRepositoryPerson())){
            person = new PrRepositoryPerson();
            person.setType(1);
            person.setName(itemInStorage.getInStoreUser());
            person.setProjectId(projectId);
            if (repositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存库管员失败");
            }
        }else{
            itemInStorage.setInStoreUser(itemInStorage.getRepositoryPerson());
        }
    }

    public Page<PrItemInStorage> listData(PrItemInStorage inStorage, Pageable pageable) {
        return prItemInStorageDao.listData(inStorage, pageable);
    }

    public List<PrItemInStorageList> showInStorageLists(Integer inStorageId) {
        return repositoryInStorageListDao.getStorageListByInStorageId(inStorageId);
    }

    public PrItemInStorage getInStorageByid(Integer inStorageId) {
        return prItemInStorageDao.getInStorageById(inStorageId);
    }

    public PrItemInStorageList getItemInStorageListByDeliveryId(Integer deliveryId) {
        PrItemInStorageList prItemInStorageList= repositoryInStorageListDao.getStorageListByDeliverId(deliveryId);
        PrItemDelivery itemDeliveryById = itemDeliveryDao.getItemDeliveryById(deliveryId);
        PrItemInStorageList itemInStorageList = new PrItemInStorageList();
        if (prItemInStorageList != null ) {
            //有入库记录
            if (prItemInStorageList.getBalanceNum() != null && prItemInStorageList.getBalanceNum().intValue() != 0) {
                itemInStorageList.setBalanceNum(prItemInStorageList.getBalanceNum());
            } else {
                itemInStorageList.setBalanceNum(itemDeliveryById.getNum());
            }
        } else {
            itemInStorageList.setBalanceNum(itemDeliveryById.getNum());
        }
        return itemInStorageList;
    }

    public List<PrItemInStorage> getInStorages() {
        return prItemInStorageDao.getInStorages();
    }

    @Transactional
    public void updateInStorage(PrItemInStorage inStorage) {
        this.savePerson(inStorage, ConstantsHolder.getContext().getCurrDataId());
        if (prItemInStorageDao.updateInStorage(inStorage)!=1){
            throw new BusinessException("修改入库单失败");
        }
    }

    @Transactional
    public void updateInStorageDetail(PrItemInStorageList detail) {
        if (repositoryInStorageListDao.updateInStorageDetail(detail)!=1){
            throw new BusinessException("修改入库明细失败");
        }
    }
}
