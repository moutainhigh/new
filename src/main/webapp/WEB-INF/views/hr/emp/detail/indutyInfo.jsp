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
           <td>任职起始日期</td>
           <td>任职终止日期</td>
           <td>任职单位</td>
           <td>部门</td>
           <td>岗位</td>
           <td>人员类别</td>
           <td>岗位序列</td>
           <td>职务级别</td>
           <td>是否在岗</td>
           <td>调动类别</td>
           <td>调配原因</td>
           <td>调配状态</td>
           <td>调配说明</td>
       </tr>
   </thead>
    <tbody>
    <c:forEach items="${detailList}" var="info">
        <tr>
            <td><fmt:formatDate value="${info.begindate}" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${info.enddate}" pattern="yyyy-MM-dd"/></td>
            <td>${info.companyName}</td>
            <td>${info.departmentName}</td>
            <td>${info.jobName}</td>
            <td>
            <c:forEach items="${empGroupData}" var="dd">
                <c:if test="${dd.dictDataValue eq info.empGroup}">${dd.dictDataKey}</c:if>
            </c:forEach>
            </td>
            <td>
                <c:forEach items="${jobSequenceData}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.jobSequence}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${dutyLevelData}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.dutyLevel}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>${info.onGuard eq 0?'否':(info.onGuard eq 1?'是':'')}</td>
            <td>
            ${info.chgType eq 1?'跨公司调动':(info.chgType eq 2?'内部调动':'')}
            </td>
            <td>
                <c:forEach items="${deployReasonData}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.chgCause}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${chgStatusData}" var="dd">
                    <c:if test="${dd.dictDataValue eq info.chgStatus}">${dd.dictDataKey}</c:if>
                </c:forEach>
            </td>
            <td>${info.chgNote}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>