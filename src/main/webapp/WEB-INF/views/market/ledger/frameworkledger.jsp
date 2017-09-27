<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 开始全局CSS -->
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
<!-- 结束全局CSS -->

<!-- 开始主题CSS -->
<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value="/resources/admin/global/css/plugins.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-table/bootstrap-table.min.css" />" rel="stylesheet" type="text/css" />
<style type="text/css">
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    .col-md-4 {
        width:50%;
        float: left;
    }
</style>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<body class="page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid">
<div class="page-container">
<div class="page-content-wrapper">
<div class="page-content">
<div class="portlet light" >
    <div class="portlet-title">
        <div style="width: 99%;text-align: center;"> <h1>合同信息</h1> </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-4">
                <label>合同名称：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.contractName}" ></span>
            </div>
            <div class="col-md-4">
                <label>合同编号：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.contractCode}" ></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <label>我方单位名称：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.aunit}" ></span>
            </div>
            <div class="col-md-4">
                <label>对方单位名称：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.bunit}" ></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <label>所属项目：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.projectName}" ></span>
            </div>
            <div class="col-md-4">
                <label>申请日期：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${fn:substring(marketCostContract.applyDate,0 ,10)}" ></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <label>累计发生金额：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.contractMoney}" ></span>
            </div>
            <div class="col-md-4">
                <label>经办人：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${marketCostContract.agentName}" ></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <label>合同开始日期：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${fn:substring(marketCostContract.startDate,0 ,10 )}" ></span>
            </div>
            <div class="col-md-4">
                <label>合同结束日期：</label>
                <span class="form-group"><input readonly type="text" class="form-control" value="${fn:substring(marketCostContract.endDate,0 ,10 )}" ></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <label>备注：</label>
                <span class="form-group"><input readonly type="text" class="form-control"  value="${marketCostContract.remark}" ></span>
            </div>
        </div>
        <label></label>
        <div class="row">
            <table data-toggle="table" data-height="250" class="table table-width table-striped table-bordered table-advance">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>执行单名称</th>
                    <th>费项</th>
                    <th>发生日期</th>
                    <th>金额</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${marketCostContract.marketCostContractExecuteDetailList}" var="e" varStatus="varstatus">
                        <tr>
                            <td>${varstatus.count}</td>
                            <td>${e.executeTitle}</td>
                            <td>${e.threeName}</td>
                            <td>${fn:substring(e.happenDate,0 ,10)}</td>
                            <td>${e.finalMoney}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-table/bootstrap-table.js"/>"></script>