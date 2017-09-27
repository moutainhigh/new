package com.huayu.inventory.service;

import com.alibaba.fastjson.JSON;
import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.NumberUtil;
import com.huayu.inventory.dao.*;
import com.huayu.inventory.domain.*;
import com.huayu.inventory.web.args.PrItemDeliveryArgs;
import com.huayu.inventory.web.args.PrItemRejectedArgs;
import com.huayu.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by DengPeng on 2017/6/13.
 */
@Service
public class PrStorageService {

    private static Logger logger = LoggerFactory.getLogger(PrStorageService.class);

    @Autowired
    private PrRepositoryPersonDao prRepositoryPersonDao;
    @Autowired
    private PrItemDeliveryDao prItemDeliveryDao;
    @Autowired
    private PrItemOutStorageDao prItemOutStorageDao;
    @Autowired
    private PrItemOutStorageListDao prItemOutStorageListDao;
    @Autowired
    private CommSequenceDao commSequenceDao;
    @Autowired
    private PrItemStorageSumDao prItemStorageSumDao;
    @Autowired
    private PrItemRejectedDao prItemRejectedDao;
    @Autowired
    private PrItemInStorageListDao prItemInStorageListDao;
    @Autowired
    private PrItemStatisticsDao prItemStatisticsDao;
    @Autowired
    private PrItemBalanceDao prItemBalanceDao;
    @Autowired
    private PrItemInStorageDao prItemInStorageDao;


    public List<PrRepositoryPerson> getAllPerson(Integer projectId, Integer type){
        PrRepositoryPerson person = new PrRepositoryPerson();
        person.setProjectId(projectId);
        person.setType(type);
        return prRepositoryPersonDao.getAllPerson(person);
    }

    /**
     * 收货
     * @param delivery
     */
    @Transactional
    public void insertReceipt(PrItemDelivery delivery) {
        Integer projectId = ConstantsHolder.getContext().getCurrDataId();
        this.savePerson(delivery,projectId);
        User user = SpringSecurityUtil.getUser();
        delivery.setRejectedNum(new BigDecimal(0));
        delivery.setStatus(0);
        delivery.setInStorageStatus(0);
        delivery.setCreatetime(new Date());
        delivery.setCreateUser(user.getUserId().intValue());
        if (prItemDeliveryDao.postData(delivery)!=1){
            throw new BusinessException("添加收货记录失败");
        }
    }

    /**
     * 添加 更新库存信息
     * @param delivery
     */
    @Deprecated
    @Transactional
    public void insertOrUpdateStorageSum(PrItemDelivery delivery){
        PrItemStorageSum entity = new PrItemStorageSum(delivery.getMaterialId(),delivery.getSpecificationId(),delivery.getRepositoryId());
        PrItemStorageSum oldSum = prItemStorageSumDao.getItemStorageSum(new PrItemStorageSum(delivery.getMaterialId(),delivery.getSpecificationId(),delivery.getRepositoryId()));
        if (oldSum == null) {
            entity.setSumPrice(delivery.getSumPrice());
            entity.setExcTaxSumPrice(delivery.getExcTaxSumPrice());
            entity.setNum(delivery.getNum());
            entity.setSupplySum(delivery.getNum());
            if (prItemStorageSumDao.post(entity)!=1){
                throw new BusinessException("添加库存失败");
            }
        } else {
            entity.setSumPrice( oldSum.getSumPrice().add(delivery.getSumPrice()));
            entity.setExcTaxSumPrice(oldSum.getExcTaxSumPrice().add(delivery.getExcTaxSumPrice()));
            entity.setNum(oldSum.getNum().add(delivery.getNum()));
            entity.setSupplySum(oldSum.getSupplySum().add(delivery.getNum()));
            entity.setId(oldSum.getId());
            if(prItemStorageSumDao.putItemStorageSum(entity)!=1){
                throw new BusinessException("修改库存失败");
            }
        }
    }

    @Transactional
    public void savePerson(PrItemDelivery delivery, Integer projectId){
        PrRepositoryPerson person;
        if (delivery.getReceiptUser() == -1){
            person = new PrRepositoryPerson();
            person.setType(3);
            person.setName(delivery.getReceiptUserName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存收货人失败");
            }
        }

        if (delivery.getCheckUser() == -1){
            person = new PrRepositoryPerson();
            person.setType(4);
            person.setName(delivery.getCheckUserName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存验货人失败");
            }
        }

        if (delivery.getItemProvider() == -1){
            person = new PrRepositoryPerson();
            person.setType(2);
            person.setName(delivery.getItemProviderName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存供货商失败");
            }
        }
        if (null!=delivery.getNoteId() && delivery.getNoteId() == -1){
            person = new PrRepositoryPerson();
            person.setType(11);
            person.setName(delivery.getNote());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存依据失败");
            }
        }
    }

    /**
     * 更新收货记录
     * @param delivery
     */
    @Transactional
    public void updateReceipt(PrItemDelivery delivery) {
        Integer projectId = ConstantsHolder.getContext().getCurrDataId();
        if (this.checkInStorage(delivery.getId())){
            throw new BusinessException("该材料已经入库，不能修改");
        }
        PrItemDelivery oldReceiptOne = prItemDeliveryDao.getReceiptOne(delivery);
        PrItemOutStorageList oldDetailOne = new PrItemOutStorageList();
        oldDetailOne.setRepositoryId(delivery.getRepositoryId());
        oldDetailOne.setMaterialId(delivery.getMaterialId());
        oldDetailOne.setSpecificationId(delivery.getSpecificationId());
        oldDetailOne.setDeliveryId(delivery.getId());
        PrItemOutStorageList outStorageSum = prItemOutStorageListDao.getOutStorageSum(oldDetailOne);//出库合计
        if (null != outStorageSum){
            BigDecimal leaveStore = delivery.getNum().subtract(outStorageSum.getNum()).add(delivery.getNum()).subtract(oldReceiptOne.getNum());
            if (leaveStore.compareTo(BigDecimal.ZERO)<0){
                throw new BusinessException("收货数量不能低于已出库数量");
            }
            BigDecimal leaveMoney = delivery.getSumPrice().subtract(outStorageSum.getSumPrice()).add(delivery.getSumPrice()).subtract(oldReceiptOne.getSumPrice());
            if (leaveMoney.compareTo(BigDecimal.valueOf(-1))<0){
                throw new BusinessException("收货金额不能低于已出库金额");
            }

            BigDecimal leaveExcTaxSumPrice = delivery.getExcTaxSumPrice().subtract(outStorageSum.getExcTaxSumPrice()).add(delivery.getExcTaxSumPrice()).subtract(oldReceiptOne.getExcTaxSumPrice());
            if (leaveExcTaxSumPrice.compareTo(BigDecimal.valueOf(-1))<0){
                throw new BusinessException("收货不含税金额不能低于已出库不含税总金额");
            }
        }

        savePerson(delivery,projectId);
        User user = SpringSecurityUtil.getUser();
        delivery.setUpdateUser(user.getUserId().intValue());
        delivery.setUpdatetime(new Date());
        if (prItemDeliveryDao.updateReceipt(delivery)!=1){
            throw new BusinessException("修改收货记录失败");
        }

    }

    public boolean checkInStorage(int id){

        return null == prItemInStorageListDao.getStorageListByDeliverId(id)?false:true;
    }

    public boolean checkOutStorage(int id){
        PrItemOutStorageList outStorageList = new PrItemOutStorageList();
        outStorageList.setDeliveryId(id);
        return null == prItemOutStorageListDao.getOutStorageByDelivery(outStorageList)?false:true;
    }

    /**
     * 收货数据
     * @param delivery
     * @param pageable
     * @return
     */
    public Page<PrItemDelivery> receiptListData(PrItemDeliveryArgs delivery, Pageable pageable) {

        return prItemDeliveryDao.getReceiptListData(delivery,pageable);
    }

    /**
     * 获取单个收货记录
     * @param id
     * @return
     */
    public PrItemDelivery getReceiptOne(Integer id) {
        PrItemDelivery delivery = new PrItemDelivery();
        delivery.setId(id);
        delivery.setStatus(0);
        return prItemDeliveryDao.getReceiptOne(delivery);
    }

    /**
     * 删除收货记录
     * @param delivery
     */
    @Transactional
    public void deleteReceipt(PrItemDeliveryArgs delivery){
        if (this.checkInStorage(delivery.getId())){
            throw new BusinessException("该材料已有入库信息不能删除");
        }
        if (this.checkOutStorage(delivery.getId())){
            throw new BusinessException("该材料已有出库信息不能删除");
        }
        if (prItemDeliveryDao.deleteReceipt(delivery)!=1){
            throw new BusinessException("删除失败");
        }
    }


    /**
     * 出库
     * @param outStorage
     */
    @Transactional
    public void postOutStorage(PrItemOutStorage outStorage){

        //保存对应的person到repository_person中
        Integer projectId = ConstantsHolder.getContext().getCurrDataId();
        this.savePerson(outStorage, projectId);

        User user = SpringSecurityUtil.getUser();
        Date date = new Date();
        outStorage.setProjectId(ConstantsHolder.getContext().getCurrDataId());
        outStorage.setCreatetime(date);
        outStorage.setCreateUser(user.getUserId().intValue());

        String nextOutStorageNo = this.getNextOutStorageNo(outStorage);
        outStorage.setOutStorageNo(nextOutStorageNo);
        if (prItemOutStorageDao.postData(outStorage)!=1){
            throw new BusinessException("出库失败");
        }
        List<PrItemOutStorageList> list = JSON.parseArray(outStorage.getListStr(), PrItemOutStorageList.class);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("缺少出库材料！");
        }
        Integer size = list.size();
        Long[] ids = commSequenceDao.getKey(new CommSequence("pr_item_out_storage_list", size));
        SqlParameterSource[] sqlParameterSource = new SqlParameterSource[size];
        for (int i = 0; i<size;i++){
            PrItemOutStorageList entity = list.get(i);
            PrItemDelivery delivery = prItemDeliveryDao.getItemDeliveryById(entity.getDeliveryId());
            entity.setMaterialId(delivery.getMaterialId());
            entity.setSpecificationId(delivery.getSpecificationId());
            entity.setRepositoryId(delivery.getRepositoryId());
            if ("-1".equals(outStorage.getUseStr())){
                entity.setUse(outStorage.getUse());
            }else {
                entity.setUse(outStorage.getUseStr());
            }
            PrItemOutStorageList outStorageSum = prItemOutStorageListDao.getOutStorageSum(entity);
            if (null!=outStorageSum){
                BigDecimal leaveStore = delivery.getNum().subtract(outStorageSum.getNum()).subtract(entity.getNum());
                if (leaveStore.doubleValue()<0){
                    throw new BusinessException("库存不足");
                }
                BigDecimal leaveMoney = delivery.getSumPrice().subtract(outStorageSum.getSumPrice()).subtract(entity.getSumPrice());
                if (leaveMoney.doubleValue()<-1){
                    throw new BusinessException("超出最大出库总金额");
                }
            }
            Long key = ids[0] + i + 1;
            entity.setId(key.intValue());
            entity.setDeliveryId(delivery.getId());
            entity.setTaxRate(delivery.getTaxRate());
            entity.setOutStorageId(outStorage.getId());
//            entity.setBalanceNum(delivery.getNum().subtract(null==outStorageSum?BigDecimal.ZERO:outStorageSum.getNum()));
//            entity.setBalanceSumPrice(delivery.getSumPrice().subtract(null==outStorageSum?BigDecimal.ZERO:outStorageSum.getSumPrice()));
//            entity.setBalanceExcTaxSumPrice(delivery.getExcTaxSumPrice().subtract(null==outStorageSum?BigDecimal.ZERO:outStorageSum.getExcTaxSumPrice()));
//            entity.setTax(entity.getSumPrice().subtract(entity.getExcTaxSumPrice()));
            sqlParameterSource[i] = new BeanPropertySqlParameterSource(entity);
        }
        prItemOutStorageListDao.batchUpdate(sqlParameterSource);
    }

    /**
     * 修改出库
     * @param outStorage
     */
    @Transactional
    public void updateOutStorage(PrItemOutStorage outStorage) {
        Integer projectId = ConstantsHolder.getContext().getCurrDataId();
        this.savePerson(outStorage, projectId);
        User user = SpringSecurityUtil.getUser();
        Date date = new Date();
        outStorage.setUpdateUser(user.getUserId().intValue());
        outStorage.setUpdatetime(date);
        if (prItemOutStorageDao.updateData(outStorage)!=1){
            throw new BusinessException("修改出库失败");
        }
    }

    @Transactional
    public void savePerson(PrItemOutStorage outStorage, Integer projectId){
        PrRepositoryPerson person;
        if ("-1".equals(outStorage.getCheckUser())){
            person = new PrRepositoryPerson();
            person.setType(1);
            person.setName(outStorage.getCheckUserName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存库管员失败");
            }
            outStorage.setCheckUser(outStorage.getCheckUserName());
        }

        if ("-1".equals(outStorage.getItemReceiver())){
            person = new PrRepositoryPerson();
            person.setType(7);
            person.setName(outStorage.getItemReceiverName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存领用人失败");
            }
            outStorage.setItemReceiver(outStorage.getItemReceiverName());
        }

        if ("-1".equals(outStorage.getItemReceiverUnit())){
            person = new PrRepositoryPerson();
            person.setType(8);
            person.setName(outStorage.getItemReceiverUnitName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存领用单位失败");
            }
            outStorage.setItemReceiverUnit(outStorage.getItemReceiverUnitName());
        }

        if ("-1".equals(outStorage.getProjectHouseNum())){
            person = new PrRepositoryPerson();
            person.setType(9);
            person.setName(outStorage.getProjectHouseNumName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存栋号失败");
            }
            outStorage.setProjectHouseNum(outStorage.getProjectHouseNumName());
        }
        if ("-1".equals(outStorage.getUseStr())){
            person = new PrRepositoryPerson();
            person.setType(12);
            person.setName(outStorage.getUse());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存用途失败");
            }
        }
    }

    /**
     *  出库数据列表
     * @param outStorage
     * @param pageable
     * @return
     */
    public Page<PrItemOutStorage> outStorageListData(PrItemOutStorage outStorage, Pageable pageable) {

        return prItemOutStorageDao.getOutStorageListData(outStorage,pageable);
    }

    /**
     * 出库明细
     * @param outStorage
     * @param pageable
     * @return
     */
    public Page<PrItemOutStorageList> getOutStorageListDetailData(PrItemOutStorageList outStorage, Pageable pageable) {

        return prItemOutStorageListDao.getOutStorageListDetailData(outStorage,pageable);
    }

    /**
     * 入库明细
     * @param storage
     * @param pageable
     * @return
     */
    public Page<PrItemInStorageList> getInStorageListDetailData(PrItemInStorageList storage, Pageable pageable) {

        return prItemInStorageListDao.getInStorageListDetailData(storage,pageable);
    }

    public PrItemDelivery getRejectedItem(Integer deliveryId) {
        PrItemDelivery itemDelivery = prItemDeliveryDao.getItemDeliveryById(new PrItemDelivery(deliveryId));
        BigDecimal inStorageSumNum = prItemInStorageListDao.getInStorageSumNum(deliveryId);
        BigDecimal rejectedNum = itemDelivery.getRejectedNum() == null ? new BigDecimal(0) : itemDelivery.getRejectedNum();
        BigDecimal bigDecimal = new BigDecimal((itemDelivery.getNum().subtract(inStorageSumNum).subtract(rejectedNum)) + "");
        itemDelivery.setBalanceNum(bigDecimal);
        return itemDelivery;
    }

    /**
     * 退货
     * @param prItemRejected
     */
    @Transactional
    public void rejectedItem(PrItemRejected prItemRejected) {
        //存退货员到表repository_person
        Integer projectId = ConstantsHolder.getContext().getCurrDataId();
        this.savePerson(prItemRejected, projectId);
        //存该退货记录到退货表rejected
        if ("-1".equals(prItemRejected.getReturnedUser())) {
            prItemRejected.setReturnedUser(prItemRejected.getReturnedUserName());
        }
        if (prItemRejectedDao.postItemRejected(prItemRejected)!=1){
            throw new BusinessException("添加退货记录失败");
        }
        PrItemDeliveryArgs args = new PrItemDeliveryArgs();
        args.setId(prItemRejected.getDeliveryId());
        PrItemDelivery receiptDataDetail = prItemDeliveryDao.getReceiptDataDetail(args);
        BigDecimal num = prItemRejected.getNum();                   //退货量
        BigDecimal balanceNum = prItemRejected.getBalanceNum();     //余存
        BigDecimal deliveryNum =receiptDataDetail.getNum();    //收货数量
        PrItemDelivery one = prItemDeliveryDao.getItemDeliveryById(prItemRejected.getDeliveryId());

        //可退数量
        BigDecimal num1 =  receiptDataDetail.getNum().subtract((receiptDataDetail.getInStorageSum().compareTo(receiptDataDetail.getOutStorageNum())==-1)?receiptDataDetail.getOutStorageNum():receiptDataDetail.getInStorageSum());
        if (num.compareTo(num1) <= 0) {
            BigDecimal rejectedNum = one.getRejectedNum();
            if (rejectedNum !=null) {
                rejectedNum = rejectedNum.add(num);
            } else {
                rejectedNum = num;
            }

            if (deliveryNum.compareTo(balanceNum) == 0) {
                //还没入库
                if (num.compareTo(deliveryNum) == 0) {
                    //全部退---直接改收货状态
                    PrItemDeliveryArgs prItemDelivery = new PrItemDeliveryArgs();
                    prItemDelivery.setId(prItemRejected.getDeliveryId());
                    prItemDelivery.setStatus(1);
                    prItemDeliveryDao.putItemDelivery(prItemDelivery);
                } else {
                    //部分退---改收货的rejectNum
                    PrItemDelivery prItemDelivery = new PrItemDelivery();
                    prItemDelivery.setId(prItemRejected.getDeliveryId());
                    prItemDelivery.setRejectedNum(num);////
                    prItemDeliveryDao.putItemDelivery(prItemDelivery);
                }
            } else {
                //有部分已经入库了
                if (num.compareTo(balanceNum) == 0) {
                    //剩下的全部退 ----  -- 修改收货表rejectedNum和入库状态
                    PrItemDelivery prItemDelivery = new PrItemDelivery();
                    prItemDelivery.setRejectedNum(rejectedNum);
                    prItemDelivery.setInStorageStatus(2);
                    prItemDelivery.setId(prItemRejected.getDeliveryId());
                    prItemDeliveryDao.putItemDeliveryNumAndStatus(prItemDelivery);

                } else {
                    //剩下的部分退 ----  -- 修改收货表的rejectedNum
                    PrItemDelivery prItemDelivery = new PrItemDelivery();
                    prItemDelivery.setId(prItemRejected.getDeliveryId());
                    prItemDelivery.setRejectedNum(rejectedNum);
                    prItemDeliveryDao.putItemDelivery(prItemDelivery);
                }

            }

            //单价
            BigDecimal price = prItemRejected.getPrice();
            //税率
            BigDecimal taxRate = prItemRejected.getTaxRate();
            //退货的金额
            BigDecimal rejectedMoney = price.multiply(num);
            //不含税退货金额
            BigDecimal taxMoney = rejectedMoney.multiply(taxRate.divide(new BigDecimal(100.00)));
            BigDecimal noTaxMoney = rejectedMoney.subtract(taxMoney);


        } else {
            throw new BusinessException("您退货数量大于了余存!");
        }
    }

    @Transactional
    public void savePerson(PrItemRejected itemRejected, Integer projectId){
        PrRepositoryPerson person;
        if ("-1".equals(itemRejected.getReturnedUser())){
            person = new PrRepositoryPerson();
            person.setType(6);
            person.setName(itemRejected.getReturnedUserName());
            person.setProjectId(projectId);
            if (prRepositoryPersonDao.postData(person)!=1){
                throw new BusinessException("保存退货人失败");
            }
        }
    }

    public PrItemOutStorage getOutStorageDetail(Integer outStorageId) {
        PrItemOutStorage prItemOutStorage = new PrItemOutStorage();
        prItemOutStorage.setId(outStorageId);
        return prItemOutStorageDao.getOutStorageDetail(prItemOutStorage);
    }

    public List<PrItemOutStorageList> getOutStorageListsByOutStorageId(Integer outStorageId) {
        PrItemOutStorageList prItemOutStorageList = new PrItemOutStorageList();
        prItemOutStorageList.setOutStorageId(outStorageId);
        return prItemOutStorageListDao.getOutStorageListsByOutStorageId(prItemOutStorageList);
    }

    /**
     * 退货列表
     * @param itemRejected
     * @param pageable
     * @return
     */
    public Page<PrItemRejected> rejectedListData(PrItemRejectedArgs itemRejected, Pageable pageable) {
        return prItemRejectedDao.getRejectedListData(itemRejected, pageable);
    }

    /**
     * 物资盘点统计
     */
    @Transactional
    public List<PrItemStatistics> statistics(PrItemStatistics entity){
        entity.setStatisticsDate(entity.getStartDate());
        List<PrItemStatistics> statisticsData = prItemStatisticsDao.getStatisticsData(entity);
        if (CollectionUtils.isEmpty(statisticsData)){
            Map<String,PrItemStatistics> dataMap = new TreeMap<>();
            List<PrItemStatistics> beginList = prItemStatisticsDao.getBeginOrEndStatisticsData(0,entity);
            List<PrItemStatistics> balanceStartData = prItemStatisticsDao.getBalanceStatisticsData(0, entity);
            Map<String, PrItemStatistics> balanceStartMap = convert2Map(balanceStartData);
            countSumPrice(0,dataMap,beginList);
            mergeBalance(0,dataMap,balanceStartMap);

            List<PrItemStatistics> addList = prItemStatisticsDao.getAddStatisticsData(entity);
            countSumPrice(1,dataMap,addList);

            List<PrItemStatistics> reduceList = prItemStatisticsDao.getReduceStatisticsData(entity);
            List<PrItemStatistics> balanceReduce = prItemStatisticsDao.getBalanceStatisticsData(1, entity);
            Map<String, PrItemStatistics> balanceReduceMap = convert2Map(balanceReduce);
            countSumPrice(2,dataMap,reduceList);
            mergeBalance(2,dataMap,balanceReduceMap);

            entity.setStatisticsDate(entity.getEndDate());
            List<PrItemStatistics> endList = prItemStatisticsDao.getBeginOrEndStatisticsData(1,entity);
            List<PrItemStatistics> balanceEndData = prItemStatisticsDao.getBalanceStatisticsData(2, entity);
            Map<String, PrItemStatistics> balanceEndMap = convert2Map(balanceEndData);
            countSumPrice(3,dataMap,endList);
            mergeBalance(3,dataMap,balanceEndMap);

            List<PrItemStatistics> dataList = new ArrayList<>();
            dataList.addAll(dataMap.values());
            return dataList;
        }else{
            return statisticsData;
        }
    }

    private void mergeBalance(Integer type, Map<String,PrItemStatistics> dataMap, Map<String, PrItemStatistics> balanceDataMap){
        PrItemStatistics tmp;
        for (String key : balanceDataMap.keySet()){
            tmp = balanceDataMap.get(key);
            PrItemStatistics data = dataMap.get(key);
            if (null == data){
                data = new PrItemStatistics();
                BeanUtils.copyProperties(tmp,data);
                data.setStatus(0);
                dataMap.put(key,data);
            }
            if (type==0){
                data.setStartSumPrice(data.getStartSumPrice().add(tmp.getBalanceSumPrice().negate()));
                data.setStartSumTax(data.getStartSumTax().add(tmp.getBalanceSumTax().negate()));
                data.setStartSumExcTaxPrice(data.getStartSumExcTaxPrice().add(tmp.getBalanceSumPrice().subtract(tmp.getBalanceSumTax()).negate()));
                if (data.getStartNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setStartPrice(data.getStartSumPrice().divide(data.getStartNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }else if (type==2){
                data.setReduceSumPrice(data.getReduceSumPrice().add(tmp.getBalanceSumPrice()));
                data.setReduceSumTax(data.getReduceSumTax().add(tmp.getBalanceSumTax()));
                data.setReduceSumExcTaxPrice(data.getReduceSumExcTaxPrice().add(tmp.getBalanceSumPrice().subtract(tmp.getBalanceSumTax())));
                if (data.getReduceNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setReducePrice(data.getReduceSumPrice().divide(data.getReduceNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }else if (type==3){
                data.setEndSumPrice(data.getEndSumPrice().add(tmp.getBalanceSumPrice().negate()));
                data.setEndSumTax(data.getEndSumTax().add(tmp.getBalanceSumTax().negate()));
                data.setEndSumExcTaxPrice(data.getEndSumExcTaxPrice().add(tmp.getBalanceSumPrice().subtract(tmp.getBalanceSumTax()).negate()));
                if (data.getEndNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setEndPrice(data.getEndSumPrice().divide(data.getEndNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }
        }
    }

    private  Map<String,PrItemStatistics> convert2Map(List<PrItemStatistics> list){
        Map<String,PrItemStatistics> dataMap = new HashMap<>();
        for (PrItemStatistics tmp : list){
            String key = tmp.getCategoryId()+"-"+tmp.getMaterialId() + "-" + tmp.getSpecificationId();
            dataMap.put(key,tmp);
        }
        return dataMap;
    }

    private void countSumPrice(Integer type, Map<String,PrItemStatistics> dataMap, List<PrItemStatistics> list){
        for (PrItemStatistics tmp : list){
            String key = tmp.getCategoryId()+"-"+tmp.getMaterialId() + "-" + tmp.getSpecificationId();
            PrItemStatistics data = dataMap.get(key);
            if (null == data){
                data = new PrItemStatistics();
                BeanUtils.copyProperties(tmp,data);
                data.setStatus(0);
                dataMap.put(key,data);
            }
            if (type == 0){//期初
                data.setStartNum(data.getStartNum().add(tmp.getNum()));
                data.setStartSumPrice(data.getStartSumPrice().add(tmp.getSumPrice()).add(tmp.getBalanceSumPrice().negate()));
                data.setStartSumTax(data.getStartSumTax().add(tmp.getTax()).add(tmp.getBalanceSumTax().negate()));
                data.setStartSumExcTaxPrice(data.getStartSumPrice().subtract(data.getStartSumTax()));
                if (data.getStartNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setStartPrice(data.getStartSumPrice().divide(data.getStartNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }else if(type == 1){
                data.setAddNum(tmp.getAddNum());
                data.setAddSumPrice(tmp.getAddSumPrice());
                data.setAddSumTax(tmp.getTax());
                data.setAddSumExcTaxPrice(tmp.getAddSumPrice().subtract(tmp.getTax()));
                if (null!=tmp.getAddNum()&&tmp.getAddNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setAddPrice(tmp.getAddSumPrice().divide(tmp.getAddNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }else if(type == 2){
                data.setReduceNum(data.getReduceNum().add(tmp.getReduceNum()));
                data.setReduceSumPrice(data.getReduceSumPrice().add(tmp.getReduceSumPrice()).add(tmp.getBalanceSumPrice()));
                data.setReduceSumTax(data.getReduceSumTax().add(tmp.getReduceSumPrice()).subtract(tmp.getReduceSumExcTaxPrice()).add(tmp.getBalanceSumTax()));
                data.setReduceSumExcTaxPrice(data.getReduceSumPrice().subtract(data.getReduceSumTax()));
                if (data.getReduceNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setReducePrice(data.getReduceSumPrice().divide(data.getReduceNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }else{//期末
                data.setEndNum(data.getEndNum().add(tmp.getNum()));
                data.setEndSumPrice(data.getEndSumPrice().add(tmp.getSumPrice()).add(tmp.getBalanceSumPrice().negate()));
                data.setEndSumTax(data.getEndSumTax().add(tmp.getTax().add(tmp.getBalanceSumTax().negate())));
                data.setEndSumExcTaxPrice(data.getEndSumPrice().subtract(data.getEndSumTax()));
                if (data.getEndNum().compareTo(BigDecimal.ZERO)!=0){
                    data.setEndPrice(data.getEndSumPrice().divide(data.getEndNum(),2,BigDecimal.ROUND_HALF_UP));
                }
            }
        }
    }

    @Transactional
    public void saveStatisticsData(List<PrItemStatistics> dataList, Integer repositoryId, Date startDate, Date endDate){
        Date now = new Date();
        int size = dataList.size();
        Long[] ids = commSequenceDao.getKey(new CommSequence("pr_item_statistics", size));
        SqlParameterSource[] sqlParameterSource = new SqlParameterSource[size];
        for (int i = 0; i<size;i++){
            Long key = ids[0] + i + 1;
            PrItemStatistics prItemStatistics = dataList.get(i);
            prItemStatistics.setRepositoryId(repositoryId);
            prItemStatistics.setStartDate(startDate);
            prItemStatistics.setEndDate(endDate);
            prItemStatistics.setId(key.intValue());
            prItemStatistics.setStatus(0);
            prItemStatistics.setCreatetime(now);
            sqlParameterSource[i] = new BeanPropertySqlParameterSource(prItemStatistics);
        }
        prItemStatisticsDao.batchUpdate(sqlParameterSource);

    }

    /**
     * 收货未入库统计
     * @param entity
     * @return
     */
    @Transactional
    public List<PrItemDelivery> receiptStatistics(PrItemDeliveryArgs entity, Integer projectId) {
        List<PrItemDelivery> list = new ArrayList<>();
        List<PrItemDelivery> prItemDeliveries = prItemDeliveryDao.receiptStatistics(entity, projectId,0);
        list.addAll(prItemDeliveries);
        List<PrItemDelivery> prItemDeliveries1 = prItemDeliveryDao.receiptStatisticsHasInstorage(entity,projectId);
        if (prItemDeliveries1 != null && prItemDeliveries1.size() > 0) {
            for (PrItemDelivery delivery :prItemDeliveries1) {
                if (delivery.getId() != null) {
                    list.add(delivery);
                }
            }
        }
        return list;
    }

    @Transactional
    public void insertBalance(PrItemBalance entity){
        entity.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        entity.setCreatetime(new Date());
        if (prItemBalanceDao.postData(entity)!=1){
            throw new BusinessException("操作失败");
        }
    }

    public Page<PrItemBalance> balanceListData(PrItemBalance entity, Pageable pageable) {

        return prItemBalanceDao.balanceListData(entity,pageable);
    }

    public PrItemBalance balanceOne(Integer id) {
        PrItemBalance balance = new PrItemBalance();
        balance.setId(id);
        return prItemBalanceDao.getBalanceOne(balance);
    }

    @Transactional
    public void updateBalance( PrItemBalance balance){
        balance.setUpdateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        balance.setUpdatetime(new Date());
        if (prItemBalanceDao.putBalanceOne(balance)!=1){
            throw new BusinessException("修改失败");
        }
    }

    /**
     * 修改出库数据
     * @param outStorageList
     */
    @Transactional
    public void updateOutStorageDetail(PrItemOutStorageList outStorageList) {
        PrItemOutStorageList oldDetailOne = prItemOutStorageListDao.getStorageDetailOne(outStorageList);
        if (null == oldDetailOne){
            throw new BusinessException("获取出库数据失败");
        }
        outStorageList.setUpdatetime(new Date());
        oldDetailOne.setUpdateUser(SpringSecurityUtil.getUser().getUserId().intValue());

        PrItemOutStorageList outStorageSum = prItemOutStorageListDao.getOutStorageSum(oldDetailOne);//出库合计

        if (prItemOutStorageListDao.updateStorageDetailOne(outStorageList)!=1){
            throw new BusinessException("修改出库数据失败");
        }

        PrItemDelivery delivery = prItemDeliveryDao.getItemDeliveryById(oldDetailOne.getDeliveryId());//收货数据
        BigDecimal leaveStore = delivery.getNum().subtract(outStorageSum.getNum()).add(oldDetailOne.getNum()).subtract(outStorageList.getNum());
        if (leaveStore.compareTo(BigDecimal.ZERO)<0){
            throw new BusinessException("出库数量不能超过收货数量");
        }
        BigDecimal leaveMoney = delivery.getSumPrice().subtract(outStorageSum.getSumPrice()).add(oldDetailOne.getSumPrice()).subtract(outStorageList.getSumPrice());
        if (leaveMoney.compareTo(BigDecimal.valueOf(-1))<0){
            throw new BusinessException("出库金额不能超过收货总金额");
        }

//        BigDecimal leaveExcTaxSumPrice = delivery.getExcTaxSumPrice().subtract(outStorageSum.getExcTaxSumPrice()).add(oldDetailOne.getExcTaxSumPrice()).subtract(outStorageList.getExcTaxSumPrice());
//        if (leaveExcTaxSumPrice.compareTo(BigDecimal.valueOf(-1))<0){
//            throw new BusinessException("出库不含税金额不能超过收货不含税总金额");
//        }
    }

    /**
     * 新增出库详情
     * @param outStorageList
     */
    @Transactional
    public void addOutStorageDetail(PrItemOutStorageList outStorageList) {
        List<PrItemOutStorageList> storageLists = JSON.parseArray(outStorageList.getDetailArray(), PrItemOutStorageList.class);
        if (CollectionUtils.isEmpty(storageLists)){
            throw new BusinessException("出库明细为空！");
        }
        Integer size = storageLists.size();
        Long[] ids = commSequenceDao.getKey(new CommSequence("pr_item_out_storage_list", size));
        SqlParameterSource[] sqlParameterSource = new SqlParameterSource[size];
        for (int i = 0; i<size;i++){
            PrItemOutStorageList entity = storageLists.get(i);
            if (null!=prItemOutStorageListDao.getStorageDetailOneByDelivery(entity)){
                throw new BusinessException("收货数据重复，请检查");
            }
            PrItemDelivery delivery = prItemDeliveryDao.getItemDeliveryById(entity.getDeliveryId());
            PrItemOutStorageList outStorageSum = prItemOutStorageListDao.getOutStorageSum(entity);
            if (null!=outStorageSum){
                BigDecimal leaveStore = delivery.getNum().subtract(outStorageSum.getNum()).subtract(entity.getNum());
                if (leaveStore.doubleValue()<0){
                    throw new BusinessException("库存不足");
                }
                BigDecimal leaveMoney = delivery.getSumPrice().subtract(outStorageSum.getSumPrice()).subtract(entity.getSumPrice());
                if (leaveMoney.doubleValue()<-1){
                    throw new BusinessException("超出最大出库总金额");
                }
            }
            Long key = ids[0] + i + 1;
            entity.setId(key.intValue());
            entity.setOutStorageId(outStorageList.getOutStorageId());
            entity.setTaxRate(delivery.getTaxRate());
            entity.setBalanceNum(delivery.getNum().subtract(null==outStorageSum?BigDecimal.ZERO:outStorageSum.getNum()));
            entity.setBalanceSumPrice(delivery.getSumPrice().subtract(null==outStorageSum?BigDecimal.ZERO:outStorageSum.getSumPrice()));
            entity.setBalanceExcTaxSumPrice(delivery.getExcTaxSumPrice().subtract(null==outStorageSum?BigDecimal.ZERO:outStorageSum.getExcTaxSumPrice()));
            entity.setTax(entity.getSumPrice().subtract(entity.getExcTaxSumPrice()));
            sqlParameterSource[i] = new BeanPropertySqlParameterSource(entity);

        }
        prItemOutStorageListDao.batchUpdate(sqlParameterSource);
    }


    public List<PrItemDelivery> receiptDeliveryListData(PrItemDelivery delivery) {

        return prItemDeliveryDao.getItemDeliveryList(delivery);
    }

    public Page<PrItemDelivery> receiptDeliveryListData(PrItemDelivery delivery, Pageable pageable) {

        return prItemDeliveryDao.getItemDeliveryList(delivery,pageable);
    }

    @Transactional
    public String getNextOutStorageNo(PrItemOutStorage prItemOutStorage) {
        Long key = prItemOutStorageDao.getOutStorageCount(prItemOutStorage);
        return "CK"+DateTimeUtil.dateTimeToString("yyyyMMdd")+ NumberUtil.buildNum(key.intValue(),6);
    }


    /**
     * 更新收票信息
     * @param outStorage
     */
    @Transactional
    public void saveBillReceiveInfo(PrItemOutStorage outStorage,PrItemInStorage prItemInStorage,Integer type) {
        User user = SpringSecurityUtil.getUser();
        if (type==1){
            if ("-1".equals(outStorage.getOldBillReceiver())){
                PrRepositoryPerson person = new PrRepositoryPerson();
                person.setType(10);
                person.setName(outStorage.getBillReceiver());
                person.setProjectId(ConstantsHolder.getContext().getCurrDataId());
                if (prRepositoryPersonDao.postData(person)!=1){
                    throw new BusinessException("保存库管员失败");
                }
            }
            outStorage.setUpdatetime(new Date());
            outStorage.setUpdateUser(user.getUserId().intValue());
            if (prItemOutStorageDao.saveBillReceiveInfo(outStorage)!=1){
                throw new BusinessException("保存收票信息失败");
            }
        }else if(type==2){
            if ("-1".equals(prItemInStorage.getOldBillReceiver())){
                PrRepositoryPerson person = new PrRepositoryPerson();
                person.setType(10);
                person.setName(prItemInStorage.getBillReceiver());
                person.setProjectId(ConstantsHolder.getContext().getCurrDataId());
                if (prRepositoryPersonDao.postData(person)!=1){
                    throw new BusinessException("保存库管员失败");
                }
            }
            prItemInStorage.setUpdatetime(new Date());
            prItemInStorage.setUpdateUser(user.getUserId().intValue());
            if (prItemInStorageDao.saveBillReceiveInfo(prItemInStorage)!=1){
                throw new BusinessException("保存收票信息失败");
            }
        }
    }

    /**
     * 查询收票
     * @param delivery
     * @return
     */
    public BaseResult receiveBillListData(PrItemDelivery delivery) {
        BaseResult baseResult = BaseResult.initBaseResult(true);
        if (null==delivery.getRepositoryId()||null==delivery.getBillReceiveDate()){
            return baseResult;
        }
        List<PrItemDelivery> outBillListData = prItemDeliveryDao.receiveOutBillListData(delivery);
        baseResult.putRdataMap("outBillListData",outBillListData);
        List<PrItemDelivery> inBillListData = prItemDeliveryDao.receiveInBillListData(delivery);
        baseResult.putRdataMap("inBillListData",inBillListData);
        baseResult.setSuccess();
        return baseResult;
    }
}
