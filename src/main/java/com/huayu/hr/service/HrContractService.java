package com.huayu.hr.service;

import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.hr.dao.HrContractDao;
import com.huayu.hr.dao.HrRemindDao;
import com.huayu.hr.domain.HrContract;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.domain.HrRemind;
import com.huayu.hr.web.args.HrContractArgs;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/3/13.
 */
@Service
public class HrContractService {

    @Autowired
    private HrContractDao hrContractDao;

    @Autowired
    private HrService hrService;


    @Autowired
    private HrRemindDao hrRemindDao;

    @Transactional
    public void postContractRegister(HrContract contract) {
        HrEmployee baseInfo = hrService.getEmpBaseInfoAndCompById(contract.getEmpId());
        contract.setEmpJobId(baseInfo.getEmpJobId());
        contract.setStatus(1);
        if (hrContractDao.post(contract)!=1){
            throw new BusinessException("添加合同失败");
        }
    }


    /**
     * 获取合同 台帐
     * @param hrContractArgs
     * @param pageable
     * @return
     */
    public Page<HrContract> getContractListData(HrContractArgs hrContractArgs, Pageable pageable) {
        HrContract contract = new HrContract();
        BeanUtils.copyProperties(hrContractArgs,contract);
        return hrContractDao.getContractList(contract,pageable);
    }

    public List<HrContract> getAllContractListData(Integer empId) {
        HrContract contract = new HrContract();
        contract.setEmpId(empId);
        return hrContractDao.getAllContractList(contract);
    }

    /**
     * 获取最后一条注册合同
     * @param empId
     * @return
     */
    public HrContract getLastEffectiveContractOne(Integer empId) {
        HrContract contract = new HrContract();
        contract.setEmpId(empId);
        return hrContractDao.getLastEffectiveContractOne(contract);
    }

    @Transactional
    public void coverContract(HrContract contract) {
        HrContract c = hrContractDao.getContractById(contract);
        if (null==c){
            throw new BusinessException("获取原合同失败");
        }
        c.setUpdatetime(new Date());
        if (hrContractDao.disableContractById(c)!=1){
            throw new BusinessException("修改原合同失败");
        }
        HrEmployee baseInfo = hrService.getEmpBaseInfoAndCompById(c.getEmpId());
        contract.setEmpJobId(baseInfo.getEmpJobId());
        contract.setContractNo(c.getContractNo());
        contract.setEmpId(c.getEmpId());
        contract.setContractType(c.getContractType());
        contract.setIsSecret(c.getIsSecret());
        contract.setIsProb("0");
        contract.setEmpNo(c.getEmpNo());
        contract.setEmpName(c.getEmpName());
        contract.setCompany(baseInfo.getCompany());
        if (contract.getOperType()!=2){
            contract.setContractCompany(c.getContractCompany());
        }
        contract.setStatus(1);
        if (hrContractDao.post(contract)!=1){
            throw new BusinessException("操作失败");
        }
        HrRemind remind = new HrRemind();
        remind.setType(3);
        remind.setEmpId(c.getEmpId());
        HrRemind remindData = hrRemindDao.getRemindDataOne(remind);
        if (null!=remindData){
            if (hrRemindDao.deleteData(remindData)!=1){
                throw new BusinessException("删除提醒数据失败");
            }
        }
    }

    public Page<HrContract> getContractEmpListData(HrContractArgs hrContractArgs, Pageable pageable) {
        HrContract contract = new HrContract();
        BeanUtils.copyProperties(hrContractArgs,contract);
        return hrContractDao.getContractEmpListData(contract,pageable);
    }

    public HrContract getEffectiveContractOne(Integer empId, Integer id) {
        HrContract contract = new HrContract();
        contract.setEmpId(empId);
        contract.setId(id);
        contract.setStatus(1);
        return hrContractDao.getContractOne(contract);
    }

    public HrContract getContractOne(Integer empId, Integer id) {
        HrContract contract = new HrContract();
        contract.setEmpId(empId);
        contract.setId(id);
        return hrContractDao.getContractOne(contract);
    }

    @Transactional
    public BaseResult postContractEdit(HrContract contract) {
        BaseResult baseResult = BaseResult.initBaseResult();
        if(null!=contract.getId()){
            if (hrContractDao.put(contract)==1){
                baseResult.setSuccess();
            }
        }
        return baseResult;
    }

    @Transactional
    public void postContractReplenish(HrContract contract) {
        HrContract lastEffectiveContractOne = hrContractDao.getLastEffectiveContractOne(contract);
        if (null == lastEffectiveContractOne){
            throw new BusinessException("未找到生效的合同");
        }else if (null==contract.getContractStartTime() || DateTimeUtil.isBefore(lastEffectiveContractOne.getContractStartTime(),contract.getContractStartTime())){
            if (hrContractDao.disableContractById(lastEffectiveContractOne)!=1){
                throw new BusinessException("修改原合同失败");
            }
        }
        HrEmployee baseInfo = hrService.getEmpBaseInfoAndCompById(contract.getEmpId());
        contract.setEmpJobId(baseInfo.getEmpJobId());
        contract.setStatus(1);
        if (hrContractDao.post(contract)!=1){
            throw new BusinessException("补录合同失败");
        }
    }
}
