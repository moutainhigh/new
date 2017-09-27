<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/css/components.min.css "/>" rel="stylesheet" id="style_components" type="text/css">

<style>
    .table-bordered > tbody > tr > td{
        border: none;
        padding: 3px;
    }
    .input-group-btn .btn{
        margin-top: 3px;
    }
    .js_tab_control>li>a{
        padding-left: 9px;
        padding-right: 9px;
    }
</style>

<form id="editForm" style="margin: 0;">
    <div class="table-scrollable">
                <table class="table table-bordered table-hover" id="js_table">
                    <thead>
                    <tr>
                        <td class="col-md-1">起始日期</td>
                        <td class="col-md-1">终止日期</td>
                        <td class="col-md-1">调动类别</td>
                        <td class="col-md-1">进出状态</td>
                        <td class="col-md-1">流动原因</td>
                        <td class="col-md-1">调动状态</td>
                        <td class="col-md-1">原单位</td>
                        <td class="col-md-1">目标单位</td>
                        <td class="col-md-1">操作时间</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${detailList}" var="info">
                        <tr>
                            <td><fmt:formatDate value="${info.beginDate}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${info.endDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${info.chgTypeStr}</td>
                            <td>${info.chgFlagStr}</td>
                            <td>${info.chgCauseStr}</td>
                            <td>${info.chgStateStr}</td>
                            <td>${info.companyName}</td>
                            <td>${info.relaCompanyName}</td>
                            <td><fmt:formatDate value="${info.createtime}" pattern="yyyy-MM-dd "/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
    </div>
</form>


<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>