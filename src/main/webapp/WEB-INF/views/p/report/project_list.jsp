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
    label{
        font-size: small;
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
            <span>项目进度报表</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/project/report/gettoreport" />">项目计划完成总表</a>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">项目计划完成总表</span>
       	</div>
        <div class="actions">
            <label>项目名称：</label>
            <input id="id_s1" class="form-control input-inline input-sm" type="text" value="${entity.s1}" placeholder="项目名称"/>
            <label>所属区域：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <option value="0">请选择区域</option>
                <c:forEach items="${projectAreaList}" var="projectArea">
                    <option value="${projectArea.id}" <c:if test="${projectArea.id eq entity.i1}">selected</c:if> >${projectArea.name}</option>
                </c:forEach>
            </select>
			<button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
            <a class="btn btn-sm blue-hoki" href="<c:url value="/project/report/gettoreportxml"/>${ly:toString(entity,'pageSize,i1,s1')}"><i class="fa fa-download"></i> 导出Excel</a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>所属区域</th>
                    <th>项目名称</th>
                    <th>节点个数</th>
                    <th>完成个数</th>
                    <th>节点完成率</th>
                    <th>节点权重</th>
                    <th>完成节点权重</th>
                    <th>完成得分率</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.areaName}</td>
                        <td>${e.projectName}</td>
                        <td>${e.nodeNum}</td>
                        <td>${e.successNodeNum}</td>
                        <td>${e.successNodeNumRate}</td>
                        <td>${e.allWeight}</td>
                        <td>${e.successWeight}</td>
                        <td>${e.successWeightRate}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.content}">
                	<tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/project/report/gettoreport${ly:toString(entity,'i1,s1,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/project/report/gettoreport${ly:toString(entity,'i1,s1,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/project/report/gettoreport"/>"+ "${ly:toString(entity,'pageSize,dt,dtn')}"+"&s1="+$.trim($("#id_s1").val())+"&i1="+$("#id_i1").val();
    }

</script>