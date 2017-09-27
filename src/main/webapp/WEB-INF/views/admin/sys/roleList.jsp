<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>


<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">角色列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/sys/roleEdit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover">
                    <thead>
                        <tr role="row" class="heading">
                            <th>序号</th>
                            <th>角色名称</th>
                            <th>所属系统</th>
                            <th>编码</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${roleList}" var="r" varStatus="step">
                            <tr>
                                <td>${step.count}</td>
                                <td>${r.name}</td>
                                <td>${r.regSystem}</td>
                                <td>${r.mark}</td>
                                <td>${r.status eq 0?'禁用':(r.status eq 1?'正常':'')}</td>
                                <td>
                                    <a href="<c:url value="/admin/sys/roleEdit/${r.regSystem}/${r.roleId}"/>" class="btn btn-sm btn-outline grey-salsa" >编辑</a>
                                    <a href="<c:url value="/admin/sys/roleAuthorityEdit/${r.roleId}"/>" class="btn btn-sm btn-outline grey-salsa"> 关联菜单</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>