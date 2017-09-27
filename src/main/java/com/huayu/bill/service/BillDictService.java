package com.huayu.bill.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.bill.dao.BillDictDao;
import com.huayu.bill.domain.BillDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Service
public class BillDictService {

    @Autowired
    private BillDictDao billDictDao;



    /**
     * 获取参数列表
     * @param billDic
     * @param pageable
     * @return
     */
    public Page<BillDic> getDictList(BillDic billDic, Pageable pageable) {
        StringBuilder sb = new StringBuilder("select c.*, p.value parentName from bill_dict c left join bill_dict p on c.parentId = p.id where c.level=:level");
        return  billDictDao.get(sb.toString(),billDic,pageable);
    }

    /**
     * 获取当前级别所有数据
     * @param level
     * @return
     */
    @Cacheable(value = "bill-dict-cache",key = "methodName+'_'+#level")
    public List<BillDic> getAllDict(Integer level) {
        BillDic billDic = new BillDic();
        billDic.setLevel(level);
        StringBuilder sb = new StringBuilder("select * from bill_dict where level=:level");
        return  billDictDao.get(sb.toString(),billDic);
    }

    /**
     * 获取子集数据
     * @param id
     * @return
     */
    @Cacheable(value = "bill-dict-cache",key = "methodName+'_'+#id")
    public List getChildDictList(Integer id) {
        BillDic billDic = new BillDic();
        billDic.setParentId(id);
        StringBuilder sb = new StringBuilder("select * from bill_dict where parentId=:parentId");
        return  billDictDao.get(sb.toString(),billDic);
    }

    /**
     * 保存数据
     * @param billDic
     * @return
     */
    @CacheEvict(value = "bill-dict-cache",allEntries = true)
    public int saveDict(BillDic billDic){
        int result ;
        if (null == billDic.getId()){
            billDic.setCreateuser(SpringSecurityUtil.getUser().getUserId());
            billDic.setId((int) billDictDao.getKey("bill_dict",billDic));
            billDic.setCreatetime(new Date());
            result = billDictDao.post(billDic);
        }else {
            billDic.setUpdateuser(SpringSecurityUtil.getUser().getUserId());
            billDic.setUpdatetime(new Date());
            result = billDictDao.put(billDic);
        }
        return result;
    }


    @Cacheable(value = "bill-dict-cache",key = "methodName+'_'+#id")
    public BillDic getDictOne(Integer id){
        BillDic billDic = new BillDic();
        billDic.setId(id);
        billDic.setIdName("id");
        return billDictDao.getOne(billDic);
    }



    @CacheEvict(value = "bill-dict-cache",allEntries = true)
    public int delBillDicOne(Integer id) {
        BillDic dict = new BillDic();
        dict.setId(id);
        dict.setIdName("id");
        List childDictList = this.getChildDictList(id);
        if (childDictList.size()>0){
            return 2;
        }
        return billDictDao.delete(dict);
    }



}
