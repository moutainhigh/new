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
            <span>营销费用预算</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/budget/get" />">年度预算编制</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>预算列表</span>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">年度预算</span>
       	</div>
        <div class="actions">
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
            <a class="btn sbold blue" href="javascript:add();"><i class="fa fa-plus-circle"></i>新增</a>
            <%--<a class="btn btn-sm blue-hoki" href="<c:url value="/admin/shop/item/xls"/>${ly:toString(entity,'pageSize,l1,l2,s1')}"><i class="fa fa-download"></i> 导出Excel</a>--%>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>项目名称</th>
                    <th>是否项目独立核算</th>
                    <th>年初预算</th>
                    <th>累计调整</th>
                    <th>调整后预算</th>
                    <th>年度剩余预算</th>
                    <%--<th width="22%">操作</th>--%>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${marketCostProjectBudgetList}" var="e" varStatus="status">
                    <tr>
                        <td>${e.projectName}</td>
                        <td>${e.isOwnName}</td>
                        <td>${e.defaultMoney}</td>
                        <td>${e.adjustMoney}</td>
                        <td>${e.currentMoney}</td>
                        <td>${e.residueMoney}</td>
                        <%--<td>
                            <a class="btn btn-xs green-meadow" href="<c:url value="/project/archives/edit?id="/>${e.projectId}"><i class="fa fa-edit"></i> 编辑 </a>
                            <c:if test="${e.isCompile eq 1&&e.associatedNum eq 0}">
                                <button type="button" class="btn btn-xs red-sunglo" onclick="del(${e.projectId},'${e.projectName}')"><i class="fa fa-times"></i> 删除 </button>
                            </c:if>
                            <c:if test="${e.isCompile eq 2&&e.isComplete eq 1}">
                                <button type="button" class="btn btn-xs blue-hoki" onclick="ok(${e.projectId},'${e.projectName}')"><i class="fa fa-check"></i> 完成 </button>
                            </c:if>
                        </td>--%>
                    </tr>
                </c:forEach>
                <c:if test="${empty marketCostProjectBudgetList}">
                	<tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<%--<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/market/budget/get${ly:toString(entity,'i1,i2,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/market/budget/get${ly:toString(entity,'i1,i2,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>--%>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/market/budget/get"/>"+"?i2="+$("#id_i2").val()+"&i1="+$("#id_i1").val();
    }

    function add() {
        window.location.href = "<c:url value="/market/budget/addpage/"/>"+$("#id_i1").val()+"/"+$("#id_i2").val();
    }

</script>