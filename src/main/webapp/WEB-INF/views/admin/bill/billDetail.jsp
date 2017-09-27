<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link type="text/css" href="/resources/admin/global/plugins/select2/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<!-- BEGIN SAMPLE FORM PORTLET-->
<div class="portlet box green">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-settings"></i>
            <a href="<c:url value="/admin/bill/billList"/>">发票台账</a>
            <span><i class="fa fa-angle-right"></i>发票详情</span>
        </div>
    </div>
    <div class="portlet-body form">
        <form class="form-horizontal" id="editForm" method="post" autocomplete="off">
            <input type="hidden" name="id" value="${id}" />
			<div class="form-body">

            <div class="form-group">
                <label class="col-md-1 control-label">经办人：</label>
                <div class="col-md-4">
                    <select class="form-control" name="handleUser" >
                        <option value="${bill.handleUser}" selected="selected">${bill.handleUser}</option>
                    </select>
                </div>
                <label class="col-md-1 control-label">区域：</label>
                <div class="col-md-4">
                    <select class="form-control js_getNext" name="areaId" data-next="companyId" data-level="0">
                        <c:forEach items="${area}" var="dict">
                            <c:if test="${bill.areaId eq dict.id}"> <option value="${dict.id}" selected="selected">${dict.value}</option></c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">公司：</label>
                <div class="col-md-4">
                    <select class="form-control js_getNext" name="companyId" data-next="projectId" data-level="1">
                        <c:forEach items="${company}" var="dict">
                            <c:if test="${bill.companyId eq dict.id}"> <option value="${dict.id}" selected="selected">${dict.value}</option></c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">项目：</label>
                <div class="col-md-4">
                    <select class="form-control js_getNext" name="projectId" data-next="stageId" data-level="2">
                        <c:forEach items="${project}" var="dict">
                            <c:if test="${bill.projectId eq dict.id}"><option value="${dict.id}" selected="selected">${dict.value}</option></c:if>
                        </c:forEach>
                    </select>
                </div>
                <label class="col-md-1 control-label">分期：</label>
                <div class="col-md-4">
                    <select class="form-control" name="stageId" >
                        <c:forEach items="${stage}" var="dict">
                            <c:if test="${bill.stageId eq dict.id}"> <option value="${dict.id}" selected="selected">${dict.value}</option></c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">发票日期：</label>
                <div class="col-md-4">
                    <input type="text" name="invoiceDate" placeholder="年-月-日" value="<fmt:formatDate value="${bill.invoiceDate}" pattern="yyyy-MM-dd"/>" class="form-control"> </div>
                <label class="col-md-1 control-label">对方单位：</label>
                <div class="col-md-4">
                    <select class="form-control" name="targetCompany" >
                        <c:forEach items="${companyList}" var="c">
                            <c:if test="${bill.targetCompany eq c}"><option value="${c}" selected="selected">${c}</option></c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">发票代码：</label>
                <div class="col-md-4">
                    <input type="text" name="invoiceCode" value="<c:if test="${not empty id}">${bill.invoiceCode}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">发票编号：</label>
                <div class="col-md-4">
                    <input type="text" name="invoiceNum" value="<c:if test="${not empty id}">${bill.invoiceNum}</c:if>" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">货物及服务：</label>
                <div class="col-md-4">
                    <input type="text" name="subject" value="<c:if test="${not empty id}">${bill.subject}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">价税合计：</label>
                <div class="col-md-4">
                    <input type="text" name="totalMoney" readonly="readonly" value="<c:if test="${not empty id}">${bill.totalMoney}</c:if>" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">价款：</label>
                <div class="col-md-4">
                    <input type="text" name="price" value="<c:if test="${not empty id}">${bill.price}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">税额：</label>
                <div class="col-md-4">
                    <input type="text" name="tax" value="<c:if test="${not empty id}">${bill.tax}</c:if>" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">税率：</label>
                <div class="col-md-4">
                    <input type="text" name="taxRate" readonly="readonly" value="<c:if test="${not empty id}">${bill.taxRate}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">认证情况：</label>
                <div class="col-md-4">
                    <select class="form-control" name="authStatus" >
                        <option value="0" <c:if test="${bill.authStatus eq 0}">selected="selected"</c:if>>未认证</option>
                        <option value="1" <c:if test="${bill.authStatus eq 1}">selected="selected"</c:if>>已认证</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">稽核情况：</label>
                <div class="col-md-4">
                <select class="form-control" name="inspectStatus" >
                    <option value="0" <c:if test="${bill.inspectStatus eq 0}">selected="selected"</c:if>>未核实</option>
                    <option value="1" <c:if test="${bill.inspectStatus eq 1}">selected="selected"</c:if>>已核实</option>
                </select>
                </div>
                <label class="col-md-1 control-label">操作员：</label>
                <div class="col-md-4">
                    <input type="text" value="${bill.username}" class="form-control" readonly="readonly">
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-1 control-label">稽核备注：</label>
                <div class="col-md-4">
                    <textarea cols="3" rows="3" class="form-control" name="inspectNote">${bill.inspectNote}</textarea>
                </div>
            </div>

            </div>
        </form>
    </div>
</div>
<!-- END SAMPLE FORM PORTLET-->