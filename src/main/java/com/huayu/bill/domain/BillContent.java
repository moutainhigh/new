package com.huayu.bill.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2016/11/16.
 */
@Table(name = "bill_content")
public class BillContent extends BaseDomain implements Serializable {

    @TableField
    private static final long serialVersionUID = -1746451859464355774L;

    private Long id;
    private String handleUser;
    private Integer areaId;
    private Integer companyId;
    private Integer projectId;
    private Integer stageId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;
    private String targetCompany;
    private String invoiceCode;
    private String invoiceNum;
    private String subject;
    private BigDecimal totalMoney;
    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal taxRate;
    private Integer authStatus;
    private Integer inspectStatus;
    private String inspectNote;
    private String certificateCode;

    private Long createuser;
    private Date createtime;
    private Long updateuser;
    private Date updatetime;

    @TableField
    private String username;
    @TableField
    private String area;
    @TableField
    private String company;
    @TableField
    private String project;
    @TableField
    private String stage;
    @TableField
    private List<String> handleUsers;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invoiceStartDate;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invoiceEndDate;

    public BillContent() {
        this.dt = "desc";
        this.dtn = "id";
        this.pageSize = 10;
    }

    public BillContent(Long id, String handleUser, Integer areaId, Integer companyId, Integer projectId, Integer stageId, Date invoiceDate,
                       String targetCompany, String invoiceCode, String invoiceNum, String subject, BigDecimal totalMoney, BigDecimal price,
                       BigDecimal tax, BigDecimal taxRate, Integer authStatus, Integer inspectStatus, String inspectNote, String certificateCode,
                       Long createuser, Date createtime, Long updateuser, Date updatetime) {
        this.id = id;
        this.handleUser = handleUser;
        this.areaId = areaId;
        this.companyId = companyId;
        this.projectId = projectId;
        this.stageId = stageId;
        this.invoiceDate = invoiceDate;
        this.targetCompany = targetCompany;
        this.invoiceCode = invoiceCode;
        this.invoiceNum = invoiceNum;
        this.subject = subject;
        this.totalMoney = totalMoney;
        this.price = price;
        this.tax = tax;
        this.taxRate = taxRate;
        this.authStatus = authStatus;
        this.inspectStatus = inspectStatus;
        this.inspectNote = inspectNote;
        this.certificateCode = certificateCode;
        this.createuser = createuser;
        this.createtime = createtime;
        this.updateuser = updateuser;
        this.updatetime = updatetime;
    }


    @Override
    public String toString(String s) {
        StringBuilder sb = new StringBuilder("?");
        if(invoiceNum!=null && s.contains("invoiceNum")) sb.append("invoiceNum="+invoiceNum);
        if(invoiceDate!=null && s.contains("invoiceDate")) sb.append("&invoiceDate="+invoiceDate);
        if(authStatus!=null && s.contains("authStatus")) sb.append("&authStatus="+authStatus);
        if(inspectStatus!=null && s.contains("inspectStatus")) sb.append("&inspectStatus="+inspectStatus);
        if(certificateCode!=null && s.contains("certificateCode")) sb.append("&certificateCode="+inspectStatus);
        if(pageNo!=null && s.contains("pageNo"))
            sb.append("&pageNo="+pageNo);
        if(pageSize!=null && s.contains("pageSize"))
            sb.append("&pageSize="+pageSize);
        if(dt!=null && s.contains("dt"))
            sb.append("&dt="+dt);
        if(dtn!=null && s.contains("dtn"))
            sb.append("&dtn="+dtn);
        return sb.toString();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTargetCompany() {
        return targetCompany;
    }

    public void setTargetCompany(String targetCompany) {
        this.targetCompany = targetCompany;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getInspectNote() {
        return inspectNote;
    }

    public void setInspectNote(String inspectNote) {
        this.inspectNote = inspectNote;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public Long getCreateuser() {
        return createuser;
    }

    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(Long updateuser) {
        this.updateuser = updateuser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }


    public String getInspectStatusStr() {
        if (inspectStatus==0){
            return "未核实";
        }else  if (inspectStatus==1){
            return "已核实";
        }else {
            return "";
        }
    }

    public String getAuthStatusStr() {
        if (authStatus==0){
            return "未认证";
        }else  if (authStatus==1){
            return "已认证";
        }else {
            return "";
        }
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public List<String> getHandleUsers() {
        return handleUsers;
    }

    public void setHandleUsers(List<String> handleUsers) {
        this.handleUsers = handleUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getInvoiceStartDate() {
        return invoiceStartDate;
    }

    public void setInvoiceStartDate(Date invoiceStartDate) {
        this.invoiceStartDate = invoiceStartDate;
    }

    public Date getInvoiceEndDate() {
        return invoiceEndDate;
    }

    public void setInvoiceEndDate(Date invoiceEndDate) {
        this.invoiceEndDate = invoiceEndDate;
    }
}
