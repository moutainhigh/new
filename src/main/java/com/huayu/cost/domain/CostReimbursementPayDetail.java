package com.huayu.cost.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by DengPeng on 2017-4-25 17:35:57.
*/
@Table(name = "cost_reimbursement_pay_detail")
public class CostReimbursementPayDetail extends BaseDomain  implements Serializable{

   @TableField
   private static final long serialVersionUID = -6201343523015788135L;
   //private String updateFiledKey = id,money,payId,serialsNumber,payWay,payDate,status,revokedtime,createtime,updatetime,createUser,revokedUser,updateUser,deletetime,deleteUser;
 //private String updateFiledValue = id=:id,money=:money,payId=:payId,serialsNumber=:serialsNumber,payWay=:payWay,payDate=:payDate,status=:status,revokedtime=:revokedtime,createtime=:createtime,updatetime=:updatetime,createUser=:createUser,revokedUser=:revokedUser,updateUser=:updateUser,deletetime=:deletetime,deleteUser=:deleteUser;
    private Long id;
    private BigDecimal money;
    private Long payId;
    private String serialsNumber;
    private String payWay;
   @JSONField(format="yyyy-MM-dd")
    private Date payDate;
    private Integer status;
    private Date createtime;
    private Date updatetime;
    private Integer createUser;
    private Integer updateUser;
    private Date deletetime;
    private Integer deleteUser;

    public CostReimbursementPayDetail() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    @TableField
    private Long contractId;

   public Long getContractId() {
      return contractId;
   }

   public void setContractId(Long contractId) {
      this.contractId = contractId;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public BigDecimal getMoney() {
      return money;
   }

   public void setMoney(BigDecimal money) {
      this.money = money;
   }

   public Long getPayId() {
      return payId;
   }

   public void setPayId(Long payId) {
      this.payId = payId;
   }

   public String getSerialsNumber() {
      return serialsNumber;
   }

   public void setSerialsNumber(String serialsNumber) {
      this.serialsNumber = serialsNumber;
   }

   public String getPayWay() {
      return payWay;
   }

   public void setPayWay(String payWay) {
      this.payWay = payWay;
   }

   public Date getPayDate() {
      return payDate;
   }

   public void setPayDate(Date payDate) {
      this.payDate = payDate;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public Date getCreatetime() {
      return createtime;
   }

   public void setCreatetime(Date createtime) {
      this.createtime = createtime;
   }

   public Date getUpdatetime() {
      return updatetime;
   }

   public void setUpdatetime(Date updatetime) {
      this.updatetime = updatetime;
   }

   public Integer getCreateUser() {
      return createUser;
   }

   public void setCreateUser(Integer createUser) {
      this.createUser = createUser;
   }

   public Integer getUpdateUser() {
      return updateUser;
   }

   public void setUpdateUser(Integer updateUser) {
      this.updateUser = updateUser;
   }

   public Date getDeletetime() {
      return deletetime;
   }

   public void setDeletetime(Date deletetime) {
      this.deletetime = deletetime;
   }

   public Integer getDeleteUser() {
      return deleteUser;
   }

   public void setDeleteUser(Integer deleteUser) {
      this.deleteUser = deleteUser;
   }
}
