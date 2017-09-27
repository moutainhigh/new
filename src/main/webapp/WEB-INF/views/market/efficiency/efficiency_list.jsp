<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    .search-icon {
        border: 1px solid #C6C6C6;
        background-image: url(<c:url value="/resources/admin/global/img/search.png"/> );
        line-height: 34px;
        padding-right: 20px;
        background-repeat: no-repeat;
        background-position: right center;
        cursor: pointer;
    }
    tr td,tr th{
        font-size: small !important;
    }
    label{
        font-size: small;
    }
    .form_update{
        height: 25px;
        line-height: inherit!important;
        width: 80px;
    }

</style>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index/default" />">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>项目费效分析</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/efficiency/efficiency/get" />">项目费效分析</a>
        </li>
    </ul>
    <%--<div class="page-toolbar">
        <div class="btn-group pull-right">
            <div class="mt-action-buttons">
                <div class="btn-group btn-group-circle">
                    <button type="button" class="btn btn-outline blue-hoki active" onclick="javascript:history.go(-1);">返回</button>
                    <span></span>
                </div>
            </div>
        </div>
    </div>--%>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <span class="caption-subject font-green sbold uppercase">项目费效分析</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="actions" style="float: left;">
            <label>所属公司：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <c:forEach items="${companyList}" var="company">
                    <option value="${company.companyId}" <c:if test="${company.companyId eq entity.i1}">selected</c:if> >${company.name}</option>
                </c:forEach>
            </select>
            <label>年度：</label>
            <select id="id_i2" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <c:forEach items="${yearList}" var="year">
                    <option value="${year.year}" <c:if test="${year.year eq entity.i2}">selected</c:if> >${year.year}</option>
                </c:forEach>
            </select>
            <button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
    </div>
    <div class="portlet-body" >
        <div class="table-scrollable fixed-table-container" >
            <table data-toggle="table" data-height="460" class="table table-striped table-bordered table-advance table-hover" id="tlist" >
                <thead>
                    <tr>
                        <th >项目名称</th>
                        <th >签约金额累计(A)</th>
                        <th >实际发生累计(B)</th>
                        <th >费效(B/A)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${marketCostProjectBudgets}" var="e" varStatus="status">
                        <tr>
                            <td >${e.projectName}</td>
                            <td >${e.signMoney}</td>
                            <td >${e.happenMoney}</td>
                            <td >${e.efficiencyRate}%</td>
                        </tr>
                    </c:forEach>
                    <%--<c:if test="${empty marketCostProjectBudgets}">
                        <tr><td colspan="20">没有对应的数据</td></tr>
                    </c:if>--%>
                </tbody>
            </table>
        </div>
        <div class="row">
        </div>
    </div>
</div>
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/market/efficiency/efficiency/get"/>"+"?i2="+$("#id_i2").val()+"&i1="+$("#id_i1").val();
    }

</script>