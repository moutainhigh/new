package com.huayu.cost.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.cost.dao.*;
import com.huayu.cost.domain.*;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/4/21.
 */
@Service
public class CostService {

    @Autowired
    private CostApplyLoanDao costApplyLoanDao;

    @Autowired
    private CostLoanPayDetailDao loanPayDetailDao;

    @Autowired
    private CostBalanceDetailDao balanceDetailDao;

    @Autowired
    private CostApplyPayDao costApplyPayDao;

    @Autowired
    private CostContractDao costContractDao;

    @Autowired
    private CostReimbursementPayDetailDao costReimbursementPayDetailDao;


    public Page<CostApplyLoan> getCostApplyData(CostApplyLoan costApplyLoan , Pageable pageable){
        if (null == costApplyLoan.getStatus()){
            costApplyLoan.setStatus(2);
        }
        return costApplyLoanDao.getCostApplyData(costApplyLoan,pageable);
    }


    public CostApplyLoan getApplyLoanOne(CostApplyLoan costApplyLoan) {
        CostApplyLoan one = costApplyLoanDao.getOne(costApplyLoan);
        if (null != one){
            CostLoanPayDetail detail = new CostLoanPayDetail();
            detail.setLoanId(one.getLoanId());
            one.setLoanPayDetailList(loanPayDetailDao.getDetailList(detail));
        }
        return one;
    }

    /**
     * 保存 修改 付款
     * @param loanId
     * @param details
     */
    @Transactional
    public void saveLoanPayDetail(Long loanId, List<CostLoanPayDetail> details){
        User user = SpringSecurityUtil.getUser();
        CostApplyLoan entity = new CostApplyLoan();
        entity.setLoanId(loanId);
        entity.setStatus(4);
        if (costApplyLoanDao.updatePayStatus(entity)!=1)
            throw new BusinessException("修改付款状态失败!");
        for (int i = 0,size = details.size();i<size;i++){
            CostLoanPayDetail detail = details.get(i);
            if (null == detail.getId()){
                detail.setStatus(0);
                detail.setCreatetime(new Date());
                detail.setCreateUser(user.getUserId().intValue());
                if (loanPayDetailDao.addDetail(detail)!=1)
                    throw new BusinessException("添加付款详情失败!");
            }else{
                detail.setUpdatetime(new Date());
                detail.setUpdateUser(user.getUserId().intValue());
                if (loanPayDetailDao.updateDetail(detail)!=1)
                    throw new BusinessException("修改付款详情失败!");
            }
        }
    }

    /**
     * 删除付款详情
     * @param detail
     */
    @Transactional
    public void deleteLoanPayDetail(CostLoanPayDetail detail) {
        User user = SpringSecurityUtil.getUser();
        detail.setDeleteUser(user.getUserId().intValue());
        detail.setDeletetime(new Date());
        if ( loanPayDetailDao.deleteDetail(detail)!=1)
            throw new BusinessException("删除付款详情失败!");
    }

    /**
     * 取消付款
     * @param entity
     */
    @Transactional
    public void cancelPay(CostApplyLoan entity) {
        User user = SpringSecurityUtil.getUser();
        CostLoanPayDetail detail = new CostLoanPayDetail();
        detail.setRevokedUser(user.getUserId().intValue());
        detail.setRevokedtime(new Date());
        detail.setLoanId(entity.getLoanId());
        if (loanPayDetailDao.cancelDetailPayStatus(detail)<1)
            throw new BusinessException("取消付款详情失败!");
        entity.setStatus(2);
        if (costApplyLoanDao.updatePayStatus(entity)!=1)
            throw new BusinessException("取消付款失败!");
    }


    public CostApplyLoan getRepayLoanOne(CostApplyLoan costApplyLoan) {
        CostApplyLoan one = costApplyLoanDao.getOne(costApplyLoan);
        if (null != one){
            CostBalanceDetail detail = new CostBalanceDetail();
            detail.setLoanId(one.getLoanId());
            one.setLoanRePayDetailList(balanceDetailDao.getDetailList(detail));
        }
        return one;
    }

    @Transactional
    public void saveLoanRePayDetail(Long loanId, List<CostBalanceDetail> details) {
        User user = SpringSecurityUtil.getUser();
        BigDecimal decimal = new BigDecimal(0);
        for (int i = 0,size = details.size();i<size;i++){
            CostBalanceDetail detail = details.get(i);
            if (null == detail.getBalanceId()){
                detail.setType(2);
                detail.setStatus(2);
                detail.setCreateDate(new Date());
                detail.setCreateUser(user.getUserId().intValue());
                decimal = decimal.add(detail.getTheMoney());
                if (balanceDetailDao.addDetail(detail)!=1)
                    throw new BusinessException("添加付款详情失败!");
            }else{
                detail.setUpdatetime(new Date());
                detail.setUpdateUser(user.getUserId().intValue());
                decimal = decimal.add(detail.getTheMoney());
                if (balanceDetailDao.updateDetail(detail)!=1)
                    throw new BusinessException("修改付款详情失败!");
            }
        }
        CostApplyLoan entity = new CostApplyLoan();
        entity.setLoanId(loanId);
        entity.setRepayDate(new Date());
        entity.setRepayMoney(decimal);
        if (costApplyLoanDao.updateRepayMoney(entity)!=1)
            throw new BusinessException("修改还款余额失败!");
    }

    public Page<CostApplyPay> getCostApplyPayData(CostApplyPay costApplyPay , Pageable pageable){
        if (null == costApplyPay.getStatus()){
            costApplyPay.setStatus(2);
        }
        return costApplyPayDao.getCostApplyPayData(costApplyPay,pageable);
    }

    public CostApplyPay getCostApplyPayOneDetail(CostApplyPay costApplyPay){
        CostApplyPay oneDetail = costApplyPayDao.getOneDetail(costApplyPay);
        CostReimbursementPayDetail detail = new CostReimbursementPayDetail();
        detail.setPayId(oneDetail.getPayId());
        List<CostReimbursementPayDetail> detailList = costReimbursementPayDetailDao.getDetailList(detail);
        BigDecimal decimal = new BigDecimal(0);
        for (int i = 0,size = detailList.size(); i < size; i++) {
            decimal = decimal.add(detailList.get(i).getMoney());
        }
        oneDetail.setActualPay(decimal);
        oneDetail.setReimbPayDetailList(detailList);
        return oneDetail;
    }


    /**
     * 报销付款
     * @param payId
     * @param contractId
     * @param details
     */
    @Transactional
    public void saveReimbPayDetail(Long payId, Long contractId, List<CostReimbursementPayDetail> details) {
        CostApplyPay old = costApplyPayDao.getOneDetail(new CostApplyPay(payId));
        if (null == old){
            throw new BusinessException("查找报销数据!");
        }
        User user = SpringSecurityUtil.getUser();
        BigDecimal decimal = new BigDecimal(0);
        for (int i = 0,size = details.size();i<size;i++){
            CostReimbursementPayDetail detail = details.get(i);
            if (null == detail.getId()){
                detail.setStatus(0);
                detail.setCreatetime(new Date());
                detail.setCreateUser(user.getUserId().intValue());
                decimal = decimal.add(detail.getMoney());
                if (costReimbursementPayDetailDao.addDetail(detail)!=1)
                    throw new BusinessException("添加付款详情失败!");
            }else{
                detail.setUpdatetime(new Date());
                detail.setUpdateUser(user.getUserId().intValue());
                decimal = decimal.add(detail.getMoney());
                if (costReimbursementPayDetailDao.updateDetail(detail)!=1)
                    throw new BusinessException("修改付款详情失败!");
            }
        }
        CostApplyPay costApplyPay = new CostApplyPay();
        costApplyPay.setPayUserId(user.getUserId());
        costApplyPay.setPayDate(new Date());
        costApplyPay.setPayId(payId);
        if (decimal.compareTo(old.getFinalMoney())==0){
            costApplyPay.setStatus(4);
        }else{
            costApplyPay.setStatus(5);
        }
        if (costApplyPayDao.updatePayStatus(costApplyPay)!=1){
            throw new BusinessException("修改付款状态失败!");
        }
        if (null != contractId && contractId !=0L){
            CostContract entity = new CostContract();
            entity.setContractId(contractId);
            entity.setPaidMoney(decimal);
            entity.setPayUser(user.getUserId());
            entity.setPayTime(new Date());
            if (costContractDao.updatePaidMoney(entity)!=1)
                throw new BusinessException("修改合同已付失败!");
        }

    }

    /**
     * 删除报销付款详情
     * @param detail
     */
    @Transactional
    public void deleteReimbPayDetail(CostReimbursementPayDetail detail) {
        User user = SpringSecurityUtil.getUser();
        detail.setDeleteUser(user.getUserId().intValue());
        detail.setDeletetime(new Date());
        if (null!=detail.getContractId() && 0!=detail.getContractId()){
            if (costReimbursementPayDetailDao.unPaidMoney(detail)!=1)
                throw new BusinessException("修改合同付款金额失败!");
        }
        if ( costReimbursementPayDetailDao.deleteDetail(detail)!=1)
            throw new BusinessException("删除付款详情失败!");
        //剩余付款记录
        List<CostReimbursementPayDetail> detailList = costReimbursementPayDetailDao.getDetailList(detail);
        BigDecimal decimal = new BigDecimal(0);
        for (int i = 0,size = detailList.size(); i < size; i++) {
            CostReimbursementPayDetail tmp =detailList.get(i);
            decimal = decimal.add(tmp.getMoney());
        }
        CostApplyPay costApplyPay = new CostApplyPay();
        costApplyPay.setPayId(detail.getPayId());
        if (decimal.equals(new BigDecimal(0))){
            costApplyPay.setStatus(2);
        }else{
            costApplyPay.setStatus(5);
        }
        if (costApplyPayDao.updatePayStatus(costApplyPay)!=1){
            throw new BusinessException("修改付款状态失败!");
        }
    }
}
