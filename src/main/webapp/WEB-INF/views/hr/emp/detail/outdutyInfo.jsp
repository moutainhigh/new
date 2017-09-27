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
           <td>离职日期</td>
           <td>离职类型</td>
           <td>公司</td>
           <td>离职后公司</td>
           <td>部门</td>
           <td>离职后部门</td>
           <td>岗位</td>
           <td>人员类别</td>
           <td>离职后人员类别</td>
           <td>离职原因</td>
           <td>离职说明</td>
       </tr>
   </thead>
    <tbody>
    <c:forEach items="${detailList}" var="info">
        <tr>
            <td><fmt:formatDate value="${info.leavedate}" pattern="yyyy-MM-dd"/></td>
            <td>
                <c:forEach items="${outDutyType}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.type}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>${info.companyBeforeName}</td>
            <td>${info.companyAfterName}</td>
            <td>${info.deptBeforeName}</td>
            <td>${info.deptAfterName}</td>
            <td>${info.jobBeforeName}</td>
            <td>
                <c:forEach items="${empGroupData}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.empGroupBefore}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${empGroupData}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.empGroupAfter}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${outDutyReason}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.reason}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>
                ${info.description}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>