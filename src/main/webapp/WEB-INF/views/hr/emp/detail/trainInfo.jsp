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
        <th width="5%">培训项目名称</th>
        <th width="5%">培训活动编号</th>
        <th width="5%">培训开始时间</th>
        <th width="5%">培训截止时间</th>
        <th width="6%">培训时间（小时）</th>
        <th width="5%">培训方式</th>
        <th width="5%">培训目标</th>
        <th width="8%">培训内容</th>
        <th width="5%">培训成绩</th>
        <th width="3%">培训费用</th>
        <th width="3%">培训类别</th>
        <th width="5%">人员所属单位</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${detailList}" var="info" varStatus="step">
        <tr>
            <td>${step.count}</td>
            <td>${info.trainName}</td>
            <td>${info.trainCode}</td>
            <td><fmt:formatDate value="${info.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${info.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${info.timeCount}</td>
            <td>${info.way}</td>
            <td>${info.target}</td>
            <td>${info.content}</td>
            <td>${info.score}</td>
            <td>${info.cost}</td>
            <td>${info.typeStr}</td>
            <td>${info.companyStr}</td>
        </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>