<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>


<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">资源列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/sys/resourceEdit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover">
                    <thead>
                    <tr role="row" class="heading">
                        <th>序号</th>
                        <th>类型</th>
                        <th>名称</th>
                        <th>所属系统</th>
                        <th>编码</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${resources}" var="r" varStatus="step">
                        <tr>
                            <td>${step.count}</td>
                            <td>${r.type}</td>
                            <td>${r.name}</td>
                            <td>${r.regSystem}</td>
                            <td>${r.data}</td>
                            <td>${r.status eq 0?'停用':(r.status eq 1?'启用':'')}</td>
                            <td>
                                <a href="<c:url value="/admin/sys/resourceEdit/${r.regSystem}/${r.rid}"/>" class="btn btn-sm btn-outline grey-salsa"  target="_blank">编辑</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>