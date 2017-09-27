package com.huayu.market.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.cost.dao.CostReimbursementPayDetailDao;
import com.huayu.cost.domain.CostReimbursementPayDetail;
import com.huayu.market.dao.MarketCostApplyPayDao;
import com.huayu.market.domain.MarketCostApplyPay;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 营销费用报销申请
 * @author ZXL 2017-07-19 10:59
 **/
@Service
public class MarketCostApplyPayService {

    @Autowired
    private MarketCostApplyPayDao marketCostApplyPayDao;
    @Autowired
    private CostReimbursementPayDetailDao costReimbursementPayDetailDao;
    @Autowired
    private MarketCostContractService marketCostContractService;
    @Resource
    private MarketCostContractSplitDetailService marketCostContractSplitDetailService;
    @Resource
    private MarketCostReimbursementDetailService marketCostReimbursementDetailService;

    /**
     * 以主键获取数据
     * @param payId
     * @return
     */
    public MarketCostApplyPay getOne(Long payId){
        MarketCostApplyPay marketCostApplyPay = new MarketCostApplyPay();
        marketCostApplyPay.setPayId(payId);
        marketCostApplyPay.setIdName("payId");
        return marketCostApplyPayDao.getOne(marketCostApplyPay);
    }

    /**
     * 台账
     * 获取数据
     * @param marketCostApplyPay
     * @return
     */
    public MarketCostApplyPay getOneOfLedger(Long payId){
        MarketCostApplyPay marketCostApplyPay = new MarketCostApplyPay();
        marketCostApplyPay.setPayId(payId);
        return marketCostApplyPayDao.getOneOfLedger(marketCostApplyPay);
    }

    /**
     * 台账
     * @param payId
     * @return
     * @throws Exception
     */
    public MarketCostApplyPay getPayOfLedger(Long payId) throws Exception{
        MarketCostApplyPay marketCostApplyPay = this.getOneOfLedger(payId);
        if (null==marketCostApplyPay){
            throw new CustomException("数据不存在");
        }
        marketCostApplyPay.setMarketCostReimbursementDetailList(marketCostReimbursementDetailService.getAllByContractIdOfLedger(payId));
        return marketCostApplyPay;
    }

    /**
     * 台账列表
     * @param pageable
     * @param marketCostApplyPay
     * @return
     */
    public Page<MarketCostApplyPay> getOfLedger(Pageable pageable, MarketCostApplyPay marketCostApplyPay){
        return marketCostApplyPayDao.getOfLedger(pageable, marketCostApplyPay);
    }

    /**
     * 分页获取营销费用
     * @param marketCostApplyPay
     * @param pageable
     * @return
     */
    public Page<MarketCostApplyPay> get(MarketCostApplyPay marketCostApplyPay, Pageable pageable){
        return marketCostApplyPayDao.get(marketCostApplyPay,pageable);
    }

    /**
     * 获取付款详情
     * @param payId
     * @return
     * @throws Exception
     */
    public MarketCostApplyPay getOneToDetail(Long payId) throws Exception{
        MarketCostApplyPay marketCostApplyPay = new MarketCostApplyPay();
        marketCostApplyPay.setPayId(payId);
        MarketCostApplyPay marketCostApplyPayDetail = marketCostApplyPayDao.getOneToDetail(marketCostApplyPay);
        if (null==marketCostApplyPayDetail){
            throw new CustomException("数据错误");
        }
        CostReimbursementPayDetail detail = new CostReimbursementPayDetail();
        detail.setPayId(payId);
        List<CostReimbursementPayDetail> detailList = costReimbursementPayDetailDao.getDetailList(detail);
        BigDecimal decimal = new BigDecimal(0);
        for (int i = 0,size = detailList.size(); i < size; i++) {
            decimal = decimal.add(detailList.get(i).getMoney());
        }
        marketCostApplyPayDetail.setActualPay(decimal);
        marketCostApplyPayDetail.setCostReimbursementPayDetailList(detailList);
        return marketCostApplyPayDetail;
    }

    /**
     * 报销付款
     * @param payId
     * @param contractId
     * @param details
     */
    @Transactional(rollbackFor = Exception.class)
    public void pay(Long payId,List<CostReimbursementPayDetail> details) throws Exception{
        MarketCostApplyPay old = this.getOne(payId);
        if (null == old){
            throw new CustomException("数据错误");
        }
        User user = SpringSecurityUtil.getUser();
        BigDecimal decimal = new BigDecimal(0);
        BigDecimal paidMoney = marketCostContractService.getOneToAllPayMoney(old.getPayId());
        for (CostReimbursementPayDetail detail : details) {
            if (null == detail.getId()){
                detail.setStatus(0);
                detail.setCreatetime(new Date());
                detail.setCreateUser(user.getUserId().intValue());
                decimal = decimal.add(detail.getMoney());
                if (costReimbursementPayDetailDao.addDetail(detail)!=1)
                    throw new CustomException("添加付款详情失败");
            }else{
                detail.setUpdatetime(new Date());
                detail.setUpdateUser(user.getUserId().intValue());
                decimal = decimal.add(detail.getMoney());
                if (costReimbursementPayDetailDao.updateDetail(detail)!=1)
                    throw new CustomException("修改付款详情失败");
            }
        }
        MarketCostApplyPay marketCostApplyPay = new MarketCostApplyPay();
        marketCostApplyPay.setPayUserId(user.getUserId());
        marketCostApplyPay.setPayDate(new Date());
        marketCostApplyPay.setPayId(payId);
        if (decimal.compareTo(old.getFinalMoney())==0){
            marketCostApplyPay.setStatus(4);
        }else{
            marketCostApplyPay.setStatus(5);
        }
        if (marketCostApplyPayDao.updatePayStatus(marketCostApplyPay)!=1){
            throw new CustomException("修改付款状态失败");
        }
        if (marketCostContractService.putToPaidMoney(old.getContractId(),paidMoney,decimal)!=1){
            throw new CustomException("修改合同已付金额失败");
        }
    }

    /**
     * 删除报销付款详情
     * @param detail
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(CostReimbursementPayDetail detail) throws Exception{
        MarketCostApplyPay old = this.getOne(detail.getPayId());
        if (null == old){
            throw new CustomException("数据错误");
        }
        detail.setContractId(old.getContractId());
        User user = SpringSecurityUtil.getUser();
        detail.setDeleteUser(user.getUserId().intValue());
        detail.setDeletetime(new Date());
        if ( costReimbursementPayDetailDao.deleteDetail(detail)!=1) {
            throw new CustomException("删除付款详情失败");
        }
        if (costReimbursementPayDetailDao.unPaidMoney(detail)!=1) {
            throw new BusinessException("修改合同付款金额失败!");
        }
        //剩余付款记录
        List<CostReimbursementPayDetail> detailList = costReimbursementPayDetailDao.getDetailList(detail);
        BigDecimal decimal = new BigDecimal("0");
        for (CostReimbursementPayDetail costReimbursementPayDetail : detailList) {
            decimal = decimal.add(costReimbursementPayDetail.getMoney());
        }
        MarketCostApplyPay marketCostApplyPay = new MarketCostApplyPay();
        marketCostApplyPay.setPayUserId(user.getUserId());
        marketCostApplyPay.setPayDate(new Date());
        marketCostApplyPay.setPayId(detail.getPayId());
        if (decimal.compareTo(new BigDecimal("0"))==0){
            marketCostApplyPay.setStatus(2);
        }else{
            marketCostApplyPay.setStatus(5);
        }
        if (marketCostApplyPayDao.updatePayStatus(marketCostApplyPay)!=1){
            throw new CustomException("修改付款状态失败");
        }
    }
}
