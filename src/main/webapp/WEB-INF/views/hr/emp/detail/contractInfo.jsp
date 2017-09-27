<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/css/components.min.css "/>" rel="stylesheet" id="style_components" type="text/css">

<style>
    .form-control{
        margin-top: 3px;
        border: #d3d6db 1px solid;
        color: #999;
        border-radius: 2px !important;
        -webkit-border-radius: 2px !important;
        -moz-border-radius: 2px !important;
        -o-border-radius: 2px !important;
        -ms-border-radius: 2px !important;
        height:28px;
        padding: 0px 12px;
        line-height: 26px;
    }
    .input-group-btn .btn{
        margin-top: 3px;
    }
    .js_tab_control>li>a{
        padding-left: 9px;
        padding-right: 9px;
    }
</style>

<div class="table-scrollable">
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th width="2%">序号</th>
        <th width="5%">合同编码</th>
        <th width="3%">业务类型</th>
        <th width="5%">合同期限类型</th>
        <th width="5%">合同期限（月）</th>
        <th width="5%">合同开始日期</th>
        <th width="5%">合同结束日期</th>
        <th width="5%">人员所属公司</th>
        <th width="5%">合同主体单位</th>
        <th width="7%">日期</th>
        <th width="10%">备注</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${detailList}" var="info" varStatus="step">
        <tr>
            <td>${step.count}</td>
            <td>${info.contractNo}</td>
            <td>${info.operTypeStr}</td>
            <td>${info.periodTypeStr}</td>
            <td>${info.periodCount}</td>
            <td><fmt:formatDate value="${info.contractStartTime}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${info.contractEndTime}" pattern="yyyy-MM-dd"/></td>
            <td>${info.companyName}</td>
            <td>
                <c:forEach items="${contractCompany}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.contractCompany}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td><fmt:formatDate value="${info.operDate}" pattern="yyyy-MM-dd"/></td>
            <td>${info.note}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>